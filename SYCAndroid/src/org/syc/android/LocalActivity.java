package org.syc.android;

import java.util.*;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LocalActivity extends Activity {
	
	private LayoutInflater inflater;
	private LinearLayout variationScroll;
	private LinearLayout scrollIndicator;
	
	private ArrayList<ListItem> variations;
	private ArrayList<String> melodiNames;
	private ArrayList<String> fileNames;
	
	private TextView property, rank;
	
	private MediaPlayer player;
	private boolean playWhenScroll = true;
	
	private ViewPager pager;
	private ArrayList<View> viewList;

	private String melodyDirectory = Environment.getExternalStorageDirectory().getPath() + "/SYC/testmu/melodies/";
	
	private View prevItemView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_local);		

		inflater = LayoutInflater.from(this);
		
		
		
		pager = (ViewPager) findViewById(R.id.item_list);
		pager.setAdapter(new MPagerAdapter());
		viewList = new ArrayList<View>();
		for (int i = 0; i < 5; ++i) {
			View view = inflater.inflate(R.layout.local_scroll, null);
			viewList.add(view);
		}
		pager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int index) {
				variationScroll = (LinearLayout) viewList.get(index).findViewById(R.id.list_scroll_view);
				//TODO obtain variation list
				updateVariationsView(getVariationList());
				onScrollChanged(index);
				if(playWhenScroll)
					playMusic(index);
			}
			@Override
			public void onPageScrolled(int index, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		
		
		scrollIndicator = (LinearLayout) findViewById(R.id.scrollindicator);
		genIndicator();
		//;//start with 0
		
		player = new MediaPlayer();
		
		variations = new ArrayList<LocalActivity.ListItem>();
		melodiNames = new ArrayList<String>();
		fileNames = new ArrayList<String>();
		
		genMelodyList();
		variationScroll = (LinearLayout) findViewById(R.id.list_scroll_view);
		
		property = (TextView) findViewById(R.id.property);
		rank = (TextView) findViewById(R.id.rank);
		
		updateProperty(3740);
		updateRank(371);
	}
	
	private void genIndicator(){
		for(int i=0; i<viewList.size(); ++i){
			LinearLayout indicator = (LinearLayout) inflater.inflate(R.layout.indicator, null);
			//indicator.setLayoutParams(param);
			//indicator.setBackgroundResource(R.drawable.note2);
			final int index = i;
			indicator.setOnClickListener(new View.OnClickListener() {				
				@Override
				public void onClick(View view) {

					pager.setCurrentItem(index);
				}
			});
			scrollIndicator.addView(indicator);
		}
	}

	private void onScrollChanged(int index){
		if(prevItemView!=null)
			prevItemView.setBackgroundResource(R.drawable.note2);
		
		View newButton = scrollIndicator.getChildAt(index).findViewById(R.id.btn_indicator);
		newButton.setBackgroundResource(R.drawable.note1);
		prevItemView = newButton;
	}
	
	
	
	private void updateProperty(long value){
		property.setText(Long.toString(value));
	}
	
	private void updateRank(long value){
		rank.setText(Long.toString(value));
	}
	
	private void genMelodyList() {
		melodiNames.clear();
		for(int i=0; i<6; i++){
			melodiNames.add("Item "+Integer.toString(i+1));
			fileNames.add("Variation"+Integer.toString(i)+".m4a");
		}
	}
	
	private ArrayList<ListItem> getVariationList() {
		ArrayList<ListItem> variations = new ArrayList<LocalActivity.ListItem>();
		variations.add(new ListItem(1L, "item1", getResources().getColor(
				R.color.dark_blue), 50L, 50L));
		variations.add(new ListItem(2L, "item2", getResources().getColor(
				R.color.dark_green), 50L, 65L));
		variations.add(new ListItem(3L, "item3", getResources().getColor(
				R.color.light_green), 40L, 50L));
		variations.add(new ListItem(1L, "item4", getResources().getColor(
				R.color.dark_blue), 75L, 80L));
		variations.add(new ListItem(2L, "item5", getResources().getColor(
				R.color.dark_green), 90L, 85L));
		variations.add(new ListItem(3L, "item6", getResources().getColor(
				R.color.light_blue), 10L, 95L));
		variations.add(new ListItem(1L, "item7", getResources().getColor(
				R.color.dark_blue), 5L, 75L));
		variations.add(new ListItem(2L, "item8", getResources().getColor(
				R.color.dark_green), 65L, 70L));
		variations.add(new ListItem(3L, "item9", getResources().getColor(
				R.color.red), 50L, 50L));
		return variations;
	}
	
		
	private void playMusic(int index){
			player.reset();
			try {
				player.setDataSource(melodyDirectory+fileNames.get(index));
				player.prepare();
				player.setLooping(true);
				player.start();
			} catch (Exception e) {
				Toast.makeText(LocalActivity.this,
						"Music Service Failed to start", Toast.LENGTH_LONG).show();
				e.printStackTrace();			
			}
	}

	
	private void updateVariationsView(ArrayList<ListItem> variations) {
		variationScroll.removeAllViews();
		variationScroll.addView(getShopItemView());
		for (int i = 0; i < variations.size(); i++)
			variationScroll.addView(getVariationItemView(variations.get(i),i));

		variationScroll.addView(getServerItemView());
	}
	
	private LinearLayout getVariationItemView(ListItem item, int pos_row){
		final String iName = item.name;
		final int iColor = item.color;
		final long iPurchase = item.purchasePrice, 
				iSelling = item.sellingPrice;
		final int pos = pos_row;

		LinearLayout variationItemLayout = (LinearLayout) inflater.inflate(R.layout.local_item, null);
		((LinearLayout) variationItemLayout).setOrientation(LinearLayout.HORIZONTAL);
		LinearLayout block = (LinearLayout) variationItemLayout.findViewById(R.id.itemColor);
		block.setBackgroundColor(0xcf000000 + iColor);

		TextView name = (TextView) variationItemLayout.findViewById(R.id.itemName);
		name.setText(iName);

		TextView purchasePrice = (TextView) variationItemLayout.findViewById(R.id.itemPurchasePrice);
		purchasePrice.setText("$" + iPurchase);

		TextView sellingPrice = (TextView) variationItemLayout.findViewById(R.id.itemSellingPrice);
		sellingPrice.setText("$" + iSelling);

		variationItemLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				Intent intent = new Intent();
				intent.setClass(LocalActivity.this, PlayScrollActivity.class);
				intent.putExtra("itemName", iName);
				intent.putExtra("itemPrice", iPurchase);
				intent.putExtra("labelPrice", iSelling);
				intent.putExtra("color", iColor);
				intent.putExtra("position", pos);
				startActivityForResult(intent, 123);
				overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
			}
		});
		
		return variationItemLayout;
	}

	private LinearLayout getShopItemView() {
		LinearLayout shopItemLayout = (LinearLayout) inflater.inflate(R.layout.shop_server,
				null);
		TextView textView = (TextView) shopItemLayout.findViewById(R.id.goLabel);
		textView.setText("SHOP");
		shopItemLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(LocalActivity.this, ShopActivity.class);
				intent.putExtra("musicID", 1);
				startActivityForResult(intent, 123);
				overridePendingTransition(R.anim.slide2, R.anim.slide1);
			}
		});
		return shopItemLayout;
	}

	private LinearLayout getServerItemView() {
		LinearLayout serverItemLayout = (LinearLayout) inflater.inflate(R.layout.shop_server,
				null);
		TextView textView = (TextView) serverItemLayout.findViewById(R.id.goLabel);
		textView.setText("SERVER");
		serverItemLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(LocalActivity.this, ShopActivity.class);
				intent.putExtra("musicID", 1);
				startActivityForResult(intent, 123);
				overridePendingTransition(R.anim.slide2, R.anim.slide1);
			}
		});
		return serverItemLayout;
	}


	
	@Override
	public void onDestroy() {
		super.onDestroy();
		player.release();
	}

	@Override
	public void onStop() {
		super.onStop();
		player.stop();
	}

	@Override
	public void onPause() {
		super.onPause();
		player.pause();
	}

	
	
	@Override
	public void onBackPressed() {
		MessageDialog exitDialog = new MessageDialog(this){
			public void onOKPressed(){
				Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                    vibrator.vibrate(200);  
				finish();
	            System.exit(0);
			}
			public void onBackPressed(){
				dismiss();
			}
		};
		exitDialog.setTitle("Exit?");
		exitDialog.setMessage("Are you sure to quit the game?");
		exitDialog.show();
	}

	private class ListItem {
		public long id;
		public String name;
		public int color;
		public long purchasePrice;
		public long sellingPrice;

		public ListItem(long id, String name, int color, long purchasePrice,
				long sellingPrice) {
			this.id = id;
			this.color = color;
			this.name = name;
			this.purchasePrice = purchasePrice;
			this.sellingPrice = sellingPrice;
		}

	}
	
	
	class MPagerAdapter extends PagerAdapter {

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(viewList.get(arg1));
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(viewList.get(arg1), 0);
			return viewList.get(arg1);
		}

		@Override
		public int getCount() {
			return viewList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == (arg1);
		}

	}
}
