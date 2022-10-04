<template>
  <div id="app">
      <LoadingSpinner ref="loading"/>
      <header class="d-flex flex-wrap align-items-center justify-content-center py-3 mb-3 border-bottom">
        <router-link v-for="(r, i) in router" class="nav-link px-4" :to="r.to" @click.stop="routerClick(i)" :key="i">
          <span :class="{'text-primary' : r.active}">
            {{r.name}}
          </span>
        </router-link>
        <div class="col-md-3 text-end">
          <button type="button" class="btn btn-outline-primary me-2">Login</button>
          <button type="button" class="btn btn-primary">Sign-up</button>
        </div>
      </header>
      <router-view />
  </div>
</template>

<script>
import LoadingSpinner from "@/components/util/LoadingSpinner";
import _ from 'lodash';

export default {
  name: 'App',
  components: {
    LoadingSpinner,
  },
  data(){
    return {
      router: [
        { name: 'Home', to: '/', active: true},
        { name: 'EmployeeList', to: '/employeeList', active: false},
        { name: 'TeamList', to: '/teamList', active: false},
        { name: 'Test1', to: '/', active: false},
        { name: 'Test2', to: '/', active: false},
        { name: 'Test3', to: '/', active: false},
      ],
    }
  },
  methods:{
    routerClick(index){
      const router = this.router;

      this.router = _.map(router, (r,i) => {
        if(i === index){
          r.active = true;
        }else{
          r.active = false;
        }
        return r;
      });
      console.log('this.router : ', this.router);
    }
  },
  created() {
    console.log('App created');
  },
  mounted() {
    console.log('App mounted');
    this.$main.loading = this.$refs.loading;
  },
  updated() {
    console.log('App updated');
  }
}
</script>

<style>
#app {

}
</style>
