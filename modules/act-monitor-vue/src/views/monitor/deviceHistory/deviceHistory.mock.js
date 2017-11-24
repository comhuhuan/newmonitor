export default [
// module.exports = [
  {
    path: '/act-monitor-web/monitor/historyState/pagingList.do',
    data: {
	        'success': {
	            'pageIndex': 1,
	            'pageSize': 20,
	            'rows|33': [
	                {
	                    'deviceId': '@id',
	                    'status|0-1': 0,
	                    'timeStamp': '@datetime()',
	                    'stateType': '@word'
	                }
	            ],
	            'total': 33,
	            'totalPageCount': 4
	        }
    }
  }
]
