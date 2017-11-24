import {ajaxPost} from '../../../js/resultUtil'

export const pagingList = params => ajaxPost('/common/roleConfig/pagingList.do', params)

export const initialize = params => ajaxPost('/common/roleConfig/initialize.do', params)

export const exisRole = params => ajaxPost('/common/roleConfig/exisRole.do', params)

export const save = params => ajaxPost('/common/roleConfig/save.do', params)

export const getSysRole = params => ajaxPost('/common/roleConfig/getSysRole.do', params)

export const remove = params => ajaxPost('/common/roleConfig/remove.do', params)
