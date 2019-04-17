package com.example.myfirstapp.Login.Entities;

import com.example.myfirstapp.baseClass.ResponseBaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseLoginData extends ResponseBaseData {

    @SerializedName("result")
    @Expose
    private Result result;

    public ResponseLoginData(Integer code, String message, Result result) {
        super(code, message);
        this.result = result;
    }
    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
    public class Result {

        @SerializedName("jwt")
        @Expose
        private String jwt;

        public String getJwt() {
            return jwt;
        }

        public void setJwt(String jwt) {
            this.jwt = jwt;
        }

    }
}