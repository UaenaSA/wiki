package com.microcore.modules.sys.mapper;

import com.microcore.modules.sys.entity.SysUserDepartmentEntity;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * DESC:
 *
 * @author leizhenyang
 * @date 2018/5/29
 */
@org.apache.ibatis.annotations.Mapper
public interface SysUserDepartmentMapper extends Mapper<SysUserDepartmentEntity> {
    List<Long> queryDepartmentIdList(Long userId);
}
