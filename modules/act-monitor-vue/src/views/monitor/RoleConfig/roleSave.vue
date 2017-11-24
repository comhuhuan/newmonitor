<template>
    <section>


        <el-col>
        <el-form :inline="true" class="view-table">
            <el-form-item style="float:right;">
                <el-button type="danger" v-on:click="handleBack(false)" size="small"><i class="fa fa-undo fa-lg"></i> 返回

                </el-button>
            </el-form-item>
        </el-form>
        </el-col>

        <el-col>
        <el-form ref="saveForm" :model="saveForm" label-width="140px" label-suffix=":" :rules="saveFormRules">

            <el-form-item label="角色名称" prop="roleName">
                <el-input v-model="saveForm.roleName" placeholder="必填项"></el-input>
            </el-form-item>

            <el-form-item label="角色描述" prop="roleDesc">
                <el-input type="textarea" v-model="saveForm.roleDesc" placeholder="必填项"></el-input>
            </el-form-item>


            <el-form-item label="页面权限">

                <el-checkbox-group v-model="saveForm.purviews" v-for="(item,index) in menuCheck" key="item.menuId">
                    <el-row><el-checkbox  :label="item.menuId" :key="index" @change="handleCheckAll(item.children,index)">{{item.menuName}}</el-checkbox></el-row>
                    <el-row>
                        <el-checkbox v-for="(itemChildren,indexChildren) in item.children" :label="itemChildren.menuId"
                                     :key="indexChildren">{{itemChildren.menuName}}

                        </el-checkbox>
                    </el-row>
                </el-checkbox-group>
            </el-form-item>

            <el-form-item>
                <el-button type="primary" @click.native="save" :loading="saveLoading">保存</el-button>
                <el-button @click.native="handleBack(true)">取消</el-button>
            </el-form-item>

        </el-form>
        </el-col>

    </section>
</template>


<script>
    import baseUtil from '../../../js/baseUtil'
    import tableHeight from '../../../js/heightUtil'
    import dateUtil from '../../../js/dateUtil'
    import {initialize, exisRole, save, getSysRole} from './roleConfig.api'

    export default {

      data () {
        var validateRole = (rule, value, callback) => {
          if (value === '') {
            callback(new Error('请输入角色名称'))
          } else {
            let para = {roleId: this.roleId}
            if (!this.roleId) {
              para.roleId = ''
            }
            exisRole(para).then((res) => {
              if (res.success.roleList.indexOf(value) !== -1) {
                callback(new Error('该角色名称已经存在'))
              } else {
                callback()
              }
            })
          }
        }

    return {
          isIndeterminate: true,
          saveLoading: false,

          saveForm: {
            commonFlag: 'add',
            roleName: '',
            roleDesc: '',
            purviews: []
          },

          menuCheck: [],
          checkedFlage: [],

          // 保存规则
          saveFormRules: {
            roleName: [
              {required: true, message: '请输入角色名称', trigger: 'blur'},
              {validator: validateRole, trigger: 'blur'}
            ],
            roleDesc: [
              {required: true, message: '请输入角色描述', trigger: 'blur'}
            ]
          }

        }
      },
      props: ['roleId'],

      methods: {

        handleCheckAll: function (children, index) {
          let temp = this.saveForm.purviews
          this.checkedFlage[index] = !this.checkedFlage[index]
          if (this.checkedFlage[index]) {
            children.forEach(
              function (item) {
                if (temp.indexOf(item.menuId) === -1) {
                  temp.push(item.menuId)
                }
              }
            )
          } else {
            let add = []
            let remove = []
            children.forEach(
              function (item) {
                remove.push(item.menuId)
              }
            )
            temp.forEach(
              function (item) {
                if (remove.indexOf(item) === -1) {
                  add.push(item)
                }
              }
            )
            this.saveForm.purviews = add
          }
        },

        getSysRole: function () {
          if (this.roleId) {
            this.showPassWord = false
            this.saveForm.commonFlag = 'edit'
            let para = {roleId: this.roleId}
            getSysRole(para).then((res) => {
              if (res.error) {
                this.$message({
                  showClose: true,
                  message: res.error,
                  type: 'error'
                })
              } else {
                this.saveForm.roleId = res.success.saveForm.roleId
                this.saveForm.roleName = res.success.saveForm.roleName
                this.saveForm.roleDesc = res.success.saveForm.roleDesc
                this.saveForm.purviews = res.success.purviews
                this.saveForm.createDate = res.success.saveForm.createDate
                this.saveForm.updateDate = res.success.saveForm.updateDate
                this.saveForm.creator = res.success.saveForm.creator
                this.saveForm.modifier = res.success.saveForm.modifier
              }
            })
          }
        },

        save: function () {
          this.$refs.saveForm.validate((valid) => {
            if (valid) {
              this.$confirm('确认提交吗？', '提示', {}).then(() => {
                this.saveLoading = true

                const currentUser = JSON.parse(sessionStorage.getItem('user'))

                if (this.roleId) {
                  this.saveForm.createDate = dateUtil.formatDate.format(new Date(this.saveForm.createDate), 'yyyy-MM-dd hh:mm:ss')
                } else {
                  this.saveForm.creator = currentUser.userId
                  this.saveForm.createDate = dateUtil.formatDate.format(new Date(), 'yyyy-MM-dd hh:mm:ss')
                }

                this.saveForm.modifier = currentUser.userId
                this.saveForm.updateDate = dateUtil.formatDate.format(new Date(), 'yyyy-MM-dd hh:mm:ss')

                save(this.saveForm).then((res) => {
                  this.saveLoading = false
                  if (res.error) {
                    this.$message({
                      showClose: true,
                      message: res.error,
                      type: 'error'
                    })
                  } else {
                    this.$message({
                      showClose: true,
                      message: '新增成功',
                      type: 'success'
                    })
                    this.$refs['saveForm'].resetFields()
                    this.handleBack(false)
                  }
                }).catch(e => {
                })
              }).catch(e => {
              })
            }
          })
        },

        // 返回服务器页面
        handleBack: function (flag) {
          if (flag) {
            this.$confirm('确认不保存当前页面信息吗？', '提示', {}).then(() => {
              this.$emit('backConfig')
            }).catch(e => {
            })
          } else {
            this.$emit('backConfig')
          }
        },

        // 加载菜单列表
        initialize: function () {
          initialize().then((res) => {
            if (res.error) {
              this.$message({
                showClose: true,
                message: res.error,
                type: 'error'
              })
            } else {
              this.menuCheck = res.success.menuCheck
            }
          })
        }

      },
      mounted () {
        this.initialize()
        this.getSysRole()
    }
    }
</script>