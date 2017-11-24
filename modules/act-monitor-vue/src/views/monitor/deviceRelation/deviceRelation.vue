<template>
    <section class="deviceRelation">
        <el-row :gutter="10">
            <!-- <transition-group tag="ElCol" name="el-fade-in-linear"> -->
            <transition appear enter-active-class="zoomInLeft" leave-active-class="zoomOutRight">
                <el-col :span="24" v-if="!isShowHouse">
                    <div class="chart" :style="{height: rowHeight + 'px'}">
                        <div class="hint">
                            <p class="normal"><span></span>正常</p>
                            <p class="abnormal"><span></span>异常</p>
                        </div>
                        <!--
                        <IEcharts v-if="!!graph" :option="graph" :loading="graphLoading" :resizable="true" style="width: 100%; height: 100%;" @click="switchHouse"></IEcharts>
                        <div class="noData" v-if="!graph"></div>
                        -->

                        <h3 class="text-center" style="font-size: 20px;">机房状态</h3>
                        <!--CU机房-->

                        <ul class="houseRelation" :style="{'height': rowHeight + 'px'}">
                            <fieldset align="center" style="border:1px dotted;border-radius: 8px;">
                                <LEGEND style="font-family:华文仿宋;">CU机房</LEGEND>
                                <li class="housePosCss" v-for="(house,index) in cuGraph" :key="house.houseID"
                                    :class="{'badHouseIcon': house.status > 0}">
                                    <el-popover :ref="`${house.houseID + index}`" placement="bottom-start" width="200"
                                                trigger="hover">
                                        <ul class="houseIcon">
                                            <li class="text-ellipsis"><span class="label">机房名称：</span>{{house.houseName}}
                                            </li>
                                            <li><span class="label">状态：</span>{{house.status > 0 ? '异常' : '正常'}}</li>
                                        </ul>
                                        <div slot="reference">
                                            <i class="houseIcon"
                                               @click="switchHouse(house.uuid,house.houseID,house.houseName,1)"></i>
                                            <p class="text-center">{{house.houseName}}</p>
                                        </div>
                                    </el-popover>
                                </li>
                            </fieldset>
                            <br/>
                            <!--EU机房-->
                            <fieldset align="center" style="border:1px dotted;border-radius: 8px; ">
                                <LEGEND style="font-family:华文仿宋;">EU机房</LEGEND>
                                <li class="housePosCss" v-for="(house,index) in euGraph" :key="house.houseID"
                                    :class="{'badHouseIcon': house.status > 0}">
                                    <el-popover :ref="`${house.houseID + index}`" placement="bottom-start" width="200"
                                                trigger="hover">
                                        <ul class="houseIcon">
                                            <li class="text-ellipsis"><span class="label">机房名称：</span>{{house.houseName}}
                                            </li>
                                            <li><span class="label">状态：</span>{{house.status > 0 ? '异常' : '正常'}}</li>
                                        </ul>
                                        <div slot="reference">
                                            <i class="houseIcon"
                                               @click="switchHouse(house.uuid,house.houseID,house.houseName,2)"></i>
                                            <p class="text-center">{{house.houseName}}</p>
                                        </div>
                                    </el-popover>
                                </li>
                            </fieldset>
                        </ul>

                        <div class="noData" v-if="!houseList"></div>
                        <i class="fa fa-mail-reply-all" v-if="isShowHouse" @click="isShowHouse = false"></i>
                    </div>
                </el-col>
            </transition>
            <el-col :span="24" v-if="isShowHouse" :style="{height: rowHeight + 'px'}">
                <ul class="houseList" :style="{'height': rowHeight + 'px'}" v-if="!!houseList">
                    <li class="housePos" v-for="(house, index) in houseList" :key="house.houseId"
                        @click="showEquipDetail(index ,house)" :class=" {'badHouse': house.status > 0}">
                        <el-popover :ref="`${house.houseId + index}`" placement="bottom-start" width="200"
                                    trigger="hover">
                            <ul class="houseInfo">
                                <li class="text-ellipsis"><span class="label">机房名称：</span>{{house.houseName}}</li>
                                <li><span class="label">IP：</span>{{house.ip}}</li>
                                <li><span class="label">更新时间：</span>{{house.recordTime}}</li>
                            </ul>
                            <div slot="reference">
                                <i class="deviceIcon"></i>
                                <p class="text-center" style="width: 120px">
                                    {{getDeviceTitle(house.ip, house.cuType, house.euID)}}</p>
                            </div>
                        </el-popover>
                    </li>
                </ul>
                <div class="noData" v-if="!houseList"></div>
                <i class="fa fa-mail-reply-all" v-if="isShowHouse" @click="isShowHouse = false"></i>
            </el-col>

            <!-- </transition-group> -->

        </el-row>
        <el-dialog :title="detailTitle" :visible.sync="isEquipDetail">
            <ul v-for="(item, index) in detailList" :key="index">
                <li v-if="item.type !== 'cuType' || item.value === 'WEB'|| item.value === 'DB'|| item.value === 'INTER'">
                    <span class="label">{{ item.label }} : </span>
                    <span :style="{'color': !item.hidden ? item.value === '正常' ? '#4F81BD' : '#C0504D' : ''}">{{ item.value
                        }}</span>
                    <el-button type="primary" size="small" v-if="!item.hidden" @click.native="showDetail(index, item)">
                        详情
                    </el-button>
                </li>
            </ul>
        </el-dialog>
    </section>
</template>

<script>
    import IEcharts from 'vue-echarts-v3'
    import {houseStatus, IDCRelation} from './deviceRelation.api'
    import {CH} from '../../../js/contentHeight'
    import storage from '../../../js/storage'
    import {mapActions} from 'vuex'
    import ElPopover from '../../../../node_modules/element-ui/packages/popover/src/main'

export default {
      name: 'deviceRelation',
      data () {
        return {
          rowHeight: 0,
          isShowHouse: false,
          cuGraph: [],
          euGraph: [],
          centerNode: null,
          graphFilters: {
            devceType: 2
          },
          centerNodeFilters: {
            devceType: 1
          },
          houseList: [],
          houseFilters: {},
          graphLoading: false,
          houseLoading: false,
          deviceName: '',
          isEquipDetail: false,
          EUList: [
            {label: 'ID', value: '', type: 'euID', hidden: true},
            {label: '名称', value: '', type: 'houseName', hidden: true},
            {label: 'IP', value: '', type: 'ip', hidden: true},
            {label: '更新时间', value: '', type: 'recordTime', hidden: true},
            {label: 'EU设备状态', value: '', type: 'mechineStatus', hidden: false},
            {label: 'EU到CU通信状态', value: '', type: 'euToCu', hidden: false},
            {label: 'EU到DU通信状态', value: '', type: 'euToDu', hidden: false},
            {label: 'EU服务状态', value: '', type: 'euServiceStat', hidden: false},
            {label: 'EU链路状态', value: '', type: 'linkStatus', hidden: false},
            {label: 'EU网卡状态', value: '', type: 'networkStatus', hidden: false}
          ],
          CUList: [
            {label: 'Type', value: '', type: 'cuType', hidden: true},
            {label: '名称', value: '', type: 'houseName', hidden: true},
            {label: 'IP', value: '', type: 'ip', hidden: true},
            {label: '更新时间', value: '', type: 'recordTime', hidden: true},
            {label: 'CU设备状态', value: '', type: 'mechineStatus', hidden: false},
            {label: 'CU到DU通信状态', value: '', type: 'cuToDu', hidden: false},
            {label: 'CU到管局通信状态', value: '', type: 'cuToSmms', hidden: false}
          ],
          detailLoading: false,
          deviceType: '',
          detailTitle: '',
          houseInfos: null,
          houseName: ''
        }
      },
      computed: {
        detailList () {
          if (this.deviceType === 'CU') {
            if (this.CUList[0].value === 'DB') {
              let tempValue = []
              for (let key in this.CUList) {
                if (this.CUList[key].type !== 'cuToDu' && this.CUList[key].type !== 'cuToSmms') {
                  tempValue.push(this.CUList[key])
                }
              }
              return tempValue
            } else if (this.CUList[0].value === 'INTER') {
              let tempValue = []
              for (let key in this.CUList) {
                if (storage.get('cuInter') != null && this.CUList[2].value === storage.get('cuInter')) {
                  if (this.CUList[key].type !== 'cuToSmms') {
                    tempValue.push(this.CUList[key])
                  }
                } else {
                  if (this.CUList[key].type !== 'cuToDu' && this.CUList[key].type !== 'cuToSmms') {
                    tempValue.push(this.CUList[key])
                  }
                }
              }
              return tempValue
            } else if (this.CUList[0].value === 'WEB') {
              if (storage.get('cuInter') != null && this.CUList[2].value !== storage.get('cuInter')) {
                let tempValue = []
                for (let key in this.CUList) {
                  if (this.CUList[key].type !== 'cuToDu') {
                    tempValue.push(this.CUList[key])
                  }
                }
                return tempValue
              } else {
                let tempValue = []
                for (let key in this.CUList) {
                  tempValue.push(this.CUList[key])
                }
                return tempValue
              }
            }
          }
          return this.deviceType === 'CU' ? this.CUList : this.EUList
        }
      },
      methods: {
        setHeight () {
          // const GAP = 60 // 预留的上下的空间
          this.rowHeight = CH()
        },
        getInstance () {
          // 参数从deviceStatus拿到

          let params = storage.get('graphParams')
          // console.log(params)
          Object.assign(this.graphFilters, params)
          Object.assign(this.centerNodeFilters, params)

          this.getIDCRelationData()
          storage.remove('graphParams')
        },
        getIDCRelationData () {
          this.graphLoading = true
          // this.graph = echartsOpt.graph()
          let opt
          IDCRelation(this.centerNodeFilters).then(res => {
            if (res.error) {
              this.$message({
                showClose: true,
                message: res.error,
                type: 'error'
              })
            } else {
              if (!res.success || !res.success.length) {
                this.cuGraph = false
                this.euGraph = false
                this.graphLoading = false
                return
              }
              let data = {
                houseName: res.success[0].houseName,
                uuid: res.success[0].uuid,
                houseID: res.success[0].houseID,
                devceType: res.success[0].devceType,
                idcID: res.success[0].idcID,
                status: res.success[0].status,
                idcName: res.success[0].idcName
              }
              this.cuGraph.push(data)

              this.centerNode = {
                name: res.success[0].houseName,
                symbolSize: this.rowHeight / 5,
                draggable: false,
                value: res.success[0].status,
                itemStyle: {
                  normal: {
                    color: res.success[0].status > 0 ? '#C0504D' : '#4F81BD'
                  }
                },
                label: {
                  normal: {},
                  emphasis: {
                    show: true,
                    textStyle: {
                      fontSize: 16
                    }
                  }
                }
              }
              res.success[0].devceType = 1 // CU 手动设置 该属性，请求机房信息是使用
              opt = res.success[0]
            }
            IDCRelation(this.graphFilters).then(cres => {
              if (cres.error) {
                this.$message({
                  showClose: true,
                  message: res.error,
                  type: 'error'
                })
              } else {
                if (!cres.success || !cres.success.length || !this.centerNode) {
                  this.cuGraph = false
                  this.euGraph = false
                  this.graphLoading = false
                  return
                }

                let category = this.centerNode ? this.centerNode.name : ''
                /* this.graph.title.text = "设备状态关系图"
                            this.graph.series[0].name = category
                            this.graph.series[0].data[0] = this.centerNode */

                cres.success.forEach((item, index) => {
                  let data = {
                    houseName: item.houseName,
                    uuid: item.uuid,
                    houseID: item.houseID,
                    devceType: item.devceType,
                    idcID: item.idcID,
                    status: item.status,
                    idcName: item.idcName
                  }
                  this.euGraph.push(data)
                  let link = {
                    source: category,
                    target: item.houseName,
                    lineStyle: {
                      normal: {
                        color: item.status > 0 ? '#C0504D' : '#4F81BD',
                        width: item.status > 0 ? 2 : 1
                      }
                    }
                  }
                  let categories = {
                    name: item.houseName
                  }
                  categories.name = item.houseName

                  /* this.graph.series[0].force.edgeLength = this.rowHeight / 2.7
                                this.graph.legend[0].data.push(item.houseName)
                                this.graph.series[0].data.push(data)
                                this.graph.series[0].links.push(link)
                                this.graph.series[0].categories.push(categories) */

                  item.devceType = 2 // EU 手动设置 该属性，请求机房信息是使用
                })
                this.graphLoading = false
                cres.success.push(opt)
                storage.set('houseInfo', cres.success)
                // console.log(this.graph)
              }
            })
          })
        },
        getHouseStatus () {
          this.detailLoading = true

          houseStatus(this.houseFilters).then(res => {
            if (res.error) {
              this.$message({
                showClose: true,
                message: res.error,
                type: 'error'
              })
            } else {
              if (!res.success) {
                this.houseList = false
                this.detailLoading = false
                return
              }
              this.houseList = res.success
            }

            this.detailLoading = false
          })
        },
        getEquipDetail (house) {
          let type = house.devceType === 1 ? 'CU' : 'EU'
          for (let key in house) {
            this[type + 'List'].forEach(item => {
              if (item.type === key) {
                if (item.hidden) {
                  if (item.type === 'cuType') {
                    item.value = house[key] === 1 ? 'WEB' : house[key] === 2 ? 'INTER' : 'DB'
                  } else {
                    item.value = house[key]
                  }
                } else {
                  item.value = house[key] === 1 ? '异常' : '正常'
                }
              }
            })
          }
          this.detailTitle = type + '设备信息'
          this.deviceType = type
          this.isEquipDetail = true
          this.detailLoading = false
          this.houseInfos = house // 用户获取机房的所有信息方便showDetail 传参
        },

        switchHouse (uuid, houseID, houseName, devceType) {
          /*	var houseInfo = storage.get('houseInfo')
                    houseInfo.forEach( item => {
                        if( item.houseName == event.name ) {
                            this.houseFilters.devceType = item.devceType
                            this.houseFilters.houseID = item.houseID
                            this.houseFilters.uuid = item.uuid
                        }
                    }) */
          this.houseFilters.devceType = devceType
          this.houseFilters.houseID = houseID
          this.houseFilters.uuid = uuid
          this.houseList = null
          this.getHouseStatus()
          this.isShowHouse = true
          this.houseName = houseName
        },
        showEquipDetail (index, house) {
          this.detailLoading = true
          this.getEquipDetail(house)
        },
        showDetail (index, item) {
          let queryType = item.type === 'mechineStatus' ? 'cpu' : ''
          let name = this.deviceType.toLowerCase()
          let deviceId = this.houseInfos[name + 'ID']
          let elementType = item.type === 'mechineStatus'
            ? name + 'Device'
            : item.type
          let tab = {
            title: deviceId + '设备状态统计',
            name: deviceId + elementType + '设备状态统计',
            component: 'monitor/deviceStatistics/deviceStatistics'
          }
          let params = {
            deviceId: deviceId,
            elementType: elementType,
            houseID: this.houseInfos.houseID,
            queryType: queryType,
            uuid: this.houseInfos.uuid
          }
          let types = {
            deviceType: this.deviceType,
            elementType: elementType,
            queryType: queryType,
            houseName: this.houseName,

          }
          storage.set('statisticsTypes', types)
          storage.set(elementType + 'statistics', params)
          this.addActive(tab)
          this.isEquipDetail = false
        },
        ...mapActions([
          'addActive'
        ]),

        getDeviceTitle (ip, cuType, euID) {
          let deviceType = cuType === null ? 'EU' : 'CU'
          let returnValue = deviceType === 'CU' ? cuType === 1 ? 'WEB' : cuType === 2 ? 'INTER' : 'DB' : euID
          return returnValue + '(' + ip + ')'
    }
      },
      created () {
        this.setHeight()
        this.getInstance()
        window.addEventListener('resize', () => {
          this.setHeight()
        })
      },
      mounted () {
        this.$nextTick(() => {

        })
      },
      components: {
        ElPopover, IEcharts
      }
    }
</script>

<style lang="scss">

    .deviceRelation {
        position: relative;
        .fa-mail-reply-all {
            position: absolute;
            z-index: 100;
            top: 10px;
            left: 10px;
            font-size: 32px;
            color: #fff;

            &:before {
                padding: 5px;
                background: rgba(0, 0, 0, 0.12);
                border-radius: 50%;
            }
            &:hover {
                cursor: pointer;
                &:before {
                    background: rgba(0, 0, 0, 0.3);
                }
            }
        }
        .chart {
            border: 1px solid #ccc;
            position: relative;

            .hint {
                position: absolute;
                width: 100px;
                left: 0;
                top: 0;
                z-index: 10;
                padding: 12px 20px;

                p {
                    font-size: 14px;
                    line-height: 24px;
                    vertical-align: middle;

                    span {
                        display: inline-block;
                        width: 26px;
                        height: 15px;
                        vertical-align: middle;
                        margin-right: 10px;
                        border-radius: 3px;
                    }

                    &.normal span {
                        background: #4F81BD;
                    }
                    &.abnormal span {
                        background: #C0504D;
                    }
                }
            }
        }
        .houseList {
            width: 80%;
            margin: 40px auto;
            // overflow-y: scroll;

            .housePos {
                position: relative;
                display: inline-block;
                margin: 3px;
                cursor: pointer;

                .deviceIcon {
                    display: inline-block;
                    width: 120px;
                    height: 120px;
                    align: center;
                    background-image: url('../../../image/device.png');
                    background-size: 120px 120px;
                }
                &.badHouse {
                    .deviceIcon {
                        background-image: url('../../../image/badDevice.png');
                    }
                }

            }
        }
        .houseRelation {
            width: 80%;
            margin: 40px auto;
            .housePosCss {
                position: relative;
                display: inline-block;
                margin: 3px;
                cursor: pointer;

                .houseIcon {
                    display: inline-block;
                    text-align: center;
                    width: 80px;
                    height: 80px;
                    background-image: url('../../../image/house.png');
                    background-size: 80px 80px;
                }
                &.badHouseIcon {
                    .houseIcon {
                        background-image: url('../../../image/badHouse.png');
                    }
                }

            }
        }
        .label {
            display: inline-block;
            text-align: right;
        }
        .el-dialog {
            width: 450px;
            ul {
                li {
                    line-height: 36px;
                    font-size: 14px;

                    .label {
                        width: 128px;
                        font-weight: bold;
                    }
                    .el-button {
                        margin-top: 4px;
                        float: right;
                    }
                }
            }
        }
        .title {
            height: 36px;
            color: #333;
            font-size: 16px;
            font-weight: bold;
            margin-bottom: 5px;
            line-height: 36px;

        }
    }

    .houseInfo {
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

</style>
