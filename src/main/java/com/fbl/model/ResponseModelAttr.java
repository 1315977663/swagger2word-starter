package com.fbl.model;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * 返回属性
 * @author kevin
 *
 */
@Data
public class ResponseModelAttr implements Serializable{
	
	private static final long serialVersionUID = -4074067438450613643L;
	
	/**
	 * 类型
	 */
	private String className = StringUtils.EMPTY;
	
	
	/**
	 * 属性
	 */
	private String value = StringUtils.EMPTY;
	
	/**
	 * 类型
	 */
	private String type = StringUtils.EMPTY;
	
	/**
	 * 属性名称/说明
	 */
	private String name;
}
