/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Secure;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author Roya
 */
public class GuestLogin extends JFrame {
    private MySqlConnection database;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JButton btnNewGuest;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPasswordField txtpassword;
    private javax.swing.JTextField txtusername;

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
    	
        jLabel1 = new JLabel("Username");
        jLabel2 = new JLabel("Password");
        txtusername = new javax.swing.JTextField();
        txtpassword = new javax.swing.JPasswordField();
        btnSubmit = new JButton("Submit");
        btnNewGuest = new JButton("New Guest");

        btnSubmit.addActionListener(getBtnSubmitAction());
        btnNewGuest.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new CreateAccount(database, GuestLogin.this);
			}
        	
        });
        
        for(int i = 0; i < 9; i++){
        	this.add(new JPanel());
        }
        this.add(jLabel1);
        this.add(txtusername);
        for(int i = 0; i < 6; i++){
        	this.add(new JPanel());
        }
        this.add(jLabel2);
        this.add(txtpassword);
        for(int i = 0; i < 6; i++){
        	this.add(new JPanel());
        }
        this.add(btnSubmit);
        this.add(btnNewGuest);
        
        for(int i = 0; i < 8; i++){
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
				boolean valid = database.loginGuest(txtusername.getText(), new String(txtpassword.getPassword()));
		    	if(valid) {
		    		JOptionPane.showMessageDialog(null,"Welcome user");
		    		
		            GuestLogin.this.setVisible(false);
		            new MainFrame(database).setVisible(true);
		    	} else {
		    		JOptionPane.showMessageDialog(null,"invalid username or password","Access Denied",JOptionPane.ERROR_MESSAGE);
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

        /* Create and display the form */
        MySqlConnection sqlcon = new MySqlConnection(); //create the model and pass it to the view/controller
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GuestLogin(sqlcon).setVisible(true);
            }
        });
    }
}
