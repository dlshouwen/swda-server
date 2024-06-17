package com.dlshouwen.swda.bms.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.annotation.Operation;
import com.dlshouwen.swda.core.entity.base.R;
import com.dlshouwen.swda.core.enums.OperateType;
import com.dlshouwen.swda.core.utils.PageResult;
import com.dlshouwen.swda.bms.query.SysAttachmentQuery;
import com.dlshouwen.swda.bms.service.SysAttachmentService;
import com.dlshouwen.swda.bms.vo.SysAttachmentVO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 附件管理
 *
 * @author 阿沐 babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@RestController
@RequestMapping("sys/attachment")
@Tag(name = "附件管理")
@AllArgsConstructor
public class SysAttachmentController {
    private final SysAttachmentService sysAttachmentService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('sys:attachment:page')")
    public R<PageResult<SysAttachmentVO>> page(@ParameterObject @Valid SysAttachmentQuery query) {
        PageResult<SysAttachmentVO> page = sysAttachmentService.page(query);

        return R.ok(page);
    }

    @PostMapping
    @Operation(summary = "保存", type = OperateType.INSERT)
    @PreAuthorize("hasAuthority('sys:attachment:save')")
    public R<String> save(@RequestBody SysAttachmentVO vo) {
        sysAttachmentService.save(vo);

        return R.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除", type = OperateType.DELETE)
    @PreAuthorize("hasAuthority('sys:attachment:delete')")
    public R<String> delete(@RequestBody List<Long> idList) {
        sysAttachmentService.delete(idList);
        return R.ok();
    }
}