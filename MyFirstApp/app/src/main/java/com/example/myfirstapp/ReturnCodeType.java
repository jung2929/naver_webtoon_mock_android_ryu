package com.example.myfirstapp;

import org.jetbrains.annotations.Contract;

public enum ReturnCodeType {
    SUCCESS(100), NEED_LOGIN(200), INVALID_TOKEN(205);
    private int code;

    ReturnCodeType(int code) {
        this.code = code;
    }

    @Contract(pure = true)
    public int getCode() {
        return code;
    }
}
