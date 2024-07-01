package com.dlshouwen.swda.bms.convert;

import com.dlshouwen.swda.bms.entity.User;
import com.dlshouwen.swda.bms.vo.UserAssistVO;
import com.dlshouwen.swda.bms.vo.SysUserExcelVO;
import com.dlshouwen.swda.bms.vo.UserVO;
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
	UserVO convert(User entity);

	/**
	 * convert
	 * @param userVO
	 * @return user
	 */
	User convert(UserVO vo);

	/**
	 * convert
	 * @param userBaseVO
	 * @return user
	 */
	User convert(UserAssistVO vo);

	/**
	 * convert
	 * @param userDetail
	 * @return user vo
	 */
	UserVO convert(UserDetail userDetail);

	/**
	 * convert detail
	 * @param user
	 * @return user detail
	 */
	UserDetail convertDetail(User entity);

	/**
	 * convert list
	 * @param userList
	 * @return user vo list
	 */
	List<UserVO> convertList(List<User> list);

	/**
	 * convert 2 list
	 * @param userList
	 * @return user excel vo list
	 */
	List<SysUserExcelVO> convert2List(List<User> list);

	/**
	 * convert list
	 * @param userExcelVOList
	 * @return user list
	 */
	List<User> convertListEntity(List<SysUserExcelVO> list);

}