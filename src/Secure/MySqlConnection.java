/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Secure;
import java.sql.*;
import javax.swing.*;

/**
 *
 * @author Roya
 */
public class MySqlConnection {
    Connection conn = null;
    public static Connection ConnectDB(){
        try{
            //Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/hotel", "root", "roya");
            //JOptionPane.showMessageDialog(null, "connected to DB");
            System.out.println("connected");
            return conn;
        }catch(Exception e){
            //JOptionPane.showMessageDialog(null, e);
            System.out.println("not connected");
            return null;
        }
    
    }
}

