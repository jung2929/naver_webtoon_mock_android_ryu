package com.example.myfirstapp.entities;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseWebtoonListData extends ResponseBaseData{

    @SerializedName("result")
    @Expose
    private List<WebtoonListData> result = null;
    /**
     *
     * @param message
     * @param result
     * @param code
     */
    public ResponseWebtoonListData(Integer code, String message, List<WebtoonListData> result) {
        super(code, message);
        this.result = result;
    }

    public List<WebtoonListData> getResult() {
        return result;
    }

    public void setResult(List<WebtoonListData> result) {
        this.result = result;
    }
}