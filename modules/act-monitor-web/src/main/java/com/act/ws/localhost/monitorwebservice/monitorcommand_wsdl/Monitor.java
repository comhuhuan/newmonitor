
package com.act.ws.localhost.monitorwebservice.monitorcommand_wsdl;

import javax.xml.namespace.QName;
import javax.xml.ws.*;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * gSOAP 2.8.19 generated service definition
 * 
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "Monitor", targetNamespace = "http://localhost//MonitorWebservice/monitorCommand?wsdl", wsdlLocation = "http://172.31.82.78:63225/MonitorWebservice/monitorCommand?wsdl")
public class Monitor
    extends Service
{

    private final static URL MONITOR_WSDL_LOCATION;
    private final static WebServiceException MONITOR_EXCEPTION;
    private final static QName MONITOR_QNAME = new QName("http://localhost//MonitorWebservice/monitorCommand?wsdl", "Monitor");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://172.31.82.78:63225/MonitorWebservice/monitorCommand?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        MONITOR_WSDL_LOCATION = url;
        MONITOR_EXCEPTION = e;
    }

    public Monitor() {
        super(__getWsdlLocation(), MONITOR_QNAME);
    }

    public Monitor(WebServiceFeature... features) {
        super(__getWsdlLocation(), MONITOR_QNAME, features);
    }

    public Monitor(URL wsdlLocation) {
        super(wsdlLocation, MONITOR_QNAME);
    }

    public Monitor(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, MONITOR_QNAME, features);
    }

    public Monitor(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public Monitor(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns MonitorPortType
     */
    @WebEndpoint(name = "Monitor")
    public MonitorPortType getMonitor() {
        return super.getPort(new QName("http://localhost//MonitorWebservice/monitorCommand?wsdl", "Monitor"), MonitorPortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns MonitorPortType
     */
    @WebEndpoint(name = "Monitor")
    public MonitorPortType getMonitor(WebServiceFeature... features) {
        return super.getPort(new QName("http://localhost//MonitorWebservice/monitorCommand?wsdl", "Monitor"), MonitorPortType.class, features);
    }

    private static URL __getWsdlLocation() {
        if (MONITOR_EXCEPTION!= null) {
            throw MONITOR_EXCEPTION;
        }
        return MONITOR_WSDL_LOCATION;
    }

}
