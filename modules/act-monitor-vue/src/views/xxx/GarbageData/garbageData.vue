<template>
    <section v-if="showTable">

        <!----------------------------------------------工具条开始------------------------------------------------------->
        <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">

            <el-form :inline="true" :model="filters" ref="filters">
                <el-form-item label="垃圾类别" prop="garbageDataType">
                    <el-select v-model="filters.garbageDataType" filterable clearable placeholder="请选择">
                        <el-option v-for="item in options" :label="item.label" :value="item.value" :key="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" v-on:click="pagingList" :disabled="menuPurview.query" size="small"><i
                            class="fa fa-search fa-lg"></i> 查询


                    </el-button>
                </el-form-item>


            </el-form>
        </el-col>
        <!----------------------------------------------工具条结束------------------------------------------------------->


        <!----------------------------------------------sql引擎查询列表开始---------------------------------------------->
        <el-table :data="dataList" :height="tableHigth" v-loading="listLoading" highlight-current-row stripe border
                  resizable>
            <el-table-column type="index" width="60"></el-table-column>
            <el-table-column prop="remark" show-overflow-tooltip label="垃圾类别" label-class-name="el-tooltip">
            </el-table-column>
            <el-table-column prop="sqlCount" show-overflow-tooltip label="数量" label-class-name="el-tooltip">
            </el-table-column>
            <el-table-column label="操作" align="center">
                <template slot-scope="scope">
                    <el-button v-if="scope.row.invalid" type="text" size="mini" disabled>数据失效</el-button>
                    <el-button v-else type="text" size="mini" @click="changeShow(scope.$index, scope.row)"
                               :disabled="menuPurview.query">查看详情
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
        <!----------------------------------------------sql引擎查询列表结束---------------------------------------------->


        <!------------------------------------------------分页器开始----------------------------------------------------->
        <el-col :span="24" class="toolbar">
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :page-sizes="[10,15,25,50,100]"
                    :page-size=filters.pageSize
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total"
                    style="float:right;">
            </el-pagination>
        </el-col>
        <!------------------------------------------------分页器结束----------------------------------------------------->
    </section>


    <!--------------------------------------异常数据报表详情界面开始------------------------------------- ---------------->
    <section v-else>


        <!----------------------------------------------工具条开始------------------------------------------------------->
        <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">

            <el-form :inline="true" :model="filters" ref="filters" style="float:right;">

                <el-form-item>
                    <el-button type="danger" v-on:click="clearData()" size="small" icon="delete"
                               :disabled="clearDisabled">清理




                    </el-button>
                    <el-button type="success" v-on:click="changeShow()" size="small"><i class="fa fa-undo fa-lg"></i> 返回




                    </el-button>
                </el-form-item>

            </el-form>

        </el-col>
        <!----------------------------------------------工具条结束------------------------------------------------------->


        <!----------------------------------------------详情分页列表开始------------------------------------ ------------->
        <el-table :data="garbageDataList" :height="tableHigth" v-loading="garbageListLoading" highlight-current-row
                  stripe border resizable>
            <el-table-column type="index" width="60" v-if="garbageTitle"></el-table-column>
            <el-table-column :label="item" :prop="item" v-for="(item,index) in garbageTitle" show-overflow-tooltip :key="item"
                             label-class-name="el-tooltip">
            </el-table-column>
        </el-table>
        <!---------------------------------------------详情分页列表结束------------------------------------ ------------->


        <!--------------------------------------详情界面分页器开始------------------------------------------------------->
        <el-col :span="24" class="toolbar">
            <el-pagination
                    @size-change="garbageSizeChange"
                    @current-change="garbageCurrentChange"
                    :page-sizes="[10,15,25,50,100]"
                    :page-size=garbageFilters.pageSize
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="garbageTotal"
                    style="float:right;">
            </el-pagination>
        </el-col>                                                         
        <!-------------------------------------详情界面分页器结束--------------------------------------------------------->

    </section>
    <!--------------------------------------异常数据报表详情界面结束------------------------------------- ---------------->

</template>


<script>
    import tableHeight from '../../../js/heightUtil'
    import purviewButton from '../../../js/purviewUtil'
    import dateUtil from '../../../js/dateUtil'
    import {pagingList, pagingGarbage, clearData, initialize} from './garbageData.api'
    export default {
      data () {
        return {
          // 按钮权限
          menuPurview: {
            query: true,
            add: true,
            import: true,
            export: true,
            modify: true,
            delete: true
          },
          options: [],
          showTable: true, // true显示初始页面 false显示报表页面

          dataList: [], // sql引擎查询列表
          listLoading: false, // 加载查询list
          tableHigth: '', // 列表高度
          total: 0, // 总数
          // 查询条件
          filters: {
            pageIndex: 0,
            pageSize: 15,
            garbageDataType: ''
          },

          // garbage查询条件
          garbageFilters: {
            pageIndex: 0,
            pageSize: 15,
            reportSql: ''
          },
          garbageTotal: 0,
          garbageDataList: [], // garbage结果list
          garbageTitle: [], // garbage表头
          garbageListLoading: false, // 列表查询
          clear: {
            garbageDelete: ''// 删除sql
          },
          clearDisabled: false

        }
      },

      methods: {

        /** ************************************初始化页面开始********************************************************/
        initialize: function () {
          initialize().then((res) => {
            if (res.error) {
              this.options = ''
            } else {
              this.options = res.success
            }
          })
        },
        /** ************************************初始化页面结束********************************************************/

        /** ************************************查询分页开始**********************************************************/
        // 分页查询
        pagingList: function () {
          this.listLoading = true
          this.tableHigth = tableHeight(350)
          pagingList(this.filters).then((res) => {
            if (res.error) {
              this.$message({
                showClose: true,
                message: res.error,
                type: 'error'
              })
            } else {
              this.total = res.success.total
              this.dataList = res.success.rows
            }
            this.listLoading = false
          })
        },
        // 改变每页列数
        handleSizeChange (val) {
          this.filters.pageSize = val
          this.handleCurrentChange(1)
        },
        // 分页器属性变化后 查询
        handleCurrentChange (val) {
          if (val == 1) {
            this.filters.pageIndex = 0
          } else {
            this.filters.pageIndex = (val - 1) * (this.filters.pageSize)
          }
          this.pagingList()
        },
        /** *************************************查询分页结束*********************************************************/

        /** ************************************切换显示开始**********************************************************/
        changeShow: function (index, row) {
          this.showTable = !this.showTable
          if (!this.showTable) {
            this.garbageFilters.reportSql = row.seleteSql
            this.clear.garbageDelete = row.deleteSql
            if (row.sqlCount == 0) {
              this.clearDisabled = true
            } else {
              this.clearDisabled = false
            }
            this.pagingGarbage()
          } else {
            this.garbageFilters.pageIndex = 0,
            this.garbageFilters.pageSize = 15,
            this.garbageTotal = 0,
            this.garbageDataList = [], // garbage结果list
            this.garbageTitle = [], // garbage表头
            this.clear.garbageDelete = ''
          }
        },
        /** ************************************切换显示结束**********************************************************/

        /** ************************************垃圾数据列表开始*******************************************************/
        pagingGarbage: function () {
          this.garbageListLoading = true
          this.tableHigth = tableHeight(350)
          pagingGarbage(this.garbageFilters).then((res) => {
            if (res.error) {
              this.$message({
                showClose: true,
                message: res.error,
                type: 'error'
              })
            } else {
              this.garbageTotal = res.success.page.total
              this.garbageDataList = res.success.page.rows
              this.garbageTitle = res.success.title
            }
            this.garbageListLoading = false
          })
        },

        // 分页器属性变化后 查询
        garbageCurrentChange (val) {
          if (val == 1) {
            this.garbageFilters.pageIndex = 0
          } else {
            this.garbageFilters.pageIndex = (val - 1) * (this.garbageFilters.pageSize)
          }
          this.pagingGarbage()
        },

        // 改变每页列数
        garbageSizeChange (val) {
          this.garbageFilters.pageSize = val
          this.garbageCurrentChange(1)
        },

        /** ************************************垃圾数据列表结束*******************************************************/

        /** ************************************清理垃圾数据开始*******************************************************/
        clearData () {
          this.$confirm('确认清除吗？', '提示', {type: 'warning'}).then(() => {
            clearData(this.clear).then((res) => {
              if (res.error) {
                this.$message({
                  showClose: true,
                  message: res.error,
                  type: 'error'
                })
              } else {
                this.$message({
                  showClose: true,
                  message: '清除成功',
                  type: 'success'
                })
              }
              this.garbageDelete = '',
              this.pagingGarbage()
            })
          })
        }
        /** ************************************清理垃圾数据结束*******************************************************/

      },

      mounted () {
        this.tableHigth = tableHeight(350)
        this.initialize()
        this.pagingList()
        let secMenuPurview = JSON.parse(sessionStorage.getItem('secMenuPurview'))
        purviewButton(secMenuPurview.garbage_data, this.menuPurview)
    }
    }
</script>

<style>
    .demo-table-expand {
        font-size: 0;
    }

    .demo-table-expand label {
        margin-right: auto;
        margin-left: auto;
        margin-bottom: 0;
        width: 50%;
        color: #99a9bf;
    }

    .demo-table-expand .el-form-item {
        margin-right: auto;
        margin-left: auto;
        margin-bottom: 0;
        width: 50%;
    }

</style>