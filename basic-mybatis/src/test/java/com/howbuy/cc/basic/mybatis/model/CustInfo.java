package com.howbuy.cc.basic.mybatis.model;

/**
 * Created by xinwei.cheng on 2015/6/3.
 */
public class CustInfo {

    private Integer custNo;
    private String custName;
    private String regDt;
    private String f1AppDt;
    private Integer invstType;
    private String idNo;
    private String lastLoginDate;
    private String custType;
    private Integer currentFundType;
    private Integer currentProtocalType;
    private Integer hisFundType;
    private Double maxVol3Month;
    private Double maxVolAll;


    public String getRegDt() {
        return regDt;
    }

    public void setRegDt(String regDt) {
        this.regDt = regDt;
    }

    public Integer getCustNo() {
        return custNo;
    }

    public void setCustNo(Integer custNo) {
        this.custNo = custNo;
    }

    public String getF1AppDt() {
        return f1AppDt;
    }

    public void setF1AppDt(String f1AppDt) {
        this.f1AppDt = f1AppDt;
    }

    public Integer getInvstType() {
        return invstType;
    }

    public void setInvstType(Integer invstType) {
        this.invstType = invstType;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(String lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Integer getCurrentFundType() {
        return currentFundType;
    }

    public void setCurrentFundType(Integer currentFundType) {
        this.currentFundType = currentFundType;
    }

    public Integer getCurrentProtocalType() {
        return currentProtocalType;
    }

    public void setCurrentProtocalType(Integer currentProtocalType) {
        this.currentProtocalType = currentProtocalType;
    }

    public Integer getHisFundType() {
        return hisFundType;
    }

    public void setHisFundType(Integer hisFundType) {
        this.hisFundType = hisFundType;
    }

    public String getCustType() {
        return custType;
    }

    public void setCustType(String custType) {
        this.custType = custType;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public Double getMaxVol3Month() {
        return maxVol3Month;
    }

    public void setMaxVol3Month(Double maxVol3Month) {
        this.maxVol3Month = maxVol3Month;
    }

    public Double getMaxVolAll() {
        return maxVolAll;
    }

    public void setMaxVolAll(Double maxVolAll) {
        this.maxVolAll = maxVolAll;
    }
}
