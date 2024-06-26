package com.dlshouwen.swda.bms.controller;

import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.annotation.Operation;
import com.dlshouwen.swda.core.entity.auth.SecurityUser;
import com.dlshouwen.swda.core.entity.auth.UserDetail;
import com.dlshouwen.swda.core.entity.base.PageResult;
import com.dlshouwen.swda.core.entity.base.R;
import com.dlshouwen.swda.core.enums.OperateType;
import com.dlshouwen.swda.bms.convert.SysUserConvert;
import com.dlshouwen.swda.bms.entity.SysUserEntity;
import com.dlshouwen.swda.bms.query.SysUserQuery;
import com.dlshouwen.swda.bms.service.SysPostService;
import com.dlshouwen.swda.bms.service.SysUserPostService;
import com.dlshouwen.swda.bms.service.SysUserRoleService;
import com.dlshouwen.swda.bms.service.SysUserService;
import com.dlshouwen.swda.bms.vo.SysUserAvatarVO;
import com.dlshouwen.swda.bms.vo.SysUserBaseVO;
import com.dlshouwen.swda.bms.vo.SysUserPasswordVO;
import com.dlshouwen.swda.bms.vo.SysUserVO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * user
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@RestController
@RequestMapping("sys/user")
@AllArgsConstructor
@Tag(name = "user")
public class SysUserController {
	
	/** user service */
	private final SysUserService sysUserService;
	
	/** role service */
	private final SysUserRoleService sysUserRoleService;
	
	/** user post service */
	private final SysUserPostService sysUserPostService;
	
	/** post service */
	private final SysPostService sysPostService;
	
	/** password encoder */
	private final PasswordEncoder passwordEncoder;

	/**
	 * page
	 * @param query
	 * @return result
	 */
	@GetMapping("page")
	@Operation(name = "page")
	@PreAuthorize("hasAuthority('sys:user:page')")
	public R<PageResult<SysUserVO>> page(@ParameterObject @Valid SysUserQuery query) {
//		page
		PageResult<SysUserVO> page = sysUserService.page(query);
//		return
		return R.ok(page);
	}

	/**
	 * get
	 * @param id
	 * @return result
	 */
	@GetMapping("{id}")
	@Operation(name = "get")
	@PreAuthorize("hasAuthority('sys:user:info')")
	public R<SysUserVO> get(@PathVariable("id") Long id) {
//		get user
		SysUserEntity entity = sysUserService.getById(id);
//		convert to user vo
		SysUserVO vo = SysUserConvert.INSTANCE.convert(entity);
//		get role id list set to user
		List<Long> roleIdList = sysUserRoleService.getRoleIdList(id);
		vo.setRoleIdList(roleIdList);
//		get post id list set to user
		List<Long> postIdList = sysUserPostService.getPostIdList(id);
		vo.setPostIdList(postIdList);
//		return
		return R.ok(vo);
	}

	/**
	 * info
	 * @return result
	 */
	@GetMapping("info")
	@Operation(name = "info")
	public R<SysUserVO> info() {
//		convert user to user vo
		SysUserVO user = SysUserConvert.INSTANCE.convert(SecurityUser.getUser());
//		get post id list set to user
		List<Long> postIdList = sysUserPostService.getPostIdList(user.getId());
		user.setPostIdList(postIdList);
//		get post name list set to user
		List<String> postNameList = sysPostService.getNameList(postIdList);
		user.setPostNameList(postNameList);
//		return
		return R.ok(user);
	}

	/**
	 * info
	 * @param userBaseVO
	 * @return result
	 */
	@PutMapping("info")
	@Operation(name = "login info", type = OperateType.UPDATE)
	public R<String> loginInfo(@RequestBody @Valid SysUserBaseVO vo) {
//		update login info
		sysUserService.updateLoginInfo(vo);
//		return
		return R.ok();
	}

	/**
	 * avatar
	 * @param avatar
	 * @return result
	 */
	@PutMapping("avatar")
	@Operation(name = "avatar", type = OperateType.UPDATE)
	public R<String> avatar(@RequestBody SysUserAvatarVO avatar) {
//		update avatar
		sysUserService.updateAvatar(avatar);
//		return
		return R.ok();
	}

	/**
	 * password
	 * @param userPasswordVO
	 * @return result
	 */
	@PutMapping("password")
	@Operation(name = "password", type = OperateType.UPDATE)
	public R<String> password(@RequestBody @Valid SysUserPasswordVO vo) {
//		get user detail
		UserDetail user = SecurityUser.getUser();
//		if password not equals
		if (!passwordEncoder.matches(vo.getPassword(), user.getPassword())) {
//			return
			return R.error("原密码不正确");
		}
//		update password
		sysUserService.updatePassword(user.getUserId(), passwordEncoder.encode(vo.getNewPassword()));
//		return
		return R.ok();
	}

	/**
	 * save
	 * @param userVO
	 * @return result
	 */
	@PostMapping
	@Operation(name = "save", type = OperateType.INSERT)
	@PreAuthorize("hasAuthority('sys:user:save')")
	public R<String> save(@RequestBody @Valid SysUserVO vo) {
//		password is not null
		if (StrUtil.isBlank(vo.getPassword())) {
//			return
			return R.error("密码不能为空");
		}
//		set password
		vo.setPassword(passwordEncoder.encode(vo.getPassword()));
//		save user
		sysUserService.save(vo);
//		return
		return R.ok();
	}

	/**
	 * update
	 * @param userVO
	 * @return result
	 */
	@PutMapping
	@Operation(name = "update", type = OperateType.UPDATE)
	@PreAuthorize("hasAuthority('sys:user:update')")
	public R<String> update(@RequestBody @Valid SysUserVO vo) {
//		if user is blank
		if (StrUtil.isBlank(vo.getPassword())) {
//			set user password is null
			vo.setPassword(null);
		} else {
//			set user password
			vo.setPassword(passwordEncoder.encode(vo.getPassword()));
		}
//		update user
		sysUserService.update(vo);
//		return
		return R.ok();
	}

	/**
	 * delete
	 * @param idList
	 * @return result
	 */
	@DeleteMapping
	@Operation(name = "delete", type = OperateType.DELETE)
	@PreAuthorize("hasAuthority('sys:user:delete')")
	public R<String> delete(@RequestBody List<Long> idList) {
//		get login user id
		Long userId = SecurityUser.getUserId();
//		if has now user
		if (idList.contains(userId)) {
//			return
			return R.error("不能删除当前登录用户");
		}
//		delete
		sysUserService.delete(idList);
//		return
		return R.ok();
	}

	/**
	 * get name list
	 * @param idList
	 * @return return
	 */
	@PostMapping("nameList")
	@Operation(name = "get real name list")
	public R<List<String>> nameList(@RequestBody List<Long> idList) {
//		get real name list
		List<String> list = sysUserService.getRealNameList(idList);
//		return
		return R.ok(list);
	}

	/**
	 * import
	 * @param file
	 * @return result
	 */
	@PostMapping("import")
	@Operation(name = "import excel", type = OperateType.IMPORT)
	@PreAuthorize("hasAuthority('sys:user:import')")
	public R<String> importExcel(@RequestParam("file") MultipartFile file) {
//		if file is empty
		if (file.isEmpty()) {
//			return
			return R.error("请选择需要上传的文件");
		}
//		import by excel
		sysUserService.importByExcel(file, passwordEncoder.encode("123456"));
//		return
		return R.ok();
	}

	/**
	 * export
	 */
	@GetMapping("export")
	@Operation(name = "export", type = OperateType.EXPORT)
	@PreAuthorize("hasAuthority('sys:user:export')")
	public void export() {
//		export
		sysUserService.export();
	}

}
