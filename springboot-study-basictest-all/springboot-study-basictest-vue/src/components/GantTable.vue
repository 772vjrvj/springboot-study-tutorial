<template>
  <div class="container">
    <div class="sub-left-container">
      <table>
        <colgroup>
          <col style="width: 80px;" />
          <col style="width: 60px;" />
        </colgroup>
        <thead>
        </thead>
        <tbody>
          <template v-for="(item, i) in colTitList" :key="i">
            <tr>
              <th class="mainTitle" :rowspan="item.children.length+1">{{item.title}}</th>
              <th class="subTitle" :colspan="aSPan">{{item.children[0].title}}</th>
            </tr>
            <tr v-for="(child, c) in item.children" :key="c">
              <th class="subTitle" v-if="c > 0">
                {{child.title}}
              </th>
            </tr>
          </template>
        </tbody>
      </table>
    </div>

    <div class="sub-right-container">
      <table>
        <colgroup>

        </colgroup>
        <thead>
        </thead>
        <tbody>
         </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import _ from "lodash"
export default {
    name: 'GantChartTest',
    data(){
      return {
        //실제 DB에서는 rowspan, deps, icon, button 은 안넘어 온다. 화면에서 동적으로 세팅한다.
        job :
          {
            id: 'tot', title: 'Total', rowspan: '', deps: '', icon: [], button:'',  children: [
              { id: 'AD', title: 'Administration',      rowspan: '', children: [
                { id: 'AD_PRES',   title: 'President',  rowspan: ''},
                { id: 'AD_VP',   title: 'Vice President',       rowspan: ''},
                { id: 'AD_ASST',   title: 'Assistant',      rowspan: ''},
                ]},
              { id: 'FI', title: 'Finance', rowspan: '', children: [
                { id: 'FI_MGR', title: 'Manager', rowspan: ''},
                { id: 'FI_ACCOUNT', title: 'Accountant', rowspan: ''},
                { id: 'AC_MGR', title: 'Manager', rowspan: ''},
                { id: 'AC_ACCOUNT', title: 'Accountant', rowspan: ''},
              ]},
              { id: 'SA', title: 'Sales', rowspan: '', children: [
                  { id: 'SA_MAN', title: 'Sales Manager', rowspan: ''},
                  { id: 'SA_REP', title: 'Sales Representative', rowspan: ''},
                  { id: 'PU_MAN', title: 'Purchasing Manager', rowspan: ''},
                  { id: 'PU_CLERK', title: 'Purchasing Clerk', rowspan: ''},
                ]},
            ]
          },

        addHd : {
          id: 'ad_asst', title: 'Administration Assistant', children: [
            { id: 'ad_asst_1', title: '', children: [
                { id: 'profit', title: 'Profit'},
                { id: 'loss', title: 'Loss'},
                { id: 'subTotal', title: 'Sub Total'},
              ]},
          ]
        },
        adAsst : {
          id: 'ad_asst', title: 'Administration Assistant', children: [
            { id: 'ad_asst_1', title: 'Administration Assistant A Team', children: [
                { id: 'profit', title: 'Profit'},
                { id: 'loss', title: 'Loss'},
                { id: 'subTotal', title: 'Sub Total'},
              ]},
            { id: 'ad_asst_2', title: 'Administration Assistant B Team', children: [
                { id: 'profit', title: 'Profit'},
                { id: 'loss', title: 'Loss'},
                { id: 'subTotal', title: 'Sub Total'},
              ]},
            { id: 'ad_asst_3', title: 'Administration Assistant C Team', children: [
                { id: 'profit', title: 'Profit'},
                { id: 'loss', title: 'Loss'},
                { id: 'subTotal', title: 'Sub Total'},
              ]},
          ]
        },
      }
    },
    methods: {
        setRowHeader(){
          const testArr = ['ad_asst','mk_man','pu_man', 'hr_rep'];
          _.each(testArr, (item) => {
            let addColTit = _.cloneDeep(this.addColTit);
            addColTit['title'] = item;
            addColTit['id'] = item;
            this.colTitList.push(addColTit);
          });
          console.log('this.colTitList : ', this.colTitList);
        }


    },
    created() {
      console.log('created');
    },
    mounted() {
      console.log('mounted');
      this.setRowHeader();
    },
    updated() {
      console.log('updated');
    },

}
</script>
<style>
.container{
  content: "";
  display: block;
  clear: both;
  width: 1800px;
  border: 1px solid red;
}

.sub-left-container {
  float: left;
  width: 20%;
  border: 1px solid skyblue;
}

.sub-right-container {
  float: right;
  width: 80%;
  border: 1px solid greenyellow;
}

.mainTitle, .subTitle{
  border: 1px solid black;
  width: 150px;
}



</style>