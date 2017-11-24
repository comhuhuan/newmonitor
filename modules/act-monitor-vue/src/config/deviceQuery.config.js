// CU & EU 设备状态统计页面的配置，还有其对应的select下拉列表
export default {
  CU () {
    return {

      cuSelect: [
        {
          value: 'all',
          label: 'CU综合状态'

        },
        {
          value: 'cuDevice',
          label: 'CU设备状态'

        }, {
          value: 'cuToSmms',
          label: 'CU到管局状态'

        }
        // {
        //     value: 'cuToDu',
        //     label: 'CU到DU通信状态',
        //     hidden: false
        // }
      ],

      select: [
        // {
        //     value: 'all',
        //     label: 'CU总状态'
        // },
        {
          value: 'cuDevice',
          label: 'CU设备状态'

        }, {
          value: 'cuToSmms',
          label: 'CU到管局状态'

        }, {
          value: 'cuToDu',
          label: 'CU到DU通信状态'

        }
      ],
      cuDevice: {
        name: 'CU设备状态',
        cpu: {
          name: 'CUP',
          baseList: [
            {label: 'IP地址', value: '', key: 'cuIP'},
            {label: 'cpu主频', value: '', key: 'cpuFrequcy'},
            {label: '总内存', value: '', key: 'memoryTotal', 'unit': 'MB'},
            {label: '型号', value: '', key: 'cpuModel'},
            {label: 'cpu总体使用率', value: '', key: 'cpuTotalRate', color: 'status'},
            {label: '状态', value: '', key: 'status', color: 'status'},
            {label: '更新时间', value: '', key: 'recordTime'}
          ],
          otherList: null,
          tableColumn: [
            {label: '机房名称', prop: 'houseName'},
            // {label: 'ID', prop: "cuID"},
            {label: '设备', prop: 'deviceId'},
            // {label: 'IP地址', prop: "cuIP"},
            {label: '状态', prop: 'status', type: 'tag'},
            {label: '更新时间', prop: 'recordTime'}
          ]
        },
        memory: {
          name: '内存',
          baseList: [
            {label: 'IP地址', value: '', key: 'cuIP'},

            {label: '空闲内存', value: '', key: 'memoryFree', unit: 'MB'},
            {label: '占用内存', value: '', key: 'memoryUsed', unit: 'MB'},

            {label: '作用缓冲区内存', value: '', key: 'memoryBuffer', unit: 'MB'},
            {label: '内存使用率', value: '', key: 'memoryRate', color: 'status'},
            {label: '状态', value: '', key: 'status', color: 'status'},
            {label: '更新时间', value: '', key: 'recordTime'}
          ],
          otherList: null,
          tableColumn: [
            {label: '机房名称', prop: 'houseName'},
            // {label: 'ID', prop: "cuID"},
            {label: '设备', prop: 'deviceId'},
            // {label: 'IP地址', prop: "cuIP"},
            {label: '状态', prop: 'status', type: 'tag'},
            {label: '更新时间', prop: 'recordTime'}
          ]

        },
        disk: {
          name: '磁盘',
          baseList: [
            // {label: 'ID', value: '', key: 'cuID'},
            {label: 'IP地址', value: '', key: 'cuIP'},
            // {label: '机房名称', value: 'CU', key: 'houseName'},
            // {label: '磁盘总大小', value: '', key: 'diskTotalSize'},
            // {label: '使用率', value: '', key: 'useRate'},
            // {label: '状态', value: '', key: 'status', color: 'status'},
            {label: '总磁盘数', value: '', key: 'diskNum'},
            {label: '更新时间', value: '', key: 'recordTime'}
          ],
          otherList: null,
          // {label: '磁盘名称', value: '', key: 'diskName'},
          tableColumn: [
            {label: '机房名称', prop: 'houseName'},
            // {label: 'ID', prop: "cuID"},
            {label: '设备', prop: 'deviceId'},
            // {label: 'IP地址', prop: "cuIP"},
            {label: '状态', prop: 'status', type: 'tag'},
            {label: '更新时间', prop: 'recordTime'}
          ]

        },
        process: {
          name: '进程',
          baseList: [
            // {label: '机房名称', value: '', key: 'houseName'},
            // {label: 'ID', value: '', key: 'cuID'},
            {label: 'IP地址', value: '', key: 'cuIP'},
            // {label: '机房名称', value: 'CU', key: 'houseName'},
            {label: '进程总数', value: '', key: 'total'},
            {label: '故障进程总数', value: '', key: 'badtotal'},
            {label: '状态', value: '', key: 'status', color: 'status'},
            {label: '更新时间', value: '', key: 'recordTime'}
          ],
          otherList: null,
          tableColumn: [
            {label: '机房名称', prop: 'houseName'},
            // {label: 'ID', prop: "cuID"},
            {label: '设备', prop: 'deviceId'},
            // {label: 'IP地址', prop: "cuIP"},
            {label: '状态', prop: 'status', type: 'tag'},
            {label: '更新时间', prop: 'recordTime'}
          ]
        }
      },
      cuToDu: {
        name: 'CU到DU通信状态',
        baseList: [
          {label: 'IP地址', value: '', key: 'cuIP'},
          // {label: '机房名称', value: 'CU', key: 'houseName'},
          // {label: 'ID', value: '', key: 'cuID'},
          // {label: '机房名称', value: '', key: 'houseName'},
          {label: '状态', value: '', key: 'status', color: 'status'},
          {label: '更新时间', value: '', key: 'recordTime'}
        ],
        otherList: null,
        tableColumn: [
          {label: '机房名称', prop: 'houseName'},
          // {label: 'ID', prop: "cuID"},
          {label: '设备', prop: 'deviceId'},
          // {label: 'IP地址', prop: "cuIP"},
          {label: '状态', prop: 'status', type: 'tag'},
          {label: '更新时间', prop: 'recordTime'}
        ]
      },
      cuToSmms: {
        name: 'CU到管局状态',
        baseList: [
          {label: 'IP地址', value: '', key: 'cuIP'},
          // {label: '机房名称', value: 'CU', key: 'houseName'},
          // {label: 'ID', value: '', key: 'cuID'},
          {label: '状态', value: '', key: 'status', color: 'status'},
          {label: '更新时间', value: '', key: 'recordTime'}
        ],
        otherList: null,
        tableColumn: [
          {label: '机房名称', prop: 'houseName'},
          // {label: 'ID', prop: "cuID"},
          {label: '设备', prop: 'deviceId'},
          // {label: 'IP地址', prop: "cuIP"},
          {label: '状态', prop: 'status', type: 'tag'},
          {label: '更新时间', prop: 'recordTime'}
        ]
      }
    }
  },
  EU () {
    return {

      euSelect: [

        {

          value: 'all',
          label: 'EU综合状态'
        },
        {
          value: 'euDevice',
          label: 'EU设备状态'
        }, {
          value: 'networkStatus',
          label: 'EU网卡状态'
        }, {
          value: 'linkStatus',
          label: 'EU链路状态'
        }, {
          value: 'euToCu',
          label: 'EU到CU通信状态'
        }, {
          value: 'euToDu',
          label: 'EU到DU通信状态'
        }, {
          value: 'euServiceStat',
          label: 'EU服务状态'
        }
      ],

      select: [

        // {
        //
        //
        //     value: 'all',
        //     label: 'EU总状态'
        // },
        {
          value: 'euDevice',
          label: 'EU设备状态'
        }, {
          value: 'networkStatus',
          label: 'EU网卡状态'
        }, {
          value: 'linkStatus',
          label: 'EU链路状态'
        }, {
          value: 'euToCu',
          label: 'EU到CU通信状态'
        }, {
          value: 'euToDu',
          label: 'EU到DU通信状态'
        }, {
          value: 'euServiceStat',
          label: 'EU服务状态'
        }
      ],
      euDevice: {
        name: 'EU设备状态',
        cpu: {
          name: 'CUP',
          baseList: [
            // {label: 'ID', value: '', key: 'cuID'},
            {label: 'IP地址', value: '', key: 'euIP'},
            {label: '机房名称', value: '', key: 'houseName'},
            {label: 'cpu主频', value: '', key: 'cpuFrequcy'},
            {label: '总内存', value: '', key: 'memoryTotal', 'unit': 'MB'},
            {label: '型号', value: '', key: 'cpuModel'},
            {label: 'cpu总体使用率', value: '', key: 'cpuTotalRate', color: 'status'},
            {label: '状态', value: '', key: 'status', color: 'status'},
            {label: '更新时间', value: '', key: 'recordTime'}
          ],
          otherList: null,
          tableColumn: [
            {label: '机房名称', prop: 'houseName'},
            // {label: 'ID', prop: "euID"},
            {label: '设备', prop: 'deviceId'},
            {label: '状态', prop: 'status', type: 'tag'},
            {label: '更新时间', prop: 'recordTime'}
          ]
        },
        memory: {
          name: '内存',
          baseList: [
            // {label: 'ID', value: '', key: 'cuID'},
            {label: 'IP地址', value: '', key: 'euIP'},
            {label: '机房名称', value: '', key: 'houseName'},
            {label: '空闲内存', value: '', key: 'memoryFree', unit: 'MB'},
            {label: '占用内存', value: '', key: 'memoryUsed', unit: 'MB'},
            {label: '作用缓冲区内存', value: '', key: 'memoryBuffer', unit: 'MB'},
            {label: '内存使用率', value: '', key: 'memoryRate', color: 'status'},
            {label: '状态', value: '', key: 'status', color: 'status'},
            {label: '更新时间', value: '', key: 'recordTime'}
          ],
          otherList: null,
          tableColumn: [
            {label: '机房名称', prop: 'houseName'},
            // {label: 'ID', prop: "euID"},
            {label: '设备', prop: 'deviceId'},
            // {label: 'IP地址', prop: "euIP"},
            {label: '状态', prop: 'status', type: 'tag'},
            {label: '更新时间', prop: 'recordTime'}
          ]

        },
        disk: {
          name: '磁盘',
          baseList: [
            // {label: 'ID', value: '', key: 'cuID'},
            {label: 'IP地址', value: '', key: 'euIP'},
            {label: '机房名称', value: '', key: 'houseName'},
            // {label: '磁盘名称', value: '', key: 'diskName'},
            // {label: '磁盘总大小', value: '', key: 'diskTotalSize'},
            // {label: '使用率', value: '', key: 'useRate'},
            // {label: '状态', value: '', key: 'status', color: 'status'},
            {label: '更新时间', value: '', key: 'recordTime'}
          ],
          otherList: null,
          tableColumn: [
            {label: '机房名称', prop: 'houseName'},
            // {label: 'ID', prop: "euID"},
            {label: '设备', prop: 'deviceId'},
            // {label: 'IP地址', prop: "euIP"},
            {label: '状态', prop: 'status', type: 'tag'},
            {label: '更新时间', prop: 'recordTime'}
          ]

        },
        process: {
          name: '进程',
          baseList: [
            // {label: 'ID', value: '', key: 'cuID'},
            {label: 'IP地址', value: '', key: 'euIP'},
            {label: '机房名称', value: '', key: 'houseName'},
            {label: '进程总数', value: '', key: 'total'},
            {label: '故障进程总数', value: '', key: 'badtotal'},
            {label: '状态', value: '', key: 'status', color: 'status'},
            {label: '更新时间', value: '', key: 'recordTime'}
          ],
          otherList: null,
          tableColumn: [
            {label: '机房名称', prop: 'houseName'},
            // {label: 'ID', prop: "euID"},
            {label: '设备', prop: 'deviceId'},
            // {label: 'IP地址', prop: "euIP"},
            {label: '状态', prop: 'status', type: 'tag'},
            {label: '更新时间', prop: 'recordTime'}
          ]
        }
      },
      euToDu: {
        name: 'EU到DU通信状态',
        baseList: [
          {label: 'IP地址', value: '', key: 'euIP'},
          {label: '机房名称', value: '', key: 'houseName'},
          // {label: 'ID', value: '', key: 'cuID'},
          {label: '状态', value: '', key: 'status', color: 'status'},
          {label: '更新时间', value: '', key: 'recordTime'}
        ],
        otherList: null,
        tableColumn: [
          {label: '机房名称', prop: 'houseName'},
          // {label: 'ID', prop: "euID"},
          {label: '设备', prop: 'deviceId'},
          // {label: 'IP地址', prop: "euIP"},
          {label: '状态', prop: 'status', type: 'tag'},
          {label: '更新时间', prop: 'recordTime'}
        ]
      },
      euServiceStat: {
        name: 'EU服务状态',
        baseList: [
          {label: 'IP地址', value: '', key: 'euIP'},
          // {label: 'ID', value: '', key: 'cuID'},
          {label: '机房名称', value: '', key: 'houseName'},
          {label: '行为日志上报', value: '', key: 'actLog_uploadNum'},
          {label: '行为日志积压', value: '', key: 'actlog_num'},
          {label: '监测日志积压', value: '', key: 'monitorLog_uploadNum'},
          {label: '封堵日志积压', value: '', key: 'blockLog_uploadNum'},
          {label: '活跃状态积压', value: '', key: 'statLog_uploadNum'},
          {label: '活跃资源积压', value: '', key: 'resLog_uploadNum'},
          {label: '状态', value: '', key: 'status', color: 'status'},
          {label: '更新时间', value: '', key: 'recordTime'}
        ],
        otherList: null,
        tableColumn: [
          {label: '机房名称', prop: 'houseName'},
          // {label: 'ID', prop: "euID"},
          {label: '设备', prop: 'deviceId'},
          // {label: 'IP地址', prop: "euIP"},
          {label: '状态', prop: 'status', type: 'tag'},
          {label: '更新时间', prop: 'recordTime'}
        ]
      },
      euToCu: {
        name: 'EU到CU通信状态',
        baseList: [
          {label: 'IP地址', value: '', key: 'euIP'},
          {label: '机房名称', value: '', key: 'houseName'},
          // {label: 'ID', value: '', key: 'cuID'},
          {label: '状态', value: '', key: 'status', color: 'status'},
          {label: '更新时间', value: '', key: 'recordTime'}
        ],
        otherList: null,
        tableColumn: [
          {label: '机房名称', prop: 'houseName'},
          // {label: 'ID', prop: "euID"},
          {label: '设备', prop: 'deviceId'},
          // {label: 'IP地址', prop: "euIP"},
          {label: '状态', prop: 'status', type: 'tag'},
          {label: '更新时间', prop: 'recordTime'}
        ]
      },
      networkStatus: {
        name: 'EU网卡状态',
        baseList: [
          {label: 'IP地址', value: '', key: 'euIP'},
          {label: '机房名称', value: '', key: 'houseName'},
          // {label: '网卡总状态', prop: "status"},
          {label: '网卡状态(只含不包网卡)', value: '', key: 'status'},
          // {label: 'mac地址', value: '', key: 'mac'},
          // {label: '网卡名称', value: '', key: 'cardName'},
          // {label: '用途', value: '', key: 'cardFunction'},
          // {label: '速率', value: '', key: 'bps', unit: 'Mb/s'},
          // {label: '连接信息', value: '', key: 'connStatus'},
          // {label: '状态', value: '', key: 'status', color: 'status'},
          {label: '更新时间', value: '', key: 'recordTime'}
        ],
        otherList: null,
        tableColumn: [
          {label: '机房名称', prop: 'houseName'},
          // {label: 'ID', prop: "euID"},
          {label: '设备', prop: 'deviceId'},
          // {label: 'IP地址', prop: "euIP"},
          {label: '状态', prop: 'status', type: 'tag'},
          {label: '更新时间', prop: 'recordTime'}
        ]
      },
      linkStatus: {
        name: 'EU链路状态',
        baseList: [
          {label: 'IP地址', value: '', key: 'euIP'},
          {label: '机房名称', value: '', key: 'houseName'},
          // {label: 'ID', value: '', key: 'cuID'},
          {label: '流量', value: '', key: 'inflow', unit: 'Mbps', color: 'status'},
          {label: '状态', value: '', key: 'status', color: 'status'},
          {label: '更新时间', value: '', key: 'recordTime'}
        ],
        otherList: null,
        tableColumn: [
          {label: '机房名称', prop: 'houseName'},
          // {label: 'ID', prop: "euID"},
          {label: '设备', prop: 'deviceId'},
          // {label: 'IP地址', prop: "euIP"},
          {label: '状态', prop: 'status', type: 'tag'},
          {label: '更新时间', prop: 'recordTime'}
        ]
      }
    }
  }
}
