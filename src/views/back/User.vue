<template>
  <div id="com_container">
    <div style="width: 100%;height: 8vh;display: flex;justify-content: space-between;flex-wrap: wrap;align-items: center">
      <div>
        <el-form style="display: flex;">
          <el-form-item>
            <el-input
                v-model="form.username"
                style="width: 240px;height: 40px"
                size="large"
                placeholder="请输入账户"
                :suffix-icon="Search"
            />
          </el-form-item>
          <el-form-item>
            <el-input
                v-model="form.className"
                style="width: 240px;height: 40px;margin-left: 10px"
                size="large"
                placeholder="请输入班级"
                :suffix-icon="Search"
            />
          </el-form-item>
          <el-form-item>
            <el-input
                v-model="form.studentId"
                style="width: 240px;height: 40px;margin-left: 10px"
                size="large"
                placeholder="请输入学号"
                :suffix-icon="Search"
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="Submit" style="height: 40px;margin-left: 10px">搜索</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="rload" style="height: 40px;margin-left: 10px">重置</el-button>
          </el-form-item>
        </el-form>
      </div>
      <el-pagination
          size="small"
          background
          layout="prev, pager, next"
          :total="30"
          v-model:current-page="form.pageNo"
          v-model:page-size="form.pageSize"
          @current-change="Submit"
          class="mt-4"
      />
    </div>
    <div style="width: 100%;height: 80vh;margin-top: 2vh;">
      <el-table :row-style="{'height':'65px'}" :data="tableData.value" style="width: 100%">
        <el-table-column type="index" label="编号" width="80" />
        <el-table-column prop="username" label="账户" width="120" />
        <el-table-column  label="头像" width="150">
          <template #default="scope">
            <el-image style="width: 80px;height: 50px" :src="scope.row.avater"/>
          </template>
        </el-table-column>
        <el-table-column prop="className" label="班级" width="150" />
        <el-table-column prop="studentId" label="学号" width="150" />
        <el-table-column prop="phone" label="手机号" width="150" />
        <el-table-column   prop="remark" label="简介" width="600" />
        <el-table-column fixed="right" label="操作" min-width="170">
          <template #default="scope">
            <el-button type="danger" size="small" @click="deleteUser(scope.row.id)">
              删除
            </el-button>
            <el-button  type="primary" @click="loadPass(scope.row.id)" size="small">重置密码</el-button>
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
import {onMounted, reactive} from "vue";
import instance from "../../api/request.js";


const form = reactive({
  username: '',
  className:'',
  studentId:'',
  pageNo: 1,
  pageSize: 7,
})

const Submit = () => {
  instance.post('/admin/user',form).then( res => {
    if (res.data.code == '200'){
      tableData.value = res.data.page.list
      console.log(res.data.page)
      console.log(tableData.value)
    }
  })
}

const deleteUser = (id) => {
  instance.delete('/admin/delete/' + id).then( res => {
    if (res.data.code == '200'){
      Submit()
    }
  })
}
const loadPass = (id) => {
  instance.post('/admin/load/' + id).then( res => {
    if (res.data.code == '200'){
      Submit()
    }
  })
}

const tableData = reactive([
  {
    date: '2016-05-03',
    name: 'Tom',
    state: 'California',
    city: 'Los Angeles',
    address: 'No. 189, Grove St, Los Angeles',
    zip: 'CA 90036',
    tag: 'Home',
  },
  {
    date: '2016-05-02',
    name: 'Tom',
    state: 'California',
    city: 'Los Angeles',
    address: 'No. 189, Grove St, Los Angeles',
    zip: 'CA 90036',
    tag: 'Office',
  },
  {
    date: '2016-05-04',
    name: 'Tom',
    state: 'California',
    city: 'Los Angeles',
    address: 'No. 189, Grove St, Los Angeles',
    zip: 'CA 90036',
    tag: 'Home',
  },
  {
    date: '2016-05-04',
    name: 'Tom',
    state: 'California',
    city: 'Los Angeles',
    address: 'No. 189, Grove St, Los Angeles',
    zip: 'CA 90036',
    tag: 'Home',
  },
  {
    date: '2016-05-04',
    name: 'Tom',
    state: 'California',
    city: 'Los Angeles',
    address: 'No. 189, Grove St, Los Angeles',
    zip: 'CA 90036',
    tag: 'Home',
  },
  {
    date: '2016-05-04',
    name: 'Tom',
    state: 'California',
    city: 'Los Angeles',
    address: 'No. 189, Grove St, Los Angeles',
    zip: 'CA 90036',
    tag: 'Home',
  },
  {
    date: '2016-05-01',
    name: 'Tom',
    state: 'California',
    city: 'Los Angeles',
    address: 'No. 189, Grove St, Los Angeles',
    zip: 'CA 90036',
    tag: 'Office',
  },
])

onMounted(() => {
  instance.post('/admin/user',form).then( res => {
    if (res.data.code == '200'){
      tableData.value = res.data.page.list
      console.log(res.data.page)
      console.log(tableData.value)
    }
  })
})

const rload = () => {
  form.username = ''
  form.className = ''
  form.studentId = ''
  instance.post('/admin/user',form).then( res => {
    if (res.data.code == '200'){
      tableData.value = res.data.page.list
      console.log(res.data.page)
      console.log(tableData.value)
    }
  })
}
</script>

<style scoped>
#com_container{
  height: 90vh;
}
</style>