<template>
  <div id="notice_container">
    <!--   10 -->
    <div style="width: 100%;height: 10%;background-color: white;display: flex">
      <div style="width: 15%;height: 100%;display: flex;z-index: 100" @click="goMycom">
        <van-icon style="margin: auto" size="1.8rem" color="#3B3B3BFF" name="arrow-left" />
      </div>
      <div style="width: 100%;height: 10%;display: flex;position: absolute">
        <div style="margin: auto;letter-spacing: 0.3rem;font-size: 1.25rem;font-weight: bolder;font-family: 华文彩云;color: #2a2828">
          发布招新
        </div>
      </div>
    </div>
    <div style="letter-spacing: 0.2rem;font-size: 1.1rem;box-sizing: border-box;padding: 0.5rem 1.5rem 0.5rem 1.5rem;width: 100%;height: 10%;background-color: white;margin-top: 1rem;display: flex;justify-content: center;align-items: center">
      发布招新信息，为您的社团增添新的力量
    </div>
    <div style="width: 100%;height: 60%;padding: 0.5rem 0.5rem 0.5rem 0.5rem;box-sizing: border-box">
      <div style="width: 100%;height: 100%;">
        <van-form @submit="onSubmit">
          <van-field
              v-model="noticeForm.title"
              name="标题"
              label="标题"
              placeholder="标题"
          />
          <van-field
              style="margin-top: 0.2rem"
              v-model="noticeForm.comment"
              type="textarea"
              name="内容"
              label="内容"
              placeholder="内容"
          />
          <van-uploader style="margin-top: 0.5rem;"  :after-read="afterRead" />


          <div style="margin: 16px;">
            <van-button round block type="primary" native-type="submit">
              提交
            </van-button>
          </div>
        </van-form>

      </div>
    </div>
  </div>
  <van-toast v-model:show="show" style="padding: 5rem">
    <template #message>
      操作成功
    </template>
  </van-toast>

</template>

<script setup>
import {useRouter} from "vue-router";
import instance from "../../api/request.js";
import {reactive, ref} from "vue";

const router = useRouter()

const show = ref(false)

const fileList = ref([])

const noticeForm = reactive({
  title:'',
  comment:'',
  imageurl:''
})

const onSubmit = () => {
  instance.post('/messages/findNew',noticeForm).then(res =>{
    if (res.data.code == '200'){
      show.value = true
      noticeForm.comment = ''
      noticeForm.title = ''
    }
  })
}

const afterRead = (file) => {
  /*  // 使用 FormData 来包装文件
    const formData = new FormData();
    formData.append('file', file.file);
    let config = {
      headers: { //添加请求头
        "Content-Type": "multipart/form-data"
      }
    };
    instance.post('/file/upload',formData,config).then(res =>{
      if (res.data.code == '200'){
        console.log(res.data)
      }
    })*/
  console.log(file)
  const formData = new FormData();
  formData.append('file', file.file);
  console.log(formData)
}

const goMycom = () => {
  router.push('/front/mycom')
}

</script>

<style scoped>
#notice_container{
  width: 100%;
  height: 92vh;
  background-color: #efefef;
}
</style>