package mplanet;

import java.net.*;
import java.io.*;

import mplanet.remote.*;
import mplanet.explorer.*;
import mplanet.sounds.*;

public class MPReqProcessor implements Runnable {
    
    Thread t = null;
    /** Creates a new instance of MPReqProcessor */
    public MPReqProcessor(String rs,MPlanetInfo i) {
        reqString = rs;
        info = i;
        t = new Thread(this,"ReqProcessor");
    }
    
    public void run(){
        if(reqString.equals("rem")){
            System.out.println("starting rem assis....");
            screenServer = new ScreenServer(info.imagePort,info.mousePort,info.keyPort,info.isSoundOn);
            screenServer.t.start();
            while(true){
                if(!screenServer.t.isAlive()){
                    info.state.remAssOn = false;
                    try{
                        screenServer.rs.setVisible(false);
                        screenServer.rs.dispose();
                        screenServer.runThread = false;                        
                    }catch(Exception e){}
                    //info.remAssButton.setText("remass");
                    info.remAssButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mplanet/images/remote.gif")));
                    break;
                }
            }
        }
        else if(reqString.equals("fs")){
            System.out.println("starting expdriver....");
            expdriver = new ExpDriver(info.explorerPort,info.searchPort);
            expdriver.t.start();
            System.out.println("Started driver.........");
            while(true){
                if(!expdriver.t.isAlive()){
                    info.state.filShrOn = false;
                    //info.fileSharButton.setText("file sharing");
                    info.fileSharButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mplanet/images/fileshare.gif")));
                    break;
                }
            }
        }
    }
    
    private String reqString = null;
    private Socket reqSocket = null;
    private ObjectOutputStream os = null;
    private ObjectInputStream is = null;
    public  ScreenServer screenServer = null;
    public  ExpDriver expdriver = null;
    public  MPlanetInfo info = null;
}
