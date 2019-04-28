package com.microcore.jcf.service.base;

import com.github.pagehelper.PageInfo;
import com.microcore.jcf.pojo.dto.base.PageParams;
import com.microcore.jcf.pojo.dto.base.QueryParams;

import java.util.List;

/**
 * 可变的查询接口服务，针对page、list、export方法
 *
 * @author leizhenyang
 */
public interface IVariableQueryService
{
    /**
     * 分页查询
     *
     * @param params 分页查询参数
     * @return 分页查询结果集
     */
    <Result> PageInfo<Result> page(PageParams params);

    /**
     * 不分页的Search查询条件查询
     *
     * @param params
     * @return
     */
    <Result> List<Result> list(QueryParams params);

}
