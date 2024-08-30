package com.dlshouwen.swda.bms.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.common.constant.Constant;
import com.dlshouwen.swda.core.common.exception.SwdaException;
import com.dlshouwen.swda.core.common.utils.TreeUtils;
import com.dlshouwen.swda.core.mybatis.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.bms.system.convert.OrganConvert;
import com.dlshouwen.swda.bms.system.entity.Organ;
import com.dlshouwen.swda.bms.system.entity.User;
import com.dlshouwen.swda.bms.system.mapper.OrganMapper;
import com.dlshouwen.swda.bms.system.mapper.UserMapper;
import com.dlshouwen.swda.bms.system.service.IOrganService;
import com.dlshouwen.swda.bms.system.vo.OrganVO;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * organ service impl
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Service
@AllArgsConstructor
public class OrganServiceImpl extends BaseServiceImpl<OrganMapper, Organ> implements IOrganService {
	
	/** user mapper */
	private final UserMapper userMapper;

	/**
	 * get organ list
	 * @return organ list
	 */
	@Override
	public List<OrganVO> getOrganList() {
//		defined params
		Map<String, Object> params = new HashMap<>();
//		put data scope
		params.put(Constant.DATA_SCOPE, getDataScope("o", "organ_id"));
//		get organ list
		List<Organ> organList = baseMapper.getOrganList(params);
//		build organ tree for return
		return TreeUtils.build(OrganConvert.INSTANCE.convert2VOList(organList));
	}
	
	/**
	 * get organ data
	 * @param organId
	 * @return organ data
	 */
	@Override
	public OrganVO getOrganData(Long organId) {
//		get organ
		Organ organ = this.getById(organId);
//		convert organ to vo
		OrganVO organVO = OrganConvert.INSTANCE.convert2VO(organ);
//		if has parent organ
		if(organ.getPreOrganId()!=null) {
//			get parent organ
			Organ parent = this.getById(organ.getPreOrganId());
//			set parent name
			organVO.setPreOrganName(parent.getOrganName());
		}
//		return organ
		return organVO;
	}

	/**
	 * add organ
	 * @param organVO
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addOrgan(OrganVO organVO) {
//		convert organ vo to organ
		Organ organ = OrganConvert.INSTANCE.convert(organVO);
//		insert
		this.save(organ);
	}

	/**
	 * update organ
	 * @param organVO
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateOrgan(OrganVO organVO) {
//		convert to organ
		Organ organ = OrganConvert.INSTANCE.convert(organVO);
//		if organ id equals pre organ id
		if (organ.getOrganId().equals(organ.getPreOrganId())) {
//			throw exception
			throw new SwdaException("上级机构不能为自身");
		}
//		get sub organ list
		List<Long> subOrganList = this.getSubOrganIdList(organ.getOrganId());
//		if change to upper organ
		if (subOrganList.contains(organ.getPreOrganId())) {
//			throw exception
			throw new SwdaException("上级机构不能为下级");
		}
//		update
		this.updateById(organ);
	}

	/**
	 * delete organ
	 * @param organId
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteOrgan(Long organId) {
//		get sub organ count
		long organCount = this.count(Wrappers.<Organ>lambdaQuery().eq(Organ::getPreOrganId, organId));
//		if has sub count
		if (organCount > 0) {
//			throw exception
			throw new SwdaException("请先删除子机构");
		}
//		get organ user count
		long userCount = userMapper.selectCount(Wrappers.<User>lambdaQuery().eq(User::getOrganId, organId));
//		if has user
		if (userCount > 0) {
//			throw exception
			throw new SwdaException("机构下面有用户，不能删除");
		}
//		delete
		this.removeById(organId);
	}

	/**
	 * get sub organ id list
	 * @param organId
	 * @return sub organ id list
	 */
	@Override
	public List<Long> getSubOrganIdList(Long organId) {
//		get organ list
		List<Organ> organList = baseMapper.getIdAndPidList();
//		defined sub organ id list
		List<Long> subOrganIdList = new ArrayList<>();
//		get sub organ id list
		getTree(organId, organList, subOrganIdList);
//		add self
		subOrganIdList.add(organId);
//		return sub organ id list
		return subOrganIdList;
	}

	/**
	 * get organ name list
	 * @param organIdList
	 * @return organ name list
	 */
	@Override
	public List<String> getOrganNameList(List<Long> organIdList) {
//		if organ id list is empty
		if (organIdList.isEmpty()) {
//			return null
			return null;
		}
//		get organ name list for return
		return baseMapper.selectBatchIds(organIdList).stream().map(Organ::getOrganName).toList();
	}

	/**
	 * get tree
	 * @param organId
	 * @param organList
	 * @param subOrganIdList
	 */
	private void getTree(Long organId, List<Organ> organList, List<Long> subOrganIdList) {
		for (Organ organ : organList) {
			if (ObjectUtil.equals(organ.getPreOrganId(), organId)) {
				getTree(organ.getOrganId(), organList, subOrganIdList);
				subOrganIdList.add(organ.getOrganId());
			}
		}
	}

}
