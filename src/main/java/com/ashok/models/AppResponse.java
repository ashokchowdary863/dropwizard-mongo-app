package com.ashok.models;

public class AppResponse<Type> {
    private int status;
    private Type response;

    public AppResponse(int status, Type response) {
        this.status = status;
        this.response = response;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Type getResponse() {
        return response;
    }

    public void setResponse(Type response) {
        this.response = response;
    }
}
