package com.dlshouwen.swda.bms.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.annotation.Operation;
import com.dlshouwen.swda.core.entity.base.PageResult;
import com.dlshouwen.swda.core.entity.base.R;
import com.dlshouwen.swda.core.enums.OperateType;
import com.dlshouwen.swda.bms.query.SysAttachmentQuery;
import com.dlshouwen.swda.bms.service.SysAttachmentService;
import com.dlshouwen.swda.bms.vo.AttachmentVO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * attachment
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@RestController
@RequestMapping("sys/attachment")
@Tag(name = "attachment")
@AllArgsConstructor
public class SysAttachmentController {
	
	/** attachment service */
	private final SysAttachmentService sysAttachmentService;

	/**
	 * page
	 * @param query
	 * @return
	 */
	@GetMapping("page")
	@Operation(name = "page")
	@PreAuthorize("hasAuthority('sys:attachment:page')")
	public R<PageResult<AttachmentVO>> page(@ParameterObject @Valid SysAttachmentQuery query) {
//		page
		PageResult<AttachmentVO> page = sysAttachmentService.page(query);
//		return
		return R.ok(page);
	}

	/**
	 * save
	 * @param attachmentVO
	 * @return result
	 */
	@PostMapping
	@Operation(name = "save", type = OperateType.INSERT)
	@PreAuthorize("hasAuthority('sys:attachment:save')")
	public R<String> save(@RequestBody AttachmentVO vo) {
//		save
		sysAttachmentService.save(vo);
//		return
		return R.ok();
	}

	/**
	 * delete
	 * @param idList
	 * @return result
	 */
	@DeleteMapping
	@Operation(name = "delete", type = OperateType.DELETE)
	@PreAuthorize("hasAuthority('sys:attachment:delete')")
	public R<String> delete(@RequestBody List<Long> idList) {
//		delete
		sysAttachmentService.delete(idList);
//		return
		return R.ok();
	}

}