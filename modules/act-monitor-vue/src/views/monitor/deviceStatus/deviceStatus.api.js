import superagent from 'superagent'
import {resultUtil} from '../../../js/resultUtil'
const BASE = '/act-monitor-web'

export const deviceInfo = params => {
  return superagent.post(`${BASE}/monitor/deviceCount/deviceInfo.do`)
    .type('application/x-www-form-urlencoded')
    .accept('application/json')
    .send(params)
    .then(res => {
      return resultUtil(res)
    })
}

export const cuDeviceInfoHistory = params => {
  return superagent.post(`${BASE}/monitor/deviceCount/cuDeviceInfoHistory.do`)
    .type('application/x-www-form-urlencoded')
    .accept('application/json')
    .send(params)
    .then(res => {
      return resultUtil(res)
    })
}

export const euDeviceInfoHistory = params => {
  return superagent.post(`${BASE}/monitor/deviceCount/euDeviceInfoHistory.do`)
    .type('application/x-www-form-urlencoded')
    .accept('application/json')
    .send(params)
    .then(res => {
      return resultUtil(res)
    })
}

export const badDevicePageList = params => {
  return superagent.post(`${BASE}/monitor/deviceCount/badDevicePageList.do`)
    .type('application/x-www-form-urlencoded')
    .accept('application/json')
    .send(params)
    .then(res => {
      return resultUtil(res)
    })
}
