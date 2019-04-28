package com.microcore.common.vo;

/**
 * 执行算法参数对象
 *
 * @author LiuChunfu
 * @date 2017/11/3
 */
public class ExecParamsVO {

    /**
     * 标识
     */
    private String bs;
    /**
     * 算法索引
     */
    private int i;
    /**
     * 标题
     */
    private String bt;
    /**
     * 正文文本
     */
    private String zwwb;

    /**
     * 相似度最大值
     */
    private double similarityMax = 1;
    /**
     * 相似度最小值
     */
    private double similarityMin;

    /**
     * 用户标识
     */
    private String yhbs;


    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public String getZwwb() {
        return zwwb;
    }

    public void setZwwb(String zwwb) {
        this.zwwb = zwwb;
    }

    public String getBt() {
        return bt;
    }

    public void setBt(String bt) {
        this.bt = bt;
    }

    public String getBs() {
        return bs;
    }

    public void setBs(String bs) {
        this.bs = bs;
    }


    public double getSimilarityMax() {
        return similarityMax;
    }

    public void setSimilarityMax(double similarityMax) {
        this.similarityMax = similarityMax;
    }

    public double getSimilarityMin() {
        return similarityMin;
    }

    public void setSimilarityMin(double similarityMin) {
        this.similarityMin = similarityMin;
    }

    public String getYhbs() {
        return yhbs;
    }

    public void setYhbs(String yhbs) {
        this.yhbs = yhbs;
    }
}
