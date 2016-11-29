/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Secure;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.*;
//import net.proteanit.sql.DbUtils;



/**
 *
 * @author Roya
 */
public class HotelInfo extends javax.swing.JFrame {
    private MySqlConnection sql;
    private JPanel resultsContainer;
    
    //private javax.swing.JTable hotel_table;
    private javax.swing.JScrollPane jScrollPane1;
    /**
     * Creates new form HotelInfo
     */
    public HotelInfo(MySqlConnection mysql) {
    	this.sql = mysql;
        initComponents();
        setVisible(false);
    }
    
    private void showHotel(){
        try{
            String sql = "Select * from hotel";
            //pst = conn.prepareStatement(sql);
            //rs = pst.executeQuery();
            
            //hotel_table.setModel(DbUtils.resultSetToTableModel(rs));
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        
        for(int i = 0; i < 100; i++	){ //currently just to display how info is structured
    		resultsContainer.add(new JLabel("Hotel " + i));
    		resultsContainer.add(new JLabel("Address " + i));
    		resultsContainer.add(new JLabel("Stars " + i));
    		resultsContainer.add(new JLabel("Room # " + i));
    		resultsContainer.add(new JLabel("Available " + i));
    		resultsContainer.add(new JLabel("Type " + i));
    		resultsContainer.add(new JButton("Reserve this room"));
    	}
        
        setVisible(true);
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
    	this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
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
    	titles.add(new JLabel("<html><u><b><font size=\"+1\">Stars</font></b></u></html>"));
    	titles.add(new JLabel("<html><u><b><font size=\"+1\">Room #</font></b></u></html>"));
    	titles.add(new JLabel("<html><u><b><font size=\"+1\">Availability</font></b></u></html>"));
    	titles.add(new JLabel("<html><u><b><font size=\"+1\">Type</font></b></u></html>"));
    	titles.add(new JLabel());
    	
    	showHotel(); //current used just for display, later remove and showhotel will be called when query inputs from user are given
    	
    	this.add(titles, BorderLayout.NORTH);
    	this.add(new JScrollPane(resultsContainer), BorderLayout.CENTER);
    	/*
        jScrollPane1 = new javax.swing.JScrollPane();
        hotel_table = new javax.swing.JTable();

        hotel_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(hotel_table);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(114, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(73, 73, 73))
        );

        pack();
        */
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
            java.util.logging.Logger.getLogger(HotelInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HotelInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HotelInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HotelInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HotelInfo(new MySqlConnection()).setVisible(true);
            }
        });
    }
}
