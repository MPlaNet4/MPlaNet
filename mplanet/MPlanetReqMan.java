package mplanet;

import java.net.*;
import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import mplanet.*;


public class MPlanetReqMan implements Runnable {
    
    public Thread t = null;
    
    public MPlanetReqMan(MPlanetInfo i,MPlanetGUI g){
        info = i;
        gui = g;
        t = new Thread(this,"Request Manager");
    }
    
    public void run(){
        Socket reqPacket = null;
        try{
            server = new ServerSocket(info.reqPort);
            System.out.println("creeatd request manager");
        }catch(Exception e){
            System.out.println("Unable to start Request Manager");
            System.exit(1);
        }
        while(true){
            System.out.println("reqman: waiting...");
            try{
                reqSocket = server.accept();
            }catch(Exception e){}
            
            //get the request
            String reqString = null;
            try{
                os = new ObjectOutputStream(new BufferedOutputStream(reqSocket.getOutputStream()));
                os.flush();
                is = new ObjectInputStream(new BufferedInputStream(reqSocket.getInputStream()));
                reqString = (String)is.readObject();
            }catch(Exception e){}
            
            //check if the request can be serviced
            
            //if remote assistance is in progress do not allow any type of service
            if(reqString.equals("rem")){
                Dimension d = null;
                try{
                    d = (Dimension)is.readObject();
                }catch(Exception e){}
                if(info.state.remAssOn || info.state.filShrOn){
                    try{
                        os.writeObject("no");
                        os.flush();
                        reqSocket.close();
                    }catch(Exception e){}
                    System.out.println("Cannot be serviced right now");
                }
                else{
                    try{
                        
                        InetAddress ip = reqSocket.getInetAddress();
                        String user = getUserName(ip.getHostAddress());
                        int resp = JOptionPane.showConfirmDialog(null,user+" is reqesting Remote Assistance","MPlaNet Information",JOptionPane.YES_NO_OPTION);
                        if(resp == JOptionPane.OK_OPTION){
                            if(checkResolution(d)){
                                os.writeObject("yes");
                                os.flush();
                                reqSocket.close();
                                info.state.remAssOn = true;
                                //info.remAssButton.setText("stop remass");
                                info.remAssButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mplanet/images/busyremote.gif")));
                                remAssProcessor = new MPReqProcessor(reqString,info);
                                remAssProcessor.t.start();
                            }
                            else{
                                os.writeObject("nores");
                                os.flush();
                                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                                os.writeObject(dim);
                                os.flush();
                            }
                        }
                        else{
                            os.writeObject("no");
                            os.flush();
                            reqSocket.close();
                        }
                        
                    }catch(Exception e){}
                }
            }
            else if(reqString.equals("fs")){
                if(info.state.remAssOn || info.state.filShrOn){
                    try{
                        os.writeObject("no");
                        os.flush();
                        reqSocket.close();
                    }catch(Exception e){}
                    System.out.println("Cannot be serviced right now");
                }
                else{
                    String user = null;
                    try{
                        InetAddress ip = reqSocket.getInetAddress();
                        user = getUserName(ip.getHostAddress());
                    }catch(Exception e){}
                    int resp = JOptionPane.showConfirmDialog(null,user+" wants to browse through your filesystem ","MPlaNet Information",JOptionPane.YES_NO_OPTION);
                    if(resp == JOptionPane.OK_OPTION){
                        System.out.println("Sending yes signal for explorer...");
                        try{
                            os.writeObject("yes");
                            os.flush();
                            reqSocket.close();
                        }catch(Exception e){
                            System.out.println("cannot Send yes signal for explorer..");
                        }
                        info.state.filShrOn = true;
                        //info.fileSharButton.setText("stop filesharing");
                        info.fileSharButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mplanet/images/busyfileshare.gif")));
                        System.out.println("starting expdriver...");
                        filShrProcessor = new MPReqProcessor(reqString,info);
                        filShrProcessor.t.start();
                    }
                    else{
                        try{
                            os.writeObject("no");
                            os.flush();
                            reqSocket.close();
                        }catch(Exception e){}
                    }
                    
                }
            }
        }
    }
    
    
    String getUserName(String ip){
        System.out.println("getUserName: "+ip);
        for(int i=0;i<info.hosts.size();i++){
            String userIp = ((MPlanetHost)info.hosts.elementAt(i)).ip;
            System.out.println("userip: "+userIp);
            if(userIp.equals(ip)){
                System.out.println("match");
                return ((MPlanetHost)info.hosts.elementAt(i)).name;
            }
        }
        return null;
    }
    
    boolean checkResolution(Dimension d){
        /*
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        if(d.getHeight()==dim.getHeight() && d.getWidth() == dim.getWidth())
           */ return true;
        /*else
            return false;*/
    }
    
    public MPlanetInfo info = null;
    private ServerSocket server = null;
    private Socket reqSocket = null;
    public MPlanetGUI gui = null;
    private ObjectOutputStream os = null;
    private ObjectInputStream is = null;
    public  MPReqProcessor remAssProcessor = null;
    public  MPReqProcessor filShrProcessor = null;
}

