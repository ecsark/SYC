import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import vee.NetworkTest;

import Framework.Const.Command;
import Framework.Const.ReturnInfo;
import Framework.Item.Item;
import Framework.Item.ItemList;
import Framework.Kernel.Interface;
import Framework.Kernel.TransactionItem;
import Framework.Kernel.TransactionItemList;
import Framework.Kernel.User;
import Framework.Util.NetTool;
import Framework.*;
import MusicTradingSystem.Item.Music;
import MusicTradingSystem.Item.MusicList;
import MusicTradingSystem.Item.OriginalMusic;


public class Test 
{
	public static void main(String[] args)
	{
		try 
		{
			NetTool.serverIP = InetAddress.getByName("10.147.106.30");
		} 
		catch (UnknownHostException e) 
		{
			e.printStackTrace();
		}
		NetTool.serverPort = 5678;
		//networkTest();
		//regist("wuhao", "123456");
		Interface.user.setName("123");
		System.out.println(Interface.nameConfliction());
		//login("wuhao", "123456");
		
		//Interface.user.setId(NetTool.getId("wuhao"));
		//logout();
		//printUser(Interface.user);
		//Interface.user.setId(NetTool.getId("wuhao"));
		//Interface.refreshRank();
		//System.out.println(Interface.user.getRank());
		//System.out.println(NetTool.getId("wuhao"));
		//refreshItemList();
		//printMusicList(Interface.itemList);
		//printItemList(Interface.user.itemList);
		//sellItem(0, 100);
		//printItemList(Interface.user.itemList);
		//refreshOringinMusicList();
		//printItemList(Interface.oML);
		//ObjectInputStream ois = new ObjectInputStream();
		//Music music = new Music(998L, "music", false, 98L);
		//TransactionItem tItem = new TransactionItem(001L, "123", music, 9999L);
		//Music music2 = (Music)tItem.getItem();
		//printMusic(music2);
		//test();
		
		//getTransactionList();
		//printTransactionItemList(Interface.auction.list);
		//makeTransaction(Interface.auction.list.get(1));
		/*refreshItemList();
		printMusicList(Interface.itemList);
		sellItem(3, 88L);
		printMusicList(Interface.itemList);
		refreshSellingList();
		printTransactionItemList(Interface.auction.sellingList);
		cancelSellItem(Interface.auction.sellingList.get(0));
		printMusicList(Interface.itemList);*/
		//getReward();
		//printUser(Interface.user);
	
	}
	
	private static void test()
	{
		TransactionItem tItem = (TransactionItem)NetTool.Receive(Command.TEST, Interface.user.getId());
		if(tItem == null)
			System.out.println("\n\n>>ITEM IS NULL!");
		Music music = (Music)(tItem.getItem());
		printMusic(music);
	}
	
	private static void regist(String name, String password)
	{
		Interface.user.setName(name);
		Interface.user.setPassword(password);
		ReturnInfo rtnInfo = Interface.regist();
		if(rtnInfo == ReturnInfo.SUCCESS)
			System.out.println("\n\n>>REGIST SUCCESS!");
		else if(rtnInfo == ReturnInfo.NAME_CONFLICT)
		{
			System.out.println("\n\n>>NAME CONFLICT!");
		}
		else {
			System.out.println("\n\n>>FAIL!");
		}
	}
	
	private static void login(String name, String password)
	{
		Interface.user.setName(name);
		Interface.user.setPassword(password);
		ReturnInfo rtnInfo = Interface.login();
		if(rtnInfo == ReturnInfo.SUCCESS)
			System.out.println("\n\n>>LOGIN SUCCESS!");
		else if(rtnInfo == ReturnInfo.NAME_CONFLICT)
		{
			System.out.println("\n\n>>NAME CONFLICT!");
		}
		else if(rtnInfo == ReturnInfo.REQUEST_TIMEOUT)
		{
			System.out.println("\n\n>>REQUEST TIMEOUT!");
		}
		else {
			System.out.println("\n\n>>FAIL!");
		}		
	}
	
	private static void logout()
	{
		ReturnInfo rtnInfo = Interface.logout();
		if(rtnInfo == ReturnInfo.SUCCESS)
			System.out.println("\n\n>>LOGOUT SUCCESS!");
		else {
			System.out.println("\n\n>>FAIL!");
		}		
	}
	
	public static void refreshItemList()
	{
		ReturnInfo rtnInfo = Interface.refreshItemList();
		if(rtnInfo == ReturnInfo.SUCCESS)
			System.out.println("\n\n>>REFRESH ITEMLIST SUCCESS!");
		else {
			System.out.println("\n\n>>FAIL!");
		}	
	}
	
	public static void sellItem(int i,long price)
	{
		TransactionItem tItem = new TransactionItem(0,0L,Interface.itemList.get(i),price);
		ReturnInfo rtnInfo = Interface.SellItem(tItem);
		if(rtnInfo == ReturnInfo.SUCCESS)
			System.out.println("\n\n>>SELLITEM SUCCESS!");
		else {
			System.out.println("\n\n>>FAIL!");
		}	
	}
	
	public static void cancelSellItem(TransactionItem tItem)
	{
		ReturnInfo rtnInfo = Interface.cancelSellItem(tItem);
		if(rtnInfo == ReturnInfo.SUCCESS)
			System.out.println("\n\n>>CANCEL SELLITEM SUCCESS!");
		else {
			System.out.println("\n\n>>FAIL!");
		}	
	}
	
	public static void refreshSellingList()
	{
		ReturnInfo rtnInfo = Interface.refreshSellingList();
		if(rtnInfo == ReturnInfo.SUCCESS)
			System.out.println("\n\n>>REFRESH SELLINGITEMLIST SUCCESS!");
		else {
			System.out.println("\n\n>>FAIL!");
		}	
	}
	
	public static void refreshOringinMusicList()
	{
		ReturnInfo rtnInfo = Interface.refreshOriginalMusicList();
		if(rtnInfo == ReturnInfo.SUCCESS)
			System.out.println("\n\n>>REFRESH ORGIN MUSIC LIST SUCCESS!");
		else {
			System.out.println("\n\n>>FAIL!");
		}	
	}
	
	public static void getTransactionList()
	{
		Interface.auction.args.musicSrcID = 10L;
		ReturnInfo rtnInfo = Interface.getTransItemList();
		if(rtnInfo == ReturnInfo.SUCCESS)
			System.out.println("\n\n>>GET TRANSACTIONLIST SUCCESS!");
		else {
			System.out.println("\n\n>>FAIL!");
		}	
	}
	
	public static void makeTransaction(TransactionItem tItem)
	{
		ReturnInfo rtnInfo = Interface.makeTransaction(tItem);
		if(rtnInfo == ReturnInfo.SUCCESS)
			System.out.println("\n\n>>MAKE TRANSACTIONLIST SUCCESS!");
		else {
			System.out.println("\n\n>>FAIL!");
		}	
	}
	
	public static void getReward()
	{
		ReturnInfo rtnInfo = Interface.getReward();
		if(rtnInfo == ReturnInfo.SUCCESS)
			System.out.println("\n\n>>GET REWARD SUCCESS!");
		else {
			System.out.println("\n\n>>FAIL!");
		}	
	}
	///////////////////////////////////////////////////////////////////////
	private static void printUser(User user)
	{
		System.out.println("id: "+user.getId());
		System.out.println("name: "+user.getName());
		System.out.println("money "+user.getProperty());
		System.out.println("rank: #"+user.getRank()+"\n");
	}
	
	private static void printItemList(ItemList itemList)
	{
		for(int i=0; i<itemList.size(); i++)
		{
			printItem(itemList.get(i));
		}
	}
	
	private static void printTransactionItemList(TransactionItemList tItemList)
	{
		for(int i=0; i<tItemList.size(); i++)
		{
			printTransactionItem(tItemList.get(i));
		}
	}
	
	private static void printItem(Item item)
	{
		System.out.println("itemId: "+item.getId());
		System.out.println("itemName: "+item.getName());
		System.out.println("isAuction: "+item.isAuction());
	}
	private static void printMusic(Music music)
	{
		printItem(music);
		System.out.println("OriginalMusicId: "+music.getOriginMusicId());
	}
	private static void printTransactionItem(TransactionItem tItem)
	{
		System.out.println("tItemId: "+tItem.getId());
		printMusic((Music)tItem.getItem());
		System.out.println("price: "+tItem.getPrice());
		System.out.println("sellerId: "+tItem.getSellerId() +"\n");
	}
	
	private static void printMusicList(ItemList mL)
	{
		for(int i=0; i<mL.size(); i++)
		{
			printMusic((Music)mL.get(i));
		}
	}
}