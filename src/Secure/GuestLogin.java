package Secure;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Roya
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
    private JCheckBox ckboxAdmin;
    private boolean check;

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
        check = false;

        ckboxAdmin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(check) check = false;
				else
					check = true;
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
     * The action performed by the submit button. If input is valid then 
     * @param evt
     */
    private ActionListener getBtnSubmitAction(){
    	return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(check) {
					boolean valid = database.loginAdmin(txtusername.getText(),new String(txtpassword.getPassword()));
					if(valid) {
						JOptionPane.showMessageDialog(null,"Welcome admin");
			    		GuestLogin.this.dispose();
			    		
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
 
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GuestLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GuestLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GuestLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GuestLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display sthe form */
        MySqlConnection sqlcon = new MySqlConnection(); //create the model and pass it to the view/controller
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GuestLogin(sqlcon).setVisible(true);
            }
        });
    }
}
