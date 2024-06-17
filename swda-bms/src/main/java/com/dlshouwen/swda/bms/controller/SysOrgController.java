package com.dlshouwen.swda.bms.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import com.dlshouwen.swda.bms.convert.SysOrgConvert;
import com.dlshouwen.swda.bms.entity.SysOrgEntity;
import com.dlshouwen.swda.bms.service.SysOrgService;
import com.dlshouwen.swda.bms.vo.SysOrgVO;
import com.dlshouwen.swda.core.annotation.Operation;
import com.dlshouwen.swda.core.entity.base.R;
import com.dlshouwen.swda.core.enums.OperateType;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 机构管理
 *
 * @author 阿沐 babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@RestController
@RequestMapping("sys/org")
@Tag(name = "机构管理")
@AllArgsConstructor
public class SysOrgController {
    private final SysOrgService sysOrgService;

    @GetMapping("list")
    @Operation(summary = "列表")
    @PreAuthorize("hasAuthority('sys:org:list')")
    public R<List<SysOrgVO>> list() {
        List<SysOrgVO> list = sysOrgService.getList();

        return R.ok(list);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('sys:org:info')")
    public R<SysOrgVO> get(@PathVariable("id") Long id) {
        SysOrgEntity entity = sysOrgService.getById(id);
        SysOrgVO vo = SysOrgConvert.INSTANCE.convert(entity);

        // 获取上级机构名称
        if (entity.getPid() != null) {
            SysOrgEntity parentEntity = sysOrgService.getById(entity.getPid());
            vo.setParentName(parentEntity.getName());
        }

        return R.ok(vo);
    }

    @PostMapping
    @Operation(summary = "保存", type = OperateType.INSERT)
    @PreAuthorize("hasAuthority('sys:org:save')")
    public R<String> save(@RequestBody @Valid SysOrgVO vo) {
        sysOrgService.save(vo);

        return R.ok();
    }

    @PutMapping
    @Operation(summary = "修改", type = OperateType.UPDATE)
    @PreAuthorize("hasAuthority('sys:org:update')")
    public R<String> update(@RequestBody @Valid SysOrgVO vo) {
        sysOrgService.update(vo);

        return R.ok();
    }

    @DeleteMapping("{id}")
    @Operation(summary = "删除", type = OperateType.DELETE)
    @PreAuthorize("hasAuthority('sys:org:delete')")
    public R<String> delete(@PathVariable("id") Long id) {
        sysOrgService.delete(id);

        return R.ok();
    }

    @PostMapping("nameList")
    @Operation(summary = "名称列表")
    public R<List<String>> nameList(@RequestBody List<Long> idList) {
        List<String> list = sysOrgService.getNameList(idList);

        return R.ok(list);
    }

}