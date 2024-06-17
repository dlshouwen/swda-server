package com.dlshouwen.swda.bms.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.annotation.Operation;
import com.dlshouwen.swda.core.entity.base.R;
import com.dlshouwen.swda.core.enums.OperateType;
import com.dlshouwen.swda.core.utils.PageResult;
import com.dlshouwen.swda.bms.convert.SysDictTypeConvert;
import com.dlshouwen.swda.bms.entity.SysDictTypeEntity;
import com.dlshouwen.swda.bms.query.SysDictTypeQuery;
import com.dlshouwen.swda.bms.service.SysDictTypeService;
import com.dlshouwen.swda.bms.vo.SysDictTypeVO;
import com.dlshouwen.swda.bms.vo.SysDictVO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 字典类型
 *
 * @author 阿沐 babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@RestController
@RequestMapping("sys/dict/type")
@Tag(name = "字典类型")
@AllArgsConstructor
public class SysDictTypeController {
    private final SysDictTypeService sysDictTypeService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('sys:dict:page')")
    public R<PageResult<SysDictTypeVO>> page(@ParameterObject @Valid SysDictTypeQuery query) {
        PageResult<SysDictTypeVO> page = sysDictTypeService.page(query);

        return R.ok(page);
    }

    @GetMapping("list/sql")
    @Operation(summary = "动态SQL数据")
    @PreAuthorize("hasAuthority('sys:dict:page')")
    public R<PageResult<SysDictVO.DictData>> listSql(Long id) {
        List<SysDictVO.DictData> list = sysDictTypeService.getDictSql(id);

        PageResult<SysDictVO.DictData> page = new PageResult<>(list, list.size());

        return R.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('sys:dict:info')")
    public R<SysDictTypeVO> get(@PathVariable("id") Long id) {
        SysDictTypeEntity entity = sysDictTypeService.getById(id);

        return R.ok(SysDictTypeConvert.INSTANCE.convert(entity));
    }

    @PostMapping
    @Operation(summary = "保存", type = OperateType.INSERT)
    @PreAuthorize("hasAuthority('sys:dict:save')")
    public R<String> save(@RequestBody @Valid SysDictTypeVO vo) {
        sysDictTypeService.save(vo);

        return R.ok();
    }

    @PutMapping
    @Operation(summary = "修改", type = OperateType.UPDATE)
    @PreAuthorize("hasAuthority('sys:dict:update')")
    public R<String> update(@RequestBody @Valid SysDictTypeVO vo) {
        sysDictTypeService.update(vo);

        return R.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除", type = OperateType.DELETE)
    @PreAuthorize("hasAuthority('sys:dict:delete')")
    public R<String> delete(@RequestBody List<Long> idList) {
        sysDictTypeService.delete(idList);

        return R.ok();
    }

    @GetMapping("all")
    @Operation(summary = "全部字典数据")
    public R<List<SysDictVO>> all() {
        List<SysDictVO> dictList = sysDictTypeService.getDictList();

        return R.ok(dictList);
    }

    @GetMapping("refreshTransCache")
    @Operation(summary = "刷新字典翻译缓存数据")
    @PreAuthorize("hasAuthority('sys:dict:refreshTransCache')")
    public R<String> refreshTransCache() {
        sysDictTypeService.refreshTransCache();
        return R.ok();
    }


}