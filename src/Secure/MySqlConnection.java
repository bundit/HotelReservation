
package Secure;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

/**
 *
 * @author Roya, Bundit, Zur
 */
public class MySqlConnection {
	private final String DB = "jdbc:mysql://localhost:3306/HotelReservation";
	private final String DB_USER = "root"; //username for database 
	private final String DB_PASSWORD = "your password goes here"; //password for database
	private int guest_id;
	private int admin_id;
	
	MySqlConnection(){

	}

	/**
	 * Returns true if the guest tuple exists in the Guest table 
	 * @param username the user's login name inputted in the username textfield on login page
	 * @param password the password inputted into the passwordfield on login page
	 * @return true if login credentials are correct, false if not
	 */
	boolean loginGuest(String userEmail, String password) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean valid = false;
		String sql = "SELECT *\n"
				+ "FROM guest\n"
				+ "WHERE email=? and password=?";
		try {
			conn = DriverManager.getConnection(DB, DB_USER, DB_PASSWORD);
			ps = conn.prepareStatement(sql);
			ps.setString(1,userEmail);
			ps.setString(2,password);
			rs = ps.executeQuery();

			if(rs.next()) {
				guest_id = rs.getInt("guest_id");
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
		} 
		return false;
	}
	/**
	 * Returns true if the admin tuple exists in the admin table 
	 * @param username the user's login name inputted in the username textfield on login page
	 * @param password the password inputted into the passwordfield on login page
	 * @return true if login credentials are correct, false if not
	 */
	boolean loginAdmin(String user, String pass) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean valid = false;
		String sql = "SELECT *\n"
				+ "FROM admin\n"
				+ "WHERE email=? and password=?";
		try {
			conn = DriverManager.getConnection(DB, DB_USER, DB_PASSWORD);
			ps = conn.prepareStatement(sql);
			ps.setString(1,user);
			ps.setString(2,pass);
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
		} 
		return false;
	}
	
	int getGuest_Id(){
		return guest_id;
	}
	int getAdmin_Id(){
		return admin_id;
	}
	
	/**
	 * Gets a list of distinct items from the table specified
	 * @param table the table to retrieve from 
	 * @param attribute the column to return
	 * @return
	 */
	ArrayList<String> getListOfDetails(String table,String attribute) {
		Connection conn = null;
		Statement ps = null;
		ResultSet rs = null;
		ArrayList<String> distinctListofItems = new ArrayList<>();

		String sql = "SELECT DISTINCT " + attribute + "\n"
				+ "FROM " + table + "\n"
				+ "ORDER BY " + attribute;
		try {
			conn = DriverManager.getConnection(DB, DB_USER, DB_PASSWORD);
			ps = conn.createStatement();
			
			rs = ps.executeQuery(sql);
			
			while(rs.next()) {
				
				distinctListofItems.add(rs.getString(attribute));	
			}
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		} finally {
			try{if(conn != null) conn.close();} catch (Exception e){}
			try{if(ps != null) ps.close();} catch (Exception e){}
		}
		return distinctListofItems;
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

	/**
	 * Cancels a guest's reservation by their reservation number
	 * @param reservationNum
	 */
	void cancelReservation(int reservationNum) {
		Connection conn = null;
		PreparedStatement ps = null;
		//ResultSet rs = null;

		String sql = "DELETE\n"
				+ "FROM reservation\n"
				+ "WhERE reserve_id = ?";
		try {
			conn = DriverManager.getConnection(DB, DB_USER, DB_PASSWORD);
			ps = conn.prepareStatement(sql);
			ps.setString(1,"" +reservationNum);
			ps.execute();

			JOptionPane.showMessageDialog(null,"Reservation " + reservationNum + " Canceled");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		} finally {
			try{if(conn != null) conn.close();} catch (Exception e){}
			try{if(ps != null) ps.close();} catch (Exception e){}
		}
	}

	/**
	 * Displays a guest's reservation by their reservation number
	 * @param reservationNum
	 */
	void viewReservation(int reservationNum) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sql = "SELECT *\n"
				+ "FROM reservation,guest,hotel\n"
				+ "WHERE reservation.guest_id = guest.guest_id\n" 
				+ "and reservation.hotel_id = hotel.hotel_id\n"
				+ "and reserve_id=?";
		try {
			conn = DriverManager.getConnection(DB, DB_USER, DB_PASSWORD);
			ps = conn.prepareStatement(sql);
			ps.setInt(1,reservationNum);
			
			rs = ps.executeQuery();

			if(rs.next()) {
				JOptionPane.showMessageDialog(null, "\nReservation Number: " + reservationNum + " for " + rs.getString("username") + "\n"
						+ "Check In Date: " + rs.getString("checkindate") + "\n" 
						+ "Check Out Date: " + rs.getString("checkoutdate") + "\n" 
						+ "Hotel: " + rs.getString("name") + "\nRoom Number: " + rs.getString("room_id") + "\n"
						+ "\nTotal Cost: " + rs.getString("totalCost"));
			} else {
				JOptionPane.showMessageDialog(null, "Reservation " + reservationNum + " does not exist");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
			System.out.println("Not correctly working");
		} finally {
			try{if(conn != null) conn.close();} catch (Exception e){}
			try{if(ps != null) ps.close();} catch (Exception e){}
			try{if(rs != null) rs.close();} catch (Exception e){}
		}
	}
	ArrayList<String> getHotelInfoForGuest(String dateIn, String dateOut, String hotelName, 
		String locations, String roomType, String minimumStars, String capacity, String maxPrice){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<String> toReturn = new ArrayList<>();
		
		String sql = "SELECT name, address, type, star, capacity, price, room_id, hotel.hotel_id\n"
					+ "FROM room JOIN hotel ON room.hotel_id = hotel.hotel_id\n"
					+ "WHERE room_id NOT IN (select r2.room_id\n"
					+ "FROM room AS r1 JOIN reservation AS r2 ON r1.room_id = r2.room_id\n"
					+ "WHERE (checkindate < ? AND checkindate <= ?)\n"
					+ "OR (checkoutdate >= ? AND checkoutdate > ?))\n";
//					+ "AND hotel.name = ?\n"
//					+ "AND hotel.address = ?\n"
//					+ "AND room.type = ?\n"
//					+ "AND hotel.star >= ?\n"
//					+ "AND room.capacity = ?\n"
//					+ "AND room.price <= ?\n";
		

		
		if(!hotelName.equals("All Hotels")) sql += "AND hotel.name = '" + hotelName + "'\n";//hotelName = "hotel.name";
			
		if(!locations.equals("All Cities")) sql += "AND hotel.address = '" + locations + "'\n";//locations = "address";
		
		if(!roomType.equals("Any Type")) sql += "AND room.type = '" + roomType + "'\n";//roomType = "type";
		
		if(!minimumStars.equals("Any Rating")) sql += "AND hotel.star >= '" + minimumStars + "'\n";//minimumStars = "star";
		
		if(!capacity.equals("Any Capacity")) sql += "AND room.capacity = '" + capacity + "'\n";//capacity = "capacity";
		
		if(!maxPrice.equals("Any Price")) sql += "AND room.price <= '" + maxPrice + "'\n";//maxPrice = "price";		
					
		
		
		try {
			conn = DriverManager.getConnection(DB, DB_USER, DB_PASSWORD);
	
			ps = conn.prepareStatement(sql);
			ps.setString(1, dateIn);
			ps.setString(2, dateIn);
			ps.setString(3, dateOut);
			ps.setString(4, dateOut);
//			ps.setString(3, hotelName);
//			ps.setString(4, locations);
//			ps.setString(5, roomType);
//			ps.setString(6, minimumStars);
//			ps.setString(7, capacity);
//			ps.setString(8, maxPrice);
			
			rs = ps.executeQuery();

			while(rs.next()) {
				toReturn.add(rs.getString("name"));
				toReturn.add(rs.getString("address"));
				toReturn.add(rs.getString("type"));
				toReturn.add(rs.getString("star"));
				toReturn.add(rs.getString("capacity"));
				toReturn.add(rs.getString("price"));
				toReturn.add(rs.getString("room_id"));
				toReturn.add(rs.getString("hotel_id"));

			}
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
			System.out.println("Not correctly working");
		} finally {
			try{if(conn != null) conn.close();} catch (Exception e){}
			try{if(ps != null) ps.close();} catch (Exception e){}
			try{if(rs != null) rs.close();} catch (Exception e){}
		}
		return toReturn;
	}

	void createNewReservation(String roomID, String hotelID, int guestID,String checkIn, String checkOut, String cost) {
		Connection conn = null;
		PreparedStatement ps = null;
		
		String sql = "INSERT INTO reservation\n"
				+ "VALUES(null, ?, ?, ?, ?, ?, ?)";
		try {
			conn = DriverManager.getConnection(DB, DB_USER, DB_PASSWORD);
			ps = conn.prepareStatement(sql);
			ps.setString(1,roomID);
			ps.setString(2,hotelID);
			ps.setInt(3,guestID);
			ps.setString(4,checkIn);
			ps.setString(5,checkOut);
			ps.setString(6,cost);
			System.out.println("create new reservation");
			
			ps.executeUpdate();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		} finally {
			try{if(conn != null) conn.close();} catch (Exception e){}
			try{if(ps != null) ps.close();} catch (Exception e){}
		}
	}

	
}

