package com.microcore.jcf.valid.rule.bean;

/**
 * 基本的元祖，灵活包裹数据类型<br>
 * 借鉴C#
 *
 * @param <A> 第一个泛型参数
 * @param <B> 第二个泛型参数
 * @author Liu.Chunfu
 */
public class Tuple<A, B>
{
    /**
     * 第一个值
     */
    public final A Item1;

    /**
     * 第二个值
     */
    public final B Item2;

    /**
     * @param a
     * @param b
     */
    public Tuple(A a, B b)
    {
        this.Item1 = a;
        this.Item2 = b;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof Tuple))
        {
            return false;
        }

        Tuple<?, ?> tuple2 = (Tuple<?, ?>) o;

        if (Item1 != null ? !Item1.equals(tuple2.Item1) : tuple2.Item1 != null)
        {
            return false;
        }
        return Item2 != null ? Item2.equals(tuple2.Item2) : tuple2.Item2 == null;
    }

    @Override
    public int hashCode()
    {
        int result = Item1 != null ? Item1.hashCode() : 0;
        result = 31 * result + (Item2 != null ? Item2.hashCode() : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return "Tuple2{" +
            "Item1=" + Item1 +
            ", Item2=" + Item2 +
            '}';
    }

    /**
     * 通过of此种静态方法，构造元祖。
     *
     * @param a
     * @param b
     * @param <A>
     * @param <B>
     * @return
     */
    public static <A, B> Tuple<A, B> of(A a, B b)
    {
        return new Tuple<>(a, b);
    }
}
