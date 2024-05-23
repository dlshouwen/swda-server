package com.dlshouwen.swda.utils;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.dlshouwen.swda.entity.grid.Column;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * grid utils
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
public class GridUtils<T> {
	
	/**
	 * format content
	 * @param column
	 * @param content
	 * @return result
	 * @throws Exception
	 */
	public static String formatContent(Column column, Object content){
		try{
//			if dict
			if(column.getDict()!=null){
				if(column.getDict().containsKey((String)content)){
					return MapUtil.getStr(column.getDict(), content);
				}
			}
//			if date
			if("date".equalsIgnoreCase(column.getType())&&!StrUtil.isEmpty(column.getFormat())){
				if(content instanceof String) {
					if(!StrUtil.isEmpty(column.getOtype())){
						if("time_stamp_s".equals(column.getOtype())){
							SimpleDateFormat sdf = new SimpleDateFormat(column.getFormat());
							Date date = new Date(Long.parseLong(content.toString())*1000);
							return sdf.format(date);
						}else if("time_stamp_ms".equals(column.getOtype())){
							SimpleDateFormat sdf = new SimpleDateFormat(column.getFormat());
							Date date = new Date(Integer.parseInt(content.toString()));
							return sdf.format(date);
						}else if("string".equals(column.getOtype())){
							if(!StrUtil.isEmpty(column.getOformat())){
								SimpleDateFormat _sdf = new SimpleDateFormat(column.getOformat());
								SimpleDateFormat sdf = new SimpleDateFormat(column.getFormat());
								Date date = _sdf.parse(content.toString());
								return sdf.format(date);
							}
						}
					}
				}
				if(content instanceof Integer || content instanceof Double || content instanceof Long) {
					Date date = new Date(Long.parseLong(content.toString())*1000);
					SimpleDateFormat sdf = new SimpleDateFormat(column.getFormat());
					return sdf.format(date);
				}
				if(content instanceof Date) {
					SimpleDateFormat sdf = new SimpleDateFormat(column.getFormat());
					return sdf.format((Date)content);
				}
				if(content instanceof LocalDateTime) {
					SimpleDateFormat sdf = new SimpleDateFormat(column.getFormat());
					return sdf.format(content);
				}
			}else if("number".equalsIgnoreCase(column.getType())&&!StrUtil.isEmpty(column.getFormat())){
				DecimalFormat df = new DecimalFormat(column.getFormat());
				content = df.format(content);
			}
		}catch(Exception ignore){
		}
//		return
		return content==null?"":content.toString();
	}
	
}