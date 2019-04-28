package com.microcore.modules.wiki.service;

import com.github.pagehelper.PageInfo;
import com.microcore.common.utils.DateUtils;
import com.microcore.modules.sys.entity.SysUserEntity;
import com.microcore.modules.sys.service.SysUserService;
import com.microcore.modules.wiki.controller.params.ProblemParam;
import com.microcore.modules.wiki.entity.*;
import com.microcore.modules.wiki.mapper.ProblemEntityMapper;
import com.microcore.modules.wiki.util.ExcelUtil;
import com.microcore.util.io.FileUtil;
import com.xiaoleilu.hutool.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * @Author JJJ
 * @Description:
 * @Date:Create in  2018/12/14-11:06
 */
@Service
public class ProblemManagerService extends BaseService<ProblemEntity> {


    @Autowired
    private AnswerProManagerService answerProManagerService;

    @Autowired
    private LogManagerService logManagerService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private FileManagerService fileManagerService;

    @Autowired
    private AssignManagerService assignManagerService;

    @Autowired
    private ProblemEntityMapper mapper;

    @Autowired
    private LogManagerService log;

    @Override
    public Mapper getMapper() {
        return mapper;
    }

    @Override
    public boolean save(ProblemEntity problemEntity) {
        return false;
    }


    /**
     * 保存问题，修改固定参数
     *
     * @param problemEntity
     * @return
     */
    //gcy 2019.4.17 根据新增操作是否成功返回boolean变量  begin
   /* @Override
    public ProblemEntity save(ProblemEntity problemEntity) {
        problemEntity.setId(UUID.randomUUID().toString());
        problemEntity.setSubtimeTime(new Date());
        problemEntity.setStatus(0);
        int result = mapper.insertSelective(problemEntity);
        log.insert("新增问题:"+problemEntity.toString(), LogEntity.LOG_TYPE.INFO);
        return problemEntity;
    }*/
    public boolean save(ProblemEntity problemEntity,MultipartFile file) {
        problemEntity.setId(UUID.randomUUID().toString());
        problemEntity.setSubmitTime(new Date());
        problemEntity.setStatus(0);
        if (file != null) {
            problemEntity.setHaveAppend(1);
        }
        int result = mapper.insertSelective(problemEntity);
        if (problemEntity.getAssignExperts() != null&& "" != problemEntity.getAssignExperts().trim()) {
            String[] experts = problemEntity.getAssignExperts().split(",");
            AssignEntity assignEntity = new AssignEntity();
            assignEntity.setProblemId(problemEntity.getId());
            for (int i = 0; i<experts.length; i++) {
                if (experts[i] != "") {
                    assignEntity.setUserId(Long.parseLong(experts[i]));
                    assignManagerService.save(assignEntity);
                }
            }
        }
        if (result == 1) {
            log.insert(problemEntity.getId(), problemEntity.getTitle(), LogEntity.LOG_TYPE.SAVE, problemEntity.getStatus(),problemEntity.getAuthor());
            if (file != null) {
                try {
                    uploadFile(file,problemEntity.getId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return true;
        }
        return false;
    }
    //gcy 2019.4.17 根据新增操作是否成功返回boolean变量  end

    /**
     * 修改问题
     *
     * @param problemEntity
     * @return
     */
    @Override
    public boolean update(ProblemEntity problemEntity) {
        problemEntity.setLastModifyTime(new Date());
        int result = mapper.updateByPrimaryKeySelective(problemEntity);
        if (result == 1) {
            if (problemEntity.getStatus() > 1) {
                assignManagerService.deleteByProId(problemEntity.getId());
            }
            //修改日志中该问题的状态
//            LogEntity logEntity = new LogEntity();
//            logEntity.setProblemId(problemEntity.getId());
//            logEntity.setProblemStatus( problemEntity.getStatus());
//            logManagerService.update(logEntity);
            log.insert(problemEntity.getId(), problemEntity.getTitle(), LogEntity.LOG_TYPE.UPDATE, problemEntity.getStatus(),problemEntity.getAuthor());
            return true;
        }
        return false;
    }


    /**
     * 查询待处理问题（指派给我的问题）
     * @param param
     * @return
     */
    public PageInfo<ProblemEntity> findToDo(ProblemParam param){
        PageInfo<ProblemEntity> pageInfo = param.getPageInfo();
        Example example = new Example(ProblemEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id",assignManagerService.getProIds(param));
        pageParam = example;
        pageInfo = super.findPage(param.getPageInfo());
        return pageInfo;
    }

    /**
     * 查询单个问题，以及它的回答
     *
     * @param entity
     * @return
     */
    public ProblemEntity findOne(ProblemEntity entity) {
        entity = findByKey(entity);
        if (entity.getAssignExperts() != null) {
            String[] experts = entity.getAssignExperts().split(",");
            List<SysUserEntity> sysUserEntities = new ArrayList<SysUserEntity>();
            for (int i = 0; i<experts.length; i++) {
                if (experts[i] != "") {
                    sysUserEntities.add(sysUserService.queryObject(Long.valueOf(experts[i])));
                }
            }
            entity.setSysUserEntities(sysUserEntities);
        }
        List<AnswerEntity> byEntity = answerProManagerService.findAnswerByProId(entity.getId());
        entity.setAnswerEntityList(byEntity);
        //log.insert("查询问题", LogEntity.LOG_TYPE.INFO);
        return entity;
    }

    /**
     * 根据查询条件 进行模糊查询
     *
     * @param param
     * @return
     */
    public PageInfo<ProblemEntity> findProblems(ProblemParam param) {
        Example example = new Example(ProblemEntity.class);
        Example.Criteria cri = example.createCriteria();
        ProblemEntity entity = param.getProblemEntity();
        if (entity != null) {
            if (entity.getModule() != null) {
                cri.andEqualTo("module", entity.getModule());
            }
            if (entity.getStatus() != null) {
                cri.andEqualTo("status", entity.getStatus());
            }
            if (entity.getTitle() != null) {
                cri.andLike("title", "%" + entity.getTitle() + "%");
            }
            if (entity.getType() != null) {
                cri.andEqualTo("type", entity.getType());
            }
            if (param.getSubmitTimeStart() != null) {
                cri.andGreaterThan("submitTime", param.getSubmitTimeStart());
            }
            if (param.getSubmitTimeEnd() != null) {
                cri.andLessThan("submitTime", param.getSubmitTimeEnd());
            }
            if (param.getLastModifyTimeStart() != null) {
                cri.andGreaterThan("lastModifyTime", param.getLastModifyTimeStart());
            }
            if (param.getLastModifyTimeEnd() != null) {
                cri.andLessThan("lastModifyTime", param.getLastModifyTimeEnd());
            }
        }
        example.orderBy("submitTime").desc();
        pageParam = example;
        PageInfo<ProblemEntity> page = super.findPage(param.getPageInfo());
        //log.insert("查询问题", LogEntity.LOG_TYPE.INFO);
        return page;
    }

    /**
     *  查询我的问题
     *
     * @param param
     * @return
     */
    public PageInfo<ProblemEntity> findMyPro(ProblemParam param){
        //先设置分页查询参数
        String  authorId = param.getProblemEntity().getAuthorId();
        Example example = new Example(ProblemEntity.class);
        Example.Criteria criteria1 = example.createCriteria();
        criteria1.andEqualTo("authorId",authorId);
        List<String> problemIds = answerProManagerService.findmyanwser(param);
        if (!problemIds.isEmpty()) {
            Example.Criteria criteria2 = example.createCriteria();
            criteria2.andIn("id", problemIds);
            example.or(criteria2);
        }
        example.orderBy("lastModifyTime").desc();
        pageParam = example;
        return findPage(param.getPageInfo());
    }

    /**
     * @return 获取专家列表
     */
    public List<SysUserEntity> getExperts(){
        return sysUserService.selectUserByRoleId(2l);
    }

    /**
     * 查询问题（一周内，所有人的问题）
     * @param param
     * @return
     */
    public PageInfo<ProblemEntity> findMyProWeek(ProblemParam param){
        //先设置分页查询参数
        Example example = new Example(ProblemEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andGreaterThan("submitTime", DateUtil.lastWeek());
        example.orderBy("lastModifyTime").desc();
        pageParam = example;
        return findPage(param.getPageInfo());
    }

    /**
     * @param resp
     * @param name         文件真实名字
     * @param downloadName 文件下载时名字
     */
    public static void download(HttpServletResponse resp, String name, String downloadName) {
        String fileName = null;
        try {
            fileName = new String(downloadName.getBytes("GBK"), "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ///home/tomcat/apache-tomcat-9.0.1/files
        String realPath = "D:" + File.separator + "wiki" + File.separator + "files";
//        String realPath=File.separator+"home"+File.separator+"tomcat"+File.separator+"apache-tomcat-9.0.1"+File.separator+"files";
        String path = realPath + File.separator + name;
        File file = new File(name);
        resp.reset();
        resp.setContentType("application/octet-stream");
        resp.setCharacterEncoding("utf-8");
        resp.setContentLength((int) file.length());
        resp.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = resp.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(file));
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 查询已解决问题（所有人提出的）
     * @param param
     * @return
     */
    public PageInfo<ProblemEntity> findMyProSolved(ProblemParam param){
        //先设置分页查询参数
        Example example = new Example(ProblemEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status", 2);
        example.orderBy("lastModifyTime").desc();
        pageParam = example;
        return findPage(param.getPageInfo());
    }

    public boolean uploadFile(@RequestBody MultipartFile file, @RequestBody String problemId)throws Exception{
        String pathname = "D:"+ File.separator + "wiki";
        pathname += File.separator;
        pathname += DateUtils.format(new Date(),"yyyy");
        pathname += File.separator;
        pathname += DateUtils.format(new Date(),"yyyy-MM");
        pathname += File.separator;
        pathname += String.valueOf(System.currentTimeMillis());
        pathname += String.valueOf((int)(Math.random()*10000));
        String originalFilename = file.getOriginalFilename();
        String filename = originalFilename.substring(originalFilename.lastIndexOf("/")+1,originalFilename.lastIndexOf("."));
        String type = originalFilename.substring(originalFilename.lastIndexOf("."));
        pathname += type;
        File newfile = new File(pathname);
        if (!newfile.getParentFile().exists()) {
            newfile.getParentFile().mkdirs();
        }
        file.transferTo(newfile);
        FileEntity fileEntity = new FileEntity();
        fileEntity.setOriginName(filename);
        fileEntity.setPathName(pathname);
        fileEntity.setProblemId(problemId);
        fileManagerService.save(fileEntity);
        return true;
    }

    public void exportExcel(HttpServletResponse resp,ProblemEntity entity){
        List<ProblemEntity> list = findByEntity(entity);
        List<String> title = new ArrayList<String>();
        title.add("编号");
        title.add("提出人");
        title.add("标题");
        title.add("所属模块");
        title.add("问题类别");
        title.add("提交时间");
        title.add("最后修改时间");
        title.add("状态");
        title.add("锁定状态");
        ExcelUtil.createExcel( resp, list, "查询问题列表", title);
    }

}
