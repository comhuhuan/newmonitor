<template>
	<section class="alarmManage">
		<el-row>

			<el-radio-group v-model="radio3"  size="large"   @change="getalarmList(radio3)" >
				<el-radio-button label="1"  >信安功能告警</el-radio-button>
				<el-radio-button label="2"  >活跃资源告警</el-radio-button>
				<el-radio-button label="3" >访问日志告警</el-radio-button>
				<el-radio-button label="4">服务器告警</el-radio-button>
				<el-radio-button label="5">通讯状态告警</el-radio-button>
			</el-radio-group>



			<!-- <el-col :span="8">  -->
			<!--<el-button type="gray" autofocus="true" id="1" @click="getalarmList(1)"> 信安功能告警 </el-button>-->
			<!--<el-button type="gray" id="2" @click="getalarmList(2)"> 活跃资源告警 </el-button>-->
			<!--<el-button type="gray" id="3" @click="getalarmList(3)"> 访问日志告警 </el-button>-->
			<!--<el-button type="gray" id="4" @click="getalarmList(4)"> 服务器告警 </el-button>-->
			<!--<el-button type="gray" id="5" @click="getalarmList(5)"> 通讯状态告警 </el-button>-->
			<template>
				<!-- `checked` 为 true 或 false -->
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<el-checkbox v-model="checkValid" @change="getalarmList(false)">仅显示异常</el-checkbox>
			</template>
			<br/><br/>

			<!--
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
				-->
				<el-table :data="alarmList" :height="tableHeight" empty-text="功能正常"
					v-loading="listLoading" style="width: 100%" border>
					<el-table-column prop="type" label="设备类型"></el-table-column>
					<el-table-column prop="host" label="设备IP"></el-table-column>
					<el-table-column prop="childclass" label="告警类型"></el-table-column>
				    <el-table-column prop="valid" label="告警状态">
						<template slot-scope="scope">
							<el-tag :type="scope.row.valid === '正常' ? 'primary' : 'danger'">
								{{scope.row.valid}}
			      			</el-tag>
						</template>
					</el-table-column>
				    <el-table-column prop="occurtime"  label="告警时间"></el-table-column>
				    <el-table-column prop="value" label="告警详情"></el-table-column>
				   <!--
				    <el-table-column label="操作">
				      	<template scope="scope">
					        <el-button @click.native.prevent="modifyUserDialog(scope.$index, userList)"
					          	type="primary" size="small" icon="edit">
					          	修改
					        </el-button>
					        <el-button @click.native.prevent="deleteUser(scope.$index, userList)"
					          	type="danger"
					          	icon="delete"
					          	size="small">
					          	 <!-- :disabled="scope.username=='admin'" -->
					<!--
					         	删除
					        </el-button>
				      	</template>
				    </el-table-column>
					-->
			    </el-table>

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
		</el-row>
	</section>
</template>

<script>
import {pagingList} from './alarmManage.api'
import {CH} from '../../../js/contentHeight'

export default {
  name: 'alarmManage',
  data () {
    return {
      radio3: '1',
      checkValid: false,
      tableHeight: '',
      alarmList: [],
      params: {
        pageIndex: 0,
        pageSize: 10,
        parentclass: 1,
        valid: 0
      },
      queryParams: {

      },
      listLoading: false,
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
      this.getalarmList(false)
    },
    getalarmList (type) {
      this.listLoading = true
      if (this.checkValid) {
        this.params.valid = 1
      } else {
        this.params.valid = 0
      }
      this.alarmList = []
      if (type) {
			    this.params.parentclass = type
      }
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
          res.success.rows.forEach((item, index) => {
            item.valid = item.valid === 0 ? '正常' : '异常'
          })
          this.alarmList = res.success.rows
          this.total = res.success.total
        }
        this.listLoading = false
      })
    },
    handleSizeChange (val) {
      this.params.pageSize = val
      this.handleCurrentChange(1)
    },
    handleCurrentChange (val) {
      this.params.pageIndex = val - 1
      this.getalarmList(false)
    }
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

.alarmManage {
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
	.el-select {
		width: 168px;
	}
	.el-table {
		text-align: center;

		th {
			text-align: center;
		}
	}
}

</style>
