package com.microcore.modules.sys.controller;

import com.microcore.common.annotation.SysLog;
import com.microcore.common.utils.Constant;
import com.microcore.common.utils.PageUtils;
import com.microcore.common.utils.Query;
import com.microcore.common.utils.R;
import com.microcore.common.validator.Assert;
import com.microcore.common.validator.ValidatorUtils;
import com.microcore.common.validator.group.AddGroup;
import com.microcore.common.validator.group.UpdateGroup;
import com.microcore.modules.sys.entity.SysUserEntity;
import com.microcore.modules.sys.service.SysUserRoleService;
import com.microcore.modules.sys.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * 
 * @author chenshun
 * @date 2016年10月31日 上午10:40:10
 */

@Api(value = "用户管理")
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	
	/**
	 * 所有用户列表
	 */
	@ApiOperation(value = "获取所有用户列表", notes = "接收Map对象")
	@ApiImplicitParam(value = "查询条件", name = "params", dataType = "Map<String, Object>", required = true)
	@RequestMapping("/list")
	@RequiresPermissions("sys:user:list")
	public R list(@RequestBody Map<String, Object> params){
		//只有超级管理员，才能查看所有管理员列表
		if(getUserId() != Constant.SUPER_ADMIN){
			params.put("createUserId", getUserId());
		}
		
		//查询列表数据
		Query query = new Query(params);
		List<SysUserEntity> userList = sysUserService.queryList(query);
		int total = sysUserService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(userList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	/**
	 * 获取登录的用户信息
	 */
	@RequestMapping("/info")
	public R info(){
		return R.ok().put("user", getUser());
	}
	
	/**
	 * 修改登录用户密码
	 */
	@ApiOperation(value = "修改登录用户密码",notes = "接收String型新密码与旧密码")
	@SysLog("修改密码")
	@RequestMapping("/password")
	public R password(String password, String newPassword){
		Assert.isBlank(newPassword, "新密码不为能空");
		
		//sha256加密
		password = new Sha256Hash(password, getUser().getSalt()).toHex();
		//sha256加密
		newPassword = new Sha256Hash(newPassword, getUser().getSalt()).toHex();
				
		//更新密码
		int count = sysUserService.updatePassword(getUserId(), password, newPassword);
		if(count == 0){
			return R.error("原密码不正确");
		}
		
		return R.ok();
	}
	
	/**uerid
	 * 用户信息
	 */
	@ApiOperation(value = "用户信息", notes = "接收Long型")
	@ApiImplicitParam(value = "用户ID", name = "userId", dataType = "Long", required = true)
	@RequestMapping("/info/{userId}")
	@RequiresPermissions("sys:user:info")
	public R info(@PathVariable("userId") Long userId){
		SysUserEntity user = sysUserService.queryObject(userId);
		
		//获取用户所属的角色列表
		List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
		user.setRoleIdList(roleIdList);
		
		return R.ok().put("user", user);
	}
	
	/**
	 * 保存用户
	 */
	@ApiOperation(value = "新建用户", notes = "接收SysUserEntity对象")
	@ApiImplicitParam(value = "用户对象", name = "user", dataType = "SysUserEntity", required = true)
	@SysLog("保存用户")
	@RequestMapping("/save")
	//@RequiresPermissions("sys:user:save")
	public R save(@RequestBody SysUserEntity user){
		ValidatorUtils.validateEntity(user, AddGroup.class);
		
		user.setCreateUserId(getUserId());
		sysUserService.save(user);
		
		return R.ok();
	}
	
	/**
	 * 修改用户
	 */
	@ApiOperation(value = "修改用户", notes = "接收SysUserEntity对象")
	@ApiImplicitParam(value = "用户对象", name = "user", dataType = "SysUserEntity", required = true)
	@SysLog("修改用户")
	@RequestMapping("/update")
	@RequiresPermissions("sys:user:update")
	public R update(@RequestBody SysUserEntity user){
		ValidatorUtils.validateEntity(user, UpdateGroup.class);
		
		user.setCreateUserId(getUserId());
		sysUserService.update(user);
		
		return R.ok();
	}
	
	/**
	 * 删除用户
	 */
	@ApiOperation(value = "删除用户", notes = "接收userid数组对象")
	@ApiImplicitParam(value = "userid组成的数组", name = "userIds", dataType = "Long[]", required = true)
	@SysLog("删除用户")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:user:delete")
	public R delete(@RequestBody Long[] userIds){
		if(ArrayUtils.contains(userIds, 1L)){
			return R.error("系统管理员不能删除");
		}
		
		if(ArrayUtils.contains(userIds, getUserId())){
			return R.error("当前用户不能删除");
		}
		
		sysUserService.deleteBatch(userIds);
		
		return R.ok();
	}
}
