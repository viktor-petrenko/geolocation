package com.geolocation.app.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.geolocation.pojo.GeoLocationError;
import com.geolocation.pojo.Result;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import com.geolocation.pojo.coords_by_location.CoordinatesByLocationNameResponse;
import com.geolocation.pojo.coords_by_zip.CoordinatesByZipCodeResponse;
import java.io.InputStream;
import java.util.Properties;
import java.io.IOException;

public class GeoLocationService {
    private static final String API_KEY;
    private static final String BASE_URL = "http://api.openweathermap.org/geo/1.0/";

    static {
        Properties properties = new Properties();
        try (InputStream input = GeoLocationService.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                throw new RuntimeException("config.properties file not found");
            }
            properties.load(input);
            API_KEY = properties.getProperty("api.key");

            if (API_KEY == null || API_KEY.trim().isEmpty()) {
                System.out.println("API key is empty. Please ensure that the api.key property in config.properties is set correctly.");
                throw new RuntimeException("API key is empty");
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to load API key from properties file", e);
        }
    }


    /**
     * Fetches geographical information for a given city and state in the United States.
     * The method queries the OpenWeatherMap Geocoding API to retrieve the latitude, longitude,
     * country, and state information based on the city and state provided. This method
     * handles cases where no matching locations are found by returning an appropriate
     * error message encapsulated in a {@link Result} object.
     *
     * @param city  The name of the city for which to retrieve geographical information.
     * @param state The two-letter state code (e.g., "TX" for Texas) in the United States.
     * @return A {@link Result} containing a {@link CoordinatesByLocationNameResponse} object
     * with geographical information for the specified city and state if found, or a
     * {@link GeoLocationError} if no matching locations are found or if an error occurs.
     * @see <a href="https://openweathermap.org/api/geocoding-api#direct_name">OpenWeatherMap Geocoding API - Coordinates by City Name</a>
     * @see CoordinatesByLocationNameResponse
     * @see GeoLocationError
     */
    public Result<CoordinatesByLocationNameResponse> getLocationByCityAndState(String city, String state) {
        return getLocationByCityAndState(city, state, 5);
    }

    /**
     * Fetches geographical information for a given city and state in the United States.
     * The method queries the OpenWeatherMap Geocoding API to retrieve the latitude, longitude,
     * country, and state information based on the city and state provided. The number of
     * results returned can be limited by specifying the {@code limit} parameter.
     *
     * This method returns a {@link Result} object which either contains the
     * {@link CoordinatesByLocationNameResponse} with the geographical information or a
     * {@link GeoLocationError} if no matching locations are found or if an error occurs
     * during the API request.
     *
     * @param city  The name of the city for which to retrieve geographical information.
     * @param state The two-letter state code (e.g., "TX" for Texas) in the United States.
     * @param limit The maximum number of results to return. If multiple locations match the query,
     *              this specifies how many should be returned by the API.
     * @return A {@link Result} object containing a {@link CoordinatesByLocationNameResponse} with
     * geographical information for the specified city and state, or a {@link GeoLocationError}
     * if no matching locations are found or if an error occurs during the API request.
     * @see <a href="https://openweathermap.org/api/geocoding-api#direct_name">OpenWeatherMap Geocoding API - Coordinates by City Name</a>
     * @see CoordinatesByLocationNameResponse
     * @see GeoLocationError
     */
    public Result<CoordinatesByLocationNameResponse> getLocationByCityAndState(String city, String state, Integer limit) {
        try {
            String url = String.format(BASE_URL + "direct?q=%s,%s,us&limit=%s&appid=%s", city, state, limit, API_KEY);
            CoordinatesByLocationNameResponse[] locations = fetchLocation(url, CoordinatesByLocationNameResponse[].class);
            if (locations != null && locations.length > 0) {
                return new Result<>(locations[0]);
            } else {
                return new Result<>(new GeoLocationError("No results found for " + city + ", " + state));
            }
        } catch (Exception e) {
            return new Result<>(new GeoLocationError("Error fetching location data", e));
        }
    }

    /**
     * Fetches geographical information for a given ZIP code in the United States.
     * The method queries the OpenWeatherMap Geocoding API to retrieve the latitude, longitude,
     * country, and area name based on the provided ZIP code.
     *
     * This method returns a {@link Result} object which either contains the
     * {@link CoordinatesByZipCodeResponse} with the geographical information or a
     * {@link GeoLocationError} if no matching location is found or if an error occurs
     * during the API request.
     *
     * @param zipCode The ZIP code for which to retrieve geographical information. The ZIP code should be
     *                in the format recognized by the API (e.g., "90210" for Beverly Hills, CA).
     * @return A {@link Result} object containing a {@link CoordinatesByZipCodeResponse} with
     * geographical information for the specified ZIP code, or a {@link GeoLocationError}
     * if no matching location is found or if an error occurs during the API request.
     * @see <a href="https://openweathermap.org/api/geocoding-api#direct_zip">OpenWeatherMap Geocoding API - Coordinates by ZIP/Post Code</a>
     * @see CoordinatesByZipCodeResponse
     * @see GeoLocationError
     */
    public Result<CoordinatesByZipCodeResponse> getLocationByZip(String zipCode)  {
        try {String url = String.format(BASE_URL + "zip?zip=%s,us&appid=%s", zipCode, API_KEY);
            CoordinatesByZipCodeResponse location = fetchLocation(url, CoordinatesByZipCodeResponse.class);
            if (location != null) {
                return new Result<>(location);
            } else {
                return new Result<>(new GeoLocationError("No results found for zip code " + zipCode));
            }
        } catch (Exception e) {
            return new Result<>(new GeoLocationError("Error fetching location data", e));
        }
    }

    /**
     * Sends an HTTP GET request to the specified URL and retrieves the response as a deserialized object of the specified type.
     * This method is designed to interact with RESTful APIs and automatically converts the JSON response
     * into a Java object of the specified type using the Jackson library.
     *
     * @param url          The URL to send the HTTP GET request to. This should be a fully constructed URL
     *                     that points to the desired API endpoint.
     * @param responseType The class type to which the JSON response should be deserialized. This allows the method
     *                     to convert the response into the appropriate Java object.
     * @param <T>          The type of the object to be returned, inferred from the responseType parameter.
     * @return An instance of the specified response type {@code T} if the request is successful and the response is
     *         deserialized correctly, or {@code null} if the request fails or if the status code is not 200.
     * @throws IOException If an I/O error occurs while executing the HTTP request or during the deserialization process.
     * @see ObjectMapper
     * @see HttpClient
     */

    public <T> T fetchLocation(String url, Class<T> responseType) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
        HttpResponse response = client.execute(request);

        if (response.getStatusLine().getStatusCode() == 200) {
            ObjectMapper mapper = new ObjectMapper();
            T location = mapper.readValue(response.getEntity().getContent(), responseType);
            return location;
        }
        return null;
    }

}