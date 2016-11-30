package Secure;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MainFrame extends JFrame{
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

		JPanel title = new JPanel(new GridLayout(0,2));
		title.add(new JLabel("<html><font size=\"+3\"><b>Main Menu\t</b></font></html>"),BorderLayout.WEST);
		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
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
				JComboBox<String> hotels = new JComboBox<>();
				hotels.addItem("All Hotels");
				hotels.addItem("Hilton");
				
				JComboBox<String> cities = new JComboBox<>();
				cities.addItem("All Cities");
				cities.addItem("San Francisco");
				cities.addItem("San Jose");
				cities.addItem("Santa Cruz");
				
				JComboBox<String> types = new JComboBox<>();
				types.addItem("Any Type");
				types.addItem("Standard");
				types.addItem("Suite");
				
				JComboBox<Object> ratings = new JComboBox<>();
				ratings.addItem("Any Rating");
				ratings.addItem(1);
				ratings.addItem(2);
				ratings.addItem(3);
				ratings.addItem(4);
				ratings.addItem(5);
				
				JComboBox<Object> capacity = new JComboBox<>();
				capacity.addItem("Any Capacity");
				capacity.addItem("1-2");
				capacity.addItem("3-4");
				capacity.addItem("5-6");
				
				JComboBox<Object> prices = new JComboBox<>();
				prices.addItem("Any Price");
				prices.addItem("50");
				prices.addItem("100");
				prices.addItem("150");
				prices.addItem("200");
				
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
				info.add(new JLabel("Minimum Price"));
				info.add(prices);
				info.add(new JPanel());
				info.add(new JPanel());
				
				JOptionPane.showConfirmDialog(null, info, "Find Hotel Rooms", JOptionPane.OK_CANCEL_OPTION);
				//this methods needs to get the input from user then show the results in a new instance of HotelInfo
				//get info from comboboxes
				
				//do stuff
				
				new HotelInfo(sql).showHotel();
			}
		};
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
				
				//input length must be less than 11 digits
				if(uInput.length() > 10) {
					JOptionPane.showMessageDialog(null, "Reservation Number does not exist");
					actionPerformed(e);
					return;
				}
				
				//check user input
				if(uInput == null || uInput.isEmpty()) {
					return;
				} else {
					for (int i = 0; i < uInput.length(); i++) {
						if (!Character.isDigit(uInput.charAt(i))){
							JOptionPane.showMessageDialog(null, "Please enter digits only");
							actionPerformed(e);
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
				} else {
					for (int i = 0; i < uInput.length(); i++) {
						if (!Character.isDigit(uInput.charAt(i))){
							JOptionPane.showMessageDialog(null, "Please enter digits only");
							actionPerformed(e);
							return;
						}
					}
				}

				MainFrame.this.sql.viewReservation(Integer.parseInt(uInput));
			}
		};
	}


	public static void main(String[] args){
		new MainFrame(new MySqlConnection()).setVisible(true);;
	}
}
