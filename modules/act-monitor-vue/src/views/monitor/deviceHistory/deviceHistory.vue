<template>
    <section class="deviceHistory">
        <el-row>
            <div class="title">共{{total}}条历史状态</div>
            <el-form :inline="true" :rules="rules" :model="historyFilters" ref="historyFilters">
                <el-form-item label="是否异常：" prop="isWrong" class="radio">
                    <el-radio-group v-model="historyFilters.isWrong">
                        <el-radio :label="''">全部</el-radio>
                        <el-radio :label="0">正常</el-radio>
                        <el-radio :label="1">异常</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="日期范围:" prop="dateQuery">
                    <el-date-picker
                            v-model="historyFilters.dateQuery"
                            type="date"
                            size="small"
                            align="right"
                            :editable="false"
                            :clearable="false"
                            placeholder="请选择日期"
                            @change="dateChange"
                            :picker-options="pickerOptions">
                    </el-date-picker>
                </el-form-item>
                <!-- <div class="fr"> -->
                <el-button type="primary" @click.native="searchHandle('historyFilters')">
                    <i class="fa fa-search fa-lg"></i>
                    查 询
                </el-button>
                <el-button @click.native="resetSearch('historyFilters')">
                    <i class="fa fa-refresh fa-lg"></i>
                    重 置
                </el-button>
                <!-- </div> -->
            </el-form>
            <el-table :data="pageList" :height="tableHeight"
                      v-loading="listLoading" style="width: 100%" border>
                <el-table-column type="selection" width="55"></el-table-column>
                <el-table-column type="index" width="55"></el-table-column>
                <el-table-column prop="deviceId" label="设备"></el-table-column>
                <el-table-column prop="state" label="状态">
                    <template slot-scope="scope">
                        <el-tag :type="scope.row.state === '正常' ? 'primary' : 'danger'">
                            {{scope.row.state}}
                        </el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="stateType" label="状态类型"></el-table-column>
                <el-table-column prop="timeStamp" sortable label="状态更新时间"></el-table-column>

                <!-- <el-table-column label="操作">
                                    <template scope="scope">
                                        <el-button @click.native.prevent="deleteDevice(scope.$index, pageList)" type="danger" size="small">
                                              删除
                                        </el-button>
                                        <el-button @click.native.prevent="moreHandle(scope.$index, pageList)" type="primary" size="small">
                                              更多
                                        </el-button>
                                    </template>
                </el-table-column> -->
            </el-table>
            <div class="wrap">
                <el-dropdown @command="exportByJson">
                    <el-button type="primary" icon="">
                        <i class="fa fa-cloud-download  fa-lg"></i>&nbsp;&nbsp;
                        导出
                        <i class="el-icon-caret-bottom el-icon--right"></i>
                    </el-button>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item command="1">导出当前页</el-dropdown-item>
                        <el-dropdown-item command="-1">导出全部</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
                <el-pagination
                        layout="total, sizes, prev, pager, next, jumper"
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"

                        :page-sizes="[20, 30, 50]"
                        :page-size="params.pageSize"
                        :total="total"
                        v-if="total"
                        style="float: right"
                >
                    <!--  :current-page.sync="params.pageIndex" -->
                </el-pagination>
            </div>
        </el-row>
    </section>
</template>

<script>
    import {pageList, exportByJson} from './deviceHistory.api'
    import {CH} from '../../../js/contentHeight'
    import storage from '../../../js/storage'
    import {mapActions} from 'vuex'
    import {exportDataExcel, exportJsonExcel} from '../../../js/excelUtil'

    export default {
      name: 'userManage',
      data () {
        return {
          tableHeight: '',
          historyFilters: {
            isWrong: '',
            dateQuery: ''
          },
          rules: {
            /* dateQuery: {require: false, type: 'string', message: '请输入正确的时间', trigger: 'change'} */
          },
          /* pickerOptions: {
                      shortcuts: [{
                        text: '最近一周',
                            onClick(picker) {
                              const end = new Date();
                              const start = new Date();
                              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                              picker.$emit('pick', [start, end]);
                            }
                          }, {
                            text: '最近一个月',
                            onClick(picker) {
                              const end = new Date();
                              const start = new Date();
                              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                              picker.$emit('pick', [start, end]);
                            }
                        }, {
                            text: '最近三个月',
                            onClick(picker) {
                              const end = new Date();
                              const start = new Date();
                              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
                              picker.$emit('pick', [start, end]);
                        }
                     }]
                }, */
          pickerOptions: {},
          pageList: [],
          params: {
            pageIndex: 0,
            pageSize: 20
          },
          searchFilters: {
            provId: '',
            uuid: '',
            houseID: '',
            deviceId: '',
            deviceType: 2,
            exportType: '',
            isWrong: '',
            stateType: 'euDevice'

          },
          listLoading: false,
          total: 0
        }
      },
      methods: {
        exportByServer (exportType) {
          this.filters.exportType = exportType
          this.exportDis = true
          exportDataExcel('/act-monitor-web/monitor/device/exportByJson.do', this.filters)
          // 如果不还原导出类型 会造成导出报错
          this.filters.exportType = ''
          this.exportDis = false
        },

        exportByJson (command) {
          this.searchFilters.exportType = command
          this.getPageList()
          this.exportDis = true
          exportByJson(this.searchFilters).then((res) => {
            if (res.error) {
              this.$message({
                showClose: true,
                message: res.error,
                type: 'error'
              })
            } else {
              let {header, dataName, dataList, fileName} = res.success
              const exportData = this.formatJson(dataName, dataList)
              exportJsonExcel(header, exportData, fileName)
            }
          })
          this.exportDis = false
        },

        formatJson: function (dataName, jsonData) {
          return jsonData.map(v => dataName.map(j => {
            if (j === 'stateType') {
              if (v[j] === 'euDevice') {
                return 'EU设备状态'
              } else {
                return 'CU设备状态'
              }
            } else if (j === 'state') {
              if (v[j] === 0) {
                return '正常'
              } else {
                return '异常'
              }
            } else {
              return v[j]
            }
          }))
        },

        setHeight () {
          const GAP = 140 // 上下预留的距离
          this.tableHeight = CH() - GAP
        },
        getInstance () {
          let params = storage.get('historyParams')
          this.historyFilters.isWrong = storage.get('isWrong')
          let isrWrong = {isWrong: this.historyFilters.isWrong}
          Object.assign(this.params, params)
          Object.assign(this.params, isrWrong)
          this.getPageList()
          storage.remove('historyParams')
          storage.remove('isWrong')
        },
        getPageList () {
          this.listLoading = true
          this.pageList = []
          pageList(this.params).then(res => {
            if (res.error) {
              this.$message({
                showClose: true,
                message: res.error,
                type: 'error'
              })
            } else {
              if (!res.success || !res.success.rows || !res.success.rows.length) {
                this.listLoading = false
                return
              }
              this.searchFilters.uuid = res.success.rows[0].uuid
              this.searchFilters.deviceId = res.success.rows[0].deviceId
              this.searchFilters.deviceType = res.success.rows[0].deviceType
              this.searchFilters.stateType = res.success.rows[0].stateType
              this.searchFilters.houseID = res.success.rows[0].houseId

              res.success.rows.forEach((item, index) => {
                item.state = item.state === 0 ? '正常' : '异常'

                switch (item.stateType) {
                  case 'cuDevice':
                    item.stateType = 'CU设备状态'
                    break
                  case 'cuToSmms':
                    item.stateType = 'CU到管局状态'
                    break
                  case 'cuToDu':
                    item.stateType = 'CU到DU通信状态'
                    break
                  case 'euDevice':
                    item.stateType = 'EU设备状态'
                    break
                  case 'linkStatus':
                    item.stateType = 'EU链路状态'
                    break
                  case 'networkStatus':
                    item.stateType = 'EU网卡状态'
                    break
                  case 'euToCu':
                    item.stateType = 'EU到CU通信状态'
                    break
                  case 'euToDu':
                    item.stateType = 'EU到DU通信状态'
                    break
                  case 'euServiceStat':
                    item.stateType = 'EU服务状态'
                    break
                  default :
                    item.stateType = ''
                    break
                }

                item.stateType
              })
              this.pageList = res.success.rows
              this.total = res.success.total
            }
            this.listLoading = false
          })
        },
        dateChange (val) {
          this.historyFilters.dateQuery = val
        },
        searchHandle (formName) {
          this.$refs[formName].validate(valid => {

            if (valid) {
                Object.assign(this.params, this.historyFilters)
                this.getPageList()

            }
          })
        },
        resetSearch (formName) {
          this.$refs[formName].resetFields()
        },
        exportList (command) {

        },
        deleteDevice (index, rows) {
          this.$confirm('确认删除该设备信息吗?', '提示', {
            type: 'warning'
          }).then(() => {
            let params = {}
            Object.assign(this.params, params)
            this.getPageList()
            this.$message({
              showClose: true,
              message: '删除成功',
              type: 'success'
            })
          }).catch(() => {
            // this.$message({
            // 	showClose: true,
            // 	message: '删除失败',
            // 	type: 'error'
            // })
          })
        },
        moreHandle (index, rows) {

        },
        handleSizeChange (val) {
          this.params.pageSize = val
          this.handleCurrentChange(1)
        },
        handleCurrentChange (val) {
          this.params.pageIndex = val - 1
          this.getPageList()
        },
        ...mapActions([
          'addActive'
        ])
      },
      created () {
        this.setHeight()
        this.getInstance()
        window.addEventListener('resize', () => {
          this.setHeight()
        })
      },
      mounted () {

      }
    }
</script>

<style lang="scss" scope>

    .deviceHistory {
        .title {
            height: 36px;
            color: #333;
            font-size: 16px;
            font-weight: bold;
            margin-bottom: 5px;
            line-height: 36px;

        }
        .el-form {
            margin: 0 10px;
            .el-form-item__label {
                font-size: 14px;
            }
        }
        .el-dialog {
            width: 680px;
        }
        .el-select {
            width: 168px;
        }
        .el-table {
            text-align: center;

            th {
                text-align: center;
            }
        }
        .wrap {
            margin-top: 20px;
        }
    }

</style>
