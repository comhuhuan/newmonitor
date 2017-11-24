import superagent from 'superagent'
import resultUtil from '../../../js/resultUtil'

let base = '/act-monitor-web'

export const editPw = params => {
  return superagent.post(`${base}/common/loginVue/modifyPw.do`).type('application/x-www-form-urlencoded').accept('application/json').send(params).then(res => res.body)
}

export const loadSub = params => {
  return superagent.post(`${base}/common/login/loadSub.do`).accept('application/json').send(params).then(res => resultUtil(res))
}
export const welcome = params => {
  return superagent.post(`${base}/common/login/welcome.do`).accept('application/json').send(params).then(res => res.body)
}
