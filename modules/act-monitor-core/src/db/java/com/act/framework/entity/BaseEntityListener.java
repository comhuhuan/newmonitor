package com.act.framework.entity;

public class BaseEntityListener implements EntityListener {

	@Override
	public void preSave(BaseEntity entity, boolean isInsert) {
	}

	@Override
	public void postSave(BaseEntity entity, boolean isInsert) {
	}

	@Override
	public void preDelete(BaseEntity entity) {
	}

	@Override
	public void postDelete(BaseEntity entity) {
	}

}
