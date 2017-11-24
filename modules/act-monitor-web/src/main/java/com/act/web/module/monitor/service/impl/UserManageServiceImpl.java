package com.act.web.module.monitor.service.impl;

import com.act.framework.util.DbUtil;
import com.act.framework.util.PageResult;
import com.act.monitor.model.RolePurview;
import com.act.monitor.model.TabSysUser;
import com.act.web.module.monitor.service.UserManageService;
import com.act.web.module.monitor.vo.UserVo;
import com.act.web.util.TranscodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserManageServiceImpl implements UserManageService {
    private final Logger log = LoggerFactory.getLogger(UserManageServiceImpl.class);

    /**
     * 保存数据新增 对应前端commonFlag
     */
    private static final String COMMON_FLAG_ADD = "add";

    /**
     * 全国管理账号标识
     */
    private static final String COMMON_FLAG_ALL_IDC_PUR = "2";

    /**
     * IDC管理账号标识
     */
    private static final String COMMON_FLAG_IDC_PUR = "3";

    @Override
    public PageResult<UserVo> pagingList(PageResult<UserVo> page,
                                         UserVo userVo) {

        StringBuffer sql = new StringBuffer("SELECT model.user_id as userId,model.username as username,model.tel as tel,model.email as email,model.uuid as uuid,idc.idcName as `limit`,CAST(model.last_time AS CHAR) AS createTime");
        sql.append(" FROM tab_sys_user model LEFT JOIN monsys_all_idc_info idc ON model.uuid = idc.UUID WHERE 1=1 ");
        List<Object> params = new ArrayList<Object>();

        if (userVo != null) {
            String userId = userVo.getUserId();
            if (userId != null && userId.trim().length() > 0) {
                sql.append(" AND model.user_id like ?");
                params.add("%" + userId.trim() + "%");
            }
        }

        Object[] param = new Object[params.size()];
        for (int i = 0; i < params.size(); i++) {
            param[i] = params.get(i);
        }

        page = DbUtil.queryPageForObjectPageResult(sql.toString(),
                UserVo.class, page.getPageIndex(), page.getPageSize(),
                param);

        List<UserVo> rows = page.getRows();
        String sql1 = "";
        for (int i = 0; i < rows.size(); i++) {
            String idcName = "";
            if (rows.get(i) != null && !"".equals(rows.get(i))) {
                String uuid = rows.get(i).getUuid();
                if (null != uuid && !"".equals(uuid.trim())) {
                    sql1 = "SELECT idcName FROM monsys_all_idc_info WHERE UUID = '" + rows.get(i).getUuid() + "'";
                    idcName = DbUtil.queryForString(sql1);

                    if (idcName != null && !"".equals(idcName.trim())) {
                        rows.get(i).setLimit(idcName);
                    } else {
                        rows.get(i).setLimit("无权限");
                    }
                } else {
                    rows.get(i).setUuid("");
                }
            }
        }
        page.setRows(rows);
        return page;
    }

    @Override
    public Object getIdcList() {
        String sql = "SELECT model.UUID as uuid,model.idcName as idcName FROM monsys_all_idc_info model";
        List<Map<String, Object>> result = DbUtil.queryForMapList(sql);
        return result;
    }

    /*
     * (非 Javadoc) <p>Title: save</p> <p>Description: 新增和修改 tab_sys_user</p>
     *
     * @param UserVo
     *
     * @param commonFlag
     *
     * @see
     * com.act.web.module.monitor.service.UserManageService#save(com.act.monitor.model
     * .UserVo, java.lang.String)
     */
    @Override
    public void save(TabSysUser user, String commonFlag) throws Exception {
        if (COMMON_FLAG_ADD.equalsIgnoreCase(commonFlag)) {
            String password = TranscodeUtil.encoderByMd5(user.getPassword());

            user.setPassword(password);
            user.setState(false);
            user.setLastTime(new Date());
            user.setLoginAmount(0);
            user.setCreateuser("admin");
            user.setSysmanage("3");
            user.insert();

            RolePurview rolePurview = new RolePurview();
            rolePurview.setUserId(user.getUserId());
            if (user.getUuid() != null && "".equals(user.getUuid())) {
                rolePurview.setRoleId(3);
            } else {
                rolePurview.setRoleId(4);
            }
            rolePurview.insert();
        } else {
            TabSysUser oldUser = TabSysUser
                    .getByKey(user.getUserId());
            oldUser.setUsername(user.getUsername());
            oldUser.setTel(user.getTel());
            oldUser.setEmail(user.getEmail());
            oldUser.setUuid(user.getUuid());
            ;
            oldUser.setLastTime(new Date());
            oldUser.update();

            String sql = "SELECT * FROM tab_user_role_purview WHERE user_id = ?";
            RolePurview rolePurview = DbUtil.queryForObject(sql, RolePurview.class, oldUser.getUserId());
            if (user.getUuid() != null && "".equals(user.getUuid())) {
                rolePurview.setRoleId(3);
            } else {
                rolePurview.setRoleId(4);
            }
        }
    }

    /*
     * (非 Javadoc) <p>Title: remove</p> <p>Description: 根据主键 user_id 删除 tab_sys_user</p>
     *
     * @param userId
     *
     * @see
     * com.act.web.module.monitor.service.UserManageService#remove(java.lang.String)
     */
    @Override
    public void remove(String userId) {
        TabSysUser.getDao().deleteByPrimaryKey(userId);
        String sql = "SELECT * FROM tab_user_role_purview WHERE user_id = ?";
        RolePurview rolePurview = DbUtil.queryForObject(sql, RolePurview.class, userId);
        RolePurview.getDao().delete(rolePurview);
    }

}