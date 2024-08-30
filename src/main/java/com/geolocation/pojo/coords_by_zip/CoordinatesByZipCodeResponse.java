package com.geolocation.pojo.coords_by_zip;

import com.geolocation.pojo.Model;

import java.util.Objects;

public class CoordinatesByZipCodeResponse extends Model {
    private String zip;
    private String name;
    private double lat;
    private double lon;
    private String country;

    public String getZip() {
        return zip;
    }

    public CoordinatesByZipCodeResponse setZip(String zip) {
        this.zip = zip;
        return this;
    }

    public String getName() {
        return name;
    }

    public CoordinatesByZipCodeResponse setName(String name) {
        this.name = name;
        return this;
    }

    public double getLat() {
        return lat;
    }

    public CoordinatesByZipCodeResponse setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public double getLon() {
        return lon;
    }

    public CoordinatesByZipCodeResponse setLon(double lon) {
        this.lon = lon;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public CoordinatesByZipCodeResponse setCountry(String country) {
        this.country = country;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoordinatesByZipCodeResponse that = (CoordinatesByZipCodeResponse) o;
        return Double.compare(lat, that.lat) == 0 && Double.compare(lon, that.lon) == 0 && Objects.equals(zip, that.zip) && Objects.equals(name, that.name) && Objects.equals(country, that.country);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CoordinatesByZipCodeResponse{");
        sb.append("zip='").append(zip).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", lat=").append(lat);
        sb.append(", lon=").append(lon);
        sb.append(", country='").append(country).append('\'');
        sb.append('}');
        return sb.toString();
    }
}