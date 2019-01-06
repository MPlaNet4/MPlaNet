package mplanet.remote;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import javax.swing.*;
import java.net.*;
import java.io.*;

public class JPEGPanel extends JTextArea {
    
    protected BufferedImage m_bi;
    Socket sk = null;
    Socket sm = null;
    ObjectOutputStream ok = null;
    ObjectOutputStream om = null;
    
    
    public JPEGPanel(Socket s1, Socket s2) {
        sk = s2;
        sm = s1;
        addListeners();
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        try{
            sk.setSendBufferSize(1);
            sk.setTcpNoDelay(true);
            sk.setTrafficClass(0x10);
            sm.setSendBufferSize(1);
            sm.setTcpNoDelay(true);
            sm.setTrafficClass(0x10);
            
            ok = new ObjectOutputStream(new BufferedOutputStream(sk.getOutputStream()));
            om = new ObjectOutputStream(new BufferedOutputStream(sm.getOutputStream()));
        }catch(Exception e){}
        
        
    }
    
    public void addListeners(){
        
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                //formMousePressed(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                //formMouseClicked(evt);
            }
        });
        
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                //formMousePressed(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        
        
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                formKeyTyped(evt);
            }
            
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
            
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });
    }
    
    
    public void formMouseClicked(java.awt.event.MouseEvent evt){
        try{
            System.out.println("Clicked "+evt.getClickCount()+" times");
            om.flush();
            om.writeObject((Object)evt);
            om.flush();
        }catch(Exception e){
            getParent().remove(this);
        }
    }
    
    private void formKeyTyped(java.awt.event.KeyEvent evt) {
        evt.consume();
        System.out.println("keycodetext "+evt.getKeyText(evt.getKeyCode()));
        try{
            ok.writeObject(evt);
            ok.flush();
        }catch(Exception e){
           
        }
    }
    
    
    private void formKeyPressed(java.awt.event.KeyEvent evt) {
        evt.consume();
        System.out.println("keycodetext "+evt.getKeyText(evt.getKeyCode()));
        try{
            ok.writeObject(evt);
            ok.flush();
        }catch(Exception e){
            
        }
    }
    
    private void formKeyReleased(java.awt.event.KeyEvent evt) {
        evt.consume();
        System.out.println("keycodetext "+evt.getKeyText(evt.getKeyCode()));
        try{
            ok.writeObject(evt);
            ok.flush();
        }catch(Exception e){
            
        }
    }
    
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Dimension dimension = getSize();
        g.setColor(getBackground());
        g.fillRect(0, 0, dimension.width, dimension.height);
        if(m_bi != null)
        {
            g.drawImage(m_bi, 0, 0, this);
        }
    }
    
    
    public void setBufferedImage(BufferedImage bufferedimage) {
        System.out.println("printing");	
        m_bi = bufferedimage;
        Dimension dimension = new Dimension(m_bi.getWidth(this), m_bi.getHeight(this));
        setPreferredSize(dimension);
        revalidate();
        repaint();
        return;
    }
}
