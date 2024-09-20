package com.geolocation.pojo.service;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.geolocation.pojo.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RestError extends Model {
    private String errorCode;
    private String message;
    private String field;
    private List<RestError> errors;
    private Map<String, Object> unknownFields = new HashMap<>();


    public RestError(String message) {
        this.message = message;
    }

    public RestError() {
    }

    public RestError(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }


    @JsonAnySetter
    public void setUnknownFields(String name, Object value) {
        unknownFields.put(name, value);
    }

    @JsonAnyGetter
    public Map<String, Object> getUnknownFields() {
        return unknownFields;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public RestError setErrorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public RestError setMessage(String message) {
        this.message = message;
        return this;
    }

    public List<RestError> getErrors() {
        return errors;
    }

    public RestError setErrors(List<RestError> errors) {
        this.errors = errors;
        return this;
    }

    public RestError setUnknownFields(Map<String, Object> unknownFields) {
        this.unknownFields = unknownFields;
        return this;
    }

    public String getField() {
        return field;
    }

    public RestError setField(String field) {
        this.field = field;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RestError{");
        sb.append("errorCode='").append(errorCode).append('\'');
        sb.append(", message='").append(message).append('\'');
        sb.append(", field='").append(field).append('\'');
        sb.append(", errors=").append(errors);
        sb.append(", unknownFields=").append(unknownFields);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestError restError = (RestError) o;
        return Objects.equals(errorCode, restError.errorCode) && Objects.equals(message, restError.message) && Objects.equals(field, restError.field) && Objects.equals(errors, restError.errors) && Objects.equals(unknownFields, restError.unknownFields);
    }

}

