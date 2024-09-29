<template>
  <div id="mycom_container">
    <!--   10 -->
    <div style="width: 100%;height: 10%;background-color: white;display: flex">
      <div style="width: 15%;height: 100%;display: flex;z-index: 100" @click="goIndex">
        <van-icon style="margin: auto" size="1.8rem" color="#3B3B3BFF" name="arrow-left" />
      </div>
      <div style="width: 100%;height: 10%;display: flex;position: absolute">
        <div style="margin: auto;letter-spacing: 0.3rem;font-size: 1.25rem;font-weight: bolder;font-family: 华文彩云;color: #2a2828">
          我的社团
        </div>
      </div>
    </div>
    <div style="letter-spacing: 0.2rem;font-size: 1.1rem;width: 100%;height: 10%;background-color: white;margin-top: 1rem;display: flex;justify-content: center;align-items: center">
      您所处社团
    </div>
    <div v-if="hasAuth('stystem:community:list')" style="padding: 0.2rem 2rem 0.2rem 2rem;box-sizing: border-box;letter-spacing: 0.2rem;font-size: 1.1rem;width: 100%;height: 20%;background-color: white;margin-top: 1rem;">
      <div style="width: 100%;height: 50%;display: flex;justify-content: center;align-items: center">您是社团管理员，可以对社团进行发布招新和发布公告</div>
      <div style="width: 100%;height: 50%;display: flex;align-items: center;justify-content: space-between">
        <van-button @click="goNotice" type="primary" style="width: 40%;height: 50%;" text="发布公告"/>
        <van-button @click="findnew" type="danger" style="width: 40%;height: 50%;" text="发布招新"/>
      </div>
    </div>
    <div>
      <div style="background-color: white;padding: 1rem 1rem 1rem 1rem;box-sizing: border-box;width: 100%;height: 7rem;margin-top: 1rem;border-radius: 0.5rem ">
        <div style="width: 100%;height: 100%;display: flex">
          <div style="border-radius: 0.8rem;width: 40%;height: 100%;">
            <van-image
                :radius="12"
                :src="comForm.avater"
                style="object-fit: cover;width: 100%;height: 100%;"
            />
          </div>
          <div style="width: 55%;margin-left: 5%">
            <div style="font-size: 1.3rem;font-family: 黑体;margin-top: 1rem">{{ comForm.name }}</div>
          </div>
        </div>
      </div>
  </div>
  </div>
</template>

<script setup>
import {onMounted, reactive} from "vue";
import instance from "../../api/request.js";
import hasAuth from "../../store/mixinFunc.js";
import {useRouter} from "vue-router";

const router = useRouter()

const goIndex = () => {
  router.push('/front/me')
}

const goNotice = () => {
  router.push('/front/notice')
}

const findnew = () => {
  router.push('/front/findnew')
}

const comForm = reactive({
  avater:'',
  name:''
})

onMounted(() =>{
  instance.get('/communities/getcom').then(res =>{
  if (res.data.code == '200'){
    comForm.avater = res.data.community.avater
    comForm.name = res.data.community.name
  }
  })
})

</script>

<style scoped>
#mycom_container{
  width: 100%;
  height: 92vh;
  background-color: #efefef;
}
</style>