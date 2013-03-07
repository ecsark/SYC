package Framework.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.sound.midi.Receiver;

import Framework.Const.Command;
import Framework.Const.ReturnInfo;
import Framework.Kernel.User;

public class NetTool 
{
	public static InetAddress serverIP;
	public static int serverPort;
	public static int timeout = 5000;
	
	public static long getId(String userName)
	{
		try
		{	
			/*Socket socket=new Socket();
			socket.setSoTimeout(timeout);
			socket.connect(new InetSocketAddress(serverIP, serverPort));*/
			Socket socket = new Socket(serverIP, serverPort);
			socket.setSoTimeout(timeout);
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			//send cmd
			oos.writeObject(Command.FETCH_ID);
			oos.flush();
			//send invalid userId, just for format
			oos.writeObject(0L);
			oos.flush();
			//send userName
			oos.writeObject(userName);
			oos.flush();
			//receive inObj
			Object inObj = ois.readObject();
			//send confirmation
			oos.writeObject(ReturnInfo.SUCCESS);
			oos.flush();
			socket.close();
			return (long)inObj;
		} 
		catch (SocketTimeoutException e)
		{
			System.out.println("timeout");
			return -1L;
		}
		catch(IOException e)
		{
			System.out.println("ioexcption");
			return 0L;
		} 
		catch (ClassNotFoundException e) 
		{
			System.out.println("class not found");
			return 0L;
		}
	}
	public static ReturnInfo send(int cmd, Object obj, long userId)
	{
		try
		{	
			Socket socket=new Socket(serverIP, serverPort);
			socket.setSoTimeout(timeout);
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			//send cmd
			oos.writeObject(cmd);
			oos.flush();
			//send userID
			oos.writeObject(userId);
			oos.flush();
			//send outObj
			oos.writeObject(obj);
			oos.flush();
			//receive confirmation
			ReturnInfo returnInfo = (ReturnInfo)ois.readObject();
			socket.close();
			return returnInfo;
		} 
		catch (SocketTimeoutException e)
		{
			return ReturnInfo.REQUEST_TIMEOUT;
		}
		catch (IOException e)
		{
			return ReturnInfo.IO_EXCEPTION;
		} 
		catch (ClassNotFoundException e) {
			return ReturnInfo.CLASS_NOT_FOUND;
		} 
	}
	
	public static Object Receive(int cmd, long userId)
	{
		try
		{	
			Socket socket=new Socket(serverIP, serverPort);
			socket.setSoTimeout(timeout);
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			//send cmd
			oos.writeObject(cmd);
			oos.flush();
			//send userId
			oos.writeObject(userId);
			oos.flush();
			//receive inObj
			Object inObj = ois.readObject();
			//send confirmation
			oos.writeObject(ReturnInfo.SUCCESS);
			oos.flush();
			socket.close();
			return inObj;
		} 
		catch (SocketTimeoutException e)
		{
			return null;
		}
		catch(IOException e)
		{
			return null;
		} 
		catch (ClassNotFoundException e) 
		{
			return null;
		}
	}
	/**String[]: SourceId, long lowerBound, long upperBund, int pageNum**/
	public static Object Receive(int cmd, String[] args, long userId )
	{
		try
		{	
			Socket socket=new Socket(serverIP, serverPort);
			socket.setSoTimeout(timeout);
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			//send cmd
			oos.writeObject(cmd);
			oos.flush();
			//send userId
			oos.writeObject(userId);
			oos.flush();
			//send args
			oos.writeObject(args);
			oos.flush();
			//receive inObj
			Object inObj = ois.readObject();
			//send confirmation
			oos.writeObject(ReturnInfo.SUCCESS);
			oos.flush();
			socket.close();
			return inObj;
		} 
		catch (SocketTimeoutException e)
		{
			return null;
		}
		catch(IOException e)
		{
			return null;
		} 
		catch (ClassNotFoundException e) 
		{
			return null;
		}
	}

}
