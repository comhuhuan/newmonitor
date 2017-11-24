export default [
// module.exports = [
  {
    path: '/act-monitor-web/monitor/device/provList.do',
    data: {
	        'success|10': [
	        	{
		        	'provId': '@id',
		        	'provName': '@word'
		        }
	        ]
    }
  }, {
    path: '/act-monitor-web/monitor/device/getIdcByProv.do',
    data: {
	        'success|10': [
	        	{
		        	'idcName': '@word',
		        	'uuid|1-100': 100
		        }
	        ]
    }
  }, {
    path: '/act-monitor-web/monitor/device/getHouseByIdc.do',
    data: {
	        'success|10': [
	        	{
		        	'houseName': '@word',
		        	'houseId': '@id'
		        }
	        ]
    }
  }, {
    path: '/act-monitor-web/monitor/device/pagingList.do',
    data: {
      'dateNum': 7,
	        'elementType': 'device',
	        'erorrCode': 58252,
	        'success': {
	            'rows|20': [
	                {
	                	'deviceId|19000-29000': 19900,
	                	'deviceType|1-2': 1, // 1-CU 2-EU 隐藏域 
	                	'houseName': '@cword',
	                	'houseId': '@id',
	                	'idcName': '@word',
	                	'provName': '@province',
	                	'status|0-1': '1', // 0-正常 1-异常
	                	'timeStamp': '@date',
	                	'uuid|888888-999999': 888888, // 隐藏值，用于查看操作查看进程详情时定位设备
	                	'stateType': '' // 隐藏值，用于查看操作查看进程详情时定位设备
	                }
	            ],
	            total: 200
	        }
    }
  }
]
