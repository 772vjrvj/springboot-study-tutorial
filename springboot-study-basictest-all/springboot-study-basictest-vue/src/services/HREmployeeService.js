const HREMPLOYEE_API_BASE_URL = '/hr'

class HREmployeeService{
  getHREmployees(page, perPageRow, perGroupPage, searchKey, searchValue, noDataList, sorting){

    if(searchKey && searchValue){
      if(searchKey === 'commissionPct'){
        searchValue = searchValue * 0.01;
      }
    }
    const noDataParams = noDataList.map((item) => `${item}No=1`).join('&');

    return this.$http.get(`${HREMPLOYEE_API_BASE_URL}/employees?page=${page}&perPageRow=${perPageRow}&perGroupPage=${perGroupPage}&key=${searchKey}&value=${searchValue}&${noDataParams}&sorting=${sorting}`);
  }
  getHREmplColList(){
    console.log('this : ', this);
    console.log('this.$http : ', this.$http);
    return this.$http.get(HREMPLOYEE_API_BASE_URL + '/empColList')
  }
}

export default new HREmployeeService()