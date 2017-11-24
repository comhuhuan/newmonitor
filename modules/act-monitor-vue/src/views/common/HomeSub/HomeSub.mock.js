const HomeSub = [{
  path: '/act-monitor-web/common/login/loadSub.do',
  data: {
    'code': null,
    'msg': null,
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
      'lastTime': 1495009707000,
      'loginAmount': 6642,
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
export default HomeSub
// module.exports = HomeSub;
