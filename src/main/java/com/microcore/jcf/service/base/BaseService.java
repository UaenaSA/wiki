package com.microcore.jcf.service.base;


import com.microcore.jcf.exception.ServiceException;
import com.microcore.jcf.exception.ServiceExceptionUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 提供Service基本所需的支持<br>
 * 如验证支持、国际化支持
 *
 * @author leizhenyang
 */
public class BaseService {

    /**
     * 日期格式化
     */
    public static final SimpleDateFormat FORMAT = new SimpleDateFormat("YYMMddHHmmssSSS");

    /**
     * 获取时间戳，可作为各类标示使用
     *
     * @return
     */
    public static Long getTimestamp() {
        String format = BaseService.FORMAT.format(new Date());
        return Long.parseLong(format);
    }

    /**
     * 抛出错误异常
     *
     * @param e 异常
     * @throws ServiceException 抛出服务异常
     */
    protected void throwError(Exception e) throws ServiceException {
        throw ServiceExceptionUtil.getErrorServiceException(e);
    }

    /**
     * 抛出错误异常
     *
     * @param e 异常描述
     * @throws ServiceException 抛出服务异常
     */
    protected void throwError(String e) throws ServiceException {
        throw ServiceExceptionUtil.getErrorServiceException(e);
    }

    /**
     * 抛出验证异常
     *
     * @param e
     * @throws ServiceException
     */
    protected void throwValidFail(String e) throws ServiceException {
        throw ServiceExceptionUtil.getValidFailServiceException(e);
    }
}
