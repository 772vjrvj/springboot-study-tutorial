<template>
    <div class = "container">
        <h1 class="text-center"> Employees List </h1>
        <table class="table table-striped">
            <thead>
                <th> Employee ENO </th>
                <th> Employee ENAME </th>
                <th> Employee JOB </th>
                <th> Employee MANAGER </th>
                <th> Employee HIREDATE </th>
                <th> Employee SALARY </th>
                <th> Employee COMMISION </th>
                <th> Employee DNO </th>
            </thead>
            <tbody>
            <tr v-for = "employee in employees" v-bind:key = "employee.id">
                <td> {{employee.eno}} </td>
                <td> {{employee.ename}} </td>
                <td> {{employee.job}} </td>
                <td> {{employee.manager}} </td>
                <td> {{employee.hiredate}} </td>
                <td> {{employee.salary}} </td>
                <td> {{employee.commission}} </td>
                <td> {{employee.dno}} </td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
import EmployeeService from '../../services/EmployeeService'

export default {
    name: 'EmployeeList',
    data(){
        return {
            employees : []
        }
    },
    methods: {
        getEmployees(){
            EmployeeService.getEmployees().then((response) => {
                this.employees = response.data;
            })
        }
    },
    created() {
        this.getEmployees()
    }
}
</script>
