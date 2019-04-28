package com.microcore.modules.wiki.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author JJJ
 * @Description: 抽象类， 做分页查询
 * @Date:Create in  2019/1/3-16:00
 */

public abstract class BaseService<T> implements IBaseService<T> {

    //如果是带条件的分页查询， 子类需要给pageParam赋值
    protected Object pageParam;


    /**
     * 默认返回null，即： 不加入条件的分页查询
     *
     * @param <></>
     * @return
     */
    private Object pageParam() {
        return pageParam;
    }

    /**
     * 清空分页条件
     *
     * @param
     */
    private void afterParam() {
        pageParam = null;
    }


    public PageInfo<T> findPage(PageInfo<T> pageInfo) {
        PageHelper.clearPage();
        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        Object t = this.pageParam();
        List<T> r;
        if (t == null) {
            r = findAll();
        } else if (t instanceof Example) {
            r = getMapper().selectByExample(t);
        } else {
            r = findByEntity((T) t);
        }
        afterParam();
        return new PageInfo<>(r);
    }
}

