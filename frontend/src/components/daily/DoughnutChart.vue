<template>
  <div class="chart-wrapper">
    <Doughnut :data="chartData" :options="chartOptions" />
  </div>
</template>

<script setup>
import { Doughnut } from 'vue-chartjs'
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js'
import ChartDataLabels from 'chartjs-plugin-datalabels'

ChartJS.register(ArcElement, Tooltip, Legend, ChartDataLabels)

const props = defineProps({
  ratio: {
    type: Object,
    required: true,
  },
})

const chartData = {
  labels: ['탄수화물', '단백질', '지방'],
  datasets: [
    {
      data: [props.ratio.carbs, props.ratio.protein, props.ratio.fat],
      label: '탄단지',
      backgroundColor: [
        '#F4C430', // 탄
        '#6BCB77', // 단
        '#FF6B6B', // 지
      ],
      borderWidth: 0,
    },
  ],
}

const chartOptions = {
  responsive: true,
  cutout: '65%',
  plugins: {
    legend: {
      position: 'bottom',
    },
    datalabels: {
      color: '#fff',
      font: { weight: 'semi-bold', size: 20, family: 'Pretandard' },
      formatter: (value) => {
        return value + '%'
      },
    },
  },
}
</script>

<style scoped>
.chart-wrapper {
  max-width: 320px;
  margin: 0 auto;
}
</style>
