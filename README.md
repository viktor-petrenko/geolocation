# GeoLocation Utility


## Overview

The GeoLocation Utility is a command-line tool that interacts with the OpenWeatherMap Geocoding API to fetch geographical information based on city/state or ZIP code inputs. The tool returns latitude, longitude, and other relevant location data for specified inputs within the United States.

## Features

- Fetch geographical data by city and state.
- Fetch geographical data by ZIP code.
- Handle multiple inputs in a single execution.
- Robust error handling for invalid inputs or API errors.

### Usage
1. download jar : [geolocation-1.0.jar](./geolocation-1.0.jar) (file could be found at the repo root https://github.com/viktor-petrenko/geolocation/blob/main/geolocation-1.0.jar)
2. open terminal
3. cd to the folder which contains geolocation-1.0.jar
4. Execute following command java -jar geolocation-1.0.jar "Madison, WI" "12345" "Chicago, IL" "10001" "85382" "dsadsa, -1"
5. output : 


```
City: Madison
Latitude: 43.074761
Longitude: -89.3837613
Country: US
-------------------------
Zip: 12345
Latitude: 42.8142
Longitude: -73.9396
Country: US
-------------------------
City: Chicago
Latitude: 41.8755616
Longitude: -87.6244212
Country: US
-------------------------
Zip: 10001
Latitude: 40.7484
Longitude: -73.9967
Country: US
-------------------------
Zip: 85382
Latitude: 33.6308
Longitude: -112.2072
Country: US
-------------------------
No results found for dsadsa, -1
```

# Setup and build your own jar to run Integration tests

### Prerequisites

- Java 19 or higher
- Maven 3.6.0 or higher
- JAVA_HOME and MAVEN_HOME, MNV_HOME system variables setup or IDE environment setup

### Cloning the Repository
```
git clone https://github.com/viktor-petrenko/geolocation.git
cd geolocation
```
## (REQUIRED) ADD API KEY TO THE CONFIG file
add api key to the src/main/resources/config.properties\
the file body should look like 
```
api.key=example_of_api_key
```
api key could be found at the "Take-Home Test" body or https://home.openweathermap.org/users/sign_in
### Building the Project
Use Maven to build the project and create a runnable JAR file.
```
mvn clean package
```
This will generate a geolocation-exercise-1.0-SNAPSHOT.jar file in the target directory.

### How to use generated jar

To run the utility, use the following command (assuming you did previous steps and jar is generated or you did download file ):
```
java -jar target/geolocation-exercise-1.0-SNAPSHOT.jar "Madison, WI" "12345" "Chicago, IL" "10001" "85382" "dsadsa, -1"
```
syntax
```
java -jar path_to_the_jar arguments
```
Command-Line Arguments

    City and State: "City, State" (e.g., "Madison, WI")
    ZIP Code: "ZIP Code" (e.g., "90210")

Example
```
java -jar target/geolocation-exercise-1.0-SNAPSHOT.jar "Madison, WI" "90210"
```
The utility will print the latitude, longitude, country, and other relevant data for the provided locations.

### Testing
The project includes integration tests written using TestNG and AssertJ. 
To run the tests clone repository and run tests in the following folder src/test/java/com/geolocation

the following command to execute unit tests
```
mvn clean test
```
Test Scenarios Covered

    Successful API call with city and state.
    Successful API call with ZIP code.
    Handling of invalid city/state.
    Handling of invalid ZIP code.
    Handling of empty or null input.
    Handling of invalid limit.
    
Or run following command to generate JAR and run Integration tests 
```
mvn clean verify
```
or execute tests manually
1. mvn clean package
2. navigate to src/test/java/com/geolocation/integration_tests/GeolocationIntegrationTest.java
3. trigger them manually 
Test Scenarios Covered

    Successful API call with city and state.
    Successful API call with ZIP code.
    Handling of invalid city/state.
    Handling of invalid ZIP code.
    Handling of multiple locations
   
### Error Handling

The utility has built-in error handling that provides informative messages if a location is not found or if there is an issue with the API request. Errors are encapsulated in a GeoLocationError object and returned to the user with a descriptive message.
Contribution

