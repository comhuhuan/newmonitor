package com.act.monitor.entity;

import com.act.framework.entity.StandardEntity;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "td_province")
public class TdProvinceEntity extends StandardEntity {
    @Id
    @NotEmpty
    @Column(name = "PROV_ID")
    private long provId;

    @NotEmpty
    @Length(max=128)
    @Column(name = "PROV_NAME")
    private String provName;

    @NotEmpty
    @Length(max=128)
    @Column(name = "PROV_CODE")
    private String provCode;

    @NotEmpty
    @Column(name = "PROV_ACTIVE")
    private int provActive;

    @Length(max=255)
    @Column(name = "PROV_NOTE")
    private String provNote;

    public static final String Property_ProvName = "provName";

    public static final String Property_ProvNote = "provNote";

    public static final String Property_ProvId = "provId";

    public static final String Property_ProvActive = "provActive";

    public static final String Property_ProvCode = "provCode";

    /**
     * @return PROV_ID
     */
    public long getProvId() {
        return provId;
    }

    /**
     * @param provId
     */
    public void setProvId(long provId) {
        addChangeField("provId",this.provId,provId);
        this.provId = provId;
    }

    /**
     * @return PROV_NAME
     */
    public String getProvName() {
        return provName;
    }

    /**
     * @param provName
     */
    public void setProvName(String provName) {
        addChangeField("provName",this.provName,provName);
        this.provName = provName;
    }

    /**
     * @return PROV_CODE
     */
    public String getProvCode() {
        return provCode;
    }

    /**
     * @param provCode
     */
    public void setProvCode(String provCode) {
        addChangeField("provCode",this.provCode,provCode);
        this.provCode = provCode;
    }

    /**
     * @return PROV_ACTIVE
     */
    public int getProvActive() {
        return provActive;
    }

    /**
     * @param provActive
     */
    public void setProvActive(int provActive) {
        addChangeField("provActive",this.provActive,provActive);
        this.provActive = provActive;
    }

    /**
     * @return PROV_NOTE
     */
    public String getProvNote() {
        return provNote;
    }

    /**
     * @param provNote
     */
    public void setProvNote(String provNote) {
        addChangeField("provNote",this.provNote,provNote);
        this.provNote = provNote;
    }
}