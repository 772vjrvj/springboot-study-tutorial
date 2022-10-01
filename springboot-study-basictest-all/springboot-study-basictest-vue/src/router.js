import { createWebHistory, createRouter } from "vue-router";

import Home from "./views/HomeView.vue";
import BootVueHREmployeeList from "./views/BootVueHREmployeeList";
import GantTableCross from "./views/GantTableCross";

const routes = [
    {
        path: "/",
        name: "Home",
        component: Home,
    },
    {
        path: "/employeeList",
        name: "BootVueHREmployeeList",
        component: BootVueHREmployeeList,
    },
    {
        path: "/teamList",
        name: "GantTableCross",
        component: GantTableCross,
    },

];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;