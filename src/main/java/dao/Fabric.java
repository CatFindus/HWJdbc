package dao;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;

public class Fabric {

    private static final String url = "jdbc:postgresql://localhost:5432/person_db";
    private static final String user = "admin";
    private static final String pass = "admin";


    @SneakyThrows
    public static Connection get() {
        return DriverManager.getConnection(url, user, pass);
    }

}
