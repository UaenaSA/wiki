package com.microcore.modules.wiki.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

@Data
@Table(name = "assign")
public class AssignEntity {

    @Column(name = "user_id")
    private long userId;

    @Column(name = "problem_id")
    private String problemId;
}
