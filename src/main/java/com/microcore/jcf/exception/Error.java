package com.microcore.jcf.exception;

/**
 * 业务层向CS或BS的数据传输对象，目标是所有Service方法都使用该对象返回
 *
 * @author leizhenyang
 */
public class Error
{
    /**
     * 状态枚举
     */
    public enum Status
    {
//        /**
//         * 失败
//         */
//        FAIL,
        /**
         * 验证失败
         */
        VALID_FAIL,
        /**
         * 异常
         */
        ERROR,
        /**
         * 空
         */
        NONE,

        /**
         * 登录超时
         */
        LOGIN_TIMEOUT
    }

    private Status status;
    private Object result;
    private boolean hasFrameSelfError = true;


    public boolean isHasFrameSelfError()
    {
        return hasFrameSelfError;
    }

    public Error()
    {
        this.status = Status.NONE;
    }

    public Error(Status status, Object result)
    {
        this.status = status;
        this.result = result;
    }

    public Status getStatus()
    {
        return status;
    }

    public void setStatus(Status status)
    {
        this.status = status;
    }

    public Object getResult()
    {
        return result;
    }

    public void setResult(Object result)
    {
        this.result = result;
    }

    @Override
    public String toString()
    {
        return "ErrorInfo{" +
            "status=" + status +
            ", result=" + result +
            '}';
    }
}
