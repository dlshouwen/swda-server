package com.dlshouwen.swda.injector;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;

import cn.hutool.core.collection.CollectionUtil;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import java.util.List;
import java.util.stream.Collectors;

/**
 * logic delete by id with fill
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
public class LogicDeleteByIdWithFill extends AbstractMethod {

	protected LogicDeleteByIdWithFill(String methodName) {
		super(methodName);
	}

	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * inject mapped statement
	 * @param mapperClass
	 * @param modelClass
	 * @param tableInfo
	 * @return mapped statement
	 */
	@Override
	public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
//		defined sql
		String sql;
//		get sql method
		SqlMethod sqlMethod = SqlMethod.LOGIC_DELETE_BY_ID;
//		if with logic delete
		if (tableInfo.isWithLogicDelete()) {
//			get field infos
			List<TableFieldInfo> fieldInfos = tableInfo.getFieldList().stream().filter(TableFieldInfo::isWithUpdateFill).collect(Collectors.toList());
//			has field infos
			if (CollectionUtil.isNotEmpty(fieldInfos)) {
//				set sql set
				String sqlSet = "set "+fieldInfos.stream().map(i->i.getSqlSet(EMPTY)).collect(Collectors.joining(EMPTY))+tableInfo.getLogicDeleteSql(false, false);
//				format sql
				sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), sqlSet, tableInfo.getKeyColumn(), tableInfo.getKeyProperty(), tableInfo.getLogicDeleteSql(true, true));
			} else {
//				format sql
				sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), sqlLogicSet(tableInfo), tableInfo.getKeyColumn(), tableInfo.getKeyProperty(), tableInfo.getLogicDeleteSql(true, true));
			}
		} else {
//			set sql method
			sqlMethod = SqlMethod.DELETE_BY_ID;
//			format sql
			sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), tableInfo.getKeyColumn(), tableInfo.getKeyProperty());
		}
//		create sql source
		SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
//		return mapped statement
		return addUpdateMappedStatement(mapperClass, modelClass, getMethod(sqlMethod), sqlSource);
	}

	/**
	 * get mthod
	 * @param sqlMethod
	 * @return method
	 */
	public String getMethod(SqlMethod sqlMethod) {
		return "deleteByIdWithFill";
	}

}
