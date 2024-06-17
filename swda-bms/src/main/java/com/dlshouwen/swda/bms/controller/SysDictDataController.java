package com.dlshouwen.swda.bms.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.annotation.Operation;
import com.dlshouwen.swda.core.entity.base.R;
import com.dlshouwen.swda.core.enums.OperateType;
import com.dlshouwen.swda.core.utils.PageResult;
import com.dlshouwen.swda.bms.convert.SysDictDataConvert;
import com.dlshouwen.swda.bms.entity.SysDictDataEntity;
import com.dlshouwen.swda.bms.query.SysDictDataQuery;
import com.dlshouwen.swda.bms.service.SysDictDataService;
import com.dlshouwen.swda.bms.vo.SysDictDataVO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 字典数据
 *
 * @author 阿沐 babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@RestController
@RequestMapping("sys/dict/data")
@Tag(name = "字典数据")
@AllArgsConstructor
public class SysDictDataController {
    private final SysDictDataService sysDictDataService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('sys:dict:page')")
    public R<PageResult<SysDictDataVO>> page(@ParameterObject @Valid SysDictDataQuery query) {
        PageResult<SysDictDataVO> page = sysDictDataService.page(query);

        return R.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('sys:dict:info')")
    public R<SysDictDataVO> get(@PathVariable("id") Long id) {
        SysDictDataEntity entity = sysDictDataService.getById(id);

        return R.ok(SysDictDataConvert.INSTANCE.convert(entity));
    }

    @PostMapping
    @Operation(summary = "保存", type = OperateType.INSERT)
    @PreAuthorize("hasAuthority('sys:dict:save')")
    public R<String> save(@RequestBody @Valid SysDictDataVO vo) {
        sysDictDataService.save(vo);

        return R.ok();
    }

    @PutMapping
    @Operation(summary = "修改", type = OperateType.UPDATE)
    @PreAuthorize("hasAuthority('sys:dict:update')")
    public R<String> update(@RequestBody @Valid SysDictDataVO vo) {
        sysDictDataService.update(vo);

        return R.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除", type = OperateType.DELETE)
    @PreAuthorize("hasAuthority('sys:dict:delete')")
    public R<String> delete(@RequestBody List<Long> idList) {
        sysDictDataService.delete(idList);
        return R.ok();
    }

}