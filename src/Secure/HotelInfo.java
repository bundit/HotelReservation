
package Secure;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Roya, Bundit, Zur
 */
public class HotelInfo extends javax.swing.JFrame {
	private static final long serialVersionUID = 1L;
	private MySqlConnection sql;
	private JPanel resultsContainer;

	/**
	 * Creates new form HotelInfo
	 */
	public HotelInfo(MySqlConnection mysql) {
		this.sql = mysql;
		initComponents();
		setVisible(false);
	}    
	private void initComponents() {
		this.setSize(1000,800);
		this.setLocationRelativeTo(null);
		this.resultsContainer = new JPanel();
		this.resultsContainer.setLayout(new GridLayout(0,7));

		JPanel titles = new JPanel();
		titles.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 25)); 
		resultsContainer.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		titles.setLayout(new GridLayout(0,7));
		titles.add(new JLabel("<html><u><b><font size=\"+1\">Name</font></b></u></html>"));
		titles.add(new JLabel("<html><u><b><font size=\"+1\">Address</font></b></u></html>"));
		titles.add(new JLabel("<html><u><b><font size=\"+1\">Type</font></b></u></html>"));
		titles.add(new JLabel("<html><u><b><font size=\"+1\">Stars #</font></b></u></html>"));
		titles.add(new JLabel("<html><u><b><font size=\"+1\">Capacity</font></b></u></html>"));
		titles.add(new JLabel("<html><u><b><font size=\"+1\">Cost</font></b></u></html>"));
		titles.add(new JLabel());

		this.add(titles, BorderLayout.NORTH);
		this.add(new JScrollPane(resultsContainer), BorderLayout.CENTER);
	}
	/**
	 * 
	 * @param dateIn
	 * @param dateOut
	 * @param hotelName
	 * @param locations
	 * @param roomType
	 * @param minimumStars
	 * @param capacity
	 * @param minimumPrice
	 * @param order
	 */
	void showHotel(String dateIn, String dateOut, String hotelName, String locations, String roomType, 
			String minimumStars, String capacity, String minimumPrice, String order){

		ArrayList<String> rs = sql.getHotelInfoForGuest(dateIn, dateOut, hotelName, locations, roomType, minimumStars, capacity, minimumPrice, order);
		int guestID = sql.getGuest_Id();
		for(int i = 0; i < rs.size(); i = i + 8) {

			String hotel = rs.get(i);
			String city = rs.get(i+1);
			String type = rs.get(i+2);
			String stars = rs.get(i+3);
			String size = rs.get(i+4);
			String price = rs.get(i+5);
			String roomID = rs.get(i+6);
			String hotelID = rs.get(i+7);

			resultsContainer.add(new JLabel(hotel));
			resultsContainer.add(new JLabel(city));
			resultsContainer.add(new JLabel(type));
			resultsContainer.add(new JLabel(stars));
			resultsContainer.add(new JLabel(size));
			resultsContainer.add(new JLabel(price + ""));

			JButton btnReserve = new JButton("Reserve this room");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd");
			LocalDate before = LocalDate.parse(dateIn, formatter);
			LocalDate after = LocalDate.parse(dateOut, formatter);
			int days = (int)ChronoUnit.DAYS.between(before, after);
			btnReserve.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					int result = JOptionPane.showConfirmDialog((Component) null, "Are you sure you want to reserve this room?",
							"Confirmation", JOptionPane.YES_NO_OPTION);
					if(result == JOptionPane.YES_OPTION) {
						int reserveID = HotelInfo.this.sql.createNewReservation(roomID, hotelID, guestID, dateIn, dateOut, days * Double.parseDouble(price));
						JOptionPane.showConfirmDialog((Component) null, "Your reservation number is: " + reserveID + ". Please keep this in your records.",
								"Reservation Confirmed!", JOptionPane.OK_CANCEL_OPTION);
					} else {
						return;
					}
				}    				
			});
			resultsContainer.add(btnReserve);
		}
		setVisible(true);
	}
}