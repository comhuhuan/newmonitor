const sqlEngine =
    [{
      path: '/act-monitor-web/monitor/sqlEngine/pagingList.do',
      data: {
        'success': {
          'pageIndex': 1,
          'pageSize': 15,
          'rows': [
            {
              'simpleDataQuery': null,
              'exportType': null,
              'fileName': null,
              'uploadInfo': null,
              'engineId': 2,
              'sqlSentence': "SELECT A.ipID, concat(startIP,'-',endIP) as '起始IP段', A.service_code  as '机房编号'  , ( SELECT B.service_name FROM  tab_service_information B WHERE A.service_code =B.service_code ) as  '机房名称'  FROM ismsx_ipseginfo  A WHERE  A.type != 0 AND A.usertype = 1  AND  concat(A.startIP,'-',A.endIP) IN ( SELECT  concat(startIP,'-',endIP) iplist  FROM ismsx_ipseginfo C  WHERE  C.usertype = 2 )",
              'remark': '机房填报非静态IP段但有用户使用的IP段\r\n',
              'createDate': 1494132815000,
              'updateDate': 1494219222000,
              'creator': '系统管理员',
              'modifier': '系统管理员',
              'sqlSentenceQuery': null,
              'remarkQuery': null,
              'createDateQuery': null,
              'updateDateQuery': null,
              'creatorQuery': null,
              'modifierQuery': null
            },
            {
              'simpleDataQuery': null,
              'exportType': null,
              'fileName': null,
              'uploadInfo': null,
              'engineId': 3,
              'sqlSentence': "SELECT A.ipID, concat(startIP,'-',endIP) as '起始IP段' , A.service_code as '机房编号' , ( SELECT B.service_name FROM  tab_service_information B WHERE A.service_code =B.service_code ) as  '机房名称'  FROM ismsx_ipseginfo  A WHERE  A.usertype = 2  AND  concat(A.startIP,'-',A.endIP) NOT IN ( SELECT  concat(startIP,'-',endIP) iplist  FROM ismsx_ipseginfo C  WHERE  C.usertype = 1 )",
              'remark': '用户使用IP段,但机房没有对应IP段\r\n',
              'createDate': 1494132815000,
              'updateDate': 1494219222000,
              'creator': '系统管理员',
              'modifier': '系统管理员',
              'sqlSentenceQuery': null,
              'remarkQuery': null,
              'createDateQuery': null,
              'updateDateQuery': null,
              'creatorQuery': null,
              'modifierQuery': null
            },
            {
              'simpleDataQuery': null,
              'exportType': null,
              'fileName': null,
              'uploadInfo': null,
              'engineId': 4,
              'sqlSentence': "SELECT count(1) AS cunt, concat(startIP,'-',endIP) as '起始IP段', A.service_code as '机房编号', ( SELECT B.service_name FROM  tab_service_information B WHERE A.service_code =B.service_code ) as  '机房名称', ( CASE A.usertype WHEN 1 THEN '机房IP段'  WHEN 2 THEN '用户IP段' ELSE '未知类型' END ) as '类型'  FROM ismsx_ipseginfo  A WHERE 1   GROUP BY  A.usertype , concat(startIP,'-',endIP)  HAVING  cunt > 1 ORDER BY cunt DESC ",
              'remark': '用户、机房填报的 重复IP段\r\n',
              'createDate': 1494132815000,
              'updateDate': 1494219222000,
              'creator': '系统管理员',
              'modifier': '系统管理员',
              'sqlSentenceQuery': null,
              'remarkQuery': null,
              'createDateQuery': null,
              'updateDateQuery': null,
              'creatorQuery': null,
              'modifierQuery': null
            },
            {
              'simpleDataQuery': null,
              'exportType': null,
              'fileName': null,
              'uploadInfo': null,
              'engineId': 5,
              'sqlSentence': "SELECT cabid, service_code as '机房编号',( SELECT service_name FROM  tab_service_information WHERE tab_service_information.service_code =res_cabinets.service_code ) as  '机房名称'  ,cabcode as '机柜编号', cabname as '机柜名称',  ( SELECT roomname FROM  res_rooms WHERE res_rooms.roomid =res_cabinets.roomid ) as  '机房区域名称' , ( CASE assign_status WHEN '1' THEN '未分配'  WHEN '2' THEN '已分配' ELSE  '未知' END ) as '分配状态' , ( CASE status WHEN '0' THEN '空'  WHEN '1' THEN '预留' WHEN '2' THEN '占用' ELSE  '未知' END ) as '使用状态'   FROM res_cabinets  WHERE  status = 2  AND cabid  NOT IN (SELECT  cabid  FROM res_servers)",
              'remark': '机架位状态是已占用,但实际未有用户使用的机架位',
              'createDate': 1494132815000,
              'updateDate': 1494219222000,
              'creator': '系统管理员',
              'modifier': '系统管理员',
              'sqlSentenceQuery': null,
              'remarkQuery': null,
              'createDateQuery': null,
              'updateDateQuery': null,
              'creatorQuery': null,
              'modifierQuery': null
            },
            {
              'simpleDataQuery': null,
              'exportType': null,
              'fileName': null,
              'uploadInfo': null,
              'engineId': 6,
              'sqlSentence': "SELECT  custid,  ( CASE usertype WHEN '1' THEN '提供应用服务的用户'  WHEN '2' THEN '其他用户' ELSE '未知类型' END ) as '用户类型' ,  name , ( CASE IDtype WHEN '1' THEN '工商营业执照号码'  WHEN '2' THEN '身份证'  WHEN '3' THEN '组织机构代码证书'  WHEN '4' THEN '事业法人证书'  WHEN '5' THEN '军队代号'  WHEN '6' THEN '社团法人证书'  WHEN '7' THEN '护照' WHEN '8' THEN '军官证' WHEN '9' THEN '台胞证'   WHEN '999' THEN '其他'   ELSE  '未知' END ) AS '证件类型'  ,IDNumber AS '证件号码'  FROM   res_customer   WHERE   CONCAT( IDtype,'#', IDNumber)  IN  (  SELECT CONCAT( IDtype,'#', IDNumber) AS IDm  FROM (  SELECT  COUNT(1) AS cunt ,  IDtype ,IDNumber   FROM   res_customer  GROUP BY  IDtype ,IDNumber  HAVING  cunt >1 ORDER BY  cunt DESC  ) AS  TABW )",
              'remark': '用户证件类型,证件号相同的用户统计(不一定违规,目前是用户名,类型,证件号,证件类型判断唯一)',
              'createDate': 1494132815000,
              'updateDate': 1494219222000,
              'creator': '系统管理员',
              'modifier': '系统管理员',
              'sqlSentenceQuery': null,
              'remarkQuery': null,
              'createDateQuery': null,
              'updateDateQuery': null,
              'creatorQuery': null,
              'modifierQuery': null
            },
            {
              'simpleDataQuery': null,
              'exportType': null,
              'fileName': null,
              'uploadInfo': null,
              'engineId': 13,
              'sqlSentence': 'select * from tab_menu',
              'remark': '菜单列表',
              'createDate': 1494418991000,
              'updateDate': 1494580005000,
              'creator': '系统管理员',
              'modifier': '系统管理员',
              'sqlSentenceQuery': null,
              'remarkQuery': null,
              'createDateQuery': null,
              'updateDateQuery': null,
              'creatorQuery': null,
              'modifierQuery': null
            }
          ],
          'total': 7,
          'totalPageCount': 1
        }
      }
    }

    ]

export default sqlEngine
