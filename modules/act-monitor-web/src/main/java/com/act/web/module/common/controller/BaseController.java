/**
 * @Title: LoginController.java
 * @Package: com.act.web.system.common.controller
 * @Description: (基础controller，用于存放controller中公用数据)
 * @author fmj
 * @date 2017-2-6 下午3:59:39
 * @version V1.0
 */
package com.act.web.module.common.controller;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

public class BaseController extends MultiActionController {


    private final Logger log = LoggerFactory.getLogger(BaseController.class);
    protected int pageSize = 10;

    // 操作状态（错误、成功）
    protected enum Status {
        error, success
    }

    // 根据操作状态、消息内容输出AJAX
    protected Map<String, Object> ajax(Status status) {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put(status.toString(), true);
        return jsonMap;
    }

    // 根据操作状态、消息内容输出AJAX
    protected Map<String, Object> ajax(Status status, Object message) {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put(status.toString(), message);
        return jsonMap;
    }

    /**
     * 解决mvc，前台提交时间无法访问的问题，需要统一格式
     *
     * @param binder
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                new SimpleDateFormat("yyyy-MM-dd"), true));
    }

    /**
     * @Title: getPurviewMap
     * @Description: 获取菜单显示权限 根据program_id 在Tab_Secondary_Menu中 找到按钮应该加载的权限
     * @create 2017-5-15 上午9:02:40
     * @update 2017-5-15 上午9:02:40
     */
    protected List<String> getPurviewMap(HttpServletRequest request,
                                         String programId) {
        Map<String, List<String>> secMenuPurview = (Map<String, List<String>>) request
                .getSession().getAttribute("secMenuPurview");
        List<String> result = secMenuPurview.get(programId);
        return result;
    }

    /**
     *
     * @Description: 导入excle模板下载
     * @param request
     *            获取系统路径
     * @param downFilePath
     *            文件下载路径
     * @param downFileName
     *            文件下载名
     * @param @throws IOException 设定文件
     * @return ResponseEntity<byte[]> 返回类型
     * @throws
     */
    protected ResponseEntity<byte[]> download(HttpServletRequest request,
                                              String downFilePath, String downFileName) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        String webPath = request.getSession().getServletContext()
                .getRealPath("/");
        String filePath = webPath + downFilePath;
        // 解决文件中文乱码
        String agent = request.getHeader("User-Agent").toLowerCase();
        if (agent != null
                && (agent.indexOf("msie") != -1 || (agent.indexOf("rv") != -1 && agent
                .indexOf("firefox") == -1))) {
            downFileName = URLEncoder.encode(downFileName + ".xls", "UTF-8");
        } else {
            downFileName = new String(
                    (downFileName + ".xls").getBytes("UTF-8"), "ISO-8859-1");
        }
        headers.setContentDispositionFormData("attachment", downFileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        File file = new File(filePath);
        byte[] bytes = FileUtils.readFileToByteArray(file);
        return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);

    }

    /**
     *
     * @Description: 根据 规定格式的表头 和数据 返回导出数据
     * @param dataList
     *            数据列表
     * @param dataHeaderStrRepost
     *            表头
     */
    protected <T> List<Object[]> initExport(List<T> dataList,
                                            String dataHeaderStrRepost) {
        try {
            List<Object[]> resultList = new ArrayList<Object[]>(); // 导出数据

            String[] headerTotalTemp = dataHeaderStrRepost.split("\\|");// 表头数组
            List<String> fieldList = new ArrayList<String>(); // 导出表头

            String[] fieldName = new String[headerTotalTemp.length]; // 标题数组
            for (int i = 0; i < headerTotalTemp.length; i++) {
                String[] attributeTemp = headerTotalTemp[i].split(":");
                fieldList.add(attributeTemp[0].trim());// 获取导出字段名
                fieldName[i] = attributeTemp[1].trim();// 导出标题
            }
            resultList.add(fieldName);
            for (int i = 0; i < dataList.size(); i++) {
                T info = dataList.get(i);
                Object[] resultObj = new Object[fieldList.size()];
                Class<?> cls = info.getClass();
                for (int j = 0; j < fieldList.size(); j++) {
                    Method m = cls.getMethod("get" + fieldList.get(j));
                    resultObj[j] = m.invoke(info);
                }
                resultList.add(resultObj); // 将结果集加入list中
            }
            return resultList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
