<template>
  <div id="login_container">
    <div class="display_box">
      <div class="login_item">
        <div style="margin: auto;">
          <van-image
              class="vimage"
              round
              width="10rem"
              height="10rem"
              style="margin: auto"
              src="../vite.svg"
          />
          <div style="text-align: center;font-family: 华文楷体;margin-top: 1rem"></div>
        </div>
      </div>

      <div class="login_form"  v-if="login == true && register == false">
       <div style="display: flex;margin: auto;width: 90%;height: 70%">
         <van-form style="margin: auto"  v-model="loginForm" @submit="onSubmitLogin">
             <van-field
                 style="border-radius: 5px;border: #ad3a36 solid 1px;"
                 v-model="loginForm.username"
                 name="账号"
                 label="账号"
                 left-icon="manager"
                 placeholder="账号"
                 :rules="[{ required: true, message: '请填写账号' }]"
             />
             <van-field
                 v-model="loginForm.password"
                 style="border-radius: 5px;border: #ad3a36 solid 1px;margin-top: 1rem"
                 type="password"
                 name="密码"
                 label="密码"
                 left-icon="lock"
                 placeholder="密码"
                 :rules="[{ required: true, message: '请填写密码' }]"
             />
           <div style="margin-top: 2rem;">
             <van-button round block style="background-color: #ad3a36"native-type="submit" @click="">
               登录
             </van-button>
           </div>
         </van-form>
       </div>
      </div>
      <div style="width: 100%;" @click="collbackhome" v-if="login == true && register == false">
        <div style="text-align: center;text-decoration: #ad3a36 underline;color: #ad3a36">进行注册</div>
      </div >
      <div class="register_form" v-if="login == false && register == true">
        <div style="display: flex;margin: auto;width: 90%;height: 70%">
          <van-form  style="margin: auto"   v-model="registerForm" @submit="onSubmitRegister">
            <van-field
                style="border-radius: 5px;border: #ad3a36 solid 1px"
                v-model="registerForm.username"
                name="账号"
                label="账号"
                left-icon="manager"
                placeholder="账号"
                :rules="[{ required: true, message: '请填写账号' }]"
            />
            <van-field
                v-model="registerForm.password"
                style="border-radius: 5px;border: #ad3a36 solid 1px;margin-top: 1rem"
                type="password"
                name="密码"
                label="密码"
                left-icon="lock"
                placeholder="密码"
                :rules="[{ required: true, message: '请填写密码' }]"
            />
            <van-field
                v-model="registerForm.secondPass"
                style="border-radius: 5px;border: #ad3a36 solid 1px;margin-top: 1rem"
                type="password"
                name="确认密码"
                label="确认密码"
                placeholder="确认密码"
                :rules="[{ required: true, message: '请再次填写密码' }]"
            />
            <van-field
                v-model="registerForm.studentId"
                style="border-radius: 5px;border: #ad3a36 solid 1px;margin-top: 1rem"
                name="学号"
                label="学号"
                placeholder="学号"
                :rules="[{ required: true, message: '请填写学号' }]"
            />
                <van-field
                v-model="registerForm.phone"
                style="border-radius: 5px;border: #ad3a36 solid 1px;margin-top: 1rem"
                name="手机号"
                label="手机号"
                placeholder="手机号"
                :rules="[{ required: true, message: '请填写手机号' }]"
            />
            <van-field
              v-model="registerForm.className"
              style="border-radius: 5px;border: #ad3a36 solid 1px;margin-top: 1rem"
              name="班级"
              label="班级"
              placeholder="班级"
              :rules="[{ required: true, message: '请填写班级' }]"
          />
            <div style="margin-top: 2rem;">
              <van-button round block style="background-color: #ad3a36" type="primary" native-type="submit" @click="">
                注册
              </van-button>
            </div>
          </van-form>
        </div>
      </div>
      <div style="width: 100%;margin-top: 2rem" @click="goLogin" v-if="login == false && register == true">
        <div style="text-align: center;text-decoration: #ad3a36 underline;color: #ad3a36">前往登录页面</div>
      </div >
    </div>
  </div>
</template>

<script setup>
import {useRouter} from "vue-router";
import {reactive, ref} from "vue";
import instance from "../../api/request";
import useDataStore from "../../store/index.js";

const useDate = useDataStore();
const router = useRouter();
const login = ref(true)
const register = ref(false)

const loginForm = reactive({
  username: '',
  password: ''
})

const registerForm = reactive({
  username: '',
  password: '',
  studentId:'',
  phone:'',
  className:'',
  secondPass:''
})

const collbackhome = () => {
  login.value = false
  register.value = true
}

const itemImage = reactive({
  url: 'https://cn.bing.com/images/search?q=%E5%9B%BE%E7%89%87&FORM=IQFRBA&id=3818C0A36E353844880BB3B0E3AF1C80A1CA0FB1'
})

const goRegister = () => {
  login.value = false
  register.value = true
}

const goLogin = () => {
  login.value = true
  register.value = false
}

const onSubmitLogin = () => {
  instance.post('/login',loginForm).then(res =>{
    console.log(loginForm)
    if (res.data.code == '200'){
      useDate.token = res.data.authorization
      useDate.username = res.data.currentUser.username
      useDate.userId = res.data.currentUser.id
      useDate.phone = res.data.currentUser.phone
      useDate.avater = res.data.currentUser.avater
      useDate.studentId = res.data.currentUser.studentId
      useDate.roles = res.data.currentUser.roles
      useDate.perms = JSON.stringify(res.data.perms)
      router.push('/front')
      console.log(useDate)
    }
  })
}

const onSubmitRegister = () => {
  instance.post('/user/register',registerForm).then(res =>{
    console.log(registerForm)
    if (res.data.code == '200'){
      login.value = true
      register.value = false
    }
  })
}

</script>

<style scoped lang="scss">
#login_container{
  width: 100vw;
  height: 100vh;
  background-color: white;
  display: flex;

  .display_box{
    position: relative;
    width: 70%;
    height: 90%;
    margin: auto;

    /*开屏显示*/
    .login_item{
      width: 100%;
      height: 20%;

      margin-top: 10%;
      display: flex;
    }
    .display_middle{
      width: 100%;
      height: 30%;
      display: flex;
      .vimage{

      }
    }
    .login_button_box {
      width: 100%;
      height: 15%;
      display: flex;

      .login_button {
        background-color: #ad3a36;
        transform-style: preserve-3d;
        transition: 1s;
        &:hover{
          background-color: #96b5ec;
          color: white;
        }
      }
    }
    .register_button_box{
      position: absolute;
      width: 100%;
      height: 10%;
      top: auto;
      bottom: 5%;
      display: flex;
    }


    /*登录显示*/
    .login_form{
      width: 100%;
      height: 40%;
      display: flex;
    }

    /*注册*/
    .register_form{
      margin-top: 10px;
      width: 100%;
      display: flex;
    }
  }
}


</style>