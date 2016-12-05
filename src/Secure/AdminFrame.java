package Secure;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

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
		JButton archive = new JButton("<html>Archive</html>");
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

		archive.addActionListener(getArchiveAction());
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

		for(int i = 0; i < 4; i++){
			menu.add(new JPanel());
		}
		menu.add(archive);
		menu.add(new JPanel());
		menu.add(new JPanel());
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

	private ActionListener getArchiveAction() {
		return new ActionListener()	{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JPanel container = new JPanel();
				container.setLayout(new GridLayout(0,2));
				
				JTextField timestamp = new JTextField();
				
				timestamp.setText("YYYY-MM-DD HH:MM:SS");
				
				container.add(new JPanel());
				container.add(new JPanel());
				container.add(new JLabel("Enter Timestamp"));
				container.add(timestamp);
				container.add(new JPanel());
				container.add(new JPanel());
				
				int ok = JOptionPane.showConfirmDialog(null, container, "Archive reservations", JOptionPane.OK_CANCEL_OPTION);

				if(ok == JOptionPane.OK_OPTION){
					sql.archiveReservations(timestamp.getText());
				}
				
				
				 
			}
			
		};
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
				JPanel container = new JPanel();
				container.setLayout(new GridLayout(0,2));
				
				JComboBox<Integer> hotelIDs = new JComboBox<>();
				ArrayList<Integer> listHotelIds = sql.getHotelIDs();
				for(int i = 0; i < listHotelIds.size(); i++) {
					hotelIDs.addItem(listHotelIds.get(i));
				}
				JComboBox<String> capacity = new JComboBox<>();
				capacity.addItem("1-2");
				capacity.addItem("3-4");
				capacity.addItem("5-6");
				capacity.addItem("7-8");
				
				JSpinner price = new JSpinner();
				price.setValue(new Double(100.00));
				
				JTextField type = new JTextField("Type of room");
				
				container.add(new JPanel());
				container.add(new JPanel());
				container.add(new JLabel("Please Enter New Room Information"));
				container.add(new JPanel());
				
				container.add(new JPanel());
				container.add(new JPanel());
				container.add(new JLabel("Hotel Id:"));
				container.add(hotelIDs);
				container.add(new JLabel("Capacity:"));
				container.add(capacity);
				container.add(new JLabel("Price:"));
				container.add(price);
				container.add(new JLabel("Room Type:"));
				container.add(type);
				container.add(new JPanel());
				container.add(new JPanel());
				
				int ok = JOptionPane.showConfirmDialog(null, container, "Add New Room", JOptionPane.OK_CANCEL_OPTION);

				if(ok == JOptionPane.OK_OPTION) {
					sql.addNewRoom(Integer.parseInt(hotelIDs.getSelectedItem().toString()), capacity.getSelectedItem().toString(), Double.parseDouble(price.getValue().toString()), type.getText());
				}
				
			}			
		};
	}
	private ActionListener getAddHotelAction() {
		return new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel container = new JPanel();
				container.setLayout(new GridLayout(0,2));
				
				JTextField hotelName = new JTextField("Enter New Hotel Name");
				JTextField hotelAddress = new JTextField("Enter New Hotel Address");
				
				JComboBox<Integer> ratings = new JComboBox<>();
				ratings.addItem(1);
				ratings.addItem(2);
				ratings.addItem(3);
				ratings.addItem(4);
				ratings.addItem(5);
				
				container.add(new JPanel());
				container.add(new JPanel());
				container.add(new JLabel("Please Enter New Hotel Information"));
				container.add(new JPanel());				
				container.add(new JPanel());
				container.add(new JPanel());
				container.add(new JLabel("Hotel Name:"));
				container.add(hotelName);
				container.add(new JLabel("Hotel Address:"));
				container.add(hotelAddress);
				container.add(new JLabel("Hotel Rating:"));
				container.add(ratings);				
				container.add(new JPanel());
				container.add(new JPanel());
				
				int ok = JOptionPane.showConfirmDialog(null, container, "Add New Room", JOptionPane.OK_CANCEL_OPTION);

				if(ok == JOptionPane.OK_OPTION) {
					sql.addNewHotel(hotelName.getText(), hotelAddress.getText(), Integer.parseInt(ratings.getSelectedItem().toString()));
				}
			}			
		};
	}
	private ActionListener getDeleteRoomAction() {
		return new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				
				JPanel container = new JPanel();
				container.setLayout(new GridLayout(0,2));
				JComboBox<Integer> roomSelections = new JComboBox<>();
				ArrayList<Integer> roomIDs = sql.getRoomIDs();
				for(int i = 0; i < roomIDs.size(); i++) {
					roomSelections.addItem(roomIDs.get(i));
				}
				
				container.add(new JPanel());
				container.add(new JPanel());
				container.add(new JLabel("Select a room to delete"));
				container.add(new JPanel());
				container.add(new JPanel());
				container.add(new JPanel());
				container.add(new JLabel("Room Id: "));
				container.add(roomSelections);
				
				int ok = JOptionPane.showConfirmDialog(null, container, "Delete A Room", JOptionPane.OK_CANCEL_OPTION);

				if(ok == JOptionPane.OK_OPTION) {
					sql.deleteRoom(Integer.parseInt(roomSelections.getSelectedItem().toString()));
				}
				
				
			}			
		};
	}

	private ActionListener getDeleteHotelAction() {
		return new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel container = new JPanel();
				container.setLayout(new GridLayout(0,2));
				JComboBox<Integer> hotelSelections = new JComboBox<>();
				ArrayList<Integer> hotelIDs = sql.getHotelIDs();
				for(int i = 0; i < hotelIDs.size(); i++) {
					hotelSelections.addItem(hotelIDs.get(i));
				}
				
				container.add(new JPanel());
				container.add(new JPanel());
				container.add(new JLabel("Select a hotel to delete"));
				container.add(new JPanel());
				container.add(new JPanel());
				container.add(new JPanel());
				container.add(new JLabel("Hotel Id: "));
				container.add(hotelSelections);
				
				int ok = JOptionPane.showConfirmDialog(null, container, "Delete A Hotel", JOptionPane.OK_CANCEL_OPTION);

				if(ok == JOptionPane.OK_OPTION) {
					sql.deleteHotel(Integer.parseInt(hotelSelections.getSelectedItem().toString()));
				}
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
