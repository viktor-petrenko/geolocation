package com.geolocation.app.services;


import com.geolocation.pojo.service.RestError;
import com.geolocation.pojo.service.RestResponseContainer;
import com.geolocation.utils.PropertyLoader;
import com.geolocation.pojo.coords_by_location.CoordinatesByLocationNameResponse;
import com.geolocation.pojo.coords_by_zip.CoordinatesByZipCodeResponse;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.jackson.JacksonFeature;

import java.util.Properties;

import java.io.IOException;

public class GeoLocationService {
    static Client client = ClientBuilder.newClient().register(JacksonFeature.class);

    private static final String API_KEY;
    private static final String BASE_URL = "http://api.openweathermap.org/geo/1.0/";

    static {
        Properties properties = PropertyLoader.loadProperties("config.properties");

        API_KEY = properties.getProperty("api.key");

        if (API_KEY == null || API_KEY.trim().isEmpty()) {
            System.out.println("________________________________________________________________________________________________");
            System.out.println("API key is empty. Please ensure that the api.key property in config.properties is set correctly.");
            System.out.println("________________________________________________________________________________________________");
            throw new RuntimeException("API key is empty");
        }
    }

    /**
     * Fetches geographical information for a given city and state in the United States.
     * This method queries the OpenWeatherMap Geocoding API to retrieve details such as latitude, longitude,
     * country, and state information based on the provided city and state.
     * </br>
     * If no matching locations are found, it returns an appropriate error message encapsulated
     * in a {@link RestResponseContainer} object.
     * </br>
     * </br> API Request URL format:
     * </br> http://api.openweathermap.org/geo/1.0/direct?q={city name},{state code},{country code}&limit={limit}&appid={API key}
     *
     * @param city  The name of the city for which to retrieve geographical information.
     * @param state The two-letter state code (e.g., "TX" for Texas) in the United States.
     * @return A {@link RestResponseContainer} containing a {@link CoordinatesByLocationNameResponse} object
     * with geographical information for the specified city and state if found, or a {@link RestError}
     * if no matching locations are found or if an error occurs.
     * </br> The {@link RestResponseContainer} also includes the HTTP status code returned by the API.
     *
     * @see <a href="https://openweathermap.org/api/geocoding-api#direct_name">OpenWeatherMap Geocoding API - Coordinates by City Name</a>
     * @see CoordinatesByLocationNameResponse
     * @see RestError
     */
    public static RestResponseContainer<CoordinatesByLocationNameResponse> getLocationByCityAndState(String city, String state) {
        return getLocationByCityAndState(city, state, 5);
    }

    /**
     * Fetches geographical information for a given city and state in the United States.
     * The method queries the OpenWeatherMap Geocoding API to retrieve latitude, longitude,
     * country, and state information based on the provided city, state, and result limit.
     * </br>
     * Handles cases where no matching locations are found by returning an appropriate error message
     * encapsulated in a {@link RestResponseContainer} object.
     * </br>
     * </br> API Request URL format:
     * </br> http://api.openweathermap.org/geo/1.0/direct?q={city name},{state code},{country code}&limit={limit}&appid={API key}
     *
     * @param city  The name of the city for which to retrieve geographical information.
     * @param state The two-letter state code (e.g., "TX" for Texas) in the United States.
     * @param limit The maximum number of results to return. If multiple locations match the query,
     *              this specifies how many should be returned by the API.
     * @return A {@link RestResponseContainer} containing a {@link CoordinatesByLocationNameResponse} object
     * with geographical information for the specified city and state if found, or a {@link RestError}
     * if no matching locations are found or if an error occurs. The {@link RestResponseContainer}
     * also includes the HTTP status code returned by the API.
     *
     * @see <a href="https://openweathermap.org/api/geocoding-api#direct_name">OpenWeatherMap Geocoding API - Coordinates by City Name</a>
     * @see CoordinatesByLocationNameResponse
     * @see RestError
     */
    public static RestResponseContainer<CoordinatesByLocationNameResponse> getLocationByCityAndState(String city, String state, Integer limit) {
        String url = String.format(BASE_URL + "direct?q=%s,%s,us&limit=%s&appid=%s", city, state, limit, API_KEY);
        WebTarget target = client.target(url);
        Invocation.Builder invocationBuilder = target.request();
        Response response = invocationBuilder.get();
        RestError error = new RestError("No results found for city='" + city + "', state='" + state + "', limit='" + limit + "'");

        if (response.getStatus() >= 200 && response.getStatus() < 300) {
            // Attempt to read as array of CoordinatesByLocationNameResponse
            CoordinatesByLocationNameResponse[] dataArray = response.readEntity(new GenericType<CoordinatesByLocationNameResponse[]>() {});
            // Check if the array has elements, return the first element wrapped in RestResponseContainer
            if (dataArray != null && dataArray.length > 0) {
                return new RestResponseContainer<>(response, dataArray[0]);
            } else {
                System.out.println("Error fetching data for city='" + city + "', state='" + state + "', limit='" + limit + "'");
                return new RestResponseContainer<>(response, error);
            }
        } else {
            error.setMessage("Error fetching data for city='" + city + "', state='" + state + "', limit='" + limit + "'");
            System.out.println("Error fetching data for city='" + city + "', state='" + state + "', limit='" + limit + "'");
            return new RestResponseContainer<>(response, error);
        }
    }


    /**
     * Fetches geographical information for a given ZIP code in the United States.
     * This method queries the OpenWeatherMap Geocoding API to retrieve the latitude, longitude,
     * country, and area name based on the provided ZIP code.
     * <p>
     * The method returns a {@link RestResponseContainer} object, which either contains the
     * {@link CoordinatesByZipCodeResponse} with the geographical information or a
     * {@link RestError} if no matching location is found or if an error occurs
     * during the API request.
     *
     * @param zipCode The ZIP code for which to retrieve geographical information. The ZIP code should be
     *                in the format recognized by the API (e.g., "90210" for Beverly Hills, CA).
     * @return A {@link RestResponseContainer} object containing a {@link CoordinatesByZipCodeResponse} with
     * geographical information for the specified ZIP code, or a {@link RestError}
     * if no matching location is found or if an error occurs during the API request.
     * The {@link RestResponseContainer} also includes the HTTP status code returned by the API.
     *
     * @see <a href="https://openweathermap.org/api/geocoding-api#direct_zip">OpenWeatherMap Geocoding API - Coordinates by ZIP/Post Code</a>
     * @see CoordinatesByZipCodeResponse
     * @see RestError
     */
    public static RestResponseContainer<CoordinatesByZipCodeResponse> getLocationByZip(String zipCode) {
        String url = String.format(BASE_URL + "zip?zip=%s,us&appid=%s", zipCode, API_KEY);
        WebTarget target = client.target(url);
        Invocation.Builder invocationBuilder = target.request();
        Response response = invocationBuilder.get();

        RestResponseContainer<CoordinatesByZipCodeResponse> responseRestResponseContainer = new RestResponseContainer<>(response, CoordinatesByZipCodeResponse.class);

        if (!responseRestResponseContainer.isResponseSuccessful()) {
            System.out.println("No results found for zip='" + zipCode + "'");
            responseRestResponseContainer.getError().setMessage("No results found for zip='" + zipCode + "'");
        }
        return responseRestResponseContainer;
    }

    /**
     * v1
     * if (statusCode == 200) {
     * ObjectMapper mapper = new ObjectMapper();
     * T location = mapper.readValue(response.getEntity().getContent(), responseType);
     * return new Result<>(location, statusCode);
     * } else if(statusCode == 404) {
     * GeoLocationError error = new GeoLocationError("No geolocation information found by provided argument"); // todo think how to add readable message based on arguments passed
     * return new Result<>(error, statusCode);
     * }
     * else {
     * GeoLocationError error = new GeoLocationError("Catastrophic geolocation result");
     * return new Result<>(error, statusCode);
     * }
     */
}