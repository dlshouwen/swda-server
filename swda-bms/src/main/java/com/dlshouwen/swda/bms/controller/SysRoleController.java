package com.dlshouwen.swda.bms.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.annotation.Operation;
import com.dlshouwen.swda.core.entity.auth.SecurityUser;
import com.dlshouwen.swda.core.entity.auth.UserDetail;
import com.dlshouwen.swda.core.entity.base.PageResult;
import com.dlshouwen.swda.core.entity.base.R;
import com.dlshouwen.swda.core.enums.OperateType;
import com.dlshouwen.swda.bms.convert.SysRoleConvert;
import com.dlshouwen.swda.bms.entity.SysRoleEntity;
import com.dlshouwen.swda.bms.query.SysRoleQuery;
import com.dlshouwen.swda.bms.query.SysRoleUserQuery;
import com.dlshouwen.swda.bms.service.*;
import com.dlshouwen.swda.bms.vo.SysMenuVO;
import com.dlshouwen.swda.bms.vo.SysRoleDataScopeVO;
import com.dlshouwen.swda.bms.vo.SysRoleVO;
import com.dlshouwen.swda.bms.vo.SysUserVO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色管理
 *
 * @author 阿沐 babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@RestController
@RequestMapping("sys/role")
@Tag(name = "角色管理")
@AllArgsConstructor
public class SysRoleController {
    private final SysRoleService sysRoleService;
    private final SysUserService sysUserService;
    private final SysRoleMenuService sysRoleMenuService;
    private final SysRoleDataScopeService sysRoleDataScopeService;
    private final SysMenuService sysMenuService;
    private final SysUserRoleService sysUserRoleService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('sys:role:page')")
    public R<PageResult<SysRoleVO>> page(@ParameterObject @Valid SysRoleQuery query) {
        PageResult<SysRoleVO> page = sysRoleService.page(query);

        return R.ok(page);
    }

    @GetMapping("list")
    @Operation(summary = "列表")
    @PreAuthorize("hasAuthority('sys:role:list')")
    public R<List<SysRoleVO>> list() {
        List<SysRoleVO> list = sysRoleService.getList(new SysRoleQuery());

        return R.ok(list);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('sys:role:info')")
    public R<SysRoleVO> get(@PathVariable("id") Long id) {
        SysRoleEntity entity = sysRoleService.getById(id);

        // 转换对象
        SysRoleVO role = SysRoleConvert.INSTANCE.convert(entity);

        // 查询角色对应的菜单
        List<Long> menuIdList = sysRoleMenuService.getMenuIdList(id);
        role.setMenuIdList(menuIdList);

        // 查询角色对应的数据权限
        List<Long> orgIdList = sysRoleDataScopeService.getOrgIdList(id);
        role.setOrgIdList(orgIdList);

        return R.ok(role);
    }

    @PostMapping
    @Operation(summary = "保存", type = OperateType.INSERT)
    @PreAuthorize("hasAuthority('sys:role:save')")
    public R<String> save(@RequestBody @Valid SysRoleVO vo) {
        sysRoleService.save(vo);

        return R.ok();
    }

    @PutMapping
    @Operation(summary = "修改", type = OperateType.UPDATE)
    @PreAuthorize("hasAuthority('sys:role:update')")
    public R<String> update(@RequestBody @Valid SysRoleVO vo) {
        sysRoleService.update(vo);

        return R.ok();
    }

    @PutMapping("data-scope")
    @Operation(summary = "数据权限", type = OperateType.UPDATE)
    @PreAuthorize("hasAuthority('sys:role:update')")
    public R<String> dataScope(@RequestBody @Valid SysRoleDataScopeVO vo) {
        sysRoleService.dataScope(vo);

        return R.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除", type = OperateType.DELETE)
    @PreAuthorize("hasAuthority('sys:role:delete')")
    public R<String> delete(@RequestBody List<Long> idList) {
        sysRoleService.delete(idList);

        return R.ok();
    }

    @GetMapping("menu")
    @Operation(summary = "角色菜单")
    @PreAuthorize("hasAuthority('sys:role:menu')")
    public R<List<SysMenuVO>> menu() {
        UserDetail user = SecurityUser.getUser();
        List<SysMenuVO> list = sysMenuService.getUserMenuList(user, null);

        return R.ok(list);
    }

    @GetMapping("user/page")
    @Operation(summary = "角色用户-分页")
    @PreAuthorize("hasAuthority('sys:role:update')")
    public R<PageResult<SysUserVO>> userPage(@Valid SysRoleUserQuery query) {
        PageResult<SysUserVO> page = sysUserService.roleUserPage(query);

        return R.ok(page);
    }

    @DeleteMapping("user/{roleId}")
    @Operation(summary = "删除角色用户", type = OperateType.DELETE)
    @PreAuthorize("hasAuthority('sys:role:update')")
    public R<String> userDelete(@PathVariable("roleId") Long roleId, @RequestBody List<Long> userIdList) {
        sysUserRoleService.deleteByUserIdList(roleId, userIdList);

        return R.ok();
    }

    @PostMapping("user/{roleId}")
    @Operation(summary = "分配角色给用户列表", type = OperateType.DELETE)
    @PreAuthorize("hasAuthority('sys:role:update')")
    public R<String> userSave(@PathVariable("roleId") Long roleId, @RequestBody List<Long> userIdList) {
        sysUserRoleService.saveUserList(roleId, userIdList);

        return R.ok();
    }

    @PostMapping("nameList")
    @Operation(summary = "名称列表")
    public R<List<String>> nameList(@RequestBody List<Long> idList) {
        List<String> list = sysRoleService.getNameList(idList);

        return R.ok(list);
    }
}