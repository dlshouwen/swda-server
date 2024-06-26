package com.dlshouwen.swda.auth.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dlshouwen.swda.auth.convert.SysOrgConvert;
import com.dlshouwen.swda.auth.dao.SysOrgDao;
import com.dlshouwen.swda.auth.dao.SysUserDao;
import com.dlshouwen.swda.auth.entity.SysOrgEntity;
import com.dlshouwen.swda.auth.entity.SysUserEntity;
import com.dlshouwen.swda.auth.service.SysOrgService;
import com.dlshouwen.swda.auth.vo.SysOrgVO;
import com.dlshouwen.swda.core.constant.Constant;
import com.dlshouwen.swda.core.exception.SwdaException;
import com.dlshouwen.swda.core.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.core.utils.TreeUtils;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * organ service impl
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class SysOrgServiceImpl extends BaseServiceImpl<SysOrgDao, SysOrgEntity> implements SysOrgService {

	/** user mapper */
	private final SysUserDao sysUserDao;

	/**
	 * get organ list
	 * @return organ vo list
	 */
	@Override
	public List<SysOrgVO> getList() {
//		defined params
		Map<String, Object> params = new HashMap<>();
//		put data scope
		params.put(Constant.DATA_SCOPE, getDataScope("t1", "id"));
//		get organ list
		List<SysOrgEntity> entityList = baseMapper.getList(params);
//		convert to organ vo tree
		return TreeUtils.build(SysOrgConvert.INSTANCE.convertList(entityList));
	}

	/**
	 * save
	 * @param organVO
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void save(SysOrgVO vo) {
//		convert to organ
		SysOrgEntity entity = SysOrgConvert.INSTANCE.convert(vo);
//		insert organ
		baseMapper.insert(entity);
	}

	/**
	 * update
	 * @param organVO
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(SysOrgVO vo) {
//		convert to organ
		SysOrgEntity entity = SysOrgConvert.INSTANCE.convert(vo);
//		parent is not self
		if (entity.getOrganId().equals(entity.getPid())) {
			throw new SwdaException("上级机构不能为自身");
		}
//		parent is not sub
		List<Long> subOrgList = getSubOrgIdList(entity.getOrganId());
		if (subOrgList.contains(entity.getPid())) {
			throw new SwdaException("上级机构不能为下级");
		}
//		update organ
		updateById(entity);
	}

	/**
	 * delete
	 * @param organId
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(Long id) {
//		get sub organ count
		long orgCount = count(new QueryWrapper<SysOrgEntity>().eq("pid", id));
//		if has sub organs
		if (orgCount > 0) {
			throw new SwdaException("请先删除子机构");
		}
//		get user count
		long userCount = sysUserDao.selectCount(new QueryWrapper<SysUserEntity>().eq("org_id", id));
//		if has users
		if (userCount > 0) {
			throw new SwdaException("机构下面有用户，不能删除");
		}
//		delete organ
		removeById(id);
	}

	/**
	 * get sub organ id list
	 * @param organId
	 * @return sub organ id list
	 */
	@Override
	public List<Long> getSubOrgIdList(Long id) {
//		get id and pid list
		List<SysOrgEntity> orgList = baseMapper.getIdAndPidList();
//		defined sub id list
		List<Long> subIdList = new ArrayList<>();
//		get sub id list
		getTree(id, orgList, subIdList);
//		add self id
		subIdList.add(id);
//		return sub id list
		return subIdList;
	}

	/**
	 * get name list
	 * @param idList
	 * @return name list
	 */
	@Override
	public List<String> getNameList(List<Long> idList) {
//		if empty then return null
		if (idList.isEmpty()) {
			return null;
		}
//		select ids by name
		return baseMapper.selectBatchIds(idList).stream().map(SysOrgEntity::getName).toList();
	}

	/**
	 * get tree
	 * @param id
	 * @param orgList
	 * @param subIdList
	 */
	private void getTree(Long id, List<SysOrgEntity> orgList, List<Long> subIdList) {
		for (SysOrgEntity org : orgList) {
			if (ObjectUtil.equals(org.getPid(), id)) {
				getTree(org.getOrganId(), orgList, subIdList);

				subIdList.add(org.getOrganId());
			}
		}
	}

}
