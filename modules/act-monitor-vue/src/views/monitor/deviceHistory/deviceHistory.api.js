import superagent from 'superagent'
import {resultUtil} from '../../../js/resultUtil'
const BASE = '/act-monitor-web'

export const pageList = params => {
  return superagent.post(`${BASE}/monitor/historyState/pagingList.do`)
    .type('application/x-www-form-urlencoded')
    .accept('application/json')
    .send(params)
    .then(res => {
      return resultUtil(res)
    })
}

export const exportByJson = params => {
  return superagent.post(`${BASE}/monitor/historyState/exportByJson.do`)
    .type('application/x-www-form-urlencoded')
    .accept('application/json')
    .send(params)
    .then(res => {
      return resultUtil(res)
    })
}
