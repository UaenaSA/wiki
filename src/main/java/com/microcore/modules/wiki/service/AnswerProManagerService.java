package com.microcore.modules.wiki.service;

import com.github.pagehelper.PageInfo;
import com.microcore.modules.wiki.controller.params.ProblemParam;
import com.microcore.modules.wiki.entity.AnswerEntity;
import com.microcore.modules.wiki.entity.LogEntity;
import com.microcore.modules.wiki.entity.ProblemEntity;
import com.microcore.modules.wiki.mapper.AnswerEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Author JJJ
 * @Description:
 * @Date:Create in  2019/1/3-16:49
 */
@Service
public class AnswerProManagerService extends BaseService<AnswerEntity> {

    @Autowired
    private ProblemManagerService problemManagerService;

    @Autowired
    private LogManagerService log;

    @Autowired
    private AnswerEntityMapper mapper;

    @Override
    public Mapper getMapper() {
        return mapper;
    }

    //gcy 2019.4.17 根据新增操作是否成功返回boolean变量  begin
    /*@Override
    public AnswerEntity save(AnswerEntity answerEntity) {
        answerEntity.setId(UUID.randomUUID().toString());
        answerEntity.setReplayTime(new Date());
        mapper.insert(answerEntity);
        log.insert("查询问题", LogEntity.LOG_TYPE.INFO);
        return answerEntity;
    }*/
    @Override
    public boolean save(AnswerEntity answerEntity) {
        answerEntity.setId(UUID.randomUUID().toString());
        answerEntity.setReplayTime(new Date());
        ProblemEntity problemEntity = new ProblemEntity();
        problemEntity.setId(answerEntity.getProblemId());
        problemEntity = problemManagerService.findByKey(problemEntity);
        answerEntity.setProblemDesc(problemEntity.getContent());
        answerEntity.setProblemDesc(problemEntity.getAssignExperts());
        int result = mapper.insert(answerEntity);
        if (result == 1) {
            log.insert(problemEntity.getId(), problemEntity.getTitle(), LogEntity.LOG_TYPE.ANSWER,problemEntity.getStatus(),answerEntity.getAuthor());
            return true;
        }
        return false;
    }


    /**
     *
     * @param param
     * @return 返回当前作者的回答的问题id
     */
    public List<String> findmyanwser(ProblemParam param){
        PageInfo<AnswerEntity> pageInfo =  new PageInfo<AnswerEntity>();
        pageInfo.setPageSize(param.getPageInfo().getPageSize()*param.getPageInfo().getPageNum());
        pageInfo.setPageNum(1);
        Example example = new Example(AnswerEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("authorId",param.getProblemEntity().getAuthorId());
        example.orderBy("replayTime").desc();
        pageParam = example;
        pageInfo = findPage(pageInfo);
        List<String> problemIds = new ArrayList<String>();
        List<AnswerEntity> answerEntities = pageInfo.getList();
        if (answerEntities != null) {
            for (AnswerEntity answerEntity : answerEntities) {
                problemIds.add(answerEntity.getProblemId());
            }
        }
        return problemIds;
    }

    /**
     * 根据问题id查询回答
     * @param proId
     * @return
     */
    public List<AnswerEntity> findAnswerByProId(String proId){
        Example example = new Example(AnswerEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("problemId",proId);
        example.orderBy("replayTime").desc();
        return mapper.selectByExample(example);
    }

    //gcy 2019.4.17 根据新增操作是否成功返回boolean变量  end
}
