package com.act.web.module.monitor.service.impl;

import com.act.framework.util.DbUtil;
import com.act.framework.util.PageResult;
import com.act.mapper.entity.Example;
import com.act.monitor.dao.ExceptionTableDao;
import com.act.monitor.entity.ExceptionTableEntity;
import com.act.monitor.model.ExceptionTable;
import com.act.web.module.monitor.service.AlarmListservice;
import com.act.web.module.monitor.vo.AlarmVo;
import com.sun.tools.corba.se.idl.ExceptionEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlarmListserviceImpl implements AlarmListservice {
    private final Logger log = LoggerFactory.getLogger(AlarmListserviceImpl.class);

       @Override
    public PageResult<AlarmVo> pageList(PageResult<AlarmVo> page, AlarmVo alarmVo) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT a.childclass as aa ,a.host,a.occurtime,a.parentclass ,a.status,a.type,SUM(valid) AS valid ,a.value, ");
        sb.append(" b.parentclassdetail ,b.childclassdetail as childclass ");
        sb.append(" FROM exception_table AS a LEFT JOIN `warningdetailtable` AS b  ");
        sb.append(" ON a.parentclass=b.parentclass and a.childclass = b.childclass ");
        sb.append(" WHERE a.parentclass='" + alarmVo.getParentclass().toString() + "' ");
        //根据前台传来参数判断查询异常或者全查
        if (alarmVo.getValid() == 1) {
            sb.append("and a.valid=1");
        }
        sb.append("  GROUP BY a.childclass,a.host");


        page = DbUtil.queryPageForObjectPageResult(sb.toString(), AlarmVo.class,
                page.getPageIndex(), page.getPageSize());
        return page;
    }
}
