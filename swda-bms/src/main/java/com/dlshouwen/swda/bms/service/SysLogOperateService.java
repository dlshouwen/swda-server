package com.dlshouwen.swda.bms.service;

import com.dlshouwen.swda.core.entity.base.PageResult;
import com.dlshouwen.swda.core.service.BaseService;
import com.dlshouwen.swda.bms.entity.SysLogOperateEntity;
import com.dlshouwen.swda.bms.query.SysLogOperateQuery;
import com.dlshouwen.swda.bms.vo.SysLogOperateVO;

/**
 * 操作日志
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface SysLogOperateService extends BaseService<SysLogOperateEntity> {

	PageResult<SysLogOperateVO> page(SysLogOperateQuery query);
}