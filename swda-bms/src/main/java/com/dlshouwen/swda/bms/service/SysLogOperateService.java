package com.dlshouwen.swda.bms.service;

import com.dlshouwen.swda.core.entity.base.PageResult;
import com.dlshouwen.swda.core.service.BaseService;
import com.dlshouwen.swda.bms.entity.SysLogOperateEntity;
import com.dlshouwen.swda.bms.query.SysLogOperateQuery;
import com.dlshouwen.swda.bms.vo.SysLogOperateVO;

/**
 * 操作日志
 *
 * @author 阿沐 babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
public interface SysLogOperateService extends BaseService<SysLogOperateEntity> {

    PageResult<SysLogOperateVO> page(SysLogOperateQuery query);
}