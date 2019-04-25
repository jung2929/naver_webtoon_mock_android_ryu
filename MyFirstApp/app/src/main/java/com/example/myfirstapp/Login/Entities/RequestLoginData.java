package com.example.myfirstapp.Login.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestLoginData {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("pw")
    @Expose
    private String pw;
    @SerializedName("token")
    @Expose
    private String token;

    public RequestLoginData(String id, String pw, String token) {
        this.id = id;
        this.pw = pw;
        this.token = token;
    }
}
