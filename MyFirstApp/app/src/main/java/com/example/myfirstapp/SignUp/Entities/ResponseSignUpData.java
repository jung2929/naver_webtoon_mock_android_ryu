package com.example.myfirstapp.SignUp.Entities;

import com.example.myfirstapp.baseClass.ResponseBaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseSignUpData extends ResponseBaseData {

    @SerializedName("id")
    @Expose
    private String id;

    public ResponseSignUpData(Integer code, String message, String id) {
        super(code, message);
        this.id = id;
    }

    /*
       @SerializedName("code")
       @Expose
       private Integer code;
       @SerializedName("message")
       @Expose
       private String message;
   */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}