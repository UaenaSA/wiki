package com.microcore.jcf.valid.rule;


import com.microcore.jcf.valid.rule.base.AbstractRule;
import com.microcore.jcf.valid.rule.bean.Tuple;

import java.util.Date;

/**
 * 时间校验
 *
 * @author leizhenyang
 */
public class DateTime extends AbstractRule<Date> {
    /**
     * 比较规则
     */
    private Compare compare;

    /**
     * 被比较的值
     */
    private Date target;

    /**
     * 起始时间
     */
    private Date start;

    /**
     * 结束时间
     */
    private Date end;

    /**
     * 是否可以相等？
     */
    private boolean isEquals;

    /**
     * 时间间隔（秒）
     */
    private Tuple<IntervalUnit, Long> interval;

    /**
     * target描述，例如“开始时间” “结束时间”
     */
    private String desc;

    /**
     * 时间间隔单位
     */
    public enum IntervalUnit {
        /**
         * 天
         */
        Day("天"),
        /**
         * 小时
         */
        Hour("小时"),
        /**
         * 分钟
         */
        Minute("分钟"),
        /**
         * 秒
         */
        Second("秒");

        private String desc;

        public String getDesc() {
            return desc;
        }

        IntervalUnit(String desc) {
            this.desc = desc;
        }

    }

    /**
     * 时间间隔单位工具
     */
    public static class IntervalUnitUtil {
        public static Tuple<IntervalUnit, Long> newInterval(IntervalUnit unit, long num) {
            return new Tuple<>(unit, num);
        }

        /**
         * 将时间根据单位转换为毫秒
         *
         * @param tuple
         * @return
         */
        public static long convert2Mill(Tuple<IntervalUnit, Long> tuple) {
            long interval = -1;
            switch (tuple.Item1) {
                case Day:
                    interval = tuple.Item2 * 24 * 60 * 60 * 1000; // * 24小时 * 60分钟 * 60秒 * 1000毫秒
                    break;
                case Hour:
                    interval = tuple.Item2 * 60 * 60 * 1000; // * 60分钟 * 60秒 * 1000毫秒
                    break;
                case Minute:
                    interval = tuple.Item2 * 60 * 1000; //  * 60秒 * 1000毫秒
                    break;
                case Second:
                    interval = tuple.Item2 * 1000; // * 1000毫秒
                    break;
                default:
                    throw new NullPointerException("");
            }
            return interval;
        }
    }

    /**
     * @param compare 比较规则
     * @param target  被比较的值
     * @param desc    target描述
     */
    public DateTime(Compare compare, Date target, String desc) {
        initCompare(compare, target, desc, false);
    }

    /**
     * @param compare  比较规则
     * @param target   被比较的值
     * @param desc     target描述
     * @param isEquals true 可以相等 false 不可相等
     */
    public DateTime(Compare compare, Date target, String desc, boolean isEquals) {
        initCompare(compare, target, desc, isEquals);
    }

    /**
     * @param compare  比较规则
     * @param target   被比较的值
     * @param desc     target描述
     * @param isEquals true 可以相等 false 不可相等
     */
    public DateTime(Compare compare, Date target, String desc, boolean isEquals, Tuple<IntervalUnit, Long> interval) {
        initCompare(compare, target, desc, isEquals);
        this.interval = interval;
    }

    /**
     * 初始化日期比较参数
     *
     * @param compare
     * @param target
     * @param desc
     * @param isEquals
     */
    private void initCompare(Compare compare, Date target, String desc, boolean isEquals) {
        this.compare = compare;
        this.target = target;
        this.desc = desc;
        this.isEquals = isEquals;
    }

    /**
     * @param start    起始时间
     * @param end      结束时间
     * @param isEquals true 可以相等 false 不可相等
     */
    public DateTime(Date start, Date end, boolean isEquals) {
        initBetween(start, end, isEquals);
    }

    private void initBetween(Date start, Date end, boolean isEquals) {
        this.compare = Compare.BETWEEN;
        this.start = start;
        this.end = end;
        this.isEquals = isEquals;
    }

    @Override
    public boolean valid(Date value) {
        int temp = 0;
        if (compare != Compare.BETWEEN) {
            temp = value.compareTo(target);
        }
        switch (compare) {
            case LT:
                if (temp >= 0 && !isEquals) {
                    message = "不能大于" + desc;
                    return false;
                } else if (temp > 0 && isEquals) {
                    message = "不能大于" + desc;
                    return false;
                }
                if (!validInterval(value, target, interval)) {
                    return false;
                }
                break;
            case GT:
                if (temp <= 0 && !isEquals) {
                    message = "不能小于" + desc;
                    return false;
                } else if (temp < 0 && isEquals) {
                    message = "不能小于" + desc;
                    return false;
                }
                if (!validInterval(target, value, interval)) {
                    return false;
                }
                break;
            case BETWEEN:
                if ((value.getTime() < start.getTime() || value.getTime() > end.getTime()) && !isEquals) {
                    message = "不在时间范围内";
                    return false;
                } else if ((value.getTime() <= start.getTime() || value.getTime() >= end.getTime()) && isEquals) {
                    message = "不在时间范围内";
                    return false;
                }
                break;
            default:
                if (temp != 0) {
                    message = "不等于" + desc;
                    return false;
                }
                break;
        }
        return true;
    }

    /**
     * 验证时间间隔
     *
     * @param min      较小的时间
     * @param max      较大的时间
     * @param interval 时间间隔
     * @return
     */
    private boolean validInterval(Date min, Date max, Tuple<IntervalUnit, Long> interval) {
        if (interval == null) {
            return true;
        }
        // 检查时间间隔
        long result = max.getTime() - min.getTime();
        if (result > IntervalUnitUtil.convert2Mill(interval)) {
            message = "时间间隔不合法，间隔为" + interval.Item2 + interval.Item1.getDesc();
            return false;
        }
        return true;
    }

    public enum Compare {
        /**
         * 大于
         */
        GT,
        /**
         * 小于
         */
        LT,
        /**
         * 等于
         */
        EQ,

        /**
         * 之间
         */
        BETWEEN
    }
}