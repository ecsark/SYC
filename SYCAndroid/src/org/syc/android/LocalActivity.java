package org.syc.android;

import java.util.*;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class LocalActivity extends Activity {

	private Gallery mGallery;
	private SimpleAdapter gallerySimpleAdapter;
	private SimpleAdapter listSimpleAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_local);
		mGallery = (Gallery) findViewById(R.id.gallery);
		initSimpleAdapter();
		//mGallery.setCallbackDuringFling(false);
		mGallery.setAdapter(gallerySimpleAdapter);
		mGallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
				//textView.setText("item " + position + " selected");
				/*listSimpleAdapter = new SimpleAdapter(this, getListData(position), R.layout.list_item,
						new String[] { "title","img" }, new int[] { R.id.list_title, R.id.list_img });
				ListView listView = (ListView)findViewById(R.id.listView1);
				listView.setAdapter(listSimpleAdapter);*/
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
	}
	
	private void initSimpleAdapter() 
	{
		gallerySimpleAdapter = new SimpleAdapter(this, getData(), R.layout.gallery_item,
		new String[] { "title","img" }, new int[] { R.id.gallery_item_title, R.id.gallery_item_image });
	}

	
	private List<Map<String, Object>> getData() 
	{
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
 
        Map<String, Object> map;
        for(int i=0;i<30;i++)
        {
        	map = new HashMap<String, Object>();
        	map.put("title", "Item"+i);
            map.put("img", R.drawable.piano);
            list.add(map);
        }
        return list;
    }
	
	/*
	private List<Map<String, Object>> getListData(int position) 
	{
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        
        Map<String, Object> map;
        for(int i=0;i<20;i++)
        {
        	map = new HashMap<String, Object>();
        	map.put("title", "Item" + position + "'s " + i);
            if(position%2==0)
            {
            	if(i%2 == 0)
            		map.put("img", R.drawable.i1);
            	else
            		map.put("img", R.drawable.i2);
            }
            else
            {
            	if(i%2 == 1)
            		map.put("img", R.drawable.i1);
            	else
            		map.put("img", R.drawable.i2);
            }
            list.add(map);
        }
        return list;
    }*/

}
