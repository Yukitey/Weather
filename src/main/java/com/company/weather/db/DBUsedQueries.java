package com.company.weather.db;

import java.sql.PreparedStatement;
import java.sql.SQLData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DBUsedQueries extends SQLConect{

    // Execution of the request whether there is data on the specified date
    public void sqlUsedSelectWeatherHistoryCheck(Date dt) {
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd");
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM weather_history WHERE weather_date = ? ");
            preparedStatement.setDate(1, java.sql.Date.valueOf(formatForDateNow.format(dt))) ;
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    // Executing a request to add a record about the current weather to the database
    public void sqlUsedInsertNewWeather(Date dt, int weatherValues){
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd");
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO weather_history(weather_date, weather_value)VALUES (?, ?)");
            preparedStatement.setDate(1, java.sql.Date.valueOf(formatForDateNow.format(dt)));
            preparedStatement.setInt(2, weatherValues);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
