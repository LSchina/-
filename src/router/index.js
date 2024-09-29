import { createRouter, createWebHashHistory } from 'vue-router'
const router = createRouter({
    // 指定路由模式
    history: createWebHashHistory(),
    // 路由地址
    routes: [
        {
            /*最常见404匹配规则：没有找到对应路径地址 */
            path: "/:pathMath(.*)*",
            name: "notfound",
            component: () => import("../views/404/404.vue"),
        },
        {
            path: "/frontLogin",
            component: () => import("../views/login/backLogin.vue")
        },
        {
            path: "/",
            redirect: "/welcome"
        },
        {
            path: "/welcome",
            component: () => import("../views/front/Poster.vue")
        },
        {
            path: "/backLogin",
            component: () => import("../views/login/frontLogin.vue")
        },
        {
            path: "/back",
            component: () => import("../views/back/Layout.vue"),
            redirect: "/back/home",
            children:[
                {
                    path: "/back/home",
                    component: () => import("../views/back/Home.vue"),
                    meta:{
                        index: 0
                    }
                },
                {
                    path: "/back/com",
                    component: () => import("../views/back/BackCom.vue"),
                },
                {
                    path: "/back/admin",
                    component: () => import("../views/back/Admin.vue"),
                },
                {
                    path: "/back/message",
                    component: () => import("../views/back/BackMessage.vue"),
                },
                {
                    path: "/back/user",
                    component: () => import("../views/back/User.vue"),
                },
                {
                    path: "/back/addCarousel",
                    component: () => import("../views/back/AddCarousel.vue"),
                },
                {
                    path: "/back/addAdmin",
                    component: () => import("../views/back/AddAdmin.vue"),
                },
                {
                    path: "/back/carousel",
                    component: () => import("../views/back/Carousel.vue"),
                },
                {
                    path: "/back/addon",
                    component: () => import("../views/back/AddCom.vue"),
                },
                {
                    path: "/back/notice",
                    component: () => import("../views/back/BackNotice.vue"),
                },
                {
                    path: "/back/new",
                    component: () => import("../views/back/BackNew.vue"),
                },
                {
                    path: "/back/love",
                    component: () => import("../views/back/BackLove.vue"),
                },
                {
                    path: "/back/addLove",
                    component: () => import("../views/back/AddLove.vue"),
                },
            ]
        },
        {
            path: "/front",
            component: () => import("../views/front/Home.vue"),
            redirect: "/front/index",
            children:[
                {
                    path: "/front/index",
                    component: () => import("../views/front/Index.vue"),
                },
                {
                    path: "/front/me",
                    component: () => import("../views/front/Meinfo.vue"),
                },
                {
                    path: "/front/message",
                    component: () => import("../views/front/Message.vue"),
                },
                {
                    path: "/front/detail",
                    component: () => import("../views/front/MessageDesc.vue"),
                },
                {
                    path: "/front/love",
                    component: () => import("../views/front/Love.vue"),
                },
                {
                    path: "/front/newList",
                    component: () => import("../views/front/newList.vue"),
                },
                {
                    path: "/front/community",
                    component: () => import("../views/front/Community.vue")
                },
                {
                    path: "/front/newIntro",
                    component: () => import("../views/front/NewIntro.vue")
                },
                {
                    path: "/front/mycom",
                    component: () => import("../views/front/MyCom.vue")
                },
                {
                    path: "/front/mynew",
                    component: () => import("../views/front/MyNew.vue")
                },
                {
                    path: "/front/share",
                    component: () => import("../views/front/Share.vue")
                },
                {
                    path: "/front/notice",
                    component: () => import("../views/front/AddNotice.vue")
                },
                {
                    path: "/front/findnew",
                    component: () => import("../views/front/FindNew.vue")
                },
                {
                    path: "/front/noticeList",
                    component: () => import("../views/front/Notice.vue")
                },

            ]
        },

    ]
})
export default router