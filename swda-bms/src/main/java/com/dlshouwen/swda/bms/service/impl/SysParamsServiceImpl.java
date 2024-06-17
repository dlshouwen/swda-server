package com.dlshouwen.swda.bms.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import com.dlshouwen.swda.core.utils.JsonUtils;
import com.dlshouwen.swda.core.utils.PageResult;
import com.dlshouwen.swda.core.exception.SwdaException;
import com.dlshouwen.swda.core.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.bms.cache.SysParamsCache;
import com.dlshouwen.swda.bms.convert.SysParamsConvert;
import com.dlshouwen.swda.bms.mapper.SysParamsDao;
import com.dlshouwen.swda.bms.entity.SysParamsEntity;
import com.dlshouwen.swda.bms.query.SysParamsQuery;
import com.dlshouwen.swda.bms.service.SysParamsService;
import com.dlshouwen.swda.bms.vo.SysParamsVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 参数管理
 *
 * @author 阿沐 babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Service
@AllArgsConstructor
public class SysParamsServiceImpl extends BaseServiceImpl<SysParamsDao, SysParamsEntity> implements SysParamsService {
    private final SysParamsCache sysParamsCache;

    @Override
    public PageResult<SysParamsVO> page(SysParamsQuery query) {
        IPage<SysParamsEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));

        return new PageResult<>(SysParamsConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
    }

    private LambdaQueryWrapper<SysParamsEntity> getWrapper(SysParamsQuery query) {
        LambdaQueryWrapper<SysParamsEntity> wrapper = Wrappers.lambdaQuery();

        wrapper.like(StrUtil.isNotBlank(query.getParamKey()), SysParamsEntity::getParamKey, query.getParamKey());
        wrapper.eq(StrUtil.isNotBlank(query.getParamValue()), SysParamsEntity::getParamValue, query.getParamValue());
        wrapper.eq(query.getParamType() != null, SysParamsEntity::getParamType, query.getParamType());
        wrapper.orderByDesc(SysParamsEntity::getId);

        return wrapper;
    }

    @Override
    public void save(SysParamsVO vo) {
        // 判断 参数键 是否存在
        boolean exist = baseMapper.isExist(vo.getParamKey());
        if (exist) {
            throw new SwdaException("参数键已存在");
        }

        SysParamsEntity entity = SysParamsConvert.INSTANCE.convert(vo);

        baseMapper.insert(entity);

        // 保存到缓存
        sysParamsCache.save(entity.getParamKey(), entity.getParamValue());
    }

    @Override
    public void update(SysParamsVO vo) {
        SysParamsEntity entity = baseMapper.selectById(vo.getId());

        // 如果 参数键 修改过
        if (!StrUtil.equalsIgnoreCase(entity.getParamKey(), vo.getParamKey())) {
            // 判断 新参数键 是否存在
            boolean exist = baseMapper.isExist(vo.getParamKey());
            if (exist) {
                throw new SwdaException("参数键已存在");
            }

            // 删除修改前的缓存
            sysParamsCache.del(entity.getParamKey());
        }

        // 修改数据
        updateById(SysParamsConvert.INSTANCE.convert(vo));

        // 保存到缓存
        sysParamsCache.save(vo.getParamKey(), vo.getParamValue());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        // 查询列表
        List<SysParamsEntity> list = baseMapper.selectBatchIds(idList);

        // 删除数据
        removeByIds(idList);

        // 删除缓存
        Object[] keys = list.stream().map(SysParamsEntity::getParamKey).toArray(String[]::new);
        sysParamsCache.del(keys);
    }

    @Override
    public String getString(String paramKey) {
        String value = sysParamsCache.get(paramKey);
        if (StrUtil.isNotBlank(value)) {
            return value;
        }

        // 如果缓存没有，则查询数据库
        SysParamsEntity entity = baseMapper.get(paramKey);
        if (entity == null) {
            throw new SwdaException("参数值不存在，paramKey：" + paramKey);
        }

        // 保存到缓存
        sysParamsCache.save(entity.getParamKey(), entity.getParamValue());

        return entity.getParamValue();
    }

    @Override
    public int getInt(String paramKey) {
        String value = getString(paramKey);

        return Integer.parseInt(value);
    }

    @Override
    public boolean getBoolean(String paramKey) {
        String value = getString(paramKey);

        return Boolean.parseBoolean(value);
    }

    @Override
    public <T> T getJSONObject(String paramKey, Class<T> valueType) {
        String value = getString(paramKey);

        return JsonUtils.parseObject(value, valueType);
    }

}