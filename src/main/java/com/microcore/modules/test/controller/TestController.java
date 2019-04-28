package com.microcore.modules.test.controller;

import com.github.pagehelper.PageInfo;
import com.microcore.jcf.base.BaseController;
import com.microcore.modules.test.controller.params.TestPageParams;
import com.microcore.modules.test.entity.Test;
import com.microcore.modules.test.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * GET  一般用于查询
 * POST 保存
 * PUT  修改
 * DELETE 删除
 *
 * @author leizhenyang
 * @date 2018/5/23
 */
@RestController
@RequestMapping("/test")
public class TestController extends BaseController {

    @Autowired
    private TestService service;

    @GetMapping("/page")
    public PageInfo<Test> page(TestPageParams pageParams) {
        return service.page(pageParams);
    }

    @GetMapping("/{id}")
    public Test page(@PathVariable Integer id) {
        Test test = new Test();
        test.setId(id);
        return service.findOne(test);
    }

    @PostMapping
    public boolean save(@RequestBody Test test) {
        return service.saveOnly(test);
    }

    @PutMapping
    public boolean update(Test test) {
        return service.modifyOnly(test);
    }

    @DeleteMapping
    public boolean delete(Test test) {
        return service.delete(test);
    }

}