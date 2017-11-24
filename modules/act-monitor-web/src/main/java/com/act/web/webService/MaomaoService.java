package com.act.web.webService;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(name = "MaomaoService")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface MaomaoService {
	@WebMethod(operationName="test")
	public String idc_command(
			@WebParam(name = "name") String name, 
			@WebParam(name = "age") String age);
}
