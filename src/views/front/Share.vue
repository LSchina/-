<template>
  <div id="share_container">
    <!--   10 -->
    <div style="width: 100%;height: 10%;background-color: white;display: flex">
      <div style="width: 15%;height: 100%;display: flex;z-index: 100" @click="goIndex">
        <van-icon style="margin: auto" size="1.8rem" color="#3B3B3BFF" name="arrow-left" />
      </div>
      <div style="width: 100%;height: 10%;display: flex;position: absolute">
        <div style="margin: auto;letter-spacing: 0.3rem;font-size: 1.25rem;font-weight: bolder;font-family: 华文彩云;color: #2a2828">
          我的动态
        </div>
      </div>
    </div>

    <van-search
        style="margin-top: 0.5rem"
        v-model="query.title"
        show-action
        placeholder="请输入动态主题"
        @search="shareSearch"
    >
      <template #action>
        <div @click="shareSearch">搜索</div>
      </template>
    </van-search>
    <div style="width: 100%;height: 73%;margin-top: 0.5rem">
      <van-swipe-cell v-for="item in tableData.value" :key="item">
        <template #left>
          <van-button @click="intro(item.id)" square type="primary" text="详情" />
        </template>
        <div
            style="margin-top: 0.1rem;width: 100%;font-size: 1.15rem;;height: 3rem;background-color: white;display: flex;justify-content: space-between">
          <div style="width: 50%;height: 100%;display: flex;">
            <div style="margin: auto">{{ item.title }}</div>
          </div>
          <div style="width: 50%;height: 100%;display: flex;">
            <div style="margin: auto">{{ formatDateTimeForHMS(item.createTime) }}</div>
          </div>
        </div>
        <template #right>
          <van-button @click="messagedelete(item.id)"  square type="danger" text="删除" />
        </template>
      </van-swipe-cell>
    </div>
    <van-pagination v-model="query.pageNo" :total-items="total" @change="shareSearch">
      <template #prev-text>
        <van-icon name="arrow-left"/>
      </template>
      <template #next-text>
        <van-icon name="arrow"/>
      </template>
    </van-pagination>

  </div>
  <van-toast v-model:show="show" style="padding: 0">
    <template #message>
      操作成功
    </template>
  </van-toast>

  <van-action-sheet v-model:show="qingshow" style="height: 55%" title="物品详情">
    <div  style="width: 100%;height: 100%;padding: 3rem 3rem 3rem 3rem;box-sizing: border-box">
      <div style="width: 100%;height: 20%;">
        <div style="width: 100%;height: 50%;color: #a2a0a0;font-weight: bolder">名称：</div>
        <div style="width: 100%;height: 50%;font-size: 1.15rem">{{introForm.title}}</div>
      </div>
      <div style="width: 100%;height: 20%;">
        <div style="width: 100%;height: 50%;color: #a2a0a0;font-weight: bolder">内容：</div>
        <div style="width: 100%;height: 50%;font-size: 1.15rem">{{introForm.comment}}</div>
      </div>
      <div style="width: 100%;height: 40%;">
        <div style="width: 100%;height: 50%;color: #a2a0a0;font-weight: bolder">图片：</div>
        <van-image style="width: 10rem;height: 5rem" :src="introForm.image"/>
      </div>
    </div>
  </van-action-sheet>
</template>

<script setup>
import {useRouter} from "vue-router";
import {onMounted, reactive, ref} from "vue";
import instance from "../../api/request.js";

const router = useRouter()

const goIndex = () => {
  router.push('/front/me')
}

const introForm = reactive({
  title:'',
  comment:'',
  image:''
})

const qingshow = ref(false)

const show = ref(false)

const total = ref()

const query = reactive({
  title:'',
  pageNo: 1,
  pageSize: 11
})

const tableData = reactive([])

const shareSearch = () => {
  instance.post('/messages/queryMy',query).then(res =>{
    if (res.data.code == '200'){
      tableData.value = res.data.page.list
      total.value = res.data.page.total
      console.log(res.data.page)
    }
  })
}

onMounted(() =>{
  instance.post('/messages/queryMy',query).then(res =>{
    if (res.data.code == '200'){
      tableData.value = res.data.page.list
      total.value = res.data.page.total
      console.log(res.data.page)
    }
  })
})

const messagedelete = (id) => {
  instance.delete('/messages/delete/' + id).then(res =>{
    if (res.data.code == '200'){
      show.value = true
    }
  })
}

const intro = (id) => {
  qingshow.value = true
  instance.post('/messages/getIntro/' + id).then(res =>{
    if (res.data.code == '200'){
      introForm.title = res.data.message.title
      introForm.comment = res.data.message.comment
      introForm.image = res.data.message.image
    }
  })
}

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
#share_container{
  width: 100%;
  height: 92vh;
  background-color: #efefef;
}
</style>