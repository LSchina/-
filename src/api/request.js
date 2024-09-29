import axios from "axios"
import qs from "qs"
import useDataStore from "../store/index.js";

const useData = useDataStore()
/**
 * 创建Axios对象
 */
const instance = axios.create({
    baseURL: "http://localhost:9090",
    timeout: 5000,//配置5s超时
})
/**
 * 响应失败处理器
 */
const errorHandle = (status, info) => {
    switch (status) {
        case 400:
            console.log("语义错误");
            break;
        case 401:
            console.log("服务器认证失败");
            break;
        case 403:
            console.log("服务器请求拒绝执行");
            break;
        case 404:
            console.log("请检查网路请求地址");
            break;
        case 500:
            console.log("服务器发生意外");
            break;
        case 502:
            console.log("服务器无响应");
            break;
        default:
            console.log(info);
            break;
    }
}
/**
 * 请求拦截器
 */
instance.interceptors.request.use(

    config => {
        if (config.method === 'post') {
            console.log(config.url)
            const strings = config.url.split('/');
            if (!strings.includes('avatar')
                &&
                !strings.includes('addon')
                &&
                !strings.includes('saveMe')
                &&
                !strings.includes('updateCom')
                &&
                !strings.includes('updateCarousel')
                &&
                !strings.includes('addActivity')) {
                // POST接收的网络请求参数需要进行格式转化
                config.data = qs.stringify(config.data)
            }
            console.log(!strings.includes('avatar'))
        }
        config.headers['token'] = useData.token
        return config
    },
    error => Promise.reject(error)
)
/**
 * 响应拦截器
 */
instance.interceptors.response.use(
    response => response.status === 200 ? Promise.resolve(response) : Promise.reject(response),
    error => {
        const {response} = error;
        if (response) {
            errorHandle(response.status, response.info)
        } else {
            console.log("网络请求失败");
        }
    }
)

export default instance