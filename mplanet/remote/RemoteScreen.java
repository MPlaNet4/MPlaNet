/*
 * RemoteScreen.java
 *
 * Created on May 27, 2003, 11:38 PM
 */

package mplanet.remote;

/**
 *
 * @author  Pradhan
 */
import java.net.*;
import java.io.*;
import mplanet.sounds.*;

public class RemoteScreen extends javax.swing.JFrame {
    
   
    public RemoteScreen(Socket s1, Socket s2,boolean sound) {
		isSoundOn = sound;
        jpgPanel=new JPEGPanel(s1, s2);
        initComponents();
        setMid();
        setVisible(true);
    }
       
    
   
    private void initComponents() {
        screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jButton1.setText("jButton1");
        //jScrollPane1.setCorner(javax.swing.JScrollPane.LOWER_RIGHT_CORNER, jButton1);
        jScrollPane1.setViewportView(jpgPanel);
        
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pack();
    }
    
  
    
    private void exitForm(java.awt.event.WindowEvent evt) {
		if(isSoundOn){
			new PlaySound("mplanet/sounds/usemainwindow.mp3");
	    }
        new MPError("Please use the stop button on the main MPlaNet window");
    }
    
    private void dofullScreen(java.awt.event.ActionEvent evt){
        fullScreen();
      }
    
    public void fullScreen(){
        if(isFull){
            end();
            setMid();
            setUndecorated(false);
            isFull=false;
            setVisible(true);
        }
        else{
            end();
            setUndecorated(true);
            
            setBounds(0, 0, screenSize.width, screenSize.height);
            isFull=true;
            setVisible(true);
        }
        
        
    }
    
    /*
    public void remScrlBar(java.awt.event.MouseEvent evt){
        if(evt.getClickCount()==1)
            fullScreen();
        else if(evt.getClickCount()==2)
        {
            jScrollPane1.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_NEVER);
            jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            fullScreen();
        }
        
    }
    */
     public void setMid(){
        setSize(new java.awt.Dimension(600, 400));
        setLocation((screenSize.width-600)/2,(screenSize.height-400)/2);
    }
     
     public void end(){
        setVisible(false);
        dispose();
    }
    
    public static void main(String args[]) {
        new RemoteScreen(new Socket(), new Socket(),false).show();
    }
    
    private java.awt.Dimension screenSize;
    boolean isFull=false;
	boolean isSoundOn = false;
    public JPEGPanel jpgPanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    
    
}
