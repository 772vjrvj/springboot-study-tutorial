<template>
  <div class = "container employeeList-container" @mousemove="empGripMouseMove" @mouseup="empGripMouseUp">
        <div class="emp-line"></div>
        <div class="text-start fs-4 mb-4 position-relative"> Employees Multi List.</div>
        <form class="row g-3 mb-4">
          <div class="col-sm-2">
            <label for="empId" class="form-label">{{searchForm.empId.text}}</label>
            <input type="text" v-model="searchForm.empId.value" class="form-control" id="empId">
          </div>
          <div class="col-sm-2">
            <label for="empFirst" class="form-label">{{searchForm.empFirst.text}}</label>
            <input type="text" v-model="searchForm.empFirst.value" class="form-control" id="empFirst">
          </div>
          <div class="col-sm-2">
            <label for="empLast" class="form-label">{{searchForm.empLast.text}}</label>
            <input type="text" v-model="searchForm.empLast.value" class="form-control" id="empLast">
          </div>
          <div class="col-sm-2">
            <label for="email" class="form-label">{{searchForm.email.text}}</label>
            <input type="email" v-model="searchForm.email.value" class="form-control" id="email">
          </div>
          <div class="col-sm-2">
            <label for="phone" class="form-label">{{searchForm.phone.text}}</label>
            <input type="text" v-model="searchForm.phone.value" class="form-control" id="phone">
          </div>
          <div class="col-sm-2">
            <label for="hireDate" class="form-label">{{searchForm.hireDate.text}}</label>
            <input type="date" v-model="searchForm.hireDate.value" class="form-control" id="hireDate">
          </div>
          <div class="col-sm-1">
            <label for="jobId" class="form-label">{{searchForm.jobId.text}}</label>
            <input type="text" v-model="searchForm.jobId.value" class="form-control" id="jobId">
          </div>
          <div class="col-sm-1">
            <label for="salary" class="form-label">{{searchForm.salary.text}}</label>
            <input type="text" v-model="searchForm.salary.value" class="form-control" id="salary">
          </div>
          <div class="col-sm-2">
            <label for="commission" class="form-label">{{searchForm.commission.text}}</label>
            <input type="text" v-model="searchForm.commission.value" class="form-control" id="commission">
          </div>
          <div class="col-sm-2">
            <label for="managerFirst" class="form-label">{{searchForm.managerFirst.text}}</label>
            <input type="email" v-model="searchForm.managerFirst.value" class="form-control" id="managerFirst">
          </div>
          <div class="col-sm-2">
            <label for="managerLast" class="form-label">{{searchForm.managerLast.text}}</label>
            <input type="text" v-model="searchForm.managerLast.value" class="form-control" id="managerLast">
          </div>
          <div class="col-sm-2">
            <label for="department" class="form-label">{{searchForm.department.text}}</label>
            <input type="text" v-model="searchForm.department.value" class="form-control" id="department">
          </div>

          <div class="col-sm-1">
            <label class="form-label">&nbsp;</label>
            <b-button @click="reset" class="w-100" variant="outline-secondary">Reset</b-button>
          </div>
          <div class="col-sm-1">
            <label class="form-label">&nbsp;</label>
            <b-button @click="multiSearch" class="w-100" variant="secondary">Search</b-button>
          </div>
        </form>

        <div class="row justify-content-start">
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
          <div class="col-auto"><span class="lh-lg">Total Row : {{this.pageObject.totalRow}}</span></div>
          <div class="col-auto">
            <b-button class="me-1 ms-4" squared variant="secondary" :disabled="selectedEmployees.length === 0"  v-b-modal.emp-delete>Delete</b-button>
            <b-button class="me-1" squared variant="secondary">Create</b-button>
            <b-button class="ms-4" v-b-modal.no-search variant="secondary">Etc. Search</b-button>
            <BIconFunnel class="employeeList-hover" v-b-modal.no-search v-if="noDataSelected.length === 0 " style="font-size: 1.5rem; color: #6c757d"></BIconFunnel>
            <BIconFunnelFill class="employeeList-hover" v-b-modal.no-search v-if="noDataSelected.length !== 0 "  style="font-size: 1.5rem; color: #6c757d"></BIconFunnelFill>
          </div>
        </div>
        <b-table-simple
          striped
          hover
          bordered
          responsive
        >
          <b-thead ref="empThead">
            <b-tr>
              <b-th v-for="(field, index) in fields" :key="index" class="emp-th" :style="{width: field.width}">
                <template v-if="field.key !== 'chkAll'">
                  {{field.label}}
                  <BIconSortDown   class="employeeList-hover" :class="{'sortActive':field.key === sortingKey}"  v-if="field.key !== 'rowNum' && field.sort === 'desc'" @click="sortingEmploy(field)" style="font-size: 0.8rem; color: #0d6efd" />
                  <BIconSortUpAlt  class="employeeList-hover" :class="{'sortActive':field.key === sortingKey}"  v-if="field.key !== 'rowNum' && field.sort === 'asc'"  @click="sortingEmploy(field)" style="font-size: 0.8rem; color: #0d6efd" />
                  <BIconFunnel     class="employeeList-hover" v-if="field.filter && !field.filterFill" style="font-size: 0.8rem; color: #6c757d" v-b-modal.department-fill />
                  <BIconFunnelFill class="employeeList-hover" v-if="field.filter && field.filterFill"  style="font-size: 0.8rem; color: #6c757d" v-b-modal.department-fill />
                  <div class="emp-grip" @mousedown="empGripMouseDown($event, index)" @dblclick="empGripDbClick(index)"></div>
                </template>
                <template v-else>
                  <b-form-checkbox @click="chkAll" :checked="field.chk" />
                  <div class="emp-grip" @mousedown="empGripMouseDown($event, index)" @dblclick="empGripDbClick(index)"></div>
                </template>
              </b-th>
            </b-tr>
          </b-thead>
          <b-tbody>
            <b-tr v-for="(emps, idx) in employees" :key="idx">
              <b-td>
                <div :style="{width: fields[0].width}">
                  <b-form-checkbox :checked="emps['chk']" @click="chkOne($event, idx)" />
                </div>
              </b-td>
              <b-td>
                <div class="emp-ellipsis" v-b-tooltip.hover :title="emps['rowNum']" :style="{width: fields[1].width}">
                  {{emps['rowNum']}}
                </div>
              </b-td>
              <b-td>
                <div class="emp-ellipsis" v-b-tooltip.hover :title="emps['employeeId']" :style="{width: fields[2].width}">
                  {{emps['employeeId']}}
                </div>
              </b-td>
              <b-td>
                <div class="emp-ellipsis" v-b-tooltip.hover :title="emps['firstName'] +' ' + emps['lastName']" :style="{width: fields[3].width}">
                  {{emps['firstName'] +' ' + emps['lastName']}}
                </div>
              </b-td>
              <b-td>
                <div class="emp-ellipsis" v-b-tooltip.hover :title="emps['email']" :style="{width: fields[4].width}">
                  {{emps['email']}}
                </div>
              </b-td>
              <b-td>
                <div class="emp-ellipsis" v-b-tooltip.hover :title="emps['phoneNumber']" :style="{width: fields[5].width}">
                  {{emps['phoneNumber']}}
                </div>
              </b-td>
              <b-td>
                <div class="emp-ellipsis" v-b-tooltip.hover :title="emps['hireDate']" :style="{width: fields[6].width}">
                  {{emps['hireDate']}}
                </div>
              </b-td>
              <b-td>
                <div class="emp-ellipsis" v-b-tooltip.hover :title="emps['jobId']" :style="{width: fields[7].width}">
                  {{emps['jobId']}}
                </div>
              </b-td>
              <b-td>
                <div class="emp-ellipsis" v-b-tooltip.hover :title="emps['salary'] === -1 ? 0 : emps['salary']" :style="{width: fields[8].width, textAlign: 'right'}">
                  {{ emps['salary'] === -1 ? 0 : emps['salary'] }}
                </div>
              </b-td>
              <b-td>
                <div class="emp-ellipsis" v-b-tooltip.hover :title="emps['commissionPct'] === -1 ? '-' : emps['commissionPct'] * 100" :style="{width: fields[9].width}">
                  {{ emps['commissionPct'] === -1 ? '-' : emps['commissionPct'] * 100  }}
                </div>
              </b-td>
              <b-td>
                <div class="emp-ellipsis" v-b-tooltip.hover :title="emps['managerFirstName'] + ' ' + emps['managerLastName']" :style="{width: fields[10].width}">
                  {{emps['managerFirstName'] + ' ' + emps['managerLastName']}}
                </div>
              </b-td>
              <b-td>
                <div class="emp-ellipsis"  v-b-tooltip.hover :title="emps['departmentName']" :style="{width: fields[11].width}">
                  {{emps['departmentName']}}
                </div>
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
            @ok="handleNoDataOk"
            @cancel="handleNoDataCancel"
        >
          <b-form-group label="Contain No Data" >
            <b-form-checkbox
                v-model="allNoDataSelected"
                :indeterminate="noDataIndeterminate"
                @change="noDataToggleAll"
            >
              {{ 'Select All' }}
            </b-form-checkbox>
            <b-form-checkbox-group
                id="noDataSelected"
                v-model="noDataSelected"
                :options="noDataOptions"
                name="noDataSelected"
                stacked
            ></b-form-checkbox-group>
          </b-form-group>
        </b-modal>
        <b-modal
            id="department-fill"
            ref="modal"
            title="Department List"
            okTitle="Filter"
            scrollable
            @ok="handleDepOk"
            @cancel="handleDepCancel"
        >
          <b-form-group>
            <b-form-checkbox
                v-model="allSelectDepCol"
                :indeterminate="depColIndeterminate"
                @change="depColToggleAll"
            >
              {{ 'Select All' }}
            </b-form-checkbox>
            <b-form-checkbox-group
                id="empDepList"
                v-model="selectDepCol"
                :options="empDepList"
                name="empDepList"
                stacked
            ></b-form-checkbox-group>
          </b-form-group>
        </b-modal>
        <b-modal
            id="emp-delete"
            title="Delete"
            @ok="empDelete"
        >
          <p class="my-4">Do you want to delete?</p>
        </b-modal>
      </div>
</template>

<script>
import {BIconFunnel, BIconFunnelFill, BIconSortDown, BIconSortUpAlt} from 'bootstrap-icons-vue'
import _ from "lodash";
import qs from "qs";

export default {
  name: 'employeeSingleList',

  components: {
    BIconFunnel,
    BIconFunnelFill,
    BIconSortDown,
    BIconSortUpAlt
  },

  data(){
    return {
      employees : [],
      selectedEmployees : [],
      searchForm: {
        empId: {
          text: 'Employee ID',
          value: ''
        },
        empFirst: {
          text: 'Employee First Name',
          value: ''
        },
        empLast: {
          text: 'Employee First Name',
          value: ''
        },
        email: {
          text: 'Email',
          value: ''
        },
        phone: {
          text: 'Phone',
          value: ''
        },
        hireDate: {
          text: 'HireDate',
          value: ''
        },
        jobId: {
          text: 'Job ID',
          value: ''
        },
        salary: {
          text: 'Salary $',
          value: ''
        },
        commission: {
          text: 'Commission (%)',
          value: ''
        },
        managerFirst: {
          text: 'Manager First Name',
          value: ''
        },
        managerLast: {
          text: 'Manager Last Name',
          value: ''
        },
        department: {
          text: 'Department',
          value: ''
        },
      },
      fields: [
        {key:'chkAll',         label:'chkAll',          width: '10px',   filter: false, defaultWidth: '10px',  chk: false}, //2%
        {key:'rowNum',         label:'No.',             width: '45px',   filter: false, defaultWidth: '45px',  sort: 'asc'}, //2%
        {key:'employeeId',     label:'ID',              width: '45px',   filter: false, defaultWidth: '45px',  sort: 'asc'}, //6%
        {key:'name',           label:'Name',            width: '90px',   filter: false, defaultWidth: '90px',  sort: 'asc'}, //8%
        {key:'email',          label:'Email',           width: '90px',   filter: false, defaultWidth: '90px',  sort: 'asc'}, //8%
        {key:'phoneNumber',    label:'Phone',           width: '110px',  filter: false, defaultWidth: '110px', sort: 'asc'}, //8%
        {key:'hireDate',       label:'HireDate',        width: '90px',   filter: false, defaultWidth: '90px',  sort: 'asc'}, //10%
        {key:'jobId',          label:'Job ID',          width: '90px',   filter: false, defaultWidth: '90px',  sort: 'asc'}, //8%
        {key:'salary',         label:'Salary $',        width: '80px',   filter: false, defaultWidth: '80px',  sort: 'asc'}, //8%
        {key:'commissionPct',  label:'Commission (%)',  width: '150px',  filter: false, defaultWidth: '150px', sort: 'asc'}, //17%
        {key:'manager',        label:'Manager',         width: '120px',  filter: false, defaultWidth: '120px', sort: 'asc'}, //10%
        {key:'departmentName', label:'Department',      width: '140px',  filter: true,  defaultWidth: '140px', sort: 'asc', filterFill: true}, //15%
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
      noDataSelectedConfirm: ['commissionNo', 'managerNo', 'departmentNo'],
      noDataSelected: ['commissionNo', 'managerNo', 'departmentNo'], // Must be an array reference!
      noDataOptions: [
        { text: 'No Commission', value: 'commissionNo' },
        { text: 'No Manager', value: 'managerNo' },
        { text: 'No Department', value: 'departmentNo' },
      ],
      allNoDataSelected : true,
      noDataIndeterminate : false,
      allSelectDepCol: false,
      depColIndeterminate: false,
      empDepList: [],
      selectDepCol: [], // Must be an array reference!
      selectDepColConfirm: [],
      url :{
        employeesColList: '/hr/empColList',
        employeesDepList: '/hr/empDepList',
        employeesList: '/hr/multiSearch/employees',
        employeesDelete: '/hr/empDelete',
      },
      isBusy: false,
      sorting: '',
      sortingKey: '',
      thElm: null,
      thElmIndex: null,
      startOffset: null,
    }
  },

  methods: {

    getHREmployees(page = 1){
        this.pageObject.page = page;
        this.$main.loading.show();
        return this.getHREmployeesApi(
            page
        ).then((res) => {
            //결과 처리
            if(res && res.status === 200){
              this.getHREmployeesRes(res.data);
            }
        })
         .catch(e => {
              console.log('error : ',e);
            })
         .finally(() => {
              this.$main.loading.hide();
            })
    },

    getHREmployeesRes(response){
      if(response && response.length > 0){
        this.employees = response;
        //화면에서 page를 만들어 보겠다.
        const totalCount = response[0].totalCount;
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
    },

    async selectRowChange(e){
      if(e === 0) return;             // 0 이면 호출하지 않는다.
      this.pageObject.perPageRow = e; // 선택된 option값을 넣어준다.
      await this.getHREmployees(1);    // 페이지 마다 보여주는 row수가 바뀌었으므로 1페이지 부터 시작한다.
    },

    async selectPageChange(e){
      if(e === 0) return;             // 0 이면 호출하지 않는다.
      this.pageObject.perGroupPage = e;
      await this.getHREmployees(1);                      // 페이지 마다 보여주는 row수가 바뀌었으므로 1페이지 부터 시작한다.
    },

    async onInputPage(){
      if(this.inputPage){
        const page = Number(this.inputPage.trim());
        if(!Number.isNaN(page) && page > 0){
          const perPage = page > this.pageObject.totalPage ? this.pageObject.totalPage : page;
          await this.getHREmployees(perPage);
          this.inputPage = perPage;
        }else{
          alert('유효한 값이 아닙니다.');
        }
      }
    },

    getHREmplColList(){
      this.$main.loading.show();
      return this.$http.get(this.url.employeesColList)
         .then((res) => {
            if(res && res.status === 200){
              const data = res.data;
              this.empColList = this.empColList.concat(data);
            }
          }).catch(e => {
            console.log('error : ',e);
          }).finally(() => {
            this.$main.loading.hide();
          });
    },

    getHREmplDepList(){
      this.$main.loading.show();
      return this.$http.get(this.url.employeesDepList)
          .then((res) => {
            if(res && res.status === 200 && res.data){
              const data = res.data;
              this.empDepList = this.empDepList.concat(data);
              this.selectDepCol = _.map(data, d => d.value);
            }
          }).catch(e => {
            console.log('error : ',e);
          }).finally(() => {
            this.$main.loading.hide();
          });
    },

    async searchChange(e){
      this.selectEmpCol = e;
      if(e === 'search'){
        this.searchInput = '';
        await this.getHREmployees(1);                      // 페이지 마다 보여주는 row수가 바뀌었으므로 1페이지 부터 시작한다.
      }
    },

    async searchEmpCol(){
        if(this.selectEmpCol === 'managerId' || this.selectEmpCol ===  'employeeId' || this.selectEmpCol === 'commissionPct'
            || this.selectEmpCol === 'salary' ||this.selectEmpCol === 'departmentId'){
          const searchInput =  Number(this.searchInput);
          if(Number.isNaN(searchInput)){
            alert('숫자를 입력하세요.');
            return;
          }
        }
        await this.getHREmployees(1);
    },

    async pagination(){
      await this.getHREmployees(this.pageObject.page);
    },

    handleNoDataOk() {
      this.noDataSelectedConfirm = this.noDataSelected;
    },

    handleNoDataCancel(){
      this.noDataSelected = this.noDataSelectedConfirm;
    },

    handleDepOk() {
      this.selectDepColConfirm = this.selectDepCol;
      if(this.empDepList.length ===  this.selectDepCol.length){
        this.fields[11].filterFill = true;
      }else{
        this.fields[11].filterFill = false;
      }
      this.getHREmployees(1);
    },

    handleDepCancel(){
      this.selectDepCol = this.selectDepColConfirm;
    },

    async getEmploy(){

      const col = this.$http.get(this.url.employeesColList);
      const dep = this.$http.get(this.url.employeesDepList);
      const emp = this.getHREmployeesApi(1);

      this.$main.loading.show();
      Promise.all([col, dep, emp])
         .then(res => {
           if(res && res.length > 0){
            _.each(res, (dt,i) => {
              let resData = dt && dt.status === 200 ? dt.data : null;
              if(resData){
                if(i === 0){
                  const colList = resData;
                  this.empColList = this.empColList.concat(colList);
                }else if(i === 1){
                  const depList = resData;
                  this.empDepList = depList;
                  this.selectDepCol = _.map(depList, d => d.value);
                }else if(i === 2){
                  this.getHREmployeesRes(resData);
                }
              }
            });
           }
         })
         .catch(e => {
            console.log('error  :', e);
         })
         .finally(() => {
           this.$main.loading.hide();
         });
    },

    async sortingEmploy(field){
      _.each(this.fields, f => {
        if(f.key !== field.key){
          f.sort = 'desc';
        }else{
          f.sort = f.sort === 'desc' ? 'asc' : 'desc'
          // if(f.sort === 'desc'){
          //   f.sort = 'asc';
          // }else if(f.sort === 'asc'){
          //   f.sort = 'desc';
          // }
        }
      });

      this.sorting = field.key + '_' + field.sort;
      this.sortingKey = field.key;
      await this.getHREmployees(this.pageObject.page);    // 페이지 마다 보여주는 row수가 바뀌었으므로 1페이지 부터 시작한다.
    },

    noDataToggleAll(checked){
      const noDataList = _.map(this.noDataOptions, o => o.value);
      this.noDataSelected = checked ? noDataList : [];
    },

    depColToggleAll(checked){
      const empDepList = _.map(this.empDepList, o => o.value);
      this.selectDepCol = checked ? empDepList : [];
    },

    getHREmployeesApi(page){

      const key = this.selectEmpCol;
      let value = this.searchInput;

      if(key && value){
        if(key === 'commissionPct'){
          value = value * 0.01;
        }
      }

      let params = {
        page: page,
        perPageRow: this.pageObject.perPageRow,
        perGroupPage: this.pageObject.perGroupPage,
        sorting: this.sorting,
        selectDepCol: this.selectDepCol,
        employeeId: this.searchForm.empId.value,
        firstName: this.searchForm.empFirst.value,
        lastName: this.searchForm.empLast.value,
        email: this.searchForm.email.value,
        phoneNumber: this.searchForm.phone.value,
        hireDate: this.searchForm.hireDate.value.replaceAll('-','.'),
        jobId: this.searchForm.jobId.value,
        salary: this.searchForm.salary.value ? this.searchForm.salary.value : -1,
        commissionPct: this.searchForm.commission.value ? this.searchForm.commission.value * 0.01 : -1,
        departmentName: this.searchForm.department.value,
        managerFirstName: this.searchForm.managerFirst.value,
        managerLastName: this.searchForm.managerLast.value,
      };

      _.each(this.noDataSelected, item => {
        params[item] = '1';
      });
      console.log('qs.stringify(params, { arrayFormat: \'brackets\' }) ; ', qs.stringify(params, { arrayFormat: 'brackets' }));

      return this.$http.get(this.url.employeesList + '?' + qs.stringify(params, { arrayFormat: 'brackets' }))
    },

    empGripMouseDown(e, i){
      const offsetWidth = e.target.parentNode.offsetWidth;
      const pageX = e.pageX;
      this.thElm = e.target.parentNode;
      this.thElmIndex = i;
      this.startOffset = offsetWidth - pageX;
    },

    empGripMouseMove(e){
      if(this.thElm){
        let width = this.startOffset + e.pageX

        //최솟값
        if(width < 45){
          width = 45;
        }

        //최대값
        let sum = 0;
        _.each(this.fields, (f, idx) => {
          if(idx !== this.thElmIndex){
            sum += parseInt(f.width, 10);
          }
        });

        if(sum + width > 1050){
          width = (1050 - sum) + 'px';
        }
        this.fields[this.thElmIndex].width = width + 'px';

      }
    },

    empGripMouseUp(){
      this.thElm = null;
      this.thElmIndex = null;
    },

    empGripDbClick(index){
      this.fields[index].width = this.fields[index].defaultWidth;
    },

    chkAll(e){
      const checked = e.target.checked;
      this.fields[0].chk = checked;
      this.employees = _.map(this.employees, e => {
        e.chk = checked;
        return e;
      });
      this.selectedEmployees = checked ? this.employees : [];
    },

    chkOne(e, index){
      this.employees[index].chk = e.target.checked;
      const checkedEmps = _.filter(this.employees, e => e.chk === true);
      this.selectedEmployees = checkedEmps;
      this.fields[0].chk = checkedEmps.length === this.employees.length ? true : false;
    },

    async empDelete(){
      const ids = _.map(this.selectedEmployees, e => e.employeeId);
      const params = {
        ids: ids
      }
      this.$main.loading.show();
      const res = await this.$http.delete(this.url.employeesDelete + '?' + qs.stringify(params, { arrayFormat: 'brackets' }))
        .then((res) => {
          if(res && res.status === 200){
            const data = res.data;
            if(data !== -1){
              return true;
            }
          }
        }).catch(e => {
          console.log('error : ',e);
        }).finally(() => {
          this.$main.loading.hide();
        });
      if(res){
        this.getHREmployees(1);
      }
    },

    reset(){
      this.searchForm.empId.value = '';
      this.searchForm.empFirst.value = '';
      this.searchForm.empLast.value = '';
      this.searchForm.email.value = '';
      this.searchForm.phone.value = '';
      this.searchForm.hireDate.value = '';
      this.searchForm.jobId.value = '';
      this.searchForm.salary.value= '';
      this.searchForm.commission.value = '';
      this.searchForm.managerFirst.value = '';
      this.searchForm.managerLast.value = '';
      this.searchForm.department.value = '' ;
    },

    multiSearch(){
      this.getHREmployees(1);
      console.log('this.searchForm ; ', this.searchForm);
    },

    hireDateOK(){

    },

    hireDateCancel(){

    },


  },

  watch: {
    noDataSelected(newValue) {
      if (newValue.length === 0) {
        this.noDataIndeterminate = false;
        this.allNoDataSelected = false;
      } else if (newValue.length === this.noDataOptions.length) {
        this.noDataIndeterminate = false;
        this.allNoDataSelected = true;
      } else {
        this.noDataIndeterminate = true;
        this.allNoDataSelected = false;
      }
    },
    selectDepCol(newValue) {
      if (newValue.length === 0) {
        this.depColIndeterminate = false;
        this.allSelectDepCol = false;
      } else if (newValue.length === this.empDepList.length) {
        this.depColIndeterminate = false;
        this.allSelectDepCol = true;
      } else {
        this.depColIndeterminate = true;
        this.allSelectDepCol = false;
      }
    },
    getValidationState({ dirty, validated, valid = null }) {
      return dirty || validated ? valid : null;
    },

  },

  created() {
    this.getEmploy();
  },

  updated() {
  },

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

  .emp-ellipsis{
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }

  .emp-th{
    position: relative;
  }

  .emp-grip{
    top: 0;
    right: 0;
    bottom: 0;
    width: 10px;
    position: absolute;
    cursor: col-resize;
  }

</style>