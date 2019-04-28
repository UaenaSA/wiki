package com.microcore.jcf.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.microcore.jcf.pojo.dto.base.PageParams;
import com.microcore.jcf.pojo.dto.base.QueryParams;
import com.microcore.jcf.service.base.BaseService;
import com.microcore.jcf.service.base.IVariableQueryService;

import java.util.List;

/**
 * DESC:抽象的可变查询模板Service
 *
 * @author leizhenyang
 * @date 2018/5/25
 */
public abstract class VariableQueryTemplateService extends BaseService implements IVariableQueryService {

    /**
     * 获取模块名称，用于注入日志来源，强制实现类必须填写模块名称
     *
     * @return
     */
    protected abstract String getModuleName();

    @Override
    public <Result> List<Result> list(QueryParams params) {
        return findPageOrListExecute(params);
    }

    /**
     * 核心的分页方法，大多数情况下用户前端分页查询显示数据
     *
     * @param params   分页查询参数
     * @param <Result>
     * @return
     */
    @Override
    public <Result> PageInfo<Result> page(PageParams params) {
        //模板方法
        List<Result> list = findPage(params);
        PageInfo pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return 结果集
     */
    private List findPage(PageParams params) {
        resetPage(params);
        return findPageOrListExecute(params);
    }

    protected void resetPage(PageParams params) {
        PageHelper.clearPage();
        PageHelper.startPage(params.getPageNumber(), params.getPageSize());
    }

    /**
     * 条件查询
     * <strong>当需要调用自己手写的SQL的时候覆盖此方法</strong>
     *
     * @param params 查询条件
     * @return 结果集
     */
    protected abstract List findPageOrListExecute(QueryParams params);

}
