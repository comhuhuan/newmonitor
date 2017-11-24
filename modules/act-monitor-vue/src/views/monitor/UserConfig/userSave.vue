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

            <el-form-item label="用户名" prop="userId">
                <el-input v-model="saveForm.userId" placeholder="用户名至少为4位"></el-input>
            </el-form-item>

            <el-form-item label="姓名" prop="userName">
                <el-input v-model="saveForm.userName" placeholder="必填项"></el-input>
            </el-form-item>

            <el-form-item label="用户描述" prop="userDescrip">
                <el-input type="textarea" v-model="saveForm.userDescrip" placeholder="必填项"></el-input>
            </el-form-item>


            <el-form-item label="密码" prop="displayPassword" v-if="showPassWord">
                <el-input type="password" v-model="saveForm.displayPassword" placeholder="密码至少为6位且与确认密码一致"></el-input>
            </el-form-item>


            <el-form-item label="确认密码" prop="confirmPassword" v-if="showPassWord">
                <el-input type="password" v-model="saveForm.confirmPassword"
                          placeholder="确认密码至少为6位且与密码一致"></el-input>
            </el-form-item>


            <el-form-item label="性别">
                <el-radio-group v-model="saveForm.userSex">
                    <el-radio :label=true>男</el-radio>
                    <el-radio :label=false>女</el-radio>
                </el-radio-group>
            </el-form-item>

            <el-form-item label="联系电话">
                <el-input v-model="saveForm.userTel" placeholder="选填项"></el-input>
            </el-form-item>

            <el-form-item label="移动电话">
                <el-input v-model="saveForm.userMobile" placeholder="选填项"></el-input>
            </el-form-item>

            <el-form-item label="电子邮箱" prop="userEmail">
                <el-input v-model="saveForm.userEmail" placeholder="选填项"></el-input>
            </el-form-item>

            <el-form-item label="状态">
                <el-radio-group v-model="saveForm.userValid">
                    <el-radio :label=true>正常</el-radio>
                    <el-radio :label=false>锁定</el-radio>
                </el-radio-group>
            </el-form-item>

            <el-form-item label="密保问题" prop="userQuestion">
                <el-select v-model="saveForm.userQuestion" filterable clearable placeholder="必选项">
                    <el-option v-for="(item,index) in questionOptions" :label="item.label" :key="index"
                               :value="item.value">
                    </el-option>
                </el-select>
            </el-form-item>

            <el-form-item label="密保答案" prop="userAnswer">
                <el-input v-model="saveForm.userAnswer" placeholder="必填项"></el-input>
            </el-form-item>


            <el-form-item label="用户角色">

                <el-transfer v-model="saveForm.roleIds" :data="roleList" filterable :titles="roleTitles"></el-transfer>

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
    import dateUtil from '../../../js/dateUtil'
    import {initialize, getSysUser, save, exisUser} from './userConfig.api'

    export default {
      data () {
        var validateUser = (rule, value, callback) => {
          if (value === '') {
            callback(new Error('请输入用户名'))
          } else {
            let para = {userId: this.userId}
            if (!this.userId) {
              para.userId = ''
            }
            exisUser(para).then((res) => {
              if (res.success.userList.indexOf(value) !== -1) {
                callback(new Error('该用户名已经存在'))
              } else {
                callback()
              }
            })
          }
        }

    var validatePass = (rule, value, callback) => {
          if (value === '') {
            callback(new Error('请输入密码'))
          } else {
            if (this.saveForm.confirmPassword !== '') {
              this.$refs.saveForm.validateField('confirmPassword')
            }
            callback()
          }
        }

    var validatePass2 = (rule, value, callback) => {
          if (value === '') {
            callback(new Error('请输入确认密码'))
          } else if (value !== this.saveForm.displayPassword) {
            callback(new Error('两次输入密码不一致!'))
          } else {
            callback()
          }
        }

    return {

          showPassWord: true,

          saveForm: {
            commonFlag: 'add',
            userId: '', // 用户名
            userName: '', // 姓名
            userDescrip: '', // 用户描述
            userPassword: '', // 提交密码
            displayPassword: '', // 显示密码
            confirmPassword: '', // 确认密码
            userSex: true, // 性别
            userTel: '', // 联系电话
            userMobile: '', // 移动电话
            userEmail: '', // 电子邮箱
            userValid: true, // 状态
            userQuestion: '最好朋友的名字', // 密保问题
            userAnswer: '', // 密保答案
            roleIds: [], // 角色
            curUserId: ''
          },

          saveLoading: false,

          questionOptions: [
            {'value': '最好朋友的名字', 'label': '最好朋友的名字'},
            {'value': '最喜欢人的名字', 'label': '最喜欢人的名字'},
            {'value': '最喜欢的运动', 'label': '最喜欢的运动'}

          ],

          roleList: [],

          roleTitles: ['角色列表', '被选择角色列表'],

          // 保存规则
          saveFormRules: {
            userId: [
              {required: true, message: '请输入用户名', trigger: 'blur'},
              {min: 4, message: '用户名长度至少为4位', trigger: 'blur'},
              {validator: validateUser, trigger: 'blur'}
            ],
            userName: [
              {required: true, message: '请输入姓名', trigger: 'blur'}
            ],
            userDescrip: [
              {required: true, message: '请输入用户描述', trigger: 'blur'}
            ],
            displayPassword: [
              {required: true, message: '请输入密码', trigger: 'blur'},
              {min: 6, message: '密码长度至少为6位', trigger: 'blur'},
              {validator: validatePass, trigger: 'blur'}
            ],
            confirmPassword: [
              {required: true, message: '请输入确认密码', trigger: 'blur'},
              {min: 6, message: '确认密码长度至少为6位', trigger: 'blur'},
              {validator: validatePass2, trigger: 'blur'}
            ],
            userQuestion: [
              {required: true, message: '请选择密保问题', trigger: 'blur'}
            ],
            userAnswer: [
              {required: true, message: '请输入密保答案', trigger: 'blur'}
            ],
            userEmail: [
              {type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur'}
            ]
          }
        }
      },
      methods: {
        // 返回列表页面
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

        getSysUser: function () {
          if (this.userId) {
            this.showPassWord = false
            this.saveForm.commonFlag = 'edit'
            let para = {userId: this.userId}
            getSysUser(para).then((res) => {
              if (res.error) {
                this.$message({
                  showClose: true,
                  message: res.error,
                  type: 'error'
                })
              } else {
                this.saveForm.curUserId = res.success.saveForm.userId
                this.saveForm.userId = res.success.saveForm.userId
                this.saveForm.userPassword = res.success.saveForm.userPassword
                this.saveForm.userName = res.success.saveForm.userName
                this.saveForm.userSex = res.success.saveForm.userSex
                this.saveForm.userTel = res.success.saveForm.userTel
                this.saveForm.userMobile = res.success.saveForm.userMobile
                this.saveForm.userEmail = res.success.saveForm.userEmail
                this.saveForm.userDescrip = res.success.saveForm.userDescrip
                this.saveForm.userValid = res.success.saveForm.userValid
                this.saveForm.userManager = res.success.saveForm.userManager
                this.saveForm.lastTime = res.success.saveForm.lastTime
                this.saveForm.userAmount = res.success.saveForm.userAmount
                this.saveForm.userQuestion = res.success.saveForm.userQuestion
                this.saveForm.userAnswer = res.success.saveForm.userAnswer
                this.saveForm.createDate = res.success.saveForm.createDate
                this.saveForm.updateDate = res.success.saveForm.updateDate
                this.saveForm.creator = res.success.saveForm.creator
                this.saveForm.modifier = res.success.saveForm.modifier
                this.saveForm.roleIds = res.success.roleIds
              }
            })
          }
        },

        // 加载权限列表
        initialize: function () {
          initialize().then((res) => {
            if (res.error) {
              this.$message({
                showClose: true,
                message: res.error,
                type: 'error'
              })
            } else {
              this.roleList = res.success.roleList
            }
          })
        },

        save: function () {
          this.$refs.saveForm.validate((valid) => {
            if (valid) {
              this.$confirm('确认提交吗？', '提示', {}).then(() => {
                this.saveLoading = true

                const currentUser = JSON.parse(sessionStorage.getItem('user'))

                if (this.showPassWord) {
                  this.saveForm.creator = currentUser.userId
                  this.saveForm.userPassword = baseUtil(this.saveForm.displayPassword)
                  this.saveForm.createDate = dateUtil.formatDate.format(new Date(), 'yyyy-MM-dd hh:mm:ss')
                } else {
                  this.saveForm.createDate = dateUtil.formatDate.format(new Date(this.saveForm.createDate), 'yyyy-MM-dd hh:mm:ss')
                  this.saveForm.lastTime = dateUtil.formatDate.format(new Date(this.saveForm.lastTime), 'yyyy-MM-dd hh:mm:ss')
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
        }

      },
      props: ['userId'],
      mounted () {
        this.initialize()
        this.getSysUser()
    }

    }
</script>