package Secure;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * CreateAccount GUI for creating a new guest account.
 * 
 *
 */
public class CreateAccount extends JFrame{
	private MySqlConnection mysql;
	private JButton submit;
	private JLabel user;
	private JLabel password;
	private JLabel email;
	private JPasswordField txtpassword;
	private JTextField txtusername;
	private JTextField txtemail;
	private GuestLogin gl;
	
	/**
	 * Constructor for account creation page
	 * @param conn the 'model' for backend operations
	 */
	public CreateAccount(MySqlConnection conn,GuestLogin gl) {
		this.gl = gl;
		this.mysql = conn;
		
		initComponents();
		this.setVisible(true);
	}
	private void initComponents() {
		this.setSize(500, 300);
    	this.setLocationRelativeTo(null);
    	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    	this.setLayout(new GridLayout(0,4));
    	
        user = new JLabel("Name");
        password = new JLabel("Password");
        email = new JLabel("Email");
        txtusername = new JTextField();
        txtpassword = new JPasswordField();
        txtemail = new JTextField();
        submit = new JButton("Submit");

        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	String uName = txtusername.getText();
            	String uPass = new String(txtpassword.getPassword());
            	String uEmail = txtemail.getText();
            
            	if(uName.isEmpty() || uPass.isEmpty() || uEmail.isEmpty()) {
            		JOptionPane.showMessageDialog(null, "Name, email, or password cannot be empty");
            		return;
            	}
            	
            	boolean valid = mysql.loginGuest(uEmail, uPass);
            	if(!valid) {
            		mysql.addNewGuest(uName, uPass, uEmail);
            		CreateAccount.this.gl.setVisible(true);
                    CreateAccount.this.dispose();
            	} else {
            		JOptionPane.showMessageDialog(null, "User email already exists, try a different one.");
            		CreateAccount.this.txtemail.setText("");
            		return;
            	}
                
          
            }
        });        
        
        for(int i = 0; i < 9; i++){
        	this.add(new JPanel());
        }
        this.add(user);
        this.add(txtusername);
        for(int i = 0; i < 6; i++){
        	this.add(new JPanel());
        }
        this.add(password);
        this.add(txtpassword);
        for(int i = 0; i < 6; i++){
        	this.add(new JPanel());
        }
        this.add(email);
        this.add(txtemail);
        for(int i = 0; i < 7; i++){
        	this.add(new JPanel());
        }
        this.add(submit);
        for(int i = 0; i < 8; i++){
        	this.add(new JPanel());
        }
	}
}
