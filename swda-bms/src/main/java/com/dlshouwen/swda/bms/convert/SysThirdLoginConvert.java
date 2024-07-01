package com.dlshouwen.swda.bms.convert;

import com.dlshouwen.swda.bms.entity.Auth;
import com.dlshouwen.swda.bms.vo.AuthLoginVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * third login convert
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface SysThirdLoginConvert {
	
	/** instance */
	SysThirdLoginConvert INSTANCE = Mappers.getMapper(SysThirdLoginConvert.class);

	/**
	 * convert
	 * @param thirdLoginVO
	 * @return third login
	 */
	Auth convert(AuthLoginVO vo);

	/**
	 * convert
	 * @param thirdLogin
	 * @return third login vo
	 */
	AuthLoginVO convert(Auth entity);

	/**
	 * convert list
	 * @param thirdLoginList
	 * @return third login vo list
	 */
	List<AuthLoginVO> convertList(List<Auth> list);

}