export default [
  {
    path: '/act-monitor-web/monitor/alarm/alarmList.do',
    data: {
      'success': {
        'rows': [
          {
            'childclass': '测试内容qu08',
            'host': '测试内容n6lg',
            'occurtime': '测试内容xd35',
            'parentclass': 71474,
            'status': '测试内容du28',
            'type': '测试内容0529',
            'valid': 47663,
            'value': '测试内容312f'
          }
        ],
        'total': 16535,
        'totalPageCount': 77223
      }
    }
  }, {
    path: '/act-monitor-web/monitor/user/getIdcList.do',
    data: {
	        'success|5': [
	        	{'idcname': '@word', 'uuid': '@id'}
	        ]
    }
  }, {
    path: '/act-monitor-web/monitor/user/remove.do',
    data: {
	        'success': '删除成功'
    }
  }, {
    path: '/act-monitor-web/monitor/user/addUser.do',
    data: {
	        'success': '新增成功'
    }
  }
]
