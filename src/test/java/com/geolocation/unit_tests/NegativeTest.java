package com.geolocation.unit_tests;

import com.geolocation.app.services.GeoLocationService;
import com.geolocation.pojo.Result;
import com.geolocation.pojo.coords_by_location.CoordinatesByLocationNameResponse;
import com.geolocation.pojo.coords_by_zip.CoordinatesByZipCodeResponse;
import org.assertj.core.api.AutoCloseableSoftAssertions;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class NegativeTest {



    @Test()
    public void testInvalidCityAndState() {
        Result<CoordinatesByLocationNameResponse> response = new GeoLocationService().getLocationByCityAndState("InvalidCity", "InvalidState", 1);

        verifyCommonChecks(response);
        assertThat(response.getError().getMessage()).contains("No results found for InvalidCity, InvalidState");
    }


    @Test()
    public void testInvalidZipCode() {
        Result<CoordinatesByZipCodeResponse> response = new GeoLocationService().getLocationByZip("00000");

        verifyCommonChecks(response);
        assertThat(response.getError().getMessage()).contains("Failed to fetch location data");
    }

    @Test
    public void testGetLocationByCityAndState_EmptyInput() {
        Result<CoordinatesByLocationNameResponse> response = new GeoLocationService().getLocationByCityAndState("", "", 1);

        verifyCommonChecks(response);
        assertThat(response.getError().getMessage()).contains("No results found for , ");
    }

    @Test
    public void testGetLocationByZip_NullInput() {
        Result<CoordinatesByZipCodeResponse> response = new GeoLocationService().getLocationByZip(null);

        verifyCommonChecks(response);
        assertThat(response.getError().getMessage()).contains("Failed to fetch location data");
    }

    @Test
    public void testLimit() {
        Result<CoordinatesByLocationNameResponse> response = new GeoLocationService().getLocationByCityAndState("Madison", "WI", -1);

        verifyCommonChecks(response);
        assertThat(response.getError().getMessage()).contains("No results found for Madison, WI");
    }


    private static void verifyCommonChecks(Result response) {
        try (AutoCloseableSoftAssertions softly = new AutoCloseableSoftAssertions()) {
            softly.assertThat(response.isSuccess()).isFalse();
            softly.assertThat(response.getError()).isNotNull();
        }
    }

}
