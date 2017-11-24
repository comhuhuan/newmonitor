import superagent from 'superagent'
import {resultUtil} from '../../../js/resultUtil'
let base = '/act-monitor-web'

export const pagingList = params => {
  return superagent.post(`${base}/monitor/sqlEngine/pagingList.do`).type('application/x-www-form-urlencoded').accept('application/json').send(params).then(res => resultUtil(res))
}

export const save = params => {
  return superagent.post(`${base}/monitor/sqlEngine/save.do`).type('application/x-www-form-urlencoded').accept('application/json').send(params).then(res => resultUtil(res))
}

export const remove = params => {
  return superagent.post(`${base}/monitor/sqlEngine/remove.do`).type('application/x-www-form-urlencoded').accept('application/json').send(params).then(res => resultUtil(res))
}
