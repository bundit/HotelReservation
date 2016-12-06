package Secure;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


import javax.swing.*;

public class MainFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	private MySqlConnection sql;

	public MainFrame(MySqlConnection sql) {
		this.sql = sql;

		initComponents();
	}

	private void initComponents() {
		this.setSize(500, 500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		JPanel mainPanel = new JPanel();

		JPanel title = new JPanel();
		title.add(new JLabel("<html><font size=\"+3\"><b>Main Menu\t</b></font></html>"),BorderLayout.WEST);
		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new GuestLogin(MainFrame.this.sql).setVisible(true);;
				MainFrame.this.dispose();
			}
		});
		title.add(btnLogout, BorderLayout.EAST);
		mainPanel.add(title, BorderLayout.NORTH);
		JPanel menu = new JPanel(new GridLayout(0,5));
		menu.setPreferredSize(new Dimension(500, 400));

		//buttons
		JButton btnSearch = new JButton("<html>Search for rooms</html>");
		JButton btnCancel = new JButton("<html>Cancel a reservation</html>");
		JButton btnView = new JButton("<html>View existing reservation</html>");
		btnSearch.addActionListener(getSearchAction());
		btnCancel.addActionListener(getCancelAction());
		btnView.addActionListener(getViewAction());

		for(int i = 0; i < 6; i++){
			menu.add(new JPanel());
		}
		menu.add(btnSearch);
		menu.add(new JPanel());
		menu.add(btnView);
		for(int i = 0; i < 7; i++){
			menu.add(new JPanel());
		}
		menu.add(btnCancel);
		for(int i = 0; i < 5; i++){
			menu.add(new JPanel());
		}

		mainPanel.add(menu);
		this.add(mainPanel);
		this.setVisible(true);
	}
	
	/**
	 * Gets the cancel button action 
	 * Cancels a reservation based on the reservation number 
	 * @return the cancel button action for its action listener
	 */
	private ActionListener getCancelAction(){
		return new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String uInput = JOptionPane.showInputDialog(null,"Input your reservation number to cancel");
				
				if(uInput == null || uInput.isEmpty()) {
					return;
				}
				//input length must be less than 11 digits
				if(uInput.length() > 10) {
					JOptionPane.showMessageDialog(null, "Reservation Number does not exist");
					actionPerformed(e);
					return;
				} else { //check user input
					for (int i = 0; i < uInput.length(); i++) {
						if (!Character.isDigit(uInput.charAt(i))){
							JOptionPane.showMessageDialog(null, "Please enter digits only");
							//actionPerformed(e);
							return;
						}
					}
				}

				MainFrame.this.sql.cancelReservation(Integer.parseInt(uInput));
			}
		};
	}
	/**
	 * Gets the view button action listener
	 * Shows the user a view of the reservation by the reservation number inputted
	 * @return the view button action for its action listener
	 */
	private ActionListener getViewAction(){
		return new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String uInput = JOptionPane.showInputDialog("Input your reservation number to view");

				//check user input
				if(uInput == null || uInput.isEmpty()) {
					return;
				} 
				
				if(uInput.length() > 10) {
					JOptionPane.showMessageDialog(null, "Reservation Number does not exist");
					actionPerformed(e);
					return;
				} else {
					for (int i = 0; i < uInput.length(); i++) {
						if (!Character.isDigit(uInput.charAt(i))){
							JOptionPane.showMessageDialog(null, "Please enter digits only");
							//actionPerformed(e);
							return;
						}
					}
				}

				MainFrame.this.sql.viewReservation(Integer.parseInt(uInput));
			}
		};
	}
	
	/**
	 * Action for search JButton
	 * Gives a form for the user to input fields to search for desired hotel rooms
	 * @return the action to set the actionlistener on search button
	 */
	private ActionListener getSearchAction(){
		return new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel info = new JPanel();
				info.setLayout(new GridLayout(0,2));
				
				ArrayList<String> hotelNames = sql.getListOfDetails("hotel", "name");
				JComboBox<String> hotels = new JComboBox<>();
				hotels.addItem("All Hotels");
				for(int i = 0; i < hotelNames.size(); i++) {
					hotels.addItem(hotelNames.get(i));
				}
				
				ArrayList<String> cityNames = sql.getListOfDetails("hotel","address");
				JComboBox<String> cities = new JComboBox<>();
				cities.addItem("All Cities");
				for(int i = 0; i < cityNames.size(); i++) {
					cities.addItem(cityNames.get(i));
				}
				
				ArrayList<String> typesList = sql.getListOfDetails("room", "type");
				JComboBox<String> types = new JComboBox<>();
				types.addItem("Any Type");
				for(int i = 0; i < typesList.size(); i++) {
					types.addItem(typesList.get(i));
				}
				
				JComboBox<String> ratings = new JComboBox<>();
				ratings.addItem("Any Rating");
				for(int i = 1; i < 6; i++) {
					ratings.addItem(i + "");
				}

				JComboBox<String> capacity = new JComboBox<>();
				capacity.addItem("Any Capacity");
				capacity.addItem("1-2");
				capacity.addItem("3-4");
				capacity.addItem("5-6");
				
				JComboBox<String> prices = new JComboBox<>();
				prices.addItem("Any Price");
				prices.addItem("50");
				prices.addItem("100");
				prices.addItem("150");
				prices.addItem("200");
				
				JComboBox<String> orderBy = new JComboBox<>();
				orderBy.addItem("Any Order");
				orderBy.addItem("Name");
				orderBy.addItem("Address");
				orderBy.addItem("Type");
				orderBy.addItem("Star");
				orderBy.addItem("Capacity");
				orderBy.addItem("Price");
				
				info.add(new JPanel());
				info.add(new JPanel());
				info.add(new JLabel("Check In"));
				SpinnerDateModel spinModelIn = new SpinnerDateModel();
			    JSpinner dayin = new JSpinner(spinModelIn);
				dayin.setEditor(new JSpinner.DateEditor(dayin,"dd/MM/yyyy"));
				info.add(dayin);
				info.add(new JPanel());
				info.add(new JPanel());
				info.add(new JLabel("Check Out"));
				SpinnerDateModel spinModelOut = new SpinnerDateModel();
			    JSpinner dayout = new JSpinner(spinModelOut);
				dayout.setEditor(new JSpinner.DateEditor(dayout,"dd/MM/yyyy"));
				info.add(dayout);
				info.add(new JPanel());
				info.add(new JPanel());
				info.add(new JLabel("Hotels"));
				info.add(hotels);
				info.add(new JPanel());
				info.add(new JPanel());
				info.add(new JLabel("Locations"));
				info.add(cities);
				info.add(new JPanel());
				info.add(new JPanel());
				info.add(new JLabel("Room Type"));
				info.add(types);
				info.add(new JPanel());
				info.add(new JPanel());
				info.add(new JLabel("Minimum Rating"));
				info.add(ratings);
				info.add(new JPanel());
				info.add(new JPanel());
				info.add(new JLabel("Capacity"));
				info.add(capacity);
				info.add(new JPanel());
				info.add(new JPanel());
				info.add(new JLabel("Maximum Price"));
				info.add(prices);
				info.add(new JPanel());
				info.add(new JPanel());
				info.add(new JLabel("Sort By"));
				info.add(orderBy);
				info.add(new JPanel());
				info.add(new JPanel());
				
				JOptionPane.showConfirmDialog(null, info, "Find Hotel Rooms", JOptionPane.OK_CANCEL_OPTION);
				
				 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				 
				 String dIn = dateFormat.format(dayin.getValue());
				 String dOut = dateFormat.format(dayout.getValue());
				 
				 String hotelStr = hotels.getSelectedItem().toString();
				 String cityStr = cities.getSelectedItem().toString();
				 String typeStr = types.getSelectedItem().toString();
				 String ratingStr = ratings.getSelectedItem().toString();
				 String capacityStr = capacity.getSelectedItem().toString();
				 String priceStr = prices.getSelectedItem().toString();
				 String order = orderBy.getSelectedItem().toString();
				
				new HotelInfo(sql).showHotel(dIn, dOut, hotelStr, cityStr, typeStr, ratingStr, capacityStr, priceStr, order);
			}
		};
	}
}
