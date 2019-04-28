package com.microcore.modules.test.controller.params;

import com.microcore.jcf.pojo.dto.base.PageParams;

/**
 * Test分页查询参数
 *
 * @author leizhenyang
 * @date 2018/5/23
 */
public class TestPageParams extends PageParams{

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
