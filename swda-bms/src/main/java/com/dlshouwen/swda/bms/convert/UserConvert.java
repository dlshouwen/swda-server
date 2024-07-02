package com.dlshouwen.swda.bms.convert;

import com.dlshouwen.swda.bms.entity.User;
import com.dlshouwen.swda.bms.vo.UserAssistVO;
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
public interface UserConvert {
	
	/** instance */
	UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

	/**
	 * convert
	 * @param userVO
	 * @return user
	 */
	User convert(UserVO vo);

	/**
	 * convert to vo
	 * @param user
	 * @return user vo
	 */
	UserVO convert2VO(User entity);

	/**
	 * convert
	 * @param userAssistVO
	 * @return user
	 */
	User convert(UserAssistVO vo);

	/**
	 * convert to vo
	 * @param userDetail
	 * @return user vo
	 */
	UserVO convert2VO(UserDetail userDetail);

	/**
	 * convert to detail
	 * @param user
	 * @return user detail
	 */
	UserDetail convert2Detail(User entity);

	/**
	 * convert to vo list
	 * @param userList
	 * @return user vo list
	 */
	List<UserVO> convert2VOList(List<User> list);

}