package mplanet.remote;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.io.*;
import java.net.*;
import com.sun.image.codec.jpeg.*;

public class ScreenServer  implements Runnable{
    
    public Thread t = null;
    static ScreenServer s = null;
    public boolean runThread = true;
    public RemoteScreen rs = null;
	public boolean isSoundOn = false;
    
    int imagePort = 0;
    int mousePort = 0;
    int keyPort = 0;
    public ScreenServer(int imp,int mp,int kp, boolean sound){
        t = new Thread(this,"ScreenServer");
		isSoundOn = sound;
        imagePort = imp;
        mousePort = mp;
        keyPort = kp;
    }
    public void run() {
        
        ServerSocket sr = null;
        try	{
            sr = new ServerSocket(imagePort);
        }catch(Exception e){
            System.out.println("server image :cannot create listener ");
        }
        
        
        //this socket is for receiving images
        Socket si = null;
        try{
            System.out.println("server : Waiting");
            si = sr.accept();
            System.out.println("server : Found Client");
        }catch(Exception e){
            System.out.println("server image :cannot create socket ");
        }
        try{
            //si.setReceiveBufferSize(1);//sk.getReceiveBufferSize()/4);
            si.setTcpNoDelay(true);
            //si.setTrafficClass(0x10);
        }catch(Exception e){}
        
        try{
            sr.close();
        }catch(Exception e){
        }
        
        
        //this socket is for MouseManager
        Socket sm = null;
        try{
            sr=new ServerSocket(mousePort);
            sm = sr.accept();
            sm.setTcpNoDelay(true);
            System.out.println("server : Found ClientMouseManager");
        }catch(Exception e){
            System.out.println("server mouse :cannot create listener or socket");
        }
        
        try{
            sr.close();
        }catch(Exception e){
        }
        
        //this socket is for KeyManager
        Socket sk = null;
        sr = null;
        try{
            sr=new ServerSocket(keyPort);
        }catch(Exception e){
            System.out.println("server key :cannot create listener");
        }
        try{
            sk = sr.accept();
        }catch(Exception e){
            System.out.println("server key :cannot create socket");
        }
        try{
            sk.setTcpNoDelay(true);
            System.out.println("server : Found ClientKeyManager");
        }catch(Exception e){
            System.out.println("server key :cannot create listener or socket");
        }
        
        try{
            sr.close();
        }catch(Exception e){
        }
        
        JPEGImageDecoder decoder = null;
        ObjectInputStream is = null;
        try {
            is = new ObjectInputStream(new BufferedInputStream(si.getInputStream()));
            decoder = JPEGCodec.createJPEGDecoder(is);
        }catch(Exception e){
            
        }
        
        
        rs = new RemoteScreen(sm, sk ,isSoundOn);
        BufferedImage i = null;
        
        //int failCount=0;
        
        while(runThread){
            try	{
                System.out.println("reading");
                i = decoder.decodeAsBufferedImage();
                System.out.println("trying to draw");
                rs.jpgPanel.setBufferedImage(i);
				System.out.println("drew");
            }	catch(Exception e){
				System.out.println("RA Exiting Server...");
				runThread=false;
                /*failCount++;
                try{Thread.sleep(2000);}catch(Exception ex){}
                if(failCount==5){
                    runThread = false;
                }*/
            }
        }
        
        try{
            si.close();
            System.out.println("Server: closed image socket");
        }catch(Exception e){
            System.out.println("Server: cannot close image socket");
        }
        try{
            sk.close();
            System.out.println("Server: closed keyb socket");
        }catch(Exception e){
            System.out.println("Server: cannot close keybr socket");
        }
        try{
            sm.close();
            System.out.println("Server: closed mouse socket");
        }catch(Exception e){
            System.out.println("Server: cannot close mouse socket");
        }
        
    }
    
    public static void main(String args[]) {
        new ScreenServer(53555,54555,55555,false).t.start();
    }
}
