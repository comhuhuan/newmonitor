/**
 * @Title: LoginChildSystemController.java
 * @Package com.act.web.module.common.controller
 * @Description: 兼容老系统登入子系统方法
 * @author fmj
 * @modifier fmj
 * @date 2017-5-24 上午8:51:13
 * @version V1.0
 */
package com.act.web.module.common.controller;

import com.act.monitor.model.TabSysManageInfo;
import com.act.monitor.model.TabSysUser;
import com.act.web.constant.CommonContant;
import com.act.web.module.common.service.LoginService;
import com.act.web.util.ConfigLoadUtil;
import com.act.web.util.SpringContextUtil;
import com.act.web.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
public class LoginChildSystemController extends BaseController {
    private final Logger log = LoggerFactory.getLogger(LoginChildSystemController.class);

    @Resource
    private LoginService loginService;

    private static final Map<String, String> randomMap = new HashMap<String, String>();

    /**
     * @Title: toChildSystem
     * @Description: 兼容老框架子系统跳转方式 common.jsp 中
     * /loginChildSystem!toChildSystem.action
     * @create 2017-5-24 上午9:12:57
     * @update 2017-5-24 上午9:12:57
     */
    @ResponseBody
    @RequestMapping(value = "/loginChildSystem!toChildSystem.action")
    public void toChildSystem(HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
        ConfigLoadUtil conf = SpringContextUtil.getBean("configLoadUtil");
        String tomcat_port = conf.getTomcat_port();
        String sys_id = request.getParameter("sys_id");
        if (sys_id == null || "".equals(sys_id)) {
            sys_id = (String) request.getSession().getAttribute("sys_id");
        }
        TabSysManageInfo tabSysmanageInfo = loginService.findSysInfo(sys_id);
        String childSystemPath = tabSysmanageInfo.getChildSystemPath();
        String timeSign = "" + new Date().getTime();
        String url = "http://127.0.0.1:" + tomcat_port + "/" + childSystemPath
                + "/loginChildSystem!createRandom.action?timeSign=" + timeSign;
        String result = requestInterface(url);
        TabSysUser user = (TabSysUser) request.getSession().getAttribute(
                CommonContant.SESSION_USERINFO);
        // DES 加密
        String userIdKey = StringUtil.DESEncrypt(user.getUserId());
        String pwKey = StringUtil.DESEncrypt(user.getPassword());
        String ticket = StringUtil.DESEncrypt(result);
        StringBuffer rUrl = new StringBuffer();
        rUrl.append(request.getScheme())
                .append("://")
                .append(request.getServerName())
                .append(":")
                .append(request.getServerPort())
                .append("/")
                .append(childSystemPath)
                .append("/loginChildSystem!loginForChild.action?timeSign="
                        + timeSign + "&userId="
                        + URLEncoder.encode(userIdKey, "utf-8") + "&password="
                        + URLEncoder.encode(pwKey, "utf-8") + "&sys_id="
                        + sys_id + "&ticket="
                        + URLEncoder.encode(ticket, "utf-8"));
        response.sendRedirect(rUrl.toString());
    }

    /**
     * @Title: createRandom
     * @Description: 返回随机字符串, 用于秘钥验证
     * @create 2017-5-24 上午9:13:58
     * @update 2017-5-24 上午9:13:58
     */
    @RequestMapping(value = "/loginChildSystem!createRandom.action")
    public String createRandom(HttpServletRequest request,
                               HttpServletResponse response) {
        PrintWriter out;
        try {
            out = response.getWriter();
            String timeSign = request.getParameter("timeSign");
            String randomStr = StringUtil.getRandomName(8);
            randomMap.put(timeSign, randomStr);
            out.write(randomStr);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("异常", e);
        }
        return null;
    }

    @RequestMapping(value = "/loginChildSystem!loginForChild.action")
    public String loginForChild(HttpServletRequest request) throws Exception {
        TabSysUser user = new TabSysUser();
        String timeSign = request.getParameter("timeSign");
        String userId = request.getParameter("userId");
        String pw = request.getParameter("password");
        String sys_id = request.getParameter("sys_id");
        request.getSession().setAttribute("sys_id", sys_id);
        String ticket = request.getParameter("ticket");
        if (StringUtils.isEmpty(timeSign) || StringUtils.isEmpty(userId)
                || StringUtils.isEmpty(pw) || StringUtils.isEmpty(ticket)) {
            // 参数不全
        }
        String randomStr = randomMap.get(timeSign);
        randomMap.remove(timeSign);
        if (randomStr != null) {
            String ticketTrue = StringUtil.DESEncrypt(randomStr);
            if (!ticket.equals(ticketTrue)) {
                // 验证票据不正确
            }
        }
        String userIdTrue = StringUtil.DESDecrypt(userId);
        String pwTrue = StringUtil.DESDecrypt(pw);
        user = loginService.findByUserId(userIdTrue);
        if (user.getPassword().equals(pwTrue)) {
            request.getSession().setAttribute(CommonContant.SESSION_USERINFO,
                    user);
            initCommon(request, user);
        } else {
            // 密码错误
        }
        return "redirect:/common/login/loginSub.do";
    }

    // 登入成功后初始化参数
    private void initCommon(HttpServletRequest request, TabSysUser user) {
        boolean isNoAdmin = loginService.getRoleByUserId(user.getUserId());
        if (isNoAdmin) { // 非管理员
            // 根据登录用户查询具有权限的serviceCode列表
//			String purService = loginService.findServicePurview(user
//					.getUserId());
//			request.getSession().setAttribute("purService", purService);

//			Map<String, String> purCodeMap = loginService
//					.findAllRoomName(purService);
//			request.getSession().setAttribute("purCodeMap", purCodeMap);
        }
        request.getSession().setAttribute(CommonContant.NOT_ADMIN, isNoAdmin);
        request.getSession().setAttribute(CommonContant.USER_ID,
                user.getUserId());
        // 哨兵封堵ip地址
        String shaobincenterip = loginService.getConfigValue("netGuardIp");
        CommonContant.SHAOBIN_CENTER_IP = shaobincenterip;
        // 读取配置值并修改常量值
        //loginService.updateConstant();

        // 读取统计日期配置值并修改常量
        ConfigLoadUtil conf = SpringContextUtil.getBean("configLoadUtil");
        String statisticsDay = conf.getStatisticsDay();
        CommonContant.DISPLAY_DATE_RANGE = Integer.valueOf(statisticsDay);

        request.getSession().setAttribute(CommonContant.LOCAL_IP,
                request.getParameter("local_ip"));
        request.getSession().setAttribute(CommonContant.LOCAL_MAC,
                request.getParameter("local_mac"));
        request.getSession().setAttribute(CommonContant.LOCAL_MACHINE,
                request.getParameter("local_machine"));

    }

    // 访问接口，获取返回值
    private static String requestInterface(String url) {
        String strJson = "";
        URL myURL = null;
        URLConnection httpsConn = null;
        try {
            myURL = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
        try {
            httpsConn = (URLConnection) myURL.openConnection();
            if (httpsConn != null) {
                InputStreamReader insr = new InputStreamReader(
                        httpsConn.getInputStream(), "UTF-8");
                BufferedReader br = new BufferedReader(insr);
                String line = null;
                StringBuilder sb = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                strJson = sb.toString();
                insr.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return strJson;
    }
}
