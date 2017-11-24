import {ajaxPost} from '../../../js/resultUtil'

export const initialize = params => ajaxPost('/common/cuSysConfig/initialize.do', params)

export const updateConfig = params => ajaxPost('/common/cuSysConfig/updateConfig.do', params)

export const resetConfig = params => ajaxPost('/common/cuSysConfig/resetConfig.do', params)

export const issuedInstructions = params => ajaxPost('/common/instructions/issuedInstructions.do', params)

export const instructionsInfo = params => ajaxPost('/common/instructions/instructionsInfo.do', params)
