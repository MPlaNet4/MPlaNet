package mplanet.remote;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.net.*;
import java.io.*;
import java.awt.event.*;

public class ClientKeyManager implements Runnable {
    Thread t = null;
    Socket sk = null;
    Robot r = null;
    
    public ClientKeyManager(Socket s)	{
        t = new Thread(this,"ClientKeyManager");
        sk=s;
        try{
            sk.setReceiveBufferSize(1);//sk.getReceiveBufferSize()/4);
            sk.setTcpNoDelay(true);
            sk.setTrafficClass(0x10);
            System.out.println("Size "+sk.getReceiveBufferSize());
        }catch(Exception e){
            
        }
    }
    
    public void run(){
        System.out.println("runnin key manager");        
        try{
            r = new Robot();
        }catch(Exception e){}
        ObjectInputStream is = null;
        try{
            is = new ObjectInputStream(new BufferedInputStream(sk.getInputStream()));
        }catch(Exception e){
            
        }
        KeyEvent evt = null;
        while(true){
            try{
                evt = (KeyEvent) is.readObject();
                System.out.println("Key Pressed: "+evt.getKeyText(evt.getKeyCode()));
                typeTheKey(evt);
            }catch(Exception e){
                System.out.println("Client key: cannot read ");
                e.printStackTrace();
                break;
            }
        }        
    }
    
    
    
    private void typeTheKey(java.awt.event.KeyEvent e) {
        //getID() returns if the event is keypressed keyreleased or keytyped
        try{
            if(e.getID() == KeyEvent.KEY_PRESSED){
                System.out.println("pressed");
                r.keyPress(e.getKeyCode());
            }
        }catch(Exception e1){
            System.out.println("pressed problem");
        }
        
        try{
            if(e.getID() == KeyEvent.KEY_RELEASED){
                System.out.println("released");
                r.keyRelease(e.getKeyCode());
            }
        }catch(Exception e2){
            System.out.println("released problem");
        }
        
    }
}

