package com.example.myfirstapp.enitites;

public class SoftComicsMemberData {
    String id;
    String pw;
    String subpw;
    String email;
    String tel;

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
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public SoftComicsMemberData(String id, String pw, String subpw, String email, String tel) {
        this.id = id;
        this.pw = pw;
        this.subpw = subpw;
        this.email = email;
        this.tel = tel;
    }
}
