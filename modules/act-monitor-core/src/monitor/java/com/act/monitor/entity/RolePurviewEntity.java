package com.act.monitor.entity;

import com.act.framework.entity.StandardEntity;
import com.act.mapper.annotation.ColumnTitle;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Table(name = "tab_user_role_purview")
public class RolePurviewEntity extends StandardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty
    @Length(max=20)
    @Column(name = "user_id")
    private String userId;

    /**
     * 权限类型 2-超级管理员 3-全国管理员 4-IDC账号
     */
    @NotEmpty
    @Column(name = "role_id")
    @ColumnTitle("权限类型")
    private int roleId;

    public static final String Property_Id = "id";

    public static final String Property_UserId = "userId";

    public static final String Property_RoleId = "roleId";

    /**
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(int id) {
        addChangeField("id",this.id,id);
        this.id = id;
    }

    /**
     * @return user_id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(String userId) {
        addChangeField("userId",this.userId,userId);
        this.userId = userId;
    }

    /**
     * 获取权限类型 2-超级管理员 3-全国管理员 4-IDC账号
     *
     * @return role_id - 权限类型 2-超级管理员 3-全国管理员 4-IDC账号
     */
    public int getRoleId() {
        return roleId;
    }

    /**
     * 设置权限类型 2-超级管理员 3-全国管理员 4-IDC账号
     *
     * @param roleId 权限类型 2-超级管理员 3-全国管理员 4-IDC账号
     */
    public void setRoleId(int roleId) {
        addChangeField("roleId",this.roleId,roleId);
        this.roleId = roleId;
    }
}