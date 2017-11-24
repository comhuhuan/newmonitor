/**
 * @Title: GarbageDataController.java 
 * @Package com.act.web.module.bdad.controller 
 * @Description: (垃圾数据管理控制器) 
 * @author   fmj
 * @modifier fmj
 * @date 2017-5-5 下午7:42:13
 * @version V1.0   
 */
package com.act.web.module.xxx.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.act.framework.util.PageResult;
import com.act.web.module.common.controller.BaseController;
import com.act.web.module.xxx.service.GarbageDataService;
import com.act.web.module.xxx.vo.GarbageDataVo;
import com.act.web.module.xxx.vo.ReportViewVo;

@Controller
@RequestMapping("/xxx/garbageData")
public class GarbageDataController extends BaseController {

	private final Logger log = LoggerFactory
			.getLogger(GarbageDataController.class);

	@Resource
	private GarbageDataService garbageDataService;

	// Initialization
	@ResponseBody
	@RequestMapping(value = "/initialize.do")
	public Object initialize() {
		try{
			Object result = garbageDataService.initialize();
			return ajax(Status.success, result);
		}catch (Exception e) {
			e.printStackTrace();
			log.error("获取查询条件失败!", e);
			return ajax(Status.error, "获取查询条件失败!");
		}
	}

	/**
	 * @Title: pagingList
	 * @Description: 分页查询t_bdad_garbagedata 并返回select sql字段总数
	 * @create 2017-5-10 下午4:58:58
	 * @update 2017-5-10 下午4:58:58
	 */
	@ResponseBody
	@RequestMapping(value = "/pagingList.do")
	public Object pagingList(PageResult<GarbageDataVo> page,
			GarbageDataVo garbageDataVo) {
		try {
			page = garbageDataService.pagingList(page, garbageDataVo);
		} catch (Exception e) {
			log.error("分页查询失败!", e);
			return ajax(Status.error, "查询失败!");
		}
		return ajax(Status.success, page);
	}

	/**
	 * 
	 * @Title: pagingGarbage
	 * @Description: 根据t_bdad_garbagedata 表select_sql字段 返回垃圾数据page 以及表头title
	 * @create 2017-5-10 下午7:03:17
	 * @update 2017-5-10 下午7:03:17
	 */
	@ResponseBody
	@RequestMapping(value = "/pagingGarbage.do")
	public Object pagingGarbage(PageResult<Map<String, Object>> page,
			ReportViewVo reportViewVo) {
		try {
			ReportViewVo result = garbageDataService.pagingGarbage(page,
					reportViewVo);
			return ajax(Status.success, result);
		} catch (Exception e) {
			log.error("查看详情失败!", e);
			return ajax(Status.error, "查看详情失败!");
		}
	}

	/**
	 * 
	 * @Title: clearData
	 * @Description: 根据t_bdad_garbagedata 表delete_sql字段 删除数据库中内容
	 * @return Object 返回值为0时.出现异常
	 * @throws
	 * @create 2017-5-12 下午2:13:57
	 * @update 2017-5-12 下午2:13:57
	 */
	@ResponseBody
	@RequestMapping(value = "/clearData.do")
	public Object clearData(String garbageDelete) {
		try {
			Integer deletes = garbageDataService.clearData(garbageDelete);
			return ajax(Status.success, deletes);
		} catch (Exception e) {
			log.error("清除失败!", e);
			return ajax(Status.error, "清除失败!");
		}
	}

}
