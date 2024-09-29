<template>
  <div id="container">
    <div style="width: 100%;height: 90%">
      <router-view/>
    </div>
    <van-tabbar style="height: 8vh" v-model="active">
      <van-tabbar-item style="--van-tabbar-item-icon-size:27px"  @click="goDating" icon="home-o">主页</van-tabbar-item>
      <van-tabbar-item  style="--van-tabbar-item-icon-size:27px"  @click="goLove" icon="balance-list-o">联谊</van-tabbar-item>
      <van-tabbar-item  style="--van-tabbar-item-icon-size:27px"  @click="goCommun" icon="friends-o" >动态</van-tabbar-item>
      <van-tabbar-item  style="--van-tabbar-item-icon-size:27px"  @click="goMe" icon="setting-o" >我的</van-tabbar-item>
    </van-tabbar>

  </div>
</template>

<script setup>
import {useRouter} from "vue-router";
import {ref} from "vue";

const router = useRouter()

const active = ref(0)

const goDating = () => {
  router.push('/front/index')
}

const goLove = () => {
  router.push('/front/love')
}

const goCommun = () => {
  router.push('/front/message')
}
const goMe = () => {
  router.push('/front/me')
}
</script>

<style scoped lang="scss">
#container{
  width: 100vw;
  background-color: #efefef;
  overflow: hidden;
  #hometitle{
    width: 100%;
    height: 10%;
    background-color: white;
    position: absolute;
    bottom: 0;
    overflow: hidden;
    .icon_box{
      box-sizing: border-box;
      width: 100%;
      height: 100%;
      padding: 0 0.2rem 0 0.2rem;
      display: flex;
      align-items: center;
    }
  }
}
</style>
