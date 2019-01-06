/*
 * MPlanetInfo.java
 *
 * Created on May 16, 2003, 8:08 PM
 */

package mplanet;

import java.util.*;
import javax.swing.*;
/**
 *
 * @author  Pradhan
 */
public class MPlanetInfo {
    
    /** Creates a new instance of MPlanetInfo */
    public MPlanetInfo() {
        user = new MPlanetHost();
        hosts = new Vector();
        state = new MPlanetState();
    }
    
    public boolean isSoundOn = true;
    public MPlanetHost user = null;
    public Vector hosts = null;
    public MPlanetState state = null;
    //mplanet main ports
    public final int mcPort = 50555;
    public final int reqPort = 51555;
    public final int chatPort = 52555;
    //remote assistance ports
    public final int imagePort = 53555;
    public final int mousePort = 54555;
    public final int keyPort = 65534;
    //explorer ports
    public final int explorerPort = 56555;
    public final int searchPort = 57555;
    
    public final String mcGroup = "224.4.3.2";
    public JButton remAssButton = null;
    public JButton fileSharButton = null;
}
