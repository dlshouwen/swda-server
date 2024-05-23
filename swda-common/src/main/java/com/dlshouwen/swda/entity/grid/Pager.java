package com.dlshouwen.swda.entity.grid;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dlshouwen.swda.entity.dict.ConditionLogic;
import com.dlshouwen.swda.entity.base.R;
import com.dlshouwen.swda.entity.dict.SortLogic;
import com.dlshouwen.swda.utils.GridUtils;
import com.dlshouwen.swda.utils.ObjectMapperUtils;
import com.dlshouwen.swda.utils.SqlUtils;
import com.dlshouwen.swda.utils.TokenUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * pager
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
@Data
public class Pager<T> {

	//	logger
	private static final Logger logger = LoggerFactory.getLogger(TokenUtils.class);

	/** query */
	private Query query;

	/** page */
	private Page<T> page;

	/** query wrapper */
	private QueryWrapper<T> queryWrapper;

	/** has previous */
	private boolean hasPrevious;

	/** has next */
	private boolean hasNext;
	
	/**
	 * construct
	 */
	public Pager(Query query){
		this.query = query;
		this.page = new Page<>(query.getCurrent(), query.getSize());
		this.queryWrapper = new QueryWrapper<>();
	}

	/**
	 * construct query wrapper
	 */
	public void constructQueryWrapper(){
//		construct search query parameter
		if(query.getSearchQueryParameters()!=null)
			constructParameters(query.getSearchQueryParameters());
//		construct fast query parameter
		if(query.getFastQueryParameters()!=null)
			constructParameters(query.getFastQueryParameters());
//		if has advance query conditions
		if(query.getAdvanceQueryConditions()!=null&&!query.getAdvanceQueryConditions().isEmpty()){
//			defined condition sql / arguments
			StringBuilder conditionSQL = new StringBuilder();
			List<Object> arguments = new ArrayList<>();
//			append
			conditionSQL.append(" ( ");
//			for each condition
			for(AdvanceQueryCondition advanceQueryCondition : query.getAdvanceQueryConditions()){
//				get left parentheses / field / condition / value / right parentheses / logic
				String leftParentheses = advanceQueryCondition.getLeftParentheses();
				String field = StrUtil.toUnderlineCase(advanceQueryCondition.getConditionField());
				String condition = advanceQueryCondition.getConditionType();
				String value = advanceQueryCondition.getConditionValue();
				String rightParentheses = advanceQueryCondition.getRightParentheses();
				String logic = advanceQueryCondition.getConditionLogic();
//				set logic
				if(ConditionLogic.AND.equals(logic)){
					logic = "and";
				}else if(ConditionLogic.OR.equals(logic)){
					logic = "or";
				}
//				append left parentheses
				conditionSQL.append(" ").append(leftParentheses).append(" ");
//				append condition
				if("0".equals(condition)){
					conditionSQL.append(" ").append(field).append(" = ? ");
					arguments.add(value);
				}else if("1".equals(condition)){
					conditionSQL.append(" ").append(field).append(" != ? ");
					arguments.add(value);
				}else if("2".equals(condition)){
					conditionSQL.append(" ").append(field).append(" like ? escape '\\' ");
					arguments.add("%"+ SqlUtils.escape(value)+"%");
				}else if("3".equals(condition)){
					conditionSQL.append(" ").append(field).append(" like ? escape '\\' ");
					arguments.add(SqlUtils.escape(value)+"%");
				}else if("4".equals(condition)){
					conditionSQL.append(" ").append(field).append(" like ? escape '\\' ");
					arguments.add("%"+SqlUtils.escape(value));
				}else if("5".equals(condition)){
					conditionSQL.append(" ").append(field).append(" > ? ");
					arguments.add(value);
				}else if("6".equals(condition)){
					conditionSQL.append(" ").append(field).append(" >= ? ");
					arguments.add(value);
				}else if("7".equals(condition)){
					conditionSQL.append(" ").append(field).append(" < ? ");
					arguments.add(value);
				}else if("8".equals(condition)){
					conditionSQL.append(" ").append(field).append(" <= ? ");
					arguments.add(value);
				}else if("9".equals(condition)){
					conditionSQL.append(" ").append(field).append(" is null ");
				}else if("10".equals(condition)){
					conditionSQL.append(" ").append(field).append(" is not null ");
				}
//				append right parentheses
				conditionSQL.append(" ").append(rightParentheses).append(" ");
//				append logic
				conditionSQL.append(" ").append(logic).append(" ");
			}
//			append
			conditionSQL.append(" ) ");
//			apply
			queryWrapper.apply(conditionSQL.toString(), arguments.toArray());
		}
//		has advance query sorts
		if(query.getAdvanceQuerySorts()!=null&&!query.getAdvanceQuerySorts().isEmpty()){
//			for each sort
			for(AdvanceQuerySort advanceQuerySort : query.getAdvanceQuerySorts()){
//				set asc
				if(SortLogic.ASC.equals(advanceQuerySort.getSortLogic()))
					queryWrapper.orderByAsc(StrUtil.toUnderlineCase(advanceQuerySort.getSortField()));
//				set desc
				if(SortLogic.DESC.equals(advanceQuerySort.getSortLogic()))
					queryWrapper.orderByDesc(StrUtil.toUnderlineCase(advanceQuerySort.getSortField()));
			}
		}
	}
	
	/**
	 * write
	 * @param request
	 * @param response
	 */
	public void write(HttpServletRequest request, HttpServletResponse response) {
//		if not export
		if(!this.getQuery().isExport()) {
//			set encoding
			response.setCharacterEncoding("UTF-8");
//			set content type
			response.setContentType("text/html;charset=UTF-8");
			try {
//				write datas
				response.getWriter().write(ObjectMapperUtils.getInstance().writeValueAsString(R.success().data("pager", this)));
			} catch (IOException e) {
				logger.error("error", e);
			}
			return;
		}
//		export
		try {
			export(request, response, false);
		} catch (Exception e) {
			logger.error("error", e);
		}
	}
	
	/**
	 * export
	 * @param request
	 * @param response
	 * @param origin
	 */
	public void export(HttpServletRequest request, HttpServletResponse response, boolean origin) {
//		set header
		response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
//		if export
		if(this.getQuery().isExport()){
//			get file name
			String fileName = "export";
			try {
				fileName = URLEncoder.encode(this.getQuery().getExportFileName(), StandardCharsets.UTF_8);
			} catch (Exception e) {
				logger.error("error", e);
			}
//			export excel
			if("excel".equals(this.getQuery().getExportType())){
				exportExcel(request, response, fileName, origin);
			}
//			export cvs
			if("csv".equals(this.getQuery().getExportType())){
				exportCsv(request, response, fileName, origin);
			}
//			export txt
			if("txt".equals(this.getQuery().getExportType())){
				exportTxt(request, response, fileName, origin);
			}
//			export pdf
			if("pdf".equals(this.getQuery().getExportType())){
				exportPdf(request, response, fileName, origin);
			}
		}
	}
	
	/**
	 * construct parameters
	 * @param params
	 */
	private void constructParameters(Map<String, String> params) {
//		for each param
		for(String key : params.keySet()){
//			get value
			String value = params.get(key);
//			if null then continue
			if(StringUtils.isEmpty(value)){
				continue;
			}
//			must have _
			if(key.contains("_")){
//				get field
				String field = key.substring(key.indexOf("_")+1);
//				convert to underline
				field = StrUtil.toUnderlineCase(field);
//				set query wrapper
				if(key.startsWith("eq_")) queryWrapper.eq(field, value);
				if(key.startsWith("ne_")) queryWrapper.ne(field, value);
				if(key.startsWith("lk_")) queryWrapper.like(field, value);
				if(key.startsWith("rl_")) queryWrapper.likeRight(field, value);
				if(key.startsWith("ll_")) queryWrapper.likeLeft(field, value);
				if(key.startsWith("in_")) queryWrapper.isNull(field);
				if(key.startsWith("inn_")) queryWrapper.isNotNull(field);
				if(key.startsWith("gt_")) queryWrapper.gt(field, value);
				if(key.startsWith("ge_")) queryWrapper.ge(field, value);
				if(key.startsWith("lt_")) queryWrapper.lt(field, value);
				if(key.startsWith("le_")) queryWrapper.le(field, value);
			}
		}
	}
	
	/**
	 * export excel
	 * @param request
	 * @param response
	 * @param fileName
	 * @param origin
	 */
	private void exportExcel(HttpServletRequest request, HttpServletResponse response, String fileName, boolean origin) {
		/**
//		get datas
		List<Map<String, Object>> exportDatas = this.getQuery().getExportDatas();
//		set context type
		response.setContentType("application/vnd.ms-excel");
//		set header
		response.setHeader("Content-Disposition", "attachment;filename="+fileName+".xls");
//		get output stream
		OutputStream outputStream = null;
		try {
			outputStream = response.getOutputStream();
		} catch (IOException e) {
			logger.error("error", e);
		}
//		get writable workbook
		WritableWorkbook book;
		try {
			book = Workbook.createWorkbook(outputStream);
		} catch (IOException e) {
			logger.error("error", e);
			return;
		}
//		create sheet
		WritableSheet sheet = book.createSheet("datas", 0);
//		freeze header
		SheetSettings settings = sheet.getSettings();
		settings.setVerticalFreeze(1);
//		defined font / cell format
		WritableFont headerFont = new WritableFont(WritableFont.createFont("Lucida Grande"), 9, WritableFont.BOLD);
		WritableFont bodyFont = new WritableFont(WritableFont.createFont("Lucida Grande"), 9, WritableFont.NO_BOLD);
		WritableCellFormat headerCellFormat = new WritableCellFormat(headerFont);
		WritableCellFormat bodyCellFormat = new WritableCellFormat(bodyFont);
		try {
//			set header cell format
			headerCellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
			headerCellFormat.setBackground(Colour.PALE_BLUE);
			headerCellFormat.setAlignment(Alignment.CENTRE);
			headerCellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
//			set body cell format
			bodyCellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
			bodyCellFormat.setAlignment(Alignment.CENTRE);
			bodyCellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
		} catch (WriteException e) {
			logger.error("error", e);
		}
//		must has export columns
		if (this.getQuery().getExportColumns()!=null&&!this.getQuery().getExportColumns().isEmpty()) {
//			add header cell
			int seq = 0;
			for (Column column : this.getQuery().getExportColumns()) {
				if(!column.isExport()){
					continue;
				}
				try {
					sheet.addCell(new Label(seq, 0, column.getLabel(), headerCellFormat));
				} catch (WriteException e) {
					logger.error("error", e);
				}
				seq++;
			}
//			has datas
			if (exportDatas!=null&&!exportDatas.isEmpty()) {
//				for each datas
				for (int i=0; i<exportDatas.size(); i++) {
//					get record
					Map<String, Object> record = exportDatas.get(i);
					seq = 0;
//					for each column
					for (int j=0; j<this.getQuery().getExportColumns().size(); j++) {
//						get column
						Column column = this.getQuery().getExportColumns().get(j);
//						must export ture
						if(!column.isExport()){
							continue;
						}
//						get content
						Object content = record.get(origin?column.getId():StrUtil.toUnderlineCase(column.getId()));
//						format content
						if(!this.getQuery().isExportDataIsProcessed()){
							content = GridUtils.formatContent(column, content);
						}
//						add body cell
						try {
							sheet.addCell(new Label(seq, i + 1, content.toString(), bodyCellFormat));
						} catch (WriteException e) {
							logger.error("error", e);
						}
						seq++;
					}
				}
			}
			try {
//				bool write & close
				book.write();
				book.close();
//				output stream flush & close
                if (outputStream != null) {
                    outputStream.flush();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (WriteException | IOException e) {
				logger.error("error", e);
			}
        }
        */
	}
	
	/**
	 * export csv
	 * @param request
	 * @param response
	 * @param fileName
	 * @param origin
	 */
	private void exportCsv(HttpServletRequest request, HttpServletResponse response, String fileName, boolean origin) {
//		get datas
		List<Map<String, Object>> exportDatas = this.getQuery().getExportDatas();
//		set content type
		response.setContentType("application/CSV");
//		set header
		response.setHeader("Content-Disposition", "attachment;filename="+fileName+".csv");
//		defined buffer
		StringBuilder buffer = new StringBuilder();
//		must has export columns
		if (this.getQuery().getExportColumns()!=null&&!this.getQuery().getExportColumns().isEmpty()) {
//			append header
			for (int i = 0; i < this.getQuery().getExportColumns().size(); i++) {
				Column column = this.getQuery().getExportColumns().get(i);
				if(!column.isExport()){
					continue;
				}
				buffer.append(column.getLabel()).append(i==(this.getQuery().getExportColumns().size()-1)?"":",");
			}
//			line
			buffer.append("\n");
//			has datas
			if (exportDatas!=null&&!exportDatas.isEmpty()) {
//				for each datas
				for (Map<String, Object> record : exportDatas) {
//					for each column
					for (int j=0; j<this.getQuery().getExportColumns().size(); j++) {
//						get column
						Column column = this.getQuery().getExportColumns().get(j);
//						must export ture
						if(!column.isExport()){
							continue;
						}
//						get content
						Object content = record.get(origin?column.getId():StrUtil.toUnderlineCase(column.getId()));
//						format content
						if(!this.getQuery().isExportDataIsProcessed()){
							content = GridUtils.formatContent(column, content);
						}
//						append body
						buffer.append("\"").append(content).append("\"").append(j==(this.getQuery().getExportColumns().size()-1)?"":",");
					}
//					line
					buffer.append("\n");
				}
			}
		}
		try {
//			write datas
			OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8);
			writer.write(new String(new byte[] { (byte) 0xEF, (byte) 0xBB,(byte) 0xBF }));
			writer.write(buffer.toString());
//			writer flush & close
			writer.flush();
			writer.close();
		} catch (IOException e) {
			logger.error("error", e);
		}
	}

	/**
	 * export txt
	 * @param request
	 * @param response
	 * @param fileName
	 * @param origin
	 */
	public void exportTxt(HttpServletRequest request, HttpServletResponse response, String fileName, boolean origin) {
//		get datas
		List<Map<String, Object>> exportDatas = this.getQuery().getExportDatas();
//		set content type
		response.setContentType("application/txt");
//		set header
		response.setHeader("Content-Disposition", "attachment;filename="+fileName+".txt");
//		defined buffer
		StringBuilder buffer = new StringBuilder();
//		must has export columns
		if (this.getQuery().getExportColumns()!=null&&!this.getQuery().getExportColumns().isEmpty()) {
//			append header
			for (int i = 0; i < this.getQuery().getExportColumns().size(); i++) {
				Column column = this.getQuery().getExportColumns().get(i);
				if(!column.isExport()){
					continue;
				}
				buffer.append(column.getLabel()).append(i==(this.getQuery().getExportColumns().size()-1)?"":"\t");
			}
//			line
			buffer.append("\r\n");
//			has datas
			if (exportDatas!=null&&!exportDatas.isEmpty()) {
//				for each datas
				for (Map<String, Object> record : exportDatas) {
//					for each column
					for (int j=0; j<this.getQuery().getExportColumns().size(); j++) {
//						get column
						Column column = this.getQuery().getExportColumns().get(j);
//						must export ture
						if(!column.isExport()){
							continue;
						}
//						get content
						Object content = record.get(origin?column.getId():StrUtil.toUnderlineCase(column.getId()));
//						format content
						if(!this.getQuery().isExportDataIsProcessed()){
							content = GridUtils.formatContent(column, content);
						}
//						append body
						buffer.append(content).append(j==(this.getQuery().getExportColumns().size()-1)?"":"\t");
					}
//					line
					buffer.append("\r\n");
				}
			}
		}
		try {
//			write datas
			OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8);
			writer.write(buffer.toString());
//			writer flush & close
			writer.flush();
			writer.close();
		} catch (IOException e) {
			logger.error("error", e);
		}
	}
	
	/**
	 * export pdf
	 * @param request
	 * @param response
	 * @param fileName
	 * @param origin
	 */
	public void exportPdf(HttpServletRequest request, HttpServletResponse response, String fileName, boolean origin) {
		/**
//		get datas
		List<Map<String, Object>> exportDatas = this.getQuery().getExportDatas();
//		set content type
		response.setContentType("application/pdf");
//		set header
		response.setHeader("Content-Disposition", "attachment;filename="+fileName+".pdf");
//		get output stream
		OutputStream outputStream = null;
		try {
			outputStream = response.getOutputStream();
		} catch (IOException e) {
			logger.error("error", e);
		}
//		create base font
		BaseFont chinese = null;
		try {
			chinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		} catch (DocumentException | IOException e) {
			logger.error("error", e);
		}
//		defined header font / body font
		Font headerFont = new Font(chinese, 9, Font.BOLD);
		Font bodyFont = new Font(chinese, 9, Font.NORMAL);
//		set page size
		Rectangle rect = new Rectangle(PageSize.A4.rotate());
//		defined document & get write
		Document document = new Document(rect);
		PdfWriter writer = null;
		try {
			writer = PdfWriter.getInstance(document, outputStream);
		} catch (DocumentException e) {
			logger.error("error", e);
		}
		if(writer==null){
			return;
		}
//		set pad version
		writer.setPdfVersion(PdfWriter.PDF_VERSION_1_2);
//		set document attr
		document.addTitle("");
		document.addAuthor("");
		document.addSubject("");
		document.addKeywords("");
		document.addCreator("");
//		set margins
		document.setMargins(0, 0, 40, 40);
//		open document
		document.open();
//		get column count
		int columnCount = this.getQuery().getExportColumns().size();
//		create table
		PdfPTable table = new PdfPTable(columnCount);
//		defined cell
		PdfPCell cell;
//		must has export columns
		if (this.getQuery().getExportColumns()!=null&&!this.getQuery().getExportColumns().isEmpty()) {
//			append header
			for (int i=0; i<this.getQuery().getExportColumns().size(); i++) {
//				get column
				Column column = this.getQuery().getExportColumns().get(i);
//				must export ture
				if(!column.isExport()){
					continue;
				}
//				add cell
				cell = new PdfPCell(new Phrase(column.getLabel(), headerFont));
				cell.setBackgroundColor(BaseColor.CYAN);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
			}
//			has datas
			if (exportDatas!=null&&!exportDatas.isEmpty()) {
//				for each datas
				for (Map<String, Object> record : exportDatas) {
//					for each column
					for (int j=0; j<this.getQuery().getExportColumns().size(); j++) {
//						get column
						Column column = this.getQuery().getExportColumns().get(j);
//						must export ture
						if(!column.isExport()){
							continue;
						}
//						get content
						Object content = record.get(origin?column.getId():StrUtil.toUnderlineCase(column.getId()));
//						format content
						if(!this.getQuery().isExportDataIsProcessed()){
							content = GridUtils.formatContent(column, content);
						}
//						add cell
						cell = new PdfPCell(new Phrase(content.toString(), bodyFont));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setVerticalAlignment(Element.ALIGN_CENTER);
						table.addCell(cell);
					}
				}
			}
			try {
//				document write & close
				document.add(table);
				document.close();
//				output stream flush & close
                if (outputStream != null) {
                    outputStream.flush();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (DocumentException | IOException e) {
				logger.error("error", e);
			}
        }
        */
	}
	
}
