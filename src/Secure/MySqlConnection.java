
package Secure;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Roya, Bundit, Zur
 */
class MySqlConnection {
	private final String DB = "jdbc:mysql://localhost:3306/HotelReservation";
	private final String DB_USER = "root"; //username for database 
	private final String DB_PASSWORD = "Jongsuwan123123"; //password for database
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
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<String> distinctListofItems = new ArrayList<>();

		String sql = "SELECT DISTINCT " + attribute + "\n"
				+ "FROM " + table + "\n"
				+ "ORDER BY " + attribute;
		try {
			conn = DriverManager.getConnection(DB, DB_USER, DB_PASSWORD);
			ps = conn.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				
				distinctListofItems.add(rs.getString(attribute));	
			}
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		} finally {
			try{if(conn != null) conn.close();} catch (Exception e){}
			try{if(ps != null) ps.close();} catch (Exception e){}
			try{if(rs != null) rs.close();} catch (Exception e) {}
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
			ps.executeUpdate();

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
			ps.executeUpdate();

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
				JOptionPane.showMessageDialog(null, "\nDisplaying reservation for " + rs.getString("username") + "\n\nReservation Number: " + reservationNum + "\n"
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
		String locations, String roomType, String minimumStars, String capacity, String maxPrice, String order){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<String> toReturn = new ArrayList<>();
		ArrayList<String> bindVariables = new ArrayList<>();
		
		String sql = "SELECT *\n"
				+ "FROM room JOIN hotel ON room.hotel_id = hotel.hotel_id\n"
				+ "WHERE room_id NOT IN ("
										+ "SELECT 	r1.room_id\n"
										+ "FROM 	room as r1 join reservation as r2 on r1.room_id = r2.room_id\n"
										+ "WHERE 	(checkindate <= ? AND checkoutdate >= ?)\n"
													+ "OR (checkindate < ? AND checkoutdate >= ?)\n"
													+ "OR (? <= checkindate AND ? >= checkindate)\n"
									+ ")\n";
		
		if(!hotelName.equals("All Hotels")) {
			sql += "AND hotel.name = ?\n";
			bindVariables.add(hotelName);
		}
		if(!locations.equals("All Cities")) {
			sql += "AND hotel.address = ?\n";
			bindVariables.add(locations);
		}
		if(!roomType.equals("Any Type")) {
			sql += "AND room.type = ?\n";
			bindVariables.add(roomType);
		}
		if(!minimumStars.equals("Any Rating")) {
			sql += "AND hotel.star >= ?\n";
			bindVariables.add(minimumStars);
		}
		if(!capacity.equals("Any Capacity")) {
			sql += "AND room.capacity = ?\n";
			bindVariables.add(capacity);
		}
		if(!maxPrice.equals("Any Price")) {
			sql += "AND room.price <= ?\n";
			bindVariables.add(maxPrice);
		}
		if(!order.equals("Any Order")) sql += "ORDER BY " + order + "\n";
		
		try {
			conn = DriverManager.getConnection(DB, DB_USER, DB_PASSWORD);
	
			ps = conn.prepareStatement(sql);
			ps.setString(1, dateIn);
			ps.setString(2, dateOut);
			ps.setString(3, dateOut);
			ps.setString(4, dateOut);
			ps.setString(5, dateIn);
			ps.setString(6, dateOut);
			System.out.println(ps);
			for(int i = 7; i < bindVariables.size() + 7; i++) {
				ps.setString(i, bindVariables.get(i-7));
			}

			rs = ps.executeQuery();

			System.out.println(ps.toString());
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
	ArrayList<Integer> getRoomIDs()	 {
		Connection conn = null;
		PreparedStatement s = null;
		ResultSet rs = null;
		ArrayList<Integer> listOfRoomIds = new ArrayList<>();

		String sql = "SELECT room_id\n" 
				+ "FROM room\n";
		try {
			conn = DriverManager.getConnection(DB, DB_USER, DB_PASSWORD);
			s = conn.prepareStatement(sql);
			
			rs = s.executeQuery();
			
			while(rs.next()) {
				
				listOfRoomIds.add(rs.getInt("room_id"));	
			}
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		} finally {
			try{if(conn != null) conn.close();} catch (Exception e){}
			try{if(s != null) s.close();} catch (Exception e){}
			try{if(rs != null) rs.close();} catch (Exception e){}
		}
		return listOfRoomIds;
	}
	
	ArrayList<Integer> getHotelIDs()	 {
		Connection conn = null;
		PreparedStatement s = null;
		ResultSet rs = null;
		ArrayList<Integer> listOfHotelIds = new ArrayList<>();

		String sql = "SELECT hotel_id\n" 
				+ "FROM hotel\n";
		try {
			conn = DriverManager.getConnection(DB, DB_USER, DB_PASSWORD);
			s = conn.prepareStatement(sql);
			
			rs = s.executeQuery();
			
			while(rs.next()) {
				
				listOfHotelIds.add(rs.getInt("hotel_id"));	
			}
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		} finally {
			try{if(conn != null) conn.close();} catch (Exception e){}
			try{if(s != null) s.close();} catch (Exception e){}
			try{if(rs != null) rs.close();} catch (Exception e){}
		}
		return listOfHotelIds;
	}
	
	/**
	 * Returns the reservation number
	 * @param roomID
	 * @param hotelID
	 * @param guestID
	 * @param checkIn
	 * @param checkOut
	 * @param cost
	 * @return the reservation number if successful. -1 if unsuccessful
	 */
	int createNewReservation(String roomID, String hotelID, int guestID,String checkIn, String checkOut, double cost) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int reserveID = -1;
		
		String sql = "INSERT INTO reservation(room_id, hotel_id, guest_id, checkindate, checkoutdate, totalcost)\n"
				+ "VALUES(?, ?, ?, ?, ?, ?)";
		try {
			conn = DriverManager.getConnection(DB, DB_USER, DB_PASSWORD);
			ps = conn.prepareStatement(sql, new String[] {"Reserve_id"});
			ps.setString(1,roomID);
			ps.setString(2,hotelID);
			ps.setInt(3,guestID);
			ps.setString(4,checkIn);
			ps.setString(5,checkOut);
			ps.setDouble(6,cost);
			
			ps.executeUpdate();
			
			rs = ps.getGeneratedKeys();
			
			if(rs.next()) {
				reserveID = rs.getInt(1);
			}
			if(reserveID == -1) JOptionPane.showMessageDialog(null, "Error creating reservation");

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		} finally {
			try{if(conn != null) conn.close();} catch (Exception e){}
			try{if(ps != null) ps.close();} catch (Exception e){}
		}
		return reserveID;
	}
	
	void editPriceOfRoom(int roomID, int price) {
		Connection conn = null;
		PreparedStatement ps = null;

		String sql = "UPDATE room\n" 
				+ "SET price = ?\n"
				+ "WHERE room_id = ?\n";
		try {
			conn = DriverManager.getConnection(DB, DB_USER, DB_PASSWORD);
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, price);
			ps.setInt(2, roomID);
			
			ps.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Room: " + roomID + " has been updated.");
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		} finally {
			try{if(conn != null) conn.close();} catch (Exception e){}
			try{if(ps != null) ps.close();} catch (Exception e){}
		}
	}
	
	void editTypeOfRoom(int roomID, String newType) {
		Connection conn = null;
		PreparedStatement ps = null;

		String sql = "UPDATE room\n" 
				+ "SET type = ?\n"
				+ "WHERE room_id = ?\n";
		try {
			conn = DriverManager.getConnection(DB, DB_USER, DB_PASSWORD);
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, newType);
			ps.setInt(2, roomID);
			
			ps.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Room: " + roomID + " has been updated.");
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		} finally {
			try{if(conn != null) conn.close();} catch (Exception e){}
			try{if(ps != null) ps.close();} catch (Exception e){}
		}
	}
	
	
	void mostFrequentGuests() {
		Connection conn = null;
		PreparedStatement s = null;
		ResultSet rs = null;
		
		String sql = "SELECT guest.guest_id, username, COUNT(reservation.guest_id) AS visits\n"
				+ "FROM guest LEFT OUTER JOIN reservation ON guest.guest_id = reservation.guest_id\n"
				+ "WHERE checkoutdate <= CURDATE()\n"
				+ "GROUP BY reservation.guest_id\n"
				+ "ORDER BY visits DESC\n";
		
		try {
			conn = DriverManager.getConnection(DB, DB_USER, DB_PASSWORD);
			s = conn.prepareStatement(sql);
						
			rs = s.executeQuery();
			JTable frequent = new JTable(buildTable(rs));
			JOptionPane.showMessageDialog(null, new JScrollPane(frequent));
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
			System.out.println("Not correctly working");
		} finally {
			try{if(conn != null) conn.close();} catch (Exception e){}
			try{if(s != null) s.close();} catch (Exception e){}
			try{if(rs != null) rs.close();} catch (Exception e){}
		}
	}
	void mostFrequentRooms() {
		Connection conn = null;
		PreparedStatement s = null;
		ResultSet rs = null;
		
		String sql = "SELECT room.Room_id, name AS Hotel, address AS City, COUNT(reservation.reserve_id) AS NumberOfTimesReserved\n"
				+ "FROM (room JOIN hotel ON hotel.hotel_id = room.hotel_id) LEFT OUTER JOIN reservation ON room.room_id = reservation.room_id\n"
				+ "GROUP BY room.room_id\n"
				+ "ORDER BY NumberOfTimesReserved DESC, name, address;\n";
		
		try {
			conn = DriverManager.getConnection(DB, DB_USER, DB_PASSWORD);
			s = conn.prepareStatement(sql);
						
			rs = s.executeQuery();
			JTable frequent = new JTable(buildTable(rs));
			JOptionPane.showMessageDialog(null, new JScrollPane(frequent));
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
			System.out.println("Not correctly working");
		} finally {
			try{if(conn != null) conn.close();} catch (Exception e){}
			try{if(s != null) s.close();} catch (Exception e){}
			try{if(rs != null) rs.close();} catch (Exception e){}
		}
	}
	
	void viewListOfHotels(String orderBy) {
		Connection conn = null;
		PreparedStatement s = null;
		ResultSet rs = null;
		
		String sql = "SELECT *\n"
				+ "FROM hotel\n";
		
		if(!orderBy.equals("Any Order")) sql += "ORDER BY " + orderBy;
		
		try {
			conn = DriverManager.getConnection(DB, DB_USER, DB_PASSWORD);
			s = conn.prepareStatement(sql);
						
			rs = s.executeQuery();
			JTable hotels = new JTable(buildTable(rs));
			JOptionPane.showMessageDialog(null, new JScrollPane(hotels));
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
			System.out.println("Not correctly working");
		} finally {
			try{if(conn != null) conn.close();} catch (Exception e){}
			try{if(s != null) s.close();} catch (Exception e){}
			try{if(rs != null) rs.close();} catch (Exception e){}
		}
	}
	
	public void viewListOfRooms(String orderBy) {
		Connection conn = null;
		PreparedStatement s = null;
		ResultSet rs = null;
		
		String sql = "SELECT *\n"
				+ "FROM room\n";
		
		if(!orderBy.equals("Any Order")) sql += "ORDER BY " + orderBy;
		
		try {
			conn = DriverManager.getConnection(DB, DB_USER, DB_PASSWORD);
			s = conn.prepareStatement(sql);
						
			rs = s.executeQuery();
			JTable hotels = new JTable(buildTable(rs));
			JOptionPane.showMessageDialog(null, new JScrollPane(hotels));
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
			System.out.println("Not correctly working");
		} finally {
			try{if(conn != null) conn.close();} catch (Exception e){}
			try{if(s != null) s.close();} catch (Exception e){}
			try{if(rs != null) rs.close();} catch (Exception e){}
		}
	}
	
	public void bestOfChainHotels() {
		Connection conn = null;
		PreparedStatement s = null;
		ResultSet rs = null;
		
		String sql = "SELECT name, hotel_id, star\n"
				+ "FROM hotel as h1\n"
				+ "WHERE star > \n"
				+ "(\n"
				+ "SELECT AVG(h2.star)\n"
				+ "FROM hotel as h2\n"
				+ "WHERE h1.name = h2.name\n"
				+ ")\n"
				+ "ORDER BY star DESC;\n";
		
		try {
			conn = DriverManager.getConnection(DB, DB_USER, DB_PASSWORD);
			s = conn.prepareStatement(sql);
						
			rs = s.executeQuery();
			JTable hotels = new JTable(buildTable(rs));
			JOptionPane.showMessageDialog(null, new JScrollPane(hotels));
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
			System.out.println("Not correctly working");
		} finally {
			try{if(conn != null) conn.close();} catch (Exception e){}
			try{if(s != null) s.close();} catch (Exception e){}
			try{if(rs != null) rs.close();} catch (Exception e){}
		}
	}

	public void bestOfHotelsInEachCity() {
		Connection conn = null;
		PreparedStatement s = null;
		ResultSet rs = null;
		
		String sql = "SELECT name, hotel_id, address, star\n"
				+ "FROM hotel as h1\n"
				+ "WHERE  star >\n"
				+ "(\n"
				+ "SELECT AVG(h2.star)\n"
				+ "FROM hotel as h2\n"
				+ "WHERE h1.address = h2.address\n"
				+ ")\n"
				+ "ORDER BY address;\n";
		
		try {
			conn = DriverManager.getConnection(DB, DB_USER, DB_PASSWORD);
			s = conn.prepareStatement(sql);
						
			rs = s.executeQuery();
			JTable hotels = new JTable(buildTable(rs));
			JOptionPane.showMessageDialog(null, new JScrollPane(hotels));
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
			System.out.println("Not correctly working");
		} finally {
			try{if(conn != null) conn.close();} catch (Exception e){}
			try{if(s != null) s.close();} catch (Exception e){}
			try{if(rs != null) rs.close();} catch (Exception e){}
		}
	}	
	
	
	void addNewHotel(String name, String city, int star) {
		Connection conn = null;
		PreparedStatement ps = null;

		String sql = "INSERT\n"
				+ "INTO hotel\n"
				+ "VALUES(null,?,?,0,?)";
		try {
			conn = DriverManager.getConnection(DB, DB_USER, DB_PASSWORD);
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, city);
			ps.setInt(3, star);
			ps.executeUpdate();

			JOptionPane.showMessageDialog(null,"New Hotel Created!");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		} finally {
			try{if(conn != null) conn.close();} catch (Exception e){}
			try{if(ps != null) ps.close();} catch (Exception e){}
		}
	}
	
	void deleteHotel(int hotelID) {
		Connection conn = null;
		PreparedStatement ps = null;

		String sql = "DELETE\n"
				+ "FROM hotel\n"
				+ "WhERE hotel_id = ?;";
		try {
			conn = DriverManager.getConnection(DB, DB_USER, DB_PASSWORD);
			ps = conn.prepareStatement(sql);
			ps.setInt(1,hotelID);
			ps.executeUpdate();

			JOptionPane.showMessageDialog(null,"Hotel " + hotelID + " has been deleted.");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		} finally {
			try{if(conn != null) conn.close();} catch (Exception e){}
			try{if(ps != null) ps.close();} catch (Exception e){}
		}

	}
	
	void addNewRoom(int hotelID, String capacity, double price, String type) {
		Connection conn = null;
		PreparedStatement ps = null;

		String sql = "INSERT\n"
				+ "INTO room\n"
				+ "VALUES(null,?,?,?,?)";
		try {
			conn = DriverManager.getConnection(DB, DB_USER, DB_PASSWORD);
			ps = conn.prepareStatement(sql);
			ps.setInt(1, hotelID);
			ps.setString(2, capacity);
			ps.setDouble(3, price);
			ps.setString(4, type);
			ps.executeUpdate();

			JOptionPane.showMessageDialog(null,"New Room Created!");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		} finally {
			try{if(conn != null) conn.close();} catch (Exception e){}
			try{if(ps != null) ps.close();} catch (Exception e){}
		}
	}
	void deleteRoom(int roomID) {
		Connection conn = null;
		PreparedStatement ps = null;

		String sql = "DELETE\n"
				+ "FROM room\n"
				+ "WhERE room_id = ?;";
		try {
			conn = DriverManager.getConnection(DB, DB_USER, DB_PASSWORD);
			ps = conn.prepareStatement(sql);
			ps.setInt(1,roomID);
			ps.executeUpdate();

			JOptionPane.showMessageDialog(null,"Room " + roomID + " has been deleted.");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		} finally {
			try{if(conn != null) conn.close();} catch (Exception e){}
			try{if(ps != null) ps.close();} catch (Exception e){}
		}

	}
	public void archiveReservations(String timestamp) {
		Connection conn = null;
		PreparedStatement ps = null;
		CallableStatement cs;

		String sql = "{CALL Archive_Reservations(?)}";
		try {
			conn = DriverManager.getConnection(DB, DB_USER, DB_PASSWORD);
			cs = conn.prepareCall(sql);
			cs.setString(1, timestamp);
			cs.executeUpdate();
			
			JOptionPane.showMessageDialog(null,"Reservations before " + timestamp + " have been archived.");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		} finally {
			try{if(conn != null) conn.close();} catch (Exception e){}
			try{if(ps != null) ps.close();} catch (Exception e){}
		}
		
	}
	
	/* Code copied from function buildTableModel obtained from http://stackoverflow.com/questions/10620448/most-simple-code-to-populate-jtable-from-resultset */
	private static DefaultTableModel buildTable(ResultSet rs)
	        throws SQLException {

	    ResultSetMetaData metaData = rs.getMetaData();

	    // names of columns
	    Vector<String> columnNames = new Vector<String>();
	    int columnCount = metaData.getColumnCount();
	    for (int column = 1; column <= columnCount; column++) {
	        columnNames.add(metaData.getColumnName(column));
	    }

	    // data of the table
	    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	    while (rs.next()) {
	        Vector<Object> vector = new Vector<Object>();
	        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
	            vector.add(rs.getObject(columnIndex));
	        }
	        data.add(vector);
	    }
	    return new DefaultTableModel(data, columnNames);
	}

	
	

	
}

