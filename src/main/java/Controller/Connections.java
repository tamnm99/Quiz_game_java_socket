/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author tamng
 */
public class Connections {

    private static Connection conn = null;

    public static Connection newConnect() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/game_trac_nghiem?serverTimezone=UTC", "root", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
