import superagent from 'superagent'
let base = '/act-monitor-web'

export const editPw = params => {
  return superagent.post(`${base}/common/loginVue/modifyPw.do`).type('application/x-www-form-urlencoded').accept('application/json').send(params).then(res => res.body)
}
