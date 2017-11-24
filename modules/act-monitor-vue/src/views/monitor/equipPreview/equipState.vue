<template>
    <section class="chart-container" >
        <el-row>
            <el-col :span="24" style="font-size: 50px">
               <center>全国监控统一平台</center>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="8">
                <div id="search_cu_div" style="width:100%; height:40px;" align="top">
                </div>
                <div id="cuState_pie" style="width:100%; height:260px;" align="bottom"></div>
            </el-col>
            <el-col :span="8">
                <div id="search_cu_div" style="width:100%; height:40px;" align="top">
                    <el-select v-model="cuStateVO"  filterable placeholder="请选择" >
                        <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
                        </el-option>
                    </el-select>
                </div>
                <div id="cuState_axis" style="width:100%; height:260px;" align="bottom"></div>
            </el-col>
            <el-col :span="8">
                <div id="cuState_axis" style="width:100%; height:300px;"></div>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="8">
                <div id="euState_pie" style="width:100%; height:300px;"></div>
            </el-col>
            <el-col :span="8">
                <div id="euState_axis" style="width:100%; height:300px;"></div>
            </el-col>
            <el-col :span="8">
                <div id="cuState_axis" style="width:100%; height:300px;"></div>
            </el-col>
        </el-row>
    </section>
</template>

<script>
    import echarts from 'echarts'
    import {pagingList, pagingGarbage, clearData, initialize} from './equipState.api'

export default {
      // components: {ElOption, ElSelect},
      //  components: {ElFormItem},
      data () {
        return {

          options: [
            {
              value: '0',
              label: 'CU到管局状态'
            },
            {
              value: '1',
              label: 'CU到EU状态'
            },
            {
              value: '2',
              label: 'CU服务器状态'
            }
          ]
          // idcid:'123'
        }
      },

      methods: {
        drawLianlu () {
          var cuState_pie = echarts.init(document.getElementById('cuState_pie'))
          var cuState_axis = echarts.init(document.getElementById('cuState_axis'))
          var euState_pie = echarts.init(document.getElementById('euState_pie'))
          var euState_axis = echarts.init(document.getElementById('euState_axis'))
          // CU设备饼图
          var option_cu_pie = {
            backgroundColor: '#666666',
            title: {
              x: 'center',
              text: 'CU设备状态统计'
            },
            tooltip: {
              trigger: 'item',
              formatter: '{a} <br/>{b}: {c} ({d}%)'
            },
            legend: {
              x: 'center',
              bottom: 40,
              // top:'50%',
              // orient: 'vertical',
              // x: 'left',
              // padding:50,

              data: ['异常', '正常']
            },
            series: [
              {
                name: 'CU设备状态统计',
                type: 'pie',
                radius: ['20%', '40%'],
                avoidLabelOverlap: false,
                label: {
                  normal: {
                    show: false,
                    position: 'center'
                  },
                  emphasis: {
                    show: true,
                    textStyle: {
                      fontSize: '30',
                      fontWeight: 'bold'
                    }
                  }
                },
                labelLine: {
                  normal: {
                    show: false
                  }
                },
                data: [
                  {
                    value: 335,
                    name: '正常',
                    itemStyle: {
                      normal: {
                        color: 'rgb(1,175,80)'
                      }
                    }
                  },
                  {
                    value: 310,
                    name: '异常',
                    itemStyle: {
                      normal: {
                        color: 'rgb(220,20,60)'
                      }
                    }
                  }
                ]
              }
            ]
          }
          // CU设备矩形图
          var option_cu_axis = {
            backgroundColor: '#666666',
            color: ['#FF0000'],
            tooltip: {
              trigger: 'axis',
              axisPointer: { // 坐标轴指示器，坐标轴触发有效
                type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
              }
            },
            grid: {
              left: '3%',
              right: '4%',
              bottom: '3%',
              containLabel: true
            },
            xAxis: [
              {
                type: 'category',
                data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
                axisTick: {
                  alignWithLabel: true
                }
              }
            ],
            yAxis: [
              {
                type: 'value'
              }
            ],
            series: [
              {
                name: 'CU设备状态统计',
                type: 'bar',
                barWidth: '50%',
                data: [10, 52, 200, 334, 390, 330, 220]
              }
            ]
          }
          // EU设备饼图
          var option_eu_pie = {
            backgroundColor: '#666666',
            title: {
              x: 'center',
              text: 'EU设备状态统计'
            },
            tooltip: {
              trigger: 'item',
              formatter: '{a} <br/>{b}: {c} ({d}%)'
            },
            legend: {
              /* orient: 'vertical', */
              x: 'center',
              bottom: 40,
              // top:'50%',
              data: ['异常', '正常']
            },
            series: [
              {
                name: 'EU设备状态统计',
                type: 'pie',
                radius: ['20%', '40%'],
                avoidLabelOverlap: false,
                label: {
                  normal: {
                    show: false,
                    position: 'center'
                  },
                  emphasis: {
                    show: true,
                    textStyle: {
                      fontSize: '30',
                      fontWeight: 'bold'
                    }
                  }
                },
                labelLine: {
                  normal: {
                    show: false
                  }
                },
                data: [
                  {
                    value: 335,
                    name: '正常',
                    itemStyle: {
                      normal: {
                        color: 'rgb(1,175,80)'
                      }
                    }
                  },
                  {
                    value: 1548,
                    name: '异常',
                    itemStyle: {
                      normal: {
                        color: 'rgb(220,20,60)'
                      }
                    }
                  }
                ]
              }
            ]
          }

          // EU设备矩形图
          var option_eu_axis = {
            backgroundColor: '#666666',
            color: ['#FF0000'],
            tooltip: {
              trigger: 'axis',
              axisPointer: { // 坐标轴指示器，坐标轴触发有效
                type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
              }
            },
            grid: {
              left: '3%',
              right: '4%',
              bottom: '3%',
              containLabel: true
            },
            xAxis: [
              {
                type: 'category',
                data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
                axisTick: {
                  alignWithLabel: true
                }
              }
            ],
            yAxis: [
              {
                type: 'value'
              }
            ],
            series: [
              {
                name: 'EU设备状态统计',
                type: 'bar',
                barWidth: '50%',
                data: [10, 52, 200, 334, 390, 330, 220]
              }
            ]
          }
          cuState_pie.setOption(option_cu_pie)
          cuState_axis.setOption(option_cu_axis)
          euState_pie.setOption(option_eu_pie)
          euState_axis.setOption(option_eu_axis)
        },
        // 分页查询
        pagingList: function () {
          this.filters.idcid = this.idcid
          pagingList(this.filters).then((res) => {
            if (res.error) {
              this.$message({
                message: res.error,
                type: 'error'
              })
            } else {
              this.$message({
                message: '操作成功',
                type: 'success'
              })
            }
          })
        }
      },
      mounted: function () {
        this.drawLianlu()
        this.pagingList()
      },
      updated: function () {
        this.drawLianlu()
      }
    }
</script>

<style scoped>
    .chart-container {
        width: 100%;
        float: left;
    }

    /*.chart-div {
        height: 400px;
        float: left;
    }*/

    .el-col {
        padding: 30px 20px;
    }
</style>
