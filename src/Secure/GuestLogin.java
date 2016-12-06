package Secure;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * The JFrame that contains login UI 
 * @author Bundit, Roya, zur
 *
 */
public class GuestLogin extends JFrame {
	private static final long serialVersionUID = 1L;
	private MySqlConnection database;
	private JButton btnSubmit;
	private JButton btnNewGuest;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JPasswordField txtpassword;
	private JTextField txtusername;
	@SuppressWarnings("unused")
	private JCheckBox ckboxAdmin;
	private boolean adminChecked;

	/**
	 * Creates new form GuestLogin
	 * @param conn the 'model' reference 
	 */
	public GuestLogin(MySqlConnection conn) {
		this.database = conn;
		initComponents();
	}

	/**
	 * Initialize the components of this JFrame
	 */
	private void initComponents() {
		this.setSize(500, 300);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(0,4));

		jLabel1 = new JLabel("User Email");
		jLabel2 = new JLabel("Password");
		txtusername = new JTextField();
		txtpassword = new JPasswordField();
		JCheckBox ckboxAdmin = new JCheckBox("Admin login");
		btnSubmit = new JButton("Submit");
		btnNewGuest = new JButton("New Guest");
		adminChecked = false;

		ckboxAdmin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(adminChecked) adminChecked = false;
				else
					adminChecked = true;
			}

		});
		btnSubmit.addActionListener(getBtnSubmitAction());
		btnNewGuest.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new CreateAccount(database, GuestLogin.this);
				GuestLogin.this.setVisible(false);
			}
		});

		for(int i = 0; i < 9; i++){ //whitespace
			this.add(new JPanel());
		}


		this.add(jLabel1);
		this.add(txtusername);
		for(int i = 0; i < 6; i++){ //whitespace
			this.add(new JPanel());
		}
		this.add(jLabel2);
		this.add(txtpassword);
		for(int i = 0; i < 3; i++){ //whitespace
			this.add(new JPanel());
		}

		this.add(ckboxAdmin);

		for(int i = 0; i < 6; i++){ //whitespace

			this.add(new JPanel());
		}
		this.add(btnSubmit);
		this.add(btnNewGuest);

		for(int i = 0; i < 8; i++){ //whitespace
			this.add(new JPanel());
		}

	}

	/**
	 * The action performed by the submit button. If input is valid then logs the user or admin in
	 * @param evt
	 */
	private ActionListener getBtnSubmitAction(){
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(adminChecked) {
					boolean valid = database.loginAdmin(txtusername.getText(),new String(txtpassword.getPassword()));
					if(valid) {
						JOptionPane.showMessageDialog(null,"Welcome admin");
						GuestLogin.this.dispose();
						new AdminFrame(database).setVisible(true);
					} else {
						JOptionPane.showMessageDialog(null,"Invalid admin username or password","Access Denied",JOptionPane.ERROR_MESSAGE);
					}
				} else {
					boolean valid = database.loginGuest(txtusername.getText(), new String(txtpassword.getPassword()));
					if(valid) {
						JOptionPane.showMessageDialog(null,"Welcome user");
						GuestLogin.this.dispose();
						new MainFrame(database).setVisible(true);
					} else {
						JOptionPane.showMessageDialog(null,"Invalid guest username or password","Access Denied",JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		};
	}
}
