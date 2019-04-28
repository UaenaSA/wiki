package com.microcore.modules.wiki.service;

import com.microcore.modules.wiki.entity.FileEntity;
import com.microcore.modules.wiki.mapper.FileEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

@Service
public class FileManagerService  extends  BaseService<FileEntity>{

    @Autowired
    private FileEntityMapper mapper;

    @Override
    public Mapper getMapper() {
        return mapper;
    }

    @Override
    public boolean save(FileEntity fileEntity) {
        int result = mapper.insert(fileEntity);
        if (result == 1) {
            return true;
        }
        return false;
    }
    public FileEntity findone(String id) {
        Example example = new Example(FileEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("problemId",id);
        return  mapper.selectOneByExample(example);
    }
}
