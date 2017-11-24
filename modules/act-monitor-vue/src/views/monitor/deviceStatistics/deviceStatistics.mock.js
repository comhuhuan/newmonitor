export default [
// module.exports = [
  {
    path: '/act-monitor-web/monitor/process/initializeTitle.do',
    data: {
	        'success': {
	        	'elementType': '@word',
	        	'euID': '@id',
	        	'cuID': '@id',
	        	'euIP': '@ip',
	        	'cuIP': '@ip',
	        	'houseName': '@cword',
	        	'soft_version': '@cword',
	        	'cpuTotalRate': '@cword',
	        	'cpuFrequcy': '@cword',
	        	'inflow': '@cword',
	        	// "mac": '@cword',
	        	// "cardName": '@cword',
	        	// "cardFunction|1-2": 1,
	        	// "connStatus": 'yes', // no
	        	// "bps": '@cword',
	        	'networkInfo|1-7': [
	        		{
	        			'mac': '@cword',
			        	'cardName': '@cword',
			        	'cardFunction|1-2': 1,
			        	'connStatus': 'yes', // no
			        	'bps': '@cword',
			        	'status|0-1': 0
	        		}
	        	],
	        	'memoryTotal': '@cword',
	        	'cpuModel': '@cword',
	        	'recordTime': '@datetime',
	        	'status|0-1': 0,
	        	'queryType': '@word',
	        	'reTimes|0-60': 60,
	        	'coreNum|1-30': [
	        		{'cpu|1-100': 1}
	        	],
	        	'cputotal_Rate|1-100': 100,
	        	'process|1-30': [
	        		{
	        			'lastReTime': '@datetime',
		        		'memory_use|0-100': 100,
		        		'pro_status|0-1': 1, // 0 正常 1异常
		        		'process_name': '@cword',
		        		'reTimes': '@datetime'
	        		}
	        	],
	        	'actLog_uploadNum': '',
	        	'monitorLog_uploadNum': '',
	        	'blockLog_uploadNum': '',
	        	'statLog_uploadNum': '',
	        	'resLog_uploadNum': '',
	        	'badtotal|0-50': 40, // 故障进程数
	        	'total|0-50': 40, // 总进程数
	        	'memoryBuffer|0-50': 40,
	        	'memoryFree|0-50': 40,
	        	'memoryUsed|0-50': 40,
	        	'memory_rate|0-100': 40,
	        	// 'diskTotalSize|0-100': 40,
	        	// 'diskName|0-100': 40,
	        	// 'useRate|0-100': 40,
	        	'diskNum|0-10': 10,
	        	'diskInfo|1-10': [
	        		{
	        			'diskname': '@word',
		        		'disktotalsize|0-1000': 100,
		        		// 'recordTime': '@datetime',
		        		'userate': '20%',
		        		'status|0-1': 0
		        	}
        		]
	        }
    }
  }, {
    path: '/act-monitor-web/monitor/deviceInfo/statusHistory.do',
    data: {
      'success': {
        'status|0-1': 0,
        'euIP': '@ip',
        		'cuIP': '@ip',
        // "text|0-5": '@cword',
        'recordTime': '@time("HH:mm")',
        'historyCpu|20-40': [
          {
            'status|0-1': 0,
            'cpu|0-100': 100,
            'elementType': '',
            'recordTime': '@time("HH:mm")'
          }
        ],
        'historyDisk|20-40': [
          {
            'diskName|3': '@cword',
            'diskTotalSize': '',
            'recordTime': '@time("HH:mm")',
            'status': '',
            'useRate|0-100': 100,
            'elementType': ''
          }
        ],
        'historyMemory|20-40': [
          {
            'elementType': '', // 缓冲区大小
            'memoryBuffer': '', // 空闲大小
            'memoryRate|0-100': 100, // 使用率
            'memoryUsed': '', // 已使用大小
            'recordTime': '@time("HH:mm")',
            'status|0-1': 0
          }
        ],
        'historyProcess|20-40': [
          {
            'badNum|0-100': 100,
            'elementType': '',
            'recordTime': '@time("HH:mm")'
          }
        ],
        /* "historyStatus|144": [
					{
						"euIP": '@ip',
        				"cuIP": '@ip',
        				"euID": '@id',
        				"status|0-1": 0,
						"elementType": '', 
						"recordTime": '@time("HH:mm")',
					}
				], */
        'historyStatus': [
          {
            'euIP': '@ip',
        				'cuIP': '@ip',
        				'euID': '@id',
        				'status|0-1': 0,
            'elementType': '',
            'recordTime': '@time("HH:mm")',
            'range': '00:00 -- 02:00',
            'rangeInfo|2-5': [{
              'recordTime': '@time("HH:mm")',
              'status|0-1': 0
            }]
          }, {
            'euIP': '@ip',
        				'cuIP': '@ip',
        				'euID': '@id',
        				'status|0-1': 0,
            'elementType': '',
            'recordTime': '@time("HH:mm")',
            'range': '02:00 -- 04:00',
            'rangeInfo|2-5': [{
              'recordTime': '@time("HH:mm")',
              'status|0-1': 0
            }]
          }, {
            'euIP': '@ip',
        				'cuIP': '@ip',
        				'euID': '@id',
        				'status|0-1': 0,
            'elementType': '',
            'recordTime': '@time("HH:mm")',
            'range': '04:00 -- 06:00',
            'rangeInfo|2-5': [{
              'recordTime': '@time("HH:mm")',
              'status|0-1': 0
            }]
          }, {
            'euIP': '@ip',
        				'cuIP': '@ip',
        				'euID': '@id',
        				'status|0-1': 0,
            'elementType': '',
            'recordTime': '@time("HH:mm")',
            'range': '06:00 -- 08:00',
            'rangeInfo|2-5': [{
              'recordTime': '@time("HH:mm")',
              'status|0-1': 0
            }]
          }, {
            'euIP': '@ip',
        				'cuIP': '@ip',
        				'euID': '@id',
        				'status|0-1': 0,
            'elementType': '',
            'recordTime': '@time("HH:mm")',
            'range': '08:00 -- 10:00',
            'rangeInfo|2-5': [{
              'recordTime': '@time("HH:mm")',
              'status|0-1': 0
            }]
          }, {
            'euIP': '@ip',
        				'cuIP': '@ip',
        				'euID': '@id',
        				'status|0-1': 0,
            'elementType': '',
            'recordTime': '@time("HH:mm")',
            'range': '10:00 -- 12:00',
            'rangeInfo|2-5': [{
              'recordTime': '@time("HH:mm")',
              'status|0-1': 0
            }]
          }, {
            'euIP': '@ip',
        				'cuIP': '@ip',
        				'euID': '@id',
        				'status|0-1': 0,
            'elementType': '',
            'recordTime': '@time("HH:mm")',
            'range': '12:00 -- 14:00',
            'rangeInfo|2-5': [{
              'recordTime': '@time("HH:mm")',
              'status|0-1': 0
            }]
          }, {
            'euIP': '@ip',
        				'cuIP': '@ip',
        				'euID': '@id',
        				'status|0-1': 0,
            'elementType': '',
            'recordTime': '@time("HH:mm")',
            'range': '14:00 -- 16:00',
            'rangeInfo|2-5': [{
              'recordTime': '@time("HH:mm")',
              'status|0-1': 0
            }]
          }, {
            'euIP': '@ip',
        				'cuIP': '@ip',
        				'euID': '@id',
        				'status|0-1': 0,
            'elementType': '',
            'recordTime': '@time("HH:mm")',
            'range': '16:00 -- 18:00',
            'rangeInfo|2-5': [{
              'recordTime': '@time("HH:mm")',
              'status|0-1': 0
            }]
          }, {
            'euIP': '@ip',
        				'cuIP': '@ip',
        				'euID': '@id',
        				'status|0-1': 0,
            'elementType': '',
            'recordTime': '@time("HH:mm")',
            'range': '18:00 -- 20:00',
            'rangeInfo|2-5': [{
              'recordTime': '@time("HH:mm")',
              'status|0-1': 0
            }]
          }, {
            'euIP': '@ip',
        				'cuIP': '@ip',
        				'euID': '@id',
        				'status|0-1': 0,
            'elementType': '',
            'recordTime': '@time("HH:mm")',
            'range': '20:00 -- 22:00',
            'rangeInfo|2-5': [{
              'recordTime': '@time("HH:mm")',
              'status|0-1': 0
            }]
          }, {
            'euIP': '@ip',
        				'cuIP': '@ip',
        				'euID': '@id',
        				'status|0-1': 0,
            'elementType': '',
            // "recordTime": '@time("HH:mm")',
            'recordTime': '22:00 -- 24:00',
            'rangeInfo|2-5': [{
              'recordTime': '@time("HH:mm")',
              'status|0-1': 0
            }]
          }
        ],
        'historyAccessLog|20-40': [{
          'actLog_uploadNum|0-100': 100,
          'recordTime': '@time("HH:mm")'
        }],
        'option|4-8': ['@word'],
        'maxUsrRate|1-40': 40,
        'selectOption': '',
        'total|100': 100
      }
    }
  }, {
    path: '/act-monitor-web/monitor/deviceInfo/statusPageList.do',
    data: {
      'success|5-10': [
        // "rows|5-10": [
        {
          'houseId': '@id',
          'houseName|3-5': '@cword',
          'idcName|3-5': '@cword',
          'euIP': '@ip',
          'cuIP': '@ip',
          'status|0-1': 0,
          'idcID': '@id',
          'recordTime': '@datetime'
        }
      ]
      // }
    }
  }, {
    path: '/act-monitor-web/monitor/deviceInfo/newStatus.do',
    data: {
      'success': {
        'status|0-1': 0,
        'recordTime': '@time("HH:mm")'
      }
    }
  }
]
