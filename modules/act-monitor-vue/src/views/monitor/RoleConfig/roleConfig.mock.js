const roleConfig =
    [{
      path: '/act-alone-web/common/roleConfig/pagingList.do',
      data: {
        'success': {
          'pageIndex': 0,
          'pageSize': 15,
          'rows': [
            {
              'roleId': '1',
              'roleName': 'demo',
              'roleDesc': 'demo',
              'createDate': 1499858135000,
              'updateDate': 1499858135000,
              'creator': 'admin',
              'modifier': 'admin',
              'roleNameQuery': null
            }
          ],
          'total': 1,
          'totalPageCount': 1
        }
      }
    },
    {
      path: '/act-alone-web/common/roleConfig/initialize.do',
      data: {
        'success': {
          'menuCheck': [
            {
              'menuId': 1,
              'menuName': '样例1',
              'parentid': '0',
              'children': [
                {
                  'menuId': 2,
                  'menuName': 'echarts页面',
                  'parentid': '1',
                  'children': null
                }
              ]
            },
            {
              'menuId': 3,
              'menuName': '配置页面',
              'parentid': '0',
              'children': [
                {
                  'menuId': 4,
                  'menuName': '用户管理',
                  'parentid': '3',
                  'children': null
                },
                {
                  'menuId': 5,
                  'menuName': '角色管理',
                  'parentid': '3',
                  'children': null
                }
              ]
            }
          ]
        }
      }
    }, {
      path: '/act-alone-web/common/roleConfig/exisRole.do',
      data: {
        'success': {
          'roleList': []
        }
      }
    }, {
      path: '/act-alone-web/common/roleConfig/getSysRole.do',
      data: {
        'success': {
          'roleList': []
        }
      }
    }]

export default roleConfig
