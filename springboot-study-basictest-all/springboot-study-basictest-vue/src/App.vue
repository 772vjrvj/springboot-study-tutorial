<template>
  <div id="app">
    <HREmployeeList />
  </div>
</template>

<script>
import HREmployeeList from './components/HREmployeeList.vue'

export default {
  name: 'App',
  components: {
    HREmployeeList
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
