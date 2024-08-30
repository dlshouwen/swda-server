package com.dlshouwen.swda.core.excel.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.converters.longconverter.LongStringConverter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.dlshouwen.swda.core.common.utils.HttpContextUtils;
import com.dlshouwen.swda.core.excel.callback.ExcelFinishCallBack;
import com.dlshouwen.swda.core.excel.listener.ExcelDataListener;
import com.fhs.common.utils.ConverterUtils;
import com.fhs.core.trans.anno.Trans;
import com.fhs.core.trans.constant.TransType;
import com.fhs.core.trans.util.ReflectUtils;
import com.fhs.core.trans.vo.TransPojo;
import com.fhs.trans.service.impl.DictionaryTransService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * excel utils
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public class ExcelUtils {

	/**
	 * read analysis
	 * @param file
	 * @param head
	 * @param callBack
	 */
	public static <T> void readAnalysis(MultipartFile file, Class<T> head, ExcelFinishCallBack<T> callBack) {
		try {
			EasyExcel.read(file.getInputStream(), head, new ExcelDataListener<>(callBack)).sheet().doRead();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * read analysis
	 * @param file
	 * @param head
	 * @param callBack
	 */
	public static <T> void readAnalysis(File file, Class<T> head, ExcelFinishCallBack<T> callBack) {
		try {
			EasyExcel.read(new FileInputStream(file), head, new ExcelDataListener<>(callBack)).sheet().doRead();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * read sync
	 * @param file
	 * @param clazz
	 * @return java.util.List
	 */
	public static <T> List<T> readSync(File file, Class<T> clazz) {
		return readSync(file, clazz, 1, 0, ExcelTypeEnum.XLSX);
	}

	/**
	 * read sync
	 * @param file
	 * @param clazz
	 * @param rowNum
	 * @param sheetNo
	 * @param excelType
	 * @return java.util.List
	 */
	public static <T> List<T> readSync(File file, Class<T> clazz, Integer rowNum, Integer sheetNo,
			ExcelTypeEnum excelType) {
		return EasyExcel.read(file).headRowNumber(rowNum).excelType(excelType).head(clazz).sheet(sheetNo).doReadSync();
	}

	/**
	 * excel export
	 * @param head
	 * @param file
	 * @param data
	 */
	public static <T> void excelExport(Class<T> head, File file, List<T> data) {
		excelExport(head, file, "sheet1", data);
	}

	/**
	 * excel export
	 * @param head
	 * @param file
	 * @param sheetName
	 * @param data
	 */
	public static <T> void excelExport(Class<T> head, File file, String sheetName, List<T> data) {
		try {
			EasyExcel.write(file, head).sheet(sheetName).registerConverter(new LongStringConverter()).doWrite(data);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * excel export
	 * @param head
	 * @param excelName
	 * @param sheetName
	 * @param data
	 */
	public static <T> void excelExport(Class<T> head, String excelName, String sheetName, List<T> data) {
		try {
			HttpServletResponse response = getExportResponse(excelName);
			EasyExcel.write(response.getOutputStream(), head).sheet(StringUtils.isBlank(sheetName) ? "sheet1" : sheetName)
					.registerConverter(new LongStringConverter()).doWrite(data);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * excel export
	 * @param head
	 * @param excelName
	 * @param sheetName
	 * @param data
	 */
	public static <T> void excelExport(List<List<String>> head, String excelName, String sheetName, List<T> data) {
		try {
			HttpServletResponse response = getExportResponse(excelName);
			EasyExcel.write(response.getOutputStream()).head(head).sheet(StringUtils.isBlank(sheetName) ? "sheet1" : sheetName)
					.registerConverter(new LongStringConverter()).doWrite(data);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * get export response
	 * @param excelName
	 * @return response
	 */
	private static HttpServletResponse getExportResponse(String excelName) {
//		get response
		HttpServletResponse response = HttpContextUtils.getHttpServletResponse();
//		set content type, header, character encoding
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
		response.setCharacterEncoding("UTF-8");
//		append time
		excelName += DateUtil.format(new Date(), "yyyyMMddHHmmss");
//		url encode file name
		String fileName = URLUtil.encode(excelName, StandardCharsets.UTF_8);
//		set header
		response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
//		return response
		return response;
	}

	/**
	 * parse dict
	 * @param dataList
	 */
	@SneakyThrows
	public static <T extends TransPojo> void parseDict(List<T> dataList) {
//		if empty
		if (CollectionUtil.isEmpty(dataList)) {
			return;
		}
//		get class
		Class<? extends TransPojo> clazz = dataList.get(0).getClass();
//		get need trans fields
		List<Field> fields = ReflectUtils.getAnnotationField(clazz, Trans.class);
//		filter dictionary
		fields = fields.stream().filter(field -> TransType.DICTIONARY.equals(field.getAnnotation(Trans.class).type()))
				.collect(Collectors.toList());
//		get trans service
		DictionaryTransService dictionaryTransService = SpringUtil.getBean(DictionaryTransService.class);
//		for each data
		for (T data : dataList) {
//			for each field
			for (Field field : fields) {
//				get trans
				Trans trans = field.getAnnotation(Trans.class);
//				if key ref is not empty
				if (StrUtil.isAllNotBlank(trans.key(), trans.ref())) {
//					get ref field
					Field ref = ReflectUtils.getDeclaredField(clazz, trans.ref());
//					set can read
					ref.setAccessible(true);
//					get value
					String value = dictionaryTransService.getDictionaryTransMap().get(trans.key() + "_" + ref.get(data));
//					if blank continue
					if (StringUtils.isBlank(value)) {
						continue;
					}
//					set can read
					field.setAccessible(true);
//					if integer
					if (Integer.class.equals(field.getType())) {
//						set value
						field.set(data, ConverterUtils.toInteger(value));
					} else {
//						set value
						field.set(data, ConverterUtils.toString(value));
					}
				}
			}
		}

	}

}
