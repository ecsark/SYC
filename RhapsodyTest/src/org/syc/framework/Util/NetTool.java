package org.syc.framework.Util;

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

import org.syc.framework.Const.*;

public class NetTool 
{
	public static InetAddress serverIP;
	public static int serverPort;
	public static int timeout = 5000;
	
	public static ReturnInfo receiveByName(int cmd, String userName, Object inObj)
	{
		Socket socket=new Socket();
		try
		{	
			socket.connect(new InetSocketAddress(serverIP, serverPort),timeout);
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			//send cmd
			oos.writeObject(cmd);
			oos.flush();
			//send void userId, just for format
			oos.writeObject(0L);
			oos.flush();
			//send userName
			oos.writeObject(userName);
			oos.flush();
			//receive inObj
			inObj = ois.readObject();
			/*//send confirmation
			oos.writeObject(ReturnInfo.SUCCESS);
			oos.flush();*/
			return ReturnInfo.SUCCESS;
		} 
		catch (SocketTimeoutException e)
		{
			return ReturnInfo.REQUEST_TIMEOUT;
		}
		catch(IOException e)
		{
			return ReturnInfo.IO_EXCEPTION;
		} 
		catch (Exception e) 
		{
			return ReturnInfo.UNKNOWN_EXPECTION;
		}
		finally
		{
			try
			{
				if(socket != null)
					socket.close();				
			}
			catch(IOException e)
			{
				return ReturnInfo.UNKNOWN_EXPECTION;
			}
		}
	}
	public static ReturnInfo send(int cmd, Object obj, long userId)
	{
		Socket socket=new Socket();
		try
		{	
			socket.connect(new InetSocketAddress(serverIP, serverPort),timeout);
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
		catch (ClassNotFoundException e) 
		{
			return ReturnInfo.CLASS_NOT_FOUND;
		}
		finally
		{
			try
			{
				if(socket != null)
					socket.close();				
			}
			catch(IOException e)
			{
				return ReturnInfo.UNKNOWN_EXPECTION;
			}
		}
		
	}
	
	public static ReturnInfo receive(int cmd, long userId, Object inObj)
	{
		Socket socket=new Socket();
		try
		{	
			socket.connect(new InetSocketAddress(serverIP, serverPort),timeout);
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			//send cmd
			oos.writeObject(cmd);
			oos.flush();
			//send userId
			oos.writeObject(userId);
			oos.flush();
			//receive inObj
			inObj = ois.readObject();
			//send confirmation
			oos.writeObject(ReturnInfo.SUCCESS);
			oos.flush();
			return ReturnInfo.SUCCESS;
		} 
		catch (SocketTimeoutException e)
		{
			return ReturnInfo.REQUEST_TIMEOUT;
		}
		catch(IOException e)
		{
			return ReturnInfo.IO_EXCEPTION;
		} 
		catch (Exception e) 
		{
			return ReturnInfo.UNKNOWN_EXPECTION;
		}
		finally
		{
			try
			{
				if(socket != null)
					socket.close();				
			}
			catch(IOException e)
			{
				return ReturnInfo.UNKNOWN_EXPECTION;
			}
		}
	}
	/**String[]: SourceId, long lowerBound, long upperBund, int pageNum**/
	public static ReturnInfo receive(int cmd, String[] args, long userId ,Object inObj)
	{
		Socket socket=new Socket();
		try
		{	
			socket.connect(new InetSocketAddress(serverIP, serverPort),timeout);
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
			inObj = ois.readObject();
			//send confirmation
			oos.writeObject(ReturnInfo.SUCCESS);
			oos.flush();
			return ReturnInfo.SUCCESS;
		} 
		catch (SocketTimeoutException e)
		{
			return ReturnInfo.REQUEST_TIMEOUT;
		}
		catch(IOException e)
		{
			return ReturnInfo.IO_EXCEPTION;
		} 
		catch (Exception e) 
		{
			return ReturnInfo.UNKNOWN_EXPECTION;
		}
		finally
		{
			try
			{
				if(socket != null)
					socket.close();				
			}
			catch(IOException e)
			{
				return ReturnInfo.UNKNOWN_EXPECTION;
			}
		}
	}

}
