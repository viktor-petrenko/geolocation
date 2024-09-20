package com.geolocation.integration_tests;

import com.geolocation.base.BaseTest;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GeolocationIntegrationTest extends BaseTest {

    @Test
    public void testSuccessfulCityState() throws Exception {
        String output = jarRunner.runJarWithArgs("Madison, WI");

        System.out.println("Output : \n" + output);
        assertThat(output).contains("City: Madison").contains("Latitude:").contains("Longitude:").contains("Country: US");
    }

    @Test
    public void testSuccessfulZipCode() throws Exception {
        String output = jarRunner.runJarWithArgs("90210");

        System.out.println("Output : \n" + output);
        assertThat(output).contains("Zip: 90210").contains("Latitude: 34.0901").contains("Longitude: -118.4065").contains("Country: US");
    }

    @Test
    public void testInvalidCityState() throws Exception {
        String output = jarRunner.runJarWithArgs("Nonexistent, ZZ");
        System.out.println("\"Error fetching data for city='Nonexistent', state='ZZ', limit='1'");
        assertThat(output).contains("Nonexistent").contains("state='ZZ'").contains(" limit='1'");
    }

    @Test
    public void testInvalidZipCode() throws Exception {
        String output = jarRunner.runJarWithArgs(invalidZip);

        System.out.println(output);
        assertThat(output).contains(String.format("zip='%s'", invalidZip));
    }

    @Test
    public void testMultipleLocations() throws Exception {
        String[] locations = {"Madison, WI", "90210", "Chicago, IL"};
        String output = jarRunner.runJarWithArgs(locations);

        System.out.println(output);
        assertThat(output).contains("Zip: 90210").contains("City: Madison").contains("City: Chicago");
    }

}
