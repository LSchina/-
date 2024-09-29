<template>
  <div id="layout_container">
    <el-menu
        style="height: 7vh"
        class="el-menu-demo"
        mode="horizontal"
        :ellipsis="false"
    >
      <el-menu-item index="/back/home">
        <img
            style="width: 40px"
            src="/public/vite.svg"
            alt="Element logo"
        />
      </el-menu-item>
      <el-menu-item index="/back/home"><div style="letter-spacing: 2px;font-size: 18px">社团服务后台管理</div></el-menu-item>
      <el-menu-item index="">
        <el-button type="text" @click="logout">退出</el-button>
      </el-menu-item>
    </el-menu>
    <div style="width: 100%;height: 100vh;display: flex;overflow: hidden">
    <el-col style="height: 100%" :span="3">
      <el-menu
          router
          style="height: 100%;background-color: white"
          class="el-menu-vertical-demo"
      >
        <el-menu-item index="/back/home">
            <el-icon><House /></el-icon>
            <span style="font-size: 15px">主页</span>
        </el-menu-item>
        <el-sub-menu index="1">
          <template #title>
            <el-icon><User /></el-icon>
            <span  style="font-size: 15px">人员管理</span>
          </template>
          <el-menu-item-group>
            <el-menu-item  style="font-size: 15px" index="/back/user">用户管理</el-menu-item>
            <el-menu-item  style="font-size: 15px" index="/back/admin">管理员管理</el-menu-item>
          </el-menu-item-group>
        </el-sub-menu>
        <el-menu-item index="/back/com">
          <el-icon><MessageBox/></el-icon>
          <span  style="font-size: 15px">社团管理</span>
        </el-menu-item>
        <el-menu-item index="/back/message">
          <el-icon><View /></el-icon>
          <span  style="font-size: 15px">动态管理</span>
        </el-menu-item>
        <el-menu-item index="/back/carousel">
          <el-icon><Operation /></el-icon>
          <span  style="font-size: 15px">广告管理</span>
        </el-menu-item>
        <el-sub-menu index="7">
          <template #title>
            <el-icon><ChatLineRound/></el-icon>
            <span  style="font-size: 15px">消息管理</span>
          </template>
          <el-menu-item-group>
            <el-menu-item  style="font-size: 15px" index="/back/notice">公告管理</el-menu-item>
            <el-menu-item  style="font-size: 15px" index="/back/new">招新管理</el-menu-item>
          </el-menu-item-group>
        </el-sub-menu>
        <el-menu-item index="/back/love">
          <el-icon><Notification /></el-icon>
          <span  style="font-size: 15px">活动管理</span>
        </el-menu-item>
      </el-menu>
    </el-col>
    <el-col style="height: 100%;overflow: hidden" :span="21">
      <div style="width: 100%;height: 100%;box-sizing: border-box;padding: 10px">
        <div style="width: 100%;height: 100%;">
          <router-view/>
        </div>
      </div>
    </el-col>
    </div>
  </div>

</template>

<script setup>
import {
  House,
  User,
  ChatLineRound,
  MessageBox,
  View,
  Operation,
  Notification
} from '@element-plus/icons-vue'
import {useRouter} from "vue-router";

const router = useRouter()

const logout = () => {
  router.push("/backLogin")
}

</script>

<style scoped lang="scss">
#layout_container {
  width: 100%;
  background-color: #efefef;
  overflow: hidden;
}
.el-menu--horizontal > .el-menu-item:nth-child(2) {
  margin-right: auto;
}
</style>