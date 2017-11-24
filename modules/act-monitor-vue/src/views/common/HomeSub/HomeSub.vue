<!-- 子系统 方式部署 页面框架 -->
<template>
    <section>
        <el-row class="container">
            <el-col :span="24" class="header">
                <el-col :span="10" class="logo" :class="collapsed?'logo-collapse-width':'logo-width'">
                    {{collapsed ? '' : sysName}}
                </el-col>
                <el-col :span="10">
                    <div class="tools" @click.prevent="collapse">
                        <i class="fa fa-align-justify"></i>
                    </div>
                </el-col>

                <el-col :span="4" class="userinfo">
                    <el-dropdown trigger="hover">
                        <span class="el-dropdown-link userinfo-inner">{{username}}</span>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item @click.native="aboutSys">关于<i
                                    class="el-icon-caret-bottom el-icon--right"></i></el-dropdown-item>
                            <el-dropdown-item divided><a href="/act-monitor-web/common/login/welcome.do"
                                                         style="text-decoration:none">返回</a></el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </el-col>
            </el-col>

            <el-col :span="24" class="main">
                <aside :class="collapsed?'menu-collapsed':'menu-expanded'">
                    <!--导航菜单 :default-active="activeName" 点击菜单tab后 左侧菜单活跃状态变化-->
                    <el-menu class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose"
                             @select="handleSelect"
                             unique-opened v-show="!collapsed">
                        <template v-for="(item,index) in $router.options.routes" v-if="!item.hidden">
                            <el-submenu :index="index+''">
                                <template slot="title"><i :class="item.iconCls"></i>{{item.name}}</template>
                                <el-menu-item v-for="child in item.children" :index="child.path" v-if="!child.hidden"
                                              :key="child"><i :class="child.iconCls"></i>{{child.name}}
                                </el-menu-item>
                            </el-submenu>
                        </template>
                    </el-menu>
                    <!--导航菜单-折叠后-->
                    <ul class="el-menu el-menu-vertical-demo collapsed" v-show="collapsed" ref="menuCollapsed">
                        <li v-for="(item,index) in $router.options.routes" v-if="!item.hidden" :key="item"
                            class="el-submenu item">
                            <template>
                                <div class="el-submenu__title" style="padding-left: 20px;"
                                     @mouseover="showMenu(index,true)" @mouseout="showMenu(index,false)"><i
                                        :class="item.iconCls"></i></div>
                                <ul class="el-menu submenu" :class="'submenu-hook-'+index"
                                    @mouseover="showMenu(index,true)" @mouseout="showMenu(index,false)">
                                    <li v-for="child in item.children" v-if="!child.hidden" class="el-menu-item"
                                        style="padding-left: 40px;" :class="$route.path==child.path?'is-active':''"
                                        @click="handleCollSelect(index,child.path)"><i
                                            :class="child.iconCls"></i>{{child.name}}
                                    </li>
                                </ul>
                            </template>
                        </li>
                    </ul>
                </aside>

                <section class="content-container">
                    <div class="grid-content bg-purple-light">

                        <el-tabs v-model="activeName" type="card" @tab-remove="handleRemoveTab" closable @tab-click="handleTabClick">
                            <transition name="fade" mode="out-in">
                                <el-tab-pane v-for="(item, index) in this.activeMenu" :key="item" :label="item.title"
                                             :name="item.name">

                                    <el-col :span="24" class="content-wrapper">

                                        <component v-bind:is=item.component>
                                        </component>

                                    </el-col>
                                </el-tab-pane>
                            </transition>
                        </el-tabs>


                    </div>
                </section>
            </el-col>

        </el-row>


        <!--修改密码-->
        <el-dialog title="修改密码" v-model="editPwVisible" :close-on-click-modal="false" size="large">
            <el-form :model="editPwForm" ref="editPwForm" label-width="120px" :rules="editPwRules">
                <el-form-item label="用户名">
                    <el-input v-model="editPwForm.curUserId" auto-complete="off" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="旧密码" prop="userOldPw">
                    <el-input type="password" v-model="editPwForm.userOldPw" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="新密码" prop="newPw">
                    <el-input type="password" v-model="editPwForm.newPw" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="确认新密码" prop="newCPw">
                    <el-input type="password" v-model="editPwForm.newCPw" auto-complete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click.native="editPwVisible = false">取消</el-button>
                <el-button type="primary" @click.native="editPw" :loading="editPwLoading">提交</el-button>
            </div>
        </el-dialog>

    </section>
</template>

<script>
    import {editPw, loadSub, welcome} from './HomeSub.api'
import baseUtil from '../../../js/baseUtil'
import {mapActions, mapGetters} from 'vuex'

export default {
      data () {
        return {
          activeName: '/test',
          sysName: '',
          sysVersion: '',
          collapsed: false,
          // 用户名 用户姓名 用户密码
          userId: '',
          username: '',
          password: '',
          editPwVisible: false, // 修改密码界面是否显示
          editPwLoading: false,
          editPwForm: {
            curUserId: '',
            userOldPw: '',
            newPw: '',
            userOldPws: '',
            newPws: '',
            newCPw: ''
          },
          editPwRules: {
            userOldPw: [
              {required: true, message: '请输入原密码', trigger: 'blur'},
              {min: 6, message: '密码长度至少为6位', trigger: 'blur'}
            ],
            newPw: [
              {min: 6, message: '密码长度至少为6位', trigger: 'blur'},
              {validator: this.validatePass, trigger: 'blur'}
            ],
            newCPw: [
              {min: 6, message: '密码长度至少为6位', trigger: 'blur'},
              {validator: this.validatePass2, trigger: 'blur'}
            ]
          }
        }
      },
      // 增加菜单路由
      computed: {
        ...mapGetters([
          'menuitems',
          'isLoadRoutes',
          'activeMenu'
        ])
      },
      methods: {
        handleTabClick (tab) {
          console.log(tab)
        },
        validatePass: (rule, value, callback) => {
          if (value === '') {
            callback(new Error('请输入密码'))
          } else {
            if (this.editPwForm.newCPw !== '') {
              this.$refs.editPwForm.validateField('newCPw')
            }
            callback()
          }
        },
        validatePass2: (rule, value, callback) => {
          if (value === '') {
            callback(new Error('请再次输入密码'))
          } else if (value !== this.editPwForm.newPw) {
            callback(new Error('两次输入密码不一致!'))
          } else {
            callback()
          }
        },
        changePw () {
          this.editPwVisible = true
          this.editPwForm.curUserId = this.userId
        },
        // 退出登录
        logout: function () {
          this.$confirm('确认退出吗?', '提示', {
            // type: 'warning'
          }).then(() => {
            sessionStorage.removeItem('user')
            sessionStorage.removeItem('authorization')
            sessionStorage.removeItem('secMenuPurview')
            location.replace('')
          }).catch(() => {

          })
        },
        // 修改密码
        editPw: function () {
          this.$refs.editPwForm.validate((valid) => {
            if (valid) {
              this.$confirm('确认提交吗？', '提示', {}).then(() => {
                this.editLoading = true
                this.editPwForm.userOldPws = baseUtil(this.editPwForm.userOldPw)
                this.editPwForm.newPws = baseUtil(this.editPwForm.newPw)
                editPw(this.editPwForm).then((res) => {
                  let {msg, code} = res
                  if (code != 200) {
                    this.$refs['editPwForm'].resetFields()
                    this.editPwLoading = false
                    this.$message({
                      showClose: true,
                      message: '原密码错误,修改密码失败!',
                      type: 'error'
                    })
                  } else {
                    this.$message({
                      showClose: true,
                      message: '修改密码成功,请重新登入!'
                    })
                    sessionStorage.removeItem('user')
                    sessionStorage.removeItem('authorization')
                    this.$router.push('/login')
                  }
                })
              })
            }
          })
        },
        // 折叠导航栏
        collapse: function () {
          this.collapsed = !this.collapsed
        },
        showMenu (i, status) {
          this.$refs.menuCollapsed.getElementsByClassName('submenu-hook-' + i)[0].style.display = status ? 'block' : 'none'
        },
        aboutSys () {
          this.$message({
            showClose: true,
            message: this.sysName + this.sysVersion
          })
        },
        handleOpen () {
          // console.log('handleOpen');
        },
        handleClose () {
          // console.log('handleClose');
        },
        handleRemoveTab (tab) {
          this.removeActive(tab)
          if (this.activeMenu.length > 0) {
            this.activeName = this.activeMenu[this.activeMenu.length - 1].name
          }
        },
        handleSelect: function (index, indexPath) {
          // index: 选中菜单项的 indexPath: 选中菜单项的 index path
          const title = this.handleTab(index, indexPath, 0)
          const component = this.handleTab(index, indexPath, 1)
          const tab = {title: title, name: index, component: component}
          this.addActive(tab)
          this.activeName = index
        },
        // name = 0 获取title  name = 1 获取component
        handleTab: function (index, indexPath, name) {
          let authorization = JSON.parse(sessionStorage.getItem('authorization'))
          let result
          const childrenMenu = authorization[parseInt(indexPath[0])].children
          childrenMenu.forEach(
            function (item) {
              if (item.path === index) {
                if (name === 0) {
                  result = item.name
                } else {
                  result = item.component
                }
              }
            }
          )
          return result
        },
        handleCollSelect: function (index, indexPath) {
          // index: 选中菜单项的 indexPath: 选中菜单项的 index path
          const colltitle = this.handleCollTab(index, indexPath, 0)
          const component = this.handleCollTab(index, indexPath, 1)
          const colltab = {title: colltitle, name: indexPath, component: component}
          this.addActive(colltab)
          this.activeName = indexPath
        },
        // name = 0 获取title  name = 1 获取component
        handleCollTab: function (index, indexPath, name) {
          let authorization = JSON.parse(sessionStorage.getItem('authorization'))
          let result
          const childrenMenu = authorization[index].children
          childrenMenu.forEach(
            function (item) {
              if (item.path === indexPath) {
                if (name === 0) {
                  result = item.name
                } else {
                  result = item.component
                }
              }
            }
          )
          return result
        },

        ...mapActions([
          'addMenu',
          'loadRoutes',
          'addActive',
          'removeActive'
        ])
      },
      mounted () {
        loadSub().then(data => {
          let {user, authorization, unitSystemVersion, tabSysManageInfo, secMenuPurview} = data
          sessionStorage.setItem('user', JSON.stringify(user))
          sessionStorage.setItem('authorization', JSON.stringify(authorization))
          sessionStorage.setItem('secMenuPurview', JSON.stringify(secMenuPurview))
          this.userId = user.userId || ''
          this.username = user.username || ''
          this.sysVersion = unitSystemVersion.systemVersion || ''
          this.sysName = tabSysManageInfo.sysmanageName || ''

          if (!this.isLoadRoutes) {
            this.addMenu(authorization)
            this.$router.options.routes = this.menuitems
            this.$router.addRoutes(this.menuitems)
            this.loadRoutes()
          }
          this.$router.push({path: '/'})
        })
  }
    }
</script>

<style scoped lang="scss">
    @import '~scss_vars';

    .container {
        position: absolute;
        top: 0px;
        bottom: 0px;
        width: 100%;
        .header {
            height: 50px;
            line-height: 50px;
            background: $color-primary;
            color: #fff;
            .userinfo {
                text-align: right;
                padding-right: 35px;
                float: right;
                .userinfo-inner {
                    cursor: pointer;
                    color: #fff;
                    img {
                        width: 40px;
                        height: 40px;
                        border-radius: 20px;
                        margin: 10px 0px 10px 10px;
                        float: right;
                    }
                }
            }
            .logo {
                //width:230px;
                height: 50px;
                font-size: 20px;
                padding-left: 20px;
                padding-right: 20px;
                border-color: rgba(238, 241, 146, 0.3);
                border-right-width: 1px;
                border-right-style: solid;
                img {
                    width: 40px;
                    float: left;
                    margin: 10px 10px 10px 18px;
                }
                .txt {
                    color: #fff;
                }
            }
            .logo-width {
                width: 180px;
            }
            .logo-collapse-width {
                width: 50px
            }
            .tools {
                padding: 0px 23px;
                width: 14px;
                height: 50px;
                line-height: 50px;
                cursor: pointer;
            }
        }
        .main {
            display: flex;
            // background: #324057;
            position: absolute;
            top: 50px;
            bottom: 0px;
            overflow: hidden;
            aside {
                flex: 0 0 180px;
                width: 180px;
                // position: absolute;
                // top: 0px;
                // bottom: 0px;
                .el-menu {
                    height: 100%;
                }
                .collapsed {
                    width: 50px;
                    .item {
                        position: relative;
                    }
                    .submenu {
                        position: absolute;
                        top: 0px;
                        left: 50px;
                        z-index: 99999;
                        height: auto;
                        display: none;
                    }

                }
            }
            .menu-collapsed {
                flex: 0 0 50px;
                width: 50px;
            }
            .menu-expanded {
                flex: 0 0 180px;
                width: 180px;
            }
            .content-container {
                // background: #f1f2f7;
                flex: 1;
                // position: absolute;
                // right: 0px;
                // top: 0px;
                // bottom: 0px;
                // left: 230px;
                overflow-y: scroll;
                padding: 20px;
                .breadcrumb-container {
                    //margin-bottom: 15px;
                    .title {
                        width: 180px;
                        float: left;
                        color: #475669;
                    }
                    .breadcrumb-inner {
                        float: right;
                    }
                }
                .content-wrapper {
                    background-color: #fff;
                    box-sizing: border-box;
                }
            }
        }
    }
</style>