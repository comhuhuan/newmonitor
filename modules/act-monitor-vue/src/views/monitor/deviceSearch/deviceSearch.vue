<template>
	<section class="deviceSearch" :style="{'height': height + 'px'}">
		<el-row :gutter="20">
			<el-col :span="7">
			<!-- :inline="true"  -->
				<div class="search">
					<el-form label-position="right" 
						label-width="100px" 
						:rules="rules"
						:model="searchFilters"
						ref="searchFilters"
					>
						<el-form-item label="省份：" prop="provId">
							<el-select v-model="searchFilters.provId" filterable placeholder="请选择省份" @change="proChange">
							    <el-option v-for="(item, index) in provSelect"
							      :key="index" :label="item.label" :value="item.value">
							    </el-option>
							</el-select>
						</el-form-item>
						<el-form-item label="运营商：" prop="uuid">
							<el-select v-model="searchFilters.uuid" filterable placeholder="请选择运营商"
								@change="idcChange" :disabled="isProChecked">
							    <el-option v-for="(item, index) in idcSelect" :key="index"
							      :label="item.label" :value="item.value">
							    </el-option>
							</el-select>
						</el-form-item>
						<el-form-item label="机房：" prop="houseId">
							<el-select v-model="searchFilters.houseId" filterable
								placeholder="请选择机房"
								:disabled="isIdcChecked"
							>
							    <el-option v-for="(item, index) in houseSelect"
							      :key="index"
							      :label="item.label"
							      :value="item.value">
							    </el-option>
							</el-select>
						</el-form-item>
						<el-form-item label="ID：" prop="deviceId">
							<el-input v-model="searchFilters.deviceId"  placeholder="请输入设备ID"></el-input>
						</el-form-item>
						<el-form-item label="设备类型：" prop="deviceType" class="radio">
							<el-radio-group v-model="searchFilters.deviceType">
							    <el-radio :label="2">EU</el-radio>
							    <el-radio :label="1">CU</el-radio>
							</el-radio-group>
						</el-form-item>
						<el-form-item label="是否异常：" prop="isWrong" class="radio">
							<el-radio-group v-model="searchFilters.isWrong">
							    <el-radio :label="''">全部</el-radio>
							    <el-radio :label="0">正常</el-radio>
							    <el-radio :label="1">异常</el-radio>
							</el-radio-group>
						</el-form-item>
						<el-form-item label="状态类型：" prop="stateType">
							<el-select v-model="searchFilters.stateType" filterable placeholder="请选择设备状态">
							    <el-option
							      v-for="(item, index) in stateSelect"
							      :key="index"
							      :label="item.label"
							      :value="item.value">
							    </el-option>
							</el-select>
						</el-form-item>
						<el-form-item>
							<el-button type="primary" @click.native="searchHandle('searchFilters')">
								<i class="fa fa-search fa-lg"></i>
								搜 索
							</el-button>
							<el-button @click.native="resetSearch('searchFilters')">
								<i class="fa fa-refresh fa-lg"></i>
								重 置
							</el-button>
						</el-form-item>
					</el-form>
				</div>
			</el-col>
			<el-col :span="17">
				<el-table :data="pageList" :height="height - 54"
					v-loading="listLoading" style="width: 100%" border>
				    <el-table-column prop="provName" label="省份"></el-table-column>
				    <el-table-column prop="idcName" label="运营商"></el-table-column>
				    <el-table-column prop="houseName" label="机房"></el-table-column>
				    <el-table-column prop="deviceId" label="设备"></el-table-column>
				    <el-table-column prop="state" label="状态">
				      	<template slot-scope="scope">
				      		<el-tag :type="scope.row.state === '正常' ? 'primary' : 'danger'">{{scope.row.state}}</el-tag>
				      	</template>
				    </el-table-column>
				    <el-table-column prop="timeStamp" width="150" sortable label="状态更新时间"></el-table-column>
				    <el-table-column label="操作" width="150">
				        <template slot-scope="scope">
					        <el-button @click.native.prevent="showDetail(scope.$index, pageList)"
					          type="primary" size="small">
					          	详情
					        </el-button>
					        <el-button @click.native.prevent="showHistory(scope.$index, pageList)"
					          	type="primary" size="small">
					          	历史
					        </el-button>
				        </template>
				    </el-table-column>
			    </el-table>
			    <div class="wrap">
					<el-dropdown @command="exportByJson">
						<el-button type="primary" size="small"><i
								class="fa fa-cloud-download fa-lg"></i> 前端导出<i
								class="el-icon-caret-bottom el-icon--right"></i>
					  	</el-button>
					  	<el-dropdown-menu slot="dropdown">
							<!--<el-dropdown-item command="0" :disabled="true">当页数据</el-dropdown-item>-->
							<el-dropdown-item command="1" :disabled="exportDis">查询数据</el-dropdown-item>
							<el-dropdown-item command="-1" :disabled="exportDis">全部数据</el-dropdown-item>
					 	</el-dropdown-menu>
					</el-dropdown>
				    <el-pagination
					    layout="total, sizes, prev, pager, next, jumper"
					    @size-change="handleSizeChange"
					    @current-change="handleCurrentChange"
					   
					    :page-sizes="[20, 30, 50]"
					    :page-size="params.pageSize"
					    :total="total"
					    v-if="total"
					    class="fr"
					>
					<!--  :current-page.sync="params.pageIndex" -->
					</el-pagination>
			    </div>
		    </el-col>
		</el-row>
	</section>
</template>

<script>
import {provList, getIdcByPro, getHouseByIdc, pagingList, exportByJson} from './deviceSearch.api'
import {CH} from '../../../js/contentHeight'
import storage from '../../../js/storage'
import baseUtil from '../../../js/baseUtil'
import config from '../../../config/deviceQuery.config'
import {mapActions} from 'vuex'
import dateUtil from '../../../js/dateUtil'
import {exportDataExcel, exportJsonExcel} from '../../../js/excelUtil'

export default {
  name: 'deviceSearch',
  data () {
    return {
      height: 0,
      searchFilters: {
        provId: '',
        uuid: '',
        houseId: '',
        deviceId: '',
        deviceType: 2,
        exportType: '',
        isWrong: '',
        stateType: 'euDevice'

      },
      params: {
        pageIndex: 0,
        pageSize: 20
      },
      rules: {
        'provId': {required: true, message: '请选择省份', trigger: 'change', type: 'number'},
        'uuid': {required: true, message: '请选择运营商', trigger: 'change', type: 'string'},
        // 	"houseId": {required: true, message: '请选择机房', trigger: 'change'},
        'deviceId': {type: 'string', required: false, message: '请输入正确的设备ID', trigger: 'blur'}
        // "deviceType": {required: true, message: '请选择设备类型', trigger: 'change'},
        // "isWrong": {required: true, message: '请选择是否异常', trigger: 'change'},
        // "stateType": {required: true, message: '请选择设备状态', trigger: 'change'}
      },
      provSelect: [],
      idcSelect: [],
      idcParams: null,
      houseSelect: [],
      houseParams: null,
      pageList: [],
      exportDis: false,
      isProChecked: true,
      isIdcChecked: true,
      listLoading: false,
      total: 0
    }
  },
  computed: {
    stateSelect () {
      return this.searchFilters.deviceType === 1
        ? config.CU().select
        : config.EU().select
    }
  },
  watch: {
    'searchFilters.deviceType': 'getStateDef'
  },
  methods: {
    exportByServer (exportType) {
      this.filters.exportType = exportType
      this.exportDis = true
      exportDataExcel('/act-monitor-web/monitor/device/exportByJson.do', this.filters)
      // 如果不还原导出类型 会造成导出报错
      this.filters.exportType = ''
      this.exportDis = false
    },

    exportByJson (command) {
	    		this.isExport = true
      this.searchFilters.exportType = command
      this.getPageList()
      this.exportDis = true
      exportByJson(this.searchFilters).then((res) => {
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
        if (j === 'state') {
          if (v[j] === 0) {
            return '正常'
          } else {
            return '异常'
          }
        } else {
          return v[j]
        }
      }))
    },

    setHeight () {
      this.height = CH()
    },
    getStateDef () {
      this.searchFilters.stateType = this.searchFilters.deviceType === 1
        ? 'cuDevice'
        : 'euDevice'
    },
    getInstance () {
      this.getProvList()
    },
    getProvList () {
      const currentUser = JSON.parse(sessionStorage.getItem('user'))
      let uuidValue = ''
      if (currentUser.uuid) {
        let reg = /^\s+|\s+$/g
        uuidValue = currentUser.uuid.replace(reg, '')
      }

      let params = {
        uuid: uuidValue
      }
      provList(params).then(res => {
        if (res.error) {
          this.$message({
	                    showClose: true,
	                    message: res.error,
	                    type: 'error'
	                })
        } else {
          if (!res.success.length) {
            this.$message({
		                    showClose: true,
		                    message: '暂无省市',
		                    type: 'error'
		                })
          }
          res.success.forEach(item => {
            let data = {}
            data.label = item.provName
            data.value = item.provId
            if (currentUser.uuid) {
						    let reg = /^\s+|\s+$/g
              let uuidValue = currentUser.uuid.replace(reg, '')
              if (uuidValue != '') {
                this.searchFilters.provId = item.provId
              }
            }
            this.provSelect.push(data)
          })
        }
      })
    },
    getPageList () {
      this.listLoading = true
      this.pageList = []
      // console.log(this.params, this.searchFilters)
      pagingList(this.params).then(res => {
        if (res.error) {
          this.$message({
	                    showClose: true,
	                    message: res.error,
	                    type: 'error'
	                })
        } else {
          if (!res.success.rows || !res.success.rows.length) {
            this.listLoading = false
            return
          }
          res.success.rows.forEach((item, index) => {
            item.state = item.state === 0 ? '正常' : '异常'
          })
          /* =========================== */
          /* // 使用时应注释
					res.success.rows.forEach(item => {
						item.stateType = this.searchFilters.stateType
					}) */
          /* =========================== */
          this.pageList = res.success.rows

          this.total = res.success.total
        }
        this.listLoading = false
      })
    },
    proChange (val) {
      if (val === '') {
        this.isProChecked = true
        return
      }
      const currentUser = JSON.parse(sessionStorage.getItem('user'))
      let uuidValue = ''
      if (currentUser.uuid) {
        let reg = /^\s+|\s+$/g
        uuidValue = currentUser.uuid.replace(reg, '')
      }
      this.idcParams = {
        provId: val,
        uuid: uuidValue
      }
      getIdcByPro(this.idcParams).then(res => {
        this.idcSelect = []
        this.isProChecked = true
        if (res.error) {
          this.$message({
	                    showClose: true,
	                    message: res.error,
	                   type: 'error'
	                })
        } else {
          if (!res.success || !res.success.length) {
            this.$message({
		                    showClose: true,
		                    message: '暂无运营商',
		                   type: 'error'
		                })
            this.houseSelect = []
            this.idcSelect = []
            this.searchFilters.uuid = ''
            this.searchFilters.houseId = ''
		                return
          }

          res.success.forEach(item => {
            let data = {}
            data.label = item.idcName
            data.value = item.uuid
            this.idcSelect.push(data)
          })
          // this.defIdcName = this.idcSelect[0].value
          this.isProChecked = false
          this.searchFilters.uuid = uuidValue
        }
      })
    },
    idcChange (val) {
      if (val === '') {
        this.isIdcChecked = true
        return
      }
      let params = {
        uuid: val
      }
      this.houseParams = Object.assign(this.idcParams, params)
      getHouseByIdc(this.houseParams).then(res => {
        this.houseSelect = []
        this.isIdcChecked = true
        if (res.error) {
          this.$message({
	                    showClose: true,
	                    message: res.error,
	                   type: 'error'
	                })
        } else {
          if (!res.success.length) {
            this.$message({
		                    showClose: true,
		                    message: '暂无机房',
		                    type: 'error'
		                })
            this.searchFilters.houseId = ''
		                return
          }
          res.success.forEach(item => {
            let data = {}
            data.label = item.houseName
            data.value = item.houseId
            this.houseSelect.push(data)
          })
          // this.defHouseName = this.houseSelect[0].value
          this.isIdcChecked = false
        }
      })
    },
    searchHandle (formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          // Object.assign(this.params, baseUtil(this.searchFilters))

          Object.assign(this.params, this.searchFilters)
          this.getPageList()
        }
      })
    },
    resetSearch (formName) {
      this.$refs[formName].resetFields()
    },
    exportList (command) {
      // command all 全部 & cur 当前页
    },
    showDetail (index, rows) {
      let data = rows[index]
      let tab = {
        title: data.deviceId + '设备状态统计',
        name: data.deviceId + data.stateType + '设备状态统计',
        component: 'monitor/deviceStatistics/deviceStatistics'
      }

      let queryType = ''
      //			console.log(data)
      if (data.stateType) {
        //				console.log(data.stateType)
        queryType = data.stateType.includes('Device') ? 'cpu' : ''
      }
      let params = {
        deviceId: data.deviceId,
        elementType: data.stateType,
        houseID: data.houseId,
        queryType: queryType,
        uuid: data.uuid
      }
      // console.log(data.stateType, queryType)
      let types = {
        deviceType: data.deviceType === 1 ? 'CU' : 'EU',
        elementType: data.stateType,
        queryType: queryType,
        houseName: data.houseName,
        cpuStat: data.cpuStat,
        memStat: data.memStat,
        diskStat: data.diskStat,
        processStat: data.processStat

      }
      // console.log(types, params)
      // 参数传给deviceStatistics
      storage.set('statisticsTypes', types)
      storage.set(data.stateType + 'statistics', params)
      this.addActive(tab)
    },
    showHistory (index, rows) {
      let data = rows[index]
      let tab = {
        title: data.deviceId + '设备历史状态',
        name: data.deviceId + data.stateType + '设备历史状态',
        component: 'monitor/deviceHistory/deviceHistory',
        isWrong: this.searchFilters.isWrong
      }

      let params = {
        deviceId: data.deviceId,
        deviceType: data.deviceType,
        stateType: data.stateType,
        houseID: data.houseId,
        uuid: data.uuid
        // isWrong:this.searchFilters.isWrong
      }

      storage.set('isWrong', this.searchFilters.isWrong)
      storage.set('historyParams', params)
      this.addActive(tab)
    },
    handleSizeChange (val) {
      this.params.pageSize = val
      this.handleCurrentChange(1)
    },
    handleCurrentChange (val) {
      this.params.pageIndex = val - 1
      this.getPageList()
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

<style lang="scss">
	
.deviceSearch {
	background: #f7f7f7;
	padding: 20px 10px;

	.search {
		
		.el-select {
			width: 100%;
		}
		.el-radio{
			height: 40px;
		    line-height: 34px;
		    margin-right: 10px;

		    & + .el-radio {
			    margin-left: 0;
			}
		}
		.radio {
			margin-bottom: 8px;
		}
		.el-form-item__label  {
			font-size: 14px;
		}
	}
	.wrap { 
		margin-top: 20px;
	}

	.el-table {
		text-align: center;

		th {
			text-align: center;
		}
	}
}

</style>
