package org.syc.android;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class ShopActivity extends Activity {

	private LinearLayout hsvLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop);
		
		hsvLayout = (LinearLayout) findViewById(R.id.hsv_layout);
		for(int i=0; i<8; ++i){
			LinearLayout column = new LinearLayout(this);
			column.setOrientation(LinearLayout.VERTICAL);
			column.setPadding(10, 10, 10, 10);
			
			
			
			LinearLayout padding = new LinearLayout(this);
			LinearLayout.LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			params.height = 20;
			padding.setLayoutParams(params);
			
			
			
			column.addView(newItemBlock("Track"+Integer.toString(i),20,Color.GRAY));
			column.addView(padding);
			column.addView(newItemBlock("Track"+Integer.toString(i),30,Color.RED));
			hsvLayout.addView(column);
			
		}
		
	}

	private ViewGroup newItemBlock(String name, int price, int color){
		LayoutInflater inflater = LayoutInflater.from(this);
		ViewGroup item = (ViewGroup)inflater.inflate(R.layout.block_button,null);
		TextView itemName = (TextView)item.findViewById(R.id.itemName);
		itemName.setText(name);
		TextView itemPrice = (TextView)item.findViewById(R.id.itemPrice);
		itemPrice.setText("$"+Integer.toString(price));
		GradientDrawable background = (GradientDrawable) item.getBackground();
		background.setColor(0xaf000000+ color);
		
		item.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View view) {
				onItemClick(view);
				
			}
		});
		return item;
	}
	
	public void onItemClick(View view){
		PriceDialog pd = new PriceDialog(this);
		String name = ((TextView) view.findViewById(R.id.itemName)).getText().toString();
		String priceLabel = ((TextView) view.findViewById(R.id.itemPrice)).getText().toString();
		priceLabel = priceLabel.substring(1, priceLabel.length());
		GradientDrawable background = (GradientDrawable) view.getBackground();
		//int bgColor = background
		int price = Integer.parseInt(priceLabel);
		
		pd.setItem(name, price, price, 0xaf000000+Color.RED);
		pd.show();
	}
}
