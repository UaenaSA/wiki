package com.microcore.modules.wiki.service;

import com.github.pagehelper.PageInfo;
import com.microcore.modules.wiki.controller.params.ProblemParam;
import com.microcore.modules.wiki.entity.AssignEntity;
import com.microcore.modules.wiki.entity.ProblemEntity;
import com.microcore.modules.wiki.mapper.AssignEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class AssignManagerService extends BaseService<AssignEntity>{

    @Autowired
    private AssignEntityMapper mapper;

    @Override
    public Mapper getMapper() {
        return mapper;
    }

    public List<String> getProIds(ProblemParam problemParam){
        PageInfo<ProblemEntity> pageInfo_old = problemParam.getPageInfo();
        PageInfo<AssignEntity> pageInfo_new = new PageInfo<AssignEntity>();
        pageInfo_new.setPageNum(pageInfo_old.getPageNum());
        pageInfo_new.setPageSize(pageInfo_old.getPageSize());
        Example example = new Example(AssignEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",problemParam.getProblemEntity().getAuthorId());
        pageParam = example;
        pageInfo_new = findPage(pageInfo_new);
        List<String> list = new ArrayList<String>();
        if (pageInfo_new.getList() != null) {
            for (AssignEntity assignEntity : pageInfo_new.getList()) {
                list.add(String.valueOf(assignEntity.getUserId()));
            }
        }
        return list;
    }

    @Override
    public boolean save(AssignEntity assignEntity) {
        int result = mapper.insert(assignEntity);
        if (result == 1)
            return true;
        return false;
    }

    public void deleteByProId(String proId) {
        Example example = new Example(AssignEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("problemId",proId);
        mapper.deleteByExample(example);
    }
}
