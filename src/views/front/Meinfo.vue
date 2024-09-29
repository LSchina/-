<template>
  <div id="meinfo_container">
    <!--   10 -->
    <div style="width: 100%;height: 10%;background-color: white;display: flex">
      <div style="width: 15%;height: 100%;display: flex;z-index: 100" @click="goIndex">
        <van-icon style="margin: auto" size="1.8rem" color="#3B3B3BFF" name="arrow-left" />
      </div>
      <div style="width: 100%;height: 10%;display: flex;position: absolute">
        <div style="margin: auto;letter-spacing: 0.3rem;font-size: 1.25rem;font-weight: bolder;font-family: 华文彩云;color: #2a2828">
          个人信息
        </div>
      </div>
    </div>

    <div style="width: 100%;height: 20%;display: flex;justify-content: center;align-items: center">
      <el-upload
          class="upload-demo"
          action=""
          :show-file-list="false"
          :http-request="uploadFile">
      <van-image
          round
          width="8rem"
          height="8rem"
          :src="useData.avater"
      />
      </el-upload>
    </div>
    <div style="width: 100%;height: 3%;display: flex;">
      <div style="margin: auto;letter-spacing: 0.2rem;font-size: 1.2rem">{{ useData.username }}</div>
    </div>
    <div style="width: 100%;height: 20%;display: flex">
      <div style="width: 50%;height: 100%;display: flex;">
        <div style="margin: auto;width: 90%;height: 80%;color: white;background:repeating-linear-gradient(to right,#5bc783,#029779);border-radius: 1.2rem;display: flex;justify-content: center;align-items: center">
          <div style="width: 100%;height: 70%;letter-spacing: 0.15rem;font-weight: bolder">
            <div style="width: 100%;height: 50%;display: flex;font-size: 1.2rem"><div style="margin: auto">我的职位</div></div>
            <div style="width: 100%;height: 50%;display: flex;font-size: 1rem"><div style="margin: auto">{{useData.roles}}</div></div>
          </div></div>
      </div>
      <div style="width: 50%;height: 100%;display: flex">
        <div style="margin: auto;width: 90%;height: 80%;color: white;background:repeating-linear-gradient(to right,#81b4fc,#b6e1fe);border-radius: 1.2rem;display: flex;justify-content: center;align-items: center">
          <div style="width: 100%;height: 70%;font-size: 1.2rem;letter-spacing: 0.15rem;font-weight: bolder">
          <div style="width: 100%;height: 50%;display: flex"><div style="margin: auto;font-size: 1.2rem">我的回复数</div></div>
          <div style="width: 100%;height: 50%;display: flex"><div style="margin: auto;font-size: 1rem">100</div></div>
        </div></div>
      </div>
    </div>
    <div style="width: 100%;height: 47%;display: flex;">
      <van-cell-group style="width: 100%;height: 95%;margin: auto" inset>
        <van-cell icon="records-o" style="width: 100%;height: 20%;--van-cell-icon-size: 1.8rem;--van-cell-large-title-font-size: 1.2rem;--van-cell-horizontal-padding: 1.5rem;letter-spacing: 0.15rem" title="修改个人信息" is-link  size="large"/>
        <van-cell @click="goMycom" icon="home-o" style="width: 100%;height: 20%;--van-cell-icon-size: 1.8rem;--van-cell-large-title-font-size: 1.2rem;--van-cell-horizontal-padding: 1.5rem;letter-spacing: 0.15rem" title="我的社团" is-link  size="large"/>
        <van-cell @click="goMyNew" v-if="hasAuth('stystem:community:list')" icon="like-o" style="width: 100%;height: 20%;--van-cell-icon-size: 1.8rem;--van-cell-large-title-font-size: 1.2rem;--van-cell-horizontal-padding: 1.5rem;letter-spacing: 0.15rem" title="我的社团招新" is-link  size="large"/>
        <van-cell @click="goShare" icon="chat-o" style="width: 100%;height: 20%;--van-cell-icon-size: 1.8rem;--van-cell-large-title-font-size: 1.2rem;--van-cell-horizontal-padding: 1.5rem;letter-spacing: 0.15rem" title="我的动态列表" is-link  size="large"/>
        <van-cell icon="revoke" to="/frontLogin" style="width: 100%;height: 20%;--van-cell-icon-size: 1.8rem;--van-cell-large-title-font-size: 1.2rem;--van-cell-horizontal-padding: 1.5rem;letter-spacing: 0.15rem" title="退出登录" is-link  size="large"/>
      </van-cell-group>
    </div>
  </div>
</template>

<script setup>
import useDataStore from "../../store/index.js";
import {useRouter} from "vue-router";
import {reactive} from "vue";
import hasAuth from "../../store/mixinFunc.js";
import instance from "../../api/request.js";


const useData = useDataStore()

const router = useRouter()

const avater = reactive({
  image:''
})

const uploadFile = (v) => {
  avater.image = v.file
  let dataForm = new FormData()
  dataForm.append('file',avater.image)
  let config = {
    headers: {
      "Content-Type": "multipart/form-data",
    }
  }
  instance.post('/user/avatar',dataForm,config).then( res => {
    if (res.data.code == '200'){
      useData.avater = res.data.msg

    }
  })
}

const user = reactive({
  identity:'社团负责人'
})

const goMycom = () => {
  router.push('/front/mycom')
}

const goShare = () => {
  router.push('/front/share')
}

const goMyNew = () => {
  router.push('/front/mynew')
}

const goIndex = () => {
  router.push('/front/index')
}
</script>

<style scoped>
#meinfo_container{
  width: 100%;
  height: 92vh;
  background-color: #efefef;
}
</style>