package com.dlshouwen.swda.auth.service;

import me.zhyd.oauth.request.AuthRequest;

import java.util.List;

import com.dlshouwen.swda.auth.entity.SysThirdLoginConfigEntity;
import com.dlshouwen.swda.auth.vo.SysThirdLoginConfigVO;
import com.dlshouwen.swda.core.service.BaseService;

/**
 * 第三方登录配置
 *
 * @author 阿沐 babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
public interface SysThirdLoginConfigService extends BaseService<SysThirdLoginConfigEntity> {

    void save(SysThirdLoginConfigVO vo);

    void update(SysThirdLoginConfigVO vo);

    void delete(List<Long> idList);

    /**
     * 根据类型，获取授权请求
     *
     * @param openType 第三方登录类型
     */
    AuthRequest getAuthRequest(String openType);
}