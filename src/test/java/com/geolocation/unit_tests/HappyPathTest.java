package com.geolocation.unit_tests;

import com.geolocation.app.services.GeoLocationService;
import com.geolocation.pojo.service.RestResponseContainer;
import org.testng.annotations.Test;
import com.geolocation.pojo.coords_by_location.CoordinatesByLocationNameResponse;
import com.geolocation.pojo.coords_by_zip.CoordinatesByZipCodeResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

public class HappyPathTest {

    @Test
    public void testGetLocationByCityAndState() {
        RestResponseContainer<CoordinatesByLocationNameResponse> locationByCity = GeoLocationService.getLocationByCityAndState("Garland", "TX");
        assertThat(locationByCity).isNotNull();

        if (locationByCity != null) {
            System.out.println("City: " + locationByCity.getModel().getName());
            System.out.println("Latitude: " + locationByCity.getModel().getLat());
            System.out.println("Longitude: " + locationByCity.getModel().getLon());
            System.out.println("Country: " + locationByCity.getModel().getCountry());
            System.out.println("__________________________________________________");
        }
    }

    @Test
    public void testGetLocationByZip() {
        RestResponseContainer<CoordinatesByZipCodeResponse> locationByZip = GeoLocationService.getLocationByZip("90210");
        assertThat(locationByZip).isNotNull();

        if (locationByZip != null) {
            System.out.println("Zip: " + locationByZip.getModel().getZip());
            System.out.println("City: " + locationByZip.getModel().getName());
            System.out.println("Latitude: " + locationByZip.getModel().getLat());
            System.out.println("Longitude: " + locationByZip.getModel().getLon());
            System.out.println("Country: " + locationByZip.getModel().getCountry());
            System.out.println("__________________________________________________");
        }
    }

    @Test
    public void testMultipleLocations() throws Exception {
        // Test with multiple valid locations
        RestResponseContainer<CoordinatesByLocationNameResponse> restResponseContainer1 = GeoLocationService.getLocationByCityAndState("Madison", "WI", 1);
        RestResponseContainer<CoordinatesByZipCodeResponse> restResponseContainer2 = GeoLocationService.getLocationByZip("90210");

        assertThat(restResponseContainer1.isResponseSuccessful()).isTrue();
        assertThat(restResponseContainer1.getModel()).isNotNull();
        assertThat(restResponseContainer2.isResponseSuccessful()).isTrue();
        assertThat(restResponseContainer2.getModel()).isNotNull();
    }

}