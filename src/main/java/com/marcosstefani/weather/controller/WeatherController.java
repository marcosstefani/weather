package com.marcosstefani.weather.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marcosstefani.weather.service.WeatherService;

@RestController
@RequestMapping("weather")
public class WeatherController {

	@Autowired
	private WeatherService weatherService;

	@GetMapping(value = "/nearby-forecast", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> weatherForecastAverage(@RequestParam(required = true) String city) {
		return weatherService.weatherForecastAverage(city);
	}

}
