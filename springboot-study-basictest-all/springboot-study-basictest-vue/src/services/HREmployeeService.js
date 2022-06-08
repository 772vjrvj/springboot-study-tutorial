import axios from 'axios'

const HREMPLOYEE_API_BASE_URL = '/hr'

class HREmployeeService{
  getHREmployees(page, perPageRow, perGroupPage, searchKey, searchValue){
    return axios.get(HREMPLOYEE_API_BASE_URL
        + `/employees?page=${page}&perPageRow=${perPageRow}&perGroupPage=${perGroupPage}&key=${searchKey}&value=${searchValue}`)
  }
  getHREmplColList(){
    return axios.get(HREMPLOYEE_API_BASE_URL + '/empColList')
  }
}

export default new HREmployeeService()