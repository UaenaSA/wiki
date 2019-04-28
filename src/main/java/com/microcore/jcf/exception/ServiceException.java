/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.microcore.jcf.exception;


/**
 * Service层公用的Exception
 *
 * @author leizhenyang
 */
public class ServiceException extends RuntimeException {
    private Error error;

    public ServiceException(Error error) {
        this.error = error;
    }

    public ServiceException(Error.Status status, Object result) {
        this.error = new Error(status, result);
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }
}
