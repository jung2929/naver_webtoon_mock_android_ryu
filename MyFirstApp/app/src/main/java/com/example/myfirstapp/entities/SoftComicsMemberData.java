package com.example.myfirstapp.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SoftComicsMemberData {
    public SoftComicsMemberData(String id, String pw, String subpw, String mail, String tel) {
        this.id = id;
        this.pw = pw;
        this.subpw = subpw;
        this.mail = mail;
        this.tel = tel;
    }

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("pw")
    @Expose
    private String pw;
    @SerializedName("subpw")
    @Expose
    private String subpw;
    @SerializedName("mail")
    @Expose
    private String mail;
    @SerializedName("tel")
    @Expose
    private String tel;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getSubpw() {
        return subpw;
    }

    public void setSubpw(String subpw) {
        this.subpw = subpw;
    }

    public String getEmail() {
        return mail;
    }

    public void setEmail(String mail) {
        this.mail = mail;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

}