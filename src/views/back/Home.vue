<template>
<div id="layout_container">
  <div style="width: 100%;height: 100%;display: flex;flex-wrap: wrap">
    <div v-for="(chart,index) in chartsList" :key="index" style="height: 50%;width: 50%">
    <div style="display: flex;justify-content: center;align-items: center;box-sizing: border-box;padding-top: 5px;padding-left: 10px" ref="chartContainers" class="chart-container">
      <v-chart :option="chart" autoresize></v-chart>
    </div></div>
  </div>
</div>
</template>

<script setup>
import * as echarts from 'echarts';
import VChart from 'vue-echarts';
import {onMounted, reactive, ref} from "vue";
import instance from "../../api/request.js";

// 初始化图表数据

const chartsList = ref([
  {
    title: {
      text: '社团人数饼形图',
      left: 'center'
    },
    tooltip: {
      trigger: 'item'
    },
    legend: {
      orient: 'vertical',
      left: 'left'
    },
    series: [
      {
        name: '已注册人数',
        type: 'pie',
        radius: '50%',
        data: [],
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        }
      }
    ]
  },
  {
    title: {
      text: '近7天的消息回复情况',
      left: 'center'
    },
    xAxis: {
      type: 'category',
      data: []
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        data: [],
        type: 'line'
      }
    ]
  },
  {
    title: {
      text: '一周内回复数趋势图',
      left: 'center'
    },
    tooltip: {
      trigger: 'axis'
    },
    xAxis: {
      type: 'category',
      data: []
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        data: [],
        type: 'bar'
      }
    ]
  },
  {
    title: {
      text: '消息公告数量可视化',
      left: 'center'
    },
    tooltip: {
      trigger: 'item'
    },
    legend: {
      top: '7%',
      left: 'center'
    },
    series: [
      {
        name: 'Access From',
        type: 'pie',
        radius: ['40%', '70%'],
        avoidLabelOverlap: false,
        padAngle: 5,
        itemStyle: {
          borderRadius: 10
        },
        label: {
          show: false,
          position: 'center'
        },
        emphasis: {
          label: {
            show: true,
            fontSize: 40,
            fontWeight: 'bold'
          }
        },
        labelLine: {
          show: false
        },
        data: [
          { value: 1048, name: '动态数' },
          { value: 735, name: '公告数' },
          { value: 580, name: '招新数' }
        ]
      }
    ]
  }
]);


const chartTwo = () => {
  instance.get('/admin/chartTwo').then( res => {
    if (res.data.code == '200'){
      for (let i = 0; i < res.data.name.length; i++) {
        chartsList.value[0].series[0].data.push({
          name: res.data.name[i],
          value: res.data.num[i]
        })
      }
    }
    console.log(chartsList.value[1])
  })
  instance.get('/admin/index').then( res => {
    if (res.data.code == '200'){
      console.log(res.data.list)
      for (let i = 0; i < res.data.list.length; i++) {
        chartsList.value[3].series[0].data[i].value = res.data.list[i]
      }
    }
})
  instance.get('/admin/count').then( res => {
    if (res.data.code == '200'){
      for (let i = 0; i < res.data.count.length; i++) {
        chartsList.value[1].xAxis.data.push(res.data.date[i])
        chartsList.value[1].series[0].data.push(res.data.count[i])
        chartsList.value[2].xAxis.data.push(res.data.date[i])
        chartsList.value[2].series[0].data.push(res.data.count[i])
      }
    }
    initCharts()
  })
}

const chartContainers = ref([]);



const initCharts = () => {
  chartsList.value.forEach((chart, index) => {
    const chartInstance = echarts.init(chartContainers.value[index],'dark');
    chartInstance.setOption(chart);

    // 自动调整图表大小
    window.addEventListener('resize', () => {
      chartInstance.resize();
    });
  });
};

onMounted(() =>{
  chartTwo()
})


</script>

<style scoped lang="scss">
#layout_container {
  width: 100%;
  height: 100%;
  background-color: #100c2a;
}

.chart-container {
  width: 100%;
  height: 100%;
}
</style>