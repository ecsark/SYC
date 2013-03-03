package org.syc.android;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class ShopActivity extends Activity {

	private LinearLayout hsvLayout;
	private ArrayList<Integer> colors;
	private int coloridx;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop);
		
		initColors();
		coloridx = 0;
		
		hsvLayout = (LinearLayout) findViewById(R.id.hsv_layout);
		for(int i=0; i<18; ++i){
			LinearLayout column = new LinearLayout(this);
			column.setOrientation(LinearLayout.VERTICAL);
			column.setPadding(10, 10, 10, 10);
			
			
			
			LinearLayout padding = new LinearLayout(this);
			LinearLayout.LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			params.height = 20;
			padding.setLayoutParams(params);
			
			
			
			column.addView(newItemBlock("Track"+Integer.toString(i),20,getNextColor()));
			column.addView(padding);
			column.addView(newItemBlock("Track"+Integer.toString(i),30,getNextColor()));
			hsvLayout.addView(column);
			
		}
		
	}
	
	private int getNextColor(){
		int color = colors.get(coloridx);
		coloridx = (coloridx+1)%colors.size();
		return color;
	}
	
	private void initColors(){
		colors = new ArrayList<Integer>();
		colors.add(getResources().getColor(R.color.dark_green));
		colors.add(getResources().getColor(R.color.dark_blue));
		colors.add(getResources().getColor(R.color.purple));
		colors.add(getResources().getColor(R.color.light_green));
		colors.add(getResources().getColor(R.color.light_blue));
		colors.add(getResources().getColor(R.color.grey));
		colors.add(getResources().getColor(R.color.red));
		
	}

	private ViewGroup newItemBlock(String name, int price, int color){
		LayoutInflater inflater = LayoutInflater.from(this);
		ViewGroup item = (ViewGroup)inflater.inflate(R.layout.block_button,null);
		TextView itemName = (TextView)item.findViewById(R.id.itemName);
		itemName.setText(name);
		TextView itemPrice = (TextView)item.findViewById(R.id.itemPrice);
		itemPrice.setText("$"+Integer.toString(price));
		GradientDrawable background = (GradientDrawable) item.getBackground();
		background.setColor(0xcf000000+ color);
		
		item.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View view) {
				onItemClick(view);
				
			}
		});
		return item;
	}
	
	public void onItemClick(View view){
		//PriceDialog pd = new PriceDialog(this);
		String name = ((TextView) view.findViewById(R.id.itemName)).getText().toString();
		String priceLabel = ((TextView) view.findViewById(R.id.itemPrice)).getText().toString();
		priceLabel = priceLabel.substring(1, priceLabel.length());
		GradientDrawable background = (GradientDrawable) view.getBackground();
		//int bgColor = background
		int price = Integer.parseInt(priceLabel);		
		
		Intent i = new Intent();
		i.setClass(this, PlayScrollActivity.class);
		i.putExtra("itemName", name);
		i.putExtra("itemPrice", price);
		startActivityForResult(i, 123);
		overridePendingTransition(R.anim.zoom_enter,R.anim.zoom_exit);	
		
		//pd.setItem(name, price, price, 0xaf000000+Color.RED);
		//pd.show();
	}

}
