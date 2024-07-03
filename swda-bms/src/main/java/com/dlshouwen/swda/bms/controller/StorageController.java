package com.dlshouwen.swda.bms.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import com.dlshouwen.swda.bms.vo.FileUploadVO;
import com.dlshouwen.swda.core.annotation.Operation;
import com.dlshouwen.swda.core.entity.base.R;
import com.dlshouwen.swda.core.enums.OperateType;
import com.dlshouwen.swda.core.exception.SwdaException;
import com.dlshouwen.swda.oss.service.StorageService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * storage
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@RestController
@RequestMapping("storage")
@Tag(name = "storage")
@AllArgsConstructor
public class StorageController {
	
	/** storage service */
	private final StorageService storageService;

	/**
	 * upload
	 * @param file
	 * @return result
	 * @throws Exception
	 */
	@PostMapping("upload")
	@Operation(name = "upload", type = OperateType.INSERT)
	public R<FileUploadVO> upload(@RequestParam("file") MultipartFile file) throws Exception {
//		file is empty
		if (file.isEmpty()) {
//			return
			return R.error("请选择需要上传的文件");
		}
//		get path
		String path = storageService.getPath(file.getOriginalFilename());
//		upload file
		String url = storageService.upload(file.getBytes(), path);
//		create file upload vo
		FileUploadVO fileUpload = new FileUploadVO();
//		set user, size, name, platform
		fileUpload.setFileUrl(url);
		fileUpload.setFileSize(file.getSize());
		fileUpload.setFileName(file.getOriginalFilename());
//		TODO fileUpload.setStoragePlatform(storageService.properties.getConfig().getType().name());
//		return
		return R.ok(fileUpload);
	}

	/**
	 * uploads
	 * @param file
	 * @return result
	 * @throws Exception
	 */
	@PostMapping("uploads")
	@Operation(name = "uploads", type = OperateType.INSERT)
	public FileUploadVO uploads(@RequestParam("file") MultipartFile file) throws Exception {
//		file is empty
		if (file.isEmpty()) {
//			throw exceptiuon
			throw new SwdaException("请选择需要上传的文件");
		}
//		get path
		String path = storageService.getPath(file.getOriginalFilename());
//		upload
		String url = storageService.upload(file.getBytes(), path);
//		create file upload vo
		FileUploadVO fileUpload = new FileUploadVO();
//		set url, name
		fileUpload.setFileUrl(url);
		fileUpload.setFileName(file.getOriginalFilename());
//		return
		return fileUpload;
	}

}
