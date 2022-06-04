<template>
  <div class = "container">
    <h3 class="text-center mb-lg-4"> Employees List </h3>
    <div class="row justify-content-between">
      <div class="col-2">
        <select v-model="selectedPerRow" @change="selectRowChange" class="form-select form-select-md mb-sm-3" aria-label="Default select example">
          <option v-for="(val, idx) in rowCountList" v-bind:key="idx" v-bind:value="val">{{val === 0 ? 'Row Count' : val}}</option>
        </select>
      </div>
      <div class="col-3 ms-auto">
        <select class="form-select form-select-md mb-sm-3" aria-label="Default select example">
          <option selected value="id">ID</option>
          <option value="firstName">First Name</option>
          <option value="lastName">Last Name</option>
          <option value="email" >Email</option>
          <option value="phoneNumber">Phone Number</option>
          <option value="hireDate">Hire Date</option>
          <option value="jobId">Job ID</option>
          <option value="salary">Salary</option>
          <option value="commissionPct">Commission Pct</option>
          <option value="managerId">Manager ID</option>
          <option value="departmentId">Department ID</option>
        </select>
      </div>
      <div class="col-3 ps-0">
        <div class="input-group mb-3">
          <input type="text" class="form-control" placeholder="Input your contents" aria-label="Recipient's username" aria-describedby="button-addon2">
          <button class="btn btn-secondary" type="button" id="button-addon2">Button</button>
        </div>
      </div>
    </div>
    <table class="table table-striped">
      <thead>
      <th> No </th>
      <th> ID </th>
      <th> First Name </th>
      <th> Last Name </th>
      <th> Email </th>
      <th> Phone Number </th>
      <th> Hire Date </th>
      <th> Job ID </th>
      <th> Salary </th>
      <th> Commission Pct </th>
      <th> Manager ID </th>
      <th> Department ID </th>
      </thead>
      <tbody>
      <tr v-for = "employee in employees" v-bind:key = "employee.id">
        <td> {{employee.rowNum}} </td>
        <td> {{employee.employeeId}} </td>
        <td> {{employee.firstName}} </td>
        <td> {{employee.lastName}} </td>
        <td> {{employee.email}} </td>
        <td> {{employee.phoneNumber}} </td>
        <td> {{employee.hireDate}} </td>
        <td> {{employee.jobId}} </td>
        <td> {{employee.salary}} </td>
        <td> {{employee.commissionPct}} </td>
        <td> {{employee.managerId}} </td>
        <td> {{employee.departmentId}} </td>
      </tr>
      </tbody>
    </table>
    <div class="row justify-content-evenly">
      <div class="col-2 pe-0">
        <select v-model="selectedPerPage" @change="selectPageChange" class="form-select form-select-md mb-sm-3" aria-label="Default select example">
          <option v-for="(val,idx) in pageCountList" v-bind:key="idx" v-bind:value="val">{{val === 0 ? 'Page Row':val}}</option>
        </select>
      </div>
      <div class="col-4 pe-0 ps-0">
        <nav aria-label="Page navigation example">
          <ul class="pagination justify-content-center pagination-md">
            <li class="page-item" v-bind:class="{disabled: !pageObject.firstPage}"
                @click="getHREmployees(pageObject.firstPage ? 1 : null)"
                v-bind:style="{cursor: pageObject.firstPage ? 'pointer' : 'default'}"
            >
              <a class="page-link" aria-label="Previous">
                <span aria-hidden="true">&laquo;</span>
              </a>
            </li>

            <li class="page-item" v-bind:class="{disabled: !pageObject.prevPage}"
                @click="getHREmployees(pageObject.prevPage ? pageObject.page - 1 : null)"
                v-bind:style="{cursor: pageObject.prevPage ? 'pointer' : 'default'}"
            >
              <a class="page-link" >Prev</a>
            </li>

            <li v-for="(val) in (pageObject.endPage - pageObject.startPage + 1)"
                v-bind:key = "val" class="page-item"
                @click="getHREmployees(pageObject.startPage - 1 + val !== pageObject.page?pageObject.startPage - 1 + val:null)"
                v-bind:class="{active : pageObject.startPage - 1 + val === pageObject.page}"
                v-bind:style="{cursor: pageObject.startPage - 1 + val !== pageObject.page ? 'pointer' : 'default'}"
            >
              <a class="page-link" >{{pageObject.startPage - 1 + val}}</a>
            </li>

            <li class="page-item" v-bind:class="{disabled: !pageObject.nextPage}"
                @click="getHREmployees(pageObject.nextPage ? pageObject.page + 1 : null)"
                v-bind:style="{cursor: pageObject.nextPage ? 'pointer' : 'default'}"
            >
              <a class="page-link" >Next</a>
            </li>

            <li class="page-item" v-bind:class="{disabled: !pageObject.lastPage}"
                @click="getHREmployees(pageObject.lastPage  ? pageObject.totalPage : null)"
                v-bind:style="{cursor: pageObject.lastPage ? 'pointer' : 'default'}"
            >
              <a class="page-link"  aria-label="Next">
                <span aria-hidden="true">&raquo;</span>
              </a>
            </li>
          </ul>
        </nav>
      </div>
      <div class="col-2 ps-0">
        <div class="input-group mb-3 w-50">
          <input type="text" v-model="inputPage" class="form-control" placeholder="Page" aria-label="Recipient's username" aria-describedby="button-addon2">
          <button class="btn btn-secondary btn-sm"  @click="onInputPage" type="button" id="button-addon3">GO</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import HREmployeeService from '../services/HREmployeeService'

export default {
  name: 'HREmployeeList',
  data(){
    return {
      employees : [],
      selectedPerRow : 0,
      rowCountList : [0, 10, 20, 30, 50, 100],
      selectedPerPage : 0,
      pageCountList : [0, 5, 10],
      inputPage: '',
      pageObject : {
        startRow: 1,
        endRow: 10,
        perPageRow: 10,
        totalRow: 1,
        page: 1,
        perGroupPage: 5,
        totalPage: 1,
        startPage: 1,
        endPage: 1,
        prevPage: false,
        nextPage: false,
        firstPage: false,
        lastPage: false,
      }
    }
  },
  methods: {
    getHREmployees(page = 1){
      if(page){
        this.pageObject.page = page;
        HREmployeeService.getHREmployees(page, this.pageObject.perPageRow, this.pageObject.perGroupPage).then((response) => {
          // if(response && response.data){
          //   this.employees = response.data.employeeList;
          //   this.pageObject = response.data.pageObject;
          // }
          // 화면에서 처리
          if(response && response.data){
            this.employees = response.data;
            //화면에서 page를 만들어 보겠다.
            const totalCount = response.data[0].totalCount;
            this.pageObject.totalRow  = totalCount ? totalCount : 0;
            this.pageObject.totalPage = Math.floor((totalCount - 1) / this.pageObject.perPageRow) + 1;
            this.pageObject.startPage = Math.floor((this.pageObject.page - 1) / this.pageObject.perGroupPage) * this.pageObject.perGroupPage + 1;
            this.pageObject.endPage   = this.pageObject.startPage + this.pageObject.perGroupPage - 1;
            // endPage 가 총 페이지 수를 넘을 수 없다.
            if(this.pageObject.endPage > this.pageObject.totalPage) this.pageObject.endPage = this.pageObject.totalPage;
            this.pageObject.prevPage  = this.pageObject.page !== 1;                                 // 이전 페이지 존재 여부
            this.pageObject.nextPage  = this.pageObject.page !== this.pageObject.totalPage;         // 다음 페이지 존재 여부
            this.pageObject.firstPage = this.pageObject.perGroupPage < this.pageObject.page;        // 처음 페이지 존재 여부
            this.pageObject.lastPage  = this.pageObject.endPage < this.pageObject.totalPage;        // 마지막 페이지 존재 여부
            console.log(this.pageObject);
          }
        })
      }
    },
    selectRowChange(){
      if(this.selectedPerRow === 0) return;             // 0 이면 호출하지 않는다.
      this.pageObject.perPageRow = this.selectedPerRow; // 선택된 option값을 넣어준다.
      this.getHREmployees(1);                      // 페이지 마다 보여주는 row수가 바뀌었으므로 1페이지 부터 시작한다.
    },
    selectPageChange(){
      console.log('this.selectedPerPage :', this.selectedPerPage);
      if(this.selectedPerPage === 0) return;             // 0 이면 호출하지 않는다.
      this.pageObject.perGroupPage = this.selectedPerPage; // 선택된 option값을 넣어준다.
      this.getHREmployees(1);                      // 페이지 마다 보여주는 row수가 바뀌었으므로 1페이지 부터 시작한다.
    },
    onInputPage(){
      if(this.inputPage){
        const page = Number(this.inputPage);
        if(!Number.isNaN(page) && page > 0){
          const perPage = page > this.pageObject.totalPage? this.pageObject.totalPage : page;
          this.getHREmployees(perPage);
        }else{
          alert('숫자가 아닙니다.');
        }
      }
    }
  },
  created() {
    this.getHREmployees()
  }
}
</script>
