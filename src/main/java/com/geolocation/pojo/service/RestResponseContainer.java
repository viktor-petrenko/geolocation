package com.geolocation.pojo.service;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;

public class RestResponseContainer<T> {
    private Response response;
    private RestError error;
    private Object responseData;

    public RestResponseContainer(Response response, T responseData) {
        this.response = response;
        this.responseData = responseData;

    }
    public RestResponseContainer(Response response, RestError error) {
        this.response = response;
        this.error = error;
    }

    public RestResponseContainer(Response response, GenericType<T> genericType) {
        this.response = response;
        if (!isResponseSuccessful()) {
            this.error = response.readEntity(RestError.class);
            // System.out.println(error.toString()); // add log4j to debug level
        } else {
            this.responseData = response.readEntity(genericType);
        }
    }

    public RestResponseContainer(Response response, Class<?> clazz) {
        this.response = response;
        if (!isResponseSuccessful()) {
            this.error = response.readEntity(RestError.class);
            // System.out.println(error.toString()); ; // add log4j to debug level
        } else {
            this.responseData = response.readEntity(clazz);
        }
    }

    public boolean isResponseSuccessful() {
        return String.valueOf(response.getStatus()).matches("20.");
    }

    public Response getResponse() {
        return response;
    }

    public T getSuccess() {
        if (!isResponseSuccessful()) {
            throw new WebApplicationException(String.format("Response is not success. Actual error code: [%s], [%s]", getFailure(), error.getErrors()));
        }
        return getModel();
    }

    public RestError getFailure() {
        if (isResponseSuccessful()) {
            throw new WebApplicationException("Response is not failure as expected");
        }
        return error;
    }

    public RestError getError() {
        return error;
    }

    public RestResponseContainer<T> setError(RestError error) {
        this.error = error;
        return this;
    }

    @SuppressWarnings("unchecked")
    public T getModel() {
        return (T) responseData;
    }
}