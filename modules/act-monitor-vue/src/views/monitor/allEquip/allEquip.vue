<template>
	<section class="allEquip">
		<el-row :gutter="10" >
			<el-col :span="14">
				<div class="mapChart" :style="{'height': mapHeight + 'px'}">
					<IEcharts :loading="mapLoading" class="map" :option="mapOpt" :resizable="true" style="width: 100%; height: 100%;" @click="provChange"></IEcharts>
				</div>
			</el-col>
			<el-col :span="10">
				<el-form label-position="right" 
						:rules="rules"
						:model="searchFilters"
						:inline="true"
						ref="searchFilters"
					>
					<el-form-item label="IDC名称：" prop="idcName">
						<el-input v-model="searchFilters.idcName" placeholder="请输入IDC名称"></el-input>
					</el-form-item>
					<el-form-item>
						<el-button type="primary" @click.native="searchHandle('searchFilters')">
							<i class="fa fa-search fa-lg"></i>
							搜 索
						</el-button>
					</el-form-item>
				</el-form>
				<el-table :data="IDCList"
				    :height="total ? mapHeight - 90 : mapHeight - 58"
					v-loading="listLoading"
				    border style="width: 100%"
				>
				    <el-table-column prop="idcName" label="IDC名称"　width="160"></el-table-column>
				    <el-table-column prop="badDviceNum" sortable label="异常设备数量">
				        <template slot-scope="scope">
							<span :style="{'color': scope.row.badDviceNum == 0 ? '#4F81BD' : '#C0504D'}" style="font-weight: bold;">{{scope.row.badDviceNum}}</span>
				        </template>
				    </el-table-column>
				    <el-table-column prop="recordTime" width="160" sortable label="最近更新时间"></el-table-column>
				    <el-table-column label="操作" width="76">
				      <template slot-scope="scope">
				        <el-button @click.native.prevent="showDetail(scope.$index, IDCList)"
				          	type="primary" size="small">
				          	详情
				        </el-button>
				      </template>
				    </el-table-column>
			  	</el-table>
			  	<el-pagination
				    layout="prev, pager, next"
				    @current-change="handleCurrentChange"
				    
				    :page-size="filters.pageSize"
				    :total="total"
				    v-if="total"
				    class="fr"
				>
				<!-- :current-page.sync="filters.pageIndex" -->
			  	</el-pagination>
			</el-col>	
		</el-row>	
	</section>
</template>

<script>
import IEcharts from 'vue-echarts-v3'
import chinaMap from 'echarts/map/json/china.json'
import {CH} from '../../../js/contentHeight'
import { mapActions } from 'vuex'
import storage from '../../../js/storage'
import { deviceCount, pageList } from './allEquip.api'
import echarts from '../../../config/echartsOpt'
IEcharts.registerMap('china', chinaMap)

export default {
  name: 'allEquip',
  data () {
    return {
      mapOpt: null,
      mapHeight: '',
      IDCList: [],
      filters: {
        provId: '',
        idcName: '',
        pageIndex: 0,
        pageSize: 10
      },
      total: '',
      listLoading: false,
      mapLoading: false,
      searchFilters: {
        idcName: ''
      },
      rules: {
        'idcName': {required: false, message: '请输入正确的IDC名称', trigger: 'blur', type: 'string'}
      }
    }
  },
  methods: {
    // 设置地图与表格的高度
    setHeight () {
      this.mapHeight = CH()
    },
    getInstance () {
      this.mapOpt = echarts.map()
      this.getPageList()
      this.getDeviceCount()
    },
    // 获取地图所需的数据
    getDeviceCount () {
      this.mapLoading = true
      deviceCount().then(res => {
        if (res.error) {
          this.$message({
	                    showClose: true,
	                    message: res.error,
	                    type: 'error'
	                })
        } else {
          if (!this.filters.provName) {
            if (!res.success || !res.success.length) {
              this.mapLoading = false
              return
            }
            storage.set('allData', res.success)
            res.success.forEach((item, index) => {
              let data = {}
              data.name = item.provName
              // data.value = item.percent
              data.value = item.percent
              this.mapOpt.series[0].data.push(data)
            })
          }
        }
        this.mapLoading = false
      })
    },
    getPageList () {
      // console.log(this.filters)
      this.listLoading = true
      this.IDCList = []
      pageList(this.filters).then(res => {
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
          this.IDCList = res.success.rows
          this.total = res.success.total
        }
        this.listLoading = false
      })
    },
    // 点击不同省市的请求
    provChange (event, instance, echarts) {
      let data = storage.get('allData')
      this.filters.provId = 'null'
      this.filters.idcName = this.searchFilters.idcName
      data.forEach((item, index) => {
			 	if (item.provName === event.name) {
          this.filters.provId = item.provId ? item.provId : 'null'
        }
      })

      this.filters.pageIndex = 0
      this.getPageList()
    },
    // IDCnaNAME 搜索
    searchHandle (formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.filters.idcName = this.searchFilters.idcName
          this.filters.pageIndex = 0
          this.getPageList()
        }
      })
    },
    // currentPage 改变时会触发
    handleCurrentChange (val) {
      this.filters.pageIndex = val - 1
      this.getPageList()
    },
    // 显示详情，点击进入IDC/ISP设备状态信息页面
    showDetail (index, rows) {
        	const ROUTES = JSON.parse(sessionStorage.getItem('authorization'))
        	const PATH = '/deviceStatus'
        	// let idcID = rows[index].idcID
        	let uuid = rows[index].uuid
        	let idcName = rows[index].idcName
        	let tab = null
      /*
        	ROUTES.forEach((items, index) => {
        		items.children.forEach((item) => {
        			console.log()
        			if(item.path == PATH) {
        				tab = {
        					title: idcName + item.name,
        					name: idcName + item.name,
        					component: item.component
        				}
        			}
        		})
        	}) */
        	tab = {
        title: idcName + 'IDC设备状态统计',
        name: idcName + 'IDC设备状态统计',
        component: 'monitor/deviceStatus/deviceStatus'
      }
        	// console.log(collTab, uuid)
        	this.addActive(tab)
      // storage.set('idcID', idcID)
      // 传参到deviceStatus
      storage.set('uuid', uuid)
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
  computed: {

  },
  components: {
    IEcharts
  }
}
</script>

<style lang="scss">

	.allEquip {
		// margin: 0 20px;
		.mapChart {
			border: 1px solid #ccc;
		}

		.el-table {
			text-align: center;
			
			th {
				text-align: center;
			}
		}
	}

</style>

