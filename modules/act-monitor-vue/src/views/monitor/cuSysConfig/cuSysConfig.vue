<template>
    <section>
        <div slot="footer" class="dialog-footer">
            <el-button type="success" @click.native="issuedInstructions" :loading="issuedInstructionsLoading"><i
                    class="el-icon-caret-bottom"></i> 下发
            </el-button>

            <el-button type="primary" @click="instructionsInfo"> 上次下发详情</el-button>

            <el-button type="success" @click.native="resetSubmit" :loading="resetLoading"><i
                    class="fa fa-reply fa-lg"></i> 还原


            </el-button>
            <el-button type="primary" @click.native="updateSubmit" :loading="updateLoading"><i
                    class="fa fa-cog fa-lg"></i> 保存配置


            </el-button>
        </div>
        <div>
            <el-col>
                <el-dialog :title="title" value="1111" :visible.sync="dialogTableVisible">

                    <el-table :data="pageList">
                        <el-table-column property="ip" label="webService地址"></el-table-column>
                        <el-table-column property="status" label="下发状态"></el-table-column>
                    </el-table>

                    <div class="wrap">
                        <el-pagination
                                layout="total, sizes, prev, pager, next, jumper"
                                @size-change="handleSizeChange"
                                @current-change="handleCurrentChange"

                                :page-sizes="[10, 30, 50]"
                                :page-size="params.pageSize"
                                :total="total"
                                v-if="total"
                                class="fr"
                        >
                            <!--  :current-page.sync="params.pageIndex" -->
                        </el-pagination>
                    </div>


                </el-dialog>


            </el-col>


        </div>


        <el-form :model="sysForm" ref="sysForm" label-position="right" label-width="350px" label-suffix=":">


            <el-row v-for="(item,index) in sysForm.sysList" :key="index">


                <!--显示框 showType= '3' -->
                <el-form-item :prop="'sysList.' + index + '.configval'" :label="item.title"
                              :rules=saveFormRules.configval v-if="item.showType === '3'">
                    <el-input v-model="item.configval" size="small" :placeholder="item.defaultval">
                    </el-input>
                </el-form-item>

                <!--输入框 showType= '0' -->
                <el-form-item :prop="'sysList.' + index + '.configval'" :label="item.title"
                              :rules=saveFormRules.configval v-if="item.showType === '0'">
                    <el-input class="el-input-ll" v-model="item.configval" size="small" :placeholder="item.defaultval">
                    </el-input>
                </el-form-item>

                <!--开关 showType= '2' -->
                <el-form-item :prop="'sysList.' + index + '.configval'" :label="item.title"
                              :rules=saveFormRules.configval v-if="item.showType === '2'">
                    <el-switch v-model="item.configval" on-text="开启" on-value="1" off-text="关闭"
                               off-value="0"></el-switch>
                </el-form-item>

                <!--下拉框-->
                <el-form-item :prop="'sysList.' + index + '.configval'" :label="item.title"
                              :rules=saveFormRules.configval v-if="item.showType === '1'">
                    <el-select v-model="item.configval" filterable clearable
                               placeholder="请选择">
                        <el-option v-for="(item,index) in hashOptions" :label="item.label" :key="index"
                                   :value="item.value">
                        </el-option>
                    </el-select>

                </el-form-item>

            </el-row>

        </el-form>

        <!--<div slot="footer" class="dialog-footer">-->
            <!--<el-button type="success" @click.native="resetSubmit" :loading="resetLoading"><i-->
                    <!--class="fa fa-reply fa-lg"></i> 还原-->


            <!--</el-button>-->
            <!--<el-button type="primary" @click.native="updateSubmit" :loading="updateLoading"><i-->
                    <!--class="fa fa-cog fa-lg"></i> 提交-->


            <!--</el-button>-->
        <!--</div>-->

    </section>
</template>


<script>
    import {initialize, resetConfig, updateConfig, issuedInstructions, instructionsInfo} from './cuSysConfig.api'

    export default {
      data () {
        return {
          queryType: {
            type: 'CU'
          },
          sysForm: {
            // 配置信息
            sysList: []
          },
          addForm: {
            // 配置信息
            sysList: []
          },
          params: {
            pageIndex: 0,
            pageSize: 20
          },
          instructionsInfoLoading: false,
          dialogTableVisible: false,
          dialogFormVisible: false,
          total: '0',
          lastTime: '',
          pageList: [],
          title: '',
          issuedInstructionsLoading: false,
          resetLoading: false,
          updateLoading: false,

          // 保存规则
          saveFormRules: {
            configval: [
              {required: true, message: '请输入参数', trigger: 'blur'}
            ]
          },

          hashOptions: [
            {'value': '0', 'label': '无hash'},
            {'value': '1', 'label': 'MD5'},
            {'value': '2', 'label': 'SHA-1'}]

        }
      },
      methods: {

        handleSizeChange (val) {
          this.params.pageSize = val
          this.handleCurrentChange(1)
        },
        handleCurrentChange (val) {
          this.params.pageIndex = val - 1
          this.getPageList()
        },

        initAddForm: function () {
          let temp = []
          this.sysForm.sysList.forEach(
            function (item) {
              temp.push(JSON.stringify(item))
            }
          )
          this.addForm.sysList = temp
          if (this.sysForm.sysList.length === 1) {
            this.addForm.sysList.push('')
          }
        },

        /** ****************************************初始化开始************************************************************/
        initialize: function () {
          initialize().then((res) => {
            if (res.error) {
              this.$message({
                showClose: true,
                message: res.error,
                type: 'error'
              })
            } else {
              this.sysForm.sysList = res.success
            }
            this.listLoading = false
          })
        },
        /** ****************************************初始化结束************************************************************/

        /** ****************************************下发配置开始**************************************************************/
        issuedInstructions: function () {
          this.$confirm('确认下发配置吗？', '提示', {}).then(() => {
            this.issuedInstructionsLoading = true
            issuedInstructions(this.queryType).then((res) => {
              this.issuedInstructionsLoading = false
              if (res.error) {
                this.$message({
                  showClose: true,
                  message: res.error,
                  type: 'error'
                })
              } else {
                this.$message({
                  showClose: true,
                  message: res.success,
                  type: 'success'
                })
              }
            })
          })
    },
        /** ****************************************下发配置结束**************************************************************/

          /** ****************************************xia详情开始**************************************************************/
          instructionsInfo: function () {
              this.pageList = []

              this.infoParams = Object.assign(this.params, this.queryType)
              this.instructionsInfoLoading = true
              instructionsInfo(this.infoParams).then((res) => {
                  this.instructionsInfoLoading = false
                  if (res.error) {
                      this.$message({
                          showClose: true,
                          message: res.error,
                          type: 'error'
                      })
                  } else {
                      if (!res.success.rows || !res.success.rows.length) {
                          this.$message({
                              showClose: true,
                              message: '上次下发全部成功 ',
                              type: 'success'
                          })
                      }
                      res.success.rows.forEach((item, index) => {
                          item.status = item.status === 1 ? '处理异常' : '链接异常'
                      })
                      /* =========================== */
                      //                   使用时应注释
                      //                        res.success.rows.forEach(item => {
                      //                            item.stateType = this.searchFilters.stateType
                      //                        })
                      /* =========================== */
                      this.pageList = res.success.rows
                      if (res.success.rows || res.success.rows.length) {
                          this.lastTime = res.success.rows[0].date
                          this.total = res.success.total
                          this.dialogTableVisible = true
                          let unixTimestamp = new Date(this.lastTime)
                          let commonTime = unixTimestamp.toLocaleString()
                          this.title = '下发时间：' + commonTime
                      }
                  }
              })
          },
          /** ****************************************详情结束**************************************************************/

          /** ****************************************还原开始**************************************************************/
        resetSubmit: function () {
          this.$confirm('确认还原配置吗？', '提示', {}).then(() => {
            this.resetLoading = true
            resetConfig().then((res) => {
              this.resetLoading = false
              if (res.error) {
                this.$message({
                  showClose: true,
                  message: res.error,
                  type: 'error'
                })
              } else {
                this.$message({
                  showClose: true,
                  message: '还原配置成功',
                  type: 'success'
                })
              }
            })
          })
    },
        /** ****************************************还原结束**************************************************************/

        /** ****************************************更新开始**************************************************************/
        updateSubmit: function () {
          this.$refs.sysForm.validate((valid) => {
            if (valid) {
              this.$confirm('确认更新配置吗？', '提示', {}).then(() => {
                this.initAddForm()
                this.updateLoading = true
                updateConfig(this.addForm).then((res) => {
                  this.updateLoading = false
                  if (res.error) {
                    this.$message({
                      showClose: true,
                      message: res.error,
                      type: 'error'
                    })
                  } else {
                    this.$message({
                      showClose: true,
                      message: '更新配置成功',
                      type: 'success'
                    })
                    this.$refs['sysForm'].resetFields()
                  }
                  this.initialize()
                })
              })
            } else {
              this.$message({
                showClose: true,
                message: '请完成必填信息,且格式正确!',
                type: 'error'
              })
            }
          })
        }
        /** ****************************************更新结束**************************************************************/

        /** ****************************************上传下载开始**************************************************************/

        /** ****************************************上传下载结束**************************************************************/

      },

      mounted () {
        this.initialize()
    }
    }
</script>



<style lang="scss" scope>

    .el-input-ll{
        width:50%;
    }

</style>