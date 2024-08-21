package com.htz.core.extra.unique.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.collections.MapUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.htz.core.config.CONFIG;
import com.htz.core.utils.ObjectMapperUtils;
import com.htz.core.utils.SpringUtils;

/**
 * 验证唯一
 * @author liujingcheng@live.cn
 * @since hygea 6.0
 */
@Controller
@RequestMapping("/core/extra/unique")
@SuppressWarnings({"deprecation", "unchecked"})
public class UniqueController {
	
	/**
	 * 执行唯一校验
	 * @param request 请求对象
	 * @param response 响应对象
	 * @return 验证结果，成功为true 失败为false
	 * @throws Exception 抛出全部异常
	 */
	@RequestMapping(value="", method=RequestMethod.POST)
	public void unique(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		获取参数
		String validParams = request.getParameter("validParams");
		Map<String, Object> validParamsInfo = ObjectMapperUtils.getInstance().readValue(validParams, Map.class);
		String sqlCode = MapUtils.getString(validParamsInfo, "sqlCode");
		String key = MapUtils.getString(validParamsInfo, "key");
		List<Object> attrs = (List<Object>)MapUtils.getObject(validParamsInfo, "attrs");
//		获取sql
		String sql = MapUtils.getString(CONFIG.UNIQUE, sqlCode);
//		执行查询结果集，结果集应该是一个数值
		JdbcTemplate template = new JdbcTemplate((DataSource)SpringUtils.getBean(CONFIG.DEFAULT_DATA_SOURCE));
//		获取参数列表
		int count;
		if(attrs==null||attrs.size()==0){
			count = template.queryForInt(sql, key);
		}else{
			attrs.add(0, key);
			count = template.queryForObject(sql, attrs.toArray(), Integer.class);
		}
//		回写数据
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(String.valueOf(count==0?true:false));
	}

}
