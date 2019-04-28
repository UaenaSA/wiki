package com.microcore.common.exception;

import com.microcore.common.utils.HttpServletUtil;
import com.microcore.jcf.exception.Error;
import com.microcore.jcf.exception.ServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 异常统一处理类，项目中的异常均需要往上抛到此处进行处理
 */
@ControllerAdvice
public class GlobalExceptionController
{
    /**
     * 异常处理
     * @param request
     * @param response
     * @param e
     * @return
     * @throws Exception
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Error exceptionHandle(HttpServletRequest request, HttpServletResponse response, Exception e) throws Exception
    {
        e.printStackTrace();
        if (HttpServletUtil.isAjaxRequest(request))
        {
            if (e instanceof ServiceException)
            {
                ServiceException exception = (ServiceException) e;
                return exception.getError();
            }
        }
        throw e;
    }
}
