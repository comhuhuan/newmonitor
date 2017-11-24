package com.act.web.module.monitor.service;

import com.act.framework.util.PageResult;
import com.act.monitor.model.TabInstructionsInfo;
import com.act.web.module.monitor.vo.IssuedInstructionsVo;

public interface IssuedInstructionsService {

	String issuedInstructions(IssuedInstructionsVo issuedInstructionsVo) throws ClassNotFoundException;

	PageResult<TabInstructionsInfo> instructionsInfo(PageResult<TabInstructionsInfo> page, IssuedInstructionsVo issuedInstructionsVo);
}
