import { createWebHistory, createRouter } from "vue-router";

import Home from "./views/HomeView.vue";
import EmployeeListTab from "./views/EmployeeListTab";
import GantTableCross from "./views/GantTableCross";

const routes = [
    {
        path: "/",
        name: "Home",
        component: Home,
    },
    {
        path: "/employeeList",
        name: "EmployeeList",
        component: EmployeeListTab,
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