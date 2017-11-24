import superagent from 'superagent'
import {resultUtil } from '../../../js/resultUtil'

let base = '/act-monitor-web'

export const login = params => {
  return superagent.post(`${base}/common/loginVue/login.do`).type('application/x-www-form-urlencoded').accept('application/json').send(params).then(res => resultUtil(res))
  // return superagent.get(`${base}/monitor/deviceCount/deviceCount.do`).type('application/x-www-form-urlencoded').accept('application/json').send(params).then(res => resultUtil(res));
}

export const cuInter = params => {
  return superagent.post(`${base}/monitor/deviceCount/cuInter.do`)
    .type('application/x-www-form-urlencoded')
    .accept('application/json')
    .then(res => {
      return resultUtil(res)
    })
}
