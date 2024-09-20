package com.geolocation;

import com.geolocation.app.services.GeoLocationService;
import com.geolocation.pojo.service.RestResponseContainer;
import com.geolocation.pojo.coords_by_location.CoordinatesByLocationNameResponse;
import com.geolocation.pojo.coords_by_zip.CoordinatesByZipCodeResponse;

import java.io.IOException;

public class GeoLocUtil {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please provide at least one location as input.");
            return;
        }

        for (String location : args) {
            try {
                if (isValidZipCode(location)) {
                    handleZipCode(location);
                } else if (isValidCityState(location)) {
                    handleCityState(location);
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
        return location.matches("\\d{5}") || location.matches("\\d{5}-\\d{4}");
    }

    private static boolean isValidCityState(String location) {
        return location.contains(",") && location.split(",").length == 2;
    }

    private static void handleZipCode(String zipCode) throws IOException {
        RestResponseContainer<CoordinatesByZipCodeResponse> restResponseContainer = GeoLocationService.getLocationByZip(zipCode);
        if (restResponseContainer.isResponseSuccessful()) {
            CoordinatesByZipCodeResponse response = restResponseContainer.getModel();
            printZipCodeInfo(response);
        } else {
            System.err.println(restResponseContainer.getFailure().getMessage());
        }
    }

    private static void handleCityState(String cityState) throws IOException {
        // TODO PLACE FOR IMPROVEMENT
        String[] parts = cityState.split(",");
        RestResponseContainer<CoordinatesByLocationNameResponse> restResponseContainer = GeoLocationService.getLocationByCityAndState(parts[0].trim(), parts[1].trim(), 1);
        if (restResponseContainer.isResponseSuccessful()) {
            CoordinatesByLocationNameResponse response = restResponseContainer.getSuccess();
            printCityStateInfo(response);
        } else {
            System.err.println(restResponseContainer.getFailure().getMessage());
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