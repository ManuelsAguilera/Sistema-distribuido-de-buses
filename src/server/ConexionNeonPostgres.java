package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionNeonPostgres {
    public static void main(String[] args) {
    	ApiManager.getRoute("-34.16967","-70.75124","-33.45033","-70.68856");
    	/*
        var sql = "CREATE TABLE VichoDameAcceso (" +
                "    id SERIAL PRIMARY KEY," +
                "    name VARCHAR(255) NOT NULL," +
                "    price DECIMAL(10, 2) NOT NULL" +
                ");";
        
        
        try (var conn =  DB.connect();
             var stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }*/
    }
}