package com.act.monitor.entity;

import com.act.framework.entity.StandardEntity;
import com.act.mapper.annotation.ColumnTitle;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "tab_sys_user")
public class TabSysUserEntity extends StandardEntity {
    @Id
    @NotEmpty
    @Length(max=20)
    @Column(name = "user_id")
    private String userId;

    @NotEmpty
    @Length(max=50)
    private String password;

    @NotEmpty
    @Length(max=30)
    private String username;

    @NotEmpty
    @Length(max=20)
    private String tel;

    @NotEmpty
    @Length(max=100)
    private String email;

    @NotEmpty
    private boolean state;

    @NotEmpty
    @Column(name = "last_time")
    private Date lastTime;

    @NotEmpty
    @Column(name = "login_amount")
    private int loginAmount;

    /**
     * 用户是谁创建用户
     */
    @NotEmpty
    @Length(max=30)
    @ColumnTitle("用户是谁创建用户")
    private String createuser;

    /**
     * 该用户拥有的子系统权限，多个用｜线分隔，每个ID对应表 tab_sysmanageinfo.syamanage_id值
     */
    @NotEmpty
    @Length(max=255)
    @ColumnTitle("该用户拥有的子系统权限，多个用｜线分隔，每个ID对应表")
    private String sysmanage;

    @Length(max=128)
    private String uuid;

    public static final String Property_LoginAmount = "loginAmount";

    public static final String Property_Username = "username";

    public static final String Property_Createuser = "createuser";

    public static final String Property_Email = "email";

    public static final String Property_Tel = "tel";

    public static final String Property_UserId = "userId";

    public static final String Property_LastTime = "lastTime";

    public static final String Property_State = "state";

    public static final String Property_Sysmanage = "sysmanage";

    public static final String Property_Uuid = "uuid";

    public static final String Property_Password = "password";

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
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        addChangeField("password",this.password,password);
        this.password = password;
    }

    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        addChangeField("username",this.username,username);
        this.username = username;
    }

    /**
     * @return tel
     */
    public String getTel() {
        return tel;
    }

    /**
     * @param tel
     */
    public void setTel(String tel) {
        addChangeField("tel",this.tel,tel);
        this.tel = tel;
    }

    /**
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     */
    public void setEmail(String email) {
        addChangeField("email",this.email,email);
        this.email = email;
    }

    /**
     * @return state
     */
    public boolean getState() {
        return state;
    }

    /**
     * @param state
     */
    public void setState(boolean state) {
        addChangeField("state",this.state,state);
        this.state = state;
    }

    /**
     * @return last_time
     */
    public Date getLastTime() {
        return lastTime;
    }

    /**
     * @param lastTime
     */
    public void setLastTime(Date lastTime) {
        addChangeField("lastTime",this.lastTime,lastTime);
        this.lastTime = lastTime;
    }

    /**
     * @return login_amount
     */
    public int getLoginAmount() {
        return loginAmount;
    }

    /**
     * @param loginAmount
     */
    public void setLoginAmount(int loginAmount) {
        addChangeField("loginAmount",this.loginAmount,loginAmount);
        this.loginAmount = loginAmount;
    }

    /**
     * 获取用户是谁创建用户
     *
     * @return createuser - 用户是谁创建用户
     */
    public String getCreateuser() {
        return createuser;
    }

    /**
     * 设置用户是谁创建用户
     *
     * @param createuser 用户是谁创建用户
     */
    public void setCreateuser(String createuser) {
        addChangeField("createuser",this.createuser,createuser);
        this.createuser = createuser;
    }

    /**
     * 获取该用户拥有的子系统权限，多个用｜线分隔，每个ID对应表 tab_sysmanageinfo.syamanage_id值
     *
     * @return sysmanage - 该用户拥有的子系统权限，多个用｜线分隔，每个ID对应表 tab_sysmanageinfo.syamanage_id值
     */
    public String getSysmanage() {
        return sysmanage;
    }

    /**
     * 设置该用户拥有的子系统权限，多个用｜线分隔，每个ID对应表 tab_sysmanageinfo.syamanage_id值
     *
     * @param sysmanage 该用户拥有的子系统权限，多个用｜线分隔，每个ID对应表 tab_sysmanageinfo.syamanage_id值
     */
    public void setSysmanage(String sysmanage) {
        addChangeField("sysmanage",this.sysmanage,sysmanage);
        this.sysmanage = sysmanage;
    }

    /**
     * @return uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * @param uuid
     */
    public void setUuid(String uuid) {
        addChangeField("uuid",this.uuid,uuid);
        this.uuid = uuid;
    }
}