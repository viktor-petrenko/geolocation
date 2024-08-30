package com.geolocation.integration_tests;

import com.geolocation.utils.JarRunner;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GeolocationIntegrationTest {
    private JarRunner jarRunner;

    public GeolocationIntegrationTest() {
        // Initialize JarRunner
        jarRunner = new JarRunner();
    }

    @Test
    public void testSuccessfulCityState() throws Exception {
        String cityState = "Madison, WI";
        String output = jarRunner.runJarWithArgs(cityState);

        System.out.println("Output : \n" + output);
        assertThat(output).contains("City: Madison").contains("Latitude:").contains("Longitude:").contains("Country: US");
    }

    @Test
    public void testSuccessfulZipCode() throws Exception {
        String zipCode = "90210";
        String output = jarRunner.runJarWithArgs(zipCode);

        System.out.println("Output : \n" + output);
        assertThat(output).contains("Zip: 90210").contains("Latitude: 34.0901").contains("Longitude: -118.4065").contains("Country: US");
    }

    @Test
    public void testInvalidCityState() throws Exception {
        String invalidCityState = "Nonexistent, ZZ";
        String output = jarRunner.runJarWithArgs(invalidCityState);

        System.out.println("Output : \n" + output);
        assertThat(output).isEmpty();
    }

    @Test
    public void testInvalidZipCode() throws Exception {
        String invalidZip = "00000";
        String output = jarRunner.runJarWithArgs(invalidZip);

        assertThat(output).isEmpty();
    }

    @Test
    public void testMultipleLocations() throws Exception {
        String[] locations = {"Madison, WI", "90210", "Chicago, IL"};
        String output = jarRunner.runJarWithArgs(locations);

        assertThat(output).contains("City: Chicago").contains("City: Madison").contains("City: Chicago");
    }

}
