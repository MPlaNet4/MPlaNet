/*
 * MPInfo.java
 *
 * Created on May 13, 2003, 6:45 PM
 */

package mplanet;

import javax.swing.*;
/**
 *
 * @author  Pradhan
 */
public class MPMessage {
    
    /** Creates a new instance of MPInfo */
    public MPMessage(String mess) {
        new JOptionPane().showMessageDialog(null,mess,"MPlaNet Information",JOptionPane.INFORMATION_MESSAGE);
    }
}
