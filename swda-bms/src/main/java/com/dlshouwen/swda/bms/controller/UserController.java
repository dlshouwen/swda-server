package com.dlshouwen.swda.bms.controller;

import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.annotation.Operation;
import com.dlshouwen.swda.core.entity.auth.SecurityUser;
import com.dlshouwen.swda.core.entity.auth.UserDetail;
import com.dlshouwen.swda.core.entity.base.R;
import com.dlshouwen.swda.core.entity.grid.PageResult;
import com.dlshouwen.swda.core.entity.grid.Query;
import com.dlshouwen.swda.core.enums.OperateType;
import com.dlshouwen.swda.bms.convert.UserConvert;
import com.dlshouwen.swda.bms.entity.User;
import com.dlshouwen.swda.bms.service.IPostService;
import com.dlshouwen.swda.bms.service.IUserPostService;
import com.dlshouwen.swda.bms.service.IUserRoleService;
import com.dlshouwen.swda.bms.service.IUserService;
import com.dlshouwen.swda.bms.vo.UserAvatarVO;
import com.dlshouwen.swda.bms.vo.UserAssistVO;
import com.dlshouwen.swda.bms.vo.UserPasswordVO;
import com.dlshouwen.swda.bms.vo.UserVO;
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
@RequestMapping("user")
@AllArgsConstructor
@Tag(name = "user")
public class UserController {
	
	/** user service */
	private final IUserService userService;
	
	/** role service */
	private final IUserRoleService userRoleService;
	
	/** user post service */
	private final IUserPostService userPostService;
	
	/** post service */
	private final IPostService postService;
	
	/** password encoder */
	private final PasswordEncoder passwordEncoder;

	/**
	 * get user list
	 * @param query
	 * @return user list
	 */
	@GetMapping("/list")
	@Operation(name = "get user list")
	@PreAuthorize("hasAuthority('bms:user:list')")
	public R<PageResult<UserVO>> getUserList(@ParameterObject @Valid Query<User> query) {
//		get user list
		PageResult<UserVO> pageResult = userService.getUserList(query);
//		return
		return R.ok(pageResult);
	}

	/**
	 * get user data
	 * @param userId
	 * @return user data
	 */
	@GetMapping("/data/{userId}")
	@Operation(name = "get user data")
	@PreAuthorize("hasAuthority('bms:user:data')")
	public R<UserVO> getUserData(@PathVariable("id") Long userId) {
//		get user data
		UserVO user = userService.getUserData(userId);
//		get role id list set to user
		List<Long> roleIdList = userRoleService.getRoleIdList(userId);
		user.setRoleIdList(roleIdList);
//		get post id list set to user
		List<Long> postIdList = userPostService.getPostIdList(userId);
		user.setPostIdList(postIdList);
//		return user
		return R.ok(user);
	}

	/**
	 * get login user data
	 * @return result
	 */
	@GetMapping("/login/data")
	@Operation(name = "get login user data")
	public R<UserVO> getLoginUserData() {
//		convert user to user vo
		UserVO user = UserConvert.INSTANCE.convert2VO(SecurityUser.getUser());
//		get post id list set to user
		List<Long> postIdList = userPostService.getPostIdList(user.getUserId());
		user.setPostIdList(postIdList);
//		get post name list set to user
		List<String> postNameList = postService.getPostNameList(postIdList);
		user.setPostNameList(postNameList);
//		return
		return R.ok(user);
	}

	/**
	 * update login user
	 * @param userAssistVO
	 * @return result
	 */
	@PutMapping("/login/update")
	@Operation(name = "update login user", type = OperateType.UPDATE)
	public R<String> updateLoginUser(@RequestBody @Valid UserAssistVO userAssistVO) {
//		update login user
		userService.updateLoginUser(userAssistVO);
//		return
		return R.ok();
	}

	/**
	 * update avatar
	 * @param userAvatarVO
	 * @return result
	 */
	@PutMapping("/avatar/update")
	@Operation(name = "update avatar", type = OperateType.UPDATE)
	public R<String> updateAvatar(@RequestBody UserAvatarVO userAvatarVO) {
//		update avatar
		userService.updateAvatar(userAvatarVO);
//		return
		return R.ok();
	}

	/**
	 * update password
	 * @param userPasswordVO
	 * @return result
	 */
	@PutMapping("password")
	@Operation(name = "password", type = OperateType.UPDATE)
	public R<String> password(@RequestBody @Valid UserPasswordVO vo) {
//		get user detail
		UserDetail user = SecurityUser.getUser();
//		if password not equals
		if (!passwordEncoder.matches(vo.getPassword(), user.getPassword())) {
//			return
			return R.error("原密码不正确");
		}
//		update password
		userService.updatePassword(user.getUserId(), passwordEncoder.encode(vo.getNewPassword()));
//		return
		return R.ok();
	}

	/**
	 * add user
	 * @param userVO
	 * @return result
	 */
	@PostMapping("/add")
	@Operation(name = "add user", type = OperateType.INSERT)
	@PreAuthorize("hasAuthority('bms:user:add')")
	public R<String> addUser(@RequestBody @Valid UserVO userVO) {
//		password is not null
		if (StrUtil.isBlank(userVO.getPassword())) {
//			return
			return R.error("密码不能为空");
		}
//		encode password
		userVO.setPassword(passwordEncoder.encode(userVO.getPassword()));
//		add user
		userService.addUser(userVO);
//		return
		return R.ok();
	}

	/**
	 * update user
	 * @param userVO
	 * @return result
	 */
	@PutMapping("/update")
	@Operation(name = "update user", type = OperateType.UPDATE)
	@PreAuthorize("hasAuthority('bms:user:update')")
	public R<String> updateUser(@RequestBody @Valid UserVO userVO) {
//		if user is blank
		if (StrUtil.isBlank(userVO.getPassword())) {
//			set user password is null
			userVO.setPassword(null);
		} else {
//			encode password
			userVO.setPassword(passwordEncoder.encode(userVO.getPassword()));
		}
//		update user
		userService.updateUser(userVO);
//		return
		return R.ok();
	}

	/**
	 * delete user
	 * @param userIdList
	 * @return result
	 */
	@DeleteMapping("/delete")
	@Operation(name = "delete user", type = OperateType.DELETE)
	@PreAuthorize("hasAuthority('bms:user:delete')")
	public R<String> deleteUser(@RequestBody List<Long> userIdList) {
//		get login user id
		Long userId = SecurityUser.getUserId();
//		if has now user
		if (userIdList.contains(userId)) {
//			return
			return R.error("不能删除当前登录用户");
		}
//		delete user
		userService.deleteUser(userIdList);
//		return
		return R.ok();
	}

	/**
	 * get user name list
	 * @param userIdList
	 * @return user name list
	 */
	@PostMapping("/name/list")
	@Operation(name = "get user name list")
	public R<List<String>> getUserNameList(@RequestBody List<Long> userIdList) {
//		get user name list
		List<String> userNameList = userService.getUserNameList(userIdList);
//		return user name list
		return R.ok(userNameList);
	}

	/**
	 * import user
	 * @param file
	 * @return result
	 */
	@PostMapping("/import")
	@Operation(name = "import excel", type = OperateType.IMPORT)
	@PreAuthorize("hasAuthority('bms:user:import')")
	public R<String> importUser(@RequestParam("file") MultipartFile file) {
//		if file is empty
		if (file.isEmpty()) {
//			return
			return R.error("请选择需要上传的文件");
		}
//		import user
		userService.importUser(file, passwordEncoder.encode("123456"));
//		return
		return R.ok();
	}

}