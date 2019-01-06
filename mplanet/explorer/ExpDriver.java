package mplanet.explorer;

import java.io.*;
import javax.swing.*;
import java.util.*;
import java.net.*;
import java.io.*;
import javax.swing.filechooser.*;
/**
 *
 * @author  Pradhan
 */
public class ExpDriver implements Runnable{
    
    public Thread t = null;
    public boolean runThread = true;
    /** Creates a new instance of ExpDriver */
    public ExpDriver(int ep,int sp) {
        explorerPort = ep;
        searchPort = sp;
        t = new Thread(this,"Driver");
    }
    public static void main(String args[]){
        new ExpDriver(56555, 57555).t.start();
    }
    
    
    public void run() {
        try{
            ServerSocket sr = new ServerSocket(explorerPort);
            System.out.println("Listening");
            sk = sr.accept();
            System.out.println("sock created");
        }catch(Exception e){
            System.out.println("NO sock");
        }
        try {
            os = new ObjectOutputStream(new BufferedOutputStream(sk.getOutputStream()));
            os.flush();
            is = new ObjectInputStream(new BufferedInputStream(sk.getInputStream()));
            System.out.println("Streams created");
            System.out.println("Streams created SEARCH");
            listTheFiles();
            sendIt();
            System.out.println("wrote objects");
        }catch(Exception e){
            try{
                sk.close();
            }catch(Exception ex){}
            try{
                searchDriver.s.close();
            }catch(Exception ex){}
            
            
            System.out.println("terminating");
        }
		//Q -> quickView
        //P -> get parent direcory
        //D -> get The directory
        //C ->copy the file
        
        while(runThread){
            String req = null;
            try{
                req = (String)is.readObject();
            }catch(Exception e){}

			if(req.charAt(0)=='Q'){
				File tempFile = null;
				int n = 0;
				byte[] tempArr=new byte[1024*1024];
                try{
                    tempFile = (File)is.readObject();
                	FileInputStream tempFs = new FileInputStream(tempFile);
					n=tempFs.read(tempArr);
					os.write(tempArr);
	                os.flush();
					os.writeInt(n);
					os.flush();
					tempFs.close();
				}catch(Exception e){System.out.println(e);}

			}
            else if(req.charAt(0)=='P'){
                File temp = rfs.getParentDirectory(curDir);
                if(temp != null)
                    curDir = temp;
                System.out.println(curDir.toString());
                listTheFiles();
                sendIt();
            }
            else if(req.charAt(0)=='D'){
                File temp = null;
                try{
                    temp = (File)is.readObject();
                }catch(Exception e){}
                if(temp != null)
                    curDir = temp;
                System.out.println(curDir.toString());
                listTheFiles();
                sendIt();
            }
            else if(req.charAt(0)=='C'){
                File temp = null;
                try{
                    temp = (File)is.readObject();
                }catch(Exception e){}
                BufferedInputStream fis = null;
                try{
                    fis =  new BufferedInputStream(new FileInputStream(temp),maxSize);
                }catch(Exception e){}
                byte[] arr = new byte[maxSize];
                try{
                    os.writeLong(temp.length());
                    os.flush();
                }catch(Exception e){}
                
                int gcCount = 0;
                while(true){
                    int bytesRead = 0;
                    try{
                        bytesRead= fis.read(arr);
                    }catch(Exception e){}
                    System.out.println(bytesRead);
                    if(bytesRead != -1){
                        try{
                            os.write(arr);
                            
                            os.flush();
                            os.writeInt(bytesRead);
                            os.flush();
                        }catch(Exception e){}
                        gcCount++;
                    }
                    else {
                        try{
                            os.write(new byte[maxSize]);
                            os.flush();
                            os.writeInt(-1);
                            os.flush();
                        }catch(Exception e){}
                        System.out.println("breaking reading");
                        break;
                    }
                    if(gcCount == 20){
                        try{
                            runGC();
                        }catch(Exception e){}
                        gcCount = 0;
                    }
                    
                }
                try{
                    fis.close();
                }catch(Exception e){}
            }
            else if(req.charAt(0)=='F'){
                System.out.println("Recieved Search Request");
                searchDriver = new SearchThreadDriver(curDir,searchPort);
            }
        }
        try{
            sk.close();
        }catch(Exception ex){}
        try{
            searchDriver.s.close();
        }catch(Exception ex){}
    }
    
    
    private static void listTheFiles(){
        dirs.removeAllElements();
        files.removeAllElements();
        File[] all = curDir.listFiles();
        RemoteFile rFiles[] = new RemoteFile[all.length];
        
        for(int i = 0;i<all.length;i++){
            rFiles[i] = new RemoteFile(all[i],rfs.getSystemDisplayName(all[i]));
        }
        
        for(int i=0;i<rFiles.length;i++){
            if(rFiles[i].file.isDirectory()){
                
                dirs.add(rFiles[i]);
            }
            else{
                files.add(rFiles[i]);
            }
        }
    }
    
    
    private void sendIt(){
        try{
            //System.out.println((RemoteFile)(dirs.elementAt(i)).file.toString());
            os.writeObject(dirs);
            os.flush();
            //System.out.println((RemoteFile)(files.elementAt(i)).file.toString());
            os.writeObject(files);
            os.flush();
            os.reset();
            
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    
    
    private static void runGC() throws Exception {
        for (int i = 0; i < 4; i++)
            _runGC();
    }
    
    private static void _runGC() throws Exception {
        for (int i = 0; i < 5; i++) {
            rTime.runFinalization();
            rTime.gc();
        }
    }
    
    
    private Socket sk = null;
    
    ObjectOutputStream os = null;
    ObjectInputStream is = null;
    
    private final int maxSize = 1024*1024;
    
    int explorerPort = 0;
    int searchPort = 0;
    
    static private FileSystemView rfs = FileSystemView.getFileSystemView();
    static private File curDir = new File(System.getProperty("user.dir"));
    static private Vector files = new Vector();
    static private Vector dirs = new Vector();
    
    SearchThreadDriver searchDriver = null;
    
    static private final Runtime rTime = Runtime.getRuntime();
}


