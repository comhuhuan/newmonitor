import {ajaxPost} from '../../../js/resultUtil'

export const initialize = params => ajaxPost('/common/alarmSysConfig/initialize.do', params)

export const updateConfig = params => ajaxPost('/common/alarmSysConfig/updateConfig.do', params)

export const resetConfig = params => ajaxPost('/common/alarmSysConfig/resetConfig.do', params)
