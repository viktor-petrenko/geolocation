package com.geolocation;

import com.geolocation.app.services.GeoLocationService;
import com.geolocation.pojo.Result;
import com.geolocation.pojo.coords_by_location.CoordinatesByLocationNameResponse;
import com.geolocation.pojo.coords_by_zip.CoordinatesByZipCodeResponse;
import org.assertj.core.api.AutoCloseableSoftAssertions;
import org.testng.annotations.Test;

public class NegativeTest {

    private final GeoLocationService service = new GeoLocationService();

    @Test()
    public void testInvalidCityAndState() {
        Result<CoordinatesByLocationNameResponse> response = service.getLocationByCityAndState("InvalidCity", "InvalidState", 1);

        verifyCommonChecks(response);
    }


    @Test()
    public void testInvalidZipCode() {
        Result<CoordinatesByZipCodeResponse> response = service.getLocationByZip("00000");

        verifyCommonChecks(response);
    }

    @Test
    public void testGetLocationByCityAndState_EmptyInput() {
        Result<CoordinatesByLocationNameResponse> response = service.getLocationByCityAndState("", "", 1);

        verifyCommonChecks(response);
    }

    @Test
    public void testGetLocationByZip_NullInput() {
        Result<CoordinatesByZipCodeResponse> response = service.getLocationByZip(null);

        verifyCommonChecks(response);
    }

    @Test
    public void testLimit() {
        Result<CoordinatesByLocationNameResponse> response = service.getLocationByCityAndState("Madison", "WI", -1);

        verifyCommonChecks(response);
    }


    private static void verifyCommonChecks(Result response) {
        try (AutoCloseableSoftAssertions softly = new AutoCloseableSoftAssertions()) {
            softly.assertThat(response.isSuccess()).isFalse();
            softly.assertThat(response.getError()).isNotNull();
            softly.assertThat(response.getError().getMessage()).contains("No results found");
        }
    }

}
