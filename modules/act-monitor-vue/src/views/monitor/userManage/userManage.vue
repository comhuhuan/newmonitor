<template>
	<section class="userManage">
		<el-row>
			<!-- <el-col :span="8">  -->
				<div class="title">用户管理</div>
				<el-form  :inline="true" :rules="rules" :model="userFilters"  ref="userFilters">	
					<el-form-item label="账号：" prop="userId">
						<el-input v-model="userFilters.userId" placeholder="请输入账号"></el-input>
					</el-form-item>
					<el-button type="primary" @click.native="searchHandle('userFilters')">
						<i class="fa fa-search fa-lg"></i>
						查 询
					</el-button>
					<el-button @click.native="resetSearch('userFilters')">
						<i class="fa fa-refresh fa-lg"></i>
						重 置
					</el-button>
					<el-button @click.native="isAddUser = true" type="success" class="fr" icon="plus">
						新 增
					</el-button>
				</el-form>
				<el-table :data="userList" :height="tableHeight"
					v-loading="listLoading" style="width: 100%" border>
				    <el-table-column prop="userId" label="账号"></el-table-column>
				    <el-table-column prop="username" label="用户名"></el-table-column>
				    <el-table-column prop="tel" sortable label="手机号"></el-table-column>
				    <el-table-column prop="email" sortable label="邮箱"></el-table-column>
				    <el-table-column prop="limit" sortable label="权限范围">
						<template slot-scope="scope">
							{{scope.row.limit==null? '全国':scope.row.limit}}
						</template>
					</el-table-column>
				    <el-table-column prop="createTime" sortable label="创建时间"> </el-table-column>
				    <el-table-column label="操作">
				      	<template slot-scope="scope">
					        <el-button @click.native.prevent="modifyUserDialog(scope.$index, userList)"
					          	type="primary" size="small" icon="edit">
					          	修改
					        </el-button>
					        <el-button @click.native.prevent="deleteUser(scope.$index, userList)"
					          	type="danger"
					          	icon="delete"
					          	size="small">
					          	 <!-- :disabled="scope.username=='admin'" -->
					         	删除
					        </el-button>
				      	</template>
				    </el-table-column>
			    </el-table>
			    <div class="wrap">
				    <!--<el-dropdown @command="exportList">-->
					  	<!--<el-button type="primary" icon="">-->
					    	<!--<i class="fa fa-cloud-download  fa-lg"></i>&nbsp;&nbsp;-->
					    	<!--导出-->
					    	<!--<i class="el-icon-caret-bottom el-icon&#45;&#45;right"></i>-->
					  	<!--</el-button>-->
					  	<!--<el-dropdown-menu slot="dropdown">-->
						    <!--<el-dropdown-item command="cur">导出当前页</el-dropdown-item>-->
						    <!--<el-dropdown-item command="all">导出全部</el-dropdown-item>-->
					 	<!--</el-dropdown-menu>-->
					<!--</el-dropdown>-->
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
					<!-- :current-page.sync="params.pageIndex" -->
					</el-pagination>
			    </div>
			<!-- </el-col> -->
		</el-row>
		<!-- 新增用户弹框 -->
		<!-- :visible.sync="isAddUser" -->
		<el-dialog title="新增用户" :visible.sync="isAddUser" v-if="isAddUser">
			<el-form :model="increasUser"
				:rules="increasRules"
				label-width="80px"
				ref="increasUser"
				:inline="true"
				label-position="right"
			>
				<el-form-item label="账号：" prop="userId">
				    <el-input v-model="increasUser.userId" placeholder="请输入账号"></el-input>
				</el-form-item>
				<el-form-item label="用户名：" prop="username">
				    <el-input v-model="increasUser.username" placeholder="请输入用户名"></el-input>
				</el-form-item>
				<el-form-item label="密码：" prop="password">
				    <el-input type="password" v-model="increasUser.password" placeholder="请输入密码"></el-input>
				</el-form-item>
				<el-form-item label="权限：" prop="uuid">
				    <el-select v-model="increasUser.uuid" filterable placeholder="请选择权限">
					    <el-option v-for="(item, index) in limitList" :key="index" :label="item.idcname" :value="item.uuid" ></el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="手机号：" prop="tel">
				    <el-input v-model="increasUser.tel" placeholder="请输入手机号"></el-input>
				</el-form-item>
				<el-form-item label="邮箱：" prop="email">
				    <el-input v-model="increasUser.email" placeholder="请输入邮箱"></el-input>
				</el-form-item>
				<!-- <el-form-item label="地址" prop="addr">
				    <el-input v-model="increasUser.addr" placeholder="请输入地址"></el-input>
				</el-form-item>
				<el-form-item label="备注" prop="remark">
				    <el-input v-model="increasUser.remark" placeholder="请输入备注"></el-input>
				</el-form-item> -->
			</el-form>
			<div class="text-center">
				<el-button type="primary" @click.native="addUserHandle('increasUser')">新 增</el-button>
				<el-button @click.native="closeAddDialog">取 消</el-button>
			</div>
		</el-dialog>


		<!-- 修改用户弹框 -->
		<el-dialog title="修改用户" :visible.sync="isModifyUser">
			<el-form :model="modifyUser"
				:rules="modifyRules"
				label-width="80px"
				ref="modifyUser"
				:inline="true"
				label-position="right"
			>
				<el-form-item label="账号：" prop="userId">
				    <el-input v-model="modifyUser.userId" placeholder="请输入账号" disabled></el-input>
				</el-form-item>
				<el-form-item label="用户名：" prop="username">
				    <el-input v-model="modifyUser.username" placeholder="请输入用户名"></el-input>
				</el-form-item>
				<el-form-item label="权限：" prop="uuid">
				    <el-select v-model="modifyUser.uuid" filterable placeholder="请选择权限">
					    <el-option v-for="(item, index) in limitList" :key="index" :label="item.idcname" :value="item.uuid" ></el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="手机号：" prop="tel">
				    <el-input v-model="modifyUser.tel" placeholder="请输入手机号"></el-input>
				</el-form-item>
				<el-form-item label="邮箱：" prop="email">
				    <el-input v-model="modifyUser.email" placeholder="请输入邮箱"></el-input>
				</el-form-item>
			</el-form>
			<div class="text-center">
				<el-button type="primary" @click.native="modifyUserHandle('modifyUser')">修 改</el-button>
				<el-button @click.native="isModifyUser = false">取 消</el-button>
			</div>
		</el-dialog>

	</section>
</template>

<script>
import {pagingList, getIdcList, remove, addUser} from './userManage.api'
import {CH} from '../../../js/contentHeight'
import {mapActions} from 'vuex'

export default {
  name: 'userManage',
  data () {
    return {
      tableHeight: '',
      userFilters: {
        userId: ''
      },
      rules: {
        userId: {require: false, message: '请输入账号', trigger: 'blur', type: 'string'}
      },
      userList: [],
      params: {
        pageIndex: 0,
        pageSize: 20
      },
      addUserParams: {
        email:	'',
        tel: '',
        password: '',
        userId:	'',
        username: '',
        uuid: ''
      },
      increasUser: {
        userId: '',
        username: '',
        password: '',
        uuid: '',
        tel: '',
        email: '',
        commonFlag: 'add'
      },
      modifyUser: {
        userId: '',
        username: '',
        uuid: '',
        tel: '',
        email: ''
      },
      limitList: [
        {'idcname': '全国', 'uuid': ''}
      ],
      increasRules: {
        userId: {required: true, message: '请输入正确账号', trigger: 'blur', type: 'string'},
        username: [
          {required: true, message: '请输入正确用户名', trigger: 'blur'},
          {min: 4, max: 40, message: '用户名在4到40个字符之间', trigger: 'blur'}
        ],
        password: [
          { required: true, message: '请输入正确密码', trigger: 'blur' },
          { message: '密码长度至少为6位', min: 6, trigger: 'blur' }
        ],
        // uuid: {required: true, message: '请选择权限', trigger: 'change', type: 'string'},
        tel: [
          {required: true, message: '请输入正确手机号', trigger: 'blur'},
          {pattern: /^1[3|4|5|7|8][0-9]\d{8}$/, message: '请输入正确的手机号', trigger: 'blur'}
        ],
        email: [
          {required: true, message: '请输入正确邮箱地址', trigger: 'blur'},
          {type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur'}
        ],
        addr: [],
        remark: []
      },
      modifyRules: {
        username: [
          {required: false, message: '请输入正确用户名', trigger: 'blur'},
          {min: 4, max: 40, message: '用户名在4到40个字符之间', trigger: 'blur'}
        ],
        tel: [
          {required: false, message: '请输入正确手机号', trigger: 'blur'},
          {pattern: /^1[3|4|5|7|8][0-9]\d{8}$/, message: '请输入正确的手机号', trigger: 'blur'}
        ],
        email: [
          {required: false, message: '请输入正确邮箱地址', trigger: 'blur'},
          {type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur'}
        ]
      },
      listLoading: false,
      isAddUser: false,
      isModifyUser: false,
      total: 0
    }
  },
  methods: {
    setHeight () {
      const GAP = 160 // 上下预留的距离
      this.tableHeight = CH() - GAP
    },
    getInstance () {
      var params = {
        userId: sessionStorage.getItem('user').userId
      }
      Object.assign(this.params, params)
      this.getUserList()
      this.getLimitList()
    },
    getUserList () {
      this.listLoading = true
      this.userList = []
      pagingList(this.params).then(res => {
        if (res.error) {
          this.$message({
            showClose: true,
	            		message: res.error,
	            		type: 'error'
          })
        } else {
          if (!res.success.rows) {
            this.listLoading = false
            return
          }
          this.userList = res.success.rows
          this.total = res.success.total
        }
        this.listLoading = false
      })
    },
    getLimitList () {
      getIdcList().then(res => {
        if (res.error) {
          this.$message({
            showClose: true,
	            		message: res.error,
	            		type: 'error'
          })
        } else {
          if (!res.success) {
            return
          }
          this.limitList = this.limitList.concat(res.success)
          //					console.log(this.limitList, res.success)
        }
      })
    },
    searchHandle (formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          // Object.assign(this.params, baseUtil(this.userFilters))
          Object.assign(this.params, this.userFilters)
          // console.log(this.params, this.userFilters)
          this.getUserList()
          // this.resetSearch(formName)
        }
      })
    },
    addUser () {
      this.listLoading = true

      addUser(this.addUserParams).then(res => {
        if (res.error) {
          this.$message({
            showClose: true,
	            		message: res.error,
	            		type: 'error'
          })
        } else {
          this.getUserList()
          this.$message({
            showClose: true,
	            		message: res.success,
	            		type: 'success'
          })
        }
        this.listLoading = false
      })
    },
    resetSearch (formName) {
      this.$refs[formName].resetFields()
    },
    exportList (command) {

    },
    modifyUserDialog (index, rows) {
      this.modifyUser = null
      if (this.$refs['modifyUser']) {
        this.$refs['modifyUser'].resetFields()
      }
      this.modifyUser = {
        email:	rows[index].email,
        tel: rows[index].tel,
        userId:	rows[index].userId,
        username: rows[index].username,
        uuid: rows[index].uuid
      }
      this.isModifyUser = true
    },
    modifyUserHandle (formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.addUserParams = this.modifyUser
          Object.assign(this.addUserParams)
          this.addUser()
          this.isModifyUser = false
        }
      })
    },
    deleteUser (index, rows) {
      this.$confirm('确认删除该用户吗?', '提示', {
        type: 'warning'
      }).then(() => {
            	let params = {
            		userId: rows[index].userId
            	}
            	remove(params).then(res => {
            		if (res.error) {
            this.$message({
              showClose: true,
		            		message: res.error,
		            		type: 'error'
            })
          } else {
            this.getUserList()
		            	this.$message({
		            		showClose: true,
		            		message: res.success,
		            		type: 'success'
		            	})
          }
            	})
      }).catch(() => {
            	// this.$message({
            	// 	showClose: true,
            	// 	message: '删除失败',
            	// 	type: 'error'
            	// })
      })
    },
    addUserHandle (formName) {
      this.addUserLoding = true
      this.$refs[formName].validate(valid => {
        if (valid) {
          // Object.assign(this.addUserParams, baseUtil(this.increasUser))
          Object.assign(this.addUserParams, this.increasUser)
          this.addUser()

          this.closeAddDialog()
        }
      })
    },
    closeAddDialog () {
      this.isAddUser = false
      this.increasUser = {
        userId: '',
        username: '',
        password: '',
        uuid: '',
        tel: '',
        email: ''
      }
    },
    /* closeModifyDialog() {
			this.isModifyUser = false
			this.modifyUser = {
				userId: '',
				username: '',
				uuid: '',
				tel: '',
				email: ''
			}
		}, */
    handleSizeChange (val) {
      this.params.pageSize = val
      this.handleCurrentChange(1)
    },
    handleCurrentChange (val) {
      this.params.pageIndex = val - 1
      this.getUserList()
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
	
.userManage {
	.title {
		height: 36px;
		color: #333;
		font-size: 16px;
		font-weight: bold;
		margin-bottom: 5px;
		line-height: 36px;

	}
	.el-form {
		margin: 0 20px;
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
