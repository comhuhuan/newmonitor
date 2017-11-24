import {ajaxPost} from '../../../js/resultUtil'

export const initialize = params => ajaxPost('/common/euSysConfig/initialize.do', params)

export const updateConfig = params => ajaxPost('/common/euSysConfig/updateConfig.do', params)

export const resetConfig = params => ajaxPost('/common/euSysConfig/resetConfig.do', params)

export const issuedInstructions = params => ajaxPost('/common/instructions/issuedInstructions.do', params)

export const instructionsInfo = params => ajaxPost('/common/instructions/instructionsInfo.do', params)
