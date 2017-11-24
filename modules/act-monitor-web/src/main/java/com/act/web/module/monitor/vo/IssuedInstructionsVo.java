package com.act.web.module.monitor.vo;

import com.act.monitor.model.TabInstructionsInfo;

import java.util.List;

public class IssuedInstructionsVo {

	private  String type;
	private List<String> ipList ;
	private String  xml;
	private String ws_interface ;
	private List<TabInstructionsInfo> tabInstructionsInfoList;


	public List<TabInstructionsInfo> getTabInstructionsInfoList() {
		return tabInstructionsInfoList;
	}

	public void setTabInstructionsInfoList(List<TabInstructionsInfo> tabInstructionsInfoList) {
		this.tabInstructionsInfoList = tabInstructionsInfoList;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getIpList() {
		return ipList;
	}

	public void setIpList(List<String> ipList) {
		this.ipList = ipList;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public String getWs_interface() {
		return ws_interface;
	}

	public void setWs_interface(String ws_interface) {
		this.ws_interface = ws_interface;
	}


	@Override
	public String toString() {
		return "IssuedInstructionsVo{" +
				"type='" + type + '\'' +
				", ipList=" + ipList +
				", xml='" + xml + '\'' +
				", ws_interface='" + ws_interface + '\'' +
				", tabInstructionsInfoList=" + tabInstructionsInfoList +
				'}';
	}
}
