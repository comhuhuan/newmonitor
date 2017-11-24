export default [
// module.exports = [
  {
    path: '/act-monitor-web/monitor/deviceCount/deviceCount.do',
	    data: {
	        /* "success": [{
                "badDviceNum|0-100": 100,
                "dviceNum|100-1000": 1000,
                "provId|2000-3000": 2000,
                "provName": '湖北',
                // "provName": '@province',
                // "percent": '@float(0, 0, 0, 4)'
                "percent|0-100": 100
	        },{
                "badDviceNum|0-100": 100,
                "dviceNum|100-1000": 1000,
                "provId|2000-3000": 2000,
                // "provName": '@province',
                "provName": '湖南',
                // "percent": '@float(0, 0, 0, 4)'
                "percent|0-100": 100
	        }] */
	        /* "success|33": [{
                "badDviceNum|0-100": 100,
                "dviceNum|100-1000": 1000,
                "provId|2000-3000": 2000,
                "provName": '@province',
                "percent": '@float(1, 100, 0, 4)'
	        }] */
	    }
  }, {
    path: '/act-monitor-web/monitor/deviceCount/pageList.do',
	    data: {
	        'success': {
	            'pageIndex': 27462,
	            'pageSize': 22848,
	            'rows|33': [
	                {
	                    'badDviceNum|0-100': 100,
	                    'dviceNum|100-1000': 1000,
	                    'idcID|1-100': 1,
	                    'uuid|888888-999999': 888888,
	                    'idcName': '@word',
	                    'provId|2000-3000': 2000,
	                    'provName': '@province',
	                    'recordTime': '@datetime'
	                    // "percent": '@float(60, 100, 0, 2)'
	                }
	            ],
	            'total': 33,
	            'totalPageCount': 4
	        }
	    }
  }
]
