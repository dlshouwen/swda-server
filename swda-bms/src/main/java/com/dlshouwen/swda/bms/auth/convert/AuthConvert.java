package com.dlshouwen.swda.bms.auth.convert;

import com.dlshouwen.swda.bms.auth.entity.Auth;
import com.dlshouwen.swda.bms.auth.vo.AuthVO;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * auth convert
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthConvert {
	
	/** instance */
	AuthConvert INSTANCE = Mappers.getMapper(AuthConvert.class);

	/**
	 * convert
	 * @param authVO
	 * @return auth
	 */
	Auth convert(AuthVO vo);

	/**
	 * convert to vo
	 * @param auth
	 * @return auth vo
	 */
	AuthVO convert2VO(Auth entity);

	/**
	 * convert to vo list
	 * @param authList
	 * @return auth vo list
	 */
	List<AuthVO> convert2VOList(List<Auth> list);

}