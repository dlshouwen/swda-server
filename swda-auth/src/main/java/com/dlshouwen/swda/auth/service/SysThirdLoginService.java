package com.dlshouwen.swda.auth.service;

import java.util.List;

import com.dlshouwen.swda.auth.entity.SysThirdLoginEntity;
import com.dlshouwen.swda.auth.vo.SysThirdLoginVO;
import com.dlshouwen.swda.core.service.BaseService;

import me.zhyd.oauth.model.AuthUser;

/**
 * 第三方登录
 *
 * @author 阿沐 babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
public interface SysThirdLoginService extends BaseService<SysThirdLoginEntity> {

    List<SysThirdLoginVO> listByUserId(Long userId);

    void unBind(Long userId, String openType);

    void bind(Long userId, String openType, AuthUser authUser);

    /**
     * 根据第三方登录类型和openId，查询用户Id
     *
     * @param openType 第三方登录类型
     * @param openId   第三方用户唯一标识
     * @return 用户Id
     */
    Long getUserIdByOpenTypeAndOpenId(String openType, String openId);

}