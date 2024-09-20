package com.geolocation.pojo;


import java.util.Objects;

public class Model implements IModel {

    public Model() {
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass().getDeclaredFields());
    }
}