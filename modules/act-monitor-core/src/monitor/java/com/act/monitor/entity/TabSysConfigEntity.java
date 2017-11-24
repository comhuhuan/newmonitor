package com.act.monitor.entity;

import com.act.framework.entity.StandardEntity;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tab_sysconfig")
public class TabSysConfigEntity extends StandardEntity {
    @Id
    @NotEmpty
    @Length(max=20)
    private String configid;

    @NotEmpty
    @Length(max=255)
    private String defaultval;

    @NotEmpty
    @Length(max=255)
    private String configval;

    @NotEmpty
    @Length(max=255)
    private String note;

    public static final String Property_Defaultval = "defaultval";

    public static final String Property_Configid = "configid";

    public static final String Property_Note = "note";

    public static final String Property_Configval = "configval";

    /**
     * @return configid
     */
    public String getConfigid() {
        return configid;
    }

    /**
     * @param configid
     */
    public void setConfigid(String configid) {
        addChangeField("configid",this.configid,configid);
        this.configid = configid;
    }

    /**
     * @return defaultval
     */
    public String getDefaultval() {
        return defaultval;
    }

    /**
     * @param defaultval
     */
    public void setDefaultval(String defaultval) {
        addChangeField("defaultval",this.defaultval,defaultval);
        this.defaultval = defaultval;
    }

    /**
     * @return configval
     */
    public String getConfigval() {
        return configval;
    }

    /**
     * @param configval
     */
    public void setConfigval(String configval) {
        addChangeField("configval",this.configval,configval);
        this.configval = configval;
    }

    /**
     * @return note
     */
    public String getNote() {
        return note;
    }

    /**
     * @param note
     */
    public void setNote(String note) {
        addChangeField("note",this.note,note);
        this.note = note;
    }
}