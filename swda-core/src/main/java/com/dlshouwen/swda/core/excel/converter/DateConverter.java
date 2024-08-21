package com.dlshouwen.swda.core.excel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.dlshouwen.swda.core.common.utils.DateUtils;

import java.util.Date;

/**
 * date converter
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public class DateConverter implements Converter<Date> {

	/**
	 * support java type key
	 * @return
	 */
	@Override
	public Class<Date> supportJavaTypeKey() {
		return Date.class;
	}

	/**
	 * support excel type key
	 * @return
	 */
	@Override
	public CellDataTypeEnum supportExcelTypeKey() {
		return CellDataTypeEnum.STRING;
	}

	/**
	 * convert to java date
	 * @param cellData
	 * @param contentProperty
	 * @param globalConfiguration
	 * @return date
	 */
	@Override
	public Date convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
		String value = cellData.getStringValue();
		return value == null ? null : DateUtils.parse(value, DateUtils.DATE_TIME_PATTERN);
	}

	/**
	 * convert to excel date
	 * @param value
	 * @param contentProperty
	 * @param globalConfiguration
	 * @return cell date
	 */
	@Override
	public WriteCellData<Date> convertToExcelData(Date value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
		return new WriteCellData<>(DateUtils.format(value, DateUtils.DATE_TIME_PATTERN));
	}

}