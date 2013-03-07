package org.syc.music_trading_system.Item;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Vector;



import org.syc.framework.Item.*;
import org.syc.framework.Kernel.TransactionItem;
import org.syc.framework.Const.*;
import org.syc.music_trading_system.Item.*;

public class ExSellingItemList implements Serializable
{
	Vector<ExSellingItem> list;

	public ExSellingItemList(ExSellingItemList eL) 
	{
		list = new Vector<ExSellingItem>(eL.list);
	}
	public ExSellingItemList() 
	{
		list = new Vector<ExSellingItem>();
	}

	public int size() 
	{
		return list.size();
	}

	public ExSellingItem get(int index) 
	{
		return list.get(index);
	}

	public boolean isEmpty() 
	{
		return list.isEmpty();
	}

	public ReturnInfo add(ExSellingItem eItem) 
	{
		if (list.add(eItem))
			return ReturnInfo.SUCCESS;
		else
			return ReturnInfo.UNKNOWN_EXPECTION;
	}

	public ReturnInfo delete(ExSellingItem eItem) 
	{
		if (list.remove(eItem))
			return ReturnInfo.SUCCESS;
		else
			return ReturnInfo.UNKNOWN_EXPECTION;
	}
	
	public void clear()
	{
		list.clear();
	}
	
	public ExSellingItem searchId(long id) 
	{
		//Music mtmp = new Music(id, "", false, 0L);
		TransactionItem tItem = new TransactionItem(id, 0L, null, 0L); 
		ExSellingItem i = new ExSellingItem(tItem, 0L, null);
		int findIndex = list.indexOf(i);
		if (findIndex == -1)
			return null;
		else
			return list.get(findIndex);
	}
	
	public static ExSellingItemList serchItemsHaveOriId(long id, ExSellingItemList list)
	{
		ExSellingItemList exl = new ExSellingItemList();
		for(int i=0; i<list.size(); i++)
		{
			if(((Music)(list.get(i).getItem())).getOriginMusicId() == id)
			{
				exl.add(list.get(i));
			}
		}
		return exl;
	}
	
	/* need complete */
	public ReturnInfo sort(String cmd) 
	{
		return ReturnInfo.SUCCESS;
	}

	/*public ReturnInfo writeToFile(String fileName)
	{
		ObjectOutputStream oos = null;
		FileOutputStream fos = null;
		
		try 
		{
			fos = openFileOutput("fileName", MODE_PRIVATE); 
			oos = new ObjectOutputStream(fos);
			oos.writeObject(this);
			return ReturnInfo.SUCCESS;

		}
		catch (IOException e)
		{						
			e.printStackTrace();
		} 
		finally
		{
			oos.close();
			fos.close();			
		}
	}
	
	public ReturnInfo readFromFile(String fileName)
	{
		ObjectInputStream ois = null;
		FileInputStream fis = null;
		
		try
		{
			fis = openFileInput(fileName); 
			ois = new ObjectInputStream(fis);
			this.list = ((ExSellingItemList)ois.readObject()).list;
		
		} catch (Exception e) {
			// TODO: handle exception
		}
		finally
		{
			ois.close();
			fis.close();
		}
	}*/

}
