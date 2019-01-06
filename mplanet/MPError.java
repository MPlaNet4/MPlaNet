/*
 * MPError.java
 *
 * Created on November 8, 2002, 3:44 PM
 */

package mplanet;

import javax.swing.*;
/**
 *
 * @author  unknown
 */
public class MPError
{
    /** Creates a new instance of MPError */
    public MPError(String mess)
    {
        new JOptionPane().showMessageDialog(null,mess,"MPlaNet Error",JOptionPane.ERROR_MESSAGE);
		
    }
    
}
