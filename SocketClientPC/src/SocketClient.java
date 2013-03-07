import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class SocketClient 
{
	private static String serverIP = "192.168.1.111";
	private static int serverPort = 5678;
	
	public static void main(String[] args)
	{
		int senderNum = 1;
		int receverNum = 1;
		
		int i;
		for(i=0; i<senderNum; i++)
		{
			genOneSendClient(i);
			
		}
		for(; i<senderNum+receverNum; i++)
		{
			genOneReceiveClient(i);
		}
	
	}
	public static void genOneSendClient(final int clientId)
	{
		new Thread(new Runnable() 
		{
			@Override
			public void run() 
			{
				myClassA outObj = new myClassA(clientId, "This is client" + clientId + "'s obj");
				send(outObj, "send");
				System.out.println("\n==============================");
			}
		}).start();
	}
	
	public static void genOneReceiveClient(final int clientId)
	{
		new Thread(new Runnable() 
		{
			@Override
			public void run() 
			{
				myClassA inObj = (myClassA)receive("receive");
				inObj.print();
				System.out.println("\n==============================");
			}
		}).start();
	}
	
	public static void send(Object outObj, String cmd)
	{
		try
		{	
			Socket socket=new Socket(serverIP, serverPort);
			System.out.println("socket established! Start sending...");
			//send cmd
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			oos.writeObject(cmd);
			oos.flush();
			//send outObj
			oos.writeObject(outObj);
			oos.flush();
			
			String returnInfo = (String)ois.readObject();
			System.out.println(returnInfo);
			
			System.out.println("socket closed!");
			socket.close();
		} 
		catch(IOException e)
		{
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static Object receive(String cmd)
	{
		try
		{	
			Socket socket=new Socket(serverIP, serverPort);
			System.out.println("socket established! Start receiving...");
			//send cmd
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(cmd);
			oos.flush();
			//recv inObj
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			Object inObj = ois.readObject();
			//confirm
			oos.writeObject(new String("Confirmation from client: Received!"));
			oos.flush();
			System.out.println("socket closed!");
			socket.close();
			return inObj;
		} 
		catch(IOException e)
		{
			e.printStackTrace();
			return null;
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
			return null;
		}
	}
}

