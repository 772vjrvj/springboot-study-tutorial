<template>
  <div class="main-container">
    <div class="sub-container" @mousemove="viewTooltip">
      <p class="t_tooltip" ref="t_tooltip" />
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
        <div class="sub-left-container-bottom" ref="subLeftContainerBottom">
          <table>
            <thead>
              <template v-for="(item, index) in subTitle" :key="index">
                <template v-for="(child, idx) in item.children">
                  <template v-for="(c, i) in child.children">
                    <tr v-if="idx ===0 && i===0" :key="index+idx+i">
                      <td :rowspan="item.rowspan">
                       {{item.title}}
                      </td>
                      <td :rowspan="child.rowspan">
                        {{child.title}}
                      </td>
                      <td :id="c.id">
                        {{c.title}}
                      </td>
                    </tr>
                    <tr v-else-if="idx !==0 && i===0" :key="idx+i">
                      <td :rowspan="child.rowspan">
                        {{item.title}}
                      </td>
                      <td :id="c.id">
                        {{c.title}}
                      </td>
                    </tr>
                    <tr v-if="i!==0" :key="i" >
                      <td :id="c.id">
                        {{c.title}}
                      </td>
                    </tr>
                  </template>
                </template>
              </template>
            </thead>
          </table>
        </div>
      </div>
      <div class="sub-right-container">
        <div class="sub-right-container-top" ref="subRightContainerTop">
          <table>
            <thead>
              <tr v-for="(title, index) in columnTitle" :key="index">
                <th v-for="(name, idx) in title" :key="idx">
                 {{name}}
                </th>
              </tr>
            </thead>
          </table>
        </div>
        <div class="sub-right-container-bottom" @scroll="scrollEvent">
          <table>
            <tbody>
              <tr v-for="(member, index) in teamMemberList" :key="index">
                <td v-for="(m, i) in member" :key="i" >
                  {{m}}
                </td>
              </tr>
            </tbody>
          </table>
        </div>
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
          teamList: '/team/list',
          memberList: '/team/member'
        },


        mainTitle: [
          { id: 'LOC',   title: 'Location', rowspan: 2, colspan: 2,  children: [
            { id: 'CT',   title: 'Country',   children: [

              ]},
            { id: 'CI',   title: 'City',   children: [

              ]},
            ]},
          { id: 'DP', title: 'Team', children: [
            { id: 'SECTION', title: 'Section', children: [
              { id: 'JOB', title: 'Job'},
            ]},
          ]},
        ], //메임 이름
        subTitle :[], //메인 하위 이름
        jobOrderList: [], //member 세팅할시 순서로 필요
        columnTitle: [],
        countryName: [],
        cityName: [],
        partName: [],
        teamMemberList: [],
        tooltipTimer: null
      }
    },
    methods: {

      viewTooltip(e){
        clearTimeout(this.tooltipTimer);
        this.$refs.t_tooltip.style.display = 'none';
        if(e.target.tagName === 'TD' || e.target.tagName === 'TH'){
          this.tooltipTimer = setTimeout(() => {
            this.$refs.t_tooltip.style.display = 'block';
            this.$refs.t_tooltip.style.left = e.pageX + 'px';
            this.$refs.t_tooltip.style.top = e.pageY - 30 + 'px';
            this.$refs.t_tooltip.innerText = e.target.innerText;
          }, 1000)
        }
      },

      scrollEvent(e){
        this.$refs.subLeftContainerBottom.scrollTop = e.target.scrollTop;
        this.$refs.subRightContainerTop.scrollLeft = e.target.scrollLeft;
      },

      setTeam(dataList){
        let team = [];
        let jobOrderList = [];
        _.each(dataList, data => {
          jobOrderList.push(data.jobId); //member 세팅할시 순서로 필요

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
        this.jobOrderList = jobOrderList;
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

      setTeamList(){
        return axios.get(this.url.teamList).then(res => {
          if(res && res.status === 200 && res.data){
            const data = res.data;
            console.log('setTeamList data : ',data);
            let team = this.setTeam(data);
            team = this.setSection(team);
            this.subTitle = team;
            return team;
          }
        }).catch(e => {
          console.log('error : ',e);
        }).finally(() => {

        })
      },

      setMemberList(){
        axios.get(this.url.memberList).then(res => {
          if(res && res.status === 200 && res.data){
            const data = res.data;

            let grp = _.groupBy(data, 'locationId');

            const grpKeys = _.keys(grp);
            const grpKeysLen =  grpKeys.length;
            const jobIdListLen = this.jobOrderList.length;

            let countryName = new Array(grpKeysLen);
            let cityName = new Array(grpKeysLen);
            let partName = new Array(grpKeysLen);

            let teaMemberArr = [];

            for (let i = 0; i < jobIdListLen; i++) {
              const grpCntArr = new Array(grpKeysLen).fill('');
              teaMemberArr.push(grpCntArr);
            }

            let colIndex = -1;
            for (const grpKey in grp) {
              colIndex++;
              let g = grp[grpKey];

                for (let i = 0; i < g.length; i++) {
                  const d = g[i];

                  //column header 세팅
                  if(i === 0){
                    const countryNm = d.countryName ? d.countryName : 'unknown';
                    const cityNm = d.city ? d.city : 'unknown';
                    const partNm = d.part ? d.part : 'unknown';
                    countryName[colIndex] = countryNm;
                    cityName[colIndex] = cityNm;
                    partName[colIndex] = partNm;
                  }

                  const jol = this.jobOrderList;
                  const rowIndex = _.findIndex(jol,jobId =>
                    jobId === d.jobId
                  );
                  teaMemberArr[rowIndex][colIndex] = d.firstName + ' ' + d.lastName;
              }
            }

            this.columnTitle = [countryName, cityName, partName];
            this.countryName = countryName;
            this.cityName = cityName;
            this.partName = partName;

            this.teamMemberList = teaMemberArr;
          }
        })
      },

      async getTeamList(){
        await this.setTeamList();
        await this.setMemberList();
      }

    },

    created() {
      this.getTeamList();
    },

    mounted() {

    },

    updated() {

    },
}
</script>
<style scoped>
.main-container{
  position: relative;
}

.sub-container{
  content: "";
  display: block;
  clear: both;
  width: 1227px;
  height: 700px;
  overflow: hidden;
  margin: 0 auto;
}

.sub-container table{
  width: 100%;
  table-layout:fixed;
}
.sub-container table th,
.sub-container table td{
  border: 1px solid #c9c9c9;
  padding: 5px;
  font-size: 14px;
  width: 100px;
  overflow: hidden;
  white-space : nowrap;
  text-overflow: ellipsis;
}
.sub-container table td{
  height: 54px;
}
.sub-container table .last_right_t{
  border-right: none;
}
.sub-container table .last_bottom_t{
  border-bottom: 2px solid #656565;
}
.sub-container table .last_top_t{
  border-bottom: none;
}
.sub-container table .last_left_t{
  border-bottom: none;
}


.sub-container .t_tooltip{
  display: none;
  position: absolute;
  background-color: rgba(0,0,0,0.5);
  color: #fff;
  padding: 4px 7px;
  white-space : nowrap;
  z-index: 999;
  font-size: 14px;
}


.sub-left-container {
  float: left;
  width: 400px;
  height: 700px;
}
.sub-left-container .sub-left-container-top {
  background-color: #f6f6f6;
  width: 100%;
  height: 97px;
  border-right: 1px solid #656565;
}
.sub-left-container .sub-left-container-top table{
  table-layout: initial;
}
.sub-left-container .sub-left-container-top table th{
  width: 33.33%;
}
.sub-left-container .sub-left-container-top table th:last-child{
  border-right: none;
}
.sub-left-container .sub-left-container-top table tr:last-child th{
  border-bottom: 1px solid #656565;
}


.sub-left-container .sub-left-container-bottom {
  height: 400px;
  width: 100%;
  overflow-x: scroll;
  overflow-y: hidden;
}
.sub-left-container .sub-left-container-bottom table td{
  font-weight: bold;
}
.sub-left-container .sub-left-container-bottom table td:last-child{
  border-right: 1px solid #656565;
}
.sub-left-container .sub-left-container-bottom table tr:first-child td{
  border-top: none;
}
.sub-left-container .sub-left-container-bottom table tr:last-child td{
  border-bottom: none;
}


.sub-left-container .sub-left-container-bottom::-webkit-scrollbar {
  width: 17px;
}
.sub-left-container .sub-left-container-bottom::-webkit-scrollbar-track {
  background-color: rgb(255, 255, 255);
  border-top: 1px solid #c9c9c9;
}
.sub-left-container .sub-left-container-bottom::-webkit-scrollbar-thumb {
  background-color: rgb(255, 255, 255);
}


.sub-right-container {
  position: relative;
  float: left;
  width: 800px;
  height: 700px;
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
.sub-right-container .sub-right-container-top table tr th:first-child {
  border-left: none;
}
.sub-right-container .sub-right-container-top table tr th:last-child{
  border-right: none;
}
.sub-right-container .sub-right-container-top table tr:last-child th{
  border-bottom: 1px solid #656565;
}

.sub-right-container .sub-right-container-top::-webkit-scrollbar {
  width: 17px;
}
.sub-right-container .sub-right-container-top::-webkit-scrollbar-track {
  background-color: rgb(255, 255, 255);
  border-left: 1px solid #c9c9c9;
}

.sub-right-container .sub-right-container-bottom {
  height: 400px;
  width: 817px;
  overflow: scroll;
}
.sub-right-container .sub-right-container-bottom table tr:first-child td {
  border-top: none;
}
.sub-right-container .sub-right-container-bottom table tr:last-child td {
  border-bottom: none;
}
.sub-right-container .sub-right-container-bottom table tr td:first-child  {
  border-left: none;
}
.sub-right-container .sub-right-container-bottom table tr td:last-child  {
  border-right: none;
}

.sub-right-container .sub-right-container-bottom::-webkit-scrollbar {
  width: 17px;
}
.sub-right-container .sub-right-container-bottom::-webkit-scrollbar-thumb {
  background-color: #dcdcdc;
}
.sub-right-container .sub-right-container-bottom::-webkit-scrollbar-track:vertical {
  border-left: 1px solid #c9c9c9;
  background-color: #f6f6f6;
}
.sub-right-container .sub-right-container-bottom::-webkit-scrollbar-track:horizontal {
  border-top: 1px solid #c9c9c9;
  background-color: #f6f6f6;
}




</style>