<template>
  <div id="message_desc_container">
    <!--   10 -->
    <div style="width: 100%;height: 10vh;background-color: white;display: flex">
      <div style="width: 15%;height: 100%;display: flex;z-index: 100" @click="goMessage">
        <van-icon style="margin: auto" size="1.8rem" color="#3B3B3BFF" name="arrow-left" />
      </div>
      <div style="width: 100%;height: 10%;display: flex;position: absolute">
        <div style="margin: auto;letter-spacing: 0.3rem;font-size: 1.25rem;font-weight: bolder;font-family: 华文彩云;color: #2a2828">
          校友交流圈
        </div>
      </div>
    </div>
    <div style="width: 100%;box-sizing: border-box;padding: 1rem">
      <div style="width: 100%;height: 5rem;display: flex;flex-wrap: wrap">
        <div style="width: 15%;height: 100%;display: flex;justify-content: center;align-items: center">
          <el-avatar
              :size="'large'"
              :src="message.avater"
          />
        </div>
        <div
            style="width: 30%;height: 100%;display: flex;justify-content: left;align-items: center;box-sizing: border-box;padding-left: 10px">
          <div style="font-size: 20px">{{ message.username }}</div>
        </div>
        <div style="width: 50%;height: 100%;display: flex;justify-content: right;align-items: end">
          <div style="color: #646363">{{ formatDateTimeForHMS(message.createTime) }}</div>
        </div>
      </div>
        <div style="width: 100%;height: 25vh;;margin-top: 15px;">
          <el-image style="width: 100%;height: 100%" :fit="'contain'" :src="message.image"/>
        </div>
        <div style="margin-top: 15px;">
          <div style="color: #646363">内容：</div>
          <div style="font-size: 20px;margin-top: 10px;letter-spacing: 2px">{{ message.comment }}</div>
        </div>
        <div style="width: 100%;margin-top: 40px;border: 1px solid #ffffff;border-radius: 5px;;box-sizing: border-box;padding: 5px">
          <el-input v-model="form.comment" type="textarea"/>
          <el-button style="margin-top: 15px" @click="messageAdd" type="primary">回复</el-button>
        </div>
        <div style="width: 100%;">
          <div style="width: 100%;height: 5vh;display: flex;justify-content: center;align-items: center">
            <div style="font-size: 20px;color: #9d9d9d">评论列表</div>
          </div>
          <div style="margin-top: 20px;width: 100%;">
            <div style="width: 100%;margin-top: 30px;border-bottom: 1px solid #e4e4e4;box-sizing: border-box;padding-bottom: 10px;padding-top: 10px"
                 v-for="item in messageRecordList.value" :key="item">
              <div style="display: flex;flex-wrap: wrap;height: 5vh">
                <el-avatar :size="35" :src="item.avatar"/>
                <div style="width: 15%;display: flex;justify-content: center;align-items: center"><div>{{item.username}}</div></div>
                <div style="margin-left: 60vw;font-size: 12px;color: #a4a3a3">{{formatDateTimeForHMS(item.createTime)}}</div>
              </div>
              <div style="margin-top: 10px;letter-spacing: 2px;line-height: 25px">
                <div>{{item.comment}}</div>
              </div>
            </div>
          </div>
        </div>


    </div>

  </div>
</template>

<script setup>
import {useRouter} from "vue-router";
import {onMounted, reactive, ref} from "vue";
import instance from "../../api/request.js";
import {ElMessage} from "element-plus";

const router = useRouter();

const messageId = ref(router.currentRoute.value.query.id)

const message = ref({})

const messageRecordList = reactive([])

const form = reactive({
  comment: '',
  messageId: messageId.value
})

const goMessage = () => {
  router.push('/front/message')
}

const messageAdd = () => {
  instance.post('/messagerecord/add',form).then( res => {
    if (res.data.code == '200'){
      ElMessage({
        message: "发表成功",
        type: 'success'
      })
      form.comment = ''
    }else {
      ElMessage({
        message: res.data.msg,
        type: 'error'
      })
    }
    messageList()
  })

}

const messageList = () => {
  instance.post('/messagerecord/' + messageId.value).then( res => {
    if (res.data.code == '200'){
      console.log(res.data.list)
      messageRecordList.value = res.data.list
    }
  })
}

const getMessageIntro = () => {
  instance.get('/messages/detail/' + messageId.value).then( res => {
    console.log(res.data.message)
    if (res.data.code == '200'){
      message.value = res.data.message
    }
  })
}

onMounted( () => {
  getMessageIntro()
  messageList()
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
#message_desc_container{
  width: 100%;
  box-sizing: border-box;
  padding-bottom: 200px;
  background-color: white;
}
</style>