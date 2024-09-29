<template>
<div id="container_poster">
  <div style="width: 100%;height: 100%;">
    <el-image :fit="'fill'" style="width: 100%;height: 100%;z-index: -1111" src="../src/assets/image/社团服务.png"/>
    <div style="width: 100%;height: 100px;background-color: #0a5bec;z-index: 1000;display: flex;justify-content: center;">
    <el-button @click="goLogin" style="height: 50px;border-radius: 15px;font-size: 20px;--el-button-bg-color: transparent;color: white;position: relative;margin-top: -150px;width: 200px;letter-spacing: 4px;border: 2px solid #444444">
      进行登录
    </el-button>
    </div>
  </div>
</div>
</template>

<script setup>
import {useRouter} from "vue-router";

const router = useRouter()


const goLogin = () => {
  router.push('/frontLogin')
}

</script>

<style scoped>
#container_poster{
  width: 430px;
  height: 844px;
  background-size: cover;
  overflow: hidden;
}
</style>