package com.microcore.jcf.service;

import tk.mybatis.mapper.common.Mapper;

/**
 * 批量执行接口
 *
 * @author LiuChunfu
 * @date 2017-08-08
 */
public interface IBatchExecute
{
    void execute(Mapper mapper);
}
