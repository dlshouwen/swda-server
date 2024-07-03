package com.dlshouwen.swda.bms.service.impl;

import lombok.AllArgsConstructor;
import com.dlshouwen.swda.core.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.bms.cache.AttrCache;
import com.dlshouwen.swda.bms.convert.AttrConvert;
import com.dlshouwen.swda.bms.mapper.AttrMapper;
import com.dlshouwen.swda.bms.entity.Attr;
import com.dlshouwen.swda.bms.service.IAttrService;
import com.dlshouwen.swda.bms.vo.AttrVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * attr service impl
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class AttrServiceImpl extends BaseServiceImpl<AttrMapper, Attr> implements IAttrService {
	
	/** attr cache */
	private final AttrCache attrCache;

	/**
	 * get attr list
	 * @return attr list
	 */
	@Override
	public List<AttrVO> getAttrList() {
//		get attr list
		List<Attr> attrList = this.list();
//		return attr list
		return AttrConvert.INSTANCE.convert2VOList(attrList);
	}

	/**
	 * save attr list
	 * @param attrList
	 */
	@Override
	public void saveAttrList(List<AttrVO> attrList) {
//		convert to attr list
		this.updateBatchById(AttrConvert.INSTANCE.convertList(attrList));
//		for each attr
		for(AttrVO attr : attrList) {
			attrCache.save(attr.getAttrId(), attr.getContent());
		}
	}
	
	/**
	 * get value
	 * @param attrId
	 * @return value
	 */
	@Override
	public String getValue(String attrId) {
//		get attr
		Attr attr = this.getById(attrId);
//		return content
		return attr.getContent();
	}

}