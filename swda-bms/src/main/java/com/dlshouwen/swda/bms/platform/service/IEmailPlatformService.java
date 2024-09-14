package com.dlshouwen.swda.bms.platform.service;

import java.util.List;

import com.dlshouwen.swda.bms.platform.entity.EmailPlatform;
import com.dlshouwen.swda.core.mybatis.service.BaseService;

/**
 * 邮件平台
 *
 * @author 阿沐 babamu@126.com
 */
public interface IEmailPlatformService extends BaseService<EmailPlatform> {

    PageResult<EmailPlatformVO> page(SysMailConfigQuery query);

    List<EmailPlatformVO> list(Integer platform);

    /**
     * 启用的邮件平台列表
     */
    List<EmailPlatform> listByEnable();

    void save(EmailPlatformVO vo);

    void update(EmailPlatformVO vo);

    void delete(List<Long> idList);
}