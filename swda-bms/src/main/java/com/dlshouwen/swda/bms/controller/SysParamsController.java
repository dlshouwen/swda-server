package com.dlshouwen.swda.bms.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.annotation.Operation;
import com.dlshouwen.swda.core.entity.base.PageResult;
import com.dlshouwen.swda.core.entity.base.R;
import com.dlshouwen.swda.core.enums.OperateType;
import com.dlshouwen.swda.bms.convert.SysParamsConvert;
import com.dlshouwen.swda.bms.entity.SysParamsEntity;
import com.dlshouwen.swda.bms.query.SysParamsQuery;
import com.dlshouwen.swda.bms.service.SysParamsService;
import com.dlshouwen.swda.bms.vo.SysParamsVO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 参数管理
 *
 * @author 阿沐 babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@RestController
@RequestMapping("sys/params")
@Tag(name = "参数管理")
@AllArgsConstructor
public class SysParamsController {
    private final SysParamsService sysParamsService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('sys:params:all')")
    public R<PageResult<SysParamsVO>> page(@ParameterObject @Valid SysParamsQuery query) {
        PageResult<SysParamsVO> page = sysParamsService.page(query);

        return R.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('sys:params:all')")
    public R<SysParamsVO> get(@PathVariable("id") Long id) {
        SysParamsEntity entity = sysParamsService.getById(id);

        return R.ok(SysParamsConvert.INSTANCE.convert(entity));
    }

    @PostMapping
    @Operation(summary = "保存", type = OperateType.INSERT)
    @PreAuthorize("hasAuthority('sys:params:all')")
    public R<String> save(@RequestBody SysParamsVO vo) {
        sysParamsService.save(vo);

        return R.ok();
    }

    @PutMapping
    @Operation(summary = "修改", type = OperateType.UPDATE)
    @PreAuthorize("hasAuthority('sys:params:all')")
    public R<String> update(@RequestBody @Valid SysParamsVO vo) {
        sysParamsService.update(vo);

        return R.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除", type = OperateType.DELETE)
    @PreAuthorize("hasAuthority('sys:params:all')")
    public R<String> delete(@RequestBody List<Long> idList) {
        sysParamsService.delete(idList);

        return R.ok();
    }
}