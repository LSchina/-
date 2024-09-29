<template>
  <div id="love_container">
    <!--   10 -->
    <div style="width: 100%;height: 7vh;background-color: white;display: flex">
      <div style="width: 15%;height: 7vh;display: flex;z-index: 100" @click="goMe">
        <van-icon style="margin: auto" size="1.8rem" color="#3B3B3BFF" name="arrow-left" />
      </div>
      <div style="width: 100%;height: 8vh;display: flex;position: absolute">
        <div style="margin: auto;letter-spacing: 0.3rem;font-size: 1.25rem;font-weight: bolder;font-family: 华文彩云;color: #2a2828">
          校园活动
        </div>
      </div>
    </div>
    <div style="letter-spacing: 0.2rem;font-size: 1.1rem;width: 100%;height: 25vh;background-color: white;margin-top: 1rem;display: flex;justify-content: center;align-items: center">
      <van-image style="width: 100%;height: 100%" :src="image.image" />
    </div>
    <van-cell v-for="item in tableData.value" :key="item" style="margin-top: 1rem;width: 100%;height: 30vh">
      <div style="width: 100%;height: 11rem;">
        <van-image style="width: 100%;height: 100%" :src="item.image"/>
      </div>
      <div style="width: 100%;height: 4rem;display: flex;justify-content: space-between;padding: 0 0.3rem 0 0.3rem">
        <div  style="width: 70%;height: 100%;display: flex;justify-content: left;align-items: center"><div style="color: black;font-size: 0.91rem">报名对象：{{item.person}}</div></div>
        <div style="width: 30%;height: 100%;display: flex">
          <van-button @click="takeIn(item.id)" style="margin: auto" type="danger" text="报名"/></div>
      </div>
    </van-cell>
  </div>
</template>

<script setup>
import {useRouter} from "vue-router";
import {onBeforeMount, onMounted, reactive, ref} from "vue";
import instance from "../../api/request.js";
import {ElMessage} from "element-plus";

const router = useRouter()

const show = ref(false)

const image = ref({})

const tableData = reactive([])

const getCarousel = () => {
  instance.post('/carousel/get',{ type:0 }).then(res => {
    if (res.data.code == '200'){
      image.value = res.data.list[0]
    }
  })
}


const takeIn = (id) => {
  instance.post('/activity/takeIn/' + id).then(res => {
    if (res.data.code == '200'){
      ElMessage({
        message: res.data.msg,
        type: 'success'
      })
    }else{
      ElMessage({
        message: res.data.msg,
        type: 'error'
      })
    }
  })
}

const getActivityList = () => {
  instance.get('/admin/activityList').then( res => {
    if (res.data.code == '200'){
      tableData.value = res.data.list
    }
  })
}

const goMe = () => {
  router.push('/front/index')
}

onMounted(() => {
  getCarousel()
  getActivityList()
})

</script>

<style scoped>
#love_container{
  width: 100%;
  height: 150vh;
  background-color: #efefef;
}
</style>