package com.geolocation;

import com.geolocation.app.services.GeoLocationService;
import com.geolocation.pojo.Result;
import com.geolocation.pojo.coords_by_location.CoordinatesByLocationNameResponse;
import com.geolocation.pojo.coords_by_zip.CoordinatesByZipCodeResponse;

import java.io.IOException;

public class GeoLocUtil {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please provide at least one location as input.");
            return;
        }

        GeoLocationService service = new GeoLocationService();

        for (String location : args) {
            try {
                if (isValidZipCode(location)) {
                    handleZipCode(service, location);
                } else if (isValidCityState(location)) {
                    handleCityState(service, location);
                } else {
                    System.out.println("Invalid location format: \"" + location + "\""); // todo add log4j
                }
            } catch (IOException e) {
                System.err.println("Error fetching data for location: \"" + location + "\""); // todo add log4j
                e.printStackTrace();
            }
        }
    }

    private static boolean isValidZipCode(String location) {
        return location.matches("\\d{5}");
    }

    private static boolean isValidCityState(String location) {
        return location.contains(",") && location.split(",").length == 2;
    }

    private static void handleZipCode(GeoLocationService service, String zipCode) throws IOException {
        Result<CoordinatesByZipCodeResponse> result = service.getLocationByZip(zipCode);
        if (result.isSuccess()) {
            CoordinatesByZipCodeResponse response = result.getData();
            printZipCodeInfo(response);
        } else {
            System.err.println(result.getError());
        }
    }

    private static void handleCityState(GeoLocationService service, String cityState) throws IOException {
        String[] parts = cityState.split(",");
        Result<CoordinatesByLocationNameResponse> result = service.getLocationByCityAndState(parts[0].trim(), parts[1].trim(), 1);
        if (result.isSuccess()) {
            CoordinatesByLocationNameResponse response = result.getData();
            printCityStateInfo(response);
        } else {
            System.err.println(result.getError());
        }
    }

    private static void printZipCodeInfo(CoordinatesByZipCodeResponse response) {
        System.out.println("Zip: " + response.getZip());
        System.out.println("Latitude: " + response.getLat());
        System.out.println("Longitude: " + response.getLon());
        System.out.println("Country: " + response.getCountry());
        System.out.println("-------------------------");
    }

    private static void printCityStateInfo(CoordinatesByLocationNameResponse response) {
        System.out.println("City: " + response.getName());
        System.out.println("Latitude: " + response.getLat());
        System.out.println("Longitude: " + response.getLon());
        System.out.println("Country: " + response.getCountry());
        System.out.println("-------------------------");
    }

}