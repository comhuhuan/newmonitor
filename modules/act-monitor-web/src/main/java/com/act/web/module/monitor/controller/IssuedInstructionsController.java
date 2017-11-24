package com.act.web.module.monitor.controller;


import com.act.framework.util.PageResult;
import com.act.monitor.model.TabInstructionsInfo;
import com.act.web.module.common.controller.BaseController;
import com.act.web.module.monitor.service.IssuedInstructionsService;
import com.act.web.module.monitor.vo.IssuedInstructionsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/common/instructions")
public class IssuedInstructionsController extends BaseController {
	private final Logger log = LoggerFactory.getLogger(IssuedInstructionsController.class);

	@Resource
	private IssuedInstructionsService issuedInstructionsService;


	@ResponseBody
	@RequestMapping(value = "/issuedInstructions.do")
	public Object issuedInstructions(IssuedInstructionsVo issuedInstructionsVo ) {
		String result = "";
		try {
			result = issuedInstructionsService.issuedInstructions(issuedInstructionsVo);

			return ajax(Status.success, result);
		} catch (Exception e) {
			return ajax(Status.error, "下发配置失败");
		}
	}


	@ResponseBody
	@RequestMapping(value = "/instructionsInfo.do")
	public Object instructionsInfo(PageResult<TabInstructionsInfo> page, IssuedInstructionsVo issuedInstructionsVo) {
		List<TabInstructionsInfo> resultList = null;
		try {
			page = issuedInstructionsService.instructionsInfo(page,issuedInstructionsVo);
		} catch (Exception e) {
			log.error("查询失败!", e);
			e.printStackTrace();
			return ajax(Status.error, "查询失败!");
		}
		return ajax(Status.success, page);
	}


}
