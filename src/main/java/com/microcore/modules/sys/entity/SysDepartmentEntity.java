package com.microcore.modules.sys.entity;

import javax.persistence.Id;
import javax.persistence.Table;
/**
 * DESC:
 *
 * @author leizhenyang
 * @date 2018/5/29
 */
@Table(name = "sys_department")
public class SysDepartmentEntity {
    /**
     * 部门ID
     */
    @Id
    private Long departmentId;

    /**
     * 部门名称
     */
    private String departmentName;

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
