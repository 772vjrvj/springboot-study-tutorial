<template>
  <div class = "container employeeList-container">
    <div class="row justify-content-start">
      <div class="text-start h4 font-weight-normal mb-5 position-relative"> Theme List.</div>
    </div>
    <div class="row justify-content-start mb-3">
      <b-card>
        Multi Search Under Construction
      </b-card>
    </div>
    <div class="row justify-content-start">form</div>
    <div class="row justify-content-center">
      <b-table-simple
          striped
          hover
          bordered
          responsive
      >
        <b-thead ref="themeThead">
          <b-tr>
            <b-th v-for="(field, index) in fields" :key="index" class="emp-th" :style="{width: field.width}">
              <template v-if="field.key !== 'chkAll'">
                {{field.label}}
                <font-awesome-icon class="fa-default" :class="{'sortActive':field.key === sortingKey}"  v-if="field.key !== 'rowNum' && field.sort === 'desc'" @click="sortingEmploy(field)" icon="fa-solid fa-arrow-down-short-wide" />
                <font-awesome-icon class="fa-default" :class="{'sortActive':field.key === sortingKey}"  v-if="field.key !== 'rowNum' && field.sort === 'asc'"  @click="sortingEmploy(field)" icon="fa-solid fa-arrow-up-wide-short" />
                <div class="emp-grip" @mousedown="empGripMouseDown($event, index)" @dblclick="empGripDbClick(index)"></div>
              </template>
              <template v-else>
                <b-form-checkbox @change="chkAll" :checked="field.chk" />
                <div class="emp-grip" @mousedown="empGripMouseDown($event, index)" @dblclick="empGripDbClick(index)"></div>
              </template>
            </b-th>
          </b-tr>
        </b-thead>
      </b-table-simple>
    </div>
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
            @page-click="pagination"
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
import _ from "lodash";

export default {
  name: "ThemeList.vue",
  data(){
    return {
      fields: [
        {key:'chkAll',         label:'ChkAll',          width: '10px',   defaultWidth: '10px',  chk: false},  //2%
        {key:'rowNum',         label:'No.',             width: '30px',   defaultWidth: '30px',  sort: 'asc'}, //2%
        {key:'name',           label:'Name',            width: '90px',   defaultWidth: '90px',  sort: 'asc'}, //6%
        {key:'desc',           label:'Desc',            width: '90px',   defaultWidth: '90px',  sort: 'asc'}, //8%
        {key:'style',          label:'Style',           width: '50px',   defaultWidth: '50px',  sort: 'asc'}, //8%
        {key:'type',           label:'Type',            width: '50px',   defaultWidth: '50px',  sort: 'asc'}, //8%
        {key:'createUser',     label:'Create User',     width: '50px',   defaultWidth: '50px',  sort: 'asc'}, //10%
        {key:'createDate',     label:'Create Date',     width: '50px',   defaultWidth: '50px',  sort: 'asc'}, //8%
      ],
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
      url :{
        themeList: '/hr/app-main/theme/list',
      },
      sortingKey: '',
      thElm: null,
      thElmIndex: null,
      startOffset: null,
    }
  },
  methods: {
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

    chkAll(chk){
      this.fields[0].chk = chk;
      this.employees = _.map(this.employees, e => {
        e.chk = chk;
        return e;
      });
      this.selectedEmployees = chk ? this.employees : [];
    },

    chkOne(chk, index){
      this.employees[index].chk = chk;
      const checkedEmps = _.filter(this.employees, e => e.chk === true);
      this.selectedEmployees = checkedEmps;
      this.fields[0].chk = checkedEmps.length === this.employees.length ? true : false;
    },
  }
}
</script>

<style scoped>
  .employeeList-container{
    width: 1350px;
    position: relative;
  }


  .emp-multi-table{
    overflow: hidden;
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

  .fa-default{
    outline: none
  }
  .fa-default:hover{
    cursor: pointer;
  }

</style>