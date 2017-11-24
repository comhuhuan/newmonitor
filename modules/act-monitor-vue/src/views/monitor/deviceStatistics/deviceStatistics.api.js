import superagent from 'superagent'
import {resultUtil} from '../../../js/resultUtil'

const BASE = '/act-monitor-web'

export const initializeTitle = params => {
  return superagent.post(`${BASE}/monitor/process/initializeTitle.do`)
    .type('application/x-www-form-urlencoded')
    .accept('application/json')
    .send(params)
    .then(res => {
      return resultUtil(res)
    })
}

export const statusHistory = params => {
  return superagent.post(`${BASE}/monitor/deviceInfo/statusHistory.do`)
    .type('application/x-www-form-urlencoded')
    .accept('application/json')
    .send(params)
    .then(res => {
      return resultUtil(res)
    })
}

export const statusPageList = params => {
  return superagent.post(`${BASE}/monitor/deviceInfo/statusPageList.do`)
    .type('application/x-www-form-urlencoded')
    .accept('application/json')
    .send(params)
    .then(res => {
      return resultUtil(res)
    })
}

export const newStatus = params => {
  return superagent.post(`${BASE}/monitor/deviceInfo/newStatus.do`)
    .type('application/x-www-form-urlencoded')
    .accept('application/json')
    .send(params)
    .then(res => {
      return resultUtil(res)
    })
}

export const labelInfo = params => {
  return superagent.post(`${BASE}/monitor/deviceInfo/labelInfo.do`)
    .type('application/x-www-form-urlencoded')
    .accept('application/json')
    .send(params)
    .then(res => {
      return resultUtil(res)
    })
}
