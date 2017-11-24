import superagent from 'superagent'
import {resultUtil} from '../../../js/resultUtil'
const BASE = '/act-monitor-web'

export const IDCRelation = params => {
  return superagent.post(`${BASE}/monitor/deviceStatus/tuopu.do`)
    .type('application/x-www-form-urlencoded')
    .accept('application/json')
    .send(params)
    .then(res => {
      return resultUtil(res)
    })
}

export const houseStatus = params => {
  return superagent.post(`${BASE}/monitor/deviceStatus/queryDeviceByHouse.do`)
    .type('application/x-www-form-urlencoded')
    .accept('application/json')
    .send(params)
    .then(res => {
      return resultUtil(res)
    })
}
