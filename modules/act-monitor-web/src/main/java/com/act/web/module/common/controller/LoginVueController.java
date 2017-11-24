/**
 * @Title: LoginVueController.java
 * @Package com.act.web.module.common.controller
 * @Description: 独立部署是 登入模块
 * @author fmj
 * @date 2017-4-1 下午3:01:57
 * @version V1.0
 */
package com.act.web.module.common.controller;

import com.act.monitor.model.TabMenu;
import com.act.monitor.model.TabSecondaryMenu;
import com.act.monitor.model.TabSysManageInfo;
import com.act.monitor.model.TabSysUser;
import com.act.web.module.common.service.LoginService;
import com.act.web.module.common.service.LoginVueService;
import com.act.web.module.common.vo.AuthorizationVo;
import com.act.web.module.common.vo.LoginVo;
import com.act.web.module.common.vo.UnitSystemVersionVo;
import com.act.web.module.common.vo.VueLoginVo;
import com.act.web.util.ConfigLoadUtil;
import com.act.web.util.SpringContextUtil;
import com.act.web.util.StringUtil;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Controller
@RequestMapping("/common/loginVue")
public class LoginVueController {
    private final Logger log = LoggerFactory.getLogger(LoginVueController.class);

    /**
     * vue 框架登入成功系统标示
     */
    private static final Integer loginCodeScc = 200;

    /**
     * vue 框架登入失败系统标示
     */
    private static final Integer loginCodeErr = 500;

    /**
     * TODO vue 框架独立登入 子系统id tab_sysmanageinfo【syamanage_id字段】
     */
    private static final String syaManageId = "3";

    @Resource
    private LoginVueService loginVueService;
    @Resource
    private LoginService loginService;

    /**
     * @Description: VUE 方式登入系统 TODO 受限IP功能未实现 tab_ip_limit
     */
    @ResponseBody
    @RequestMapping(value = "/login.do")
    public Object login(LoginVo vo, HttpServletRequest request) throws Exception {
        VueLoginVo result = new VueLoginVo();
        String msg = "";
        TabSysUser user = new TabSysUser();
        if (vo != null) {
            ConfigLoadUtil conf = SpringContextUtil.getBean("configLoadUtil");
            String isCode = conf.getLoginCode();
            vo.setLoginIsCode(isCode);
            String passwordCheck = loginService.getConfigValue("passwordCheck");
            if (null == passwordCheck || "".equals(passwordCheck)) {
                msg = "参数表未配置登录模式";
            }
            if ("1".equalsIgnoreCase(passwordCheck)) {//
                if ("Y".equalsIgnoreCase(vo.getLoginIsCode()) && !vo.getVerifyId().equalsIgnoreCase(
                        request.getSession().getAttribute("rand").toString())) {
                    request.getSession().removeAttribute("rand");
                    msg = "验证码错误";
                    result.setCode(loginCodeErr);
                    result.setMsg(msg);
                    return result;
                } else {
                    request.getSession().removeAttribute("rand");
                }
            }

            String userIdBase64 = vo.getUserId();
            vo.setUserId(new String(
                    Base64.decodeBase64(userIdBase64.getBytes())));
            if (null != vo.getUserId() && !"".equals(vo.getUserId().trim())) {
                user = loginVueService.findByUserId(vo.getUserId().trim());
                //查询uuid
                String uuid = loginVueService.findUUID();
                user.setUuid(uuid);

                if (user != null) {
                    if ("1".equalsIgnoreCase(passwordCheck)) {//非单点登录
                        vo.setPassword(new String(new Base64().decode(vo.getPassword().getBytes())));
                        if (user.getPassword().equals(StringUtil.encoderByMd5(vo.getPassword().trim()))) {
                            if (user.getState()) {
                                msg = "用户[" + user.getUsername() + "]当前状态已被锁定，请联系管理员!";
                            } else {
                                // 更新用户最后登录时间
                                Integer loginAmount = user.getLoginAmount();
                                loginVueService.updateLonginUser(loginAmount + 1,
                                        new Timestamp(System.currentTimeMillis()),
                                        user.getUserId());
                                msg = "请求成功";
                                // 初始化 菜单权限 的系统参数
                                result.setCode(loginCodeScc);
                                result.setMsg(msg);
                                result.setUser(user);
                                initLoad(user.getUserId(), result);
                                return result;
                            }
                        }
                    } else if ("0".equalsIgnoreCase(passwordCheck)) {//单点登录
                        Integer loginAmount = user.getLoginAmount();
                        loginVueService.updateLonginUser(loginAmount + 1,
                                new Timestamp(System.currentTimeMillis()),
                                user.getUserId());
                        msg = "请求成功";
                        // 初始化 菜单权限 的系统参数
                        result.setCode(loginCodeScc);
                        result.setMsg(msg);
                        result.setUser(user);
                        initLoad(user.getUserId(), result);
                        return result;
                    }
                }
                result.setCode(loginCodeErr);
                msg = (msg == "") ? "账号或密码错误" : msg;
                result.setMsg(msg);
            }
        }
        return result;
    }

    /**
     * @param curUserId 修改密码用户id user_id
     * @param userOldPw 原密码
     * @param newPw     现密码
     * @return Object 返回类型
     * @Description: 修改密码
     */
    @ResponseBody
    @RequestMapping(value = "/modifyPw.do")
    public Object modifyPw(@RequestParam String curUserId,
                           @RequestParam String userOldPws, @RequestParam String newPws)
            throws Exception {
        VueLoginVo loginVo = new VueLoginVo();
        TabSysUser curUser = loginVueService.findByUserId(curUserId);
        String oldPwString = StringUtil.encoderByMd5(
                new String(new Base64().decode(userOldPws.getBytes()))).trim();
        if (oldPwString.equals(curUser.getPassword())) {
            String encryptPwd = StringUtil.encoderByMd5(new String(Base64
                    .decodeBase64(newPws.getBytes())));
            loginVueService.updateUserPwd(encryptPwd, curUser.getUserId());
            loginVo.setCode(loginCodeScc);
        } else {
            loginVo.setCode(loginCodeErr);
        }
        return loginVo;
    }

    /**
     * 初始化系统参数
     */
    private void initLoad(String userId, VueLoginVo result) {

        UnitSystemVersionVo unitSystemVersion = loginVueService.findVersion();
        TabSysManageInfo tabSysManageInfo = loginVueService.findSysInfo(syaManageId);
        boolean isNoAdmin = loginVueService.getRoleByUserId(userId);
        // 启动缓存 返回一级菜单
        List<TabMenu> list = loginVueService.getFirstMenu(userId, syaManageId,
                isNoAdmin);
        // 启动缓存 返回二级菜单列表
        List<Map<String, Object>> menuList = loginVueService
                .getSecMenuList(list, userId, syaManageId, isNoAdmin);

        // 启动缓存 返回菜单权限
        List<TabSecondaryMenu> purviewList = loginVueService
                .findAllPurview(userId, isNoAdmin);
        // 启动缓存 返回按钮权限列表(sA) 权限Map获取失败 才有List获取
        Map<String, List<String>> secMenuPurview = loginVueService
                .getPurviewMapList(purviewList);

        List<AuthorizationVo> firstList = new ArrayList<AuthorizationVo>();

        for (Map<String, Object> menus : menuList) {
            TabMenu first = (TabMenu) menus.get("menu");
            List<TabMenu> seconds = (List<TabMenu>) menus.get("list");
            AuthorizationVo firstAuth = new AuthorizationVo("/",
                    first.getMenuParameter(), first.getMenuNameCh(),
                    first.getMenuImage(), false);
            List<AuthorizationVo> secondList = new ArrayList<AuthorizationVo>();
            for (TabMenu second : seconds) {
                AuthorizationVo secondAuth = new AuthorizationVo("/"
                        + second.getMenuUrl(), second.getMenuParameter(),
                        second.getMenuNameCh(), second.getMenuImage(),
                        false);
                secondList.add(secondAuth);
            }
            firstAuth.setChildren(secondList.toArray());
            firstList.add(firstAuth);
        }
        Object[] authorizations = firstList.toArray();
        result.setSecMenuPurview(secMenuPurview);
        result.setAuthorization(authorizations);
        result.setUnitSystemVersion(unitSystemVersion);
        result.setTabSysManageInfo(tabSysManageInfo);
    }

    /**
     * @Title: verification
     * @Description: 返回验证码
     * @create 2017-6-19 上午10:32:04
     * @update 2017-6-19 上午10:32:04
     */
    @ResponseBody
    @RequestMapping(value = "/verification.do ")
    public void verification(HttpServletRequest request,
                             HttpServletResponse response, HttpSession session)
            throws IOException {
        // 在内存中创建图象
        int width = 80;
        int height = 28;
        // 生成随机类
        Random random = new Random();
        // 设置response头信息 禁止缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        // 生成缓冲区image类
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        // 获取图形上下文
        Graphics g = image.getGraphics();
        // 设定背景色
        g.setColor(new Color(209, 227, 248));
        g.fillRect(0, 0, width, height);
        // 设定字体
        g.setFont(new Font("Times New Roman", Font.PLAIN, 22));
        StringBuffer randomCode = new StringBuffer();
        // 随机产生4数字的验证码。
        for (int i = 0; i < 4; i++) {
            // 得到随机产生的验证码数字。
            String strRand = String.valueOf(StringUtil.codeSequence[random
                    .nextInt(56)]);
            g.setColor(new Color(7, 85, 134));
            // 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
            g.drawString(strRand, 16 * i + 4, 20);

            // 将产生的四个随机数组合在一起。
            randomCode.append(strRand);
        }
        // 将字符保存到session中用于前端的验证
        session.setAttribute("rand", randomCode);
        // 图象生效
        g.dispose();
        ImageIO.write(image, "JPEG", response.getOutputStream());
        response.getOutputStream().flush();
    }


}
