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

import Framework.Enum.Command;
import Framework.Enum.ReturnInfo;
import Framework.Kernel.User;

public class NetTool 
{
	private static InetAddress serverIP;
	private static int serverPort;
	
	public static ReturnInfo send(int cmd, Object obj, String userId)
	{
		return ReturnInfo.SUCCESS;
	}
	
	public static Object Receive(int cmd, String userId)
	{
		return ReturnInfo.SUCCESS;
	}
	
	public static Object Receive(int cmd, String[] args, String userId )
	{
		return ReturnInfo.SUCCESS;
	}
	//先获取inObj,然后传出cmd和outObj,最后接收server成功信息
	public static ReturnInfo connect(Object outObj, Object inObj, Command cmd)
	{
		try
		{	
			Socket socket=new Socket(serverIP, serverPort);

			if(inObj != null)
			{
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				inObj = ois.readObject();
			}
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(cmd);
			oos.writeObject(outObj);
			oos.flush();
			ObjectInputStream oisRtn = new ObjectInputStream(socket.getInputStream());
			ReturnInfo rtnInfo = (ReturnInfo)oisRtn.readObject();
			socket.close();
			return rtnInfo;
		} 
		catch(IOException e)
		{
			return ReturnInfo.NETWORK_ERROR;
		}
		catch(ClassNotFoundException e)
		{
			return ReturnInfo.CLASS_NOT_FOUND;
		}
	}
}
