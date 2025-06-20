package com.dlshouwen.swda.core.storage.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.storage.strategy.StorageStrategy;
import com.dlshouwen.swda.core.storage.vo.FileUploadVO;
import com.dlshouwen.swda.core.base.entity.R;
import com.dlshouwen.swda.core.base.exception.SwdaException;
import com.dlshouwen.swda.core.log.annotation.Operation;
import com.dlshouwen.swda.core.log.enums.OperateType;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * storage
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@RestController
@RequestMapping("/storage")
@Tag(name = "storage")
@AllArgsConstructor
public class StorageController {
	
	/** storage service */
	private final StorageStrategy storageService;

	/**
	 * upload
	 * @param file
	 * @return result
	 * @throws Exception
	 */
	@PostMapping("/upload")
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
		fileUpload.setPlatform(storageService.properties.getConfig().getType().getValue());
		fileUpload.setUrl(url);
		fileUpload.setSize(file.getSize());
		fileUpload.setName(file.getOriginalFilename());
//		return
		return R.ok(fileUpload);
	}

	/**
	 * uploads
	 * @param file
	 * @return result
	 * @throws Exception
	 */
	@PostMapping("/uploads")
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
		fileUpload.setUrl(url);
		fileUpload.setName(file.getOriginalFilename());
//		return
		return fileUpload;
	}

}
