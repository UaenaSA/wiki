package com.microcore.jcf.entity.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.OrderBy;
import java.io.Serializable;
import java.util.Date;

/**
 * @author leizhenyang
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class BaseEntity implements Serializable {

    /**
     * 默认时区GMT,最后序列化时间,根据+-时间设置
     */
    @Column(name = "CREATE_TIME")
    private Date createTime;

    /**
     * 修改时间
     */
    @OrderBy(value = "DESC")
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    /**
     * 创建时间
     */
    @Column(name = "CREATE_USER")
    private String createUser;

    /***
     * 修改用户
     */
    @Column(name = "UPDATE_USER")
    private String updateUser;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public static class Field {
        public static final String CREATE_TIME = "createTime";
        public static final String UPDATE_TIME = "updateTime";
        public static final String CREATE_USER = "createUser";
        public static final String UPDATE_USER = "updateUser";
        public static final String RESPONSE_PROTOCOL_CMD = "responseProtocolCMD";
    }
}