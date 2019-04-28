package com.microcore.modules.wiki.entity;

import com.microcore.modules.sys.entity.SysUserEntity;
import com.xiaoleilu.hutool.collection.CollectionUtil;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Table(name = "problem")
@Data
public class ProblemEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "author_id")
    private String authorId;

    /**
     * 0：故障诊断 ,1:远程监控 2:信息浏览与发布 3:任务管理
     */
    private Integer module;

    /**
     * 0：COMMON ,1:CASE 2:tree
     */
    private Integer type;

    private String title;

    private String content;

    /**
     * 0：submit ,1:replyed   2:solved   3:closed
     */
    private Integer status;

    @Column(name = "submit_time")
    private Date submitTime;

    @Column(name = "last_modify_time")
    private Date lastModifyTime;

    //问题是否拥有附件 0：无 1：有
    @Column(name = "have_append")
    private int haveAppend;

    private String author;

    @Column(name = "assign_experts")
    private String assignExperts;


    private List<AnswerEntity> answerEntityList = CollectionUtil.newArrayList();
    private List<SysUserEntity> sysUserEntities = CollectionUtil.newArrayList();
    private MultipartFile file;

}