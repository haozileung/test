package com.haozileung.web.domain.system.logging;

/**
 * Created by haozi on 16-3-8.
 */
public class OperateLog {
    private Long operateLogId;
    private Long operationId;
    private String params;
    private Long userId;
    private String ipAddress;
    private String remarks;

    public Long getOperateLogId() {
        return operateLogId;
    }

    public void setOperateLogId(Long operateLogId) {
        this.operateLogId = operateLogId;
    }

    public Long getOperationId() {
        return operationId;
    }

    public void setOperationId(Long operationId) {
        this.operationId = operationId;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
