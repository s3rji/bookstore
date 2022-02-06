package com.example.MyBookShopApp.dto;

public class ApiResult {
    private boolean result;
    private String error;

    public ApiResult(boolean result) {
        this.result = result;
    }

    public ApiResult(boolean result, String error) {
        this(result);
        this.error = error;
    }

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
