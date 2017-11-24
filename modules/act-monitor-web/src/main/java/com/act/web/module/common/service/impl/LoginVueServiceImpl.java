/**
 * @Title: LoginVueServiceImpl.java
 * @Package com.act.web.module.common.service.impl
 * @Description: 登入系统/子系统 模块初始化Sevice 实现类
 * @author fmj
 * @date 2017-4-6 下午4:41:24
 * @version V1.0
 */
package com.act.web.module.common.service.impl;

import com.act.framework.util.DbUtil;
import com.act.monitor.model.*;
import com.act.web.module.common.service.LoginVueService;
import com.act.web.module.common.vo.UnitSystemVersionVo;
import com.act.web.util.ConfigLoadUtil;
import com.act.web.util.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LoginVueServiceImpl implements LoginVueService {

    private final static Logger log = LoggerFactory
            .getLogger(LoginVueServiceImpl.class);


    // 根据用户id得到信息
    @Override
    public TabSysUser findByUserId(String uid) {
        String sql = "select * from tab_sys_user where user_id = ?";
        TabSysUser user = DbUtil.queryForObject(sql, TabSysUser.class, uid);
        return user;
    }

    // 更新 TabSysUser(tab_sys_user) 根据UserId
    @Override
    public void updateLonginUser(Integer loginAmount, Timestamp lastTime,
                                 String uid) {
        String sql = "update tab_sys_user set login_amount = ?,last_time = ? where user_id = ? ";
        DbUtil.update(sql, loginAmount, lastTime, uid);
    }

    // 更新 TabSysUser 根据UserId 和 密码
    @Override
    public void updateUserPwd(String uid, String encryptPwd) {
        String sql = "update tab_sys_user set password = ? where user_id = ? ";
        DbUtil.update(sql, uid, encryptPwd);
    }

    // 根据userId判断当前用户是不管理员权限
    @Override
    public boolean getRoleByUserId(String userId) {
        String sql = "select * from tab_user_role_purview where user_id = ?";
        List<RolePurview> roleList = DbUtil.queryForObjectList(sql,
                RolePurview.class, userId);
        boolean notAdmin = true;
        if (roleList != null && roleList.size() > 0) {
            for (int i = 0; i < roleList.size(); i++) {
                // 如果包含有超级管理员的角色
                if (roleList.get(i).getRoleId() == 1) {
                    notAdmin = false;
                    break;
                }
            }
        }
        return notAdmin;
    }

    // 根据用户名Id 系统Id 是否管理员 来返回一级菜单list(加入ehcache缓存)
    @Override
//	@Cacheable(value = "myCache")
    public List<TabMenu> getFirstMenu(String userId, String systemVersion,
                                      boolean isNonAdmin) {
        List<TabMenu> menuList = null;
        try {
            String menuSql;
            if ("55".equals(systemVersion)) {
                menuSql = "SELECT A.* FROM tab_menu as A, tab_menu_role as B  WHERE  A.menu_id = B.menu_id  AND B.is_active ='Y'  AND A.parent_Id=0 AND A.menu_id =6 AND A.verflag=5 ORDER BY B.show_seq ASC ";
            } else {
                menuSql = "SELECT A.* FROM tab_menu as A, tab_menu_role as B  WHERE  A.menu_id = B.menu_id  AND B.is_active ='Y'  AND A.parent_Id=0 AND A.verflag="
                        + systemVersion + " ORDER BY B.show_seq ASC ";
            }

            // 如果非超级管理员登录或非超级管理员权限，需要取得对应的一级菜单
            if (!"admin".equals(userId) && isNonAdmin) {
                String purviewSql = "select * from tab_purview where role_id in (select role_id from tab_user_role_purview where user_id= ?) and sec_menu_id='0'";
                List<TabPurview> list = DbUtil.queryForObjectList(purviewSql,
                        TabPurview.class, userId);
                StringBuilder menuIds = new StringBuilder();
                if (list != null && list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        menuIds.append("'").append(list.get(i).getProgramId())
                                .append("',");
                    }
                }
                if (menuIds.length() > 0) {
                    menuSql = "SELECT A.* FROM tab_menu as A, tab_menu_role as B  WHERE  A.menu_id = B.menu_id AND B.menu_url in ("
                            + menuIds.substring(0, menuIds.length() - 1)
                            + ") AND B.is_active ='Y'  AND A.parent_Id=0 AND A.verflag="
                            + systemVersion + " ORDER BY B.show_seq ASC ";
                } else {
                    menuSql = "SELECT A.* FROM tab_menu as A, tab_menu_role as B  WHERE  A.menu_id = B.menu_id AND B.menu_url in ('') AND B.is_active ='Y'  AND A.parent_Id=0 AND A.verflag="
                            + systemVersion + " ORDER BY B.showSeq ASC ";
                }
            }
            menuList = DbUtil.queryForObjectList(menuSql, TabMenu.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return menuList;
    }

    // 根据登录名和一级菜单的id查找二级菜单
    @Override
//	@Cacheable(value = "myCache")
    public List<Map<String, Object>> getSecMenuList(List<TabMenu> list,
                                                    String userId, String sv, boolean isNoAdmin) {
        List<Map<String, Object>> menuList = new ArrayList<Map<String, Object>>();
        if (list != null && list.size() != 0) {
            for (TabMenu tabMenu : list) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("menu", tabMenu);
                Integer parentId = tabMenu.getMenuId();
                List<TabMenu> nlist = getSecMenu(parentId, userId, sv,
                        isNoAdmin);
                if (nlist.size() < 1)
                    continue;
                map.put("list", nlist);
                menuList.add(map);
            }
        }
        return menuList;
    }

    // 根据登录名和一级菜单的id查找二级菜单
    public List<TabMenu> getSecMenu(Integer parentId, String userId, String sv,
                                    boolean isNoAdmin) {
        List<TabMenu> secList = null;
        try {
            String hql = "select A.* FROM Tab_Menu A, Tab_Menu_Role B  WHERE A.menu_Id = B.menu_Id AND B.is_Active='Y' AND B.parent_Id="
                    + parentId
                    + " AND B.parent_Id > 0  AND A.verflag="
                    + sv
                    + " ORDER BY B.show_seq ASC ";
            if (isNoAdmin) {
                String sql = "SELECT sec.* FROM tab_secondary_menu sec, tab_purview pur, tab_user_role_purview role WHERE sec.sec_menu_id = pur.sec_menu_id AND pur.role_id = role.role_id AND role.user_id = ? ";
                List<TabSecondaryMenu> list = DbUtil.queryForObjectList(sql,
                        TabSecondaryMenu.class, userId);
                StringBuilder menuUrls = new StringBuilder();
                if (list != null && list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        menuUrls.append("'").append(list.get(i).getProgramId())
                                .append("',");
                    }
                }
                if (menuUrls.length() > 0) {
                    hql = "SELECT A.*  FROM tab_menu A, tab_menu_role B  WHERE A.menu_id = B.menu_id AND A.menu_url in ("
                            + menuUrls.substring(0, menuUrls.length() - 1)
                            + ") AND B.is_Active='Y' AND B.parent_Id="
                            + parentId
                            + " AND B.parent_Id > 0  AND A.verflag="
                            + sv + " ORDER BY B.show_Seq ASC ";
                } else {
                    hql = "SELECT A.*  FROM tab_menu A, tab_menu_role B  WHERE A.menu_id = B.menu_id AND A.menu_url in ( '' ) AND B.is_active='Y' AND B.parent_Id="
                            + parentId
                            + " AND B.parent_Id > 0  AND A.verflag="
                            + sv + " ORDER BY B.show_Seq ASC ";
                }
            }
            secList = DbUtil.queryForObjectList(hql, TabMenu.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return secList;
    }

    // 按钮权限
    @Override
//	@Cacheable(value = "myCache")
    public List<TabSecondaryMenu> findAllPurview(String userId,
                                                 boolean isNotAdmin) {
        if (!isNotAdmin) {
            String sql = "select * from tab_secondary_menu";
            List<TabSecondaryMenu> result = DbUtil.queryForObjectList(sql,
                    TabSecondaryMenu.class);
            return result;
        } else {
            String sql = "select p.* from tab_purview p, tab_user_role_purview u where p.role_id=u.role_id and p.purview_str='1' and p.sec_menu_id<>0 and u.user_id= ? order by sec_menu_id asc";
            List<TabPurview> list = DbUtil.queryForObjectList(sql,
                    TabPurview.class, userId);
            Map<String, String> map = new HashMap<String, String>();
            for (TabPurview tabPurview : list) {
                String programId = tabPurview.getProgramId();
                String purviews = tabPurview.getPurviewList();
                if (map.get(programId) != null) {
                    map.put(programId, map.get(programId) + purviews);
                } else {
                    map.put(programId, purviews);
                }
            }
            List<TabSecondaryMenu> result = new java.util.ArrayList<TabSecondaryMenu>();
            for (java.util.Iterator<String> it = map.keySet().iterator(); it
                    .hasNext(); ) {
                TabSecondaryMenu tabSecondaryMenu = new TabSecondaryMenu();
                String programId = it.next();
                String purviews = map.get(programId);
                tabSecondaryMenu.setProgramId(programId);
                tabSecondaryMenu.setPurviewList(purviews);
                result.add(tabSecondaryMenu);
            }
            return result;
        }
    }

    @Override
//	@Cacheable(value = "myCache")
    public Map<String, List<String>> getPurviewMapList(
            List<TabSecondaryMenu> purviewList) {
        Map<String, List<String>> secMenuPurview = new HashMap<String, List<String>>();
        if (purviewList != null && purviewList.size() > 0) {
            for (int i = 0; i < purviewList.size(); i++) {
                TabSecondaryMenu ts = purviewList.get(i);
                String menuurl = ts.getProgramId();
                String purview = ts.getPurviewList();
                List<String> purviewMap = getPurviewMap(purview);
                secMenuPurview.put(menuurl, purviewMap);
            }
        }
        return secMenuPurview;
    }

    // 按钮权限字典表
    public List<String> getPurviewMap(String purview) {
        List<String> purviewMap = new ArrayList<String>();
        String[] purviews = purview.split("@");
        for (String str : purviews) {
            if ("1".equals(str)) {
                purviewMap.add("query");
            } else if ("2".equals(str)) {
                purviewMap.add("print");
            } else if ("3".equals(str)) {
                purviewMap.add("import");
            } else if ("4".equals(str)) {
                purviewMap.add("export");
            } else if ("5".equals(str)) {
                purviewMap.add("add");
            } else if ("6".equals(str)) {
                purviewMap.add("modify");
            } else if ("7".equals(str)) {
                purviewMap.add("delete");
            } else if ("8".equals(str)) {
                purviewMap.add("block");
            } else if ("9".equals(str)) {
                purviewMap.add("operate");
            }
        }
        return purviewMap;
    }

    /**
     * 根据 配置文件中 系统简称 得到 系统版本号
     */
    @Override
    public UnitSystemVersionVo findVersion() {
        ConfigLoadUtil conf = SpringContextUtil.getBean("configLoadUtil");
        String sysName = conf.getSysName();
        String sql = "select a.system_version as systemVersion from unit_system_version a where a.name = ?";
        UnitSystemVersionVo unitSystemVersion = DbUtil.queryForObject(sql, UnitSystemVersionVo.class, sysName);
        if (unitSystemVersion == null) {
            //unit_system_version 获取不到数据时 初始化为1.0.0.0
            unitSystemVersion = new UnitSystemVersionVo();
            unitSystemVersion.setSystemVersion("1.0.0.0");
        }
        return unitSystemVersion;
    }

    /**
     * 根据系统syamanage_id 得到系统信息
     */
    @Override
    public TabSysManageInfo findSysInfo(String syamanageId) {
        TabSysManageInfo tabSysManageInfo = TabSysManageInfo.getByKey(Byte.valueOf(syamanageId));
        return tabSysManageInfo;
    }

    @Override
    public String findUUID() {
        String sql = "SELECT UUID  FROM monsys_all_housename_info LIMIT 1";
        SqlRowSet query = DbUtil.query(sql);
        List<Map<String, Object>> maps = DbUtil.queryForList(sql);
        Object uuid = "";
        if (maps.size() > 0) {
            Map<String, Object> stringObjectMap = maps.get(0);
            uuid = stringObjectMap.get("uuid");
        }
        return uuid.toString();
    }

}
