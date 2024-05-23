package com.dlshouwen.swda.entity.grid;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * query
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
@Data
public class Query {

	/** current page no */
	private Long current;

	/** size per page */
	private Long size;

	/** parameters */
	private Map<String, String> parameters;

	/** search query parameters */
	private Map<String, String> searchQueryParameters;

	/** fast query parameters */
	private Map<String, String> fastQueryParameters;

	/** advance query conditions */
	private List<AdvanceQueryCondition> advanceQueryConditions;

	/** advance query sorts */
	private List<AdvanceQuerySort> advanceQuerySorts;

	/** is export */
	private boolean isExport;

	/** export type: excel / pdf / txt / cvs */
	private String exportType;

	/** export file name */
	private String exportFileName;

	/** export columns */
	private List<Column> exportColumns;

	/** export all data */
	private boolean exportAllData;

	/** export data is processed */
	private boolean exportDataIsProcessed;

	/** export datas */
	private List<Map<String, Object>> exportDatas;

}
