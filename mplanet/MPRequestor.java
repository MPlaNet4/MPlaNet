package mplanet;

import java.io.*;
import java.net.*;
import java.awt.*;
import javax.swing.*;
import mplanet.remote.*;
import mplanet.explorer.*;
import mplanet.sounds.*;

public class MPRequestor extends MPHostIcon implements Runnable {
    
    Thread t = null;
    /** Creates a new instance of MPRequestor */
    
    public MPRequestor(MPlanetHost h, MPlanetInfo i, String rs) {
        host = h;
        info = i;
        reqString = rs;
        t = new Thread(this,"Requestor");
    }
    
    public void run(){
        if(reqString.equals("rem")){
            if(!(info.state.remAssOn && info.state.filShrOn)){
                Socket reqSocket = null;
                try{
                    reqSocket = new Socket(host.ip,info.reqPort);
                    System.out.println(host.ip);
                    is = new ObjectInputStream(new BufferedInputStream(reqSocket.getInputStream()));
                    os = new ObjectOutputStream(new BufferedOutputStream(reqSocket.getOutputStream()));
                    os.flush();
                    os.writeObject("rem");
                    os.flush();
                    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
                    os.writeObject(d);
                    os.flush();
                }catch(Exception e){
					if(info.isSoundOn){
					   new PlaySound("mplanet/sounds/onlyoneservice.mp3");
		            }
                    new MPError("Only one service at a time");
                }
                String respString = null;
                try{
                    respString = (String)is.readObject();
                    reqSocket.close();
                }catch(Exception e){
                    new MPError("Cannot get response");
                }
                
                if(respString.equals("yes")){
                    new MPMessage("Request for Remote Assistance has been Accepted");
                    try{
                        t.sleep(1000);
                    }catch(Exception e){}
                    screenClient = new ScreenClient(host.ip,info.imagePort,info.mousePort,info.keyPort);
                    screenClient.t.start();
                    info.state.remAssOn = true;
                    //info.remAssButton.setText("stop remass");
                    info.remAssButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mplanet/images/busyremote.gif")));
                    while(true){
                        if(!screenClient.t.isAlive()){
                            info.state.remAssOn = false;
                            //info.remAssButton.setText("remass");
                            info.remAssButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mplanet/images/remote.gif")));
                            break;
                        }
                    }
                }
                else if(respString.equals("nores")){
                    Dimension dim = null;
                    try{
                        dim = (Dimension)is.readObject();
                    }catch(Exception e){}
                    JOptionPane.showMessageDialog(null,"Change your resolution to "+dim.width+"X"+dim.height);
                }
                else{
					if(info.isSoundOn){
					   new PlaySound("mplanet/sounds/reqturneddown.mp3");
		            }
                    new MPError("Your request has been turned down");
                }
            }
        }
        else if(reqString.equals("fs")){
            System.out.println("requesting...");
            if(!(info.state.remAssOn && info.state.filShrOn)){
                Socket reqSocket = null;
                try{
                    System.out.println("requestor	"+host.ip+"   "+info.reqPort);
                    reqSocket = new Socket(host.ip,info.reqPort);
                    System.out.println(host.ip);
                    is = new ObjectInputStream(new BufferedInputStream(reqSocket.getInputStream()));
                    os = new ObjectOutputStream(new BufferedOutputStream(reqSocket.getOutputStream()));
                    os.flush();
                    os.writeObject("fs");
                    os.flush();
                }catch(Exception e){
					if(info.isSoundOn){
					   new PlaySound("mplanet/sounds/onlyoneservice.mp3");
		            }
                    new MPError("Only one service at a time");
                }
                String respString = null;
                try{
                    respString = (String)is.readObject();
                    reqSocket.close();
                }catch(Exception e){
                    new MPError("Cannot get response");
                }
                
                if(respString.equals("yes")){
                    new MPMessage("Request for File Sharing has been Accepted");
                    /*
                    try{
                        t.sleep(3000);///////////////////// CHECK THIS CODE
                    }catch(Exception e){}
                     */
                    explorer = new Explorer(host.ip,info.explorerPort,info.searchPort,info.isSoundOn);
                    info.state.filShrOn = true;
                    //info.fileSharButton.setText("stop filesharing");
                    info.fileSharButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mplanet/images/busyfileshare.gif")));
                    while(true){
                        if(explorer == null ){
                            new MPMessage("explorer == null");
                            info.state.filShrOn = false;
                            //info.fileSharButton.setText("file sharing");
                            info.fileSharButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mplanet/images/busyfileshare.gif")));
                            break;
                        }
                    }
                }
                else{
					if(info.isSoundOn){
					   new PlaySound("mplanet/sounds/reqturneddown.mp3");
		            }
                    new MPError("Your request has been turned down");
                    
                }
            }
        }
    }
    public Explorer explorer = null;
    public ScreenClient screenClient = null;
    private MPlanetHost host = null;
    private String reqString = null;
    public MPlanetInfo info;
    private ObjectOutputStream os = null;
    private ObjectInputStream is = null;
}
