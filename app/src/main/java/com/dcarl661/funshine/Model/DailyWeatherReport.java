package com.dcarl661.funshine.Model;

/**
 * Created by David Carlson on 12/30/2016.
 */

public class DailyWeatherReport
{

    public static final String WEATHER_TYPE_CLOUDS = "Clouds";
    public static final String WEATHER_TYPE_CLEAR  = "Clear";
    public static final String WEATHER_TYPE_RAIN   = "Rain";
    public static final String WEATHER_TYPE_WIND   = "Wind";
    public static final String WEATHER_TYPE_SNOW   = "Snow";

    private String cityName;
    private String country;
    private int    currentTemp;
    private int    maxTemp;
    private int    minTemp;
    private String weather;
    private String formattedDate;


    public DailyWeatherReport(String cityName, String country, int currentTemp,
                              int maxTemp, int minTemp, String weather, String formattedDate)
    {
        this.cityName      = cityName;
        this.country       = country;
        this.currentTemp   = currentTemp;
        this.maxTemp       = maxTemp;
        this.minTemp       = minTemp;
        this.weather       = weather;
        this.formattedDate = RawDateToPretty(formattedDate);
    }

    public String RawDateToPretty(String rawDate)
    {
        //convert raw to formatted
        return "May 1";
    }

    public String getCityName() {
        return cityName;
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    public String getCountry() {
        return country;
    }

    public int getCurrentTemp() {
        return currentTemp;
    }

    public int getMaxTemp() {
        return maxTemp;
    }

    public int getMinTemp() {
        return minTemp;
    }

    public String getWeather() {
        return weather;
    }
}
