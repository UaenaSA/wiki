package com.microcore.jcf.valid.validate.annotate;


import com.microcore.jcf.valid.rule.base.IRuleBuilder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author leizhenyang
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface RuleBuilder
{
    Class<? extends IRuleBuilder> builder();
}