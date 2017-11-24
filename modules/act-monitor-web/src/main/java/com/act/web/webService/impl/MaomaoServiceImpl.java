package com.act.web.webService.impl;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import com.act.web.webService.MaomaoService;

@WebService(name = "MaomaoService",serviceName = "MaomaoService",
endpointInterface = "com.act.web.webService.MaomaoService")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class MaomaoServiceImpl implements  MaomaoService{

	@Override
	public String idc_command(String name, String age) {
		System.out.println(name+age);
		return "你好："+name;
	}

}
