<template>
  <div id="community_container">
    <!--  header  -->
    <div style="width: 100%;height: 5%;background-color: white;display: flex;">
      <div style="position: absolute;height: 5%;width: 10%;display: flex;" @click="goHome">
        <van-icon size="1.5rem" style="margin: auto" name="arrow-left"/>
      </div>
      <div style="margin: auto;font-size: 1.2rem;font-family: 华文中宋">社团之家</div>
    </div>

    <div style="margin-top: 1rem">
      <van-cell-group inset>
        <van-field
            v-model="query.name"
            center
            clearable
            icon-prefix="search"
            placeholder="请输入社团的名称"
        >
          <template #button>
            <van-button size="small" type="primary" @click="queryCommunity">
              <van-icon size="1.3rem" name="search"/>
            </van-button>
          </template>
        </van-field>
      </van-cell-group>
    </div>

    <div style="width: 100%;height: 78%;display: flex">
      <ul style="margin: auto;width: 94%;height: 100%">
        <li v-for="item in communities.value" :key="item"
            style="background-color: white;padding: 1rem 1rem 1rem 1rem;box-sizing: border-box;width: 100%;height: 6.5rem;margin-top: 1rem;border-radius: 0.5rem ">
          <div @click="communityIntro(item.id)" style="width: 100%;height: 100%;display: flex">
            <div style="border-radius: 0.8rem;width: 40%;height: 100%;">
              <van-image
                  :radius="8"
                  :src="item.avater"
                  style="object-fit: cover;width: 100%;height: 100%;"
              />
            </div>
            <div style="width: 55%;margin-left: 5%">
              <div style="font-size: 1.3rem;margin-top: 0.5rem;letter-spacing: 2px">{{item.name}}</div>
              <div style="white-space: nowrap;overflow: hidden;text-overflow: ellipsis;max-width: 180px;font-size: 0.8rem;margin-top: 0.5rem;color: #747475;">
                {{item.remark}}
              </div>
            </div>
          </div>
        </li>
      </ul>
    </div>

    <div style="margin-top: 1rem;background-color: white">
      <van-pagination v-model="query.pageNo" :page-count="pageSize" :total-items="total" @change="queryCommunity">
        <template #prev-text>
          <van-icon size="1.5rem" name="arrow-left"/>
        </template>
        <template #next-text>
          <van-icon size="1.5rem" name="arrow"/>
        </template>
      </van-pagination>
    </div>

  </div>
  <van-dialog v-model:show="show" :title="community.name" show-cancel-button>
    <div style="width: 100%;height: 8rem;display: flex;margin-top: 1rem">
      <van-image
          style="width: 80%;height: 100%;margin: auto"
          :src="community.avater"
      />
    </div>
    <div style="width: 100%;height: 2rem;display: flex;margin-top: 1rem">
      <div style="margin: auto;width: 80%;height: 100%;display: flex;justify-content: space-between">
        <div style=";color: #646363">社团人数:</div>
        <div>{{community.number}}</div>
      </div>
    </div>
    <div style="width: 100%;height: 15rem;display: flex;margin-top: 0.5rem">
      <div style="margin: auto;width: 80%;height: 100%;">
        <div style="color: #646363">社团简介:</div>
        <div style="margin-top: 1rem;letter-spacing: 0.2rem;color: #646363">{{community.remark}}</div>
      </div>
    </div>


  </van-dialog>

</template>

<script setup>
import {useRouter} from "vue-router";
import {onMounted, reactive, ref} from "vue";
import instance from "../../api/request.js";

const show = ref(false)

const router = useRouter()
const pageSize = ref(0)

const query = reactive({
  name:'',
  pageSize:5,
  pageNo: 1
})

const communities = reactive([])

const total = ref(0)

const community = reactive({
  name:'',
  number:'',
  remark:'',
  avater:''
})

const queryCommunity = () => {
  instance.post('/communities/pageList',query).then(res =>{
    console.log(query)
    if (res.data.code == '200'){
      communities.value = res.data.page.list
      total.value = res.data.page.total
      pageSize.value = res.data.page.pages
      console.log(communities.value)
    }
  })
}

const communityIntro = (id) => {
instance.get('/communities/intro/' + id).then(res =>{
  if (res.data.code == '200'){
    community.name = res.data.community.name
    community.number = res.data.community.number
    community.avater = res.data.community.avater
    community.remark = res.data.community.remark
    console.log(community.name.value)
    show.value = true
  }
})
}

const goHome = () => {
  router.push('/front/index')
}

onMounted(() =>{
  instance.post('/communities/pageList',query).then(res =>{
    console.log(query)
    if (res.data.code == '200'){
      communities.value = res.data.page.list
      console.log(res.data.page)
      total.value = res.data.page.total
      pageSize.value = res.data.page.pages
      console.log(communities.value)
    }
  })
})


</script>

<style scoped>
#community_container{
  width: 100%;
  height: 92vh;
  background-color: #efefef;
}
</style>