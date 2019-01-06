package mplanet.explorer;

import java.io.*;
/**
 *
 * @author  Pradhan
 */

public class RemoteFile implements Serializable{
    File file  = null;
    String sysName = null;
    String absolutePath = "";
    RemoteFile(File f,String s){
        file = f;
        sysName = s;
        try{
            absolutePath = file.getParentFile().getAbsolutePath();
        }catch(Exception e){}
    }
}