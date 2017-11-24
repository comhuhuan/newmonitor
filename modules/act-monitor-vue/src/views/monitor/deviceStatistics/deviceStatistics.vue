<template>
    <section class="deviceStatistics">
        <el-row>
            <el-col :span="24">
                <div class="statistics_info statistics" v-loading="titleLoading">
                    <h3 class="title">设备状态详情</h3>
                    <div class="title indent-20 clearfix">
                        <h3>{{title}}  &nbsp;&nbsp; {{title2}}</h3>
                        <el-select v-model="statisticsDef" size="small" placeholder="请选择设备状态类型"
                                   @change="switchStatistics">
                            <el-option v-for="(item, index) in statisticsSelect" :key="index" :label="item.label"
                                       :value="item.value"></el-option>
                        </el-select>
                        <el-radio-group v-model="usageRateDef" v-if="isShowRadio" @change="switchRadio">
                            <el-radio v-for="(item, index) in usageRateList" :key="index" :label="item.value">
                                <!--<span :style="{'color':item.type}"> {{item.label}}{{item.value}}{{item.type}}</span>-->
                                <span :class="{ active: item.type }"> {{item.label}}</span>
                            </el-radio>
                        </el-radio-group>
                    </div>
                    <div class="infoList" :class="{'showArrow': isShowArrow}">
                        <ul class="">
                            <li v-for="(item, index) in baseList" :key="index" class="text-ellipsis"
                                :class="[{'normal': item.value === '正常'},{'abnormal': item.value === '异常'}]">

                                <span>{{item.label}}：</span>{{item.value}}{{item.unit}}
                            </li>
                        </ul>
                        <!-- <transition> -->

                        <ul class="differentInfo" :class="{'showHidden': isShowHidden}" v-if="otherList.length > 0">
                            <li v-for="(item, index) in otherList" :key="index">
                                <el-popover ref="detailInfo" placement="bottom-start" width="135" trigger="hover"
                                            :disabled="!item.detailInfo">
                                    <ul v-if="item.detailInfo" class="detailInfo">
                                        <!--<li class="title">{{item.label}}</li>-->
                                        <li v-for="(citem, cindex) in item.detailInfo" :key="index"
                                            class="text-ellipsis"
                                            :class="[{'normal': citem.value === '正常'},{'abnormal': citem.value === '异常'}]">
                                            <span>{{citem.label}}：</span>{{citem.value}}{{citem.unit}}
                                        </li>
                                    </ul>

                                    <p class="text-ellipsis" slot="reference"
                                       :class="[{'normal': item.value === '正常'},{'abnormal': item.value === '异常'}]">
                                        <span>{{item.label}}：</span>{{item.value}}
                                    </p>

                                </el-popover>
                            </li>
                        </ul>

                        <!-- </transition> -->
                        <i class="icon" :class="isShowHidden ? 'el-icon-arrow-up' : 'el-icon-arrow-down'"
                           v-if="isShowArrow" @click="isShowHidden = !isShowHidden"></i>
                    </div>

                </div>
                <div class="statistics_line statistics">
                    <div class="title clearfix">
                        <h3>{{subTitle}}</h3>
                        <div class="switch">
                            <el-date-picker
                                    v-model="dateDef"
                                    type="date"
                                    size="small"
                                    align="right"
                                    :editable="false"
                                    :clearable="false"
                                    placeholder="请选择日期"
                                    @change="changeDate"
                                    :picker-options="pickerOptions">
                            </el-date-picker>
                            <el-select v-model="trendDef" size="small" placeholder="请选择" v-if="trendSelect.length > 0">
                                <!-- @change="switchTrend" -->
                                <el-option v-for="(item,index) in trendSelect" :key="index"
                                           @click.native="switchTrend(item.value)" :label="item.label"
                                           :value="item.value"></el-option>
                            </el-select>
                        </div>
                    </div>
                    <div class="content">
                        <div class="noData" v-if="!lineList"></div>
                        <div v-if="!!lineList">
                            <el-row :gutter="10" v-if="isLineChart">
                                <el-col :span="isShowLeft ? 3 : 0" class="lineChart" v-if="isShowLeft">
                                    <!-- <div class="info">
                                        <p>累计超限次数</p>
                                        <p class="num">320</p>
                                        <p class="compare">
                                            <i class="fa fa-long-arrow-up normal"></i>
                                            <span class="normal">10%</span>&nbsp;&nbsp;同比上周
                                        </p>
                                    </div> -->
                                    <div class="info">
                                        <p>今日故障次数</p>
                                        <p class="num">{{overTotal}}</p>
                                        <!-- <p class="compare">
                                            <i class="fa fa-long-arrow-down abnormal"></i>
                                            <span class="abnormal">10%</span>&nbsp;&nbsp;同比上周
                                        </p> -->
                                    </div>
                                </el-col>
                                <el-col :span="isShowLeft ? 21 : 24" class="lineChart">
                                    <IEcharts :option="lineList" :loading="lineLoading" :resizable="true"
                                              style="width:100%; height:100%;"></IEcharts>
                                </el-col>
                            </el-row>
                            <el-row v-if="!isLineChart" class="lineChart">
                                <!-- <el-steps :space="50" :active="lineListTotal" finish-status="success">
                                      <el-step v-for="(item, index) in lineList" :key="index" :title="item.status" :status="item.status === '正常' ? 'success' : 'error' " :description="item.time"></el-step>
                                </el-steps> -->
                                <div class="statusList">
                                    <div class="am">
                                        <h3>00:00 -- 12:00</h3>
                                        <div class="statusLine">
                                            <el-button-group>
                                                <el-button size="large" v-for="(timeRange, index) in lineList[0]"
                                                           :key="index" :class="timeRange.type">
                                                    <el-popover trigger="hover" placement="top-start" width="135">
                                                        <ul class="abnormalList" v-if="timeRange.status == '异常'">
                                                            <li v-for="time in timeRange.rangeInfo">{{time.recordTime}} : {{time.status}}</li>
                                                        </ul>
                                                        <ul class="abnormalList" v-if="timeRange.status == '正常'">
                                                            <li v-if="timeRange.allRecoder > 0">正常</li>
                                                            <li v-if="timeRange.allRecoder == 0">暂无数据</li>
                                                        </ul>
                                                        <div slot="reference">{{timeRange.range}}</div>
                                                    </el-popover>
                                                </el-button>
                                            </el-button-group>
                                        </div>
                                    </div>

                                    <div class="pm">
                                        <h3>12:00 -- 24:00</h3>
                                        <div class="statusLine">
                                            <el-button-group>
                                                <el-button size="large" v-for="(timeRange, index) in lineList[1]"
                                                           :key="index" :class="timeRange.type">
                                                    <el-popover trigger="hover" placement="top-start" width="135">
                                                        <ul class="abnormalList" v-if="timeRange.status == '异常'">
                                                            <!-- v-if="timeRange.status == '异常'" -->
                                                            <li v-for="time in timeRange.rangeInfo">{{time.recordTime}} : {{time.status}}</li>
                                                        </ul>
                                                        <ul class="abnormalList" v-if="timeRange.status == '正常'">
                                                            <!-- v-if="timeRange.status == '异常'" -->
                                                            <li v-if="timeRange.allRecoder > 0">正常</li>
                                                            <li v-if="timeRange.allRecoder == 0">暂无数据</li>
                                                        </ul>
                                                        <div slot="reference">{{timeRange.range}}</div>
                                                    </el-popover>
                                                </el-button>
                                            </el-button-group>
                                        </div>
                                    </div>
                                </div>
                            </el-row>
                        </div>

                    </div>
                </div>
                <div class="statistics_table statistics">
                    <div class="title">最新上报数据</div>
                    <el-table :data="tableData" border v-loading="tableLoading">
                        <el-table-column type="index" width="50" align="center"></el-table-column>
                        <el-table-column v-for="(item, index) in tableColumn" :key="index" :prop="item.prop"
                                         :label="item.label" align="center">
                            <template slot-scope="scope">
                                <el-tag :type="scope.row.status === '正常' ? 'primary' : 'danger'"
                                        v-if="item.type === 'tag'">{{scope.row.status}}
                                </el-tag>
                                <div v-else>{{scope.row[item.prop]}}</div>
                            </template>
                        </el-table-column>
                    </el-table>
                </div>
            </el-col>
        </el-row>
    </section>
</template>

<script>
    import IEcharts from 'vue-echarts-v3'
    import {initializeTitle, statusHistory, labelInfo, statusPageList, newStatus} from './deviceStatistics.api'
    import storage from '../../../js/storage'
    import echartsOpt from '../../../config/echartsOpt'
    import config from '../../../config/deviceQuery.config'
    import ElOption from '../../../../node_modules/element-ui/packages/select/src/option'
    import formatDate from '../../../js/dateUtil'

    export default {
      name: 'deviceSearch',
      data () {
        return {
          height: 0,
          statisticsSelect: [],
          statisticsDef: '',
          usageRateList: [
            {label: 'CPU', value: 'cpu', type: ''},
            {label: '内存', value: 'memory', type: ''},
            {label: '磁盘', value: 'disk', type: ''},
            {label: '进程', value: 'process', type: ''}
          ],
          usageRateDef: 'cpu',
          baseList: [],
          otherList: [],
          trendSelect: [],
          trendDef: '',
          CPUTableData: [],
          tableData: [],
          tableColumn: null,
          params: {
            pageIndex: 0,
            pageSize: 20
          },
          titleFilters: {},
          lineFilters: {},
          tableFilters: {},
          pickerOptions: {},
          dateDef: new Date(),
          lineList: null,
          lineListTotal: 0,
          overTotal: 0,
          titleLoading: false,
          lineLoading: false,
          tableLoading: false,
          isShowArrow: false,
          isShowHidden: false,
          isShowRadio: false,
          isLineChart: false,
          isShowLeft: false,
          title: '',
          title2: '',
          subTitle: '',
          deviceType: 'CU',
          elementType: 'cuDevice',
          config: null,
          // yAxisRange: ["00:00 -- 05: 00", "06:00 -- 12:00", "12:00 -- 18:00", "18:00 -- 24:00" ],
          // hoursRange: []

          processStat: '',
          cpuStat: '',
          memStat: '',
          diskStat: '',
          houseName: ''
        }
      },
      methods: {
        setSize () {
          const LW = 180
          const GAP = 80	// 显示content的padding值
          const LIW = 135 // li 的宽度(包括margin,padding)， 这里改动对应的css也要改动
          let w = document.documentElement.clientWidth - LW - GAP
          // this.height = CH()
          this.isShowArrow = w / LIW < this.otherList.length
        },
        init () {
          let lineChart = ['euDevice', 'cuDevice', 'euServiceStat']
          let showLeft = ['euDevice', 'cuDevice']

          // console.log(this.elementType)
          this.trendSelect = []
          this.isLineChart = lineChart.includes(this.elementType)
          this.isShowLeft = showLeft.includes(this.elementType)
          // console.log(this.isShowLeft)
          this.lineList = this.isLineChart ? echartsOpt.line() : []
        },
        getInstance () {
          // 参数从deviceRelation 或 deviceSearch中拿到
          let types = storage.get('statisticsTypes')
          if (types) {
            this.houseName = types.houseName
            this.deviceType = types.deviceType
            this.elementType = types.elementType
            this.usageRateDef = types.queryType

          }
          let c = config[this.deviceType]()
          this.statisticsSelect = c.select
          this.statisticsSelect.forEach(item => {
            if (item.value === this.elementType) {
              this.statisticsDef = item.label
            }
          })
          let params = storage.get(this.elementType + 'statistics')

          this.isShowRadio = this.elementType.includes('Device')
          this.titleFilters = params

          this.lineFilters = Object.assign({}, params, {queryDate: this.dateDef})

          this.tableFilters = {
            deviceId: params.deviceId,
            elementType: params.elementType,
            houseID: params.houseID,
            queryType: params.queryType,
            queryDate: params.queryDate,
            uuid: params.uuid,
            pageSize: 5,
            pageIndex: 0
          }

          // console.log(this.titleFilters, this.lineFilters,this.tableFilters);
          this.getInitializeTitle()
          this.getStatusHistory()
          this.getStatusPageList()
          this.getLabelInfo()
          storage.remove([this.elementType + 'statistics', 'statisticsTypes'])
        },
        getList (listType) {
          let c = config[this.deviceType]()
          let flag = this.elementType.includes('Device')
          return flag ? c[this.elementType][this.usageRateDef][listType]
            : c[this.elementType][listType]
        },
        getLabelInfo () {
          labelInfo(this.lineFilters).then(res => {
            if (res.error) {
              this.$message({
                showClose: true,
                message: res.error,
                type: 'error'
              })
            } else {
              if (!res.success) {
                this.lineList = false
                this.lineLoading = false
                return
              }
              this.usageRateList[3].type = res.success.processStat,
              this.usageRateList[0].type = res.success.cpuStat,
              this.usageRateList[1].type = res.success.memStat,
              this.usageRateList[2].type = res.success.diskStat
            }
          })
        },

        getInitializeTitle () {
          this.titleLoading = true
          initializeTitle(this.titleFilters).then(res => {
            if (res.error) {
              this.$message({
                showClose: true,
                message: res.error,
                type: 'error'
              })
            } else {
              if (!res.success) {
                this.titleLoading = false
                return
              }
              let data = res.success
              let id = this.deviceType === 'CU' ? data.cuIP : data.euID
              let soft_version = this.deviceType === 'CU' ? '' : data.soft_version
              let baseList = this.getList('baseList')
              let cuType = this.deviceType === 'CU' ? data.cuType === 1 ? '(WEB服务器)' : data.cuType === 2 ? '(接口服务器)' : '(数据库服务器)' : ''
              //                        console.log(baseList)
              this.title = this.deviceType + id
              this.title2 = (soft_version) + ' ' + cuType
              data['status'] = this.isNormal(data['status'])
              // 如果是DB或INTER服务器，去掉部分下拉选项
              if (this.deviceType === 'CU') {
                // 如果是
                if (data.cuType === 4) {
                  let c = config[this.deviceType]()
                  let cuSelect = c.select
                  let tempValue = []
                  cuSelect.forEach(item => {
                    if (item.value !== 'cuToSmms' && item.value !== 'cuToDu') {
                      tempValue.push(item)
                    }
                  })
                  this.statisticsSelect = tempValue
                }
                if (data.cuType === 2) {
                  let c = config[this.deviceType]()
                  let cuSelect = c.select
                  let tempValue = []
                  cuSelect.forEach(item => {
                    if (storage.get('cuInter') != null && data.cuIP !== storage.get('cuInter')) {
                      if (item.value !== 'cuToSmms' && item.value !== 'cuToDu') {
                        tempValue.push(item)
                      }
                    } else {
                      if (item.value !== 'cuToSmms') {
                        tempValue.push(item)
                      }
                    }
                  })
                  this.statisticsSelect = tempValue
                }
                if (data.cuType === 1) {
                  if (storage.get('cuInter') != null && data.cuIP !== storage.get('cuInter')) {
                    let c = config[this.deviceType]()
                    let cuSelect = c.select
                    let tempValue = []
                    cuSelect.forEach(item => {
                      if (item.value !== 'cuToDu') {
                        tempValue.push(item)
                      }
                    })
                    this.statisticsSelect = tempValue
                  } else {
                    let c = config[this.deviceType]()
                    let cuSelect = c.select
                    let tempValue = []
                    cuSelect.forEach(item => {
                      tempValue.push(item)
                    })
                    this.statisticsSelect = tempValue
                  }
                }
              }

              for (let i in data) {
                baseList.forEach(item => {
                  if (item.key === i) {
                    if (!!item.color && item.color === 'status') {
                      item.color = data['status'] === '正常' ? 'normal' : 'abnormal'
                    }
                    if (i === 'houseName') {
                      data[i] = this.houseName
                    }
                    item.value = data[i]
                  }
                })
              }
              this.baseList = baseList

              if (this.usageRateDef === 'cpu' && toString.call(data.coreRate) === '[object Object]') {
                for (let i in data.coreRate) {
                  let value = {
                    label: i,
                    value: data.coreRate[i]
                  }
                  this.otherList.push(value)
                }
              }
              if (this.usageRateDef === 'process' && toString.call(data.process) === '[object Array]') {
                data.process.forEach(item => {
                  let detailInfo = [
                    {label: '进程名称', value: item.process_name},
                    {label: '进程状态', value: this.isNormal(item.pro_status)}]
                  // let detailInfo = {label: process_name, value:this.isNormal(item.pro_status) }
                  let value = {
                    label: item.process_name,
                    value: this.isNormal(item.pro_status),
                    detailInfo: detailInfo
                  }
                  this.otherList.push(value)
                })
              }
              if (this.usageRateDef === 'disk' && toString.call(data.diskInfo) === '[object Array]') {
                data.diskInfo.forEach(items => {
                  items.status = this.isNormal(items.status)
                  let detailInfo = [
                    {label: '分区大小', value: '', key: 'disktotalsize'},
                    {label: '使用率', value: '', key: 'userate'},
                    {label: '状态', value: '', key: 'status'}
                  ]
                  for (let i in items) {
                    detailInfo.forEach(item => {
                      if (i === item.key) {
                        item.value = items[i]
                      }
                    })
                  }
                  let value = {
                    label: items.diskname,
                    value: items.status,
                    detailInfo: detailInfo
                  }

                  this.otherList.push(value)
                })
              }

              if (this.elementType === 'networkStatus' && toString.call(data.networkInfo) === '[object Array]') {
                data.networkInfo.forEach(items => {
                  items.cardFunction = !!items.cardFunction && items.cardFunction === 1 ? '捕包网卡' : '其他网卡' // 2 表示其他网卡
                  items.connStatus = !!items.connStatus && items.connStatus === 'yes' ? '连接' : '断开' // no 表示断开
                  items.status = items.connStatus
                  let detailInfo = [
                    {label: 'mac地址', value: '', key: 'mac'},
                    //                                    {label: '网卡名称', value: '', key: 'cardName'},
                    {label: '用途', value: '', key: 'cardFunction'},
                    {label: '速率', value: '', key: 'bps', unit: 'Mb/s'}

                  ]
                  for (let i in items) {
                    detailInfo.forEach(item => {
                      if (i === item.key) {
                        item.value = items[i]
                      }
                    })
                  }
                  let value = {
                    label: items.cardName,
                    value: items.status,
                    detailInfo: detailInfo
                  }

                  this.otherList.push(value)
                })
              }
              this.setSize()
            }
            this.titleLoading = false
          })
        },
        getStatusHistory () {
          this.lineLoading = true
          this.init()
          // console.log(this.lineFilters)
          statusHistory(this.lineFilters).then(res => {
            if (res.error) {
              this.$message({
                showClose: true,
                message: res.error,
                type: 'error'
              })
            } else {
              if (!res.success) {
                this.lineList = false
                this.lineLoading = false
                return
              }
              let data = res.success
              this.selectOption = data.selectOption
              this.subTitle = this.statisticsDef + '趋势统计'
              // console.log(data, this.isLineChart)
              if (this.isLineChart) {
                let key, value
                if (this.usageRateDef) {
                  let valueList = {
                    'cpu': 'cpu',
                    'memory': 'memoryRate',
                    'disk': 'useRate',
                    'process': 'badtotal'
                  }
                  let hasSelectArr = ['cpu', 'disk']
                  key = 'history' + this.usageRateDef.slice(0, 1).toUpperCase() + this.usageRateDef.slice(1)

                  for (let i in valueList) {
                    if (i === this.usageRateDef) {
                      value = valueList[i]
                    }
                  }
                  if (hasSelectArr.includes(this.usageRateDef)) {
                    // alert(data.selectOption )
                    this.trendDef = data.selectOption
                    /* if(!data['option'] || !data['option'].length ){
                                        this.lineList = false
                                        this.lineLoading = false
                                        return
                                    } */
                    if (data['option'] && data['option'].length && data['option'].length > 0) {
                      data.option.forEach(item => {
                        // console.log(item)
                        let select = {
                          label: item,
                          value: item
                        }
                        if (item === 'cpuTotal_rate') {
                          select.label = '总CPU'
                        }
                        this.trendSelect.push(select)
                      })
                      this.trendDef = this.trendDef ? this.trendDef : this.trendSelect[0]
                    }
                    // this.trendSelect =  data.option
                  }
                  this.overTotal = data.total
                } else {
                  key = 'historyAccessLog' // historyAccessLog
                  value = 'actLog_uploadNum'
                }

                if (!data[key] || !data[key].length) {
                  this.lineList = false
                  this.lineLoading = false
                  return
                }

                data[key].reverse()

                data[key].forEach(item => {
                  item.status = this.isNormal(item.status)

                  let lineValue
                  if (typeof item[value] === 'number') {
                    lineValue = item[value]
                    this.lineList.yAxis.axisLabel = {
                      formatter: '{value}'
                    }
                    this.lineList.tooltip.formatter = '{b}\n{c}'
                  } else if (typeof item[value] === 'string') {
                    let index = item[value].indexOf('%')
                    lineValue = parseFloat(item[value].substring(0, index))
                    this.lineList.yAxis.axisLabel = {
                      formatter: '{value} %'
                    }
                    this.lineList.tooltip.formatter = '{b}\n{c}%'
                  }

                  this.lineList.series[0].data.push(lineValue)
                  this.lineList.xAxis.data.push(item.recordTime)
                })
              } else {
                let list = []
                if (!data.historyStatus || !data.historyStatus.length) {
                  this.lineList = false
                  this.lineLoading = false
                  return
                }

                data.historyStatus.forEach(item => {
                  let listStatus = {}
                  listStatus.allRecoder = item.allRecoder
                  listStatus.status = this.isNormal(item.status)
                  if (listStatus.status === '异常') {
                    item.rangeInfo.forEach(value => {
                      value.status = this.isNormal(value.status)
                    })
                    listStatus.rangeInfo = item.rangeInfo
                    listStatus.type = 'statusFail'
                  } else {
                    if (item.allRecoder > 0) {
                      listStatus.type = 'statusSuccess'
                    } else { // 没有数据
                      listStatus.type = 'statusNoData'
                    }
                  }
                  listStatus.range = item.recordTime
                  list.push(listStatus)
                })
                list.slice(0, 5)
                // console.log(list, list.slice(0,6))
                this.lineList.push(list.slice(0, 6), list.slice(6, 12))
                // this.lineListTotal = this.lineList.length
                // this.refresh()
              }

              // console.log(this.lineList)
            }
            this.lineLoading = false
          })
        },
        getStatusPageList () {
          this.tableColumn = this.getList('tableColumn')
          this.tableData = []
          // console.log(this.tableColumn)
          this.tableLoading = true
          statusPageList(this.tableFilters).then(res => {
            if (res.error) {
              this.$message({
                showClose: true,
                message: res.error,
                type: 'error'
              })
            } else {
              if (!res.success || !res.success.rows || !res.success.rows.length) {
                this.tableLoading = false
                return
              }
              res.success.rows.forEach(item => {
                item.status = this.isNormal(item.status)
              })

              this.tableData = res.success.rows
              //					console.log(this.tableData)
            }
            this.tableLoading = false
          })
        },
        refresh () {
          let interval = 1000 * 60 * 10
          if (this.dateDef.toLocaleDateString() === new Date().toLocaleDateString()) {
            setInterval(this.getNewStatus(), interval)
          }
        },
        isNormal (status) {
          if (typeof status === 'null' || typeof status === 'undefined') return
          return status === 0 ? '正常' : '异常'
        },
        switchStatistics (val) {
          this.titleFilters.elementType = val
          this.lineFilters.elementType = val
          this.lineFilters.queryDate = formatDate.formatDate.format(new Date())
          this.dateDef = formatDate.formatDate.format(new Date())
          this.lineFilters.selectOption = ''
          this.tableFilters.elementType = val

          this.elementType = val
          this.isShowRadio = val.includes('Device')
          this.usageRateDef = this.isShowRadio ? 'cpu' : ''
          this.titleFilters.queryType = this.usageRateDef
          this.lineFilters.queryType = this.usageRateDef
          this.otherList = []
          this.getInitializeTitle()
          this.getStatusHistory()
          this.getStatusPageList()
        },
        switchRadio (val) {
          this.titleFilters.queryType = val
          this.lineFilters.queryType = val
          this.tableFilters.queryType = val
          this.lineFilters.selectOption = ''
          this.trendDef = ''
          this.otherList = []
          this.getInitializeTitle()
          this.getStatusHistory()
          this.getStatusPageList()
        },
        switchTrend (val) {
          this.trendDef = val
          this.lineFilters.selectOption = val

          this.getStatusHistory()
        },
        changeDate (val) {
          // this.lineFilters.queryDate = this.dateDef.toLocaleDateString()
          this.lineFilters.queryDate = val
          this.getStatusHistory()
        },
        handleSizeChange (val) {
          this.params.pageSize = val
          this.handleCurrentChange(1)
        },
        handleCurrentChange (val) {
          this.params.pageIndex = val - 1
          this.getPageList()
        }
      },
      computed: {
        listLength () {
          return this.otherList.length
        }
      },
      created () {
        this.getInstance()
        window.addEventListener('resize', () => {
          this.setSize()
        })
      },
      beforeMount () {

      },
      mounted () {

      },
      components: {
        ElOption, IEcharts
      }
    }
</script>

<style lang="scss">

    .deviceStatistics {
        // background: #f7f7f7;
        height: 100%;
        // padding: 20px;
        .statistics {
            background: #f7f7f7;
            border-radius: 5px;
            margin-bottom: 10px;
            padding: 10px 20px;
            border: 1px solid #e8e8e8;

            .title {
                font-size: 16px;
                font-weight: bold;
                line-height: 30px;

                h3 {
                    float: left;
                    font-weight: lighter;

                    font-size: 14px;
                    width: 300px;
                }

                .el-select, .el-date-editor {
                    // float: left;
                    margin: 0 10px;
                }
            }
            .indent-20 {
                padding-left: 20px;
                margin-bottom: 10px;
                h3 {
                    width: 220px;
                }
            }
            .lineChart {
                margin-top: 10px;
                height: 330px;
            }
        }
        .statistics_header {
            background: #fff;
            padding: 8px 20px;
            margin-top: -15px;

        }

        .statistics_info {

            .infoList {
                margin-left: 20px;
                margin-bottom: 10px;
                li {
                    display: inline-block;
                    margin-right: 15px;
                    line-height: 20px;
                }
                span {
                    font-size: 13px;
                }
            }
            .showArrow {
                position: relative;
                margin-bottom: 20px;
                .icon {
                    position: absolute;
                    left: 50%;
                    bottom: -14px;
                    margin-left: -30px;
                    z-index: 12;
                    border: none;
                    font-size: 20px;
                }
            }
            .differentInfo {
                min-height: 20px;
                max-height: 20px;
                width: 100%;
                // transition: all 0.3s ease;
                overflow: hidden;
                li {
                    width: 120px;
                    position: relative;
                    cursor: pointer;
                }

                &.showHidden {
                    max-height: 100%;
                    height: 100%;
                }

            }

        }

        .statistics_line {
            .content {
                height: 350px;

                .info {
                    margin-top: 25px;
                    padding-left: 20px;
                    p {
                        line-height: 24px;
                        &.num {
                            font-size: 32px;
                        }
                    }
                }
            }
            .el-steps {
                // width: 80%;
                margin: 0 auto;
                // margin-top: 135px;
            }
            .statusList {
                // margin-top: 50px;
                .am, .pm {
                    //width: 90%;
                    display: flex;
                    margin: 85px auto;
                }

                h3 {
                    flex: 0 0 135px;
                    width: 135px;
                    height: 40px;
                    line-height: 40px;
                    font-size: 18px;
                }

                .statusLine {
                    flex: 1;
                    .el-button-group {
                        width: 100%;

                        .el-button {
                            width: 16.66%;

                        }
                    }

                }

            }
        }

        .statistics_table {
            margin-bottom: 0;
        }
    }

    .detailInfo {
        li {
            padding-top: 5px;
            &:last-child {
                padding-bottom: 5px;
            }
        }
        span.label {
            display: inline-block;
            text-align: right;
            width: 65px;
        }
    }

    .normal {
        color: #008000;
    }

    .abnormal {
        color: #c23531;
    }

    .statusSuccess {
        color: #fff;
        background-color: #4F81BD;
        border-color: #4F81BD;
    }

    .statusNoData {
        color: #fff;
        background-color: #D3D3D3;
        border-color: #D3D3D3;
    }

    .statusFail {
        color: #fff;
        background-color: #C0504D;
        border-color: #C0504D;
    }

    .abnormalList {
        li {
            width: 100%;
            line-height: 20px;
            font-size: 14px;
            text-align: center;
        }
    }

    .active {
        color: #e64242;
    }

</style>
