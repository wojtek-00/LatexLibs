package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class App {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/Zoo";
        String user = "postgres";
        String password = "geheim";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("✅ Połączenie udane!");

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT version();");

            if (rs.next()) {
                System.out.println("Wersja PostgreSQL: " + rs.getString(1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
