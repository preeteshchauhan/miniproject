package com.example.miniproject;

import java.sql.Connection;
import java.sql.DriverManager;

public class mydatabase {

    public  Connection databaseLink;

    public  Connection connectDb(){


            String databaseName = "growvest";


            String username= "root";
            String password= "chauhan";

            String url = "jdbc:mysql://localhost/" + databaseName;

            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                databaseLink = DriverManager.getConnection(url,username,password);
                System.out.println("done");
            } catch (Exception e) {
                e.printStackTrace();
                // e.getCause();
                System.out.println("not done");
            }
            return databaseLink;
    }
}