<template>
  <div class="d-flex flex-wrap align-items-center justify-content-center">
    <b-tabs pills vertical>
      <b-tab title="Single" active>
        <EmployeeSingleList />
      </b-tab>
      <b-tab title="Multi">
        <EmployeeMultiList />
      </b-tab>
    </b-tabs>
  </div>
</template>

<script>
import employeeSingleList from '@/views/employeeList/EmployeeSingleList'
import employeeMultiList from '@/views/employeeList/EmployeeMultiList'

export default {
  components:{
    EmployeeSingleList :employeeSingleList,
    EmployeeMultiList :employeeMultiList,
  }
}

</script>
