package com.microcore.jcf.service.base;

import com.microcore.jcf.entity.BusinessLogger;
import com.microcore.jcf.service.SimpleTemplateService;

/**
 * 日志基类
 *
 * @author leizhenyang
 */
public abstract class BaseLoggerService<T extends BusinessLogger> extends SimpleTemplateService<T>
{

    /**
     * 注入客户端IP
     *
     * @param entity 实体
     */
    @Override
    protected void saveBefore(BusinessLogger entity)
    {
        //Object ip = ContextHolderUtil.getRequestAttributes("IP");
        //if (ip == null)
        //{
        //    entity.setIpAddress("127.0.0.1");
        //}
        //else
        //{
        //    entity.setIpAddress(ip.toString());
        //}
    }
}
