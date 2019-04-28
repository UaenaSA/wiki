package com.microcore.modules.wiki.service;

import com.github.pagehelper.PageInfo;
import com.microcore.modules.wiki.controller.params.LogParam;
import com.microcore.modules.wiki.entity.LogEntity;
import com.microcore.modules.wiki.mapper.LogEntityMapper;
import com.xiaoleilu.hutool.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.UUID;

/**
 * @Author JJJ
 * @Description:
 * @Date:Create in  2019/1/17-18:51
 */
@Service
public class LogManagerService extends BaseService<LogEntity> {


    @Autowired
    private LogEntityMapper mapper;

    @Override
    public Mapper getMapper() {
        return mapper;
    }

    //gcy 2019.4.17 根据新增操作是否成功返回boolean变量  begin
    /*@Override
    public LogEntity save(LogEntity entity) {
        entity.setId(UUID.randomUUID().toString());
        entity.setTime(new Date());
        mapper.insertSelective(entity);
        return entity;
    }*/
    @Override
    public boolean save(LogEntity entity) {
        entity.setId(UUID.randomUUID().toString());
        entity.setTime(new Date());
        int result = mapper.insertSelective(entity);
        if (result == 1) {
            return true;
        }
        return false;
    }
    //gcy 2019.4.17 根据新增操作是否成功返回boolean变量  end


    /**
     * 插入日志
     *
     * @param problemId
     * @param problemTitle
     * @param type
     * @param problemSattus
     */
    public void insert(String problemId, String problemTitle, int type, int problemSattus,String user) {
        LogEntity logEntity = new LogEntity();
        logEntity.setTime(new Date());
        logEntity.setId(UUID.randomUUID().toString());
        logEntity.setProblemId(problemId);
        logEntity.setProblemTitle(problemTitle);
        logEntity.setType(type);
        logEntity.setProblemStatus(problemSattus);
        logEntity.setUser(user);
        this.save(logEntity);
    }

    @Override
    public boolean update(LogEntity logEntity){
        Example example = new Example(LogEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("problemId",logEntity.getProblemId());
        mapper.updateByExampleSelective(logEntity,example);
        return true;
    }

    /**
     * 分页查询
     *
     * @param param
     * @return
     */
    public PageInfo<LogEntity> findPage(LogParam param) {
        //先设置分页查询参数
        pageParam = param.getLogEntity();
        PageInfo<LogEntity> page = super.findPage(param.getPageInfo());
        return page;
    }

    /**
     * 查询今天的日志
     *
     * @param param
     * @return
     */
    public PageInfo<LogEntity> findToday(LogParam param) {
        Example example = new Example(LogEntity.class);
        example.createCriteria().andGreaterThanOrEqualTo("time", DateUtil.beginOfDay(new Date()));
        pageParam = example;
        PageInfo<LogEntity> page = super.findPage(param.getPageInfo());
        return page;
    }


    /**
     * 查询一周的日志
     *
     * @param param
     * @return
     */
    public PageInfo<LogEntity> findWeek(LogParam param) {
        Example example = new Example(LogEntity.class);
        example.createCriteria().andGreaterThanOrEqualTo("time", DateUtil.lastWeek());
        pageParam = example;
        PageInfo<LogEntity> page = super.findPage(param.getPageInfo());
        return page;
    }

    /**
     * 查询一月的日志
     *
     * @param param
     * @return
     */
    public PageInfo<LogEntity> findMonth(LogParam param) {
        Example example = new Example(LogEntity.class);
        example.createCriteria().andGreaterThanOrEqualTo("time", DateUtil.lastMonth());
        pageParam = example;
        PageInfo<LogEntity> page = super.findPage(param.getPageInfo());
        return page;
    }


}
