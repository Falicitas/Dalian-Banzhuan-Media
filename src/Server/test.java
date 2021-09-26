import java.sql.*;

import java.io.*;
import java.net.*;
public class test {
    public static final String URL = "jdbc:mysql://127.0.0.1:3306/project";//test
    public static final String USER = "debian-sys-maint";
    public static final String PASSWORD = "xTZEF0G87SMCClsh";//qweasdzxc0
    public static void main(String[] args){

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL,USER,PASSWORD);
        } catch (Exception e) {
		e.printStackTrace();
        }
    }
}

