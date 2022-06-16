import axios from 'axios'

const HREMPLOYEE_API_BASE_URL = '/hr'

class HREmployeeService{
  getHREmployees(page, perPageRow, perGroupPage, searchKey, searchValue, noDataList){

    if(searchKey && searchValue){
      if(searchKey === 'commissionPct'){
        searchValue = searchValue * 0.01;
      }
    }
    const noDataParams = noDataList.map((item) => `${item}No=1`).join('&');

    return axios.get(`${HREMPLOYEE_API_BASE_URL}/employees?page=${page}&perPageRow=${perPageRow}&perGroupPage=${perGroupPage}&key=${searchKey}&value=${searchValue}&${noDataParams}`);
  }
  getHREmplColList(){
    return axios.get(HREMPLOYEE_API_BASE_URL + '/empColList')
  }
}

export default new HREmployeeService()