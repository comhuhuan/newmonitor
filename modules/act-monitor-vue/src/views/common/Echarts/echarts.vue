<template>
    <section class="chart-container">
        <el-row>
            <el-col :span="24">
                <div id="chartColumn" style="height:700px;"></div>
            </el-col>
        </el-row>
    </section>
</template>

<script>
    import echarts from 'echarts'

    export default {
      data () {
        return {
          chartColumn: null
        }
      },

      methods: {
        drawColumnChart () {
          this.chartColumn = echarts.init(document.getElementById('chartColumn'))
          this.chartColumn.setOption({

            backgroundColor: new echarts.graphic.RadialGradient(0.3, 0.3, 0.8, [{
              offset: 0,
              color: '#f7f8fa'
            }, {
              offset: 1,
              color: '#cdd0d5'
            }]),
            title: {
              text: '基础数据结构',
              subtext: '关系图',
              top: 'top',
              left: 'center'
            },
            tooltip: {},
            legend: [{
              formatter: function (name) {
                return echarts.format.truncateText(name, 40, '14px Microsoft Yahei', '…')
              },
              tooltip: {
                show: true
              },
              selectedMode: 'false',
              bottom: 20,
              data: ['机房1', '机房2', '机房3']
            }],
            toolbox: {
              show: true,
              feature: {
                dataView: {show: true, readOnly: true},
                restore: {show: true},
                saveAsImage: {show: true}
              }
            },
            animationDuration: 3000,
            animationEasingUpdate: 'quinticInOut',
            series: [{
              name: '经营者',
              type: 'graph',
              layout: 'force',
              force: {
                repulsion: 300
              },
              data: [{
                'name': '经营者',
                'symbolSize': 70,
                'draggable': 'true',
                'value': 27

              }, {
                'name': '机房1',
                'value': 3,
                'symbolSize': 100,
                'category': '机房1',
                'draggable': 'true'
              }, {
                'name': 'Tik Tok -Kesha',
                'symbolSize': 40,
                'category': '机房1',
                'draggable': 'true',
                'value': 1
              }, {
                'name': 'Need You Now - Lady antebellum',
                'symbolSize': 30,
                'category': '机房1',
                'draggable': 'true',
                'value': 2
              }, {
                'name': 'Hey Soul Sister - Train',
                'symbolSize': 20,
                'category': '机房1',
                'draggable': 'true',
                'value': 3
              }, {
                'name': 'California Gurls - Katy Perry',
                'symbolSize': 10,
                'category': '机房1',
                'draggable': 'true',
                'value': 4
              }, {
                'name': 'OMG - Usher',
                'symbolSize': 8,
                'category': '机房1',
                'draggable': 'true',
                'value': 5
              }, {
                'name': '机房2',
                'value': 6,
                'symbolSize': 100,
                'category': '机房2',
                'draggable': 'true'
              }, {
                'name': 'Rolling In The Deep - Adele',
                'symbolSize': 40,
                'category': '机房2',
                'draggable': 'true',
                'value': 1
              }, {
                'name': 'Party Rock Anthem - LMFAO',
                'symbolSize': 30,
                'category': '机房2',
                'draggable': 'true',
                'value': 1
              }, {
                'name': 'Fire Work - Katy Perry',
                'symbolSize': 20,
                'category': '机房2',
                'draggable': 'true',
                'value': 1
              }, {
                'name': 'ET - Katy Perry',
                'symbolSize': 10,
                'category': '机房2',
                'draggable': 'true',
                'value': 1
              }, {
                'name': 'Give Me Everything - Pitbull',
                'symbolSize': 8,
                'category': '机房2',
                'draggable': 'true',
                'value': 1

              }, {
                'name': '机房3',
                'value': 5,
                'symbolSize': 100,
                'category': '机房3',
                'draggable': 'true'
              }, {
                'name': ' Somebody That I Used To Know - Eotye',
                'symbolSize': 40,
                'category': '机房3',
                'draggable': 'true',
                'value': 1
              }, {
                'name': 'Call Me Baby - Carly Rae Jepsen',
                'symbolSize': 30,
                'category': '机房3',
                'draggable': 'true',
                'value': 1
              }, {
                'name': 'We Are Young - Fun.',
                'symbolSize': 20,
                'category': '机房3',
                'draggable': 'true',
                'value': 1
              }, {
                'name': 'Payphone - Maroon 5',
                'symbolSize': 10,
                'category': '机房3',
                'draggable': 'true',
                'value': 1
              }, {
                'name': 'Lights - Ellie Goulding',
                'symbolSize': 8,
                'category': '机房3',
                'draggable': 'true',
                'value': 1
              }, {
                'name': '用户3',
                'value': 6,
                'symbolSize': 100,
                'category': '机房3',
                'draggable': 'true'
              }
              ],
              links: [{
                'source': '经营者',
                'target': '机房1'
              }, {
                'source': '机房1',
                'target': 'Tik Tok -Kesha'
              }, {
                'source': '机房1',
                'target': 'Need You Now - Lady antebellum'
              }, {
                'source': '机房1',
                'target': 'Hey Soul Sister - Train'
              }, {
                'source': '机房1',
                'target': 'California Gurls - Katy Perry'
              }, {
                'source': '机房1',
                'target': 'OMG - Usher'
              }, {
                'source': '经营者',
                'target': '机房2'
              }, {
                'source': '机房2',
                'target': 'Rolling In The Deep - Adele'
              }, {
                'source': '机房2',
                'target': 'Party Rock Anthem - LMFAO'
              }, {
                'source': '机房2',
                'target': 'Fire Work - Katy Perry'
              }, {
                'source': '机房2',
                'target': 'ET - Katy Perry'
              }, {
                'source': '机房2',
                'target': 'Give Me Everything - Pitbull'

              }, {
                'source': '经营者',
                'target': '机房3'
              }, {
                'source': '机房3',
                'target': ' Somebody That I Used To Know - Eotye'
              }, {
                'source': '机房3',
                'target': 'Call Me Baby - Carly Rae Jepsen'
              }, {
                'source': '机房3',
                'target': 'We Are Young - Fun.'
              }, {
                'source': '机房3',
                'target': 'Payphone - Maroon 5'
              }, {
                'source': '机房3',
                'target': 'Lights - Ellie Goulding'
              }, {
                'source': '机房3',
                'target': '用户3'
              }],
              categories: [{
                'name': '机房1'
              }, {
                'name': '机房2'
              }, {
                'name': '机房3'
              }],
              focusNodeAdjacency: true,
              roam: true,
              label: {
                normal: {

                  show: true,
                  position: 'top'

                }
              },
              lineStyle: {
                normal: {
                  color: 'source',
                  curveness: 0,
                  type: 'solid'
                }
              }
            }]

          })
        },
        drawCharts () {
          this.drawColumnChart()
        }
      },

      mounted: function () {
        this.drawCharts()
      },
      updated: function () {
        this.drawCharts()
      }
    }
</script>

<style scoped>
    .chart-container {
        width: 100%;
        float: left;
    }

    /*.chart div {
        height: 400px;
        float: left;
    }*/

    .el-col {
        padding: 30px 20px;
    }
</style>
