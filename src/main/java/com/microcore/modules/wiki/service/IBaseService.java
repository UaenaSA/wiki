package com.microcore.modules.wiki.service;

import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author JJJ
 * @Description: 暂时定一个接口，后面做必要的扩展
 * @Date:Create in  2018/12/11-15:13
 */
public interface IBaseService<T> {


    Mapper getMapper();

    /**
     * save 方法让子类重写， 主要是需要生成UUID
     *
     * @param t
     * @return
     */
    //gcy 2019.4.17 将新增方法的返回值改为bool
    //abstract T save(T t);
    abstract boolean save(T t);

    default boolean delete(T t) {
        int result = getMapper().deleteByPrimaryKey(t);
        if (result == 1) {
            return true;
        }
        return false;
    }

    default boolean update(T t) {
        int result =  getMapper().updateByPrimaryKeySelective(t);
        if (result == 1) {
            return true;
        }
        return false;
    }

    default T findByKey(T t) {
        Object o = getMapper().selectByPrimaryKey(t);
        return (T) o;
    }

    default List<T> findByEntity(T t) {
        List<T> list = getMapper().select(t);
        return list;
    }

    default List<T> findAll() {
        List<T> list = getMapper().selectAll();
        return list;
    }


}
