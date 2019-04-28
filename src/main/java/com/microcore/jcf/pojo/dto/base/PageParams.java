package com.microcore.jcf.pojo.dto.base;

/**
 * 分页的数据传输对象基类
 *
 * @author leizhenyang
 */
public class PageParams extends QueryParams {

    /**
     * 页码
     */
    private int pageNumber = 1;

    /**
     * 每页行数
     */
    private int pageSize = 10;

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public PageParams() {
    }

    /**
     * 分页参数
     *
     * @param pageNumber
     * @param pageSize
     */
    public PageParams(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    /**
     * 字段
     */
    public static class Feild {
        /**
         * 页码
         */
        public static final String PAGE_NUMBER = "pageNumber";
        /**
         * 行数
         */
        public static final String PAGE_SIZE = "pageSize";
    }
}
