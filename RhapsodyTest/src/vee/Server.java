package vee;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.concurrent.*;


public class Server 
{
    private int port = 5678;
    private ServerSocket serverSocket;
    private ExecutorService executorService;  //ThreadPool
    private final int POOL_SIZE = 10;  //PoolSize for each CPU
    
    public Server() throws IOException
    {
        serverSocket = new ServerSocket(port);
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*POOL_SIZE);
        System.out.println("Server Starts Listening...");
    }
    
    public void service()
    {
    	while(true)
        {
            Socket socket=null;
            try 
            {
                socket=serverSocket.accept();
                System.out.println("accepted");
                executorService.execute(new Invoker(socket));
            } 
            catch (Exception e) 
            {
            	//LogWriter.write();  
            }
        }
    }
    
    public static void main(String[] args) throws IOException 
    {
        new Server().service();
    }

}

class Invoker implements Runnable
{
    private Socket socket;
    public Invoker(Socket socket)
    {
        this.socket=socket;
    }
    
    public void run()
    {
        try {
        	//get i/o stream
        	ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());  
        	ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream()); 
        	//System.in.read();
            //fetch command and user id
            int cmd = (int) ois.readObject();
            long uid = (long) ois.readObject();
            //test code
            //User u = (User)ois.readObject();
            //System.out.println(u.getName());
            //serve the request
            Service svs = new Service(cmd, uid, ois, oos);
            svs.Serve();

            socket.close();
        }
        catch(IOException e)
        {
        	System.out.println("io exc");
        } 
        catch (ClassNotFoundException e) 
        {
        	System.out.println("classnotfound exc");
		}
    }
    


}