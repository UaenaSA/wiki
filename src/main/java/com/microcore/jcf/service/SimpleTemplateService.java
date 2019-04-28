package com.microcore.jcf.service;

import com.microcore.jcf.config.BusinessModuleConfig;
import com.microcore.jcf.config.Global;
import com.microcore.jcf.pojo.dto.base.QueryParams;
import com.microcore.jcf.service.base.IBaseService;
import com.microcore.jcf.util.ContextHolderUtil;
import com.microcore.util.misc.Generator;
import com.microcore.util.reflect.ClassUtil;
import com.microcore.util.reflect.ReflectionUtil;
import org.apache.ibatis.binding.MapperProxy;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 提供基于entity泛型的简单CRUD实现抽象业务层
 *
 * @param <T> 数据库实体对象
 * @author leizhenyang
 */
public abstract class SimpleTemplateService<T extends Object> extends VariableQueryTemplateService implements IBaseService<T> {
    private Logger logger = LoggerFactory.getLogger(SimpleTemplateService.class);

    /**
     * add、modify、delete接口是否启用环绕
     */
    public static class Around {

        /**
         * 日志对象
         */
        private static Logger logger = LoggerFactory.getLogger(Around.class);

        private static final ThreadLocal<Boolean> TEMP = new ThreadLocal<>();

        /**
         * 执行以下方法<br/>
         * saveBefore()、updateBefore()、deleteBefore()<br/>
         * saveAfter()、updateAfter()、deleteAfter()<br/>
         */
        public static void yes() {
            TEMP.set(true);
        }

        /**
         * 不执行以下方法<br/>
         * saveBefore()、updateBefore()、deleteBefore()<br/>
         * saveAfter()、updateAfter()、deleteAfter()<br/>
         */
        public static void no() {
            TEMP.set(false);
        }

        public static void remove() {
            TEMP.remove();
        }

        public static boolean current() {
            boolean flag;
            if (TEMP.get() == null) {
                flag = true;
            } else {
                flag = TEMP.get();
            }
            if (logger.isDebugEnabled()) {
                logger.debug("current status is " + flag);
            }
            return flag;
        }
    }

    @Autowired
    private SqlSessionTemplate template;

    /**
     * 获取持久化接口
     *
     * @return 持久化接口
     */
    protected abstract Mapper<T> getMapper();

    /**
     * 获取模块名称，用于注入日志来源，强制实现类必须填写模块名称
     *
     * @return
     */
    protected abstract String getModuleName();

    {
        if (getModuleName() != null) {
            BusinessModuleConfig.modules.put(this.getClass(), getModuleName());
        }
    }

    /**
     * 统一实例化Example的方法
     *
     * @return Example
     */
    protected final Example newInstanceExample() {
        return new Example(ClassUtil.getClassGenericType(this.getClass()));
    }

    /**
     * 获取当前泛型实体的Sequence
     *
     * @return 序列号
     */
    public final int getSequence() {
        Table table = ClassUtil.getClassGenericType(this.getClass()).getAnnotation(Table.class);
        return 0;//sequence.getSequence(table.name());
    }

    public final String getUUID() {
        return Generator.uuid2();//没有通过-分割
    }


    /**
     * 缓存 代理后的Mapper-真正的Mapper，避免每次获取
     */
    private static final Map<Mapper, Class> MAPPER_CACHE = new ConcurrentHashMap<>();

    /**
     * 批量执行操作
     *
     * @param batchExecute 批量操作接口
     */
    public void batch(IBatchExecute batchExecute) {
        SqlSession sqlSession = template.getSqlSessionFactory().openSession(ExecutorType.BATCH,
                false);
        Mapper proxyMapper = getMapper();
        Class clazz = MAPPER_CACHE.get(proxyMapper);
        if (clazz == null) {
            MapperProxy mapperProxy = (MapperProxy) Proxy.getInvocationHandler(getMapper());
            clazz = ReflectionUtil.getFieldValue(mapperProxy, "mapperInterface");
            MAPPER_CACHE.put(proxyMapper, clazz);
        }

        Mapper realMapper = (Mapper) sqlSession.getMapper(clazz);
        try {
            batchExecute.execute(realMapper);
            sqlSession.commit();
            sqlSession.clearCache();
        } catch (Exception e) {
            sqlSession.rollback();
            throw e;
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 对于实体类中ID为序列的情况且ID没有手动赋值的情况，其保存和更新的时候该方法被调用<br>
     * 通过反射设置实体主键值，若是组合主键不适用，需要重写
     *
     * @param entity 实体
     */
    protected void setPrimaryKey(T entity) {
        Class clazz = entity.getClass();
        Set<Field> fields = ClassUtil.getAnnotatedFields(clazz, Id.class);
        for (Field field : fields) {
            field.setAccessible(true);
            Object fieldValue = ReflectionUtil.getFieldValue(entity, field);
            if (fieldValue == null || "".equals(fieldValue)) {
                if ("java.lang.String".equals(field.getType().getTypeName())) {
                    String uuid = getUUID();
                    ReflectionUtil.setField(entity, field, uuid);
                } else {
                    ReflectionUtil.setField(entity, field, getSequence());
                }
            }
        }
    }

    /**
     * 保存前强制执行的方法
     *
     * @param entity 实体
     */
    private void saveForceBefore(T entity) {
        if (entity == null) {
            return;
        }
        // setPrimaryKey(entity);
        if (Around.current()) {
            saveBefore(entity);
        }
        try {
            ReflectionUtil.setFieldValue(entity, "createTime", new Date());
            ReflectionUtil.setFieldValue(entity, "updateTime", new Date());

            Object createUser = ReflectionUtil.getFieldValue(entity, "createUser");
            if (createUser == null || !Global.SYSTEM_USER_SET.contains(createUser)) {
                // 注入创建用户
                ReflectionUtil.setFieldValue(entity, "createUser", currentUserId());
            }

            Object updateUser = ReflectionUtil.getFieldValue(entity, "updateUser");
            if (updateUser == null || !Global.SYSTEM_USER_SET.contains(updateUser)) {
                // 注入修改用户
                ReflectionUtil.setFieldValue(entity, "updateUser", currentUserId());
            }
        } catch (Exception e) {
            // 不做处理
            return;
        }
    }

    /**
     * 保存前
     *
     * @param entity 实体
     */
    protected void saveBefore(T entity) {

    }

    /**
     * 保存后
     *
     * @param entity 实体
     */
    protected void saveAfter(T entity) {

    }

    /**
     * 最终执行保存的方法<br/>
     * 该强制执行saveBefore方法
     *
     * @param entity 实体
     * @return true = 保存成功 false = 保存失败
     */
    private boolean isSaved(T entity) {
        try {
            saveForceBefore(entity);
            if (saveExecute(entity) == 1) {
                //保存成功
                if (Around.current()) {
                    saveAfter(entity);
                }
                return true;
            }
            return false;
        } finally {
            // 操作完成后，设置Around方法需要执行
            Around.yes();
        }
    }

    /**
     * 保存操作真实的执行方法
     *
     * @param entity 实体
     * @return 操作结果
     */
    protected int saveExecute(T entity) {
        return getMapper().insertSelective(entity);
    }


    /**
     * 保存对象并返回所保存的实体<strong>实体类必须含有主键</strong><br>
     * 可重写的依赖方法:<br>
     * <li>validWithException()}</li>
     * <li>saveBefore</li>
     * <li>saveExecute</li>
     * <li>saveAfter</li>
     *
     * @param entity 实体
     * @return 所保存的实体类，
     */
    @Override
    public T save(T entity) {
        if (isSaved(entity)) {
            return entity;
        }
        return null;
    }

    /**
     * 保存指定对象<strong>实体类必须含有主键</strong>，返回保存结果！true=保存成功  false=保存失败<br>
     * 该方法内部默认调用顺序如下所示，以下方法都可被手动重写<br>
     * <li>validWithException()</li>
     * <li>saveBefore</li>
     * <li>saveExecute</li>
     * <li>saveAfter</li>
     *
     * @param entity 实体<strong>（实体类必须含有主键）</strong>
     * @return true=保存成功  false=保存失败
     */
    @Override
    public boolean saveOnly(T entity) {
        return isSaved(entity);
    }

    /**
     * 修改前强制执行的方法
     *
     * @param entity 实体
     */
    private void updateForceBefore(T entity) {
        if (entity == null) {
            return;
        }
        if (Around.current()) {
            updateBefore(entity);
        }
        try {
            ReflectionUtil.setFieldValue(entity, "updateTime", new Date());
            Object updateUser = ReflectionUtil.getFieldValue(entity, "updateUser");
            if (updateUser == null || !Global.SYSTEM_USER_SET.contains(updateUser)) {
                // 注入修改用户
                ReflectionUtil.setFieldValue(entity, "updateUser", currentUserId());
            }
        } catch (Exception e) {
            // 不做处理
            return;
        }
    }

    /**
     * 修改前
     *
     * @param entity 实体
     */
    protected void updateBefore(T entity) {

    }

    /**
     * 修改后
     *
     * @param entity 实体
     */
    protected void updateAfter(T entity) {

    }

    /**
     * 最终执行保存的方法<br/>
     * 该强制执行saveBefore方法
     *
     * @param entity 实体
     * @return true = 保存成功 false = 保存失败
     */
    private boolean isUpdated(T entity) {
        try {
            updateForceBefore(entity);
            if (updateExecute(entity) == 1) {
                if (Around.current()) {
                    updateAfter(entity);
                }
                return true;
            }
            return false;
        } finally {
            Around.yes();
        }
    }

    /**
     * 修改操作真正执行的方法
     *
     * @param entity 实体(必须带有主键)
     * @return 操作结果
     */
    protected int updateExecute(T entity) {
        return getMapper().updateByPrimaryKeySelective(entity);
    }


    @Override
    public T modify(T entity) {
        if (isUpdated(entity)) {
            return entity;
        }
        return null;
    }

    @Override
    public boolean modifyOnly(T entity) {
        return isUpdated(entity);
    }

    /**
     * 删除前
     *
     * @param entity 实体
     */
    protected void deleteBefore(T entity) {

    }

    /**
     * 删除后
     *
     * @param entity 实体
     */
    protected void deleteAfter(T entity) {

    }

    /**
     * 删除真正执行的方法
     *
     * @param entity 实体
     * @return 操作结果
     */
    protected int deleteExecute(T entity) {
        return getMapper().delete(entity);
    }


    @Override
    public boolean delete(T entity) {
        deleteBefore(entity);
        deleteExecute(entity);
        deleteAfter(entity);
        return true;
    }

    @Override
    public T findOne(T entity) {
        return findOneExecute(entity);
    }

    /**
     * findOne接口方法的查询方法，若需要改变查询规则，则重写该方法
     *
     * @param entity
     * @return
     */
    protected T findOneExecute(T entity) {
        return getMapper().selectOne(entity);
    }

    @Override
    public List<T> find(T entity) {
        return findExecute(entity);
    }

    @Override
    public List<T> findAll() {
        return getMapper().selectAll();
    }

    /**
     * find接口方法的查询方法，若需要改变查询规则，则重写该方法
     *
     * @param entity
     * @return
     */
    protected List<T> findExecute(T entity) {
        return getMapper().select(entity);
    }

    /**
     * 获取分页查询条件<br>
     * <strong>通常情况下，如果使用Example来进行分页，则覆盖此方法来获取业务Example条件</strong>
     *
     * @param params 分页查询参数
     * @return 查询条件
     */
    protected Example getPageCondition(QueryParams params) {
        return newInstanceExample();
    }

    ///**
    // * 核心的分页方法，大多数情况下用户前端分页查询显示数据
    // *
    // * @param params   分页查询参数
    // * @param <Result>
    // * @return
    // */
    //@Override
    //public <Result> PageInfo<Result> page(PageParams params) {
    //    //模板方法
    //    List<Result> list = findPage(params);
    //    PageInfo pageInfo = new PageInfo<>(list);
    //    return pageInfo;
    //}
    //
    //@Override
    //public <Result> List<Result> list(QueryParams params) {
    //    return findPageOrListExecute(params);
    //}
    //
    ///**
    // * 分页查询
    // *
    // * @param params 查询参数
    // * @return 结果集
    // */
    //private List findPage(PageParams params) {
    //    PageHelper.clearPage();
    //    PageHelper.startPage(params.getPageNumber(), params.getPageSize());
    //    return findPageOrListExecute(params);
    //}
    //

    /**
     * 条件查询
     * <strong>当需要调用自己手写的SQL的时候覆盖此方法</strong>
     *
     * @param params 查询条件
     * @return 结果集
     */
    protected List findPageOrListExecute(QueryParams params) {
        return getMapper().selectByExample(getPageCondition(params));
    }

    /**
     * 是否存在
     *
     * @param entity 实体
     * @return true:存在  false:不存在
     */
    @Override
    public boolean isExist(T entity) {
        List<T> results = getMapper().select(entity);
        if (results.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * 从RequestContextHolder中获取HttpServlet对象
     *
     * @return 用户id
     */
    protected String currentUserId() {
        if (Global.isJunitTest) {
            return "TEST";
        }
        String userKey = "user";
        Object obj = ContextHolderUtil.getRequestAttributes(userKey);
        if (obj == null) {
            throwError("无法获取到当前用户");
        }
        return obj.toString();
    }
}
