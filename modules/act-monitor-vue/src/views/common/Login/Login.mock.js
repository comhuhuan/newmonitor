const Login =
    [{
      path: '/act-monitor-web/common/loginVue/login.do',
      data: {
        'code': 200,
        'msg': '请求成功',
        'user': {
          'userId': 'admin',
          'password': 'e10adc3949ba59abbe56e057f20f883e',
          'username': '系统管理员',
          'serviceCode': '00000000000000',
          'sex': true,
          'tel': '',
          'mobile': '15933333333',
          'email': 'machuan@act-telecom.com',
          'descrip': '',
          'state': false,
          'lastTime': 1495008703000,
          'loginAmount': 6637,
          'isHint': false,
          'question': '',
          'answer': '',
          'createuser': 'admin',
          'sysmanage': '1|2|3|4|5|6|7|16',
          'valueChanged': false
        },
        'authorization': [
          {
            'path': '/',
            'component': 'common/Home/Home',
            'name': '样例1',
            'iconCls': 'fa fa-hand-peace-o fa-lg',
            'hidden': false,
            'children': [
              {
                'path': '/sql_engine',
                // "component": "monitor/SqlEngine/sqlEngine",
                'component': 'xxx/SqlEngine/sqlEngine',
                'name': '分页样例',
                'iconCls': 'fa fa-arrow-circle-right fa-lg',
                'hidden': false,
                'children': null
              }
            ]
          },
          {
            'path': '/',
            'component': 'common/Home/Home',
            'name': '样例2',
            'iconCls': 'fa fa-hand-peace-o fa-lg',
            'hidden': false,
            'children': [
              {
                'path': '/garbage_data',
                // "component": "monitor/GarbageData/garbageData",
                'component': 'xxx/GarbageData/garbageData',
                'name': '动态sql',
                'iconCls': 'fa fa-arrow-circle-right fa-lg',
                'hidden': false,
                'children': null
              }
            ]
          },
          {
            'path': '/',
            'component': 'common/Home/Home',
            'name': '样例3',
            'iconCls': 'fa fa-hand-peace-o fa-lg',
            'hidden': false,
            'children': [
              {
                'path': '/test',
                'component': 'common/Test/test',
                'name': 'test样例',
                'iconCls': 'fa fa-arrow-circle-right fa-lg',
                'hidden': false,
                'children': null
              }
            ]
          },
          {
            'path': '/',
            'component': 'common/Home/Home',
            'name': '样例4',
            'iconCls': 'fa fa-hand-peace-o fa-lg',
            'hidden': false,
            'children': [
              {
                'path': '/echarts',
                'component': 'common/Echarts/echarts',
                'name': 'echarts页面',
                'iconCls': 'fa fa-arrow-circle-right fa-lg',
                'hidden': false,
                'children': null
              }
            ]
          },
          {
            'path': '/',
            'component': 'common/Home/Home',
            'name': '监控子系统',
            'iconCls': 'fa fa-eye-slash fa-lg',
            'hidden': false,
            'children': [
              {
                'path': '/allEquip',
                'component': 'monitor/allEquip/allEquip',
                'name': '全国统计图',
                'iconCls': 'fa fa-globe fa-lg',
                'hidden': false,
                'children': null
              }, {
                'path': '/deviceStatus',
                'component': 'monitor/deviceStatus/deviceStatus',
                'name': 'IDC设备状态统计',
                'iconCls': 'fa fa-cube fa-lg',
                'hidden': true,
                'children': null
              }, {
                'path': '/deviceRelation',
                'component': 'monitor/deviceRelation/deviceRelation',
                'name': '设备状态关系图',
                'iconCls': 'fa fa-joomla fa-lg',
                'hidden': false,
                'children': null
              }, {
                'path': '/deviceStatistics',
                'component': 'monitor/deviceStatistics/deviceStatistics',
                'name': '设备状态统计',
                'iconCls': 'fa fa-bar-chart-o',
                'hidden': false,
                'children': null
              }, {
                'path': '/deviceSearch',
                'component': 'monitor/deviceSearch/deviceSearch',
                'name': '设备搜索',
                'iconCls': 'fa fa-search fa-lg',
                'hidden': false,
                'children': null
              }, {
                'path': '/userManage',
                'component': 'monitor/userManage/userManage',
                'name': '用户管理',
                'iconCls': 'fa fa-group fa-lg',
                'hidden': false,
                'children': null
              }, {
                'path': '/alarm',
                'component': 'monitor/alarm/alarmManage',
                'name': '告警信息',
                'iconCls': 'fa fa-group fa-lg',
                'hidden': false,
                'children': null
              }
            ]
          }
        ],
        'unitSystemVersion': {
          'name': 'COMMON',
          'packetType': 'ACT-IDCNT',
          'systemVersion': '1.0.0.0',
          'svnVersion': '0',
          'timestamp': 1494998715000,
          'descSys': '界面样例系统',
          'valueChanged': false
        },
        'tabSysManageInfo': {
          'syamanageId': 3,
          'sysmanageName': '监控子系统',
          'picname': '/images/login/m9.png',
          'pathurl': '',
          'opentype': 'self',
          'showMk': 'Y',
          'childSystemPath': 'act-monitor-web',
          'valueChanged': false
        },
        'secMenuPurview': {
          'test1': [
            'query',
            'import',
            'export',
            'add',
            'modify',
            'delete',
            'block',
            'operate'
          ],
          'test': [
            'query',
            'import',
            'export',
            'add',
            'modify',
            'delete',
            'block',
            'operate'
          ],
          'echarts': [
            'query',
            'import',
            'export',
            'add',
            'modify',
            'delete',
            'block',
            'operate'
          ],
          'garbage_data': [
            'query',
            'import',
            'export',
            'add',
            'modify',
            'delete',
            'block',
            'operate'
          ],
          'test4': [
            'query',
            'import',
            'export',
            'add',
            'modify',
            'delete',
            'block',
            'operate'
          ],
          'test2': [
            'query',
            'import',
            'export',
            'add',
            'modify',
            'delete',
            'block',
            'operate'
          ],
          'test3': [
            'query',
            'import',
            'export',
            'add',
            'modify',
            'delete',
            'block',
            'operate'
          ],
          'sql_engine': [
            'query',
            'import',
            'export',
            'add',
            'modify',
            'delete',
            'block',
            'operate'
          ]
        }
      }
    }]
export default Login
// module.exports = Login;
