package com.dlshouwen.swda.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 操作类型
 *
 * @author 阿沐 babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Getter
@AllArgsConstructor
public enum OperateType {
	/**
	 * 查询
	 */
	UNKNOW(0),
    /**
     * 查询
     */
    SEARCH(1),
    /**
     * 新增
     */
    INSERT(2),
    /**
     * 修改
     */
    UPDATE(3),
    /**
     * 删除
     */
    DELETE(4),
    /**
     * 导出
     */
    EXPORT(5),
    /**
     * 导入
     */
    IMPORT(6),
	/** 登录 */
	LOGIN(7),

	/** 登出 */
	LOGOUT(8),
    /**
     * 其它
     */
    OTHER(9);

    private final int value;
}
