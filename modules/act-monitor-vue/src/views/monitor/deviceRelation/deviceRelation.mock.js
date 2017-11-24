export default [
// module.exports = [
  {
    path: '/act-monitor-web/monitor/deviceStatus/tuopu.do',
    data: {
      // "elementType|1-2": 1, // 设备类型：2-EU，1-CU
	        'success|10': [{
	        	'houseID|1-100': '@id',
	        	'houseName': '@word',
	        	'idcID': '@id',
	        	'idcName': '@word',
	        	'status|0-10': 0,
	        	'uuid': '@id'
	        	// "devceType|1-2": 1 // 1 cu  2 eu
		    }]
    }
  }, {
    path: '/act-monitor-web/monitor/deviceStatus/queryDeviceByHouse.do',
    data: {
      'dateNum': 7,
	        'elementType': 'device',
	        'erorrCode': 58252,
	        'success|10-40': [{
	        	'uuid': '@id',
	        	'houseName': '@word',
	        	'houseId': '@id',
	        	'euID': '@id',
	        	'cuID': '@id',
	        	'devceType|1-2': 1,
	            'ip': '@ip',
	            'mechineStatus': '@word',
	            'euToCu': '@word',
	            'euToDu': '@word',
	            'cuToDu': '@word',
	            'cuToSmms': '@word',
	            'euServiceStat': '@word',
	            'networkStatus': '@word',
	            'linkStatus': '@word',
	            'recordTime': '@dateTime',
	            'status|0-1': 0 // 0 正常  1 异常 
	        }]
    }
  }
]
