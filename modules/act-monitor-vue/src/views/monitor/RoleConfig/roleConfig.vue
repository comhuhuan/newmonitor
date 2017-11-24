<template>
    <section v-if="showConfig">
        <!----------------------------------------------工具条开始------------------------------------------------------->
        <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">

            <el-form :inline="true" :model="filters" ref="filters">

                <el-form-item label="角色名称" prop="roleNameQuery">
                    <el-input v-model="filters.roleNameQuery" placeholder="角色名称模糊查询">
                    </el-input>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" v-on:click="pagingList()" size="small"><i
                            class="fa fa-search fa-lg"></i> 查询


                    </el-button>
                </el-form-item>

                <el-form-item>
                    <el-button type="success" v-on:click="handleReset()"
                               size="small"><i class="fa fa-refresh fa-lg"></i> 重置


                    </el-button>
                </el-form-item>

                <el-form :inline="true">
                    <el-form-item>
                        <el-button type="success" @click="handleSave('')" icon="plus" size="small"
                                   title="新增">
                            新增
                        </el-button>
                    </el-form-item>


                </el-form>

            </el-form>


        </el-col>
        <!----------------------------------------------工具条结束------------------------------------------------------->


        <!-----------------------------------------tab_role查询列表开始---------------------------------------------->
        <el-table :data="dataList" :height="tableHigth" v-loading="listLoading" @selection-change="selsChange"
                  highlight-current-row stripe border resizable>
            <el-table-column type="selection"></el-table-column>
            <el-table-column type="index" label="序号" width="80"></el-table-column>
            <el-table-column prop="roleName" show-overflow-tooltip label="角色名称" label-class-name="el-tooltip">
            </el-table-column>
            <el-table-column prop="roleDesc" show-overflow-tooltip label="角色描述" label-class-name="el-tooltip">
            </el-table-column>
            <el-table-column prop="createDate" show-overflow-tooltip label="创建时间" label-class-name="el-tooltip"
                             :formatter="formatDate">
            </el-table-column>
            <el-table-column prop="updateDate" show-overflow-tooltip label="修改时间" label-class-name="el-tooltip"
                             :formatter="formatDate">
            </el-table-column>
            <el-table-column prop="creator" show-overflow-tooltip label="创建人" label-class-name="el-tooltip">
            </el-table-column>
            <el-table-column prop="modifier" show-overflow-tooltip label="修改人" label-class-name="el-tooltip">
            </el-table-column>
            <el-table-column label="操作" width="140" header-align="center" align="center">
                <template slot-scope="scope">
                    <el-button-group>
                        <el-button size="small" type="primary"
                                   @click="handleSave(scope.row.roleId)" icon="edit" title="编辑"></el-button>
                        <el-button size="small" type="danger"
                                   @click="handleDel(scope.$index, scope.row)" icon="delete" title="删除"></el-button>
                    </el-button-group>
                </template>
            </el-table-column>
        </el-table>
        <!-----------------------------------------tab_role查询列表结束---------------------------------------------->


        <!------------------------------------------------分页器开始----------------------------------------------------->
        <el-col :span="24" class="toolbar">
            <el-button type="danger" @click="batchRemove" :disabled="this.sels.length===0">批量删除
            </el-button>

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

    <section v-else-if="showSave">
        <component @backConfig="backConfig" :roleId="roleIdQuery" v-bind:is=roleSave>
        </component>
    </section>
</template>


<script>
    import tableHeight from '../../../js/heightUtil'
    import dateUtil from '../../../js/dateUtil'
    import {pagingList, remove} from './roleConfig.api'
    import roleSave from './roleSave'

    export default {
      data () {
        return {
          // 显示配置角色信息
          roleSave: roleSave,
          roleIdQuery: '',
          showConfig: true,
          showSave: true,

          dataList: [], // sql引擎查询列表
          listLoading: false, // 加载查询list
          tableHigth: '', // 列表高度
          sels: [], // 列表选中列
          total: 0, // 总数

          // 查询条件
          filters: {
            pageIndex: 0,
            pageSize: 15,
            roleNameQuery: ''
          }
        }
      },

      methods: {

        // 格式化时间
        formatDate: function (row, column) {
          return (!row[column.property] || row[column.property] === '') ? '' : dateUtil.formatDate.format(new Date(row[column.property]), 'yyyy-MM-dd hh:mm:ss')
        },

        /** ************************************查询分页开始**********************************************************/
        // 分页查询
        pagingList: function () {
          this.listLoading = true
          this.tableHigth = tableHeight(363)
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
        // 还原查询条件
        handleReset () {
          this.$refs['filters'].resetFields()
          this.filters.pageIndex = 0,
          this.filters.pageSize = 15,
          this.pagingList()
        },
        // 改变每页列数
        handleSizeChange (val) {
          this.filters.pageSize = val
          this.handleCurrentChange(1)
        },
        // 分页器属性变化后 查询
        handleCurrentChange (val) {
          if (val === 1) {
            this.filters.pageIndex = 0
          } else {
            this.filters.pageIndex = (val - 1) * (this.filters.pageSize)
          }
          this.pagingList()
        },
        /** *************************************查询分页结束*********************************************************/

        /** *************************************删除开始*************************************************************/
        // 点击勾选框
        selsChange: function (sels) {
          this.sels = sels
        },
        handleDel: function (index, row) {
          this.$confirm('确认删除该记录吗?', '提示', {
            type: 'warning'
          }).then(() => {
            let para = {deleteIds: row.roleId}
            remove(para).then((res) => {
              if (res.error) {
                this.$message({
                  showClose: true,
                  message: res.error,
                  type: 'error'
                })
              } else {
                this.$message({
                  showClose: true,
                  message: '删除成功',
                  type: 'success'
                })
              }
              this.pagingList()
            })
          })
        },
        batchRemove: function () {
          const ids = this.sels.map(item => item.roleId).toString()
          this.$confirm('确认删除选中记录吗？', '提示', {
            type: 'warning'
          }).then(() => {
            let para = {deleteIds: ids}
            remove(para).then((res) => {
              if (res.error) {
                this.$message({
                  showClose: true,
                  message: res.error,
                  type: 'error'
                })
              } else {
                this.$message({
                  showClose: true,
                  message: '删除成功',
                  type: 'success'
                })
              }

              this.pagingList()
            })
          })
        },
        /** ************************************删除结束**************************************************************/

        /** ***********************************角色配置开始************************************************************/
        handleSave: function (roleId) {
          this.roleIdQuery = roleId
          this.showConfig = false// 配置页面
          this.showSave = true// 角色页面
        },
        backConfig: function () {
          this.showConfig = true// 配置页面
          this.showSave = false// 角色页面
          this.pagingList()
        }

        /** ***********************************角色配置结束************************************************************/
      },

      mounted () {
        this.pagingList()
    }
    }
</script>

<style>
    .view-table label {
        color: #99a9bf;
    }

</style>