package com.dlshouwen.swda.bms.tool.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.base.entity.R;
import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.log.annotation.Operation;
import com.dlshouwen.swda.core.log.enums.OperateType;
import com.dlshouwen.swda.bms.tool.entity.Attachment;
import com.dlshouwen.swda.bms.tool.service.IAttachmentService;
import com.dlshouwen.swda.bms.tool.vo.AttachmentVO;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * attachment
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@RestController
@RequestMapping("/bms/tool/attachment")
@Tag(name = "attachment")
@AllArgsConstructor
public class AttachmentController {
	
	/** attachment service */
	private final IAttachmentService attachmentService;

	/**
	 * get attachment page result
	 * @param query
	 * @return attachment page result
	 */
	@PostMapping("/page")
	@Operation(name = "get attachment page result", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:tool:attachment:page')")
	public R<PageResult<AttachmentVO>> getAttachmentPageResult(Query<Attachment> query) {
//		get attachment page result
		PageResult<AttachmentVO> pageResult = attachmentService.getAttachmentPageResult(query);
//		return attachment page result
		return R.ok(pageResult);
	}
	
	/**
	 * get attachment data
	 * @param attachmentId
	 * @return attachment data
	 */
	@GetMapping("/{attachmentId}/data")
	@Operation(name = "get attachment data", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:tool:attachment:data')")
	public R<AttachmentVO> getAttachmentData(@PathVariable(name="attachmentId") Long attachmentId) {
//		get attachment data
		AttachmentVO attachment = attachmentService.getAttachmentData(attachmentId);
//		return attachment
		return R.ok(attachment);
	}

	/**
	 * add attachment
	 * @param attachmentVO
	 * @return result
	 */
	@PostMapping("/add")
	@Operation(name = "add attachment", type = OperateType.INSERT)
	@PreAuthorize("hasAuthority('bms:tool:attachment:add')")
	public R<String> addAttachment(@RequestBody @Valid AttachmentVO attachmentVO) {
//		save
		attachmentService.addAttachment(attachmentVO);
//		return
		return R.ok();
	}
	
	/**
	 * update attachment
	 * @param attachmentVO
	 * @return result
	 */
	@PostMapping("/update")
	@Operation(name = "update attachment", type = OperateType.UPDATE)
	@PreAuthorize("hasAuthority('bms:tool:attachment:update')")
	public R<String> updateAttachment(@RequestBody @Valid AttachmentVO attachmentVO) {
//		save
		attachmentService.updateAttachment(attachmentVO);
//		return
		return R.ok();
	}

	/**
	 * delete attachment
	 * @param attachmentIdList
	 * @return result
	 */
	@PostMapping("/delete")
	@Operation(name = "delete attachment", type = OperateType.DELETE)
	@PreAuthorize("hasAuthority('bms:tool:attachment:delete')")
	public R<String> deleteAttachment(@RequestBody List<Long> attachmentIdList) {
//		delete
		attachmentService.deleteAttachment(attachmentIdList);
//		return
		return R.ok();
	}

}