<template>
  <div id="newIntro_container">
    <!--  header  -->
    <div style="width: 100%;height: 10%;background-color: white;display: flex">
      <div style="width: 15%;height: 100%;display: flex;z-index: 100" @click="goIndex">
        <van-icon style="margin: auto" size="1.8rem" color="#3B3B3BFF" name="arrow-left" />
      </div>
      <div style="width: 100%;height: 10%;display: flex;position: absolute">
        <div style="margin: auto;letter-spacing: 0.3rem;font-size: 1.25rem;font-weight: bolder;font-family: 华文彩云;color: #2a2828">
          招新详情
        </div>
      </div>
    </div>
    <div style="width: 100%;height: 90%;padding: 1rem 1rem 1rem 1rem;box-sizing: border-box">
      <div style="width: 100%;height: 100%;background-color: white">
        <div style="letter-spacing: 0.3rem;font-size: 1.25rem;font-weight: bolder;font-family: 华文彩云;width: 100%;height: 10%;display: flex;justify-content: center;align-items: center">
          招新简章
        </div>
        <div style="width: 100%;height: 30%;padding: 0.5rem 0.5rem 0.5rem 0.5rem;box-sizing: border-box">
          <van-image style="width: 100%;height: 100%" :src="messageForm.avater"/>
        </div>
        <div style="width: 100%;height: 60%;padding: 0.5rem 0.5rem 0.5rem 0.5rem;box-sizing: border-box">
          <div style="width: 100%;height: 5%">招新主题：</div>
          <div  style="width: 100%;height: 10%;display: flex;justify-content: center;align-items: center;font-size: 1.15rem;letter-spacing: 0.2rem">
            {{messageForm.title}}
          </div>
          <div style="width: 100%;height: 5%">招新内容：</div>
          <div  style="width: 100%;height: 40%;display: flex;justify-content: center;align-items: center;font-size: 1.15rem;letter-spacing: 0.2rem">
            {{messageForm.comment}}
          </div>
          <div style="width: 100%;height: 5%">开始时间：</div>
          <div  style="width: 100%;height: 15%;display: flex;justify-content: center;align-items: center;font-size: 1.15rem;letter-spacing: 0.2rem">
            {{formatDateTimeForHMS(messageForm.createTime)}}
          </div>
          <div @click="addrecord" style="width: 100%;height: 20%;display: flex"><van-button style="margin: auto" type="primary" text="申请报名"/></div>
        </div>

      </div>
    </div>
  </div>
  <van-toast v-model:show="show" style="padding: 5rem">
    <template #message>
      {{text.value}}
    </template>
  </van-toast>
</template>

<script setup>
import {useRouter} from "vue-router";
import {onMounted, reactive, ref} from "vue";
import instance from "../../api/request.js";

const router = useRouter()

const show = ref(false)

const text = reactive({
  value:''
})

const goIndex = () => {
  router.push('/front/newList')
}

const messageForm = reactive({
  id:'',
  communityId:'',
  comment:'',
  avater:'',
  title:'',
  createTime:'',

})

const addrecord = () => {
  instance.post('/record/add',messageForm).then(res =>{
    if (res.data.code == '200'){
      show.value = true
      text.value = "申请成功"
    }else{
      show.value = true
      text.value = res.data.msg
    }
  })
}

onMounted(() =>{
  const id = router.currentRoute.value.query.id
  instance.get('/messages/getNew/' + id).then(res =>{
    if (res.data.code == '200'){
      console.log(res.data.message)
      messageForm.id = res.data.message.id
      messageForm.communityId = res.data.message.communityId
      messageForm.avater = res.data.message.image
      messageForm.comment = res.data.message.comment
      messageForm.title = res.data.message.title
      messageForm.createTime = res.data.message.createTime
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
#newIntro_container{
  width: 100%;
  height: 92vh;
  background-color: #efefef;
}
</style>