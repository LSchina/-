<template>
  <div id="index_container">
    <!--  header  -->
    <div style="width: 100%;height: 8%;background-color: white;display: flex;">
      <div style="margin: auto;font-size: 1.2rem;font-family: 华文中宋">社团服务平台</div>
    </div>

    <div>
    </div>
    <van-swipe :autoplay="3000" :height="250"  lazy-render>
      <van-swipe-item  v-for="image in images.value" :key="image">
        <div style="width: 100%;height: 100%;">
          <van-image style="width: 100%;height: 100%" fit="fill" :src="image.image"/>
        </div>
      </van-swipe-item>
    </van-swipe>

    <div style="height: 15%;width: 100%;margin-top: 1.5%;display: flex">
      <div style="width: 95%;height: 90%;margin: auto;display: flex;border-radius: 0.5rem;justify-content: space-between;background-color: white;padding: 0.5rem 0.5rem 0.5rem 0.5rem">
        <div style="height: 100%;width: 28%;" @click="goCommunity">
          <div style="display: flex;width: 100%;height: 70%">
            <div style="background-color: #dadada;width: 70%;height: 90%;display: flex;margin: auto;border-radius: 1rem">
              <van-icon color="black" size="2rem" style="margin: auto" name="friends-o"/>
            </div>
          </div>
          <div style="width: 100%;text-align: center;margin-top: 0.5rem">社团之家</div>
        </div>
        <div style="height: 100%;width: 28%;" @click="goNews">
          <div style="display: flex;width: 100%;height: 70%">
            <div style="background-color: #dadada;width: 70%;height: 90%;display: flex;margin: auto;border-radius: 1rem">
              <van-icon size="2rem" style="margin: auto" name="fire-o"/>
            </div>
          </div>
          <div style="width: 100%;text-align: center;margin-top: 0.5rem">活动招新</div>
        </div>
        <div style="height: 100%;width: 28%;" @click="goComList">
          <div style="display: flex;width: 100%;height: 70%">
            <div style="background-color: #dadada;width: 70%;height: 90%;display: flex;margin: auto;border-radius: 1rem">
              <van-icon size="2rem" style="margin: auto" name="comment-o"/>
            </div>
          </div>
          <div style="width: 100%;text-align: center;margin-top: 0.5rem">社团公告</div>
        </div>
      </div>
    </div>

    <div style="width: 100%;height: 40%;display: flex;box-sizing: border-box;margin-top: 1.5%">
      <div style="width: 100%;margin: auto;height: 100%;">
        <div style="box-sizing: border-box;padding: 1rem 1rem 1rem 1rem;;width: 100%;height: 45%;background-color: white;border-radius: 0.5rem;margin-top: 3%" v-for="item in newsRecList.value" key="item">
          <div style="width: 100%;height: 100%;display: flex;">
            <div style="border-radius: 0.8rem;width: 40%;height: 100%;">
              <van-image
                  :radius="12"
                  :src="item.avater"
                  style="object-fit: cover;width: 100%;height: 100%;"
              />
            </div>
            <div style="width: 58%;height: 100%;margin-left: 2%;margin-top: 0.6rem">
              <div style="width: 100%;height: 30%;text-align: left;letter-spacing: 0.15rem;font-size: 1.15rem;font-weight: bold;font-family: 'Microsoft YaHei'">
                {{item.communityName}}
              </div>
              <div style="line-height: 1.5rem;font-size: 0.85rem;letter-spacing: 0.1rem;font-family: 'Microsoft YaHei';color: #646363;">{{item.comment}}</div>
              <div style="">
                <div style="font-family: 'Bodoni MT Poster Compressed';font-size: 0.85rem;color: #646363;text-align: right;margin-top: 0.5rem">
                  {{ formatDateTimeForHMS(item.createTime) }}
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import useDataStore from "../../store/index.js";
import {onMounted, reactive} from "vue";
import instance from "../../api/request.js";
import {useRouter} from "vue-router";

const router = useRouter()
const useDate = useDataStore()

const newsRecList = reactive([])

const images = reactive([]);

const getCarousel = () => {
  instance.post('/carousel/get',{ type:1 }).then(res => {
    if (res.data.code == '200'){
      images.value = res.data.list
    }
  })
}

onMounted(() =>{
  instance.get('/messages/newsRec').then( res =>{
    if (res.data.code == '200'){
      newsRecList.value = res.data.newsR
    }
  })
  getCarousel()
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

const goCommunity = () => {
  router.push('/front/community')
}

const goNews = () => {
  router.push('/front/newList')
}

const goComList = () => {
  router.push('/front/noticeList')
}

</script>

<style scoped lang="scss">
#index_container{
  width: 100%;
  height: 92vh;
}
</style>