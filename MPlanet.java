import mplanet.*;
import javax.swing.*;
import java.util.*;
import java.net.*;
import mpthemes.*;

public class MPlanet {
    public MPlanet() {
        
    }
    
    public static void main(String args[]) {
        //start mplanet

        new MPlanet();
/*
        javax.swing.JFrame.setDefaultLookAndFeelDecorated(true);
        java.awt.Toolkit.getDefaultToolkit().setDynamicLayout(true);
        System.setProperty("sun.awt.noerasebackground","true");
*/
        //create a blank state info
        info = new MPlanetInfo();
        
        //start the gui
        gui = new MPlanetGUI(info);
        gui.setTitle("MPlaNet");
       
    }
    
    static public MPlanetGUI gui;
    static public MPlanetInfo info;
}
