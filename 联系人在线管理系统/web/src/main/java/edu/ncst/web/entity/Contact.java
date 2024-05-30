package edu.ncst.web.entity;

import java.util.Date;

//联系人实体，对应数据库t_contact表
public class Contact {
    private String user_id;
    private String ct_id;
    private String ct_name;
    private String ct_ad;
    private String ct_yb;   //联系人邮编
    private String ct_qq;   //联系人qq
    private String ct_wx;   //联系人微信
    private String ct_em;   //联系人邮箱
    private String ct_mf;   //联系人性别
    private Date ct_birth;//联系人生日
    private String ct_phone;//联系人手机号
    private Integer ct_delete;//联系人状态，0正常，1屏蔽

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCt_id() {
        return ct_id;
    }

    public void setCt_id(String ct_id) {
        this.ct_id = ct_id;
    }
    public String getCt_name() {
        return ct_name;
    }

    public void setCt_name(String ct_name) {
        this.ct_name = ct_name;
    }

    public String getCt_ad() {
        return ct_ad;
    }

    public void setCt_ad(String ct_ad) {
        this.ct_ad = ct_ad;
    }

    public String getCt_yb() {
        return ct_yb;
    }

    public void setCt_yb(String ct_yb) {
        this.ct_yb = ct_yb;
    }

    public String getCt_qq() {
        return ct_qq;
    }

    public void setCt_qq(String ct_qq) {
        this.ct_qq = ct_qq;
    }

    public String getCt_wx() {
        return ct_wx;
    }

    public void setCt_wx(String ct_wx) {
        this.ct_wx = ct_wx;
    }

    public String getCt_em() {
        return ct_em;
    }

    public void setCt_em(String ct_em) {
        this.ct_em = ct_em;
    }

    public String getCt_mf() {
        return ct_mf;
    }

    public void setCt_mf(String ct_mf) {
        this.ct_mf = ct_mf;
    }

    public Date getCt_birth() {
        return ct_birth;
    }

    public void setCt_birth(Date ct_birth) {
        this.ct_birth = ct_birth;
    }

    public String getCt_phone() {
        return ct_phone;
    }

    public void setCt_phone(String ct_phone) {
        this.ct_phone = ct_phone;
    }

    public Integer getCt_delete() {
        return ct_delete;
    }

    public void setCt_delete(Integer ct_delete) {
        this.ct_delete = ct_delete;
    }
}
