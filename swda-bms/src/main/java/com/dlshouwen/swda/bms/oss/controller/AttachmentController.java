package com.dlshouwen.swda.bms.oss.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.common.entity.R;
import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.log.annotation.Operation;
import com.dlshouwen.swda.core.log.enums.OperateType;
import com.dlshouwen.swda.bms.oss.entity.Attachment;
import com.dlshouwen.swda.bms.oss.service.IAttachmentService;
import com.dlshouwen.swda.bms.oss.vo.AttachmentVO;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * attachment
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@RestController
@RequestMapping("/attachment")
@Tag(name = "attachment")
@AllArgsConstructor
public class AttachmentController {
	
	/** attachment service */
	private final IAttachmentService attachmentService;

	/**
	 * get attachment list
	 * @param query
	 * @return attachment list
	 */
	@GetMapping("/list")
	@Operation(name = "get attachment list", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:attachment:list')")
	public R<PageResult<AttachmentVO>> getAttachmentList(Query<Attachment> query) {
//		get attachment list
		PageResult<AttachmentVO> pageResult = attachmentService.getAttachmentList(query);
//		return page result
		return R.ok(pageResult);
	}
	
	/**
	 * get attachment data
	 * @param attachmentId
	 * @return attachment data
	 */
	@GetMapping("/data")
	@Operation(name = "get attachment data", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:attachment:data')")
	public R<AttachmentVO> getAttachmentData(Long attachmentId) {
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
	@PreAuthorize("hasAuthority('bms:attachment:add')")
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
	@PreAuthorize("hasAuthority('bms:attachment:update')")
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
	@DeleteMapping("/delete")
	@Operation(name = "delete attachment", type = OperateType.DELETE)
	@PreAuthorize("hasAuthority('bms:attachment:delete')")
	public R<String> delete(@RequestBody List<Long> attachmentIdList) {
//		delete
		attachmentService.deleteAttachment(attachmentIdList);
//		return
		return R.ok();
	}

}