package mplanet.remote;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.net.*;
import java.io.*;
import java.awt.event.*;


public class ClientMouseManager implements Runnable {
    Thread t = null;
    Socket sm = null;
    
    
    public ClientMouseManager(Socket s)	{
        t = new Thread(this,"ClientMouseManager");
        sm=s;
        try{
            sm.setReceiveBufferSize(1);
            sm.setTcpNoDelay(true);
            sm.setTrafficClass(0x10);
        }catch(Exception e){
            System.out.println("Cannot set buffer");
        }
    }
    
    public void run(){
        System.out.println("Running client mouse manager");
        Robot r = null;
        try{
            r = new Robot();
        }catch(Exception e){
        }
        
        ObjectInputStream is = null;
        try{
            is = new ObjectInputStream(new BufferedInputStream(sm.getInputStream()));
        }catch(Exception e){}
        
        MouseEvent evt = null;
        
        while(true){
            try{
                evt = (MouseEvent) is.readObject();
            }catch(Exception e){
                System.out.println("Client mouse: cannot read ");
                break;
            }
            if ( evt.getID() == MouseEvent.MOUSE_CLICKED){
                if(SwingUtilities.isLeftMouseButton(evt)){  //if left click
                    
                    if(evt.getClickCount() == 1) {
                        r.mouseMove(evt.getX(),evt.getY());
                        r.mousePress(InputEvent.BUTTON1_MASK);
                        r.mouseRelease(InputEvent.BUTTON1_MASK);
                    }
                    if(evt.getClickCount() > 1) {
                        r.mouseMove(evt.getX(),evt.getY());
                        r.mousePress(InputEvent.BUTTON1_MASK);
                        r.mouseRelease(InputEvent.BUTTON1_MASK);
                        r.mousePress(InputEvent.BUTTON1_MASK);
                        r.mouseRelease(InputEvent.BUTTON1_MASK);
                    }
                }//end of left click
                
                if(SwingUtilities.isRightMouseButton(evt)){ // if right click
                    r.mouseMove(evt.getX(),evt.getY());
                    r.mousePress(InputEvent.BUTTON3_MASK);
                    r.mouseRelease(InputEvent.BUTTON3_MASK);
                }//end of right click
            }
        }
    }
}

