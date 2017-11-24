const userConfig =
    [{
      path: '/act-alone-web/common/userConfig/pagingList.do',
      data: {
        'success': {
          'pageIndex': 0,
          'pageSize': 15,
          'rows': [
            {
              'simpleDataQuery': null,
              'exportType': null,
              'fileName': null,
              'uploadInfo': null,
              'userId': 'admin',
              'userPassword': 'e10adc3949ba59abbe56e057f20f883e',
              'userName': '系统管理员',
              'userSex': true,
              'userTel': '322753',
              'userMobile': '18771962254',
              'userEmail': 'admin@act-telecom.com',
              'userDescrip': '系统管理员账户',
              'userValid': true,
              'userManager': true,
              'lastTime': 1499858018000,
              'userAmount': 3,
              'userQuestion': '最喜欢的运动',
              'userAnswer': '足球',
              'createDate': 1495017510000,
              'updateDate': 1495017510000,
              'creator': 'admin',
              'modifier': 'admin',
              'userIdQuery': null
            },
            {
              'simpleDataQuery': null,
              'exportType': null,
              'fileName': null,
              'uploadInfo': null,
              'userId': 'test',
              'userPassword': 'e10adc3949ba59abbe56e057f20f883e',
              'userName': '系统测试员',
              'userSex': false,
              'userTel': '322753',
              'userMobile': '18771962254',
              'userEmail': 'admin@act-telecom.com',
              'userDescrip': '系统测试员账户',
              'userValid': true,
              'userManager': false,
              'lastTime': 1495017510000,
              'userAmount': 0,
              'userQuestion': '最喜欢的运动',
              'userAnswer': '足球',
              'createDate': 1495017510000,
              'updateDate': 1495017510000,
              'creator': 'admin',
              'modifier': 'admin',
              'userIdQuery': null
            }
          ],
          'total': 2,
          'totalPageCount': 1
        }
      }
    }, {
      path: '/act-alone-web/common/userConfig/initialize.do',
      data: {
        'success': {
          'roleList': [
            {
              'key': 1,
              'label': 'demo',
              'disabled': false
            }
          ]
        }
      }
    }, {
      path: '/act-alone-web/common/userConfig/getSysUser.do',
      data: {
        'success': {
          'roleList': [
            {
              'key': 1,
              'label': 'demo',
              'disabled': false
            }
          ]
        }
      }
    }, {
      path: '/act-alone-web/common/userConfig/exportByJson.do',
      data: {
        'success': {
          'header': [
            '登入名',
            '用户名称',
            '用户性别',
            '固定电话',
            '移动电话',
            '电子邮箱',
            '用户描述'
          ],
          'dataName': [
            'userId',
            'userName',
            'userSex',
            'userTel',
            'userMobile',
            'userEmail',
            'userDescrip'
          ],
          'dataList': [
            {
              'simpleDataQuery': null,
              'exportType': null,
              'fileName': null,
              'uploadInfo': null,
              'userId': 'admin',
              'userPassword': 'e10adc3949ba59abbe56e057f20f883e',
              'userName': '系统管理员',
              'userSex': true,
              'userTel': '322753',
              'userMobile': '18771962254',
              'userEmail': 'admin@act-telecom.com',
              'userDescrip': '系统管理员账户',
              'userValid': true,
              'userManager': true,
              'lastTime': 1503891614000,
              'userAmount': 9,
              'userQuestion': '最喜欢的运动',
              'userAnswer': '足球',
              'createDate': 1495017510000,
              'updateDate': 1495017510000,
              'creator': 'admin',
              'modifier': 'admin',
              'userIdQuery': null
            },
            {
              'simpleDataQuery': null,
              'exportType': null,
              'fileName': null,
              'uploadInfo': null,
              'userId': 'test',
              'userPassword': 'e10adc3949ba59abbe56e057f20f883e',
              'userName': '系统测试员',
              'userSex': false,
              'userTel': '322753',
              'userMobile': '18771962254',
              'userEmail': 'admin@act-telecom.com',
              'userDescrip': '系统测试员账户',
              'userValid': true,
              'userManager': false,
              'lastTime': 1495017510000,
              'userAmount': 0,
              'userQuestion': '最喜欢的运动',
              'userAnswer': '足球',
              'createDate': 1495017510000,
              'updateDate': 1495017510000,
              'creator': 'admin',
              'modifier': 'admin',
              'userIdQuery': null
            }
          ],
          'fileName': '用户列表'
        }
      }
    }]

export default userConfig
