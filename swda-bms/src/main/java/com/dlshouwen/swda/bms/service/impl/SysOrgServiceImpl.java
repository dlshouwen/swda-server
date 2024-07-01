package com.dlshouwen.swda.bms.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import com.dlshouwen.swda.core.utils.TreeUtils;
import com.dlshouwen.swda.core.constant.Constant;
import com.dlshouwen.swda.core.exception.SwdaException;
import com.dlshouwen.swda.core.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.bms.convert.SysOrgConvert;
import com.dlshouwen.swda.bms.mapper.SysOrgDao;
import com.dlshouwen.swda.bms.mapper.SysUserDao;
import com.dlshouwen.swda.bms.entity.SysOrgEntity;
import com.dlshouwen.swda.bms.entity.SysUserEntity;
import com.dlshouwen.swda.bms.service.SysOrgService;
import com.dlshouwen.swda.bms.vo.OrganVO;
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
	 * get list
	 * @return organ list
	 */
	@Override
	public List<OrganVO> getList() {
//		defined params
		Map<String, Object> params = new HashMap<>();
//		put data scope
		params.put(Constant.DATA_SCOPE, getDataScope("t1", "id"));
//		get organ list
		List<SysOrgEntity> entityList = baseMapper.getList(params);
//		build organ tree for return
		return TreeUtils.build(SysOrgConvert.INSTANCE.convertList(entityList));
	}

	/**
	 * save
	 * @param organVO
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void save(OrganVO vo) {
//		convert organ vo to organ
		SysOrgEntity entity = SysOrgConvert.INSTANCE.convert(vo);
//		insert
		baseMapper.insert(entity);
	}

	/**
	 * update
	 * @param organVO
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(OrganVO vo) {
//		convert to organ
		SysOrgEntity entity = SysOrgConvert.INSTANCE.convert(vo);
//		if organ id equals pre organ id
		if (entity.getId().equals(entity.getPid())) {
//			throw exception
			throw new SwdaException("上级机构不能为自身");
		}
//		get sub organ list
		List<Long> subOrgList = getSubOrgIdList(entity.getId());
//		if change to upper organ
		if (subOrgList.contains(entity.getPid())) {
//			throw exception
			throw new SwdaException("上级机构不能为下级");
		}
//		update
		updateById(entity);
	}

	/**
	 * delete
	 * @param id
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(Long id) {
//		get sub organ count
		long orgCount = count(new QueryWrapper<SysOrgEntity>().eq("pid", id));
//		if has sub count
		if (orgCount > 0) {
//			throw exception
			throw new SwdaException("请先删除子机构");
		}
//		get organ user count
		long userCount = sysUserDao.selectCount(new QueryWrapper<SysUserEntity>().eq("org_id", id));
//		if has user
		if (userCount > 0) {
//			throw exception
			throw new SwdaException("机构下面有用户，不能删除");
		}
//		delete
		removeById(id);
	}

	/**
	 * get sub organ id list
	 * @param id
	 * @return sub organ id list
	 */
	@Override
	public List<Long> getSubOrgIdList(Long id) {
//		get organ list
		List<SysOrgEntity> orgList = baseMapper.getIdAndPidList();
//		defined sub id list
		List<Long> subIdList = new ArrayList<>();
//		get sub id list
		getTree(id, orgList, subIdList);
//		add self
		subIdList.add(id);
//		return sub id list
		return subIdList;
	}

	/**
	 * get name list
	 * @param id list
	 * @return name list
	 */
	@Override
	public List<String> getNameList(List<Long> idList) {
//		if id list is empty
		if (idList.isEmpty()) {
//			return null
			return null;
		}
//		get name list for return
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
				getTree(org.getId(), orgList, subIdList);

				subIdList.add(org.getId());
			}
		}
	}

}
