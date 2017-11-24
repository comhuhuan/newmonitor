/**
 * @Title: LoginServiceImpl.java
 * @Package com.act.web.module.common.service.impl
 * @Description: (登入界面service实现类)
 * @author fmj
 * @date 2017-2-6 下午4:53:05
 * @version V1.0
 */
package com.act.web.module.common.service.impl;

import com.act.framework.util.DbUtil;
import com.act.monitor.model.*;
import com.act.web.module.common.service.LoginService;
import com.act.web.module.common.vo.UnitSystemVersionVo;
import com.act.web.util.ConfigLoadUtil;
import com.act.web.util.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {
    private final static Logger log = LoggerFactory
            .getLogger(LoginServiceImpl.class);

    // 根据用户id得到信息
    @Override
    public TabSysUser findByUserId(String uid) {
        String sql = "select * from tab_sys_user where user_id = ?";
        TabSysUser user = DbUtil.queryForObject(sql, TabSysUser.class, uid);
        return user;
    }

    // 更新 TabSysUser 根据UserId 和 密码
    @Override
    public void updateUserPwd(String uid, String encryptPwd) {
        String sql = "update tab_sys_user set password = ? where user_id = ? ";
        DbUtil.update(sql, uid, encryptPwd);
    }

    // 更新 TabSysUser(tab_sys_user) 根据UserId
    @Override
    public void updateLonginUser(Integer loginAmount, Timestamp lastTime,
                                 String uid) {
        String sql = "update tab_sys_user set login_amount = ?,last_time = ? where user_id = ? ";
        DbUtil.update(sql, loginAmount, lastTime, uid);
    }

    // 检查 登入IP(tab_ip_limit) 是否受限
//	@Override
//	public boolean checkIpLimit(String ip) {
//		boolean flag = true;
//		String sql = "select * from tab_ip_limit";
//		List<TabIpLimit> ipList = DbUtil.queryForObjectList(sql,
//				TabIpLimit.class, null);
//		if (ipList != null && ipList.size() > 0) {
//			TabIpLimit ipInfo = ipList.get(0);
//			Byte useAllowIp = ipInfo.getUseAllowIp();
//			Byte userLimitIp = ipInfo.getUseLimitIp();
//			String allowIp = ipInfo.getAllowIp();
//			String limitIp = ipInfo.getLimitIp();
//			long ipLong = IpUtil.ipToLong(ip);
//			if (useAllowIp == 1 && StringUtil.checkEmpty(allowIp)) {
//				String[] ips = allowIp.split(",");
//				for (String eachIp : ips) {
//					long eachIpLong = Long.valueOf(eachIp);
//					if (ipLong == eachIpLong) {
//						flag = true;
//						break;
//					} else {
//						flag = false;
//						continue;
//					}
//				}
//			}
//			if (userLimitIp == 1 && StringUtil.checkEmpty(limitIp)) {
//				String[] ips = limitIp.split(",");
//				for (String eachIp : ips) {
//					long eachIpLong = Long.valueOf(eachIp);
//					if (ipLong == eachIpLong) {
//						flag = false;
//						break;
//					} else {
//						flag = true;
//						continue;
//					}
//				}
//			}
//		}
//
//		return flag;
//	}

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

    // 查找用户本身所具有的区域信息
//	@Override
//	public String findServicePurview(String userId) {
//		String sql = "SELECT * FROM tab_servicepurview WHERE purvtype =1 AND user_id = ?";
//		List<ServicePurview> services = DbUtil.queryForObjectList(sql,
//				ServicePurview.class, userId);
//		StringBuffer sb = new StringBuffer();
//		if (services != null && services.size() > 0) {
//			for (int i = 0; i < services.size(); i++) {
//				sb.append("'").append(services.get(i).getServicecode())
//						.append("',");
//			}
//		} else {
//			sb.append("'00000000000000',");
//		}
//		return sb.substring(0, sb.length() - 1);
//	}

    // 根据权限得到所属机房
//	@Override
//	public Map<String, String> findAllRoomName(String purService) {
//		Map<String, String> map = new LinkedHashMap<String, String>();
//		String sql = "select * from tab_service_information where service_code in (?)";
//		List<TabServiceInformation> list = DbUtil.queryForObjectList(sql,
//				TabServiceInformation.class, purService);
//		if (list != null && list.size() > 0) {
//			for (TabServiceInformation service : list) {
//				map.put(service.getServiceCode(), service.getServiceName());
//			}
//		} else {
//			map.put("", "");
//		}
//		return map;
//	}

    @Override
    public String getConfigValue(String config) {
        String sql = "select * from tab_sysconfig where configid = ? ";
        List<TabSysConfig> list = DbUtil.queryForObjectList(sql,
                TabSysConfig.class, config);
        if (list != null && !list.isEmpty()) {
            TabSysConfig sysConfig = (TabSysConfig) list.get(0);
            String value = sysConfig.getConfigval() != null ? sysConfig
                    .getConfigval() : sysConfig.getDefaultval();
            return value;
        }
        return null;
    }

    // 读取配置值并修改常量值
/*	@Override
    public void updateConstant() {
		Map<String, String> configMap = new HashMap<String, String>();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from tab_sysconfig where configid in (");
		sql.append("'gRealFluxRefresh','gPageSize','gRealObserveNum','gRealObserveInterval','gOfflineTimes','data_store','gAlarmContent','gRowNums',");
		sql.append("'coreseekMode','coreseekIdsSort','coreseekMultiDB','section_number','result_number')");

		List<TabSysConfig> list = DbUtil.queryForObjectList(sql.toString(),
				TabSysConfig.class);
		if (list != null && list.size() > 0) {
			for (TabSysConfig sysConfig : list) {
				String configId = sysConfig.getConfigid();
				String defaultValue = sysConfig.getDefaultval();
				String configValue = sysConfig.getConfigval();
				if (configValue != null && !"".equals(configValue)) {
					configMap.put(configId, configValue);
				} else if (defaultValue != null && !"".equals(defaultValue)) {
					configMap.put(configId, defaultValue);
				}
			}
		}

		// 实时流量刷新时间间隔
		CommonContant.FLOW_REFRESH_TIME = Integer.valueOf(configMap
				.get("gRealFluxRefresh"));
		// 每页显示条数
		CommonContant.ALARM_PAGE_SIZE = Integer.valueOf(configMap
				.get("gPageSize"));
		// 实时报警显示记录数
		CommonContant.ALART_COUNT = Integer.valueOf(configMap
				.get("gRealObserveNum"));
		// 实时报警刷新时间间隔
		CommonContant.ALARM_REFRESH_TIME = Integer.valueOf(configMap
				.get("gRealObserveInterval"));
		// 通讯离线时间
		CommonContant.LEAVE_TIME = Integer.valueOf(configMap
				.get("gOfflineTimes"));
		// 日志保存时间
		CommonContant.LOG_SAVE_MONTH = Integer.valueOf(configMap
				.get("data_store"));
		// 显示查询区每行输出项数
		CommonContant.TD_NUM = Integer.valueOf(configMap.get("gRowNums"));
		// 分割查询的数量
		CommonContant.SECTION_NUMBER = Long.valueOf(configMap
				.get("section_number"));
		// 查询返回的数量
		CommonContant.RESULT_NUMBER = Long.valueOf(configMap
				.get("result_number"));
		// 是否开启coreseek搜索模式
		CommonContant.CORESEEK_MODE = "Y".equals(configMap.get("coreseekMode"));
		// 返回索引ID是否排序Y是N否
		CommonContant.CORESEEK_IDS_SORT = "Y".equals(configMap
				.get("coreseekIdsSort"));
		// 开启索引是否多子库查询Y是N否
		CommonContant.CORESEEK_MULTI_DB = "Y".equals(configMap
				.get("coreseekMultiDB"));

		String[] gAlarmContent = configMap.get("gAlarmContent").split("@@");
		String[] alarmContent = new String[gAlarmContent.length - 1];
		System.arraycopy(gAlarmContent, 0, alarmContent, 0,
				gAlarmContent.length - 1);
		CommonContant.ALARM_CONTENT = alarmContent;
	}*/

    // 查找菜单子系统 show_mk = y
    @Override
    public List<TabSysManageInfo> findAllSubSystem() {
        String sql = " select * from tab_sysmanageinfo where show_mk = 'Y'  ";
        List<TabSysManageInfo> list = DbUtil.queryForObjectList(sql,
                TabSysManageInfo.class);
        return list;
    }

//	/**
//	 * TODO 加载报警权限
//	 */
//	@Deprecated
//	public String getSchemeIdList(String userId, String gOpenalarmUser) {
//		String returnValue = "";
//		if ("Y".equals(gOpenalarmUser)
//				|| ("N".equals(gOpenalarmUser) && !"admin".equals(userId))) {
//			String sql = "SELECT * from tab_user_role_purview WHERE user_id=? ";
//			List<RolePurview> list = DbUtil.queryForObjectList(sql,
//					RolePurview.class, userId);
//			StringBuilder roleIdList = new StringBuilder();
//			if (list != null && list.size() > 0) {
//				for (int i = 0; i < list.size(); i++) {
//					roleIdList.append("'").append(list.get(i).getRoleId())
//							.append("',");
//				}
//			}
//			if (roleIdList.length() <= 0) {
//				roleIdList.append("'9999',");
//			}
//			String aSql = "SELECT * from t_auditpolicy_purview where ( user_type ='B' AND allsid IN (?) ) or ( user_type ='A' and allsid = ? )";
//			List<AuditpolicyPurview> aList = DbUtil.queryForObjectList(aSql,
//					AuditpolicyPurview.class,
//					roleIdList.substring(0, roleIdList.length() - 1), userId);
//			StringBuilder schemeList = new StringBuilder();
//			if (aList != null && aList.size() > 0) {
//				for (int i = 0; i < aList.size(); i++) {
//					schemeList.append("'").append(aList.get(i).getSchemeId())
//							.append("',");
//				}
//			}
//			// 加载本身用户的scheme_id
//			String sSql = "SELECT * FROM t_scheme WHERE creator = ? AND scheme_type !=1";
//			List<TScheme> sList = DbUtil.queryForObjectList(sSql,
//					TScheme.class, userId);
//			if (sList != null && sList.size() > 0) {
//				for (int i = 0; i < sList.size(); i++) {
//					schemeList.append("'").append(sList.get(i).getSchemeId())
//							.append("',");
//				}
//			}
//			if (schemeList.length() <= 0) {
//				schemeList.append("'99999999999999999999',");
//			}
//			return "  AND rule_identifier IN ( "
//					+ schemeList.substring(0, schemeList.length() - 1) + " ) ";
//		}
//		return returnValue;
//	}

    // 根据用户名Id 系统Id 是否管理员 来返回一级菜单list(加入ehcache缓存)
    @Override
    @Cacheable(value = "myCache")
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

    // 按钮权限
    @Override
    @Cacheable(value = "myCache")
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

    // 根据登录名和一级菜单的id查找二级菜单
    @Override
    @Cacheable(value = "myCache")
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

    @Override
    @Cacheable(value = "myCache")
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
}
