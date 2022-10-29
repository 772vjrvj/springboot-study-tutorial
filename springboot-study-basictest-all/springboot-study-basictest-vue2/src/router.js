import Vue from 'vue'
import Router from 'vue-router'

import Home from "./views/HomeView.vue";
import EmployeeListTab from "./views/EmployeeListTab";

Vue.use(Router)

export default new Router({
    mode: 'history',
    base: process.env.BASE_URL,
    routes: [
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
            component: () => import(/* webpackChunkName: "about" */ './views/GantTableCross.vue')
        },
        {
            path: "/themeList",
            name: "ThemeList",
            component: () => import(/* webpackChunkName: "about" */ './views/ThemeList.vue')
        },
    ]
})
