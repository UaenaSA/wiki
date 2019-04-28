package com.microcore.modules.wiki.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "log")
@Data
public class LogEntity {
    @Id
    private String id;

    @Column(name = "problem_id")
    private String problemId;

    @Column(name = "problem_title")
    private String problemTitle;

    /**
     * 0:save 1:answer 2:update
     */
    private Integer type;

    /**
     * 0：submit ,1:replyed   2:solved   3:closed
     */
    @Column(name = "problem_status")
    private Integer problemStatus;

    private Date time;

    private String user;

    public static class LOG_TYPE {
        public static int SAVE = 0;
        public static int ANSWER = 1;
        public static int UPDATE = 2;
    }


}