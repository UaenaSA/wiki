package com.microcore.modules.sys.entity;

import javax.persistence.Id;
import javax.persistence.Table;
/**
 * DESC:
 *
 * @author leizhenyang
 * @date 2018/5/29
 */
@Table(name = "sys_user_department")
public class SysUserDepartmentEntity {
    /**
     * 主键
     */
    @Id
    private Long userDepartmentId;
    /**
     * 部门ID
     */
    private Long departmentId;

    /**
     * 部门名称
     */
    private Long userId;

    public Long getUserDepartmentId() {
        return userDepartmentId;
    }

    public void setUserDepartmentId(Long userDepartmentId) {
        this.userDepartmentId = userDepartmentId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
