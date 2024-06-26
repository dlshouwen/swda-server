package com.dlshouwen.swda.bms.convert;

import com.dlshouwen.swda.bms.entity.SysUserEntity;
import com.dlshouwen.swda.bms.vo.SysUserBaseVO;
import com.dlshouwen.swda.bms.vo.SysUserExcelVO;
import com.dlshouwen.swda.bms.vo.SysUserVO;
import com.dlshouwen.swda.core.entity.auth.UserDetail;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface SysUserConvert {
	SysUserConvert INSTANCE = Mappers.getMapper(SysUserConvert.class);

	SysUserVO convert(SysUserEntity entity);

	SysUserEntity convert(SysUserVO vo);

	SysUserEntity convert(SysUserBaseVO vo);

	SysUserVO convert(UserDetail userDetail);

	UserDetail convertDetail(SysUserEntity entity);

	List<SysUserVO> convertList(List<SysUserEntity> list);

	List<SysUserExcelVO> convert2List(List<SysUserEntity> list);

	List<SysUserEntity> convertListEntity(List<SysUserExcelVO> list);

}
