package mplanet.explorer;

import java.util.*;
import java.io.*;
import java.net.*;
import java.awt.*;
import javax.swing.filechooser.*;
import javax.swing.table.*;
import javax.swing.*;


public class SearchThread implements Runnable {
    
    public SearchThread(String ip,int p,JTextField jt, JTable jtble, DefaultTableModel dt) {
        searchPort = p;
        t = new Thread(this,"search");
        
        try{
            Thread.sleep(1000);
            s = new Socket(ip,searchPort);
        }catch(Exception e){
            System.out.println("Search : cannot create search socket");
        }
        System.out.println("Search : Connection established...");
        searchField=jt;
        jTable2=jtble;
        tableModel=dt;
        System.out.println("starting SEARCH thread");
    }
    
    
    public void run() {
        initTheTable();
        try{
            is = new ObjectInputStream(new BufferedInputStream(s.getInputStream()));
            os = new ObjectOutputStream(new BufferedOutputStream(s.getOutputStream()));
            os.flush();
        }catch(Exception e){}
        System.out.println("running SEARCH thread");
        String searchString = searchField.getText();
        System.out.println("Entered: "+searchField.getText());
        if(searchString == null || searchString.equals(""))
            return;
        try {
            os.writeObject(searchString);
            os.flush();
            System.out.println("Sent string "+searchField.getText());            
        }catch(Exception e){
            
        }
        
        while(runThread){
            try{
                String name = (String)is.readObject();
                String path = (String)is.readObject();
                System.out.println("recieved a match: ");
                if(name.equals("-1") && path.equals("-1"))
                    break;
                System.out.println(name+" "+path);
                Object objArr[] = {name,path};
                tableModel.addRow(objArr);
            } catch (Exception e) {
                System.out.println("Exception in searchThread"+e);
                break;
            }
        }
        try{
            s.close();
        }catch(Exception e){}
    }
    
    public void initTheTable(){
        tableModel = new DefaultTableModel();
        tableModel.setColumnCount(2);
        Object arr[] = {"Name","Path"};
        tableModel.setColumnIdentifiers(arr);
        jTable2.setModel(tableModel);
    }
    
    private ObjectOutputStream os = null;
    private ObjectInputStream is = null;
    JTextField searchField = null;
    JTable jTable2 = null;
    public DefaultTableModel tableModel =null;
    public boolean runThread = true;
    public int searchPort = 0;
    public Socket s = null;
    public Thread t = null;
}
