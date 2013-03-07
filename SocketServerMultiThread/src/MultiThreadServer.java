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

public class MultiThreadServer 
{
    private int port = 5678;
    private ServerSocket serverSocket;
    private ExecutorService executorService;  //ThreadPool
    private final int POOL_SIZE = 10;  //PoolSize for each CPU
    
    public MultiThreadServer() throws IOException
    {
        serverSocket = new ServerSocket(port);
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*POOL_SIZE);
        System.out.println("Server Starts Listening...");
    }
    
    public void service()
    {
        int listen_count = 0;
    	while(true)
        {
            Socket socket=null;
            try 
            {
                socket=serverSocket.accept();
                listen_count++;
                executorService.execute(new Invoker(socket, listen_count));
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            }
        }
    }
    
    public static void main(String[] args) throws IOException 
    {
        new MultiThreadServer().service();
    }

}

class Invoker implements Runnable
{
    private Socket socket;
    private int listen_count;
    public Invoker(Socket socket, int listen_count)
    {
        this.socket=socket;
        this.listen_count = listen_count;
    }
    /*private PrintWriter getWriter(Socket socket) throws IOException
    {
        OutputStream socketOut=socket.getOutputStream();
        return new PrintWriter(socketOut,true);
    }
    private BufferedReader getReader(Socket socket) throws IOException
    {
        InputStream socketIn=socket.getInputStream();
        return new BufferedReader(new InputStreamReader(socketIn));
    }
    public String echo(String msg)
    {
        return "echo:"+msg;
    }*/
    public void run()
    {
    	
    	SimpleDateFormat sdf = new SimpleDateFormat(listen_count + ": yyyy-MM-dd HH:mm:ss.SSS");  
       	System.out.println(sdf.format(System.currentTimeMillis()));  
		
		myClassA inObj = new myClassA();
		ObjectInputStream ois = null;  
        ObjectOutputStream oos = null;  
        try 
        {  
            ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));  
            oos = new ObjectOutputStream(socket.getOutputStream());  
            //get cmd
            Object obj = ois.readObject();
            String cmd = (String)obj;
            System.out.println("Client's command is:" + cmd);
            if(cmd.equals(new String("send")))
            {
            	Object obj2 = ois.readObject();
                inObj = (myClassA)obj2;
                inObj.print();
                oos.writeObject(new String("Confirmation from server: Received!"));
            }
            if(cmd.equals(new String("receive")))
            {
            	//gen a example of myClassA
            	myClassA outObj = new myClassA(999, "This is server's obj");
            	oos.writeObject(outObj);
            	oos.flush();
            	String returnInfo = (String)ois.readObject();
            	System.out.println(returnInfo);
            }
            socket.close();  
            System.out.println("Socket closed!\n");
            
        }
        catch(IOException e)
        {
        	e.printStackTrace();
        } 
        catch (ClassNotFoundException e) 
        {
			e.printStackTrace();
		}
        /*try 
        {
            System.out.println("New connection accepted "+socket.getInetAddress()+":"+socket.getPort());
            BufferedReader br=getReader(socket);
            PrintWriter pw=getWriter(socket);
            String msg=null;
            while((msg=br.readLine())!=null)
            {
                System.out.println(msg);
                pw.println(echo(msg));
                if(msg.equals("bye"))
                    break;
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        finally
        {
            try 
            {
                if(socket!=null)
                    socket.close();
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
        }*/
    }
    	
}