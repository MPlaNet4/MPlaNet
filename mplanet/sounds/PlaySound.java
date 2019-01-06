package mplanet.sounds;

import javazoom.jl.player.*;
import java.io.*;

public class PlaySound implements Runnable {
    
    File f;
    Player p;
    FileInputStream is=null;
    String mp3;
        
    public PlaySound(String name)
    {
        mp3=name;
       	new Thread(this, "sound player").start();
    }
    
    public void run()
    {
        File f=new File(mp3);
		System.out.println(mp3);
        try{
            is=new FileInputStream(f);
            p=new Player(is);
			while(p.play(1));
			p.close();
		}catch(Exception e){System.out.println(e);}
    }
}

