package com.act.web.module.monitor.service.impl;

import com.act.framework.util.DbUtil;
import com.act.framework.util.PageResult;
import com.act.monitor.model.CuSysConfig;
import com.act.monitor.model.TabInstructionsInfo;
import com.act.monitor.model.WebSysConfig;
import com.act.web.module.monitor.service.IssuedInstructionsService;
import com.act.web.module.monitor.vo.IssuedInstructionsVo;
import com.act.web.util.EuSysConfigUtil;
import com.act.web.util.WebSysConfigUtil;
import com.act.ws.localhost.monitorwebservice.monitorcommand_wsdl.MonitorPortType;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.xml.namespace.QName;
import javax.xml.ws.WebServiceException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;


/**
 * @author hh
 * The type Issued instructions service.
 */
@Service
public class IssuedInstructionsServiceImpl implements IssuedInstructionsService {
    private final org.slf4j.Logger log = LoggerFactory.getLogger(IssuedInstructionsServiceImpl.class);
    volatile ConcurrentLinkedQueue<String> twice = new ConcurrentLinkedQueue<>();

    /**
     * 指令下发  0：成功；1：失败；2：调用接口失败；
     *
     * @param issuedInstructionsVo
     * @return
     * @throws ClassNotFoundException
     */

    @Override
    public String issuedInstructions(IssuedInstructionsVo issuedInstructionsVo) throws ClassNotFoundException {
        List<Future<String>> results = new ArrayList<Future<String>>();
        final String type = issuedInstructionsVo.getType();
        final String xml = "EU".equals(type) ? getEuXMl() : "CU".equals(type) ? getCuXml() : getWebXml();
        List<String> ipList = getIpList(type);
        String ws_interface = EuSysConfigUtil.loadConfig(EuSysConfigUtil.EU_WS_INTERFACE);
        LinkedBlockingQueue<String> webServiceAddress = getWebServiceAddress(ipList, ws_interface);
        String result = "";
        final Date date = new Date();
        log.info("xml:{},ip:{},ws:{}",xml, StringUtils.join(ipList.toArray()) ,StringUtils.join(webServiceAddress.toArray()));


        int cycleNum = webServiceAddress.size();
        ExecutorService executor = Executors.newFixedThreadPool(webServiceAddress.size() / 100 < 4 ? 4 : webServiceAddress.size() / 100);
        ExecutorCompletionService completionService = new ExecutorCompletionService<>(executor);
        for (int i = 0; i < cycleNum; i++) {
            final String wsaddr = webServiceAddress.poll();
            if (wsaddr != null) {
                Callable<String> task = new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        URL url;
                        String result = "";
                        try {
                            url = new URL(wsaddr);
                        } catch (MalformedURLException e) {
                            log.error("接口调用失败!");
                            e.printStackTrace();
                            return "接口调用失败!";
                        }
                        QName qName = null;
                        try {
                            qName = new QName("http://localhost//MonitorWebservice/monitorCommand?wsdl", "Monitor");

                            javax.xml.ws.Service service = javax.xml.ws.Service.create(url, qName);
                            MonitorPortType monitorPortType = service.getPort(new QName("http://localhost//MonitorWebservice/monitorCommand?wsdl", "Monitor"), MonitorPortType.class);
                            result = monitorPortType.monitorCommand(xml);
                            //状态码为1的时候表示下发失败
                            if ("1".equals(result)) {
                                boolean add = twice.add(wsaddr);
                            }
                            TabInstructionsInfo tabInstructionsInfo = new TabInstructionsInfo();
                            tabInstructionsInfo.setIp(wsaddr);
                            tabInstructionsInfo.setDate(date);
                            tabInstructionsInfo.setStatus(result);
                            tabInstructionsInfo.setType(type);
                            TabInstructionsInfo.getDao().insert(tabInstructionsInfo);
                            log.debug("{}下发状态为{}，已记录",wsaddr,result);

                        } catch (WebServiceException e) {
                            //状态码为2的时候为未成功连接
                            log.debug("调用接口失败：" + e);
                            e.printStackTrace();
                            TabInstructionsInfo tabInstructionsInfo = new TabInstructionsInfo();
                            tabInstructionsInfo.setIp(wsaddr);
                            tabInstructionsInfo.setDate(date);
                            tabInstructionsInfo.setStatus("2");
                            tabInstructionsInfo.setType(type);
                            TabInstructionsInfo.getDao().insert(tabInstructionsInfo);
                            log.debug("{}下发状态为{}，已记录",wsaddr,result);

                            boolean add = twice.add(wsaddr);
                        }
                        return result;
                    }
                };
                Future<String> submit = completionService.submit(task);
                //results.add(submit);
                //try {
                //    String o = submit.get();
                //    log.debug(o);
                //} catch (InterruptedException e) {
                //    e.printStackTrace();
                //} catch (ExecutionException e) {
                //    e.printStackTrace();
                //}
                //executor.shutdown();
            }
        }

        //int count = 0;
        //for (int i = 0; i < results.size(); i++) {
        //    Future<String> stringFuture = results.get(i);
        //    try {
        //        String s = stringFuture.get();
        //        if ("0".equals(s)) {
        //            count++;
        //        }
        //    } catch (InterruptedException e) {
        //        e.printStackTrace();
        //    } catch (ExecutionException e) {
        //        e.printStackTrace();
        //    }
        //}
        //todo 页面显示上次下发时间

        //if (count == 0) {
        //    return "下发指令全部失败";
        //} else if (count == results.size()) {
        //    return "下发指令全部成功";
        //} else {
        //    return "下发部分成功：成功" + count + "个，失败" + (results.size() - count) + "个";
        //}


        return "指令全部下发";

    }


    /**
     * 前端详情分页展示
     *
     * @param page
     * @param issuedInstructionsVo
     * @return
     */

    @Override
    public PageResult<TabInstructionsInfo> instructionsInfo(PageResult<TabInstructionsInfo> page, IssuedInstructionsVo issuedInstructionsVo) {
        Object[] param = {issuedInstructionsVo.getType(), issuedInstructionsVo.getType()};

        String sql = "select  * from tab_instructions_info where date=(select   date  from   tab_instructions_info  WHERE type = ?  order   by    date  desc   limit   1) AND `status`!='0'  and type=?";
        page = DbUtil.queryPageForObjectPageResult(sql, TabInstructionsInfo.class, page.getPageIndex(), page.getPageSize(), param);
//		List<TabInstructionsInfo> tabInstructionsInfoList = DbUtil.queryForObjectList(sql, TabInstructionsInfo.class, param);
        return page;
    }


    /**
     * 取得euIP  eu: tab_interface_config    cu: isms_device_info
     *
     * @param type the type
     * @return connect ucenter
     * @throws ClassNotFoundException the class not found exception
     */
    public List<String> getIpList(String type) throws ClassNotFoundException {
        String ucenter_db_ip = WebSysConfigUtil.loadConfig(WebSysConfigUtil.UCENTER_DB_IP);
        String ucenter_db_name = WebSysConfigUtil.loadConfig(WebSysConfigUtil.UCENTER_DB_NAME);
        String ucenter_db_passwd = WebSysConfigUtil.loadConfig(WebSysConfigUtil.UCENTER_DB_PASSWD);
        String ucenter_db_port = WebSysConfigUtil.loadConfig(WebSysConfigUtil.UCENTER_DB_PORT);
        String ucenter_db_user = WebSysConfigUtil.loadConfig(WebSysConfigUtil.UCENTER_DB_USER);
        ArrayList<String> eu = new ArrayList();

        String url = "jdbc:mysql://" + ucenter_db_ip + ":" + ucenter_db_port + "/" + ucenter_db_name;
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            con = DriverManager.getConnection(url, ucenter_db_user, ucenter_db_passwd);
            statement = con.createStatement();
            if ("EU".equals(type)) {
                String sql = " SELECT interface_ws_url FROM `tab_interface_config` where interface_type=1;";
                resultSet = statement.executeQuery(sql);

                while (resultSet.next()) {
                    String interface_ws_url = resultSet.getString("interface_ws_url");
                    log.debug("获得Eu接口" + interface_ws_url);
                    String[] split = interface_ws_url.split(":");
                    String euIP = split[0] + ":" + split[1];
//				String[] split1 = split[1].toString().split("/");
//				String euIP = split1[2].toString();
                    //			http://127.0.0.1:33262/IDCWebService/idcCommand?wsdl
                    eu.add(euIP);
                }
            }
            if ("CU".equals(type)) {
                String sql = " SELECT manage_ip FROM `isms_device_info`;";
                resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                    String interface_ws_url = resultSet.getString("manage_ip");
                    log.debug("获得Cu接口" + interface_ws_url);
                    String euip = "http://" + interface_ws_url;
                    eu.add(euip);

                }
            }
            if ("WEB".equals(type)) {

                String euip = "http://127.0.0.1";
                eu.add(euip);
            }

        } catch (SQLException e) {
            log.debug("信安数据库链接失败" + e);
        } finally {
            if (statement != null) try {
                if (statement != null) statement.close();
                if (resultSet != null) resultSet.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                log.debug("关闭连接失败");
                e.printStackTrace();
            }
        }
        return eu;
    }


    /**
     * 拼装xml准备下发
     *
     * @return xml xml
     */
    public String getEuXMl() {
        StringBuffer sb = new StringBuffer();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sb.append("<EuConfigs>");
        sb.append("<EU_CU_COMM_STAT>");
        sb.append("<bak_flag>").append(EuSysConfigUtil.loadConfig("1000_bak_flag")).append("</bak_flag>");
        sb.append("<bak_path>").append(EuSysConfigUtil.loadConfig("1000_bak_path")).append("</bak_path>");
        sb.append("<codeUpPath>").append(EuSysConfigUtil.loadConfig("1000_codeUpPath")).append("</codeUpPath>");
        sb.append("<cycle_time>").append(EuSysConfigUtil.loadConfig("1000_cycle_time")).append("</cycle_time>");
        sb.append("<EnableSwitch>").append(EuSysConfigUtil.loadConfig("1000_EnableSwitch")).append("</EnableSwitch>");
        sb.append("<filenamekey>").append(EuSysConfigUtil.loadConfig("1000_filenamekey")).append("</filenamekey>");
        sb.append("<srcpath>").append(EuSysConfigUtil.loadConfig("1000_srcpath")).append("</srcpath>");
        sb.append("<uploadPath>").append(EuSysConfigUtil.loadConfig("1000_uploadPath")).append("</uploadPath>");
        sb.append("</EU_CU_COMM_STAT>");
        sb.append("<DEVICE_STAT>");
        sb.append("<bak_flag>").append(EuSysConfigUtil.loadConfig("1001_bak_flag")).append("</bak_flag>");
        sb.append("<bak_path>").append(EuSysConfigUtil.loadConfig("1001_bak_path")).append("</bak_path>");
        sb.append("<codeUpPath>").append(EuSysConfigUtil.loadConfig("1001_codeUpPath")).append("</codeUpPath>");
        sb.append("<cycle_time>").append(EuSysConfigUtil.loadConfig("1001_cycle_time")).append("</cycle_time>");
        sb.append("<EnableSwitch>").append(EuSysConfigUtil.loadConfig("1001_EnableSwitch")).append("</EnableSwitch>");
        sb.append("<filenamekey>").append(EuSysConfigUtil.loadConfig("1001_filenamekey")).append("</filenamekey>");
        sb.append("<srcpath>").append(EuSysConfigUtil.loadConfig("1001_srcpath")).append("</srcpath>");
        sb.append("<uploadPath>").append(EuSysConfigUtil.loadConfig("1001_uploadPath")).append("</uploadPath>");
        sb.append("</DEVICE_STAT>");
        sb.append("<PROCESS_STAT>");
        sb.append("<bak_flag>").append(EuSysConfigUtil.loadConfig("1002_bak_flag")).append("</bak_flag>");
        sb.append("<bak_path>").append(EuSysConfigUtil.loadConfig("1002_bak_path")).append("</bak_path>");
        sb.append("<codeUpPath>").append(EuSysConfigUtil.loadConfig("1002_codeUpPath")).append("</codeUpPath>");
        sb.append("<cycle_time>").append(EuSysConfigUtil.loadConfig("1002_cycle_time")).append("</cycle_time>");
        sb.append("<EnableSwitch>").append(EuSysConfigUtil.loadConfig("1002_EnableSwitch")).append("</EnableSwitch>");
        sb.append("<filenamekey>").append(EuSysConfigUtil.loadConfig("1002_filenamekey")).append("</filenamekey>");
        sb.append("<srcpath>").append(EuSysConfigUtil.loadConfig("1002_srcpath")).append("</srcpath>");
        sb.append("<uploadPath>").append(EuSysConfigUtil.loadConfig("1002_uploadPath")).append("</uploadPath>");
        sb.append("</PROCESS_STAT>");
        sb.append("<CPU_STAT>");
        sb.append("<bak_flag>").append(EuSysConfigUtil.loadConfig("1003_bak_flag")).append("</bak_flag>");
        sb.append("<bak_path>").append(EuSysConfigUtil.loadConfig("1003_bak_path")).append("</bak_path>");
        sb.append("<codeUpPath>").append(EuSysConfigUtil.loadConfig("1003_codeUpPath")).append("</codeUpPath>");
        sb.append("<cycle_time>").append(EuSysConfigUtil.loadConfig("1003_cycle_time")).append("</cycle_time>");
        sb.append("<EnableSwitch>").append(EuSysConfigUtil.loadConfig("1003_EnableSwitch")).append("</EnableSwitch>");
        sb.append("<filenamekey>").append(EuSysConfigUtil.loadConfig("1003_filenamekey")).append("</filenamekey>");
        sb.append("<srcpath>").append(EuSysConfigUtil.loadConfig("1003_srcpath")).append("</srcpath>");
        sb.append("<uploadPath>").append(EuSysConfigUtil.loadConfig("1003_uploadPath")).append("</uploadPath>");
        sb.append("</CPU_STAT>");
        sb.append("<DISK_STAT>");
        sb.append("<bak_flag>").append(EuSysConfigUtil.loadConfig("1004_bak_flag")).append("</bak_flag>");
        sb.append("<bak_path>").append(EuSysConfigUtil.loadConfig("1004_bak_path")).append("</bak_path>");
        sb.append("<codeUpPath>").append(EuSysConfigUtil.loadConfig("1004_codeUpPath")).append("</codeUpPath>");
        sb.append("<cycle_time>").append(EuSysConfigUtil.loadConfig("1004_cycle_time")).append("</cycle_time>");
        sb.append("<EnableSwitch>").append(EuSysConfigUtil.loadConfig("1004_EnableSwitch")).append("</EnableSwitch>");
        sb.append("<filenamekey>").append(EuSysConfigUtil.loadConfig("1004_filenamekey")).append("</filenamekey>");
        sb.append("<srcpath>").append(EuSysConfigUtil.loadConfig("1004_srcpath")).append("</srcpath>");
        sb.append("<uploadPath>").append(EuSysConfigUtil.loadConfig("1004_uploadPath")).append("</uploadPath>");
        sb.append("</DISK_STAT>");
        sb.append("<MEMORY_STAT>");
        sb.append("<bak_flag>").append(EuSysConfigUtil.loadConfig("1005_bak_flag")).append("</bak_flag>");
        sb.append("<bak_path>").append(EuSysConfigUtil.loadConfig("1005_bak_path")).append("</bak_path>");
        sb.append("<codeUpPath>").append(EuSysConfigUtil.loadConfig("1005_codeUpPath")).append("</codeUpPath>");
        sb.append("<cycle_time>").append(EuSysConfigUtil.loadConfig("1005_cycle_time")).append("</cycle_time>");
        sb.append("<EnableSwitch>").append(EuSysConfigUtil.loadConfig("1005_EnableSwitch")).append("</EnableSwitch>");
        sb.append("<filenamekey>").append(EuSysConfigUtil.loadConfig("1005_filenamekey")).append("</filenamekey>");
        sb.append("<srcpath>").append(EuSysConfigUtil.loadConfig("1005_srcpath")).append("</srcpath>");
        sb.append("<uploadPath>").append(EuSysConfigUtil.loadConfig("1005_uploadPath")).append("</uploadPath>");
        sb.append("</MEMORY_STAT>");
        sb.append("<RUNINFO_STAT>");
        sb.append("<bak_flag>").append(EuSysConfigUtil.loadConfig("1006_bak_flag")).append("</bak_flag>");
        sb.append("<bak_path>").append(EuSysConfigUtil.loadConfig("1006_bak_path")).append("</bak_path>");
        sb.append("<codeUpPath>").append(EuSysConfigUtil.loadConfig("1006_codeUpPath")).append("</codeUpPath>");
        sb.append("<cycle_time>").append(EuSysConfigUtil.loadConfig("1006_cycle_time")).append("</cycle_time>");
        sb.append("<EnableSwitch>").append(EuSysConfigUtil.loadConfig("1006_EnableSwitch")).append("</EnableSwitch>");
        sb.append("<filenamekey>").append(EuSysConfigUtil.loadConfig("1006_filenamekey")).append("</filenamekey>");
        sb.append("<srcpath>").append(EuSysConfigUtil.loadConfig("1006_srcpath")).append("</srcpath>");
        sb.append("<uploadPath>").append(EuSysConfigUtil.loadConfig("1006_uploadPath")).append("</uploadPath>");
        sb.append("</RUNINFO_STAT>");
        sb.append("<EU_DU_COMM_STAT>");
        sb.append("<bak_flag>").append(EuSysConfigUtil.loadConfig("1007_bak_flag")).append("</bak_flag>");
        sb.append("<bak_path>").append(EuSysConfigUtil.loadConfig("1007_bak_path")).append("</bak_path>");
        sb.append("<codeUpPath>").append(EuSysConfigUtil.loadConfig("1007_codeUpPath")).append("</codeUpPath>");
        sb.append("<cycle_time>").append(EuSysConfigUtil.loadConfig("1007_cycle_time")).append("</cycle_time>");
        sb.append("<EnableSwitch>").append(EuSysConfigUtil.loadConfig("1007_EnableSwitch")).append("</EnableSwitch>");
        sb.append("<filenamekey>").append(EuSysConfigUtil.loadConfig("1007_filenamekey")).append("</filenamekey>");
        sb.append("<srcpath>").append(EuSysConfigUtil.loadConfig("1007_srcpath")).append("</srcpath>");
        sb.append("<uploadPath>").append(EuSysConfigUtil.loadConfig("1007_uploadPath")).append("</uploadPath>");
        sb.append("</EU_DU_COMM_STAT>");
        sb.append("<LINK_STAT>");
        sb.append("<bak_flag>").append(EuSysConfigUtil.loadConfig("1008_bak_flag")).append("</bak_flag>");
        sb.append("<bak_path>").append(EuSysConfigUtil.loadConfig("1008_bak_path")).append("</bak_path>");
        sb.append("<codeUpPath>").append(EuSysConfigUtil.loadConfig("1008_codeUpPath")).append("</codeUpPath>");
        sb.append("<cycle_time>").append(EuSysConfigUtil.loadConfig("1008_cycle_time")).append("</cycle_time>");
        sb.append("<EnableSwitch>").append(EuSysConfigUtil.loadConfig("1008_EnableSwitch")).append("</EnableSwitch>");
        sb.append("<filenamekey>").append(EuSysConfigUtil.loadConfig("1008_filenamekey")).append("</filenamekey>");
        sb.append("<srcpath>").append(EuSysConfigUtil.loadConfig("1008_srcpath")).append("</srcpath>");
        sb.append("<uploadPath>").append(EuSysConfigUtil.loadConfig("1008_uploadPath")).append("</uploadPath>");
        sb.append("</LINK_STAT>");
        sb.append("<NETWORK_STAT>");
        sb.append("<bak_flag>").append(EuSysConfigUtil.loadConfig("1009_bak_flag")).append("</bak_flag>");
        sb.append("<bak_path>").append(EuSysConfigUtil.loadConfig("1009_bak_path")).append("</bak_path>");
        sb.append("<codeUpPath>").append(EuSysConfigUtil.loadConfig("1009_codeUpPath")).append("</codeUpPath>");
        sb.append("<cycle_time>").append(EuSysConfigUtil.loadConfig("1009_cycle_time")).append("</cycle_time>");
        sb.append("<EnableSwitch>").append(EuSysConfigUtil.loadConfig("1009_EnableSwitch")).append("</EnableSwitch>");
        sb.append("<filenamekey>").append(EuSysConfigUtil.loadConfig("1009_filenamekey")).append("</filenamekey>");
        sb.append("<srcpath>").append(EuSysConfigUtil.loadConfig("1009_srcpath")).append("</srcpath>");
        sb.append("<uploadPath>").append(EuSysConfigUtil.loadConfig("1009_uploadPath")).append("</uploadPath>");
        sb.append("</NETWORK_STAT>");
        sb.append("<FTP_INFO>");
        sb.append("<ftp_ip>").append(EuSysConfigUtil.loadConfig("ftp_ip")).append("</ftp_ip>");
        sb.append("<ftp_port>").append(EuSysConfigUtil.loadConfig("ftp_port")).append("</ftp_port>");
        sb.append("<ftp_user>").append(EuSysConfigUtil.loadConfig("ftp_user")).append("</ftp_user>");
        sb.append("<ftp_pwd>").append(EuSysConfigUtil.loadConfig("ftp_pwd")).append("</ftp_pwd>");
        sb.append("<ftp_type>").append(EuSysConfigUtil.loadConfig("ftp_type")).append("</ftp_type>");
        sb.append("<ftp_mode>").append(EuSysConfigUtil.loadConfig("ftp_mode")).append("</ftp_mode>");
        sb.append("</FTP_INFO>");
        sb.append("</EuConfigs>");
        log.debug("配置文件xml:{}" , sb.toString());
        return sb.toString();
    }


    /**
     * Gets cu xml.
     *
     * @return the cu xml
     */
    public String getCuXml() {
        List<CuSysConfig> sysConfigs = CuSysConfig.getDao().selectAll();
        Iterator<CuSysConfig> iterator = sysConfigs.iterator();
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sb.append("<CuConfigs>");
        while (iterator.hasNext()) {
            CuSysConfig config = iterator.next();
            sb.append("<").append(config.getConfigid().trim()).append(">")
                    .append(config.getConfigval().trim())
                    .append("</").append(config.getConfigid().trim()).append(">");
        }
        sb.append("</CuConfigs>");
        String xml = sb.toString();
        log.debug("配置文件xml:{}", xml);
        return xml;
    }

    /**
     * 拼装下发的xml
     *
     * @return 下发的xml
     */
    public String getWebXml() {
        List<WebSysConfig> sysConfigs = WebSysConfig.getDao().selectAll();
        Iterator<WebSysConfig> iterator = sysConfigs.iterator();
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sb.append("<WebConfigs>");
        while (iterator.hasNext()) {
            WebSysConfig config = iterator.next();
            sb.append("<").append(config.getConfigid().trim()).append(">")
                    .append(config.getConfigval().trim())
                    .append("</").append(config.getConfigid().trim()).append(">");
        }
        sb.append("</WebConfigs>");
        String xml = sb.toString();
        log.debug("配置文件xml:{}" ,xml);
        return xml;
    }

    /**
     * 拼装ws地址
     *
     * @param ipList       the ip list
     * @param ws_interface the ws interface
     * @return the web service address
     */
    public LinkedBlockingQueue<String> getWebServiceAddress(List<String> ipList, String ws_interface) {
//		ArrayList<String> wsAddress = new ArrayList<>();
        LinkedBlockingQueue<String> wsAddress = new LinkedBlockingQueue<>();
        for (String list : ipList) {
            wsAddress.add(list + ws_interface);
        }
        return wsAddress;
    }
}
