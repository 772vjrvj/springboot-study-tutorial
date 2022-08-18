// import { createApp } from 'vue'
// import App from './App.vue'
// import 'bootstrap/dist/css/bootstrap.min.css'
//
// createApp(App).mount('#app')



//BootstrapVue3를 사용하기 위한
import App from './App.vue'
import {createApp} from 'vue'
import {BootstrapVue3} from 'bootstrap-vue-3'
import { BootstrapIconsPlugin } from 'bootstrap-icons-vue';
import router from './router';

// Optional, since every component import their Bootstrap functionality
// the following line is not necessary
// import 'bootstrap'

import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue-3/dist/bootstrap-vue-3.css'

const app = createApp(App)
app.use(BootstrapIconsPlugin);
app.use(BootstrapVue3);
app.use(router);
app.mount('#app')