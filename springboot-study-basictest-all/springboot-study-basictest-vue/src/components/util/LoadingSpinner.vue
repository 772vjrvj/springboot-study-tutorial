<template>
  <div id="loading-spinner"  class="d-flex justify-content-center align-items-center" v-if="isLoading">
    <div class="spinner-border" style="width: 3rem; height: 3rem;" role="status">
      <span class="visually-hidden">Loading...</span>
    </div>
  </div>
</template>

<script>

export default {
  name: 'BootVueHREmployeeList',
  data() {
    return {
      isLoading: false,
    }
  },
  methods: {
    show(){
      this.isLoading = true;
    },
    hide(){
      this.isLoading = false;
    }
  },
}
</script>
<style scoped>
#loading-spinner{
  position: absolute;
  background-color: white;
  height: 100vh;
  z-index: 999;
  width: 100%;
  opacity: 0.5;
  overflow: hidden;
}
</style>