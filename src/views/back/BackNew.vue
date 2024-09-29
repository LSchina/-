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
                placeholder="请输入招新的标题"
                :suffix-icon="Search"
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="Submit" style="height: 40px;margin-left: 10px">搜索</el-button>
          </el-form-item>

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
        <el-table-column type="index" label="编号" width="100" />
        <el-table-column prop="communityName" label="社团名字" width="150" />
        <el-table-column prop="title" label="标题" width="150" />
        <el-table-column  label="图片" width="200">
          <template #default="scope">
            <el-image style="width: 80px;height: 50px" :src="scope.row.image"/>
          </template>
        </el-table-column>
        <el-table-column  label="发布时间" width="200">
          <template #default="scope">
            {{ formatDateTimeForHMS(scope.row.createTime) }}
          </template>
        </el-table-column>
        <el-table-column  label="内容" width="600">
          <template #default="scope">
            <el-tooltip placement="top" effect="light" show-after="300">
              <template #content><div style="color: #797878">{{ scope.row.comment }}</div></template>
              <div class="text-limit">{{ scope.row.comment }}</div>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" min-width="120">
          <template #default="scope">
            <el-button type="danger" size="small" @click="handleClick(scope.row.id)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script setup>
import {
  Search,Plus
} from '@element-plus/icons-vue'
import {onMounted, reactive, ref} from "vue";
import instance from "../../api/request.js";
import router from "../../router/index.js";
import {ElMessage} from "element-plus";

const form = reactive({
  title: '',
  pageNo: 1,
  pageSize: 7,
  type: 1
})



const Submit = () => {
  console.log(form)
  instance.post('/admin/notice',form).then( res => {
    if (res.data.code == '200'){
      tableData.value = res.data.page.list
    }
  })
}


const handleClick = (id) => {
  instance.delete('/admin/message/delete/' + id).then( res => {
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



const tableData = reactive([])

onMounted(() => {
  instance.post('/admin/notice',form).then( res => {
    console.log(res.data.page)
    if (res.data.code == '200'){
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