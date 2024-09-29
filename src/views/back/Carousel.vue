<template>
  <div class="main-border">
    <el-menu default-active="1" class="el-menu-demo" mode="horizontal" @select="">
      <el-menu-item @click="watchLogin" index="1">联谊功能海报</el-menu-item>
      <el-menu-item @click="watchIndex" index="2">主页轮播图</el-menu-item>
      <el-button @click="addCarousel" style="margin-left: 880px;margin-top: 10px" type="primary">添加轮播图</el-button>
    </el-menu>

    <el-table v-show="loginCarousel"
              :data="loginData"
              stripe
              style="width: 100%;color: #5a5c61;">
      <el-table-column
          type="index"
          label="编号"
          show-overflow-tooltip
          min-width="150"
          width="100">
      </el-table-column>
      <el-table-column  label="图片" width="200">
        <template #default="scope">
          <el-image style="width: 80px;height: 50px" :src="scope.row.image"/>
        </template>
      </el-table-column>
      <el-table-column  label="上传时间" width="200">
        <template #default="scope">
          {{ formatDateTimeForHMS(scope.row.createTime) }}
        </template>
      </el-table-column>
      <el-table-column  label="内容" width="600">
        <template #default="scope">
          <el-tooltip placement="top" effect="light" show-after="300">
            <template #content><a style="color: #797878">{{ scope.row.url }}</a></template>
            <a class="text-limit">{{ scope.row.url }}</a>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" min-width="120">
        <template #default="scope">
          <el-button type="danger" size="small" @click="handleClick(scope.row.id)">
            删除
          </el-button>
          <el-button type="primary" size="small" @click="editCom(scope.row.id)">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-table v-show="indexCarousel"
              :data="indexData"
              stripe
              style="width: 100%;">
      <el-table-column
          type="index"
          label="编号"
          show-overflow-tooltip
          min-width="150"
          width="100">
      </el-table-column>
      <el-table-column  label="图片" width="200">
        <template #default="scope">
          <el-image style="width: 80px;height: 50px" :src="scope.row.image"/>
        </template>
      </el-table-column>
      <el-table-column  label="上传时间" width="200">
        <template #default="scope">
          {{ formatDateTimeForHMS(scope.row.createTime) }}
        </template>
      </el-table-column>
      <el-table-column  label="内容" width="600">
        <template #default="scope">
          <el-tooltip placement="top" effect="light" show-after="300">
            <template #content><a style="color: #797878">{{ scope.row.url }}</a></template>
            <a class="text-limit">{{ scope.row.url }}</a>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" min-width="120">
        <template #default="scope">
          <el-button type="danger" size="small" @click="handleClick(scope.row.id)">
            删除
          </el-button>
          <el-button type="primary" size="small" @click="editCom(scope.row.id)">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
  <el-dialog v-model="dialogFormVisible" title="修改图片信息" width="500">
    <el-form :model="carousel">
      <el-form-item label="目标网站" :label-width="100">
        <el-input v-model="carousel.url" autocomplete="off" />
      </el-form-item>
      <el-form-item label="图片" :label-width="100">
        <el-upload
            class="avatar-uploader"
            action=""
            :file-list="fileList"
            :http-request="uploadIMage"
        >
          <el-image :src="carousel.image" style="width: 200px;height: 120px"/>
        </el-upload>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="editUpload">
          提交
        </el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup>
import {useRouter} from "vue-router";
import {onMounted, reactive, ref} from "vue";
import instance from "../../api/request.js";
import {ElMessage} from "element-plus";

const router = useRouter()

const fileList = ref([])

const uploadIMage = (v) => {
  carousel.file  = v.file
}

const carousel = reactive({
  id: '',
  url:'',
  image:'',
})

const editUpload = (id) => {
  let formData = new FormData();
  formData.append('file', carousel.file)
  formData.append('id', carousel.id)
  formData.append('url', carousel.url)
  let config = {
    headers: {
      "Content-Type": "multipart/form-data",
    }
  }
  instance.post('/admin/updateCarousel',formData,config).then( res => {
    if (res.data.code == '200'){
      dialogFormVisible.value = false
      watchIndex()
    }else {
      ElMessage({
        message: res.data.msg,
        type: 'error'
      })
    }
  })
}

const loginData = ref([])

const loginCarousel = ref(true)

const indexCarousel = ref(false)

const indexData = ref([])

const dialogFormVisible = ref(false)

const editCom = (id) => {
  dialogFormVisible.value = true
  instance.get('/admin/carousel/' + id).then( res => {
    if (res.data.code == '200'){
      carousel.url = res.data.carousel.url
      carousel.image = res.data.carousel.image
      carousel.id = res.data.carousel.id
    }
  })
}

const watchLogin = () => {
  loginCarousel.value = true
  indexCarousel.value = false
  instance.post('/admin/carousel', {type: 0}).then( res => {
    loginData.value = res.data.list
  })
}

const watchIndex = () => {
  loginCarousel.value = false
  indexCarousel.value = true
  instance.post('/admin/carousel', {type: 1}).then( res => {
    console.log(res.data.list)
    indexData.value = res.data.list
  })
}


const addCarousel = () => {
  router.push('/back/addCarousel')
}

const handleClick = (id) => {
  instance.delete('/admin/carousel/delete/' + id).then( res => {
    if (res.data.code == '200'){
      watchIndex()
    }else {
      ElMessage({
        message: res.data.msg,
        type: 'error'
      })
    }
  })
}

onMounted(() => {
  watchLogin()
})

function formatDateTimeForHMS(obj) {
  if (obj == null) {
    return null
  }
  let date = new Date(obj);
  let y = 1900 + date.getYear();
  let m = "0" + (date.getMonth() + 1);
  let d = "0" + date.getDate();
  let h = "0" + date.getHours();
  let mm = "0" + date.getMinutes();
  let s = date.getSeconds();
  return y + "-" + m.substring(m.length - 2, m.length) + "-" + d.substring(d.length - 2, d.length) + " " + h.substring(h.length - 2, h.length) + ":" + mm.substring(mm.length - 2, mm.length);
}

</script>

<style scoped>
.main-border{
  width: 100%;
  height: 88vh;
  background-color: #FFF;
  padding: 10px 30px;
  box-shadow: 0 1px 15px -6px rgba(0,0,0,.5);
  border-radius: 5px;
}
.block {
  display: flex;
  justify-content:center;
  padding-top: 15px;
  padding-bottom: 10px;
  width: 100%;
}
.addAdminButton{
  display:flex;
  justify-content: flex-end;
  align-items: center;
  height: 60px;
  outline: none;
}
.text-limit {
  width: 500px; /* 这个宽度应该根据你的字体大小和字符宽度来调整 */
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
</style>