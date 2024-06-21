package com.dlshouwen.swda.core.excel;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.dlshouwen.swda.core.utils.DateUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * local date time converter
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public class LocalDateTimeConverter implements Converter<LocalDateTime> {

	/**
	 * support java type key
	 * @return local date time
	 */
	@Override
	public Class<LocalDateTime> supportJavaTypeKey() {
		return LocalDateTime.class;
	}

	/**
	 * support excel type key
	 * @return cell date type
	 */
	@Override
	public CellDataTypeEnum supportExcelTypeKey() {
		return CellDataTypeEnum.STRING;
	}

	/**
	 * convert to java data
	 * @param cellData
	 * @param contentProperty
	 * @param globalConfiguration
	 * @return local date time
	 */
	@Override
	public LocalDateTime convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
		String value = cellData.getStringValue();
		return value == null ? null : LocalDateTimeUtil.parse(value, DateTimeFormatter.ofPattern(DateUtils.DATE_TIME_PATTERN));
	}

	/**
	 * convert to excel data
	 * @param value
	 * @param contentProperty
	 * @param globalConfiguration
	 * @return cell date
	 */
	@Override
	public WriteCellData<LocalDateTime> convertToExcelData(LocalDateTime value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
		String dateValue = LocalDateTimeUtil.format(value, DateUtils.DATE_TIME_PATTERN);
		return new WriteCellData<>(dateValue);
	}
}
