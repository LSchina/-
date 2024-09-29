<template>
  <div class="login">

    <el-form label-position="top" :model="loginForm" :rules="loginRules" class="login-form">
      <h3 class="title">社团服务平台管理端</h3>
      <el-form-item label="账号" prop="username">
        <el-input
            v-model="loginForm.username"
            type="text"
            size="large"
            auto-complete="off"
            placeholder="账号"
        >
          <template #prefix><svg-icon icon="user" /></template>
        </el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input
            v-model="loginForm.password"
            type="password"
            size="large"
            auto-complete="off"
            placeholder="密码"
            @keyup.enter="handleLogin"
        >
          <template #prefix><svg-icon icon="password" /></template>
        </el-input>
      </el-form-item>
      <el-checkbox style="margin:0px 0px 25px 0px;">记住密码</el-checkbox>
      <el-form-item style="width:100%;">
        <el-button
            size="large"
            type="primary"
            style="width:100%;"
            @click.prevent="handleLogin"
        >
          <span>登 录</span>

        </el-button>

      </el-form-item>
    </el-form>
    <!--  底部  -->
    <div class="el-login-footer">
      <span>Copyright © 2024 <a href="https://github.com/LSchina/lishuo.first" target="_blank">LSchina.github</a> 版权所有.</span>
    </div>
  </div>
</template>

<script setup>
import {reactive, ref} from 'vue'
import {ElMessage} from 'element-plus'
import instance from "../../api/request.js";
import {useRouter} from "vue-router";
import Cookies from 'js-cookie';
import useDataStore from "../../store/index.js";

const router = useRouter();

const useData = useDataStore()

const loginForm = reactive({
  username: "",
  password: "",
});

const loginRules = {
  username: [{ required: true, trigger: "blur", message: "请输入您的账号" }],
  password: [{ required: true, trigger: "blur", message: "请输入您的密码" }],
};


const handleLogin = () => {
console.log(loginForm)
  instance.post("/admin/login",loginForm).then(res => {
    if (res.data.code == '200'){
      console.log(res.data.user)
      ElMessage({
        message: '欢迎您的光临',
        type: 'success',
      })
      Cookies.set('admin',"admin")
      useData.userId = res.data.user.id
      console.log(useData.userId)
      router.push('/back')
    }else {
      ElMessage({
        message: res.data.msg,
        type: 'error',
      })
    }
  })
}











</script>

<style lang="scss" scoped>
a{
  color:white
}
.login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-size: cover;
  background-color: #efefef;
}
.title {
  margin: 0px auto 30px auto;
  text-align: center;
  color: #707070;
}

.login-form {
  border-radius: 6px;
  background: #ffffff;
  width: 400px;
  padding: 25px 25px 5px 25px;
  box-shadow: 0 0 10px 5px rgba(0, 0, 0, 0.5);
  .el-input {
    height: 40px;



    input {
      display: inline-block;
      height: 40px;
    }
  }
  .input-icon {
    height: 39px;
    width: 14px;
    margin-left: 0px;
  }

}
.login-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}
.login-code {
  width: 33%;
  height: 40px;
  float: right;
  img {
    cursor: pointer;
    vertical-align: middle;
  }
}
.el-login-footer {
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: #fff;
  font-family: Arial;
  font-size: 12px;
  letter-spacing: 1px;
}
.login-code-img {
  height: 40px;
  padding-left: 12px;
}


</style>
