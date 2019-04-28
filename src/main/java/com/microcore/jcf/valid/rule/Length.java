package com.microcore.jcf.valid.rule;


import com.microcore.jcf.valid.rule.base.AbstractRule;
import com.microcore.jcf.valid.rule.support.BoundaryValueSupport;
import com.microcore.jcf.valid.validate.util.ValidUtil;

/**
 * 字符串长度校验
 *
 * @author leizhenyang
 */
public class Length extends AbstractRule<String> {

    private BoundaryValueSupport boundaryValueSupport;

    public Length(long maxLength) {
        boundaryValueSupport = new BoundaryValueSupport(maxLength);
        boundaryValueSupport.setMin(0);
    }

    public Length(long minLength, long maxLength) {
        boundaryValueSupport = new BoundaryValueSupport(maxLength);
        boundaryValueSupport.ltZero(minLength);
    }

    @Override
    public boolean valid(String value) {
        if (ValidUtil.isBlank(value)) {
            return true;
        }
        //MoreStringUtil.utf8EncodedLength(value)
        if (!ValidUtil.between(boundaryValueSupport.getMin(), boundaryValueSupport.getMax(), value.length())) {
            message = "长度必须在[" + boundaryValueSupport.getMin() + "," + boundaryValueSupport.getMax() + "]之间";
            return false;
        }
        return true;
    }
}