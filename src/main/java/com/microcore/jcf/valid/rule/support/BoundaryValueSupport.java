package com.microcore.jcf.valid.rule.support;


/**
 * 边界值属性的扩展支持<br/>
 * <b>实际含义以实现类为准<b/>
 *
 * @author leizhenyang
 */
public class BoundaryValueSupport
{

    private long min = 0;

    private long max = Long.MAX_VALUE;

    public BoundaryValueSupport()
    {

    }

    public BoundaryValueSupport(long max)
    {
        this.max = max;
        gtMax(max);
    }

    public BoundaryValueSupport(long min, long max)
    {
        this.min = min;
        this.max = max;
        ltMin(min);
        gtMax(max);
    }

    /**
     * > max 强制设置this.max = Long.MAX_VALUE;
     *
     * @param max
     */
    public void gtMax(long max)
    {
        if (max >= Long.MAX_VALUE)
        {
            this.max = Long.MAX_VALUE;
        }
        this.max = max;
    }

    /**
     * < min 强制设置this.min = Long.MIN_VALUE;
     *
     * @param min
     */
    public void ltMin(long min)
    {
        if (min <= Long.MIN_VALUE)
        {
            this.min = Long.MIN_VALUE;
        }
        this.min = min;
    }

    /**
     * min < 0 强制设置this.min = 0;
     *
     * @param min
     */
    public void ltZero(long min)
    {
        if (min < 0)
        {
            this.min = 0;
        }
        this.min = min;
    }

    public long getMin()
    {
        return min;
    }

    public void setMin(long min)
    {
        this.min = min;
    }

    public long getMax()
    {
        return max;
    }

    public void setMax(long max)
    {
        this.max = max;
    }

}
