package com.dlshouwen.swda.core.excel.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;

/**
 * poi utils
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public class PoiUtils {

	/**
	 * get value
	 * @param cell
	 * @return value
	 */
	public static String getString(Cell cell) {
//		format
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		DecimalFormat df = new DecimalFormat("##0.##");
//		string
		if(cell.getCellType()==CellType.STRING) {
			return cell.getStringCellValue();
		}
//		numeric
		if(cell.getCellType()==CellType.NUMERIC) {
//			date
			if(DateUtil.isCellDateFormatted(cell)) {
				return sdf.format(cell.getDateCellValue());
			}else {
				return df.format(cell.getNumericCellValue());
			}
		}
//		formula
		if(cell.getCellType()==CellType.FORMULA) {
			try {
//				date
				if(DateUtil.isCellDateFormatted(cell)) {
					return sdf.format(cell.getDateCellValue());
				}else {
					return df.format(cell.getNumericCellValue());
				}
			}catch(IllegalStateException e) {
//				rich string
				return cell.getRichStringCellValue().getString();
			}
		}
//		return empty
		return "";
	}

}
