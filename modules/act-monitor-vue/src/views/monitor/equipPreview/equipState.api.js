import superagent from 'superagent'
import {resultUtil} from '../../../js/resultUtil'
let base = '/act-monitor-web'

export const pagingList = params => {
  return superagent.post(`${base}/monitor/sqlEngine/pagingList.do`).type('application/x-www-form-urlencoded').accept('application/json').send(params).then(res => resultUtil(res))
}

export const pagingGarbage = params => {
  return superagent.post(`${base}/equipMonitor/equipPreview/pagingGarbage.do`).type('application/x-www-form-urlencoded').accept('application/json').send(params).then(res => resultUtil(res))
}

export const clearData = params => {
  return superagent.post(`${base}/equipMonitor/equipPreview/clearData.do`).type('application/x-www-form-urlencoded').accept('application/json').send(params).then(res => resultUtil(res))
}

export const initialize = params => {
  return superagent.post(`${base}/equipMonitor/equipPreview/initialize.do`).type('application/x-www-form-urlencoded').accept('application/json').send(params).then(res => resultUtil(res))
}
