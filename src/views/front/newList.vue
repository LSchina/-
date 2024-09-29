<template>
  <div id="newsList_container">
    <!--  header  -->
    <div style="width: 100%;height: 5%;background-color: white;display: flex;">
      <div style="position: absolute;height: 5%;width: 10%;display: flex;" @click="goHome">
        <van-icon size="1.5rem" style="margin: auto" name="arrow-left"/>
      </div>
      <div style="margin: auto;font-size: 1.2rem;font-family: 华文中宋">招新新闻</div>
    </div>

    <div style="margin-top: 1rem">
      <van-cell-group inset>
        <van-field
            v-model="query.name"
            center
            clearable
            icon-prefix="search"
            placeholder="请输入社团名称"
        >
          <template #button>
            <van-button size="small" type="primary" @click="queryNews">
              <van-icon size="1.3rem" name="search"/>
            </van-button>
          </template>
        </van-field>
      </van-cell-group>
    </div>

    <div style="width: 100%;height: 80%;display: flex">
      <ul style="margin: auto;width: 94%;height: 100%">
        <li v-for="item in tableDate.value" :key="item"
            style="background-color: white;padding: 0.2rem 1rem 1rem 1rem;box-sizing: border-box;width: 100%;height: 7rem;margin-top: 1rem;border-radius: 0.5rem ">
          <div style="width: 100%;height: 100%">
            <div style="font-size: 1.3rem;font-family: 黑体;margin-top: 1rem">{{item.title}}</div>
            <div @click="goIntro(item.id)"  style="font-size: 0.8rem;margin-top: 0.5rem;color: #0a5bec;text-decoration: underline">
              {{item.comment}}
            </div>
            <div style="">
              <div style="font-family: 'Bodoni MT Poster Compressed';font-size: 0.85rem;color: #646363;text-align: right;margin-top: 0.5rem">
                {{ formatDateTimeForHMS(item.createTime) }}
              </div>
            </div>
          </div>
        </li>
      </ul>
    </div>

    <div style="margin-top: 0rem">
      <van-pagination v-model="query.pageNo" :total-items="total" @change="queryNews">
        <template #prev-text>
          <van-icon size="1.5rem" name="arrow-left"/>
        </template>
        <template #next-text>
          <van-icon size="1.5rem" name="arrow"/>
        </template>
      </van-pagination>
    </div>

  </div>

</template>

<script setup>
import {useRouter} from "vue-router";
import {onMounted, reactive, ref} from "vue";
import instance from "../../api/request.js";

const show = ref(false)

const router = useRouter()

const query = reactive({
  title:'',
  pageSize:5,
  pageNo: 1
})

const tableDate = reactive([])

const total = ref(0)


const queryNews = () => {
  instance.post('/messages/newList',query).then(res =>{
    if (res.data.code == '200'){
      tableDate.value = res.data.page.list
      total.value = res.data.page.total
    }
  })
}

onMounted(() =>{
  instance.post('/messages/newList',query).then(res =>{
    if (res.data.code == '200'){
      tableDate.value = res.data.page.list
      total.value = res.data.page.total
    }
  })
})

const goHome = () => {
  router.push('/front/index')
}


const goIntro = (id) => {
  router.push({ path: '/front/newIntro', query: { id: id }})
}

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
#newsList_container{
  width: 100%;
  height: 92vh;
  background-color: #efefef;
}
</style>