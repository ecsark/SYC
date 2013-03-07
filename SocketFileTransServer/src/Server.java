import java.net.*;
import java.io.*;

public class Server 
{
	public static void main(String[] args) 
	{
		Socket client = null;
		byte[] outputBytes = null;
		int length = 0;
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		String path = "D:/Lab/tde.zip";
		int fileSize;
		try
		{
			try
			{
				ServerSocket serverSocket = new ServerSocket(5678);
				System.out.println(">>Start listing...");
				client = serverSocket.accept();
				System.out.println(">>Accepted a client!");
				
				File file = new File(path);
				fileSize = (int)file.length();
				outputBytes = new byte[fileSize];
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				bis.read(outputBytes, 0, fileSize);
				OutputStream os = client.getOutputStream();
				System.out.println(fileSize);
				byte[] fileSizeToBytesArray = intToByteArray(fileSize);   
				os.write(fileSizeToBytesArray, 0, 4);
				os.write(outputBytes, 0, fileSize);
				os.flush();
				System.out.println(">>File sended!");
				/*ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
				int returnInfo = (int)ois.readObject();
				if(returnInfo == 1)
				{
					return;
				}
				else {
					System.in.read();
				}*/
				/*while((length = fis.read(outputBytes, 0, outputBytes.length)) > 0);
				{
					dos.write(outputBytes, 0, length);
					dos.flush();
				}*/
				
			}
			finally
			{
				if(fis != null)
					fis.close();
				if(client != null)
					client.close();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static byte[] intToByteArray(int n) 
	{  
        byte[] b = new byte[4];  
        b[3] = (byte) (n & 0xff);  
        b[2] = (byte) (n >> 8 & 0xff);  
        b[1] = (byte) (n >> 16 & 0xff);  
        b[0] = (byte) (n >> 24 & 0xff);  
        return b;  
    }  

}
