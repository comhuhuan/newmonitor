package com.act.framework.entity;


/**
 * 含有创建人/创建时间/更新人/更新时间标准字段的实体
 * @author james
 *
 */
public class StandardEntity extends BaseEntity{
	
	

    public void insert() {
		super.insert();
	}
	
	public boolean update() {
		return super.update();
	}
	
	//是否记录审计日志
	protected boolean isAudit(){
		return true;
	}
	
}
