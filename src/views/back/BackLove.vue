<template>
  <div id="com_container">
    <div style="width: 100%;height: 8vh;display: flex;justify-content: space-between;flex-wrap: wrap;align-items: center">
      <div>
        <el-form style="display: flex;">
          <el-form-item>
            <el-input
                v-model="form.title"
                style="width: 240px;height: 40px"
                size="large"
                placeholder="请输入活动的名称"
                :suffix-icon="Search"
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="Submit" style="height: 40px;margin-left: 10px">搜索</el-button>
          </el-form-item>
          <el-button type="primary" @click="AddLove" :icon="Plus" style="height: 40px;width: 100px;margin-left: 10px">创建活动</el-button>

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
            <el-image style="width: 80px;height: 50px" :src="scope.row.image"/>
          </template>
        </el-table-column>
        <el-table-column prop="title" label="名称" width="150" />
        <el-table-column prop="num" label="已参与人数" width="120" />
        <el-table-column  label="发布时间" width="200">
          <template #default="scope">
            {{ formatDateTimeForHMS(scope.row.createTime) }}
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" min-width="120">
          <template #default="scope">
            <el-button type="danger" size="small" @click="handleClick(scope.row.id)">
              删除
            </el-button>
            <el-button type="primary" size="small" @click="handleLook(scope.row.id)">
              点击查看参加人员
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
  <el-dialog v-model="dialogTableVisible" title="该活动已参加人数" width="600">
    <el-table :data="gridData.value">
      <el-table-column property="username" label="用户名" width="150" />
      <el-table-column property="className" label="班级" width="200" />
      <el-table-column property="phone" label="手机号" />
    </el-table>
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

const dialogTableVisible = ref(false)

const gridData = reactive([])

const form = reactive({
  title: '',
  pageNo: 1,
  pageSize: 7,
})

const Submit = () => {
  console.log(form)
  instance.post('/admin/activity',form).then( res => {
    if (res.data.code == '200'){
      tableData.value = res.data.page.list
    }
  })
}

const AddLove = () => {
  router.push('/back/addLove')
}

const handleClick = (id) => {
  instance.delete('/admin/activity/delete/' + id).then( res => {
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

const handleLook = (id) => {
  dialogTableVisible.value = true
  instance.post('/admin/activity/' + id).then( res => {
    if (res.data.code == '200'){
      console.log(res.data)
      gridData.value = res.data.list
    }
  })
}

const tableData = reactive([])

onMounted(() => {
  instance.post('/admin/activity',form).then( res => {
    if (res.data.code == '200'){
      console.log(res.data.page.list)
      tableData.value = res.data.page.list
    }
  })
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