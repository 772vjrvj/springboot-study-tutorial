<template>
  <div class="container">
    <div class="text-start fs-4 mb-lg-5 position-relative">Team List.</div>

    <div class="row justify-content-start">
      <div class="col-auto">
        <div class="pt-1"><span>Total Row : </span></div>
      </div>
      <div class="col-auto">
        <div class="pt-1"><span>Total Team : </span></div>
      </div>
    </div>

    <div class="sub-left-container">
      <div class="sub-left-container-top">
        <table>
          <thead>
            <tr>
              <th colspan="2" rowspan="2">
                {{mainTitle[0].title}}
              </th>
              <th>
                {{mainTitle[0].children[0].title}}
              </th>
            </tr>
            <tr>
              <th>
                {{mainTitle[0].children[1].title}}
              </th>
            </tr>
            <tr>
              <th>
                {{mainTitle[1].title}}
              </th>
              <th>
                {{mainTitle[1].children[0].title}}
              </th>
              <th>
                {{mainTitle[1].children[0].children[0].title}}
              </th>
            </tr>
          </thead>
        </table>
      </div>
      <div class="sub-left-container-bottom" ref="subleftcontainerbottom">
        <table>
          <thead>
            <template v-for="(item, index) in subTitle" :key="index">
              <template v-for="(child, idx) in item.children">
                <template v-for="(c, i) in child.children">
                  <tr v-if="idx ===0 && i===0" :key="index+idx+i">
                    <th :rowspan="item.rowspan">
                      {{item.title}}
                    </th>
                    <th :rowspan="child.rowspan">
                      {{child.title}}
                    </th>
                    <th :id="c.id">
                      {{c.title}}
                    </th>
                  </tr>
                  <tr v-else-if="idx !==0 && i===0" :key="idx+i">
                    <th :rowspan="child.rowspan">
                      {{child.title}}
                    </th>
                    <th :id="c.id">
                      {{c.title}}
                    </th>
                  </tr>
                  <tr v-if="i!==0" :key="i">
                    <th :id="c.id">
                      {{c.title}}
                    </th>
                  </tr>
                </template>
              </template>
            </template>
          </thead>
        </table>
      </div>
    </div>
    <div class="sub-right-container">
      <div class="sub-right-container-top" ref="subrightcontainertop">
        <table>
          <thead>
            <tr>
              <th v-for="(h, i) in headerObj" :key="i">
                {{h.COUNTRY}}
              </th>
            </tr>
            <tr>
              <th v-for="(h, i) in headerObj" :key="i">
                {{h.CITY}}
              </th>
            </tr>
            <tr>
              <th v-for="(h, i) in headerObj" :key="i">
                {{h.TEAM}}
              </th>
            </tr>
          </thead>
        </table>
      </div>
      <div class="sub-right-container-bottom" @scroll="scrollEvent">
        <table>
          <tbody>
            <tr v-for="(obj, index) in contentObj" :key="index">
              <td v-for="(o, i) in obj" :key="i">
                {{o}}
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
import _ from "lodash"
import axios from 'axios'

export default {
    name: 'GantChartTest',
    data(){
      return {
        url: {
          teamList: '/team/list'
        },


        //기본적인 약속
        // main title은 2rows에 2번째로 row에 column이 2개 혹은 3개
        // sub title은 column이 2개 또는 3개 이다.
        // 실제 DB에서는 rowspan, deps, icon, button 은 안넘어 온다. 화면에서 동적으로 세팅한다. 아래는 서버에서 넘어온
        // 데이터 형식이다.
        //왼쪽 헤더의 dept에 따라 넓이를 동적으로 정해주어야 한다. 1~3까지만 지원


        //순서를 위한 job list
        titleList: [],
        mainTitle: [
          { id: 'LOC',   title: 'Location', rowspan: 2, colspan: 2,  children: [
            { id: 'CT',   title: 'Country',   children: [

              ]},
            { id: 'CI',   title: 'City',   children: [

              ]},
            ]},
          { id: 'DP', title: 'Department', children: [
            { id: 'SECTION', title: 'Section', children: [
              { id: 'JOB', title: 'Job', children: [

                ]},
            ]},
          ]},
        ]
        ,
        //DB에서 가져올 수도 있고 미리 만들어 놓을 수도 있다. 여기선 편의상 미리 만들어 놓은 rowspan만 동적으로 넣자
        // subTitle :[
        //   { id: 'TAD', title: 'Total Administration', children: [
        //     { id: 'AD', title: 'Administration', children: [
        //       { id: 'AD_PRES', title: 'AD_PRES'},
        //       { id: 'AD_VP',   title: 'AD_VP'},
        //       { id: 'AD_ASST', title: 'AD_ASST'},
        //       ]},
        //     ]},
        //   { id: 'EX', title: 'Exchequer', children: [
        //     { id: 'FA', title: 'Finance', children: [
        //       { id: 'FI_MGR',     title: 'FI_MGR'},
        //       { id: 'FI_ACCOUNT', title: 'FI_ACCOUNT'},
        //       { id: 'AC_MGR',     title: 'AC_MGR'},
        //       { id: 'AC_ACCOUNT', title: 'AC_ACCOUNT'},
        //       ]},
        //     { id: 'SH', title: 'Stock', children: [
        //       { id: 'ST_MAN',     title: 'ST_MAN'},
        //       { id: 'ST_CLERK',   title: 'ST_CLERK'},
        //       { id: 'SH_CLERK',   title: 'SH_CLERK'},
        //       ]},
        //     ]},
        //   { id: 'ST', title: 'Sales', children: [
        //     { id: 'SP', title: 'Sales & Purchasing', children: [
        //       { id: 'SA_MAN',   title: 'SA_MAN'},
        //       { id: 'SA_REP',   title: 'SA_REP'},
        //       { id: 'PU_MAN',   title: 'PU_MAN'},
        //       { id: 'PU_CLERK', title: 'PU_CLERK'}
        //       ]},
        //     { id: 'MK', title: 'Marketing', children: [
        //       { id: 'MK_MAN',   title: 'MK_MAN'},
        //       { id: 'MK_REP',   title: 'MK_REP'}
        //       ]},
        //     ]},
        //   { id: 'HR', title: 'Human Resources', children: [
        //     { id: 'HP', title: 'Human & Public', children: [
        //       { id: 'HR_REP', title: 'HR_REP'},
        //       { id: 'PR_REP', title: 'PR_REP'}
        //     ]},
        //   ]},
        //   { id: 'ITD', title: 'IT Development', children: [
        //     { id: 'IT', title: 'IT', children: [
        //         { id: 'IT_PROG', title: 'IT_PROG'}
        //       ]},
        //   ]},
        // ],
        subTitle :[],
        headerObj: [],
        contentObj: [],
      }
    },
    methods: {
      setBodyRightBottom(result){
        _.each(this.titleList, (title, index) => {
          let resList = [];
          _.each(result, res => {
            resList.push(res[title]);
          });
          this.contentObj[index] = resList;
        });
      },
      setRowHeader(){
        const subTitleObj = this.subTitleObj;
        _.each(subTitleObj.children, o1 => {
          o1.rowspan = 0;

          _.each(o1.children, o2 => {
            o2.rowspan = o2.children.length;
            o1.rowspan = o1.rowspan + o2.rowspan;
          });

        });
        this.subTitleObj = subTitleObj;

        _.each(subTitleObj.children, o1 => {
          _.each(o1.children, o2 => {
            _.each(o2.children, o3 => {
              this.titleList.push(o3.id);
            });
          });
        });

      },
      scrollEvent(e){
        this.$refs.subleftcontainerbottom.scrollTop = e.target.scrollTop;
        this.$refs.subrightcontainertop.scrollLeft = e.target.scrollLeft;
      },
      setTeam(dataList){
        let team = [];
        _.each(dataList, data => {
          const index = _.findIndex(team, o =>
              o.id === data.teamId
          );
          const childObj = {
            jobId: data.jobId,
            jobTitle: data.jobTitle,
            sectionId: data.sectionId,
            sectionName: data.sectionName
          }
          if(index > -1){
            team[index].children.push(childObj);
            team[index].rowspan = team[index].rowspan + 1;
          }else{
            const obj = {
              id: data.teamId,
              title: data.teamName,
              rowspan: 1,
              children: [childObj]
            }
            team.push(obj);
          }
        });
        return team;
      },
      setSection(teamList){
        _.each(teamList, team => {
          let sectionList = [];
          _.each(team.children, child => {
            const index = _.findIndex(sectionList, o =>
                o.id === child.sectionId
            );
            const childObj = {
              id: child.jobId,
              title: child.jobTitle,
            }
            if(index > -1){
              sectionList[index].children.push(childObj);
              sectionList[index].rowspan = sectionList[index].rowspan + 1;
            }else{
              const obj = {
                id: child.sectionId,
                title: child.sectionName,
                rowspan: 1,
                children: [childObj]
              }
              sectionList.push(obj);
            }
          });
          team.children = sectionList;
        });
        return teamList;
      },
    },
    created() {
      axios.get(this.url.teamList).then(res => {
        if(res && res.status === 200 && res.data){
          const dataList = res.data;
          let team = this.setTeam(dataList);
          team = this.setSection(team);
          this.subTitle = team;
        }
      });
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
  width: 1000px;
  margin: 0 auto;
}



.sub-left-container {
  float: left;
  width: 30%;
  height: 700px;
}

.sub-left-container table td,th{
  padding: 5px;
  font-size: 14px;
}

.sub-left-container .sub-left-container-top {
  background-color: #f6f6f6;
  height: 97px;
}
.sub-left-container .sub-left-container-top table{
  width: 100%;
}
.sub-left-container .sub-left-container-top table th{
  border: 1px solid #c9c9c9;
  width: 33.33%;
}
.sub-left-container .sub-left-container-top table .main{
  width: 100%;
}
.sub-left-container .sub-left-container-top table .sub{
  width: 50%;
}

.sub-left-container .sub-left-container-bottom {
  height: 400px;
  overflow-y: hidden;
  overflow-x: scroll;
}
.sub-left-container .sub-left-container-bottom::-webkit-scrollbar {
  width: 17px;
}
.sub-left-container .sub-left-container-bottom::-webkit-scrollbar-track {
  background-color: rgb(255, 255, 255);
}
.sub-left-container .sub-left-container-bottom::-webkit-scrollbar-thumb {
  background-color: rgb(255, 255, 255);
}

.sub-left-container .sub-left-container-bottom table{
  width: 100%;
}
.sub-left-container .sub-left-container-bottom table th{
  border: 1px solid #c9c9c9;
  width: 33.33%;
}

.sub-right-container {
  position: relative;
  float: right;
  width: 70%;
  height: 700px;
}

.sub-right-container table td,th{
  padding: 5px;
  font-size: 14px;
}


.sub-right-container .sub-right-container-top {
  box-sizing: border-box;
  background-color: #f6f6f6;
  height: 97px;
  width: 817px;
  overflow-x: hidden;
  overflow-y: scroll;
  position: relative;
}
.sub-right-container .sub-right-container-top::-webkit-scrollbar {
  width: 17px;
}
.sub-right-container .sub-right-container-top::-webkit-scrollbar-track {
  background-color: rgb(255, 255, 255);
}
.sub-right-container .sub-right-container-bottom::-webkit-scrollbar-thumb {
  background-color: rgb(255, 255, 255);
}



.sub-right-container .sub-right-container-top table{
  width: 2400px;
}
.sub-right-container .sub-right-container-top table th{
  border: 1px solid #c9c9c9;
}
.sub-right-container .sub-right-container-top table .main{
  width: 300px;
}
.sub-right-container .sub-right-container-top table .sub{
  width: 150px;
}

.sub-right-container .sub-right-container-bottom {
  height: 400px;
  width: 817px;
  overflow: scroll;
}

.sub-right-container .sub-right-container-bottom::-webkit-scrollbar {
  width: 17px;
}
.sub-right-container .sub-right-container-bottom::-webkit-scrollbar-track {
  background-color: white;
}
.sub-right-container .sub-right-container-bottom::-webkit-scrollbar-thumb {
  background-color: #eaeaea;
}


.sub-right-container .sub-right-container-bottom table{
  width: 2400px;
}
.sub-right-container .sub-right-container-bottom table td{
  border: 1px solid #c9c9c9;
  width: 150px;
}
</style>