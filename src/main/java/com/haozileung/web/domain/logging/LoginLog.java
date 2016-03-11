/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2016
 */

package com.haozileung.web.domain.logging;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Haozi
 * @version 1.0
 * @since 1.0
 */

public class LoginLog implements Serializable {

    // columns START
    private Long loginLogId;
    private Long userId;
    private String ipAddress;
    private Date loginTime;
    private Integer result;
    private String browser;
    private String remarks;
    // columns END

    public LoginLog() {
    }

    public LoginLog(Long loginLogId) {
        this.loginLogId = loginLogId;
    }

    public Long getLoginLogId() {
        return this.loginLogId;
    }

    public void setLoginLogId(Long value) {
        this.loginLogId = value;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long value) {
        this.userId = value;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    public void setIpAddress(String value) {
        this.ipAddress = value;
    }

    public Date getLoginTime() {
        return this.loginTime;
    }

    public void setLoginTime(Date value) {
        this.loginTime = value;
    }

    public Integer getResult() {
        return this.result;
    }

    public void setResult(Integer value) {
        this.result = value;
    }

    public String getBrowser() {
        return this.browser;
    }

    public void setBrowser(String value) {
        this.browser = value;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public void setRemarks(String value) {
        this.remarks = value;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public int hashCode() {
        return new HashCodeBuilder().append(getLoginLogId()).toHashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof LoginLog == false)
            return false;
        if (this == obj)
            return true;
        LoginLog other = (LoginLog) obj;
        return new EqualsBuilder().append(getLoginLogId(), other.getLoginLogId()).isEquals();
    }
}
