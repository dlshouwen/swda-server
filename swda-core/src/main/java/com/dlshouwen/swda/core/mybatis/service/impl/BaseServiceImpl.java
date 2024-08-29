package com.dlshouwen.swda.core.mybatis.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlshouwen.swda.core.common.dict.ZeroOne;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.grid.utils.GridUtils;
import com.dlshouwen.swda.core.mybatis.interceptor.DataScope;
import com.dlshouwen.swda.core.mybatis.mapper.BaseMapper;
import com.dlshouwen.swda.core.mybatis.service.BaseService;
import com.dlshouwen.swda.core.security.user.SecurityUser;
import com.dlshouwen.swda.core.security.user.UserDetail;

import java.util.List;

/**
 * base service impl
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {
	
	/**
	 * wrapper
	 * @param query
	 * @return ipage
	 */
	protected QueryWrapper<T> wrapper(Query<T> query) {
//		construct and return wrapper
		return GridUtils.constructQueryWrapper(query);
	}
	
	/**
	 * page
	 * @param query
	 * @return ipage
	 */
	protected IPage<T> page(QueryWrapper<T> wrapper, Page<T> page) {
//		select page for return
		return baseMapper.selectPage(page, wrapper);
	}
	
	/**
	 * page
	 * @param query
	 * @return ipage
	 */
	protected IPage<T> page(Query<T> query) {
//		construct wrapper
		QueryWrapper<T> wrapper = GridUtils.constructQueryWrapper(query);
//		select page
		IPage<T> page = baseMapper.selectPage(query.getPage(), wrapper);
//		return page
		return page;
	}
	
	/**
	 * data scope wrapper
	 * @param queryWrapper
	 */
	protected void dataScopeWrapper(LambdaQueryWrapper<T> queryWrapper) {
		DataScope dataScope = getDataScope(null, null);
		if (dataScope != null) {
			queryWrapper.apply(dataScope.getSqlFilter());
		}
	}

	/**
	 * get data scrope
	 * @param tableAlias
	 * @param organIdAlias
	 * @return
	 */
	protected DataScope getDataScope(String tableAlias, String organIdAlias) {
//		get user
		UserDetail user = SecurityUser.getUser();
//		super admin then return
		if (user.getSuperAdmin().equals(ZeroOne.YES)) {
			return null;
		}
//		is null then enpty
		tableAlias = tableAlias == null ? "" : tableAlias;
//		append .
		if (StringUtils.isNotBlank(tableAlias)) {
			tableAlias += ".";
		}
//		set condition
		StringBuilder condition = new StringBuilder();
		condition.append(" (");
//		get data scope list
		List<Long> dataScopeList = user.getDataScopeList();
//		if all then return
		if (dataScopeList == null) {
			return null;
		}
//		has data scope
		if (dataScopeList.size() > 0) {
//			set organ id alias
			if (StringUtils.isBlank(organIdAlias)) {
				organIdAlias = "organ_id";
			}
//			set condition
			condition.append(tableAlias).append(organIdAlias);
			condition.append(" in (").append(StrUtil.join(",", dataScopeList)).append(")");
			condition.append(" or ");
		}
//		creator is self
		condition.append(tableAlias).append("creator").append("=").append(user.getUserId());
		condition.append(")");
//		return data scope
		return new DataScope(condition.toString());
	}

}