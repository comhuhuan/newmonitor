package com.act.framework.entity;

public interface EntityListener {
	public void preSave(BaseEntity entity, boolean isInsert);
	public void postSave(BaseEntity entity, boolean isInsert);
	public void preDelete(BaseEntity entity);
	public void postDelete(BaseEntity entity);
}
