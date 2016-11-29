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
 * @author Roya, Bundit, Zur
 */
public class MySqlConnection {
	private final String DB = "jdbc:mysql://localhost/HotelReservation";
	private final String DB_USER = "root"; //username for database 
	private final String DB_PASSWORD = "enter your password here"; //password for database

	MySqlConnection(){

	}

	/**
	 * Returns true if the guest tuple exists in the Guest table 
	 * @param username the user's login name inputted in the username textfield on login page
	 * @param password the password inputted into the passwordfield on login page
	 * @return true if login credentials are correct, false if not
	 */
	boolean loginGuest(String username, String password) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean valid = false;
		String sql = "SELECT *\n"
				+ "FROM guest\n"
				+ "WHERE username=? and password=?";
		try {
			conn = DriverManager.getConnection(DB, DB_USER, DB_PASSWORD);
			ps = conn.prepareStatement(sql);
			ps.setString(1,username);
			ps.setString(2,password);
			rs = ps.executeQuery();

			if(rs.next()) {
				valid = true;
			} else {
				valid = false;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
			System.out.println("Not correctly working");
		} finally {
			try{if(conn != null) conn.close();} catch (Exception e){}
			try{if(ps != null) ps.close();} catch (Exception e){}
			try{if(rs != null) rs.close();} catch (Exception e){}

		}
		if (valid) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Adds a new guest user to the database
	 * @param user
	 * @param pass
	 * @param email
	 */
	void addNewGuest(String user, String pass, String email) {
		Connection conn = null;
		PreparedStatement ps = null;
		//ResultSet rs = null;

		String sql = "INSERT\n"
				+ "INTO guest\n"
				+ "VALUES(null,?,?,?)";
		try {
			conn = DriverManager.getConnection(DB, DB_USER, DB_PASSWORD);
			ps = conn.prepareStatement(sql);
			ps.setString(1,user);
			ps.setString(2,pass);
			ps.setString(3, email);
			ps.execute();

			JOptionPane.showMessageDialog(null,"New guest account created!\nPlease log in.");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		} finally {
			try{if(conn != null) conn.close();} catch (Exception e){}
			try{if(ps != null) ps.close();} catch (Exception e){}
		}
	}

	void cancelReservation(int reservationNum) {

	}

	void viewReservation(int reservationNum) {

	}
}

