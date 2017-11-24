
package com.act.ws;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="monitorCommand" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "monitorCommand"
})
@XmlRootElement(name = "monitorCommand")
public class MonitorCommand {

    @XmlElement(required = true)
    protected String monitorCommand;

    /**
     * Gets the value of the monitorCommand property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMonitorCommand() {
        return monitorCommand;
    }

    /**
     * Sets the value of the monitorCommand property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMonitorCommand(String value) {
        this.monitorCommand = value;
    }

}
