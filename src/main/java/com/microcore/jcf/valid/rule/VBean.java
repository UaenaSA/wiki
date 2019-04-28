package com.microcore.jcf.valid.rule;


import com.microcore.jcf.valid.rule.base.AbstractRule;
import com.microcore.jcf.valid.rule.base.IRuleBuilder;
import com.microcore.jcf.valid.validate.MCValidator;
import com.microcore.jcf.valid.validate.base.IValidateAcceptor;
import com.microcore.jcf.valid.validate.util.ValidUtil;

import java.util.Collection;
import java.util.Map;

/**
 * 实体类校验
 *
 * @author leizhenyang
 */
public class VBean<T> extends AbstractRule<T> {
    private Class<? extends IRuleBuilder> builderClass;

    /**
     * 实体类校验
     *
     * @param builderClass
     */
    public VBean(Class<? extends IRuleBuilder> builderClass) {
        this.builderClass = builderClass;
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
        if (ValidUtil.isNull(value)) {
            return true;
        }
        if (value instanceof Map || value instanceof Collection) {
            message = "不支持的校验类型，当前参数类型：" + value.getClass();
            return false;
        }
        MCValidator mcValidator = MCValidator.getValidator();
        IRuleBuilder builder;
        builder = builderClass.newInstance();
        builder.build(value);
        mcValidator.setBuilder(builder);
        IValidateAcceptor validate = mcValidator.validateForBean(value);
        if (validate == null || validate.isEmpty()) {
            return true;
        }
        message = validate;
        return false;
    }

    /**
     * 获取构建器class
     *
     * @return
     */
    public Class<? extends IRuleBuilder> getBuilderClass() {
        return builderClass;
    }

    /**
     * 设置构建器class
     *
     * @param builderClass
     */
    public void setBuilderClass(Class<? extends IRuleBuilder> builderClass) {
        this.builderClass = builderClass;
    }

}
