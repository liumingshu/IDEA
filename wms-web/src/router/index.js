import VueRouter from "vue-router";
import Register from "../components/Register.vue"; // 导入 Register 组件

const routes = [
    {
        path: "/",
        name: "login",
        component: () => import("../components/Login.vue")
    },
    {
        path: "/Index",
        name: "Index",
        component: () => import("../components/Index.vue")
    },
    {
        path: "/Register",
        name: "Register",
        component: Register // 使用 Register 组件
    }
];

const router = new VueRouter({
    mode: "history",
    routes
});

export default router;