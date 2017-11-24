const sysConfig =
    [{
      path: '/act-monitor-web/common/systemConfig/initialize.do',
      data: {
        'success': [
          {
            'configid': 'center_flag',
            'defaultval': 'N',
            'configval': 'N',
            'title': '安全中心开关',
            'showType': '2'
          },
          {
            'configid': 'recur_ws_off',
            'defaultval': 'N',
            'configval': 'N',
            'title': '递归服务基础代码下载开关',
            'showType': '2'
          },
          {
            'configid': 'recur_ws_url',
            'defaultval': '',
            'configval': 'a',
            'title': '递归服务基础代码下载接口',
            'showType': '0'
          },
          {
            'configid': 'recur_jks_pwd',
            'defaultval': 'iUvFxu',
            'configval': 'iUvFxu',
            'title': '递归服务KeyStore密码',
            'showType': '0'
          },
          {
            'configid': 'recur_file_pwd',
            'defaultval': 'baed6be6c20cc13d3560e7c15bd24c7d',
            'configval': 'baed6be6c20cc13d3560e7c15bd24c7d',
            'title': '递归文件密码',
            'showType': '0'
          },
          {
            'configid': 'recur_file_pyl',
            'defaultval': '9b2126006d3805cecaedfa2ebb082a49',
            'configval': '9b2126006d3805cecaedfa2ebb082a49',
            'title': '递归文件偏移量',
            'showType': '0'
          },
          {
            'configid': 'recur_jks_alias',
            'defaultval': 'def8e-69',
            'configval': 'def8e-69',
            'title': '递归服务Keystore别名',
            'showType': '0'
          },
          {
            'configid': 'recur_jks_pripwd',
            'defaultval': 'LYbTGh',
            'configval': 'LYbTGh',
            'title': '递归服务Keystore私钥获取密码',
            'showType': '0'
          },
          {
            'configid': 'recur_enterprise_id',
            'defaultval': '67b4e5269b0c45ceb9ac7c4dd2adef8e',
            'configval': '67b4e5269b0c45ceb9ac7c4dd2adef8e',
            'title': '递归服务企业ID',
            'showType': '0'
          },
          {
            'configid': 'recur_user_password',
            'defaultval': 'Yoiq6HkO71SHel1l',
            'configval': 'Yoiq6HkO71SHel1l',
            'title': '递归服务用户密码',
            'showType': '0'
          },
          {
            'configid': 'recur_basecode_cycle',
            'defaultval': '24',
            'configval': '24',
            'title': '递归服务基础代码下载周期(单位:小时)',
            'showType': '0'
          },
          {
            'configid': 'authority_ws_off',
            'defaultval': 'N',
            'configval': 'N',
            'title': '权威服务基础代码下载开关',
            'showType': '2'
          },
          {
            'configid': 'authority_ws_url',
            'defaultval': '',
            'configval': 'a',
            'title': '权威服务基础代码下载接口',
            'showType': '0'
          },
          {
            'configid': 'authority_jks_pwd',
            'defaultval': 'C6SBN8',
            'configval': 'C6SBN8',
            'title': '权威服务Keystore密码',
            'showType': '0'
          },
          {
            'configid': 'authority_file_pwd',
            'defaultval': 'e78212936280fcac62a839a1ede6222e',
            'configval': 'e78212936280fcac62a839a1ede6222e',
            'title': '权威文件密码',
            'showType': '0'
          },
          {
            'configid': 'authority_file_pyl',
            'defaultval': '8a3626ef7bd0a6c45c2c534a49b721f7',
            'configval': '8a3626ef7bd0a6c45c2c534a49b721f7',
            'title': '权威文件偏移量',
            'showType': '0'
          },
          {
            'configid': 'authority_jks_alias',
            'defaultval': '0f24d-44',
            'configval': '0f24d-44',
            'title': '权威服务Keystore别名',
            'showType': '0'
          },
          {
            'configid': 'authority_jks_pripwd',
            'defaultval': '9jPdtK',
            'configval': '9jPdtK',
            'title': '权威服务Keystore私钥密码',
            'showType': '0'
          },
          {
            'configid': 'authority_enterprise_id',
            'defaultval': 'decdb852aa1c46a9af0b71b6aca0f24d',
            'configval': 'decdb852aa1c46a9af0b71b6aca0f24d',
            'title': '权威服务企业ID',
            'showType': '0'
          },
          {
            'configid': 'authority_user_password',
            'defaultval': 'DkbQT4W7tSWsY5mx',
            'configval': 'DkbQT4W7tSWsY5mx',
            'title': '权威服务用户密码',
            'showType': '0'
          },
          {
            'configid': 'authority_basecode_cycle',
            'defaultval': '24',
            'configval': '24',
            'title': '权威服务基础代码下载周期(单位:小时)',
            'showType': '0'
          },
          {
            'configid': 'domain_ws_off',
            'defaultval': 'N',
            'configval': 'N',
            'title': '域名注册服务基础代码下载开关',
            'showType': '2'
          },
          {
            'configid': 'domain_jks_pwd',
            'defaultval': 'g49d0y',
            'configval': 'g49d0y',
            'title': '域名注册服务Keystore密码',
            'showType': '0'
          },
          {
            'configid': 'domain_sftp_pwd',
            'defaultval': 'pQJwifc9U2K4VEGqbRs0',
            'configval': 'pQJwifc9U2K4VEGqbRs0',
            'title': '域名注册服务SFTP下载密码',
            'showType': '0'
          },
          {
            'configid': 'domain_jks_alias',
            'defaultval': 'fdbe5-43',
            'configval': 'fdbe5-43',
            'title': '域名注册服务Keystore别名',
            'showType': '0'
          },
          {
            'configid': 'domain_server_ip',
            'defaultval': '202.108.211.44',
            'configval': '202.108.211.44',
            'title': '域名注册服务SFTP下载地址',
            'showType': '0'
          },
          {
            'configid': 'domain_sftp_path',
            'defaultval': '/basecode/dn/service',
            'configval': '/basecode/dn/service',
            'title': '域名注册服务SFTP下载路径',
            'showType': '0'
          },
          {
            'configid': 'domain_sftp_user',
            'defaultval': 'jczydownload',
            'configval': 'jczydownload',
            'title': '域名注册服务SFTP下载用户',
            'showType': '0'
          },
          {
            'configid': 'domain_jks_pripwd',
            'defaultval': 'M9h79H',
            'configval': 'M9h79H',
            'title': '域名注册服务Keystore私钥密码',
            'showType': '0'
          },
          {
            'configid': 'domain_server_port',
            'defaultval': '2001',
            'configval': '2001',
            'title': '域名注册服务SFTP下载端口',
            'showType': '0'
          },
          {
            'configid': 'domain_enterprise_id',
            'defaultval': 'f14989d00b6f448087fb6009c59fdbe5',
            'configval': 'f14989d00b6f448087fb6009c59fdbe5',
            'title': '域名注册服务企业ID',
            'showType': '0'
          },
          {
            'configid': 'domain_user_password',
            'defaultval': 'Iq29wTx2EySRVwFA',
            'configval': 'Iq29wTx2EySRVwFA',
            'title': '域名注册服务用户密码',
            'showType': '0'
          },
          {
            'configid': 'domain_basecode_cycle',
            'defaultval': '24',
            'configval': '24',
            'title': '域名注册服务基础代码下载周期(单位:小时)',
            'showType': '0'
          },
          {
            'configid': 'domain_regist_file_pwd',
            'defaultval': '3d7254b50710dafe2da731c4058a7739',
            'configval': '3d7254b50710dafe2da731c4058a7739',
            'title': '域名注册文件密码',
            'showType': '0'
          },
          {
            'configid': 'domain_regist_file_pyl',
            'defaultval': 'fcebf7429730b1f3e5b4e3bbb0b7b96d',
            'configval': 'fcebf7429730b1f3e5b4e3bbb0b7b96d',
            'title': '域名注册文件偏移量',
            'showType': '0'
          },
          {
            'configid': 'domain_regist_upload_sftp_ip',
            'defaultval': '127.0.0.1',
            'configval': '127.0.0.1',
            'title': '域名注册记录上报SFTP地址',
            'showType': '0'
          },
          {
            'configid': 'domain_regist_upload_sftp_pwd',
            'defaultval': '123456',
            'configval': '123456',
            'title': '域名注册记录上报SFTP密码',
            'showType': '0'
          },
          {
            'configid': 'domain_regist_upload_sftp_path',
            'defaultval': 'upload/source',
            'configval': '/upload/source',
            'title': '域名注册记录上报SFTP路径',
            'showType': '0'
          },
          {
            'configid': 'domain_regist_upload_sftp_port',
            'defaultval': '22',
            'configval': '22',
            'title': '域名注册记录上报SFTP端口',
            'showType': '0'
          },
          {
            'configid': 'domain_regist_upload_sftp_switch',
            'defaultval': 'Y',
            'configval': 'Y',
            'title': '域名注册上报开关',
            'showType': '2'
          },
          {
            'configid': 'domain_regist_upload_sftp_username',
            'defaultval': 'dnsmSftp',
            'configval': 'dnsmSftp',
            'title': '域名注册记录上报SFTP用户名',
            'showType': '0'
          },
          {
            'configid': 'source_record_sftp_ip',
            'defaultval': '127.0.0.1',
            'configval': '127.0.0.1',
            'title': '存放资源记录zoon文件sftp地址',
            'showType': '0'
          },
          {
            'configid': 'source_record_sftp_pwd',
            'defaultval': '123456',
            'configval': '123456',
            'title': '存放资源记录zoon文件sftp密码',
            'showType': '0'
          },
          {
            'configid': 'source_record_xml_path',
            'defaultval': '/date/xml/source',
            'configval': '/date/xml/source',
            'title': '资源记录上报xml本地记录',
            'showType': '0'
          },
          {
            'configid': 'source_record_sftp_port',
            'defaultval': '22',
            'configval': '22',
            'title': '存放资源记录zoon文件sftp端口',
            'showType': '0'
          },
          {
            'configid': 'source_record_sftp_username',
            'defaultval': 'dnsmSftp',
            'configval': 'dnsmSftp',
            'title': '存放资源记录zoon文件sftp用户名',
            'showType': '0'
          },
          {
            'configid': 'source_record_sftp_zoon_path',
            'defaultval': 'upload/source',
            'configval': 'upload/source',
            'title': '存放资源记录zoon文件sftp路径',
            'showType': '0'
          },
          {
            'configid': 'source_record_sftp_upload_path',
            'defaultval': 'upload/source',
            'configval': 'upload/source',
            'title': '资源记录文件上报地址',
            'showType': '0'
          },
          {
            'configid': 'source_record_sftp_upload_switch',
            'defaultval': 'Y',
            'configval': 'Y',
            'title': '资源记录上报开关',
            'showType': '2'
          },
          {
            'configid': 'smms_flag',
            'defaultval': 'N',
            'configval': 'N',
            'title': '研究院开关',
            'showType': '2'
          },
          {
            'configid': 'isms_auth_dnsId',
            'defaultval': '2017060100453100002',
            'configval': '2017060100453100002',
            'title': '权威企业ID',
            'showType': '0'
          },
          {
            'configid': 'isms_recur_dnsId',
            'defaultval': '2017060100453100001',
            'configval': '2017060100453100001',
            'title': '递归企业ID',
            'showType': '0'
          },
          {
            'configid': 'isms_domain_dnsId',
            'defaultval': '2017060100453100003',
            'configval': '2017060100453100003',
            'title': '域名注册企业ID',
            'showType': '0'
          },
          {
            'configid': 'isms_zip',
            'defaultval': 'Y',
            'configval': 'Y',
            'title': '是否使用zip压缩',
            'showType': '2'
          },
          {
            'configid': 'isms_hash',
            'defaultval': '1',
            'configval': '1',
            'title': '哈希算法',
            'showType': '1'
          },
          {
            'configid': 'isms_encode',
            'defaultval': 'Y',
            'configval': 'Y',
            'title': '是否加密',
            'showType': '2'
          },
          {
            'configid': 'isms_msg_key',
            'defaultval': '1234567890abcDEF',
            'configval': '1234567890abcDEF',
            'title': '消息认证密钥',
            'showType': '0'
          },
          {
            'configid': 'isms_pad_key',
            'defaultval': '1234567890abcDEF',
            'configval': '1234567890abcDEF',
            'title': '加密密钥',
            'showType': '0'
          },
          {
            'configid': 'isms_pass_py',
            'defaultval': '1234567890abcDEF',
            'configval': '1234567890abcDEF',
            'title': '数据加密密匙偏移量',
            'showType': '0'
          },
          {
            'configid': 'isms_user_pass',
            'defaultval': '1234567890abcDEF',
            'configval': '1234567890abcDEF',
            'title': '用户口令',
            'showType': '0'
          },
          {
            'configid': 'isms_sftp_ip',
            'defaultval': '127.0.0.1',
            'configval': '127.0.0.1',
            'title': '日志服务器SFTPIP地址',
            'showType': '0'
          },
          {
            'configid': 'smms_version',
            'defaultval': '1.0',
            'configval': '1.0',
            'title': '管局接口版本',
            'showType': '0'
          },
          {
            'configid': 'isms_sftp_port',
            'defaultval': '22',
            'configval': '22',
            'title': '日志服务器SFTP端口',
            'showType': '0'
          },
          {
            'configid': 'isms_sftp_user',
            'defaultval': 'dnsmSftp',
            'configval': 'dnsmSftp',
            'title': '日志服务器SFTP用户名',
            'showType': '0'
          },
          {
            'configid': 'isms_sftp_password',
            'defaultval': 'yhsj@2017',
            'configval': 'yhsj@2017',
            'title': '日志服务器SFTP密码',
            'showType': '0'
          },
          {
            'configid': 'isms_center_num',
            'defaultval': '20',
            'configval': '20',
            'title': 'dns探针中心端个数',
            'showType': '0'
          },
          {
            'configid': 'isms_smms_ws_url',
            'defaultval': 'http://127.0.0.1:9090/ismi-gj/ws/DnsAckWebService/dns_commandack?wsdl',
            'configval': 'http://127.0.0.1:9090/ismi-gj/ws/DnsAckWebService/dns_commandack?wsdl',
            'title': '管局侧dns_commandack接口地址',
            'showType': '0'
          }
        ]
      }
    }]

export default sysConfig
