package Secure;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class AdminFrame extends JFrame	{
	private static final long serialVersionUID = 1L;
	MySqlConnection sql;
	public AdminFrame(MySqlConnection sql) {
		this.sql = sql;

		initComponents();
	}

	private void initComponents() {
		this.setSize(500, 500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		JPanel mainPanel = new JPanel();

		JPanel title = new JPanel();
		title.add(new JLabel("<html><font size=\"+3\"><b>Admin Menu\t</b></font></html>"),BorderLayout.WEST);
		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				AdminFrame.this.dispose();
				new GuestLogin(AdminFrame.this.sql).setVisible(true);
			}
		});
		title.add(btnLogout, BorderLayout.EAST);
		mainPanel.add(title, BorderLayout.NORTH);
		JPanel menu = new JPanel(new GridLayout(0,6));
		menu.setPreferredSize(new Dimension(500, 400));

		//buttons
		JButton editPrice = new JButton("<html>Edit price of a room</html>");
		JButton editType = new JButton("<html>Edit type of a room</html>");
		JButton newHotel = new JButton("<html>Add a new hotel</html>");
		JButton deleteHotel = new JButton("<html>Delete a hotel</html>");
		JButton newRoom = new JButton("<html>Add a new room</html>");
		JButton deleteRoom = new JButton("<html>Delete a room</html>");
		JButton viewHotels = new JButton("<html>View hotels</html>");
		JButton viewRooms = new JButton("<html>View rooms</html>");
		JButton topGuests = new JButton("<html>View guest visit counts</html>");
		JButton topRooms = new JButton("<html>View frequented rooms</html>");
		JButton bestHotelsInCity = new JButton("<html>Above avg rated hotels per city</html>");
		JButton bestOfChainHotels = new JButton("<html>Best of chain hotels</html>");

		editPrice.addActionListener(getEditPriceAction());
		editType.addActionListener(getEditTypeAction());
		newHotel.addActionListener(getAddHotelAction());
		deleteHotel.addActionListener(getDeleteHotelAction());
		newRoom.addActionListener(getAddRoomAction());
		deleteRoom.addActionListener(getDeleteRoomAction());
		viewHotels.addActionListener(getViewHotelsAction());
		viewRooms.addActionListener(getViewRoomsAction());
		topGuests.addActionListener(getFrequentGuests());
		topRooms.addActionListener(getTopRoomsAction());
		bestHotelsInCity.addActionListener(getBestHotelsAction());
		bestOfChainHotels.addActionListener(getBestChainHotelsAction());

		for(int i = 0; i < 7; i++){
			menu.add(new JPanel());
		}
		menu.add(viewHotels);
		menu.add(viewRooms);
		menu.add(editPrice);
		menu.add(editType);

		menu.add(new JPanel());
		menu.add(new JPanel());

		menu.add(newHotel);
		menu.add(deleteHotel);
		menu.add(newRoom);
		menu.add(deleteRoom);

		menu.add(new JPanel());
		menu.add(new JPanel());

		menu.add(topGuests);
		menu.add(topRooms);
		menu.add(bestHotelsInCity);
		menu.add(bestOfChainHotels);

		for(int i = 0; i < 7; i++){
			menu.add(new JPanel());
		}

		mainPanel.add(menu);
		this.add(mainPanel);
		this.setVisible(true);
	}

	private ActionListener getEditPriceAction() {
		return new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel container = new JPanel();
				JSpinner prices = new JSpinner();
				prices.setValue(new Integer(100));
				JComboBox<Integer> roomIds = new JComboBox<>();
				ArrayList<Integer> rooms = sql.getRoomIDs();
				for(int i = 0; i < rooms.size(); i++) {
					roomIds.addItem(rooms.get(i));
				}

				container.add(new JLabel("Room Id"));
				container.add(roomIds);
				container.add(new JLabel("New Price"));
				container.add(prices);

				int ok = JOptionPane.showConfirmDialog(null, container, "Edit room", JOptionPane.OK_CANCEL_OPTION);

				if(ok == JOptionPane.OK_OPTION){
					sql.editPriceOfRoom((int)roomIds.getSelectedItem(), (int)prices.getValue());
				}
			}			
		};
	}
	private ActionListener getEditTypeAction() {
		return new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel container = new JPanel();
				JComboBox<Integer> roomIds = new JComboBox<>();
				JTextField newType = new JTextField("Enter New Type");
				ArrayList<Integer> rooms = sql.getRoomIDs();
				for(int i = 0; i < rooms.size(); i++) {
					roomIds.addItem(rooms.get(i));
				}

				container.add(new JLabel("Room Id"));
				container.add(roomIds);
				container.add(new JLabel("New Type"));
				container.add(newType);

				int ok = JOptionPane.showConfirmDialog(null, container, "Edit room", JOptionPane.OK_CANCEL_OPTION);

				if(ok == JOptionPane.OK_OPTION) {
					sql.editTypeOfRoom((int)roomIds.getSelectedItem(), newType.getText());
				}

			}			
		};
	}
	private ActionListener getAddRoomAction() {
		return new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {

			}			
		};
	}
	private ActionListener getAddHotelAction() {
		return new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {

			}			
		};
	}
	private ActionListener getDeleteRoomAction() {
		return new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}			
		};
	}

	private ActionListener getDeleteHotelAction() {
		return new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {

			}			
		};
	}

	private ActionListener getViewHotelsAction() {
		return new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel container = new JPanel();
				container.setLayout(new GridLayout(0,1));
				JComboBox<String> hotelAttributes = new JComboBox<>();
				hotelAttributes.addItem("Any Order");	
				hotelAttributes.addItem("Name");	
				hotelAttributes.addItem("Address");	
				hotelAttributes.addItem("Rooms");	
				hotelAttributes.addItem("Star");	

				container.add(new JLabel("Default sorted by hotel id"));

				container.add(new JPanel());
				container.add(new JLabel("Sort By"));
				container.add(hotelAttributes);
				container.add(new JPanel());
				JOptionPane.showMessageDialog(null, container);

				sql.viewListOfHotels(hotelAttributes.getSelectedItem().toString());
			}			
		};
	}

	private ActionListener getViewRoomsAction() {
		return new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel container = new JPanel();
				container.setLayout(new GridLayout(0,1));
				JComboBox<String> roomAttributes = new JComboBox<>();
				roomAttributes.addItem("Any Order");	
				roomAttributes.addItem("Hotel_id");	
				roomAttributes.addItem("Capacity");	
				roomAttributes.addItem("Price");	
				roomAttributes.addItem("Type");	

				container.add(new JLabel("Default sorted by room id"));

				container.add(new JPanel());
				container.add(new JLabel("Sort By"));
				container.add(roomAttributes);
				container.add(new JPanel());
				JOptionPane.showMessageDialog(null, container);

				sql.viewListOfRooms(roomAttributes.getSelectedItem().toString());
			}			
		};
	}


	private ActionListener getFrequentGuests() {
		return new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				sql.mostFrequentGuests();
			}

		};
	}
	private ActionListener getTopRoomsAction() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				sql.mostFrequentRooms();
			}
		};
	}



	private ActionListener getBestChainHotelsAction() {
		return new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				sql.bestOfChainHotels();
			}

		};
	}

	private ActionListener getBestHotelsAction() {
		return new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				sql.bestOfHotelsInEachCity();
			}

		};
	}

	public static void main(String... args) {
		MySqlConnection sqlcon = new MySqlConnection(); //create the model and pass it to the view/controller
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new AdminFrame(sqlcon).initComponents();
			}
		});
	}
}
