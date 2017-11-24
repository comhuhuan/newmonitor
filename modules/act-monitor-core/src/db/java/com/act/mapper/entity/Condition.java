package com.act.mapper.entity;

import com.act.framework.entity.BaseEntity;



/**
 * Condition - 条件查询，命名就是任性
 *
 * @author liuzh
 * @since 2015-06-10
 */
public class Condition extends Example {
    public Condition(Class<? extends BaseEntity> entityClass) {
        super(entityClass);
    }

    public Condition(Class<? extends BaseEntity> entityClass, boolean exists) {
        super(entityClass, exists);
    }
}
