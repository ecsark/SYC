package Framework.Kernel;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.concurrent.*;

import javax.security.auth.login.LoginContext;

import Framework.Const.Command;
import Framework.Util.LogWriter;

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
                executorService.execute(new Invoker(socket));
            } 
            catch (Exception e) 
            {
            	LogWriter.write();  
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
		ObjectInputStream ois = null;  
        ObjectOutputStream oos = null;  
        try 
        {  
            ois = new ObjectInputStream(socket.getInputStream());  
            oos = new ObjectOutputStream(socket.getOutputStream());  
            //get cmd
            int cmd = (int)ois.readObject();
            //get userID
            String userId = (String)ois.readObject();
            
            Handler handler = new Handler(cmd, userId, ois, oos);
            handler.handle();
            socket.close();  
            LogWriter.write();            
        }
        catch(IOException e)
        {
        	LogWriter.write();  
        } 
        catch (ClassNotFoundException e) 
        {
        	LogWriter.write();  
		}
        
    }
    
    
    	
}