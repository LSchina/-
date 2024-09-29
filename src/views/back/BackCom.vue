<template>
  <div id="com_container">
    <div style="width: 100%;height: 8vh;display: flex;justify-content: space-between;flex-wrap: wrap;align-items: center">
      <div>
      <el-form style="display: flex;">
        <el-form-item>
          <el-input
              v-model="form.name"
              style="width: 240px;height: 40px"
              size="large"
              placeholder="请输入社团的名称"
              :suffix-icon="Search"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="Submit" style="height: 40px;margin-left: 10px">搜索</el-button>
        </el-form-item>
        <el-button type="primary" @click="AddCom" :icon="Plus" style="height: 40px;width: 100px;margin-left: 10px">创建社团</el-button>

      </el-form>
      </div>
      <el-pagination
          size="small"
          background
          layout="prev, pager, next"
          :total="13"
          v-model:current-page="form.pageNo"
          v-model:page-size="form.pageSize"
          @current-change="Submit"
          class="mt-4"
      />
    </div>
    <div style="width: 100%;height: 80vh;margin-top: 2vh;">
      <el-table :row-style="{'height':'65px'}" :data="tableData.value" style="width: 100%">
        <el-table-column type="index" label="编号" width="80" />
        <el-table-column  label="图片" width="150">
          <template #default="scope">
            <el-image style="width: 80px;height: 50px" :src="scope.row.avater"/>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="名称" width="150" />
        <el-table-column prop="number" label="人数" width="120" />
        <el-table-column  label="社团宣言" width="600">
          <template #default="scope">
            <el-tooltip placement="top" effect="light" show-after="300">
              <template #content><div style="color: #797878">{{ scope.row.remark }}</div></template>
              <div class="text-limit">{{ scope.row.remark }}</div>
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
  </div>
  <el-dialog v-model="dialogFormVisible" title="修改社团" width="500">
    <el-form :model="comForm">
      <el-form-item label="社团名称" :label-width="100">
        <el-input v-model="comForm.name" autocomplete="off" />
      </el-form-item>
      <el-form-item label="社团宣言" :label-width="100">
        <el-input type="textarea" v-model="comForm.remark" autocomplete="off" />
      </el-form-item>
      <el-form-item label="图片" :label-width="100">
        <el-upload
            class="avatar-uploader"
            action=""
            :file-list="fileList"
            :http-request="uploadIMage"
        >
          <el-image :src="comForm.avater" style="width: 200px;height: 120px"/>
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
import {
  Search,Plus
} from '@element-plus/icons-vue'
import {onMounted, reactive, ref} from "vue";
import instance from "../../api/request.js";
import router from "../../router/index.js";
import {ElMessage} from "element-plus";

const dialogFormVisible = ref(false)

const form = reactive({
  name: '',
  pageNo: 1,
  pageSize: 7,
})

const fileList = ref([])

const comForm = reactive({
  id: '',
  name: '',
  remark: '',
  avater: ''
})

const uploadIMage = (v) => {
  comForm.avater = v.file
}

const Submit = () => {
  console.log(form)
  instance.post('/admin/com',form).then( res => {
    if (res.data.code == '200'){
      tableData.value = res.data.page.list
    }
  })
}

const AddCom = () => {
  router.push('/back/addon')
}

const handleClick = (id) => {
  instance.delete('/admin/deletion/' + id).then( res => {
    if (res.data.code == '200'){
      Submit()
    }else {
      ElMessage({
        message: res.data.msg,
        type: 'error'
      })
    }
  })
}

const editCom = (id) => {
  dialogFormVisible.value = true
  instance.get('/admin/com/' + id).then( res => {
    if (res.data.code == '200'){
      console.log(res.data.com)
      comForm.id = res.data.com.id
      comForm.name = res.data.com.name
      comForm.remark = res.data.com.remark
      comForm.avater = res.data.com.avater
    }
  })
}

const editUpload = () => {
  dialogFormVisible.value = false
  let formData = new FormData();
  formData.append('file', comForm.avater)
  formData.append('id',comForm.id)
  formData.append('name', comForm.name)
  formData.append('remark', comForm.remark)
  let config = {
    headers: {
      "Content-Type": "multipart/form-data",
    }
  }
  console.log(comForm.avater)
  instance.post('/admin/updateCom',formData,config).then( res => {
    if (res.data.code == '200'){
      Submit()
      fileList.value = []
    }
  })
}

const tableData = reactive([])

onMounted(() => {
  instance.post('/admin/com',form).then( res => {
    if (res.data.code == '200'){
      tableData.value = res.data.page.list
    }
  })
})

</script>

<style scoped>
#com_container{
  height: 100%;
}
.text-limit {
   width: 500px; /* 这个宽度应该根据你的字体大小和字符宽度来调整 */
   overflow: hidden;
   text-overflow: ellipsis;
   white-space: nowrap;
 }
</style>