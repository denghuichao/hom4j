package com.github.dhc.framework.hom.pos;

import com.github.dhc.framework.hom.annotation.Column;
import com.github.dhc.framework.hom.annotation.RowKey;
import com.github.dhc.framework.hom.annotation.Table;

import java.util.Calendar;


/**
 * Created by hcdeng on 2017/8/30.
 */
@Table("SchemeSnapShort")
public class SchemeSnapshot {

    @RowKey(auto = true)
    public Long snapShortID;

    @Column(family = "cf")
    public String snapShortStatus;

    @Column(family = "cf")
    public Calendar snapShortTime;

    @Column(family = "cf")
    public Long orderID;

    @Column(family = "cf")
    public String platformUserId;

    @Column(family = "cf")
    public Long platformProviderId;

    @Column(family = "cf")
    public Calendar dataChangeCreateTime;

    @Column(family = "cf")
    public Calendar dataChangeLastTime;

    @Column(family = "cf")
    public String createUser;

    @Column(family = "cf")
    public String modifyUser;

    @Column(family = "cf")
    public String schemeInfo;

    public Long getSnapShortID() {
        return snapShortID;
    }

    public void setSnapShortID(Long snapShortID) {
        this.snapShortID = snapShortID;
    }

    public String getSnapShortStatus() {
        return snapShortStatus;
    }

    public void setSnapShortStatus(String snapShortStatus) {
        this.snapShortStatus = snapShortStatus;
    }

    public Calendar getSnapShortTime() {
        return snapShortTime;
    }

    public void setSnapShortTime(Calendar snapShortTime) {
        this.snapShortTime = snapShortTime;
    }

    public Long getOrderID() {
        return orderID;
    }

    public void setOrderID(Long orderID) {
        this.orderID = orderID;
    }

    public String getPlatformUserId() {
        return platformUserId;
    }

    public void setPlatformUserId(String platformUserId) {
        this.platformUserId = platformUserId;
    }

    public Long getPlatformProviderId() {
        return platformProviderId;
    }

    public void setPlatformProviderId(Long platformProviderId) {
        this.platformProviderId = platformProviderId;
    }

    public Calendar getDataChangeCreateTime() {
        return dataChangeCreateTime;
    }

    public void setDataChangeCreateTime(Calendar dataChangeCreateTime) {
        this.dataChangeCreateTime = dataChangeCreateTime;
    }

    public Calendar getDataChangeLastTime() {
        return dataChangeLastTime;
    }

    public void setDataChangeLastTime(Calendar dataChangeLastTime) {
        this.dataChangeLastTime = dataChangeLastTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public String getSchemeInfo() {
        return schemeInfo;
    }

    public void setSchemeInfo(String schemeInfo) {
        this.schemeInfo = schemeInfo;
    }
}
