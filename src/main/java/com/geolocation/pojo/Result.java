package com.geolocation.pojo;

public class Result<T> {
    private T data;
    private GeoLocationError error;

    public Result(T data) {
        this.data = data;
    }

    public Result(GeoLocationError error) {
        this.error = error;
    }

    public boolean isSuccess() {
        return error == null;
    }

    public T getData() {
        return data;
    }

    public GeoLocationError getError() {
        return error;
    }
}