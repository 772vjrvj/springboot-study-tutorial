<template>
  <div class = "container employeeList-container">
    <div class="text-start fs-4 mb-lg-5 position-relative"> Employees List.</div>
    <div class="row justify-content-between">
      <div class="col-auto">
        <b-form-select
            @change="selectRowChange"
            id="inline-form-custom-select-pref"
            class="mb-2 ms-2"
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
        <b-button class="me-1"  v-b-modal.no-search variant="primary">Etc. Search</b-button>
        <BIconFunnel v-if="noDataSelected.length === 0 " style="font-size: 1.5rem; color: #0d6efd"></BIconFunnel>
        <BIconFunnelFill v-if="noDataSelected.length !== 0 "  style="font-size: 1.5rem; color: #0d6efd"></BIconFunnelFill>
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
    <b-table-simple striped hover responsive>
      <b-thead>
        <b-tr>
          <b-th v-for="(field, index) in fields" :key="index" :style="{width: field.width}">
            {{field.label}}
            <BIconSortDown   class="employeeList-hover" :class="{'sortActive':field.key === sortingKey}"  v-if="field.key !== 'rowNum' && field.sort === 'desc'" @click="sortingEmploy(field)" style="font-size: 0.8rem; color: #0d6efd"></BIconSortDown>
            <BIconSortUpAlt     class="employeeList-hover" :class="{'sortActive':field.key === sortingKey}"  v-if="field.key !== 'rowNum' && field.sort === 'asc'"   @click="sortingEmploy(field)" style="font-size: 0.8rem; color: #0d6efd"></BIconSortUpAlt>
            <BIconFunnel        class="employeeList-hover"   v-if="field.filter && field.filterFill !== 'full'" @click="filtering(field)" style="font-size: 0.8rem; color: #0d6efd"></BIconFunnel>
            <BIconFunnelFill  v-b-modal.department-fill  class="employeeList-hover" v-if="field.filter && field.filterFill === 'full'" @click="filtering(field)" style="font-size: 0.8rem; color: #0d6efd"></BIconFunnelFill>
          </b-th>
        </b-tr>
      </b-thead>
      <b-tbody>
        <b-tr v-for="(emps, idx) in employees" :key="idx">
          <b-td>
            {{emps['rowNum']}}
          </b-td>
          <b-td>
            {{emps['employeeId']}}
          </b-td>
          <b-td>
            {{emps['firstName'] +' ' + emps['lastName']}}
          </b-td>
          <b-td>
            {{emps['email']}}
          </b-td>
          <b-td>
            {{emps['phoneNumber']}}
          </b-td>
          <b-td>
            {{emps['hireDate']}}
          </b-td>
          <b-td>
            {{emps['jobId']}}
          </b-td>
          <b-td>
            {{ emps['salary'] === -1 ? 0 : emps['salary'] }}
          </b-td>
          <b-td>
            {{ emps['commissionPct'] === -1 ? '-' : emps['commissionPct'] * 100  }}
          </b-td>
          <b-td>
            {{emps['managerFirstName'] + ' ' + emps['managerLastName']}}
          </b-td>
          <b-td>
            {{emps['departmentName']}}
          </b-td>
        </b-tr>
      </b-tbody>
    </b-table-simple>
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
    <b-modal
        id="no-search"
        ref="modal"
        title="Etc. Search"
        @ok="handleOk"
        @cancel="handleCancel"
    >
      <b-form-group label="No Data Search Check" >
        <b-form-checkbox-group
            id="checkbox-group-1"
            v-model="noDataSelected"
            :options="noDataOptions"
            name="flavour-1"
            stacked
        ></b-form-checkbox-group>
      </b-form-group>
    </b-modal>
    <b-modal
        id="department-fill"
        ref="modal"
        title="Etc. Search"
        @ok="handleOk"
        @cancel="handleCancel"
    >
      <b-form-group label="No Data Search Check" >
        <b-form-checkbox-group
            id="checkbox-group-1"
            v-model="departSelect"
            :options="departOption"
            name="flavour-1"
            stacked
        ></b-form-checkbox-group>
      </b-form-group>
    </b-modal>
  </div>
</template>

<script>
import {BIconFunnel, BIconFunnelFill, BIconSortDown, BIconSortUpAlt} from 'bootstrap-icons-vue'
import _ from "lodash";

export default {
  name: 'BootVueHREmployeeList',
  components: {
    BIconFunnel,
    BIconFunnelFill,
    BIconSortDown,
    BIconSortUpAlt
  },
  data(){
    return {
      employees : [],
      fields: [
        {key:'rowNum',         label:'No.',           sort: 'asc', width: '90px',  filter: false},
        {key:'employeeId',     label:'ID',            sort: 'asc', width: '70px',  filter: false},
        {key:'name',           label:'Name',          sort: 'asc', width: '140px', filter: false},
        {key:'email',          label:'Email',         sort: 'asc', width: '105px', filter: false},
        {key:'phoneNumber',    label:'Phone',         sort: 'asc', width: '140px', filter: false},
        {key:'hireDate',       label:'HireDate',      sort: 'asc', width: '120px', filter: false},
        {key:'jobId',          label:'JobId',         sort: 'asc', width: '120px', filter: false},
        {key:'salary',         label:'Salary',        sort: 'asc', width: '110px', filter: false},
        {key:'commissionPct',  label:'Commission (%)',sort: 'asc', width: '200px', filter: false},
        {key:'manager',        label:'Manager',       sort: 'asc', width: '150px', filter: false},
        {key:'departmentName', label:'Department',    sort: 'asc', width: '150px', filter: true,  filterFill: 'full'},
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
      noDataSelectedConfirm: [],
      noDataSelected: [], // Must be an array reference!
      noDataOptions: [
        { text: 'No Commission', value: 'commission' },
        { text: 'No Manager', value: 'manager' },
        { text: 'No Department', value: 'department' },
      ],
      departSelectConfirm: [],
      departSelect: [], // Must be an array reference!
      departOption: [
        { text: 'IT', value: 'IT' },
        { text: 'Public Relations', value: 'Public Relations' },
        { text: 'Shipping', value: 'Shipping' },
      ],
      url :{
        employeesBase: '/hr'
      },
      isBusy: false,
      sorting: '',
      sortingKey: ''
    }
  },
  methods: {
    getHREmployees(page = 1){
        this.pageObject.page = page;
        return this.getHREmployeesApi(page, this.pageObject.perPageRow, this.pageObject.perGroupPage, this.selectEmpCol, this.searchInput, this.noDataSelected, this.sorting).then((response) => {
          // 화면에서 처리
          if(response && response.data && response.data.length > 0){
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
          }
          else
          {
            this.employees = [];
            this.pageObject.totalRow  = 0;
            this.pageObject.totalPage = 0;
            this.pageObject.startPage = 0;
            this.pageObject.endPage   = 0;
            // endPage 가 총 페이지 수를 넘을 수 없다.
            this.pageObject.prevPage  = false;
            this.pageObject.nextPage  = false;
            this.pageObject.firstPage = false;
            this.pageObject.lastPage  = false;
          }
        }).catch(e => {
          console.log('error : ',e);
        }).finally(() => {
          return true;
        })
    },
    async selectRowChange(e){
      if(e === 0) return;             // 0 이면 호출하지 않는다.
      this.pageObject.perPageRow = e; // 선택된 option값을 넣어준다.
      this.$main.loading.show();
      await this.getHREmployees(1);    // 페이지 마다 보여주는 row수가 바뀌었으므로 1페이지 부터 시작한다.
      this.$main.loading.hide();
    },
    async selectPageChange(e){
      if(e === 0) return;             // 0 이면 호출하지 않는다.
      this.pageObject.perGroupPage = e;
      this.$main.loading.show();
      await this.getHREmployees(1);                      // 페이지 마다 보여주는 row수가 바뀌었으므로 1페이지 부터 시작한다.
      this.$main.loading.hide();
    },
    async onInputPage(){
      if(this.inputPage){
        const page = Number(this.inputPage.trim());
        if(!Number.isNaN(page) && page > 0){
          const perPage = page > this.pageObject.totalPage ? this.pageObject.totalPage : page;
          this.$main.loading.show();
          await this.getHREmployees(perPage);
          this.$main.loading.hide();
          this.inputPage = perPage;
        }else{
          alert('유효한 값이 아닙니다.');
        }
      }
    },
    getHREmplColList(){
      return this.getHREmplColListApi().then((res) => {
        if(res && res.data){
          this.empColList = this.empColList.concat(res.data);
        }
      }).catch(e => {
        console.log('error : ',e);
      }).finally(() => {
        console.log('getHREmplColList');
        return true;
      });
    },
    async searchChange(e){
      this.selectEmpCol = e;
      if(e === 'search'){
        this.searchInput = '';
        this.$main.loading.show();
        await this.getHREmployees(1);                      // 페이지 마다 보여주는 row수가 바뀌었으므로 1페이지 부터 시작한다.
        this.$main.loading.hide();
      }
    },
    async searchEmpCol(){
      if(this.selectEmpCol && this.searchInput){
        if(this.selectEmpCol === 'managerId' || this.selectEmpCol ===  'employeeId' || this.selectEmpCol === 'commissionPct'
            || this.selectEmpCol === 'salary' ||this.selectEmpCol === 'departmentId'){
          const searchInput =  Number(this.searchInput);
          if(Number.isNaN(searchInput)){
            alert('숫자를 입력하세요.');
            return;
          }
        }
        this.$main.loading.show();
        await this.getHREmployees(1);
        this.$main.loading.hide();
      }
    },
    async pagination(){
      this.$main.loading.show();
      await this.getHREmployees(this.pageObject.page);
      this.$main.loading.hide();
    },
    handleOk() {
      this.noDataSelectedConfirm = this.noDataSelected;
    },
    handleCancel(){
      this.noDataSelected = this.noDataSelectedConfirm;
    },
    async getEmploy(){

      this.$main.loading.show();
      await this.getHREmplColList();
      await this.getHREmployees();
      this.$main.loading.hide();
    },
    async sortingEmploy(field){

      _.each(this.fields, f => {
        if(f.key !== field.key){
          f.sort = 'desc';
        }else{
          if(f.sort === 'desc'){
            f.sort = 'asc';
          }else if(f.sort === 'asc'){
            f.sort = 'desc';
          }
        }
      });

      this.sorting = field.key + '_' + field.sort;
      this.sortingKey = field.key;
      this.$main.loading.show();
      await this.getHREmployees(this.pageObject.page);    // 페이지 마다 보여주는 row수가 바뀌었으므로 1페이지 부터 시작한다.
      this.$main.loading.hide();
    },
    filtering(field){
      console.log('field :', field);
    },
    getHREmployeesApi(page, perPageRow, perGroupPage, searchKey, searchValue, noDataList, sorting){

      if(searchKey && searchValue){
        if(searchKey === 'commissionPct'){
          searchValue = searchValue * 0.01;
        }
      }
      const noDataParams = noDataList.map((item) => `${item}No=1`).join('&');

      return this.$http.get(`${this.url.employeesBase}/employees?page=${page}&perPageRow=${perPageRow}&perGroupPage=${perGroupPage}&key=${searchKey}&value=${searchValue}&${noDataParams}&sorting=${sorting}`);
    },
    getHREmplColListApi(){
      return this.$http.get(this.url.employeesBase + '/empColList');
    }
  },
  created() {
    this.getEmploy();
    console.log('BootVueHREmployeeList created :', this.$main.loading);
  },
  mounted() {
    console.log('BootVueHREmployeeList mounted :', this.$refs.modal);
  },
  updated() {
    console.log('BootVueHREmployeeList updated :', this.$refs.modal);
  }
}
</script>
<style scoped>
  .employeeList-container{
    position: relative;
  }

  .employeeList-hover:hover{
    cursor: pointer;
  }

  .sortActive{
    color: red !important;
    font-weight: bolder !important;
  }

</style>