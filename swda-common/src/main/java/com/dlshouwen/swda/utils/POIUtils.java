//package com.dlshouwen.swda.utils;
//
//import org.apache.poi.hssf.usermodel.HSSFDateUtil;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.util.CellRangeAddress;
//
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
///**
// * poi utils
// * @author liujingcheng@live.cn
// * @since 0.0.1-SNAPSHOT
// */
//@SuppressWarnings("deprecation")
//public class POIUtils {
//
//	/**
//	 * get cell value
//	 * @param cell
//	 * @return value
//	 */
//	public static String getCellValue(Cell cell) {
//		String strCell;
//		if (cell == null) {
//			return "";
//		}
//		switch (cell.getCellType()) {
//			case Cell.CELL_TYPE_FORMULA:
//				try {
//					if (HSSFDateUtil.isCellDateFormatted(cell)) {
//						Date date = cell.getDateCellValue();
//						DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//						strCell = df.format(date);
//						break;
//					} else {
//						strCell = String.valueOf(cell.getNumericCellValue());
//					}
//				} catch (IllegalStateException e) {
//					strCell = String.valueOf(cell.getRichStringCellValue());
//				}
//				break;
//			case Cell.CELL_TYPE_STRING:
//				strCell = cell.getStringCellValue();
//				break;
//			case Cell.CELL_TYPE_NUMERIC:
//				if (HSSFDateUtil.isCellDateFormatted(cell)) {
//					Date date = cell.getDateCellValue();
//					DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//					strCell = format.format(date);
//				} else {
//					strCell = String.valueOf(cell.getNumericCellValue());
//				}
//				break;
//			case Cell.CELL_TYPE_BOOLEAN:
//				strCell = String.valueOf(cell.getBooleanCellValue());
//				break;
//			default:
//				strCell = "";
//				break;
//		}
//		return strCell;
//	}
//
//	/**
//	 * is merged region
//	 * @param cell
//	 * @return is merged region
//	 */
//	public static boolean isMergedRegion(Cell cell) {
//		int sheetMergeCount = cell.getSheet().getNumMergedRegions();
//		for (int i = 0; i < sheetMergeCount; i++) {
//			CellRangeAddress range = cell.getSheet().getMergedRegion(i);
//			int firstColumn = range.getFirstColumn();
//			int lastColumn = range.getLastColumn();
//			int firstRow = range.getFirstRow();
//			int lastRow = range.getLastRow();
//			if (cell.getRowIndex() >= firstRow && cell.getRowIndex() <= lastRow) {
//				if (cell.getColumnIndex() >= firstColumn && cell.getColumnIndex() <= lastColumn) {
//					return true;
//				}
//			}
//		}
//		return false;
//	}
//
//}
