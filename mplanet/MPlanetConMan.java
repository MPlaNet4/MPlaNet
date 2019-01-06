/*
 * MPlanetConMan.java
 *
 * Created on January 1, 1998, 1:40 AM
 */

package mplanet;

import java.net.*;
import java.util.*;
import mplanet.*;
import mplanet.sounds.*;


public class MPlanetConMan implements Runnable {
    
    public Thread t = null;
    
    public MPlanetConMan(MPlanetInfo i,MPlanetGUI g) {
        info = i;
        gui = g;
        mcPort = info.mcPort;
        reqPort = info.reqPort;
        mcGroup = info.mcGroup;
        t = new Thread(this,"Connection Manager");
    }
    
    public MPlanetConMan(){
    }
    
    public void run() {
        // code to go online starts here
        try{
            mcGroupAdd = InetAddress.getByName(info.mcGroup);
            ms = new MulticastSocket(info.mcPort);
            String mcOn = "ON"+" "+info.user.name;
            byte onBuf[] = new byte[mcOn.length()];
            onBuf = mcOn.getBytes();
            DatagramPacket dp = new DatagramPacket(onBuf, onBuf.length, mcGroupAdd, info.mcPort);
            ms.send(dp);
            System.out.println("Sent ON frame");
        }catch(Exception e) {
			if(info.isSoundOn){
                new PlaySound("mplanet/sounds/connotconnect.mp3");
            }
            new MPError("Cannot connect to the MPlaNet network");
            System.exit(1);
        }
        // code to go online ends here
        
        // join the multicast group
        try{
            ms.joinGroup(mcGroupAdd);
            System.out.println("joined the m/c group");
        }catch(Exception e){
			if(info.isSoundOn){
                new PlaySound("mplanet/sounds/connotconnect.mp3");
            }
            new MPError("Cannot to connect to the MPlaNet network");
        }
        
        //code to start the pinger(ping thread)
        
        //new MplanetPinger(user,hosts,gui).start();
        
        // code to receive new connections and ack starts here
        byte recBuf[];
        
        while(true) {
            // receive any kind of packet from any client
            recBuf = new byte[512];
            DatagramPacket dp = new DatagramPacket(recBuf, recBuf.length);
            try{
                ms.receive(dp);
                System.out.println("received packet from:");
            }catch(Exception e) {
                System.out.println("Cannot receive multicast message");
                continue;
            }
            //copy the received packet to tempBuf
            byte tempBuf[] = new byte[recBuf.length];
            for(int i=0;i<recBuf.length;i++)
                tempBuf[i] = recBuf[i];
            
            // if received decompose it to get IP, type etc.
            
            // find the sender's IP using the datagram
            String senderIP = dp.getAddress().getHostAddress();
            System.out.println(senderIP);
            
            // find the type, name using the contents of the received packet
            String tempStr = new String(tempBuf).trim();
            StringTokenizer dcType = new StringTokenizer(tempStr," ");
            String mesType = dcType.nextToken();
            
            System.out.println("Type"+mesType);
            String name = dcType.nextToken();
            System.out.println("From"+name);
            
            
            // handle the message suitably
            if( mesType.equals("ON")) {
                //send acknowledge
                System.out.println("This is :"+info.user.name+"  "+name);
                try{
                    t.sleep(200);
                }catch(Exception e){}
                String ackString = "ACK"+" "+info.user.name;
                byte temp[] = new byte[ackString.length()];
                temp = ackString.getBytes();
                DatagramPacket ack = new DatagramPacket(temp,temp.length,mcGroupAdd,mcPort);
                try{
                    ms.send(ack);
                }catch(Exception e){
                    System.out.println("Cannot acknowledge ON message sent");
                }
                //check and add the user to the list
                
                try{
                    System.out.println(senderIP+" "+InetAddress.getLocalHost().getHostAddress());
                    if(!senderIP.equals(InetAddress.getLocalHost().getHostAddress())) {
                        boolean newHostFlag = true;
                        for(int i=0;i<info.hosts.size();i++) {
                            if(((MPlanetHost)info.hosts.elementAt(i)).ip.equals(senderIP)) {
                                newHostFlag = false;
                                break;
                            }
                        }
                        if(newHostFlag) {
                            MPlanetHost h = new MPlanetHost(reqPort,senderIP,name);
                            System.out.println("ConMan: "+name);
                            info.hosts.add(h);
                            gui.refresh();
                        }
                    }
                }catch(Exception e){}
            }
            
            else if(mesType.equals("ACK") && !info.user.name.equals(name)) {
                //if( not in the list ) { append } else { ignore }
                
                try{
                    System.out.println(senderIP+" "+InetAddress.getLocalHost().getHostAddress());
                    if(!senderIP.equals(InetAddress.getLocalHost().getHostAddress())) {
                        
                        boolean newHostFlag = true;
                        for(int i=0;i<info.hosts.size();i++) {
                            if(((MPlanetHost)info.hosts.elementAt(i)).ip.equals(senderIP)) {
                                newHostFlag = false;
                                break;
                            }
                        }
                        if(newHostFlag) {
                            MPlanetHost h = new MPlanetHost(reqPort,senderIP,name);
                            info.hosts.add(h);
                            gui.refresh();
                        }
                    }
                }catch(Exception e){}
            }
            
            else if(mesType.equals("OFF")) {
                //remove user from the list
                for(int i=0;i<info.hosts.size();i++) {
                    if(((MPlanetHost)info.hosts.elementAt(i)).ip.equals(senderIP)) {
                        info.hosts.removeElementAt(i);
                        //MPlanetHost h = (MPlanetHost)hosts.get(i);                        
                        gui.refresh();
                        break;
                    }
                }
            }
        }// end of infinite while() loop
    }
    
    
    
    public int mcPort = 0;
    public int reqPort = 0;
    public String mcGroup = null;
    
    public InetAddress mcGroupAdd = null;
    public MulticastSocket ms = null;
    
    public MPlanetGUI gui;
    public MPlanetInfo info;
}
