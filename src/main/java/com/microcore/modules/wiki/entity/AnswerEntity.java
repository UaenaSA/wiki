package com.microcore.modules.wiki.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "answer")
public class AnswerEntity {
    @Id
    private String id;

    @Column(name = "problem_id")
    private String problemId;

    private String author;

    @Column(name = "author_id")
    private String authorId;


    @Column(name = "assign_experts")
    private String assignExperts;

    @Column(name = "problem_desc")
    private String problemDesc;

    @Column(name = "problem_reason")
    private String problemReason;

    @Column(name = "replay_time")
    private Date replayTime;

    private String advise;

    /**
     * 0: nonmal 1:warm 2:import
     */
    @Column(name = "problem_level")
    private String problemLevel;


    @Column(name = "answer_append_url")
    private String answerAppendUrl;


    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return problem_id
     */
    public String getProblemId() {
        return problemId;
    }

    /**
     * @param problemId
     */
    public void setProblemId(String problemId) {
        this.problemId = problemId;
    }

    /**
     * @return author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    /**
     * @return problem_reason
     */
    public String getProblemReason() {
        return problemReason;
    }

    /**
     * @param problemReason
     */
    public void setProblemReason(String problemReason) {
        this.problemReason = problemReason;
    }

    /**
     * @return replay_time
     */
    public Date getReplayTime() {
        return replayTime;
    }

    /**
     * @param replayTime
     */
    public void setReplayTime(Date replayTime) {
        this.replayTime = replayTime;
    }

    public String getProblemDesc() {
        return problemDesc;
    }

    public void setProblemDesc(String problemDesc) {
        this.problemDesc = problemDesc;
    }

    public String getAssignExperts() {
        return assignExperts;
    }

    public void setAssignExperts(String assignExperts) {
        this.assignExperts = assignExperts;
    }

    /**
     * @return advise
     */
    public String getAdvise() {
        return advise;
    }

    /**
     * @param advise
     */
    public void setAdvise(String advise) {
        this.advise = advise;
    }

    /**
     * 获取0: nonmal 1:warm 2:import
     *
     * @return problem_level - 0: nonmal 1:warm 2:import
     */
    public String getProblemLevel() {
        return problemLevel;
    }

    /**
     * 设置0: nonmal 1:warm 2:import
     *
     * @param problemLevel 0: nonmal 1:warm 2:import
     */
    public void setProblemLevel(String problemLevel) {
        this.problemLevel = problemLevel;
    }

    /**
     * @return answer_append_url
     */
    public String getAnswerAppendUrl() {
        return answerAppendUrl;
    }

    /**
     * @param answerAppendUrl
     */
    public void setAnswerAppendUrl(String answerAppendUrl) {
        this.answerAppendUrl = answerAppendUrl;
    }
}