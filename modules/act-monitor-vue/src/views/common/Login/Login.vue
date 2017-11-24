<template>
    <section>
        <el-row id="box"></el-row>
        <div class="cent-box">
            <el-row class="cent-box-header">
                <h1 class="main-title">全国统一监控平台</h1>
                <h2 class="sub-title"></h2>
            </el-row>

            <el-row class="cont-main clearfix">

                <el-form :model="ruleForm2" :rules="rules2" ref="ruleForm2" label-position="left" label-width="0px">
                    <el-form-item prop="userId">
                        <el-input type="text" v-model="ruleForm2.userId" auto-complete="off"
                                  placeholder="账号"></el-input>
                    </el-form-item>

                    <el-form-item prop="password">
                        <el-input type="password" v-model="ruleForm2.password" auto-complete="off"
                                  placeholder="密码"></el-input>
                    </el-form-item>

                    <el-form-item prop="verifyId">
                        <el-input type="text" v-model="ruleForm2.verifyId" auto-complete="off"
                                  @keyup.13.native="handleSubmit2" placeholder="验证码">
                        </el-input>
                        <img v-bind:src=verification class="imgcode" @click="changeVerify()" title="看不清？点击更换">
                    </el-form-item>

                    <el-form-item style="width:100%;">
                        <el-button type="primary" style="width:100%; " @click.native.prevent="handleSubmit2"
                                   :loading="logining">登录
                        </el-button>
                    </el-form-item>

                    <el-form-item style="width:100%;">
                        <el-button type="primary" style="width:100%; " @click.native.prevent="handleReset2">重置
                        </el-button>
                    </el-form-item>


                </el-form>

            </el-row>
        </div>

        <!--<div class="footer">
            <p>vue2.2.2 + element-ui1.3.6 + webpack2.2.1</p>
            <p>Designed By ACT & <a href="http://element.eleme.io/#/zh-CN">eleme.api</a> 2017</p>
        </div>-->
    </section>
</template>

<script>
    import {login, cuInter} from './Login.api'
import {mapActions, mapGetters} from 'vuex'
import baseUtil from '../../../js/baseUtil'
import particlesJS from '../../../js/loginUtil.js'
import '../../../styles/login/login.css'
import storage from '../../../js/storage'

    export default {
      data () {
        return {
          logining: false,
          codeSrc: '',
          verification: '',
          ruleForm: {
            userId: '',
            password: '',
            verifyId: ''
          },
          ruleForm2: {
            userId: '',
            password: '',
            verifyId: ''
          },
          rules2: {
            userId: [
              {required: true, message: '请输入账号', trigger: 'blur'}
            ],
            password: [
              {required: true, message: '请输入密码', trigger: 'blur'},
              {required: true, message: '密码长度至少为6位', min: 6, trigger: 'blur'}
            ],
            verifyId: [
              {required: true, message: '请输入验证码', trigger: 'blur'},
              {required: true, message: '验证码长度必须为4位', min: 4, max: 4, trigger: 'blur'}
            ]
          },
          checked: true
        }
  },
      // 增加菜单路由
      computed: {
        ...mapGetters([
          'menuitems',
          'isLoadRoutes'
        ])
      },
      methods: {
        getCuInter () {
          cuInter().then(res => {
            if (res.error) {
              this.$message({
                showClose: true,
                message: '未获取CU接口信息',
                type: 'warn'
              })
            } else {
              if (res.success) {
                storage.set('cuInter', res.success.cuIP)
              }
            }
          }
          )
        },

        changeVerify () {
          let time = new Date().getTime()
          this.verification = '/act-monitor-web/common/loginVue/verification.do?d=' + time
        },
        handleReset2 () {
          this.$refs.ruleForm2.resetFields()
        },
        // 单点登录是设置用户名和密码
        userLogin () {
          let userCode = sessionStorage.getItem('userCode')
          if (userCode === 'admin') {
            this.ruleForm2.userId = userCode
            this.ruleForm2.password = 'ADMIN1'// 此参数没用到，为了校验通过赋一个任意字符串
            this.ruleForm2.verifyId = 'none'
            this.handleSubmit2()
          }
        },
        handleSubmit2 () {
          this.$refs.ruleForm2.validate((valid) => {
            if (valid) {
              this.logining = true
              this.ruleForm.userId = baseUtil(this.ruleForm2.userId)
              this.ruleForm.password = baseUtil(this.ruleForm2.password)
              this.ruleForm.verifyId = this.ruleForm2.verifyId
              login(this.ruleForm).then(data => {
                this.logining = false
                let {msg, code, user, authorization, secMenuPurview, unitSystemVersion, tabSysManageInfo} = data
                if (code !== 200) {
                  this.$message({
                    showClose: true,
                    message: msg,
                    type: 'error'
                  })
                  this.changeVerify()
                } else {
                  sessionStorage.setItem('user', JSON.stringify(user))
                  sessionStorage.setItem('authorization', JSON.stringify(authorization))
                  sessionStorage.setItem('secMenuPurview', JSON.stringify(secMenuPurview))
                  sessionStorage.setItem('unitSystemVersion', JSON.stringify(unitSystemVersion))
                  sessionStorage.setItem('tabSysManageInfo', JSON.stringify(tabSysManageInfo))
                  if (!this.isLoadRoutes) {
                    this.addMenu(authorization)
                    this.$router.options.routes = this.menuitems
                    this.$router.addRoutes(this.menuitems)
                    this.loadRoutes()
                  }
                  this.firstActive(JSON.stringify(user))
                  this.$router.push({path: '/'})
                  this.ruleForm2.password = ''
                  this.ruleForm2.verifyId = ''
                }
              })
            }
          })
        },
        ...mapActions([
          'addMenu',
          'loadRoutes',
          'firstActive'

        ])
      },
    mounted () {
        this.changeVerify()
        this.userLogin()
        this.getCuInter()
        if (this.isLoadRoutes) {
          sessionStorage.removeItem('user')
          sessionStorage.removeItem('authorization')
          sessionStorage.removeItem('secMenuPurview')
          location.replace('')
        }
        ;
        particlesJS('box',
          {
            'particles': {
              'number': {
                'value': 25,
                'density': {
                  'enable': true,
                  'value_area': 800
                }
              },
              'color': {
                'value': '#e5e5e5'
              },
              'shape': {
                'type': 'circle',
                'stroke': {
                  'width': 0,
                  'color': '#000'
                },
                'polygon': {
                  'nb_sides': 5
                },
                'image': {
                  'src': 'img/github.svg',
                  'width': 100,
                  'height': 100
                }
              },
              'opacity': {
                'value': 0.5,
                'random': false,
                'anim': {
                  'enable': false,
                  'speed': 1,
                  'opacity_min': 0.1,
                  'sync': false
                }
              },
              'size': {
                'value': 15,
                'random': true,
                'anim': {
                  'enable': false,
                  'speed': 5,
                  'size_min': 0.1,
                  'sync': false
                }
              },
              'line_linked': {
                'enable': true,
                'distance': 150,
                'color': '#ddd',
                'opacity': 0.4,
                'width': 1
              },
              'move': {
                'enable': true,
                'speed': 2,
                'direction': 'none',
                'random': false,
                'straight': false,
                'out_mode': 'out',
                'attract': {
                  'enable': false,
                  'rotateX': 600,
                  'rotateY': 1200
                }
              }
            },
            'interactivity': {
              'detect_on': 'canvas',
              'events': {
                'onhover': {
                  'enable': false,
                  'mode': 'repulse'
                },
                'onclick': {
                  'enable': true,
                  'mode': 'push'
                },
                'resize': true
              },
              'modes': {
                'grab': {
                  'distance': 400,
                  'line_linked': {
                    'opacity': 1
                  }
                },
                'bubble': {
                  'distance': 400,
                  'size': 40,
                  'duration': 2,
                  'opacity': 8,
                  'speed': 3
                },
                'repulse': {
                  'distance': 200
                },
                'push': {
                  'particles_nb': 4
                },
                'remove': {
                  'particles_nb': 2
                }
              }
            },
            'retina_detect': true,
            'config_demo': {
              'hide_card': false,
              'background_color': '#b61924',
              'background_image': '',
              'background_position': '50% 50%',
              'background_repeat': 'no-repeat',
              'background_size': 'cover'
            }
          }
        )
  }
    }
</script>

<style lang="scss" scoped>
</style>