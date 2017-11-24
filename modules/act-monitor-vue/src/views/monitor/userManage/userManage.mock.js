export default [
  {
    path: '/act-monitor-web/monitor/user/pagingList.do',
    data: {
	        'success': {
	            'pageIndex': 1,
	            'pageSize': 20,
	            'rows|33': [
	                {
	                    'userId': '@id',
	                    'username': '@name',
	                    'idcName': '@name',
	                    'tel': '@integer(10000)',
	                    'email': '@email',
	                    'limit': '@county(true)',
	                    'uuid': '@id',
	                    'createTime': '@datetime'
	                }
	            ],
	            'total': 33,
	            'totalPageCount': 4
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
