package com.groupd.bms.model;

import java.io.Serializable;

public class Member implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String userid;
    private String userkey;
    private String name;
    private String hpno;
    private String email;
    private String birthday;
    private String gender;
    private String grade;
    private String siteCode;
    private String siteName;
    private String siteAddress;
    private String code;
    private String codeName;
    private String codeSeq;

    public Member() {}
    

    public Member(String userid, String userkey, String name, String hpno, String email, String birthday , String gender , String grade , String siteCode , String siteName , String siteAddress , String code , String codeName , String codeSeq) {
        this.userid = userid;
        this.userkey = userkey;
        this.name = name;
        this.hpno = hpno;
        this.email = email;
        this.birthday = birthday;
        this.gender = gender;
        this.grade = grade;
        this.siteCode = siteCode;
        this.siteName = siteName;
        this.siteAddress = siteAddress;
        this.code = code;
        this.codeName = codeName;
        this.codeSeq = codeSeq;
    } 

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserkey() {
        return userkey;
    }

    public void setUserkey(String userkey) {
        this.userkey = userkey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHpno() {
        return hpno;
    }

    public void setHpno(String hpno) {
        this.hpno = hpno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteAddress() {
        return siteAddress;
    }

    public void setSiteAddress(String siteAddress) {
        this.siteAddress = siteAddress;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getCodeSeq() {
        return codeSeq;
    }

    public void setCodeSeq(String codeSeq) {
        this.codeSeq = codeSeq;
    }

    @Override
    public String toString() {
        return "Member [userid=" + userid + ", userkey=" + userkey + ", name=" + name + ", hpno=" + hpno + ", email="+ email + ", birthday=" + birthday + ", code=" + code + ", codeName=" + codeName + ", codeSeq=" + codeSeq;
    }
    
}

