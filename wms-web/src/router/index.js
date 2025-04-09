import VueRouter from "vue-router";
import Register from "../components/Register.vue";
import Main from "@/components/Main.vue";
import Home from "@/components/Home.vue";
import AdminManage from "@/components/admin/AdminManage.vue";
import UserManage from "@/components/user/UserManage.vue";
import StorageManage from "@/components/storage/StorageManage.vue";
const routes = [
    {
        path: "/",
        name: "login",
        component: () => import("../components/Login.vue")
    },
    {
        path: "/Index",
        name: "Index",
        component: () => import("../components/Index.vue"),
        children: [
            {
                path: '', // 默认路径
                component: Main // 默认显示 Main 组件
            },
            {
                path: '/Home',
                name: 'home',
                meta: {
                    title: '首页'
                },
                component: Home // 点击“个人中心”时显示 Home 组件
            },
            {
                path: '/Main', // 新增路径
                name: 'main',
                meta: {
                    title: '主页面'
                },
                component: Main // 点击“首页”时显示 Main 组件
            },
            {
                path:'/Admin',
                name:'admin',
                meta:{
                    title:'管理员管理'
                },
                component:AdminManage
            },
            {
                path:'/User',
                name:'user',
                meta:{
                    title:'用户管理'
                },
                component:UserManage
            },
            {
                path:'/Storage',
                name:'storage',
                meta:{
                    title:'仓库管理'
                },
                component:StorageManage
            },
            {
                path: "/Register",
                name: "Register",
                component: Register // 使用 Register 组件
            }
        ]
    },

];

const router = new VueRouter({
    mode: "history",
    routes
})
const VueRouterPush = VueRouter.prototype.push
VueRouter.prototype.push = function push (to){
    return VueRouterPush.call(this,to).catch(err=>err)
}
export default router;