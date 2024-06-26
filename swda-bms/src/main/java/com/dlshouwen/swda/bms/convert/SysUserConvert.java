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
 * user convert
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface SysUserConvert {
	
	/** instance */
	SysUserConvert INSTANCE = Mappers.getMapper(SysUserConvert.class);

	/**
	 * convert
	 * @param user
	 * @return user vo
	 */
	SysUserVO convert(SysUserEntity entity);

	/**
	 * convert
	 * @param userVO
	 * @return user
	 */
	SysUserEntity convert(SysUserVO vo);

	/**
	 * convert
	 * @param userBaseVO
	 * @return user
	 */
	SysUserEntity convert(SysUserBaseVO vo);

	/**
	 * convert
	 * @param userDetail
	 * @return user vo
	 */
	SysUserVO convert(UserDetail userDetail);

	/**
	 * convert detail
	 * @param user
	 * @return user detail
	 */
	UserDetail convertDetail(SysUserEntity entity);

	/**
	 * convert list
	 * @param userList
	 * @return user vo list
	 */
	List<SysUserVO> convertList(List<SysUserEntity> list);

	/**
	 * convert 2 list
	 * @param userList
	 * @return user excel vo list
	 */
	List<SysUserExcelVO> convert2List(List<SysUserEntity> list);

	/**
	 * convert list
	 * @param userExcelVOList
	 * @return user list
	 */
	List<SysUserEntity> convertListEntity(List<SysUserExcelVO> list);

}