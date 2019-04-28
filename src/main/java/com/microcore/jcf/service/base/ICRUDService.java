package com.microcore.jcf.service.base;

import java.util.List;

/**
 * @author leizhenyang
 */
public interface ICRUDService<T>
{
    /**
     * 新增并返回实体
     *
     * @param entity 实体
     * @return 被操作的实体
     */
    T save(T entity);

    /**
     * 仅新增
     *
     * @param entity 实体
     * @return 操作是否成功
     */
    boolean saveOnly(T entity);

    /**
     * 修改并返回实体
     *
     * @param entity 实体
     * @return 被操作的实体
     */
    T modify(T entity);

    /**
     * 仅修改
     *
     * @param entity 实体
     * @return 操作是否成功
     */
    boolean modifyOnly(T entity);

    /**
     * 删除
     *
     * @param entity 实体
     * @return 操作是否成功
     */
    boolean delete(T entity);

    /**
     * 以泛型实体为参数查询一个实体
     *
     * @param entity 实体
     * @return 实体
     */
    T findOne(T entity);

    /**
     * 以泛型实体为参数查询多个实体
     *
     * @param entity 实体
     * @return 实体集合
     */
    List<T> find(T entity);

    /**
     * 查询所有数据
     *
     * @return
     */
    List<T> findAll();

    /**
     * 是否存在
     * @param entity
     * @return
     */
    boolean isExist(T entity);
}
