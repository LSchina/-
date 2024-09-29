import { createApp } from 'vue'
import App from './App.vue'
// 导入router和store
import router from './router/index'

//导入ElementPlus
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

import {createPinia} from 'pinia'
import presist from 'pinia-plugin-persistedstate'


const pinia = createPinia();
const app = createApp(App)
pinia.use(presist)
app.use(pinia)
app.use(router)
app.use(ElementPlus)
app.mount('#app')