package com.geolocation.unit_tests;

import com.geolocation.app.services.GeoLocationService;
import com.geolocation.base.BaseTest;
import com.geolocation.pojo.service.RestResponseContainer;
import com.geolocation.pojo.coords_by_location.CoordinatesByLocationNameResponse;
import com.geolocation.pojo.coords_by_zip.CoordinatesByZipCodeResponse;
import org.assertj.core.api.AutoCloseableSoftAssertions;
import org.testng.annotations.Test;

import static com.geolocation.app.services.GeoLocationService.getLocationByCityAndState;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class NegativeTest extends BaseTest {

    @Test()
    public void testInvalidCityAndState() {
        RestResponseContainer<CoordinatesByLocationNameResponse> response = getLocationByCityAndState("InvalidCity", "InvalidState", 1);

        try (AutoCloseableSoftAssertions softly = new AutoCloseableSoftAssertions()) {
            softly.assertThat(!response.isResponseSuccessful()).isFalse();
            softly.assertThat(response.getError().getMessage()).isNotNull();
            softly.assertThat(response.getError().getMessage()).contains( "No results found for city='InvalidCity', state='InvalidState', limit='1'");
        }
    }

    @Test()
    public void testInvalidZipCode() {
        RestResponseContainer<CoordinatesByZipCodeResponse> response = GeoLocationService.getLocationByZip(invalidZip);

        verifyCommonChecks(response);
        assertThat(response.getFailure().getMessage()).contains(String.format("No results found for zip='%s'", invalidZip));
    }

    @Test
    public void testGetLocationByCityAndState_EmptyInput() {
        RestResponseContainer<CoordinatesByLocationNameResponse> response = GeoLocationService.getLocationByCityAndState("", "", 1);

        verifyCommonChecks(response);
        assertThat(response.getFailure().getMessage()).contains("Error fetching data for city='', state='', limit='1'");
    }

    @Test
    public void testGetLocationByZip_NullInput() {
        RestResponseContainer<CoordinatesByZipCodeResponse> response =  GeoLocationService.getLocationByZip("null");

        verifyCommonChecks(response);
        assertThat(response.getFailure().getMessage()).contains( "No results found for zip='null'");
    }

    @Test
    public void testGetLocationByZip_EmptyInput() {
        RestResponseContainer<CoordinatesByZipCodeResponse> response =  GeoLocationService.getLocationByZip("");

        verifyCommonChecks(response);
        assertThat(response.getFailure().getMessage()).contains("No results found for zip=''");
    }

    @Test
    public void testLimit() {
        RestResponseContainer<CoordinatesByLocationNameResponse> response = GeoLocationService.getLocationByCityAndState("Madison", "WI", -1);

        verifyCommonChecks(response);
        assertThat(response.getFailure().getMessage()).contains("Error fetching data for city='Madison', state='WI', limit='-1'");
    }


    private static void verifyCommonChecks(RestResponseContainer response) {
        try (AutoCloseableSoftAssertions softly = new AutoCloseableSoftAssertions()) {
            softly.assertThat(response.isResponseSuccessful()).isFalse();
            softly.assertThat(response.getError().getMessage()).isNotNull();
        }
    }

}
