package com.dlshouwen.swda.bms.platform.mapper;

import net.maku.framework.mybatis.dao.BaseDao;
import net.maku.system.entity.SysMailConfigEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 邮件配置
 *
 * @author 阿沐 babamu@126.com
 */
@Mapper
public interface EmailPlatformMapper extends BaseDao<SysMailConfigEntity> {

}