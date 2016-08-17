package com.candao.member.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * tb_data_dictionary表
 * @author mew
 *
 */
@SuppressWarnings("serial")
public class TbDataDictionary implements Serializable {
	
	private java.lang.String id; // 类别编号
	private java.lang.String code_id; // 项目编号
	private java.lang.String code_desc; // 项目描述
	private java.lang.Integer code_sort; // 项目排序
	private java.lang.Integer status; // 状态 1：有效  0：无效
	private java.lang.String type; // 分类
	private java.lang.String typename; //分类名称 
	
	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	public java.lang.String getCode_id() {
		return code_id;
	}

	public void setCode_id(java.lang.String code_id) {
		this.code_id = code_id;
	}


	public java.lang.Integer getStatus() {
		return status;
	}

	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}

	public java.lang.String getType() {
		return type;
	}

	public void setType(java.lang.String type) {
		this.type = type;
	}

	public java.lang.String getTypename() {
		return typename;
	}

	public void setTypename(java.lang.String typename) {
		this.typename = typename;
	}
	

	public java.lang.String getCode_desc() {
		return code_desc;
	}

	public void setCode_desc(java.lang.String code_desc) {
		this.code_desc = code_desc;
	}

	public java.lang.Integer getCode_sort() {
		return code_sort;
	}

	public void setCode_sort(java.lang.Integer code_sort) {
		this.code_sort = code_sort;
	}

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("TbDataDictionary");
        sb.append("{id=").append(id);
        sb.append(", code_id=").append(code_id);
        sb.append(", item_desc=").append(code_desc);
        sb.append(", item_sort=").append(code_sort);
        sb.append(", status=").append(status);
        sb.append(", type=").append(type);
        sb.append(", typename=").append(typename);
		sb.append('}');
        return sb.toString();
    }
}