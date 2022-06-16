<template>
  <div id="app">
    <GantTest />
  </div>
</template>

<script>
// import BootVueHREmployeeList from './components/BootVueHREmployeeList.vue'
import GantTest from './components/GantTest.vue'

export default {
  name: 'App',
  components: {
    // BootVueHREmployeeList,
    GantTest
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
