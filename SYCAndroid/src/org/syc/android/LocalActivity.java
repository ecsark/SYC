package org.syc.android;

import java.util.*;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class LocalActivity extends Activity {

	private Gallery mGallery;
	private LinearLayout mScrollLayout;
	private SimpleAdapter gallerySimpleAdapter;
	//private SimpleAdapter listSimpleAdapter;
	private Vector<ListItem> list;
	
	private void genList()
	{
		list.clear();
		list.add(new ListItem(1L, "item1", getResources().getColor(R.color.dark_blue), 50L, 50L));
		list.add(new ListItem(2L, "item2", getResources().getColor(R.color.dark_green), 50L, 65L));
		list.add(new ListItem(3L, "item3", getResources().getColor(R.color.light_green), 40L, 50L));
		list.add(new ListItem(1L, "item4", getResources().getColor(R.color.dark_blue), 75L, 80L));
		list.add(new ListItem(2L, "item5", getResources().getColor(R.color.dark_green), 90L, 85L));
		list.add(new ListItem(3L, "item6", getResources().getColor(R.color.light_green), 10L, 95L));
		list.add(new ListItem(1L, "item7", getResources().getColor(R.color.dark_blue), 5L, 75L));
		list.add(new ListItem(2L, "item8", getResources().getColor(R.color.dark_green), 65L, 70L));
		list.add(new ListItem(3L, "item9", getResources().getColor(R.color.light_green), 50L, 50L));
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_local);
		
		list = new Vector<LocalActivity.ListItem>();
		
		mGallery = (Gallery) findViewById(R.id.gallery);
		mScrollLayout = (LinearLayout)findViewById(R.id.list_scroll_view);
		initSimpleAdapter();
		//mGallery.setCallbackDuringFling(false);
		mGallery.setAdapter(gallerySimpleAdapter);
		mGallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
				genList();
				genScrollView();
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
	}
	
	private void initSimpleAdapter(){
		gallerySimpleAdapter = new SimpleAdapter(this, getData(), R.layout.gallery_item,
		new String[] { "title","img" }, new int[] { R.id.gallery_item_title, R.id.gallery_item_image });
	}

	private void genScrollView()
	{
		mScrollLayout.removeAllViews();
		addShop(mScrollLayout);
		for(int i=0; i<list.size(); i++)
		{
			final String iName = list.get(i).name;
			final int iColor = list.get(i).color;
			final long iPurchase = list.get(i).purchasePrice,
					iSelling = list.get(i).sellingPrice;
			final int pos = i;			
			
			LayoutInflater inflater = LayoutInflater.from(this);
			LinearLayout scrollViewItem = (LinearLayout)inflater.inflate(R.layout.local_item, null);
			((LinearLayout)scrollViewItem).setOrientation(LinearLayout.HORIZONTAL);
			LinearLayout block = (LinearLayout)scrollViewItem.findViewById(R.id.itemColor);
			block.setBackgroundColor(0xcf000000+ iColor);
						
			TextView name = (TextView)scrollViewItem.findViewById(R.id.itemName);
			name.setText(iName);
			
			TextView purchasePrice = (TextView)scrollViewItem.findViewById(R.id.itemPurchasePrice);
			purchasePrice.setText("$"+iPurchase);
			
			TextView sellingPrice = (TextView)scrollViewItem.findViewById(R.id.itemSellingPrice);
			sellingPrice.setText("$"+iSelling);
			
			scrollViewItem.setOnClickListener(new View.OnClickListener() {				
				@Override
				public void onClick(View view) {
					
					Intent intent = new Intent();
					intent.setClass(LocalActivity.this, PlayActivity.class);
					intent.putExtra("itemName", iName);
					intent.putExtra("itemPrice", iPurchase);
					//Toast.makeText(LocalActivity.this, Long.toString(iPurchase), Toast.LENGTH_SHORT);
					intent.putExtra("labelPrice", iSelling);
					intent.putExtra("color", iColor);
					intent.putExtra("position", pos);
					startActivityForResult(intent, 123);
					overridePendingTransition(R.anim.zoom_enter,R.anim.zoom_exit);					
				}
			});
			
			mScrollLayout.addView(scrollViewItem);		

		}
    }
	
	
	
	private void addShop(LinearLayout scrollLayout){
		LayoutInflater inflater = LayoutInflater.from(this);
		LinearLayout goOut = (LinearLayout)inflater.inflate(R.layout.go_out, null);
		TextView textView = (TextView) goOut.findViewById(R.id.goLabel);
		textView.setText("SHOP");
		goOut.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(LocalActivity.this, ShopActivity.class);
				intent.putExtra("musicID", 1);
				startActivityForResult(intent, 123);
				overridePendingTransition(R.anim.slide2,R.anim.slide1);
			}
		});
		scrollLayout.addView(goOut);
	}
	
	
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Bundle bundle =  data.getExtras();
		long labelPrice = bundle.getLong("labelPrice", -1);
		int pos = bundle.getInt("position", -1);
		updateLabelPrice(pos, labelPrice);
		
        //Toast.makeText(this, Integer.toString(labelPrice)+" "+Integer.toString(pos), Toast.LENGTH_SHORT).show();
       
    }
	
	
	private void updateLabelPrice(int pos, long labelPrice){
		list.get(pos).sellingPrice = labelPrice;
		//TODO update in server and xml
		genScrollView();
	}
	
	private List<Map<String, Object>> getData()	{
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
 
        Map<String, Object> map;
        for(int i=0;i<6;i++)
        {
        	map = new HashMap<String, Object>();
        	map.put("title", "Item"+i);
            map.put("img", R.drawable.piano);
            list.add(map);
        }
        return list;
    }

	

	private class ListItem
	{
		public long id;
		public String name;
		public int color;
		public long purchasePrice;
		public long sellingPrice;
		
		public ListItem(long id, String name, int color, long purchasePrice, long sellingPrice)
		{
			this.id = id;
			this.color = color;
			this.name = name;
			this.purchasePrice = purchasePrice;
			this.sellingPrice = sellingPrice;
		}
		
	}
}
