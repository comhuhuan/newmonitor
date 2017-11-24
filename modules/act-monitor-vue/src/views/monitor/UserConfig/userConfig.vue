<template>
    <section v-if="showConfig">
        <!----------------------------------------------工具条开始------------------------------------------------------->
        <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">

            <el-form :inline="true" :model="filters" ref="filters">

                <el-form-item label="用户名称" prop="userIdQuery">
                    <el-input v-model="filters.userIdQuery" placeholder="用户名称模糊查询">
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

                    <el-form-item>
                        <el-dropdown @command="exportByServer">
                            <el-button type="primary" size="small"><i
                                    class="fa fa-cloud-download fa-lg"></i> 服务端导出<i
                                    class="el-icon-caret-bottom el-icon--right"></i>
                            </el-button>
                            <el-dropdown-menu slot="dropdown">
                                <el-dropdown-item command="0" :disabled="exportDis">当页数据</el-dropdown-item>
                                <el-dropdown-item command="1" :disabled="exportDis">查询数据</el-dropdown-item>
                                <el-dropdown-item command="-1" :disabled="exportDis">全部数据</el-dropdown-item>
                            </el-dropdown-menu>
                        </el-dropdown>
                    </el-form-item>


                    <el-form-item>
                        <el-dropdown @command="exportByJson">
                            <el-button type="primary" size="small"><i
                                    class="fa fa-cloud-download fa-lg"></i> 前端导出<i
                                    class="el-icon-caret-bottom el-icon--right"></i>
                            </el-button>
                            <el-dropdown-menu slot="dropdown">
                                <el-dropdown-item command="0" :disabled="exportDis">当页数据</el-dropdown-item>
                                <el-dropdown-item command="1" :disabled="exportDis">查询数据</el-dropdown-item>
                                <el-dropdown-item command="-1" :disabled="exportDis">全部数据</el-dropdown-item>
                            </el-dropdown-menu>
                        </el-dropdown>
                    </el-form-item>

                </el-form>

            </el-form>


        </el-col>
        <!----------------------------------------------工具条结束------------------------------------------------------->


        <!-----------------------------------------tab_sys_user查询列表开始---------------------------------------------->
        <el-table :data="dataList" :height="tableHigth" v-loading="listLoading" @selection-change="selsChange"
                  highlight-current-row stripe border resizable>
            <el-table-column type="selection"></el-table-column>
            <el-table-column type="index" label="序号" width="80"></el-table-column>
            <el-table-column prop="userId" show-overflow-tooltip label="用户名" label-class-name="el-tooltip">
            </el-table-column>
            <el-table-column prop="userName" show-overflow-tooltip label="姓名" label-class-name="el-tooltip">
            </el-table-column>
            <el-table-column prop="lastTime" show-overflow-tooltip label="最近登入时间" label-class-name="el-tooltip"
                             :formatter="formatDate">
            </el-table-column>
            <el-table-column prop="userAmount" show-overflow-tooltip label="登入次数" label-class-name="el-tooltip">
            </el-table-column>
            <el-table-column prop="userValid" show-overflow-tooltip label="用户状态" label-class-name="el-tooltip">
                <template slot-scope="scope">
                    <el-tag
                            :type="scope.row.userValid ? 'primary' : 'danger'"
                            close-transition>{{scope.row.userValid ? '正常' : '锁定'}}


                    </el-tag>
                </template>
            </el-table-column>
            <el-table-column label="修改密码" width="100">
                <template slot-scope="scope">
                    <el-button @click="handlePw(scope.$index, scope.row)" type="text" size="small" title="修改密码"><span
                            style="color:#F00">修改密码</span></el-button>
                </template>
            </el-table-column>
            <el-table-column label="操作" width="140" header-align="center" align="center">
                <template slot-scope="scope">
                    <el-button-group>
                        <el-button size="small" type="primary"
                                   @click="handleSave(scope.row.userId)" icon="edit" title="编辑"></el-button>
                        <el-button size="small" type="danger"
                                   @click="handleDel(scope.$index, scope.row)" icon="delete" title="删除"></el-button>
                    </el-button-group>
                </template>
            </el-table-column>
        </el-table>
        <!-----------------------------------------tab_sys_user查询列表结束---------------------------------------------->


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


        <!--修改密码-->
        <el-dialog title="修改密码" v-model="editPwVisible" label-suffix=":" :close-on-click-modal="false" size="large">
            <el-form :model="editPwForm" ref="editPwForm" label-width="120px" :rules="editPwRules">
                <el-form-item label="用户名" prop="userIdView">
                    <el-input v-model="editPwForm.userIdView" auto-complete="off" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="新密码" prop="pwView">
                    <el-input type="password" v-model="editPwForm.pwView" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="确认新密码" prop="pwConfirmView">
                    <el-input type="password" v-model="editPwForm.pwConfirmView" auto-complete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click.native="editPwVisible = false">取消</el-button>
                <el-button type="primary" @click.native="editPw" :loading="editPwLoading">提交</el-button>
            </div>
        </el-dialog>

    </section>

    <section v-else-if="showSave">
        <component @backConfig="backConfig" :userId="userIdQuery" v-bind:is=userSave>
        </component>
    </section>
</template>


<script>
    import tableHeight from '../../../js/heightUtil'
    import purviewButton from '../../../js/purviewUtil'
    import dateUtil from '../../../js/dateUtil'
    import {exportDataExcel, exportJsonExcel} from '../../../js/excelUtil'
    import baseUtil from '../../../js/baseUtil'
import {pagingList, save, remove, editPw, exportByJson} from './userConfig.api'
    import userSave from './userSave'

    export default {
      data () {
        let validatePass = (rule, value, callback) => {
          if (value === '') {
            callback(new Error('请输入密码'))
          } else {
            if (this.editPwForm.pwView !== '') {
              this.$refs.editPwForm.validateField('pwConfirmView')
            }
            callback()
          }
        }
    let validatePass2 = (rule, value, callback) => {
          if (value === '') {
            callback(new Error('请再次输入密码'))
          } else if (value !== this.editPwForm.pwView) {
            callback(new Error('两次输入密码不一致!'))
          } else {
            callback()
          }
        }

    return {
          // 显示配置用户信息
          userSave: userSave,
          userIdQuery: '',
          showConfig: true,
          showSave: true,

          dataList: [], // 查询列表
          listLoading: false, // 加载查询list
          tableHigth: '', // 列表高度
          sels: [], // 列表选中列
          total: 0, // 总数

          // 查询条件
          filters: {
            pageIndex: 0,
            pageSize: 15,
            userIdQuery: ''
          },

          editPwVisible: false, // 修改密码界面是否显示
          editPwLoading: false,
          editPwForm: {
            userIdView: '',
            pwView: '',
            pwConfirmView: '',
            userId: '',
            passWord: ''
          },

          editPwRules: {
            pwView: [
              {required: true, message: '请输入新密码', trigger: 'blur'},
              {min: 6, message: '密码长度至少为6位', trigger: 'blur'},
              {validator: validatePass, trigger: 'blur'}
            ],
            pwConfirmView: [
              {required: true, message: '请输入确认新密码', trigger: 'blur'},
              {min: 6, message: '密码长度至少为6位', trigger: 'blur'},
              {validator: validatePass2, trigger: 'blur'}
            ]
          },

          // 导出
          exportDis: false
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

        /** *************************************修改密码开始*********************************************************/

        handlePw: function (index, row) {
          this.editPwVisible = true
          this.editPwForm.userIdView = row.userId
        },

        editPw: function () {
          this.$refs.editPwForm.validate((valid) => {
            if (valid) {
              this.$confirm('确认提交吗？', '提示', {}).then(() => {
                this.editPwLoading = true
                this.editPwForm.userId = baseUtil(this.editPwForm.userIdView)
                this.editPwForm.passWord = baseUtil(this.editPwForm.pwView)
                editPw(this.editPwForm).then((res) => {
                  this.editPwLoading = false
                  if (res.error) {
                    this.$message({
                      showClose: true,
                      message: res.error,
                      type: 'error'
                    })
                  } else {
                    this.$message({
                      showClose: true,
                      message: '修改成功',
                      type: 'success'
                    })
                    this.editPwLoading = false
                    this.$refs['editPwForm'].resetFields()
                  }
                }).catch(e => {
                })
              }).catch(e => {
              })
            }
          })
        },
        /** *************************************修改密码结束*********************************************************/

        /** *************************************删除开始*************************************************************/
        // 点击勾选框
        selsChange: function (sels) {
          this.sels = sels
        },
        handleDel: function (index, row) {
          this.$confirm('确认删除该记录吗?', '提示', {
            type: 'warning'
          }).then(() => {
            let para = {deleteIds: row.userId}
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
          const ids = this.sels.map(item => item.userId).toString()
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

        /** ***********************************用户配置开始************************************************************/
        handleSave: function (userId) {
          this.userIdQuery = userId
          this.showConfig = false// 配置页面
          this.showUser = false// 用户页面
        },
        backConfig: function () {
          this.showConfig = true// 配置页面
          this.showUser = false// 用户页面
          this.pagingList()
        },

        /** ***********************************用户配置结束************************************************************/

        /** ************************************导出开始**************************************************************/
        exportByServer (exportType) {
          this.filters.exportType = exportType
          this.exportDis = true
          exportDataExcel('/act-alone-web/bdad/userConfig/exportExcel.do', this.filters)
          // 如果不还原导出类型 会造成导出报错
          this.filters.exportType = ''
          this.exportDis = false
        },

        exportByJson: function (exportType) {
          this.exportDis = true
          exportByJson(this.filters).then((res) => {
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
            if (j === 'userSex') {
              if (v[j] === true) {
                return '男'
              } else {
                return '女'
              }
            } else {
              return v[j]
            }
          }))
        }

        /** ************************************导出结束**************************************************************/

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