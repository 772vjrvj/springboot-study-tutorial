<template>
  <div class="container">
    <div class="sub-left-container">
      <div ref="subleftcontainertop" class="sub-left-container-top">
        <table>
          <thead>
            <tr>
              <th>11</th>
            </tr>
          </thead>
        </table>
      </div>
      <div ref="subleftcontainerbottom" class="sub-left-container-bottom">
        <table>
          <thead>
            <tr>
              <th>22</th>
            </tr>
          </thead>
        </table>
      </div>
    </div>
    <div class="sub-right-container">
      <div ref="subrightcontainertop" class="sub-right-container-top">
        <table>
          <thead>
          <tr>
            <th>33</th>
          </tr>
          </thead>
        </table>
      </div>
      <div ref="subrightcontainerbottom" class="sub-right-container-bottom" v-on:scroll="scrollevent">
        <table>
          <thead>
          </thead>
          <tbody>
            <tr>
              <td>44</td>
            </tr>
          </tbody>
        </table>
      </div>
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
        total :
          {
            id: 'tot', title: 'Total', rowspan: '', deps: '', icon: [], button:'',  children: [
              { id: 'dept', title: 'Department',      rowspan: '', children: [
                { id: 'ad',   title: 'Administration',  rowspan: ''},
                { id: 'mk',   title: 'Marketing',       rowspan: ''},
                { id: 'pu',   title: 'Purchasing',      rowspan: ''},
                { id: 'hr',   title: 'Human Resources', rowspan: ''},
                ]},
              { id: 'job', title: 'Job', rowspan: '', children: [
                { id: 'ad_asst', title: 'Administration Assistant', rowspan: ''},
                { id: 'mk_man', title: 'Marketing Manager', rowspan: ''},
                { id: 'pu_man', title: 'Purchasing Manager', rowspan: ''},
                { id: 'hr_rep', title: 'Human Resources Representative', rowspan: ''},
              ]}
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
        },
      scrollevent(e){
        console.log('this.$ref.subleftcontainerbottom ; ', this.$refs.subleftcontainerbottom.scrollLeft);
        console.log('this.$ref.subrightcontainertop ; ', this.$refs.subrightcontainertop.scrollTop);
        console.log('e.tartget.scrollLeft ; ', e.target.scrollLeft);
        console.log('e.tartget.scrollTop ; ', e.target.scrollTop);
        this.$refs.subleftcontainerbottom.scrollTop = e.target.scrollTop;
        this.$refs.subrightcontainertop.scrollLeft = e.target.scrollLeft;

      }



    },
    created() {
    },
    mounted() {
    },
    updated() {
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
  height: 700px;
  background-color: skyblue;
}




.sub-right-container {
  float: right;
  width: 80%;
  height: 700px;
  background-color: greenyellow;
}

.sub-left-container-top{
  width: 260px;
  height: 200px;
  background-color: dodgerblue;
}

.sub-left-container-bottom{
  width: 260px;
  height: 500px;
  background-color: aquamarine;
  overflow-y: hidden;
}

.sub-left-container-bottom table{
  width: 250px;
  height: 800px;
  background-color: #df7fff;
}


.sub-right-container-top{
  width: 1056px;
  height: 200px;
  background-color: red;
  overflow-x: hidden;
}

.sub-right-container-top table{
  width: 1600px;
  height: 190px;
  background-color: #ff8800;
}

.sub-right-container-bottom{
  width: 1074px;
  height: 518px;
  background-color: #42b983;
  overflow: scroll;
}

.sub-right-container-bottom table{
  width: 1600px;
  height: 800px;
  background-color: #4268b9;
}

</style>