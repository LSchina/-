<template>
  <div id="addon_container">
    <el-card style="width: 95%;height: 95%;background-color: white">
      <el-form label-position="top">
        <el-form-item label="社团名称">
          <el-input style="width: 500px" v-model="form.name" />
        </el-form-item>
        <el-form-item label="社团宣言">
          <el-input style="width: 500px" :rows="3"  v-model="form.remark" type="textarea"/>
        </el-form-item>
        <el-form-item label="上传图片">
          <el-upload
              class="avatar-uploader"
              action=""
              :file-list="fileList"
              :http-request="uploadIMage"
          >
            <img v-if="imageUrl" :src="imageUrl"   class="avatar" />
            <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
          </el-upload>
        </el-form-item>
        <el-form-item>
          <el-button @click="submit" style="width: 180px" type="primary">提交</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import {useRouter} from "vue-router";
import {Plus} from "@element-plus/icons-vue";
import {reactive, ref} from "vue";
import instance from "../../api/request.js";
import {ElMessage} from "element-plus";

const router = useRouter()

const imageUrl = ref('')

const fileList = ref([])

const form = reactive({
  name: '',
  remark: '',
  file: ''
})

const uploadIMage = (v) => {
  form.file = v.file
}

const submit = () => {
  console.log(form)
  let dateForm = new FormData()
  dateForm.append('file',form.file)
  dateForm.append('name',form.name)
  dateForm.append('remark',form.remark)
  let config = {
    headers: {
      "Content-Type": "multipart/form-data",
    }
  }
  instance.post('/admin/addon',dateForm,config).then( res =>{
    if (res.data.code == '200'){
      ElMessage({
        message: '添加成功',
        type: 'success',
      })
      router.push('/back/com')
    }else {
      ElMessage({
        message: res.data.msg,
        type: 'error',
      })
    }
  })
}

</script>

<style scoped>
#addon_container {
  width: 100%;
  height: 92vh;
  box-sizing: border-box;
  display: flex;
  justify-content: center;
  align-items: center;
}
.avatar-uploader{
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}

</style>