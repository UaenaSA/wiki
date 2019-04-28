package com.microcore.jcf.valid.validate;


import com.microcore.jcf.valid.rule.*;
import com.microcore.jcf.valid.rule.base.IRuleBuilder;
import com.microcore.jcf.valid.rule.base.IValid;

import java.util.Date;
import java.util.Set;

/**
 * 校验规则
 *
 * @author leizhenyang
 */
public class Rules
{

    /**
     * 相等
     *
     * @param value
     * @param <T>
     * @return
     */
    public static <T> Equals eq(T value)
    {
        return new Equals(value);
    }

    /**
     * 包含在Set集合中
     *
     * @param values
     * @return
     */
    public static Contains contains(Set values)
    {
        return new Contains(values);
    }

    /**
     * 字符串blank判断
     *
     * @return
     */
    public static NotBlank notBlank()
    {
        return new NotBlank();
    }

    /**
     * 字符串empty判断
     *
     * @return
     */
    public static NotEmpty notEmpty()
    {
        return new NotEmpty();
    }

    /**
     * 非空校验
     *
     * @return
     */
    public static NotNull notNull()
    {
        return new NotNull();
    }

    /**
     * 范围校验
     *
     * @param max 最大值
     * @return
     */
    public static Range range(Number max)
    {
        return new Range(max);
    }

    /**
     * 范围校验
     *
     * @param min 最小值
     * @param max 最大值
     * @return
     */
    public static Range range(Number min, Number max)
    {
        return new Range(min, max);
    }

    /**
     * 字符串长度校验
     *
     * @param maxLength 字节最大长度(byte length)
     * @return
     */
    public static Length length(long maxLength)
    {
        return new Length(maxLength);
    }

    /**
     * 字符串长度校验
     *
     * @param minLength 最小长度
     * @param maxLength 最大长度
     * @return
     */
    public static Length length(long minLength, long maxLength)
    {
        return new Length(minLength, maxLength);
    }

    /**
     * 正则表达式校验
     *
     * @param regex
     * @return
     */
    public static Regex regex(String regex)
    {
        return new Regex(regex);
    }

    /**
     * 邮箱校验
     *
     * @return
     */
    public static Email email()
    {
        return new Email();
    }

    /**
     * 实体类校验
     *
     * @param builderClass
     * @return
     */
    public static VBean bean(Class<? extends IRuleBuilder> builderClass)
    {
        return new VBean(builderClass);
    }

    /**
     * list校验
     *
     * @param valids 泛型校验器集合
     * @return
     */
    public static VList list(IValid... valids)
    {
        return new VList(valids);
    }

    /**
     * list校验
     *
     * @param minSize 最小数量
     * @param valids  泛型校验器集合
     * @return
     */
    public static VList list(long minSize, IValid... valids)
    {
        return new VList(minSize, valids);
    }

    /**
     * list校验
     *
     * @param minSize 最小数量
     * @param maxSize 最大数量
     * @param valids  泛型校验器集合
     * @return
     */
    public static VList list(long minSize, long maxSize, IValid... valids)
    {
        return new VList(minSize, maxSize, valids);
    }

    /**
     * IP V4校验
     *
     * @return
     */
    public static IPV4 ipv4()
    {
        return new IPV4();
    }

    /**
     * IP V6校验
     *
     * @return
     */
    public static IPV6 ipv6()
    {
        return new IPV6();
    }

    /**
     * 密码校验(暂未实现)
     *
     * @return
     */
    public static Password password()
    {
        return new Password();
    }

    /**
     * 路径校验(暂未实现)
     *
     * @return
     */
    public static Path path()
    {
        return new Path();
    }

    /**
     * 端口号
     *
     * @return
     */
    public static Range port()
    {
        return new Range(0, 65535);
    }

    /**
     * 经度
     *
     * @return
     */
    public static Range longitude()
    {
        return new Range(-180, 180);
    }

    /**
     * 纬度
     *
     * @return
     */
    public static Range latitude()
    {
        return new Range(-90, 90);
    }

    /**
     * 比较日期大于目标值
     *
     * @param target 目标时间
     * @param desc   目标描述
     * @return
     */
    public static DateTime compareDateTimeGT(Date target, String desc)
    {
        return new DateTime(DateTime.Compare.GT, target, desc, false);
    }

    /**
     * 比较日期大于目标值并且间隔不超过n天
     *
     * @param target 目标时间
     * @param desc   目标描述
     * @param days   间隔天数
     * @return
     */
    public static DateTime compareDateTimeGTIntervalByDay(Date target, String desc, long days)
    {
        return new DateTime(DateTime.Compare.GT, target, desc, false, DateTime.IntervalUnitUtil.newInterval(DateTime.IntervalUnit.Day, days));
    }

    /**
     * 比较日期大于等于目标值
     *
     * @param target 目标时间
     * @param desc   目标描述
     * @return
     */
    public static DateTime compareDateTimeGTorEQ(Date target, String desc)
    {
        return new DateTime(DateTime.Compare.GT, target, desc, true);
    }

    /**
     * 比较日期大于等于目标值并且间隔不超过n天
     *
     * @param target 目标时间
     * @param desc   目标描述
     * @param days   间隔天数
     * @return
     */
    public static DateTime compareDateTimeGTorEQIntervalByDay(Date target, String desc, long days)
    {
        return new DateTime(DateTime.Compare.GT, target, desc, true, DateTime.IntervalUnitUtil.newInterval(DateTime.IntervalUnit.Day, days));
    }

    /**
     * 比较日期小于目标值
     *
     * @param target 目标时间
     * @param desc   目标描述
     * @return
     */
    public static DateTime compareDateTimeLT(Date target, String desc)
    {
        return new DateTime(DateTime.Compare.LT, target, desc, false);
    }

    /**
     * 比较日期小于目标值并且间隔不超过n天
     *
     * @param target 目标时间
     * @param desc   目标描述
     * @return
     */
    public static DateTime compareDateTimeLTIntervalByDay(Date target, String desc, long days)
    {
        return new DateTime(DateTime.Compare.LT, target, desc, false, DateTime.IntervalUnitUtil.newInterval(DateTime.IntervalUnit.Day, days));
    }

    /**
     * 比较日期小于等于目标值
     *
     * @param target 目标时间
     * @param desc   目标描述
     * @return
     */
    public static DateTime compareDateTimeLTorEQ(Date target, String desc)
    {
        return new DateTime(DateTime.Compare.LT, target, desc, true);
    }

    /**
     * 比较日期小于等于目标值并且间隔不超过n天
     *
     * @param target 目标时间
     * @param desc   目标描述
     * @return
     */
    public static DateTime compareDateTimeLTorEQIntervalByDay(Date target, String desc, long days)
    {
        return new DateTime(DateTime.Compare.LT, target, desc, true, DateTime.IntervalUnitUtil.newInterval(DateTime.IntervalUnit.Day, days));
    }

}
