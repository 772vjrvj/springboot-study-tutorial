<template>
  <div class = "container">
    <h3 class="text-center mb-lg-4"> Employees List </h3>
    <div class="row justify-content-between">
      <div class="col-auto">
        <b-form-select
            @change="selectRowChange"
            id="inline-form-custom-select-pref"
            class="mb-2 mr-sm-2 mb-sm-0"
            :options="rowCountList"
            v-model="pageObject.perPageRow"
            value-field="item"
            text-field="name"
        >
        </b-form-select>
      </div>
      <div class="col-auto">
        <div class="pt-1"><span>Total Row : {{this.pageObject.totalRow}}</span></div>
      </div>
      <div class="col-auto">
        <b-button v-b-modal.modal-1 variant="outline-secondary" >Etc. Search</b-button>
        <b-modal id="modal-1" title="BootstrapVue">
          <p class="my-4">Hello from modal!</p>
        </b-modal>
      </div>
      <div class="col-auto">
        <div class="input-group mb-3">
          <b-form-select
              @change="searchChange"
              id="inline-form-custom-select-pref"
              class="mb-2 mr-sm-2 mb-sm-0"
              :options="empColList"
              v-model="selectEmpCol"
              value-field="KEY"
              text-field="VALUE"
          >
          </b-form-select>
          <b-form-input
              v-model="searchInput"
              type="text"
              placeholder="Input your contents"
              :disabled="selectEmpCol === 'search'"
              @keyup.enter="searchEmpCol"
          ></b-form-input>
          <b-button
              :disabled="selectEmpCol === 'search'"
              @click="searchEmpCol"
              variant="secondary"
          >Go</b-button>
        </div>
      </div>
    </div>
    <b-table striped hover responsive
             :fields="fields"
             :items="employees"
    >
      <template #cell(commissionPct)="data">
        {{ data.value === -1 ? '-' : data.value }}
      </template>
      <template #cell(salary)="data">
        {{ data.value === -1 ? 0 : data.value }}
      </template>
      <template #cell(name)="data">
        {{ data.item.firstName }} {{ data.item.lastName }}
      </template>
      <template #cell(manager)="data">
        {{ data.item.managerFirstName }} {{ data.item.managerLastName }}
      </template>
    </b-table>
    <div class="row justify-content-center">
      <div class="col-auto">
        <b-form-select
            @change="selectPageChange"
            id="inline-form-custom-select-pref"
            class="mb-2 mr-sm-2 mb-sm-0"
            :options="pageCountList"
            v-model="selectedPerPage"
            value-field="item"
            text-field="name"
        >
        </b-form-select>

      </div>
      <div class="col-auto">
        <b-pagination
            v-model="pageObject.page"
            :total-rows="pageObject.totalRow"
            :per-page="pageObject.perPageRow"
            aria-controls="my-table"
            first-text="First"
            prev-text="Prev"
            next-text="Next"
            last-text="Last"
            :limit="pageObject.perGroupPage"
            align="center"
            @click="pagination"
        ></b-pagination>
      </div>
      <div class="col-auto">
        <div class="input-group mb-3">
          <b-form-input
              :style="{width: '70px'}"
              v-model="inputPage"
              type="text"
              placeholder="Page"
              @keyup.enter="onInputPage"
          ></b-form-input>
          <b-button
              @click="onInputPage"
              variant="secondary"
          >Go</b-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import HREmployeeService from '../services/HREmployeeService'

export default {
  name: 'BootVueHREmployeeList',
  data(){
    return {
      employees : [],
      fields: [
        {key:'rowNum', label:'No.'},
        {key:'employeeId', label:'ID'},
        {key:'name', label:'Name'},
        // {key:'firstName', label:'firstName'},
        // {key:'lastName', label:'lastName'},
        {key:'email', label:'Email'},
        {key:'phoneNumber', label:'Phone'},
        {key:'hireDate', label:'HireDate'},
        {key:'jobId', label:'JobId'},
        {key:'salary', label:'Salary'},
        {key:'commissionPct', label:'Commission (%)'},
        // {key:'managerId', label:'managerId'},
        {key:'manager', label:'Manager'},
        // {key:'managerFirstName', label:'managerFirstName'},
        // {key:'managerLastName', label:'managerLastName'},
        // {key:'departmentId', label:'departmentId'},
        {key:'departmentName', label:'Department'},
      ],
      rowCountList : [
        { item: 0, name: 'Per Row' },
        { item: 5, name: '5' },
        { item: 10, name: '10' },
        { item: 20, name: '20' },
        { item: 30, name: '30' },
        { item: 50, name: '50' },
        { item: 100, name: '100' }],
      pageCountList : [
        { item: 0, name: 'Per Page' },
        { item: 5, name: '5' },
        { item: 10, name: '10' },],
      selectedPerPage: 0,
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
      },
      empColList: [{VALUE:'Search', KEY:'search' }],
      selectEmpCol:'search',
      searchInput:'',
    }
  },
  methods: {
    getHREmployees(page = 1){
      if(page){
        this.pageObject.page = page;
        HREmployeeService.getHREmployees(page, this.pageObject.perPageRow, this.pageObject.perGroupPage, this.selectEmpCol, this.searchInput).then((response) => {
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
    selectRowChange(e){
      if(e === 0) return;             // 0 이면 호출하지 않는다.
      this.pageObject.perPageRow = e; // 선택된 option값을 넣어준다.
      this.getHREmployees(1);    // 페이지 마다 보여주는 row수가 바뀌었으므로 1페이지 부터 시작한다.
    },
    selectPageChange(e){
      if(e === 0) return;             // 0 이면 호출하지 않는다.
      this.pageObject.perGroupPage = e;
      this.getHREmployees(1);                      // 페이지 마다 보여주는 row수가 바뀌었으므로 1페이지 부터 시작한다.
    },
    onInputPage(){
      if(this.inputPage){
        const page = Number(this.inputPage.trim());
        if(!Number.isNaN(page) && page > 0){
          const perPage = page > this.pageObject.totalPage ? this.pageObject.totalPage : page;
          this.getHREmployees(perPage);
          this.inputPage = perPage;
        }else{
          alert('유효한 값이 아닙니다.');
        }
      }
    },
    getHREmplColList(){
      HREmployeeService.getHREmplColList().then((res) => {
        if(res && res.data){
          this.empColList = this.empColList.concat(res.data);
        }
      })
    },
    searchChange(e){
      this.selectEmpCol = e;
      if(e === 'search'){
        this.searchInput = '';
        this.getHREmployees(1);                      // 페이지 마다 보여주는 row수가 바뀌었으므로 1페이지 부터 시작한다.
      }
    },
    searchEmpCol(){
      if(this.selectEmpCol && this.searchInput){
        if(this.selectEmpCol === 'managerId' || this.selectEmpCol ===  'employeeId' || this.selectEmpCol === 'commissionPct'
           || this.selectEmpCol === 'salary' ||this.selectEmpCol === 'departmentId'){
          const searchInput =  Number(this.searchInput);
          if(Number.isNaN(searchInput)){
            alert('숫자를 입력하세요.');
            return;
          }
        }
        this.getHREmployees(1);
      }
    },
    pagination(){
      this.getHREmployees(this.pageObject.page);
    }
  },
  created() {
    this.getHREmplColList();
    this.getHREmployees();
  }
}
</script>
<style>
.test{
  width: 15%
}
</style>
