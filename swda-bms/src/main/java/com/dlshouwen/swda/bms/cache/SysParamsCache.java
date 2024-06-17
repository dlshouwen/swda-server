package com.dlshouwen.swda.bms.cache;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.dlshouwen.swda.core.cache.RedisCache;

/**
 * 参数管理 Cache
 *
 * @author 阿沐 babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Service
@AllArgsConstructor
public class SysParamsCache {
    private final RedisCache redisCache;

    /**
     * 参数管理 KEY
     */
    private final String SYSTEM_PARAMS_KEY = "system:params";

    /**
     * 根据参数键，获取参数值
     *
     * @param paramKey 参数键
     */
    public String get(String paramKey) {
        return (String) redisCache.hGet(SYSTEM_PARAMS_KEY, paramKey);
    }

    /**
     * 根据参数键，获取参数值
     *
     * @param paramKey   参数键
     * @param paramValue 参数值
     */
    public void save(String paramKey, String paramValue) {
        redisCache.hSet(SYSTEM_PARAMS_KEY, paramKey, paramValue, RedisCache.NOT_EXPIRE);
    }

    /**
     * 根据参数键，删除参数值
     *
     * @param paramKey 参数键
     */
    public void del(Object... paramKey) {
        redisCache.hDel(SYSTEM_PARAMS_KEY, paramKey);
    }
}
