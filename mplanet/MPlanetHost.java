package mplanet;

public class MPlanetHost
{
    public int port;
    public String ip = null;
    public String name = null;
   
    public MPlanetHost(){        
    }
    
    public MPlanetHost(int p,String i,String n)
    {
        port = p;
        ip = i;
        name = n;
    }		
}
