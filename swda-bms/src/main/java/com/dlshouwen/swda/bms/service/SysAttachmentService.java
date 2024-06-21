package com.dlshouwen.swda.bms.service;

import com.dlshouwen.swda.core.entity.base.PageResult;
import com.dlshouwen.swda.core.service.BaseService;
import com.dlshouwen.swda.bms.entity.SysAttachmentEntity;
import com.dlshouwen.swda.bms.query.SysAttachmentQuery;
import com.dlshouwen.swda.bms.vo.SysAttachmentVO;

import java.util.List;

/**
 * 附件管理
 *
 * @author 阿沐 babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
public interface SysAttachmentService extends BaseService<SysAttachmentEntity> {

    PageResult<SysAttachmentVO> page(SysAttachmentQuery query);

    void save(SysAttachmentVO vo);

    void update(SysAttachmentVO vo);

    void delete(List<Long> idList);
}