import superagent from 'superagent'
import {resultUtil} from '../../../js/resultUtil'

const BASE = '/act-monitor-web'

export const deviceCount = params => {
  return superagent.post(`${BASE}/monitor/deviceCount/deviceCount.do`)
    .type('application/x-www-form-urlencoded')
    .accept('application/json')
    .send(params)
    .then(res => {
      return resultUtil(res)
    })
}

export const pageList = params => {
  return superagent.post(`${BASE}/monitor/deviceCount/pageList.do`)
    .type('application/x-www-form-urlencoded')
    .accept('application/json')
    .send(params)
    .then(res => {
      return resultUtil(res)
    })
}
