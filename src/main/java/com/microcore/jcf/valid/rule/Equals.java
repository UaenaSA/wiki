package com.microcore.jcf.valid.rule;


import com.microcore.jcf.exception.ServiceExceptionUtil;
import com.microcore.jcf.valid.rule.base.AbstractRule;

/**
 * equals验证
 *
 * @author leizhenyang
 */
public class Equals<T> extends AbstractRule<T> {

    private T temp;

    /**
     * equals
     *
     * @param temp
     */
    public Equals(T temp) {
        if (temp == null) {
            ServiceExceptionUtil.getErrorServiceException("被比较的值不可为空");
        }
        this.temp = temp;
    }

    /**
     * 验证
     *
     * @param value
     * @return
     * @throws Exception
     */
    @Override
    public boolean valid(T value) throws Exception {
        if (value == null) {
            return true;
        }
        if (!temp.equals(value)) {
            message = "必须为" + temp;
            return false;
        }
        return true;
    }
}
