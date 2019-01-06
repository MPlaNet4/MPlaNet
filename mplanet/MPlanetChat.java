package mplanet;

import javax.swing.*;
import java.net.*;
import java.io.*;

public class MPlanetChat implements Runnable{
    
    Thread t = null;
    
    public MPlanetChat(MPlanetInfo i, MPlanetGUI g) {
        gui = g;
        info = i;
        t = new Thread(this,"chat");
        try{
            chatSocket = new DatagramSocket(info.chatPort);
        } catch(Exception e){}
    }
    
    public void run() {
        byte buf[] = new byte[2048];
        DatagramPacket dp = new DatagramPacket(buf,2048);
        while(true){
            String temp = null;
            try{
                chatSocket.receive(dp);
                temp = getUserName(dp.getAddress().getHostAddress());
            }catch(Exception e){
            }
            String str = new String(dp.getData(),0,dp.getLength());
            gui.chatHistory.setText(gui.chatHistory.getText()+"\n"+temp+" says:    "+str);
        }
    }
    
    public void sendMessage(String m,String n){
        String ip = getUserIP(n);
        try{
            InetAddress i = InetAddress.getByName(ip);
            chatSocket.send(new DatagramPacket(m.getBytes(),m.length(),i,info.chatPort));
        }catch(Exception e){}
    }
    
    public void multicastMessage(String m){
        try{
            for(int i=0;i<info.hosts.size();i++){
                String ip = ((MPlanetHost)info.hosts.elementAt(i)).ip;
                InetAddress iadd = InetAddress.getByName(ip);
                chatSocket.send(new DatagramPacket(m.getBytes(),m.length(),iadd,info.chatPort));
            }
        }catch(Exception e){}
    }
    
    
    public String getUserIP(String n){
        for(int i=0;i<info.hosts.size();i++){
            if(((MPlanetHost)info.hosts.elementAt(i)).name.equals(n)){
                return ((MPlanetHost)info.hosts.elementAt(i)).ip;
            }
        }
        return null;
    }
    
    public String getUserName(String ip){
        for(int i=0;i<info.hosts.size();i++){
            if(((MPlanetHost)info.hosts.elementAt(i)).ip.equals(ip)){
                return ((MPlanetHost)info.hosts.elementAt(i)).name;
            }
        }
        return null;
    }
    
    
    public MPlanetGUI gui;
    public MPlanetInfo info;
    public DatagramSocket chatSocket;
}
