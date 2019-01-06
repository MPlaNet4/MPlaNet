package mplanet.explorer;

import java.io.*;
import java.net.*;
import javax.swing.filechooser.*;
import java.util.*;

class SearchThreadDriver implements Runnable {
    
    
    public SearchThreadDriver(File ff,int sp) {
        searchPort = sp;
        ServerSocket ss = null;
        try{
            ss = new ServerSocket(searchPort);
            System.out.println("search driver :waiting...");
            s = ss.accept();
            System.out.println("search driver :connection estb...");
            os = new ObjectOutputStream(new BufferedOutputStream(s.getOutputStream()));
            os.flush();
            is = new ObjectInputStream(new BufferedInputStream(s.getInputStream()));
        }catch(Exception e){
            System.out.println("search driver :cannot create search sock...");
        }
        try{
            ss.close();
        }catch(Exception e){}
        
        System.out.println(ff.toString());
        sf = ff;
        rfs = FileSystemView.getFileSystemView();
        t = new Thread(this, "SearchDriver");
        t.start();
    }
    
    public void run() {
        System.out.println("search dir: "+sf.toString());
        try{
            sstr = (String)is.readObject();
            System.out.println("SEARCH: search str recivd: "+sstr);
        }catch(Exception e){
            System.out.println("cant search");
        }
        
        Search(sf, sstr);
        
        try{
            os.writeObject("-1");
            os.flush();
            os.writeObject("-1");
            os.flush();
        }catch(Exception e){
            try{
                s.close();
            }catch(Exception ee){}
        }
        
        
        try{
            s.close();
        }catch(Exception ee){}
        System.out.println("SearchDriver: Finished searching");
    }
    
    public void Search(File sf,String sstr) {
        System.out.println(sf.toString()+" "+sstr);
        File f[] = sf.listFiles();
        Vector dlist = new Vector();
        try{
            for(int i = 0;i<f.length;i++){
                if(f[i].isDirectory()&&rfs.isTraversable(f[i]).booleanValue()){
                    dlist.add(f[i]);
                }
                else{
                    if(match(f[i].getName(),sstr)){
                        try{
                            os.writeObject(rfs.getSystemDisplayName(f[i]));
                            os.flush();
                            os.writeObject(f[i].getAbsolutePath());
                            os.flush();
                            
                        }catch(Exception e){
                            System.out.println("Trying to join...");
                            try{
                                s.close();
                                t.destroy();
                            }catch(Exception ee){}
                            System.out.println("Joined!!!");
                        }
                    }
                }
            }
            for(int i=0;i<dlist.size();i++){
                if(match(((File)(dlist.elementAt(i))).getName(),sstr)){
                    try{
                        os.writeObject(rfs.getSystemDisplayName((File)dlist.elementAt(i)));
                        os.flush();
                        os.writeObject(((File)dlist.elementAt(i)).getAbsolutePath());
                        os.flush();
                    }catch(Exception e){
                        System.out.println("Trying to join... 2 ");
                        try{
                            s.close();
                            t.destroy();
                        }catch(Exception ee){
                            System.out.println("Joined!!!2");
                        }
                    }
                    //count++;
                }
                Search((File)(dlist.elementAt(i)),sstr);
            }
        }catch(Exception e){System.out.println("A:\\");}
    }
    
    static boolean match(String s, String m) {
        //System.out.println("match");
        if ((s == null) || (m == null)) {
            throw new IllegalArgumentException("Cannot pass in null arguments.");
        }
        
        final int sl = s.length();
        final int ml = m.length();
        int pos = 0;
        
        for (pos = 0; pos < sl && pos < ml && (m.charAt(pos)== '?' || Character.toUpperCase(m.charAt(pos)) == Character.toUpperCase(s.charAt(pos))); ++pos) {
        }
        
        return pos == ml ? pos == sl : m.charAt(pos) == '*' && (match(s.substring(pos), m.substring(pos + 1)) || (pos < sl && match(s.substring(pos + 1), m.substring(pos))));
    }
    
    int searchPort = 0;
    File sf = null;
    String sstr =null;
    ObjectOutputStream os = null;
    ObjectInputStream is = null;
    FileSystemView rfs = null;
    String searchString = null;
    Thread t = null;
    static boolean runThread = true;
    static Socket s = null;
    
}
