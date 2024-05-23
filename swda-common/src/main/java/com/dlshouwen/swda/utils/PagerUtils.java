package com.dlshouwen.swda.utils;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dlshouwen.swda.extra.spring.mapper.ClassRowMapper;

import com.dlshouwen.swda.entity.grid.Pager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * pager utils
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
public class PagerUtils {

	/**
	 * query
	 * @param baseMapper
	 * @param pager
	 */
	public static <T> void query(BaseMapper<T> baseMapper, Pager<T> pager) {
//		construct query wrapper
		pager.constructQueryWrapper();
//		select page
		baseMapper.selectPage(pager.getPage(), pager.getQueryWrapper());
//		set previous & next
		pager.setHasPrevious(pager.getPage().hasPrevious());
		pager.setHasNext(pager.getPage().hasPrevious());
	}

	/**
	 * query
	 * @param _class
	 * @param pager
	 * @param template
	 * @param sql
	 * @param args
	 */
	@SuppressWarnings("unchecked")
	public static <T> void query(Class<T> _class, Pager<T> pager, JdbcTemplate template, String sql, Object... args){
//		construct query wrapper
		pager.constructQueryWrapper();
//		defined execute sql
		String executeSQL = "select * from ("+sql+") _swda_query_table_alisa_ ";
//		construct target sql
		if(!StringUtils.isEmpty(pager.getQueryWrapper().getTargetSql())) {
			if(pager.getQueryWrapper().getTargetSql().trim().toLowerCase().startsWith("order by")) {
				executeSQL += " "+pager.getQueryWrapper().getTargetSql();
			}else {
				executeSQL += "where "+pager.getQueryWrapper().getTargetSql();
			}
		}
//		if export & export all
		if(pager.getQuery().isExport()&&pager.getQuery().isExportAllData()){
			pager.getQuery().setExportDatas(template.queryForList(executeSQL, args));
			return;
		}
//		handle execute args
		List<Object> executeArgs = new ArrayList<>();
		Collections.addAll(executeArgs, args);
		List<Object> queryArgs = new ArrayList<>();
		for(String key : pager.getQueryWrapper().getParamNameValuePairs().keySet()){
			queryArgs.add(0, pager.getQueryWrapper().getParamNameValuePairs().get(key));
		}
		if(!queryArgs.isEmpty()) {
			executeArgs.addAll(queryArgs);
		}
//		query total & set to pager
		String countSQL = "select count(*) from ("+executeSQL+") _swda_query_count_table_alisa_ ";
		Long total = template.queryForObject(countSQL, Long.class, executeArgs.toArray());
		total = total==null?0:total;
		pager.getPage().setTotal(total);
//		append page sql
		executeSQL += " limit ?, ?";
		executeArgs.add((pager.getPage().getCurrent()-1)*pager.getPage().getSize());
		executeArgs.add(pager.getPage().getSize());
//		if export & not export all
		if(pager.getQuery().isExport()&&!pager.getQuery().isExportAllData()){
			pager.getQuery().setExportDatas(template.queryForList(executeSQL, executeArgs.toArray()));
			return;
		}
//		set pages
		pager.getPage().setPages((total+pager.getPage().getSize()-1)/pager.getPage().getSize());
//		query & set datas
		if(_class == Map.class){
			List<Map<String, Object>> records = template.queryForList(executeSQL, executeArgs.toArray());
			//noinspection unchecked
			pager.getPage().setRecords((List<T>)records);
		}else{
			List<T> records = template.query(executeSQL, new ClassRowMapper<>(_class), executeArgs.toArray());
			pager.getPage().setRecords(records);
		}
//		set previous & next
		pager.setHasPrevious(pager.getPage().hasPrevious());
		pager.setHasNext(pager.getPage().hasPrevious());
	}

}
