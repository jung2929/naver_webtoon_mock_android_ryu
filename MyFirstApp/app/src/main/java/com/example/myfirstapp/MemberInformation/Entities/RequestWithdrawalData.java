package com.example.myfirstapp.MemberInformation.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestWithdrawalData {
    @SerializedName("pw")
    @Expose
    String pw;

    public RequestWithdrawalData(String pw) {
        this.pw = pw;
    }
}
