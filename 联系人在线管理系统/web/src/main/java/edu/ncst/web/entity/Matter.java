package edu.ncst.web.entity;
//对应数据库表t_contact_matter
public class Matter {
    private String ct_id;
    private String matter_id;
    private String matter_time;
    private String matter;
    private Integer matter_delete;
    private String ct_name; //t_contact中对应ct_id的名字

    public String getCt_name() {
        return ct_name;
    }

    public void setCt_name(String ct_name) {
        this.ct_name = ct_name;
    }

    public String getCt_id() {
        return ct_id;
    }

    public void setCt_id(String ct_id) {
        this.ct_id = ct_id;
    }

    public String getMatter_id() {
        return matter_id;
    }

    public void setMatter_id(String matter_id) {
        this.matter_id = matter_id;
    }

    public String getMatter_time() {
        return matter_time;
    }

    public void setMatter_time(String matter_time) {
        this.matter_time = matter_time;
    }

    public String getMatter() {
        return matter;
    }

    public void setMatter(String matter) {
        this.matter = matter;
    }

    public Integer getMatter_delete() {
        return matter_delete;
    }

    public void setMatter_delete(Integer matter_delete) {
        this.matter_delete = matter_delete;
    }
}
