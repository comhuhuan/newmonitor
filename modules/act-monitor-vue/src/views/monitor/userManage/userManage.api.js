import superagent from 'superagent'
import {resultUtil} from '../../../js/resultUtil'
const BASE = '/act-monitor-web'

export const pagingList = params => {
  return superagent.post(`${BASE}/monitor/user/pagingList.do`)
    .type('application/x-www-form-urlencoded')
    .accept('application/json')
    .send(params)
    .then(res => {
      return resultUtil(res)
    })
}

export const getIdcList = params => {
  return superagent.post(`${BASE}/monitor/user/getIdcList.do`)
    .type('application/x-www-form-urlencoded')
    .accept('application/json')
    .send(params)
    .then(res => {
      return resultUtil(res)
    })
}

export const remove = params => {
  return superagent.post(`${BASE}/monitor/user/remove.do`)
    .type('application/x-www-form-urlencoded')
    .accept('application/json')
    .send(params)
    .then(res => {
      return resultUtil(res)
    })
}

export const addUser = params => {
  return superagent.post(`${BASE}/monitor/user/addUser.do`)
    .type('application/x-www-form-urlencoded')
    .accept('application/json')
    .send(params)
    .then(res => {
      return resultUtil(res)
    })
}
