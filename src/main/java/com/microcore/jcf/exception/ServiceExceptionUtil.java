package com.microcore.jcf.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author leizhenyang
 */
public class ServiceExceptionUtil
{
    private static final Logger log = LoggerFactory.getLogger(ServiceExceptionUtil.class);

    /**
     * 转换服务异常
     *
     * @param exp 异常
     * @return 服务异常
     */
    public static ServiceException getErrorServiceException(Exception exp)
    {
        return getErrorServiceException(exp.getMessage());
    }

    /**
     * 转换服务异常
     *
     * @param exp 异常描述
     * @return 返回服务异常
     */
    public static ServiceException getErrorServiceException(Object exp)
    {
        Error error = new Error();
        error.setResult(exp);
        error.setStatus(Error.Status.ERROR);
        log.error("{}ERROR:{}", System.lineSeparator(), error.toString());
        throw new ServiceException(error);
    }

    /**
     * @param exp
     * @return
     */
    public static ServiceException getValidFailServiceException(Object exp)
    {
        Error error = new Error();
        error.setResult(exp);
        error.setStatus(Error.Status.VALID_FAIL);
        log.error("{}ERROR:{}", System.lineSeparator(), error.toString());
        throw new ServiceException(error);
    }


    /**
     * 登录超时
     * @return
     */
    public static ServiceException getLoginTimeoutServiceException() {
        Error error = new Error();
        error.setResult("");
        error.setStatus(Error.Status.LOGIN_TIMEOUT);
        throw new ServiceException(error);
    }
}
