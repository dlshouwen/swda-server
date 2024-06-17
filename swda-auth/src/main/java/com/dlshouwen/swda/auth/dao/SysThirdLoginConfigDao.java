package com.dlshouwen.swda.auth.dao;

import org.apache.ibatis.annotations.Mapper;

import com.dlshouwen.swda.auth.entity.SysThirdLoginConfigEntity;
import com.dlshouwen.swda.core.mapper.BaseMapper;

/**
 * 第三方登录配置
 *
 * @author 阿沐 babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Mapper
public interface SysThirdLoginConfigDao extends BaseMapper<SysThirdLoginConfigEntity> {

}