import HomeSub from './views/common/HomeSub/HomeSub.vue'
import Login from './views/common/Login/Login.vue'
// TODO 子系统部署
// let routes = [
//     {
//         path: '/',
//         component: HomeSub,
//         name: '',
//         hidden: true
//     }
// ];

// TODO 独立部署
let routes = [
  {
    path: '/login',
    component: Login,
    name: '',
    hidden: true

  }
]

export default routes
