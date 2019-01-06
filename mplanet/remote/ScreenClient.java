package mplanet.remote;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.net.*;
import java.io.*;
import com.sun.image.codec.jpeg.*;
import java.awt.Graphics2D;



public class ScreenClient implements Runnable {
    
    public Thread t = null;
    String ip = null;
    public boolean runThread = true;
    int imagePort = 0;
    int mousePort = 0;
    int keyPort = 0;
    
    public ScreenClient(String i,int imp,int mp,int kp)	{
        imagePort = imp;
        mousePort = mp;
        keyPort = kp;
        ip = i;
        System.out.println(keyPort);
        t = new Thread(this,"Client");
    }
    
    public static void main(String args[]) {
        ScreenClient c = new ScreenClient("127.0.0.1",53555,54555,55555);
        c.t.start();
    }
    
    public void run(){
        Robot r = null;
        try{
            r = new Robot();
        }catch(Exception e){}
        
        //this socket is for sending images
        Socket si = null;
        try{
            t.sleep(100);
            si = new Socket(ip,imagePort);
        }catch(Exception e){}
        
        //this socket is for MouseManager
        Socket sm = null;
        try{
            t.sleep(100);
            sm = new Socket(ip,mousePort);
            sm.setTcpNoDelay(true);
        }catch(Exception e){}
        ClientMouseManager m = new ClientMouseManager(sm);
        //m.t.setPriority(Thread.MAX_PRIORITY);
        m.t.start();
        
        //this socket is for KeyManager
        Socket sk = null;
        try{
            t.sleep(1000);
            sk = new Socket(ip,keyPort);
            sm.setTcpNoDelay(true);
        }catch(Exception e){}
        ClientKeyManager k = new ClientKeyManager(sk);
        k.t.start();
        ObjectOutputStream o = null;
        JPEGImageEncoder encoder = null;
        try	{
            o = new ObjectOutputStream(new BufferedOutputStream(si.getOutputStream()));
            o.flush();
            System.out.println("sent frame");
            encoder = JPEGCodec.createJPEGEncoder(o);
        }catch(Exception e){
            System.out.println("Cannot create stream or encoder");
            //System.exit(1);
            try{
                t.join(10);
            }catch(Exception ex){}
        }
        
        BufferedImage i = null;
        //int failCount=0;
	Dimension ss=java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        while(runThread){
            i = r.createScreenCapture(new Rectangle(ss));
            try	{
                o.reset();
                encoder.encode(i);
            }	catch(Exception e){
				System.out.println("Client image: cannot read ");
                runThread = false;
	    	/*failCount++;
			try{Thread.sleep(2000);}catch(Exception ex){}
	       	ss=java.awt.Toolkit.getDefaultToolkit().getScreenSize();
                System.out.println("Client image: cannot read ");
                if(failCount==5){
			runThread = false;
			}*/
            }
        }//while
        try{
            si.close();
            System.out.println("Client: closed image socket");
        }catch(Exception e){
            System.out.println("Client: cannot close image socket");
        }
        try{
            sk.close();
            System.out.println("Client: closed keyb socket");
        }catch(Exception e){
            System.out.println("Client: cannot close keybr socket");
        }
        try{
            sm.close();
            System.out.println("Client: closed mouse socket");
        }catch(Exception e){
            System.out.println("Client: cannot close mouse socket");
        }
    }
}

