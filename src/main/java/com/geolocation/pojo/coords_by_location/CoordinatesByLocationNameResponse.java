package com.geolocation.pojo.coords_by_location;

import com.geolocation.pojo.Model;

import java.util.Map;
import java.util.Objects;

public class CoordinatesByLocationNameResponse extends Model {
    private String name;
    private Map<String, String> local_names;
    private double lat;
    private double lon;
    private String country;
    private String state;

    public String getName() {
        return name;
    }

    public CoordinatesByLocationNameResponse setName(String name) {
        this.name = name;
        return this;
    }

    public Map<String, String> getLocal_names() {
        return local_names;
    }

    public CoordinatesByLocationNameResponse setLocal_names(Map<String, String> local_names) {
        this.local_names = local_names;
        return this;
    }

    public double getLat() {
        return lat;
    }

    public CoordinatesByLocationNameResponse setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public double getLon() {
        return lon;
    }

    public CoordinatesByLocationNameResponse setLon(double lon) {
        this.lon = lon;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public CoordinatesByLocationNameResponse setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getState() {
        return state;
    }

    public CoordinatesByLocationNameResponse setState(String state) {
        this.state = state;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoordinatesByLocationNameResponse that = (CoordinatesByLocationNameResponse) o;
        return Double.compare(lat, that.lat) == 0 && Double.compare(lon, that.lon) == 0 && Objects.equals(name, that.name) && Objects.equals(local_names, that.local_names) && Objects.equals(country, that.country) && Objects.equals(state, that.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, local_names, lat, lon, country, state);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CoordinatesByLocationNameResponse{");
        sb.append("name='").append(name).append('\'');
        sb.append(", local_names=").append(local_names);
        sb.append(", lat=").append(lat);
        sb.append(", lon=").append(lon);
        sb.append(", country='").append(country).append('\'');
        sb.append(", state='").append(state).append('\'');
        sb.append('}');
        return sb.toString();
    }
}