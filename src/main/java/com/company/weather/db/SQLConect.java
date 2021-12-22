package com.company.weather.db;

import java.sql.*;

public class SQLConect {
    protected static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/Weather";
    protected static final String USER = "postgres";
    protected static final String PASS = "root";

    protected Connection connection;
    protected Statement statement;
    protected PreparedStatement preparedStatement;
    protected ResultSet resultSet;





    // Database Connection Designer
    public SQLConect(){
        System.out.println("Сonnection to PostgreSQL JDBC");

        // Checking for a Driver
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            e.printStackTrace();
            return;
        }

        System.out.println("PostgreSQL JDBC Driver successfully connected");
        // Zeroing the connection
        if (connection != null) connection = null;

        // Establishing a connection
        try {
            connection = DriverManager
                    .getConnection(DB_URL, USER, PASS);

        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
            return;
        }
        // Checking the connection
        if (connection != null) {
            System.out.println("You successfully connected to database now");
        } else {
            System.out.println("Failed to make connection to database");
        }
    }


    // A method that implements sending a request to the database and storing the result into a connection object
    public void SQLQuery(String query) throws SQLException{
        if (statement != null) statement = null;
        try {

            statement = connection.createStatement();

            //Executing a request
            resultSet = statement.executeQuery(query);
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    // Method for checking the receipt of data from the database
    public boolean notNullResult() throws SQLException {
        return resultSet.next();
    }

    // Method for retrieving a query result string by the required field
    public String getStringResult(String seekingField) throws SQLException {
        return resultSet.getString(seekingField);
    }
}
