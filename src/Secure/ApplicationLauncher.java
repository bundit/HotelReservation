package Secure;
public class ApplicationLauncher {

	public static void main(String[] args) {
		/* Set the Nimbus look and feel */
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

        /* Create and display the application */
        MySqlConnection sqlcon = new MySqlConnection(); //create the model and pass it to the view/controller
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GuestLogin(sqlcon).setVisible(true);
            }
        });
	}
}
