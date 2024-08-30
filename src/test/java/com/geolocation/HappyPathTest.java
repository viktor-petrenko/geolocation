package com.geolocation;

import com.geolocation.app.services.GeoLocationService;
import com.geolocation.pojo.Result;
import org.testng.annotations.Test;
import com.geolocation.pojo.coords_by_location.CoordinatesByLocationNameResponse;
import com.geolocation.pojo.coords_by_zip.CoordinatesByZipCodeResponse;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

public class HappyPathTest {
    GeoLocationService service = new GeoLocationService();

    @Test
    public void testGetLocationByCityAndState() throws IOException {
        Result<CoordinatesByLocationNameResponse> locationByCity = service.getLocationByCityAndState("Garland", "TX");
        assertThat(locationByCity).isNotNull();

        if (locationByCity != null) {
            System.out.println("City: " + locationByCity.getData().getName());
            System.out.println("Latitude: " + locationByCity.getData().getLat());
            System.out.println("Longitude: " + locationByCity.getData().getLon());
            System.out.println("Country: " + locationByCity.getData().getCountry());
        }
    }

    @Test
    public void testGetLocationByZip() throws IOException {
        Result<CoordinatesByZipCodeResponse> locationByZip = service.getLocationByZip("75220");
        assertThat(locationByZip).isNotNull();

        if (locationByZip != null) {
            System.out.println("Zip: " + locationByZip.getData().getZip());
            System.out.println("City: " + locationByZip.getData().getName());
            System.out.println("Latitude: " + locationByZip.getData().getLat());
            System.out.println("Longitude: " + locationByZip.getData().getLon());
            System.out.println("Country: " + locationByZip.getData().getCountry());
        }
    }

    @Test
    public void testMultipleLocations() throws Exception {
        // Test with multiple valid locations
        Result<CoordinatesByLocationNameResponse> result1 = service.getLocationByCityAndState("Madison", "WI", 1);
        Result<CoordinatesByZipCodeResponse> result2 = service.getLocationByZip("90210");

        assertThat(result1.isSuccess()).isTrue();
        assertThat(result1.getData()).isNotNull();
        assertThat(result2.isSuccess()).isTrue();
        assertThat(result2.getData()).isNotNull();
    }

}