package org.syc.android;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Vibrator;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PlayActivity extends Activity {

	private Button btn_pause;
	private TextView time;
	private Context context;
	
	private String itemName;
	private int itemPrice, labelPrice;

	private boolean isPlaying;

	private boolean mIsBound = false;
	private MusicService mService;
	private ServiceConnection svConn = new ServiceConnection() {

		public void onServiceConnected(ComponentName name, IBinder binder) {
			mService = ((MusicService.MusicBinder) binder).getService();
		}

		public void onServiceDisconnected(ComponentName name) {
			mService = null;
		}
	};

	void doBindService() {
		bindService(new Intent(this, MusicService.class), svConn,
				Context.BIND_AUTO_CREATE);
		mIsBound = true;
	}

	void doUnbindService() {
		if (mIsBound) {
			unbindService(svConn);
			mIsBound = false;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);

		isPlaying = true;
		context = PlayActivity.this;
		
		Intent i = getIntent();
		itemName = i.getStringExtra("itemName");
		itemPrice = i.getIntExtra("itemPrice", 5);
		
		doBindService();

		btn_pause = (Button) findViewById(R.id.button_pause);
		btn_pause.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isPlaying) {
					mService.pauseMusic();
					isPlaying = false;
					showPricingDialog();
				} else {
					mService.playMusic();
					isPlaying = true;
				}
			}
		});
	}
	
	private void showPricingDialog(){
		PriceDialog pd = new PriceDialog(this);
		pd.setItem(itemName, itemPrice, itemPrice, 0xaf000000+Color.RED);
		pd.show();
	}
	
	@Override
	public void onBackPressed() {

		doUnbindService();
		Intent i =new Intent();
        i.putExtra("labelPrice", itemPrice);
        setResult(10, i);
		this.finish();
	}
	
	



}
