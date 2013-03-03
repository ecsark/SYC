package org.syc.android;

import java.util.*;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LocalActivity extends Activity {
	
	private LayoutInflater inflater;
	private LinearLayout variationScroll;
	private LinearLayout melodyScroll;
	
	private ArrayList<ListItem> variations;
	private ArrayList<String> melodiNames;
	private ArrayList<String> fileNames;
	
	private TextView property, rank;
	
	private MediaPlayer player;
	private boolean playWhenScroll = true;

	private String melodyDirectory = Environment.getExternalStorageDirectory().getPath() + "/SYC/testmu/melodies/";
	
	private View prevItemView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_local);		

		inflater = LayoutInflater.from(this);
		
		player = new MediaPlayer();
		
		variations = new ArrayList<LocalActivity.ListItem>();
		melodiNames = new ArrayList<String>();
		fileNames = new ArrayList<String>();
		
		genMelodyList();
		melodyScroll = (LinearLayout)findViewById(R.id.list_horizontal_scroll_view);
		variationScroll = (LinearLayout) findViewById(R.id.list_scroll_view);
		genMelodyScrollView();
		
		property = (TextView) findViewById(R.id.property);
		rank = (TextView) findViewById(R.id.rank);
		
		updateProperty(3740);
		updateRank(371);
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
	
	private void genList() {
		variations.clear();
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
	}
	
	
	private void genMelodyScrollView() {
		for (int i = 0; i < melodiNames.size(); i++) {
			final String iName = melodiNames.get(i);
			
			LayoutInflater inflater = LayoutInflater.from(this);
			LinearLayout hsvItem = (LinearLayout) inflater.inflate(R.layout.gallery_item, null);			
			
			TextView name = (TextView)hsvItem.findViewById(R.id.gallery_item_title);
			name.setText(iName);
			
			ImageView image = (ImageView)hsvItem.findViewById(R.id.gallery_item_image);
			image.setBackgroundResource(R.drawable.piano_unselected);

			hsvItem.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					setImage(prevItemView, R.drawable.piano_unselected);
					setImage(view, R.drawable.piano);
					prevItemView = view;
					genList();
					genScrollView();
					if(playWhenScroll){
						int index = ((ViewGroup)view.getParent()).indexOfChild(view);
						playMusic(index);
					}
				}
			});

			melodyScroll.addView(hsvItem);
		}
	}	
	
	private void setImage(View view, int res){
		if(view == null)
			return;
		ImageView image = (ImageView)view.findViewById(R.id.gallery_item_image);
		image.setBackgroundResource(res);
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

	
	private void genScrollView() {
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

	/*@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Bundle bundle = data.getExtras();
		long labelPrice = bundle.getLong("labelPrice", -1);
		int pos = bundle.getInt("position", -1);
		// updateLabelPrice(pos, labelPrice);

	}*/

	private void updateLabelPrice(int pos, long labelPrice) {
		variations.get(pos).sellingPrice = labelPrice;
		// TODO update in server and xml
		genScrollView();
	}
/*
	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		

		Map<String, Object> map;
		for (int i = 0; i < 6; i++) {
			map = new HashMap<String, Object>();
			map.put("title", "Item" + i);
			map.put("img", R.drawable.piano);
			list.add(map);
			melodies.add("Variation"+Integer.toString(i)+".m4a");
		}
		return list;
	}*/
	
	
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
}
