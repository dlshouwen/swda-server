package com.dlshouwen.swda.bms.system.provider;

/**
 * region service
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public class RegionProvider {

    public String getRedisData(Integer regionId) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from bms_region where region_id=#{regionId}");
        return sql.toString();
    }

}
