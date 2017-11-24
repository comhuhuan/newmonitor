import Mock from 'mockjs'
import Login from '../views/common/Login/Login.mock'
import HomeSub from '../views/common/HomeSub/HomeSub.mock'
import allEquip from '../views/monitor/allEquip/allEquip.mock'
import deviceStatus from '../views/monitor/deviceStatus/deviceStatus.mock'
import deviceSearch from '../views/monitor/deviceSearch/deviceSearch.mock'
import userManage from '../views/monitor/userManage/userManage.mock'
import deviceHistory from '../views/monitor/deviceHistory/deviceHistory.mock'
import deviceRelation from '../views/monitor/deviceRelation/deviceRelation.mock'
import deviceStatistics from '../views/monitor/deviceStatistics/deviceStatistics.mock'
import alarmManage from '../views/monitor/alarm/alarmManage.mock'

// import Test from '../views/common/Test/test.mock'
// import GarbageData from '../views/monitor/GarbageData/garbageData.mock'
// import SqlEngine from '../views/monitor/SqlEngine/sqlEngine.mock'
import Test from '../views/common/Test/test.mock'
import GarbageData from '../views/xxx/GarbageData/garbageData.mock'
import SqlEngine from '../views/xxx/SqlEngine/sqlEngine.mock'

addToMock(Login)
addToMock(HomeSub)
addToMock(Test)
addToMock(GarbageData)
addToMock(SqlEngine)
addToMock(allEquip)
addToMock(deviceStatus)
addToMock(deviceSearch)
addToMock(userManage)
addToMock(deviceHistory)
addToMock(deviceRelation)
addToMock(deviceStatistics)
addToMock(alarmManage)

function addToMock (list) {
  list.forEach(n => {
    Mock.mock(n.path, n.data)
  })
}

export default Mock
