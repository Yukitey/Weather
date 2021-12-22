package com.company.weather.data;

import com.company.weather.db.SQLConect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class GetWeather {

    // Method for obtaining and converting data about the current weather from the site
    private static int getDateWeather() throws IOException{

        int resultWeather = 0;
        URL adr = new URL("https://yandex.ru");

        // We get the html code of the page from the Yandex site
        BufferedReader in = new BufferedReader(new InputStreamReader(adr.openStream()));
        String line;
        StringBuilder result = new StringBuilder();
            while ((line = in.readLine()) != null) {
            result.append(line);
        }

        // Setting the framework for the pattern
        final String regex = "weather__temp'>(.*)°</div>";

        // Fetching data using regular expressions
        final Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        final Matcher matcher = pattern.matcher(result.toString());

         while (matcher.find()) {
             // We convert the result into an integer
             resultWeather = Integer.parseInt(matcher.group(1).replaceAll("−", "-"));
         }
            in.close();
            return resultWeather;
    }

    // Method that returns weather data from a database or from a website
    public static int getCurrentWeather() {
        int currentWeather = 1;
        // We get the current date and convert it to the required format
        Date dt = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy.MM.dd");

        try {
            // Database connection
            SQLConect db = new SQLConect();
            // Request to check if there is a record in the database with the current date
            db.SQLQuery("SELECT * FROM weather_history WHERE weather_date ='"+formatForDateNow.format(dt)+"'");
            // Checking the presence of data in the database
            if (db.notNullResurlt()){
                System.out.println("Data found in database");
                currentWeather = Integer.parseInt(db.getStringResult("weather_value"));
            } else {
                System.out.println("The data was not found in the database, new data was taken from the Yandex site");
                // Getting data from the site
                currentWeather = getDateWeather();
                // Creating a record about the current weather in the database
                db.SQLQuery("INSERT INTO weather_history(weather_date, weather_value)VALUES ('"+formatForDateNow.format(dt)+"', "+currentWeather+")");
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return currentWeather;
    }
}
