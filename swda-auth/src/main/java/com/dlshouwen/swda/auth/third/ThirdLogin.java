package com.dlshouwen.swda.auth.third;

import lombok.Data;

import java.io.Serializable;

/**
 * 第三方登录 表单数据
 *
 * @author 阿沐 babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
public class ThirdLogin implements Serializable {
    private static final long serialVersionUID = 1L;

	/**
     * 开放平台类型
     */
    private String openType;

    /**
     * 开放平台Code
     */
    private String code;

    /**
     * state
     */
    private String state;
}
