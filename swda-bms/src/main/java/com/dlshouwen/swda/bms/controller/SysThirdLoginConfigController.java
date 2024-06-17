package com.dlshouwen.swda.bms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import com.dlshouwen.swda.bms.convert.SysThirdLoginConfigConvert;
import com.dlshouwen.swda.bms.entity.SysThirdLoginConfigEntity;
import com.dlshouwen.swda.bms.service.SysThirdLoginConfigService;
import com.dlshouwen.swda.bms.vo.SysThirdLoginConfigVO;
import com.dlshouwen.swda.core.entity.base.R;
import com.dlshouwen.swda.core.query.Query;
import com.dlshouwen.swda.core.utils.PageResult;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 第三方登录配置
 *
 * @author 阿沐 babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@RestController
@RequestMapping("sys/third/config")
@Tag(name = "第三方登录配置")
@AllArgsConstructor
public class SysThirdLoginConfigController {
    private final SysThirdLoginConfigService sysThirdLoginConfigService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('third:config:all')")
    public R<PageResult<SysThirdLoginConfigVO>> page(@ParameterObject @Valid Query query) {
        PageResult<SysThirdLoginConfigVO> page = sysThirdLoginConfigService.page(query);

        return R.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('third:config:all')")
    public R<SysThirdLoginConfigVO> get(@PathVariable("id") Long id) {
        SysThirdLoginConfigEntity entity = sysThirdLoginConfigService.getById(id);

        return R.ok(SysThirdLoginConfigConvert.INSTANCE.convert(entity));
    }

    @PostMapping
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority('third:config:all')")
    public R<String> save(@RequestBody SysThirdLoginConfigVO vo) {
        sysThirdLoginConfigService.save(vo);

        return R.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @PreAuthorize("hasAuthority('third:config:all')")
    public R<String> update(@RequestBody @Valid SysThirdLoginConfigVO vo) {
        sysThirdLoginConfigService.update(vo);

        return R.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority('third:config:all')")
    public R<String> delete(@RequestBody List<Long> idList) {
        sysThirdLoginConfigService.delete(idList);

        return R.ok();
    }
}