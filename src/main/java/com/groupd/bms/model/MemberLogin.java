package com.groupd.bms.model;

import java.io.Serializable;

public class MemberLogin implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userid;
    private String userpw;
    private String ip;
    private String sysGubun;
    private Integer retVal;
    private String errMsg;

    public MemberLogin(String userid, String userpw, String ip, String sysGubun) {
        this.userid = userid;
        this.userpw = userpw;
        this.ip = ip;
        this.sysGubun = sysGubun;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserpw() {
        return userpw;
    }

    public void setUserpw(String userpw) {
        this.userpw = userpw;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSysGubun() {
        return sysGubun;
    }

    public void setSysGubun(String sysGubun) {
        this.sysGubun = sysGubun;
    }

    public Integer getRetVal() {
        return retVal;
    }

    public void setRetVal(Integer retVal) {
        this.retVal = retVal;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
    
    @Override
    public String toString() {
        return "MemberLogin [errMsg=" + errMsg + ", ip=" + ip + ", retVal=" + retVal + ", sysGubun=" + sysGubun
                + ", userpw=" + userpw + ", userid=" + userid + "]";
    }
}