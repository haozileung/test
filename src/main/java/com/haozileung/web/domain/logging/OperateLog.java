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

/**
 * @author Haozi
 * @version 1.0
 * @since 1.0
 */
public class OperateLog implements Serializable {

    // columns START
    private Long operateLogId;
    private Long operationId;
    private String params;
    private Long userId;
    private String ipAddress;
    private String remarks;
    // columns END

    public OperateLog() {
    }

    public OperateLog(Long operateLogId) {
        this.operateLogId = operateLogId;
    }

    public Long getOperateLogId() {
        return this.operateLogId;
    }

    public void setOperateLogId(Long value) {
        this.operateLogId = value;
    }

    public Long getOperationId() {
        return this.operationId;
    }

    public void setOperationId(Long value) {
        this.operationId = value;
    }

    public String getParams() {
        return this.params;
    }

    public void setParams(String value) {
        this.params = value;
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
        return new HashCodeBuilder().append(getOperateLogId()).toHashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof OperateLog == false)
            return false;
        if (this == obj)
            return true;
        OperateLog other = (OperateLog) obj;
        return new EqualsBuilder().append(getOperateLogId(), other.getOperateLogId()).isEquals();
    }
}
