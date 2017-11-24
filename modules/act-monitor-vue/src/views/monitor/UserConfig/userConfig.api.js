import {ajaxPost} from '../../../js/resultUtil'

export const pagingList = params => ajaxPost('/common/userConfig/pagingList.do', params)

export const save = params => ajaxPost('/common/userConfig/save.do', params)

export const remove = params => ajaxPost('/common/userConfig/remove.do', params)

// 得到所有用户/加载角色穿梭框选择列表tab_role
export const initialize = params => ajaxPost('/common/userConfig/initialize.do', params)

// 得到需要修改的用户
export const getSysUser = params => ajaxPost('/common/userConfig/getSysUser.do', params)

// 得到所有用户/加载角色穿梭框选择列表tab_role
export const exisUser = params => ajaxPost('/common/userConfig/exisUser.do', params)

export const editPw = params => ajaxPost('/common/userConfig/editPw.do', params)

// 得到需要导出excel的json数据
export const exportByJson = params => ajaxPost('/common/userConfig/exportByJson.do', params)
