const garbageData =
    [{
      path: '/act-monitor-web/monitor/garbageData/initialize.do',
      data: {
        'success': [
          {
            'value': 1,
            'label': '测试数据'
          }
        ]
      }
    }, {
      path: '/act-monitor-web/monitor/garbageData/pagingList.do',
      data: {
        'success': {
          'pageIndex': 0,
          'pageSize': 15,
          'rows': [
            {
              'garbageId': 1,
              'seleteSql': "select  startIp as '起始IP',endIp as '终止IP' from t_common_test",
              'deleteSql': 'select * from t_common_test',
              'remark': '测试数据',
              'sqlCount': 2,
              'invalid': null,
              'garbageDataType': null
            }
          ],
          'total': 1,
          'totalPageCount': 1
        }
      }
    }, {
      path: '/act-monitor-web/monitor/garbageData/pagingGarbage.do',
      data: {
        'success': {
          'simpleDataQuery': null,
          'exportType': null,
          'fileName': null,
          'uploadInfo': null,
          'reportSql': null,
          'page': {
            'pageIndex': 0,
            'pageSize': 15,
            'rows': [
              {
                '起始IP': '192.168.0.0',
                '终止IP': '192.168.0.1'
              },
              {
                '起始IP': '192.169.2.0',
                '终止IP': '192.169.3.0'
              }
            ],
            'total': 2,
            'totalPageCount': 1
          },
          'title': [
            '起始IP',
            '终止IP'
          ]
        }
      }
    }
    ]

export default garbageData
// module.exports = garbageData;
