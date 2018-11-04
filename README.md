# Weather REST API
REST API developed in Java with Spring Boot in order to provide information on weather information.

## Summary

This API was all developed in Java 8 and Spring Boot in order to demonstrate an application example where, from the name of a city look for weather's information regarding that city based on the following metrics:
- Average of daily (06:00 - 18:00) and nightly (18:00 - 06:00) temperatures in Celsius for the next 3 days from today's date.
- Average of pressure for the next 3 days from today's date.

## API documentation and Test of user
To document the API, I used the maven dependency of Swagger 2, with this to access it, after the project is UP just consult the url: `http://{host}/api/v1/swagger-ui.html`. In my case, I used it for local tests `http://localhost:8080/api/v1/swagger-ui.html`. When you open Swagger, you will need to click on `weather-controller` and then on the GET method that will appear `/weather/nearby-forecast`. After that, it will open the EndPoint detail. Click `Try it out`, then enter the name of the city you want to see. To make the request, click `Execute`. Here is an example of the returned JSON:
```
[
  {
    "date": "2018-11-04",
    "daily": 10.65,
    "nightly": 9.65,
    "pressure": 10.31
  },
  {
    "date": "2018-11-05",
    "daily": 12.58,
    "nightly": 9.52,
    "pressure": 11.05
  },
  {
    "date": "2018-11-06",
    "daily": 13.71,
    "nightly": 11.76,
    "pressure": 12.74
  },
  {
    "date": "2018-11-07",
    "daily": 13.84,
    "nightly": 13.53,
    "pressure": 13.68
  }
]
```
As you can see, the return is a list of objects with information from the next three days (including current). This information is:
- `date`: Reference date.
- `daily`: Average of daily (06:00 - 18:00).
- `nightly`: Average of nightly (18:00 - 06:00).
- `pressure`: Average pressure on day.

**_ps. temperatures in Celsius_**

This test can also be performed by a specific tool for REST calls, in my case I used Postman. The API test linke we developed is obtained by the code below:
```
curl -X GET \
  'http://localhost:8080/api/v1/weather/nearby-forecast?city=London' \
  -H 'Content-Type: application/json'
```

# Run and Test
The maven version used was 3.5.3 and the commands for testing, compiling, installing, cleaning, package, etc. are those used by maven. Follow the documentation: https://maven.apache.org/guides/getting-started

`ex. Test = mvn test`

# My rationale and motivations behind the code
### Why choose Spring Boot?
In my opinion Spring Boot is the most robust and complete tool for working with Java. Its range of libraries are easily imported and make developing complex applications extremely simple. In addition, I have a long experience with the tool and this enables me and gives me more security because I know how much it supports large volumes of requests and facilitates the development.

### IDE:
The tool I used for development was Eclipse, however I preferred to use a customization of the same made available by spring.io itself, it is called `SpringToolSuite4` (`https://spring.io/tools`). With this tool I can run local running the `Spring Boot App` mode and I can test my code using` Maven test`, leaving only the dependency of Java 8 installed on the machine, like any Eclipse.

### Dependencies:
I decided to use the latest version of Spring Boot (2.1.1.BUILD-SNAPSHOT) to take advantage of what it can best offer me and to use interesting annotation features, such as `@GetMapping` in the controller. I imported dependencies from Spring Boot libraries so our application can make EndPoints available, can run Swagger along with Swagger-ui, be able to render objects returned through the Jackson library, and be able to run tests.

### Configuration
I decided to change the base path of the application in the main class `WeatherDataApplication`. With this, we can use good practices in the definition of the URI. Another decision that I consider important was to inform which package Swagger should track in the `SwaggerConfig.java` file, this limits the package that will contain the controllers that will be displayed in Swagger.

### Controller:
In the controller, I decided to use the @ApiOperation and @ApiParam annotations to document API information through Swagger. In it, I had a great challenge when defining the path of the URI, because this API is not a CRUD or a query of a variable amount of information and therefore I defined to return a list with the result in the JSON without paging as it will always bring a small amount of records.

### Service
For a smooth operation of the process reducing the number of lines so as not to damage the readability of the code some decisions have been made. The first one was to not just use lambda, this functionality significantly reduces the number of lines in the code, however, depending on how much you enter functions over functions it becomes very easy to lose code readability, putting a lot of complexity on a little line of code. So I decided to do after the 5 day API query, a for loop on the days that will be displayed, based on that day I filter the API return with the lambda only records that are in memory of the day based on. For this, I had to define a final variable, getting the current loop value again because the lambda function does not accept non-final variables to ensure that they do not change their value. It was a decision made in order to reduce the amount of lines, if I made a traditional loop I would end up developing at least three times the amount of lines of that filter. Another decision I took and what I understand as very interesting for the proper functioning of the API was to return a ResponseEntity and treat it in try_catch. So we avoided the return of the openweathermap API for example 400 and our API returns 500, that would be at least incoherent, the way I developed the feedback will be passed since the openweathermap API because they use the convention codes thus avoiding some validations.
