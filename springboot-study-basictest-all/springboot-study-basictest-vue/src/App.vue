<template>
  <div id="app">
      <LoadingSpinner ref="loading"/>
      <header class="d-flex flex-wrap align-items-center justify-content-center py-3 mb-4 border-bottom">
        <ul class="nav nav-pills col-12 col-md-auto mb-2 justify-content-center mb-md-0">
          <li class="nav-item">
            <router-link class="nav-link px-4 link-dark text-dark" to="/">Home</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link px-4 link-primary text-primary" to="/employeeList">EmployeeList</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link px-4 link-dark text-dark" to="/teamList">TeamList</router-link>
          </li>
          <li class="nav-item"><a href="#" class="nav-link px-4 link-dark text-dark">Pricing</a></li>
          <li class="nav-item"><a href="#" class="nav-link px-4 link-dark text-dark">FAQs</a></li>
          <li class="nav-item"><a href="#" class="nav-link px-4 link-dark text-dark">About</a></li>
        </ul>
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


export default {
  name: 'App',
  components: {
    LoadingSpinner
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
