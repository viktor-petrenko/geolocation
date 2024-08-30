package com.geolocation.pojo;

import java.util.Objects;

public class Result<T> {
    private T data;
    private GeoLocationError error;
    private int statusCode;

    public Result(T data, int statusCode) {
        this.data = data;
        this.statusCode = statusCode;
    }

    public Result(GeoLocationError error, int statusCode) {
        this.error = error;
        this.statusCode = statusCode;
    }

    public boolean isSuccess() {
        return data != null && error == null;
    }

    public T getData() {
        return data;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }

    public GeoLocationError getError() {
        return error;
    }

    public Result<T> setError(GeoLocationError error) {
        this.error = error;
        return this;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Result<T> setStatusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result<?> result = (Result<?>) o;
        return statusCode == result.statusCode && Objects.equals(data, result.data) && Objects.equals(error, result.error);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, error, statusCode);
    }

    @Override
    public String  toString() {
        final StringBuilder sb = new StringBuilder("Result{");
        sb.append("data=").append(data);
        sb.append(", error=").append(error);
        sb.append(", statusCode=").append(statusCode);
        sb.append('}');
        return sb.toString();
    }
}