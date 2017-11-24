import superagent from 'superagent'
import {resultUtil} from '../../../js/resultUtil'
const BASE = '/act-monitor-web'

export const provList = params => {
  return superagent.post(`${BASE}/monitor/device/provList.do`)
    .type('application/x-www-form-urlencoded')
    .accept('application/json')
    .send(params)
    .then(res => {
      return resultUtil(res)
    })
}

export const getIdcByPro = params => {
  return superagent.post(`${BASE}/monitor/device/getIdcByProv.do`)
    .type('application/x-www-form-urlencoded')
    .accept('application/json')
    .send(params)
    .then(res => {
      return resultUtil(res)
    })
}

export const getHouseByIdc = params => {
  return superagent.post(`${BASE}/monitor/device/getHouseByIdc.do`)
    .type('application/x-www-form-urlencoded')
    .accept('application/json')
    .send(params)
    .then(res => {
      return resultUtil(res)
    })
}

export const pagingList = params => {
  return superagent.post(`${BASE}/monitor/device/pagingList.do`)
    .type('application/x-www-form-urlencoded')
    .accept('application/json')
    .send(params)
    .then(res => {
      return resultUtil(res)
    })
}

export const exportByJson = params => {
  return superagent.post(`${BASE}/monitor/device/exportByJson.do`)
    .type('application/x-www-form-urlencoded')
    .accept('application/json')
    .send(params)
    .then(res => {
      return resultUtil(res)
    })
}
