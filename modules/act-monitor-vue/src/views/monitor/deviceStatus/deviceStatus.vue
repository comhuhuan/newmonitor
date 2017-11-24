<template>
    <section class="deviceStatus">
        <el-row class="row" :gutter="10">
            <el-col :span="6">
                <div class="title">CU当前状态统计:  </div>
                <div class="chart" :style="{'height': rowHeight + 'px'}">
                    <IEcharts v-if="!!CUPie" v-loading="CUPieLoading" :option="CUPie" :loading="false" :resizable="true"
                              style="width: 100%; height: 100%;" @click="showHoseInfo(uuid)"></IEcharts>
                    <div class="noData" v-if="!CUPie"></div>
                </div>
            </el-col>
            <el-col :span="10">

                <div class="title">
                    <span>CU历史状态统计</span>
                    <el-select align="right" v-model="CUDefaultSelect" placeholder="请选择"
                               @change="switchBar(CUDefaultSelect, 'CU')">
                        <el-option v-for="item in CUSelect"
                                   :key="item.value"
                                   :label="item.label"
                                   :value="item.value">
                        </el-option>
                    </el-select>

                </div>
                <div class="chart" :style="{'height': rowHeight + 'px'}">
                    <IEcharts v-if="!!CUBar" v-loading="CUBarLoading" :option="CUBar" :loading="false" :resizable="true"
                              style="width: 100%; height: 100%;"></IEcharts>
                    <div class="noData" v-if="!CUBar"></div>
                </div>
            </el-col>
            <el-col :span="8">
                <div class="title">CU最新故障设备列表</div>
                <el-table :data="CUList"
                          :height="rowHeight"
                          v-loading="CUListLoading" border style="width: 100%">
                    <el-table-column prop="cuName" label="设备"></el-table-column>
                    <el-table-column prop="status" label="状态">
                        <template slot-scope="scope">
                            <el-tag :type="scope.row.status === '正常' ? 'primary' : 'danger'">
                                {{scope.row.status}}
                            </el-tag>
                        </template>
                    </el-table-column>
                    <el-table-column prop="recordTime" width="150" sortable label="最近更新时间"></el-table-column>
                    <el-table-column
                            label="操作">
                        <template slot-scope="scope">
                            <el-button @click.native.prevent="showDetail(scope.$index, CUList)"
                                       type="primary" size="small">
                                详情
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-col>
        </el-row>
        <el-row :gutter="10">
            <el-col :span="6">
                <div class="title">EU当前状态统计</div>
                <div class="chart" :style="{'height': rowHeight + 'px'}">
                    <IEcharts v-if="!!EUPie" v-loading="EUPieLoading" :option="EUPie" :loading="false" :resizable="true"
                              style="width: 100%; height: 100%;" @click="showHoseInfo(uuid)"></IEcharts>
                    <div class="noData" v-if="!EUPie"></div>
                </div>
            </el-col>
            <el-col :span="10">
                <div class="title">
                    <span>EU历史状态统计:</span>
                    <el-select v-model="EUDefaultSelect" placeholder="请选择" @change="switchBar(EUDefaultSelect, 'EU')">
                        <el-option v-for="item in EUSelect"
                                   :key="item.value"
                                   :label="item.label"
                                   :value="item.value">
                        </el-option>
                    </el-select>
                </div>
                <div class="chart" :style="{'height': rowHeight + 'px'}">
                    <IEcharts v-if="!!EUBar" v-loading="EUBarLoading" :option="EUBar" :loading="false" :resizable="true"
                              style="width: 100%; height: 100%;"></IEcharts>
                    <div class="noData" v-if="!EUBar"></div>
                </div>
            </el-col>
            <el-col :span="8">
                <div class="title">EU最新故障设备列表</div>
                <el-table :data="EUList"
                          :height="rowHeight"
                          v-loading="EUListLoading" style="width: 100%" border>
                    <el-table-column prop="euId" label="设备">
                    </el-table-column>
                    <el-table-column prop="status" label="状态">
                        <template slot-scope="scope">
                            <el-tag :type="scope.row.status === '正常' ? 'primary' : 'danger'">{{scope.row.status}}
                            </el-tag>
                        </template>
                    </el-table-column>
                    <el-table-column prop="recordTime" width="150" sortable label="最近更新时间"></el-table-column>
                    <el-table-column label="操作">
                        <template slot-scope="scope">
                            <el-button @click.native.prevent="showDetail(scope.$index, EUList)" type="primary"
                                       size="small">
                                详情
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-col>
        </el-row>
    </section>
</template>

<script>
    import IEcharts from 'vue-echarts-v3'
    import {deviceInfo, cuDeviceInfoHistory, euDeviceInfoHistory, badDevicePageList} from './deviceStatus.api'
    import {CH} from '../../../js/contentHeight'
    import storage from '../../../js/storage'
    import echartsOpt from '../../../config/echartsOpt'
    import config from '../../../config/deviceQuery.config'
    import {mapActions} from 'vuex'

    export default {
        name: 'deviceStatus',
        data() {
            return {
                rowHeight: 0,
                CUPie: null,
                CUBar: null,
                CUList: [],
                CUSelect: null,
                CUDefaultSelect: '',
                CUBarLoading: false,
                EUBarLoading: false,
                CUPieLoading: false,
                EUPieLoading: false,
                CUListLoading: false,
                EUListLoading: false,
                EUPie: null,
                EUBar: null,
                EUList: [],
                EUSelect: null,
                EUDefaultSelect: '',
                uuid: '',
                filters: {
                    CUPie: {
                        devceType: 1, // CU
                        uuid: ''
                    },
                    CUBar: {
                        elementType: 'all',
                        uuid: ''
                    },
                    CUList: {
                        devceType: 1, // CU
                        uuid: '',
                        pageSize: 5,
                        pageIndex: 0
                    },
                    EUPie: {
                        devceType: 2, // EU
                        uuid: ''
                    },
                    EUBar: {
                        elementType: 'all',
                        uuid: ''
                    },
                        EUList: {
                        devceType: 2, // EU
                        uuid: '',
                        pageSize: 5,
                        pageIndex: 0,
                        euId:""

                    },
                }
            }
        },
        methods: {
            setHeight() {
                const GAP = 60 // 两行之间的距离
                this.rowHeight = (CH() / 2 - GAP) > 240 ? (CH() / 2 - GAP) : 240
            },
            getInstance() {
                // let idcID = storage.get('idcID')
                // 参数从allEquip拿到
                let userInfo = JSON.parse(sessionStorage.getItem('user'))
                let uuid = !!storage.get('uuid') ? storage.get('uuid') : userInfo['uuid']
//                console.log(uuid)

                this.uuid = uuid

                this.filters.CUPie.uuid = uuid
                this.filters.EUPie.uuid = uuid
                this.filters.CUList.uuid = uuid
                this.filters.EUList.uuid = uuid
                this.filters.CUBar.uuid = uuid
                this.filters.EUBar.uuid = uuid

                this.CUPie = echartsOpt.pie()
                this.EUPie = echartsOpt.pie()

                this.getListData(this.filters.CUList, 'CU')
                this.getListData(this.filters.EUList, 'EU')
                this.getPieData(this.filters.CUPie, 'CU')
                this.getPieData(this.filters.EUPie, 'EU')
                this.getCUBarData(this.filters.CUBar)
                this.getEUBarData(this.filters.EUBar)
                // storage.remove(['uuid', 'idcID'])
                storage.remove('uuid')
            },
            getPieData(params, type) {
                this[type + 'PieLoading'] = true

                deviceInfo(params).then(res => {
                    if (res.error) {
                        this.$message({
                            showClose: true,
                            message: res.error,
                            type: 'error'
                        })
                    } else {
                        if (!res.success || !res.success.length) {
                            this[type + 'Pie'] = false
                            this[type + 'PieLoading'] = false
                            return
                        }
                        res.success.forEach((item) => {
                            let obj = {
                                name: item.status == 0 ? '正常' : '异常',
                                itemStyle: {
                                    normal: {
                                        color: item.status == 0 ? '#4F81BD' : '#C0504D'
                                    }
                                },
                                value: item.dviceNum
                            }

                            /*obj.name = item.status == 0 ? '正常' : '异常'
                            obj.itemStyle.normal.color = item.status == 0 ? 'green' : 'red'
                            obj.value = item.dviceNum*/
                            this[type + 'Pie'].series[0].data.push(obj)
                            this[type + 'Pie'].legend.data.push(obj.name)
                        })
                        this[type + 'Pie'].series[0].name = type + '设备状态'

                    }
                    this[type + 'PieLoading'] = false
                })
            },
            getListData(params, type) {
                this[type + 'ListLoading'] = true
                this[type + 'List'] = []
                badDevicePageList(params).then(res => {
                    if (res.error) {
                        this.$message({
                            showClose: true,
                            message: res.error,
                            type: 'error'
                        })
                    } else {
                        if (!res.success || !res.success.rows || !res.success.rows.length) {
                            this[type + 'ListLoading'] = false
                            return
                        }
                        res.success.rows.forEach((item, index) => {
                            item.status = item.status == 0 ? '正常' : '异常'
                            item.devcetype = type == 'CU' ? 1 : 2
                        })
                        this[type + 'List'] = res.success.rows
//                        console.log(res.success.rows)
                    }
                    this[type + 'ListLoading'] = false
                })
            },
            getCUBarData(params) {
                this.CUBarLoading = true
                this.CUBar = echartsOpt.bar()

                cuDeviceInfoHistory(params).then(res => {
                    if (res.error) {
                        this.$message({
                            showClose: true,
                            message: res.error,
                            type: 'error'
                        })
                    } else {
                        if (!res.success || !res.success.length) {
                            this.CUBar = false
                            this.CUBarLoading = false
                            return
                        }
                        res.success.forEach((item, index) => {
                            this.CUBar.xAxis[0].data.push(item.recordTime)
                            this.CUBar.series[0].data.push(item.badDviceNum)
                            this.CUBar.series[1].data.push(item.dviceNum - item.badDviceNum)
                        })

                        // console.log(this.CUBar.xAxis)

                    }
                    this.CUBarLoading = false
                })
            },
            getEUBarData(params) {
                this.EUBarLoading = true
                this.EUBar = echartsOpt.bar()
                // console.log(params)
                euDeviceInfoHistory(params).then(res => {
                    if (res.error) {
                        this.$message({
                            showClose: true,
                            message: res.error,
                            type: 'error'
                        })
                    } else {
                        if (!res.success || !res.success.length) {
                            this.EUBar = false
                            this.EUBarLoading = false
                            return
                        }
                        res.success.forEach((item, index) => {
                            this.EUBar.xAxis[0].data.push(item.recordTime)
                            this.EUBar.series[0].data.push(item.badDviceNum)
                            this.EUBar.series[1].data.push(item.dviceNum - item.badDviceNum)

                        })
                    }
                    this.EUBarLoading = false
                })
            },
            switchBar(val, type) {
                this.filters[type + 'Bar'].elementType = val
                this[type + 'Bar'] = null
                this['get' + type + 'BarData'](this.filters[type + 'Bar'])
            },
            showDetail(index, rows) {
                let elementType ;
                let deviceId;
                let  houseID;
                let deviceType;
                let houseName;

//                console.log(rows)
                if (rows[index].devcetype == '1') {
                    elementType = 'cuDevice'
                    deviceType='CU'
                    houseName='CU'
                    deviceId=rows[index].cuID
                    houseID='0'
                }else {
                    deviceType='EU'
                    houseName='EU'
                    elementType = 'euDevice'
                    deviceId=rows[index].euId
                    houseID=rows[index].houseID.toString()
                }
                let tab = {
                    title: deviceId+ '设备状态统计',
                    name: deviceId + elementType + '设备状态统计',
                    component: "monitor/deviceStatistics/deviceStatistics"
                }
                let types = {
                    houseName:houseName,
                    elementType: elementType,
                    deviceType: deviceType,
                    queryType: 'cpu',
                }
                let params = {
                    uuid: rows[index].uuid,
                    deviceId: deviceId,
                    elementType: elementType,
                    houseID: houseID,
                    queryType: 'cpu',
                    deviceType: deviceType
                }
//                console.log(params)
                this.addActive(tab)
                // 传参到deviceRelation
                storage.set('graphParams', params)
                storage.set(elementType + 'statistics', params)
                storage.set( 'statisticsTypes', types)
            },
            showHoseInfo(houseuuid) {
//                console.log(houseuuid)
                let tab = {
                    title: ' 设备状态图',
                    name: ' 设备状态图',
                    component: "monitor/deviceRelation/deviceRelation"
                }
                let params = {
                    // devceType: rows[index].devceType,
                    uuid: houseuuid
                }
                this.addActive(tab)
                // 传参到deviceRelation
                storage.set('graphParams', params)
            },

            ...mapActions([
                'addActive'
            ])
        },

        created() {
            this.setHeight()
            this.EUSelect = config.EU().euSelect
            this.CUSelect = config.CU().cuSelect
            this.EUDefaultSelect = this.EUSelect[0].label
            this.CUDefaultSelect = this.CUSelect[0].label
            this.getInstance()
            window.addEventListener('resize', () => {
                this.setHeight();
            })


        },
        filters: {
            // percent:
        },
        components: {
            IEcharts
        }
    }

</script>

<style lang="scss">

    .deviceStatus {
        .row {
            margin-bottom: 20px;
        }
        .chart {
            border: 1px solid #ccc;
        }
        .title {
            height: 36px;
            color: #333;
            font-size: 16px;
            font-weight: bold;
            margin-bottom: 5px;
            line-height: 36px;

        }
        .el-table {
            text-align: center;

            th {
                text-align: center;
            }

        }
    }

</style>
