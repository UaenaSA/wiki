package com.microcore.modules.test.entity;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
/**
 * XXXX
 *
 * @author weihanyu
 * @date 2018/5/23
 */
@Table(name = "TEST")
public class Test {

    @Id
    private Integer id;

    private Long age;

    private Date birth;

    private String name;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
