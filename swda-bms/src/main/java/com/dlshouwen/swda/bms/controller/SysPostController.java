package com.dlshouwen.swda.bms.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.annotation.Operation;
import com.dlshouwen.swda.core.entity.base.PageResult;
import com.dlshouwen.swda.core.entity.base.R;
import com.dlshouwen.swda.core.enums.OperateType;
import com.dlshouwen.swda.bms.convert.SysPostConvert;
import com.dlshouwen.swda.bms.entity.SysPostEntity;
import com.dlshouwen.swda.bms.query.SysPostQuery;
import com.dlshouwen.swda.bms.service.SysPostService;
import com.dlshouwen.swda.bms.vo.SysPostVO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 岗位管理
 *
 * @author 阿沐 babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@RestController
@RequestMapping("sys/post")
@Tag(name = "岗位管理")
@AllArgsConstructor
public class SysPostController {
    private final SysPostService sysPostService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('sys:post:page')")
    public R<PageResult<SysPostVO>> page(@ParameterObject @Valid SysPostQuery query) {
        PageResult<SysPostVO> page = sysPostService.page(query);

        return R.ok(page);
    }

    @GetMapping("list")
    @Operation(summary = "列表")
    public R<List<SysPostVO>> list() {
        List<SysPostVO> list = sysPostService.getList();

        return R.ok(list);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('sys:post:info')")
    public R<SysPostVO> get(@PathVariable("id") Long id) {
        SysPostEntity entity = sysPostService.getById(id);

        return R.ok(SysPostConvert.INSTANCE.convert(entity));
    }

    @PostMapping
    @Operation(summary = "保存", type = OperateType.INSERT)
    @PreAuthorize("hasAuthority('sys:post:save')")
    public R<String> save(@RequestBody SysPostVO vo) {
        sysPostService.save(vo);

        return R.ok();
    }

    @PutMapping
    @Operation(summary = "修改", type = OperateType.UPDATE)
    @PreAuthorize("hasAuthority('sys:post:update')")
    public R<String> update(@RequestBody @Valid SysPostVO vo) {
        sysPostService.update(vo);

        return R.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除", type = OperateType.DELETE)
    @PreAuthorize("hasAuthority('sys:post:delete')")
    public R<String> delete(@RequestBody List<Long> idList) {
        sysPostService.delete(idList);

        return R.ok();
    }

    @PostMapping("nameList")
    @Operation(summary = "名称列表")
    public R<List<String>> nameList(@RequestBody List<Long> idList) {
        List<String> list = sysPostService.getNameList(idList);

        return R.ok(list);
    }
}