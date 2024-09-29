<template>
  <div id="mynew_container">
    <!--   10 -->
    <div style="width: 100%;height: 10%;background-color: white;display: flex">
      <div style="width: 15%;height: 100%;display: flex;z-index: 100" @click="goMe">
        <van-icon style="margin: auto" size="1.8rem" color="#3B3B3BFF" name="arrow-left" />
      </div>
      <div style="width: 100%;height: 10%;display: flex;position: absolute">
        <div style="margin: auto;letter-spacing: 0.3rem;font-size: 1.25rem;font-weight: bolder;font-family: 华文彩云;color: #2a2828">
          我的社团招新
        </div>
      </div>
    </div>
    <div style="letter-spacing: 0.2rem;font-size: 1.1rem;width: 100%;height: 10%;background-color: white;margin-top: 1rem;display: flex;justify-content: center;align-items: center">
      社团加入申请列表
    </div>
    <div style="width: 100%;height: 65%;margin-top: 5%;">
      <div
          v-for="item in tableData.value"
           :key="item"
          style="width: 100%;height: 24%;background-color: white;margin-bottom: 1.5%;padding: 0.5rem 0.5rem 0.5rem 0.5rem;box-sizing: border-box">
        <div style="width: 100%;height: 100%;display: flex">
          <div style="height: 100%;width: 70%;">
            <div style="width: 100%;height: 33%;display: flex;justify-content: left;align-items: center">
              <div style="width: 40%">姓名：</div>
              <div style="width: 30%;height: 100%;display: flex;justify-content: left;align-items: center;">
                {{item.username}}
              </div>
            </div>
            <div style="width: 100%;height: 33%;display: flex;justify-content: left;align-items: center">
              <div style="width: 40%">班级：</div>
              <div style="width: 60%;height: 100%;display: flex;justify-content: left;align-items: center">
                {{item.className}}
              </div>
            </div>
            <div style="width: 100%;height: 33%;display: flex;justify-content: left;align-items: center">
              <div style="width: 40%">申请时间：</div>
              <div style="width: 60%;height: 100%;display: flex;justify-content: left;align-items: center;">
                {{formatDateTimeForHMS(item.createTime)}}
              </div>
            </div>

          </div>
          <div style="width: 30%;height: 100%">
            <div style="width: 100%;height: 50%;display: flex">
              <van-button @click="agreeAdd(item)" style="width: 80%;height: 80%;margin: auto" type="primary" text="同意"/>
            </div>
            <div style="width: 100%;height: 50%;display: flex">
              <van-button @click="reject(item.id)" style="width: 80%;height: 80%;margin: auto" type="danger" text="拒绝"/>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div style="margin-top: 0rem;width: 100%;height: 10%;display: flex">
      <van-pagination style="margin: auto" v-model="query.pageNo" :total-items="total" @change="queryNew">
        <template #prev-text>
          <van-icon size="1.5rem" name="arrow-left"/>
        </template>
        <template #next-text>
          <van-icon size="1.5rem" name="arrow"/>
        </template>
      </van-pagination>
    </div>
  </div>
</template>

<script setup>
import {useRouter} from "vue-router";
import {onMounted, reactive, ref} from "vue";
import instance from "../../api/request.js";
import {ElMessage} from "element-plus";

const router = useRouter()

const show = ref(false)

const goMe = () => {
  router.push('/front/me')
}

const total = ref()

const query = reactive({
  pageNo: 1,
  pageSize: 4
})

const agreeAdd = (item) => {
  instance.post('/record/agreeRecord',item).then(res =>{
    if (res.data.code == '200'){
      instance.post('/record/getNew',query).then(res =>{
        if (res.data.code == '200'){
          tableData.value = res.data.page.list
          total.value = res.data.page.total
          console.log(res.data.page)
        }
      })
      ElMessage({
        message: "已同意",
        type: 'success'
      })
    }else {
      ElMessage({
        message: "系统错误",
        type: 'error'
      })
    }
  })
}

const reject = (id) => {
  instance.post('/record/reject/' + id).then(res =>{
    if (res.data.code == '200'){
      instance.post('/record/getNew',query).then(res =>{
        if (res.data.code == '200'){
          tableData.value = res.data.page.list
          total.value = res.data.page.total
          console.log(res.data.page)
        }
      })
      ElMessage({
        message: "已拒绝",
        type: 'error'
      })
    }else {
      ElMessage({
        message: "系统错误",
        type: 'error'
      })
    }
  })
}

const tableData = reactive([])

const queryNew = () => {
  instance.post('/record/getNew',query).then(res =>{
    if (res.data.code == '200'){
      tableData.value = res.data.page.list
      total.value = res.data.page.total
      console.log(res.data.page)
    }
  })
}

onMounted(() =>{
  instance.post('/record/getNew',query).then(res =>{
    if (res.data.code == '200'){
      tableData.value = res.data.page.list
      total.value = res.data.page.total
      console.log(res.data.page)
    }
  })
})

function formatDateTimeForHMS(obj) {
  if (obj == null) {
    return null
  }
  let date = new Date(obj);
  let y = 1900 + date.getYear();
  let m = "0" + (date.getMonth() + 1);
  let d = "0" + date.getDate();
  let h = "0" + date.getHours();
  let mm = "0" + date.getMinutes();
  let s = date.getSeconds();
  return y + "-" + m.substring(m.length - 2, m.length) + "-" + d.substring(d.length - 2, d.length) + " " + h.substring(h.length - 2, h.length) + ":" + mm.substring(mm.length - 2, mm.length);
}

</script>

<style scoped>
#mynew_container{
  width: 100%;
  height: 92vh;
  background-color: #efefef;
}
</style>