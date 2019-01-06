package mplanet;

import javax.swing.*;
import java.util.*;

public class MPHostIcon extends JToggleButton{
    
    /** Creates a new instance of MPHostIcon */
    public MPHostIcon(){}
    
    public MPHostIcon(String text,MPlanetInfo i,MPlanetHost u) {
        setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        //setBackground(new java.awt.Color(255, 255, 255));
        //setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        setIcon(new javax.swing.ImageIcon(getClass().getResource("/mplanet/images/dull.gif")));
        System.out.println("hosticon: "+text);
        setText(text);
        //setToolTipText("IP address Should go here");
        
        host = u;
        info = i;
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setIcon(new javax.swing.ImageIcon(getClass().getResource("/mplanet/images/dull.gif")));
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setIcon(new javax.swing.ImageIcon(getClass().getResource("/mplanet/images/host.gif")));
            }
        });
      
    }
    
   

    MPlanetInfo info;
    MPlanetHost host;

}



