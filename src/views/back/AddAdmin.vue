<template>
  <div id="addon_container">
    <el-card style="width: 95%;height: 95%;background-color: white">
      <el-form label-position="top">
        <el-form-item label="管理员名称">
          <el-input style="width: 500px" v-model="form.username" />
        </el-form-item>
        <el-form-item label="管理员联系方式">
          <el-input style="width: 500px" :rows="3"  v-model="form.phone"/>
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


const form = reactive({
  username: '',
  phone: ''
})

const submit = () => {
  instance.post('/admin/addAdmin',form).then( res => {
    if (res.data.code == '200'){
      ElMessage.success('添加成功')
      router.push('/back/admin')
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