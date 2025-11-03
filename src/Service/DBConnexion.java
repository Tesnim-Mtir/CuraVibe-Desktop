package com.example.final_version.Services;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnexion {
    static String login = "root";
    static String pwd = "";
    static String url = "jdbc:mysql://localhost:3306/curavibe";
    static String driver ="com.mysql.cj.jdbc.Driver";


    public static  Connection getCon(){
        Connection con = null;
        try {
            Class.forName(driver);
            try {
                con= DriverManager.getConnection(url, login, pwd);
            } catch (SQLException e){
                throw new RuntimeException(e);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


        return con;
    }



}
