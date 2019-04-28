package com.microcore.modules.wiki.controller;

import com.microcore.common.utils.R;
import com.microcore.modules.wiki.controller.params.LogParam;
import com.microcore.modules.wiki.entity.LogEntity;
import com.microcore.modules.wiki.service.LogManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author JJJ
 * @Description:
 * @Date:Create in  2018/12/13-16:32
 */

@Api(value = "日志管理功能")
@RestController
public class LogManagerController {

    @Autowired
    private LogManagerService service;

    @ApiOperation(value = "查看日志详细信息",notes = "POST接收日志ID")
    @ApiImplicitParam(value = "包含日志ID的Log对象",name = "param",dataType = "LogEntity",required = true)
    @PostMapping(value = "30001")
    public R findOne(@RequestBody LogEntity param) {
        return R.ok().put("data", service.findByKey(param));
    }

    @ApiOperation(value = "查看今天的日志信息", notes = "POST接收分页对象，Log对象")
    @ApiImplicitParam(value = "分页的pageSize 、pageNum，查询条件组成的Log对象", name = "param", dataType = "LogParam", required = true)
    @PostMapping(value = "30002")
    public R findToday(@RequestBody LogParam param) {
        //return service.findPage(param);
        return R.ok().put("data", service.findToday(param));
    }

    @ApiOperation(value = "查看近一周的日志信息", notes = "POST接收分页对象，Log对象")
    @ApiImplicitParam(value = "分页的pageSize 、pageNum，查询条件组成的Log对象", name = "param", dataType = "LogParam", required = true)
    @PostMapping(value = "30003")
    public R findWeek(@RequestBody LogParam param) {
        //return service.findPage(param);
        return R.ok().put("data", service.findWeek(param));
    }

    @ApiOperation(value = "查看近一月的日志信息", notes = "POST接收分页对象，Log对象")
    @ApiImplicitParam(value = "分页的pageSize 、pageNum，查询条件组成的Log对象", name = "param", dataType = "LogParam", required = true)
    @PostMapping(value = "30004")
    public R findMonth(@RequestBody LogParam param) {
        //return service.findPage(param);
        return R.ok().put("data", service.findMonth(param));
    }
}
