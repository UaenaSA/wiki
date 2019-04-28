package com.microcore.modules.wiki.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "file")
@Data
public class FileEntity {


    @Column(name = "problem_id")
    private String problemId;

    @Column(name = "origin_name")
    private String originName;

    @Column(name = "path_name")
    private String pathName;
}
