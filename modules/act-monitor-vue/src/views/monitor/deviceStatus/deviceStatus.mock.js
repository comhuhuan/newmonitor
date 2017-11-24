export default [
// module.exports = [
  {
    path: '/act-monitor-web/monitor/deviceCount/deviceInfo.do',
    data: {
      /* "success": {
	            "cuID": "@csentence(3, 5)",
	            "euID": "@csentence(3, 5)",
	            "deviceType|0-1": 0,
	            "idcID|1-100": 100,

	             df
	            "recordTime": "@datetime",
	            "dviceNum|20-80": 20,
	            "badDviceNum|1-20": 20
	        } */
	        'success': [
	        	{
		        	'dviceNum|1-100': 100,
		        	'idcID|1-100': 100,
		        	'status': 0 // 正常
		        }, {
		        	'dviceNum|1-100': 100,
		        	'idcID|1-100': 100,
		        	'status': 1 // 异常
		        }
	        ]
    }
  }, {
    path: '/act-monitor-web/monitor/deviceCount/cuDeviceInfoHistory.do',
    data: {
      'dateNum': 7,
	        'elementType': 'device',
	        'erorrCode': 58252,
	        'success|7': [{
            	'recordTime': '@date',
            	'idcID|1-100': 100,
            	'badDviceNum|1-50': 50,
            	'dviceNum|1-50': 50
      }]
    }
  }, {
    path: '/act-monitor-web/monitor/deviceCount/euDeviceInfoHistory.do',
    data: {
      'dateNum': 7,
	        'elementType': 'device',
	        'erorrCode': 58252,
	        'success|7': [{
            	'recordTime': '@date',
            	'idcID|1-100': 100,
            	'dviceNum|1-50': 50,
            	'badDviceNum|1-50': 50
      }]
    }
  }, {
    path: '/act-monitor-web/monitor/deviceCount/badDevicePageList.do',
    data: {
      'erorrCode': 26648,
	        'success': {
	            'pageIndex': 38220,
	            'pageSize': 43760,
	            'rows|10': [
	                {
	                	'euID|0-1000': 1000,
	                	'cuID|0-1000': 1000,
	                	'euName': '@word',
	                	'cuName': '@word',
	                	'houseID|20000-40000': 30000,
	                	'idcID|1-100': 100,
	                	'recordTime': '@datetime',
	                	'status|0-1': 0, // 1 - 异常    0 - 正常
	                	'uuid': '@id'
	                }
	            ],
	            'total': 80765,
	            'totalPageCount': 15774
	        }
    }
  }
]
