package org.syc.android;


import java.io.IOException;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.text.Layout;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.PorterDuff;

public class HomeActivity extends Activity {

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
	
		/*PriceDialog pd = new PriceDialog(this);
		pd.setItem("Track212", 210, 120,0xffffff);
		pd.show();*/
		
		//startActivity(new Intent(this,CommenceActivity.class));
		startActivity(new Intent(this,ShopActivity.class));
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
		};
		exitDialog.setTitle("Exit?");
		exitDialog.show();
	}

}
