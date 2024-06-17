package com.dlshouwen.swda.bms.controller;

import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.annotation.Operation;
import com.dlshouwen.swda.core.entity.auth.SecurityUser;
import com.dlshouwen.swda.core.entity.auth.UserDetail;
import com.dlshouwen.swda.core.entity.base.R;
import com.dlshouwen.swda.core.enums.OperateType;
import com.dlshouwen.swda.core.utils.PageResult;
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
 * 用户管理
 *
 * @author 阿沐 babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@RestController
@RequestMapping("sys/user")
@AllArgsConstructor
@Tag(name = "用户管理")
public class SysUserController {
    private final SysUserService sysUserService;
    private final SysUserRoleService sysUserRoleService;
    private final SysUserPostService sysUserPostService;
    private final SysPostService sysPostService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('sys:user:page')")
    public R<PageResult<SysUserVO>> page(@ParameterObject @Valid SysUserQuery query) {
        PageResult<SysUserVO> page = sysUserService.page(query);

        return R.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('sys:user:info')")
    public R<SysUserVO> get(@PathVariable("id") Long id) {
        SysUserEntity entity = sysUserService.getById(id);

        SysUserVO vo = SysUserConvert.INSTANCE.convert(entity);

        // 用户角色列表
        List<Long> roleIdList = sysUserRoleService.getRoleIdList(id);
        vo.setRoleIdList(roleIdList);

        // 用户岗位列表
        List<Long> postIdList = sysUserPostService.getPostIdList(id);
        vo.setPostIdList(postIdList);

        return R.ok(vo);
    }

    @GetMapping("info")
    @Operation(summary = "登录用户")
    public R<SysUserVO> info() {
        SysUserVO user = SysUserConvert.INSTANCE.convert(SecurityUser.getUser());

        // 用户岗位列表
        List<Long> postIdList = sysUserPostService.getPostIdList(user.getId());
        user.setPostIdList(postIdList);

        // 用户岗位名称列表
        List<String> postNameList = sysPostService.getNameList(postIdList);
        user.setPostNameList(postNameList);

        return R.ok(user);
    }

    @PutMapping("info")
    @Operation(summary = "修改登录用户信息", type = OperateType.UPDATE)
    public R<String> loginInfo(@RequestBody @Valid SysUserBaseVO vo) {
        sysUserService.updateLoginInfo(vo);

        return R.ok();
    }

    @PutMapping("avatar")
    @Operation(summary = "修改登录用户头像", type = OperateType.UPDATE)
    public R<String> avatar(@RequestBody SysUserAvatarVO avatar) {
        sysUserService.updateAvatar(avatar);

        return R.ok();
    }

    @PutMapping("password")
    @Operation(summary = "修改密码", type = OperateType.UPDATE)
    public R<String> password(@RequestBody @Valid SysUserPasswordVO vo) {
        // 原密码不正确
        UserDetail user = SecurityUser.getUser();
        if (!passwordEncoder.matches(vo.getPassword(), user.getPassword())) {
            return R.error("原密码不正确");
        }

        // 修改密码
        sysUserService.updatePassword(user.getUserId(), passwordEncoder.encode(vo.getNewPassword()));

        return R.ok();
    }

    @PostMapping
    @Operation(summary = "保存", type = OperateType.INSERT)
    @PreAuthorize("hasAuthority('sys:user:save')")
    public R<String> save(@RequestBody @Valid SysUserVO vo) {
        // 新增密码不能为空
        if (StrUtil.isBlank(vo.getPassword())) {
            return R.error("密码不能为空");
        }

        // 密码加密
        vo.setPassword(passwordEncoder.encode(vo.getPassword()));

        // 保存
        sysUserService.save(vo);

        return R.ok();
    }

    @PutMapping
    @Operation(summary = "修改", type = OperateType.UPDATE)
    @PreAuthorize("hasAuthority('sys:user:update')")
    public R<String> update(@RequestBody @Valid SysUserVO vo) {
        // 如果密码不为空，则进行加密处理
        if (StrUtil.isBlank(vo.getPassword())) {
            vo.setPassword(null);
        } else {
            vo.setPassword(passwordEncoder.encode(vo.getPassword()));
        }

        sysUserService.update(vo);

        return R.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除", type = OperateType.DELETE)
    @PreAuthorize("hasAuthority('sys:user:delete')")
    public R<String> delete(@RequestBody List<Long> idList) {
        Long userId = SecurityUser.getUserId();
        if (idList.contains(userId)) {
            return R.error("不能删除当前登录用户");
        }

        sysUserService.delete(idList);

        return R.ok();
    }

    @PostMapping("nameList")
    @Operation(summary = "用户姓名列表")
    public R<List<String>> nameList(@RequestBody List<Long> idList) {
        List<String> list = sysUserService.getRealNameList(idList);

        return R.ok(list);
    }

    @PostMapping("import")
    @Operation(summary = "导入用户", type = OperateType.IMPORT)
    @PreAuthorize("hasAuthority('sys:user:import')")
    public R<String> importExcel(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return R.error("请选择需要上传的文件");
        }
        sysUserService.importByExcel(file, passwordEncoder.encode("123456"));

        return R.ok();
    }

    @GetMapping("export")
    @Operation(summary = "导出用户", type = OperateType.EXPORT)
    @PreAuthorize("hasAuthority('sys:user:export')")
    public void export() {
        sysUserService.export();
    }
}
