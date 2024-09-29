<template>
  <div class="wrap">
    <div class="logo">

      <div style="width: 100%;height: 30%;display: flex;">
        <van-image
            round
            style="width: 10rem;height: 10rem;margin: auto"
            src="https://fastly.jsdelivr.net/npm/@vant/assets/cat.jpeg"
        />
      </div>
      <div style="width: 100%;height: 20%;display: flex">
        <h1 style="color: #ba54da;font-size: 10rem;margin: auto;font-family: 华文新魏">404</h1>
      </div>

      <div style="width: 100%;height: 13%;text-align: center;margin-top: 5rem;font-size: 1.5rem">
        <div>The Page not Found</div>
        <div>找不到你要访问的页面</div>
      </div>

      <div style="width: 100%;height: 5%;display: flex;">
        <el-button @click="comeBack" class="sub_button">
          <div style="letter-spacing: 1px;color: black;font-size: 18px;font-weight: 800">返回登录页面</div>
        </el-button>
      </div>
    </div>
  </div>
</template>

<script setup>
import {useRouter} from "vue-router";

const router = useRouter()

const comeBack = () => {
  router.push('/frontLogin')
}
</script>

<style scoped lang="scss">
.wrap{
  background: white;
  font-family: 'Courgette', cursive;
  width: 100vw;
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
}
.logo{
  width: 90%;
  height: 80%;
}
.logo h1{

}
.logo p{

}
.logo p span{

}

  .sub_button{
    margin: auto;
    background-color: #95bafc;
    width: 10rem;
    height: 3rem;
    border-radius: 1rem;
  .sub_button:hover{

  }
}

</style>