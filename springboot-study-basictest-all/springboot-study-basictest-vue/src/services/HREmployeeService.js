import axios from 'axios'

const HREMPLOYEE_API_BASE_URL = '/hr'

class HREmployeeService{
  getHREmployees(page, perPageRow, perGroupPage){
    return axios.get(HREMPLOYEE_API_BASE_URL + `/employees?page=${page}&perPageRow=${perPageRow}&perGroupPage=${perGroupPage}`)
  }
  getHREmplColList(){
    return axios.get(HREMPLOYEE_API_BASE_URL + '/colList')
  }
}

export default new HREmployeeService()