import echarts from 'echarts'

export default {
  // 地图配置
  map () {
    return {
      tooltip: {
        trigger: 'item',
        formatter: '{b}: {c}'
      },
      visualMap: {
        min: 0,
        max: 40,
        left: 'left',
        top: 'bottom',
        text: ['High', 'Low'],
        calculable: true
      },

      toolbox: {
        show: true,
        orient: 'vertical',
        right: 20,
        top: 'center',
        feature: {
          restore: {show: true},
          saveAsImage: {show: true}
        }
      },
      series: [
        {
          type: 'map',
          map: 'china',
          roam: true,
          label: {
            normal: {
              show: true
            },
            emphasis: {
              show: true
            }
          },
          data: [] // 需要添加数据
        }
      ]
    }
  },
  // 柱状图配置
  bar () {
    return {
      // color: ['#3398DB'],
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
		    toolbox: {
        show: true,
        orient: 'vertical',
        right: 20,
        top: 'center',
        feature: {
          restore: {show: true},
          saveAsImage: {show: true}
        }
      },
		    xAxis: [
		        {

		            type: 'category',
		            data: [], // 需要添加x轴的数据
		            axisTick: {
		                alignWithLabel: true
		            },
		            axisLabel: {
            interval: 0// 横轴信息全部显示
            // rotate: 30,//-30度角倾斜显示  
          },
          axisLine: {
                    	show: false
          }
		        }
		    ],
		    yAxis: [
		        {

		            type: 'value',
          minInterval: 1
		        }
		    ],
      legend: {
        data: ['正常数量', '异常数量']
      },
		    series: [
        {
          name: '异常数量',
          type: 'bar',
          stack: '数量',
          barGap: '-100%',
          barWidth: '50%',
          // barWidth: '60%',
          itemStyle: {
            normal: {

              color: '#C0504D'
            }
          },
          data: [] // // 需要添加数据
        },
        {
          name: '正常数量',
          type: 'bar',
          stack: '数量',
          barGap: '-100%',
          // barWidth: '60%',
          itemStyle: {
            normal: {
              color: '#4F81BD'
            }
          },
          data: [] // // 需要添加数据
        }

		    ]
    }
  },
  // 环形图配置
  pie () {
    return {
      tooltip: {
		        trigger: 'item',
		        formatter: '{a} <br/>{b}: {c} ({d}%)'
		    },
		    legend: {
		        orient: 'vertical',
		        left: 'auto',
		        data: []
		    },
		    toolbox: {
        show: true,
        orient: 'vertical',
        right: 20,
        top: 'center',
        feature: {
          restore: {show: true},
          saveAsImage: {show: true}
        }
      },
		    series: [
		        {
		            name: '',
		            type: 'pie',
		            radius: ['50%', '70%'],

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
		            data: [] // 需要添加数据
		        }
		    ]
    }
  },
  // 关系图配置
  graph () {
    return {
      backgroundColor: new echarts.graphic.RadialGradient(0.3, 0.3, 0.8, [{
        offset: 0,
        color: '#f7f8fa'
      }, {
        offset: 1,
        color: '#cdd0d5'
      }]),
      title: {
	            text: '',
	            top: 'top',
	            left: 'center'
	        },
	        toolbox: {
        show: true,
        orient: 'vertical',
        right: 20,
        top: 'center',
        feature: {
          restore: {show: true, title: '还原'},
          saveAsImage: {show: true}
        }
      },
      legend: [{
        show: false,
	            tooltip: {
	                show: true
	            },
	            orient: 'vertical',
	            selectedMode: 'false',
	            left: 20,
	            top: 20,
	            data: [], // 对应关系图的机房
	            formatter: function (name) {
	                return echarts.format.truncateText(name, 100, '14px Microsoft Yahei', '…')
	            }
	        }],
      animationDuration: 1500,
      animationEasingUpdate: 'quinticInOut',
      series: [
            	{
            		name: '', // 对应中心节点的名称
            		type: 'graph',
            		roam: false,
            		layout: 'force',
            		force: {
            repulsion: 300,
            edgeLength: 120
          },
          edgeSymbol: ['none', 'arrow'],
          symbolSize: 50,
          data: [], // 对应关系图的信息{name: '', symbolSize: 20, category: '', draggable: '', value: ''}
          links: [], // 对应关系图的从属关系 {source: '', target: ''}
          categories: [], // 对应关系图的分类名称 {name: ''}
          // focusNodeAdjacency: true,
          label: {
	                    normal: {
	                    	show: true,
	                        position: 'top',
	                        // formatter: '{b} : {c}',
	                        formatter: '{b}'
	                    }
	                },
	                lineStyle: {
	                    normal: {
	                        color: 'target',
	                        curveness: 0
	                    }
	                }
            	}
      ]
    }
  },
  // 折线图配置
  line () {
    return {
      backgroundColor: '#f7f7f7',
      title: {
		        text: '' // 可根据需要配置
		    },
		    tooltip: {
		        trigger: 'axis',
	            axisPointer: {
	                animation: true,
	                lineStyle: {
	                    color: '#ccc',
	                    width: 2,
	                    opacity: 1
	                }
	            }
	            // formatter: "{b}\n{c}%"
		    },
	     	toolbox: {
        show: true,
        orient: 'vertical',
        right: 20,
        top: 'center',
        feature: {
          restore: {show: true, title: '还原'},
          saveAsImage: {show: true}
        }
      },
		    xAxis: {
		        type: 'category',
	            axisTick: {
	                // alignWithLabel: true
	            },
	            axisLine: {
	                // onZero: false,
	            },
	            data: []
		    },
		    yAxis: {
		        type: 'value',
		        /* axisLabel: {
		            formatter: '{value} %'
		        }, */
		        axisPointer: {
		            snap: true
		        }
		        /* boundaryGap: [0, '100%'],
		        splitLine: {
		            show: false
		        } */
		    },
		    series: [{
		        name: '趋势统计',
		        type: 'line',
		        avoidLabelOverlap: false,
	            animation: true,
	            // animationEasing: "cubicIn",
	            animationDuration: 2000,
	            smooth: true,
		        data: [] // 需要配置的数据
		    }]
    }
  },
  scatter () {
    return {
      // legend: {
		 //        data: [],
		 //        left: 'right'
		 //    },
		    title: [], /*
			textBaseline: 'middle',
       	 	top: (idx + 0.5) * 100 / 7 + '%',
        	text: day
		    */
		    tooltip: {
		        position: 'top'
		        /* formatter: function (params) {
		            return params.value[2] + ' commits in ' + hours[params.value[0]] + ' of ' + days[params.value[1]];
		        } */
		    },
		    toolbox: {
        show: true,
        orient: 'vertical',
        right: 20,
        top: 'center',
        feature: {
          restore: {show: true, title: '还原'},
          saveAsImage: {show: true}
        }
      },
		    // grid: {
		    //     left: 2,
		    //     bottom: -10,
		    //     right: 10,
		    //     containLabel: true
		    // },
		    singleAxis: [],
		    series: []
    }
  }
}
