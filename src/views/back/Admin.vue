<template>
  <div id="com_container">
    <div style="width: 100%;height: 8vh;display: flex;justify-content: space-between;flex-wrap: wrap;align-items: center">
      <div>
        <el-form style="display: flex;">
          <el-button @click="addAdmin" type="primary" :icon="Plus" style="height: 40px;width: 100px;margin-left: 10px">添加管理员</el-button>
        </el-form>
      </div>
      <el-pagination
          size="small"
          background
          layout="prev, pager, next"
          :total="10"
          :current-page="form.pageNo"
          :page-size="form.pageSize"
          @current-change="Submit"
          class="mt-4"
      />
    </div>
    <div style="width: 100%;height: 80vh;margin-top: 2vh;">
      <el-table :row-style="{'height':'65px'}" :data="tableData.value" style="width: 100%">
        <el-table-column type="index" label="编号" width="80" />
        <el-table-column prop="username" label="账户" width="150" />
        <el-table-column prop="phone" label="手机号" width="150" />
        <el-table-column  label="创建时间" width="200">
          <template #default="scope">
            {{ formatDateTimeForHMS(scope.row.createTime) }}
          </template>
        </el-table-column>
        <el-table-column  label="身份" width="120">
          <template #default="scope">
            <div style="border: 1px solid red;text-align: center;border-radius: 5px;background-color: red;color: white;">超级管理员</div>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" min-width="150">
          <template #default="scope">
            <el-button v-if="scope.row.id == useData.userId" type="danger" size="small" @click="handleClick">
              修改
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
import {onMounted, reactive} from "vue";
import instance from "../../api/request.js";
import useDataStore from "../../store/index.js";
import {useRouter} from "vue-router";

const router = useRouter()

const useData = useDataStore()

const form = reactive({
  pageNo: 1,
  pageSize: 7,
})

const Submit = () => {
  instance.post('/admin/page',form).then( res => {
    if (res.data.code == '200'){
      tableData.value = res.data.page.list
      console.log(res.data.page)
      console.log(tableData.value)
    }
  })
}

const handleClick = () => {

}

const addAdmin = () => {
  router.push('/back/addAdmin')
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
  instance.post('/admin/page',form).then( res => {
    if (res.data.code == '200'){
      tableData.value = res.data.page.list
      console.log(res.data.page)
      console.log(tableData.value)
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
  height: 90vh;
}
</style>