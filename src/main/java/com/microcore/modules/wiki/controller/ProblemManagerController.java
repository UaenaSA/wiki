package com.microcore.modules.wiki.controller;

import com.microcore.common.utils.DateUtils;
import com.microcore.common.utils.R;
import com.microcore.modules.wiki.controller.params.ProblemParam;
import com.microcore.modules.wiki.entity.AnswerEntity;
import com.microcore.modules.wiki.entity.FileEntity;
import com.microcore.modules.wiki.entity.ProblemEntity;
import com.microcore.modules.wiki.service.AnswerProManagerService;
import com.microcore.modules.wiki.service.FileManagerService;
import com.microcore.modules.wiki.service.ProblemManagerService;
import com.microcore.util.io.FileUtil;
import com.microcore.util.io.PathUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Date;

/**
 * @Author JJJ
 * @Description: 问题管理controller
 * @Date:Create in  2018/12/10-14:52
 */

@Api(value = "问题管理功能")
@RestController
@CrossOrigin
public class ProblemManagerController {

    @Autowired
    private ProblemManagerService service;

    @Autowired
    private AnswerProManagerService answerProManagerService;

    @Autowired
    private FileManagerService fileManagerService;

    /**
     * 提交问题
     *
     * @return
     */
    @ApiOperation(value = "提交问题", notes = "POST接收Problem对象")
    @ApiImplicitParam(value = "问题对象", name = "entity", dataType = "ProblemEntity", required = true)
    @PostMapping(value = "20001")
    public R submitProblem(@RequestParam("file") MultipartFile file , ProblemEntity entity) {
        return R.ok().put("data", service.save(entity,file));
    }

    /**
     * 回答
     *
     * @param
     * @return
     */
    @ApiOperation(value = "回答问题", notes = "POST接收Answer对象")
    @ApiImplicitParam(value = "Answer对象", name = "answerEntity", dataType = "AnswerEntity", required = true)
    @PostMapping(value = "20002")
    public R answerProblem(@RequestBody AnswerEntity answerEntity) {
        return R.ok().put("data", answerProManagerService.save(answerEntity));
    }

    /**
     * 修改问题（主要是修改为是否解决）
     *
     * @param
     * @return
     */
    @ApiOperation(value = "修改问题", notes = "POST接收Problem对象，主要是修改为是否解决")
    @ApiImplicitParam(value = "Problem对象", name = "entity", dataType = "ProblemEntity", required = true)
    @PostMapping(value = "20003")
    public R updateProblem(@RequestBody ProblemEntity entity) {
        return R.ok().put("data", service.update(entity));
    }


    /**
     * 查询问题
     *
     * @param
     * @return
     */
    @ApiOperation(value = "查询问题", notes = "POST接收ProblemParam对象")
    @ApiImplicitParam(value = "ProblemParam对象，包含分页信息与查询条件", name = "problemParam", dataType = "ProblemParam", required = true)
    @PostMapping(value = "20004")
    public R findPage(@RequestBody ProblemParam problemParam) {
        return R.ok().put("data", service.findProblems(problemParam));
    }

    /**
     * 查询单个问题
     *
     * @param
     * @return
     */
    @ApiOperation(value = "查询单个问题", notes = "POST接收Problem对象")
    @ApiImplicitParam(value = "Problem对象，包含id字段", name = "entity", dataType = "ProblemEntity", required = true)
    @PostMapping(value = "20005")
    public R findOne(@RequestBody ProblemEntity entity) {
        return R.ok().put("data", service.findOne(entity));
    }


    /**
     * 查询待处理问题
     *
     * @param
     * @return
     */
    @ApiOperation(value = "查询待处理问题", notes = "POST接收ProblemParam对象")
    @ApiImplicitParam(value = "ProblemParam对象，包含分页信息与查询条件（待处理条件要添加进去）", name = "problemParam", dataType = "ProblemParam", required = true)
    @PostMapping(value = "20006")
    public R findUnHandPage(@RequestBody ProblemParam problemParam) {
        return R.ok().put("data", service.findToDo(problemParam));
    }

    /**
     * 查询我的问题
     *
     * @param
     * @return
     */
    @ApiOperation(value = "查询问题", notes = "POST接收ProblemParam对象")
    @ApiImplicitParam(value = "ProblemParam对象，包含分页信息与查询条件（author:'XX'条件要添加进去）", name = "problemParam", dataType = "ProblemParam", required = true)
    @PostMapping(value = "20007")
    public R findMyPro(@RequestBody ProblemParam problemParam) {
        return R.ok().put("data", service.findMyPro(problemParam));
    }

    @ApiOperation(value = "获取专家列表")
    @PostMapping(value = "20008")
    public R getExperts(){
        return R.ok().put("data",service.getExperts());
    }

    @ApiOperation(value = "获取问题（最近一周，所有人的）")
    @PostMapping(value = "20009")
    public R findMyProWeek(@RequestBody ProblemParam problemParam){
        return R.ok().put("data", service.findMyProWeek(problemParam));
    }

    @ApiOperation(value = "获取问题（已解决，所有人的）")
    @PostMapping(value = "20010")
    public R findMyProSolved(@RequestBody ProblemParam problemParam){
        return R.ok().put("data", service.findMyProSolved(problemParam));
    }


    @ApiOperation(value = "文件下载")
    @RequestMapping(value = "20011", method = RequestMethod.GET)
    public void downloadFile(HttpServletRequest req, HttpServletResponse resp, @RequestParam String id) {
        FileEntity fileEntity = fileManagerService.findone(id);
        //真实文件名
        String name = fileEntity.getPathName();
        String downloadName=fileEntity.getOriginName();
        downloadName += name.substring(name.lastIndexOf("."));
//        进行转码后的文件名，用来下载之后的文件名
        service.download(resp,name,downloadName);
    }

    @ApiOperation(value = "导出Excel")
    @PostMapping(value = "20012")
    public void exportExcel(HttpServletResponse resp,@RequestBody ProblemEntity entity) {
        service.exportExcel(resp,entity);
    }

}
