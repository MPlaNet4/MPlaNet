package mplanet.explorer;

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
