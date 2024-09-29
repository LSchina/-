<template>
  <div id="message_container">
    <!--   10 -->
    <div style="width: 100%;height: 10%;background-color: white;display: flex">
      <div style="width: 15%;height: 100%;display: flex;z-index: 100" @click="goIndex">
        <van-icon style="margin: auto" size="1.8rem" color="#3B3B3BFF" name="arrow-left" />
      </div>
      <div style="width: 100%;height: 10%;display: flex;position: absolute">
        <div style="margin: auto;letter-spacing: 0.3rem;font-size: 1.25rem;font-weight: bolder;font-family: 华文彩云;color: #2a2828">
          校友交流圈
        </div>
      </div>
    </div>

    <div style="margin-top: 1rem">
      <van-cell-group inset>
        <van-field
            v-model="query.title"
            center
            clearable
            icon-prefix="search"
            placeholder="请输入内容的主题"
        >
          <template #button>
            <van-button size="small" type="primary" @click="queryMessage">
              <van-icon size="1.3rem" name="search"/>
            </van-button>
          </template>
        </van-field>
      </van-cell-group>
    </div>

    <div style="width: 100%;height:70%;display: flex;margin-top: 1rem;justify-content: center">
      <div style="width: 95%;">
        <ul>
          <li v-for="item in pageList.value"
              :key="item.id"
              @click="goDetail(item.id)"
              style="background-color: white;padding: 0.3rem 0.3rem 0.3rem 1rem;box-sizing: border-box;width: 100%;height: 7rem;margin-top: 1rem;border-radius: 0.5rem ">
            <div style="width: 100%;height: 20%;display: flex;">
              <div style="display: flex">
                <div style="margin: auto;font-family: '汉仪中黑 197'">主题:</div>
              </div>
              <div style="letter-spacing: 0.12rem;display: flex;flex: 1;">
                <div style="margin: auto;color: #646363">{{ item.title }}</div>
              </div>
            </div>
            <div style="width: 100%;height: 50%;display: flex;align-items: center">
              <div style="letter-spacing: 0.08rem;color: #646363">{{ item.comment}}
              </div>
            </div>
            <div style="display: flex;width: 100%;height: 30%;">
              <div style="display: flex">
                <van-image
                    style="margin: auto"
                    round
                    width="1.5rem"
                    height="1.5rem"
                    :src="item.avater"
                />
              </div>
              <div style="display: flex;justify-content: space-between">
                <div style="display: flex;margin-left: 0.5rem">
                  <div style="margin: auto">{{ item.username }}</div>
                </div>
                <div style="display: flex;margin-left: 7.5rem">
                  <div style="margin: auto;text-align: right;color: #646363;font-size: 0.9rem">{{ formatDateTimeForHMS(item.createTime) }}</div>
                </div>
              </div>
            </div>
          </li>
        </ul>
      </div>
    </div>
    <div style="margin-top: 1rem;background-color: white">
      <van-pagination v-model="query.pageNo" :page-count="pageSize" :total-items="total" @change="queryMessage">
        <template #prev-text>
          <van-icon size="1.5rem" name="arrow-left"/>
        </template>
        <template #next-text>
          <van-icon size="1.5rem" name="arrow"/>
        </template>
      </van-pagination>
    </div>
  </div>


  <van-floating-bubble
      axis="xy"
      icon="plus"
      magnetic="x"
      style="background-color: #a2a0a0"
      @click="openDialog"
  />
  <!-- 弹出框 -->
  <van-dialog v-model:show="show" title="发布您的信息" @confirm="saveMessage" show-cancel-button>
    <van-cell-group inset>
      <van-field
          v-model="message.title"
          rows="1"
          autosize
          label="主题"
          type="textarea"
          placeholder="请输入主题"
      />
      <!-- 输入任意文本 -->
      <van-field
          v-model="message.comment"
          rows="3"
          autosize
          label="写出您的心声"
          type="textarea"
          maxlength="50"
          placeholder="请输入您的内容"
          show-word-limit
      />
      <van-uploader style="margin-left: 20px;margin-top: -30px" :after-read="afterRead" />

    </van-cell-group>

  </van-dialog>

</template>

<script setup>
import {useRouter} from "vue-router";
import {onMounted,reactive, ref} from "vue";
import instance from "../../api/request";


const router = useRouter();

const total = ref(0);
const pageSize = ref(0);

const query = reactive({
  pageNo: 1,
  pageSize: 4,
  title:''
})

const pageList = reactive([])

const goDetail = (id) => {
  router.push({ path: '/front/detail', query: { id: id }})
}

const queryMessage = () => {
  instance.post('/messages/query',query).then(res => {
    console.log(query)
    if (res.data.code = '200'){
      console.log(res.data.pageList)
      pageList.value = res.data.pageList.list
      pageSize.value = res.data.pageList.pages
    }
  })
}

onMounted(()=>{
  instance.post('/messages/query',query).then(res =>{
    if (res.data.code == '200'){
      console.log(res.data.pageList)
      total.value = res.data.pageList.total
      pageSize.value = res.data.pageList.pages
      pageList.value = res.data.pageList.list
    }
  })
})

const goIndex = () => {
  router.push('/front/index')
}
const message = reactive({
  comment:'',
  title:'',
  file:''
})
const show = ref(false)

const afterRead = (v) => {
  message.file =  v.file
}

const openDialog = () => {
  show.value = true
}

const saveMessage = () => {
  let formData = new FormData();
  formData.append('file', message.file)
  formData.append('comment', message.comment)
  formData.append('title', message.title)
  let config = {
    headers: {
      "Content-Type": "multipart/form-data",
    }
  }
  console.log(message.file)
  instance.post('/messages/saveMe',formData, config).then(res => {
    if (res.data.code == '200') {
      queryMessage()
    }
    message.comment = ''
    queryMessage()
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
#message_container{
  height: 92vh;
  width: 100%;
}
</style>