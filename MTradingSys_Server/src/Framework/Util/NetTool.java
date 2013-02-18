package Framework.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.sound.midi.Receiver;

import Framework.Const.Command;
import Framework.Const.ReturnInfo;
import Framework.Kernel.User;

public class NetTool 
{
	private static InetAddress serverIP;
	private static int serverPort;
	
	public static ReturnInfo send(int cmd, Object obj, String userId)
	{
		try
		{	
			Socket socket=new Socket(serverIP, serverPort);
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
		catch (IOException e)
		{
			return ReturnInfo.IO_EXCEPTION;
		} 
		catch (ClassNotFoundException e) {
			return ReturnInfo.CLASS_NOT_FOUND;
		} 
	}
	
	public static Object Receive(int cmd, String userId)
	{
		try
		{	
			Socket socket=new Socket(serverIP, serverPort);
		
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
		catch(IOException e)
		{
			return null;
		} 
		catch (ClassNotFoundException e) 
		{
			return null;
		}
	}
	
	public static Object Receive(int cmd, String[] args, String userId )
	{
		try
		{	
			Socket socket=new Socket(serverIP, serverPort);
		
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
