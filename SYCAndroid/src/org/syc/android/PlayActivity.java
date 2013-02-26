package org.syc.android;

import java.io.File;
import java.io.IOException;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PlayActivity extends Activity {

	private MediaPlayer mp;
	private Button btn_pause;
	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);
		
		Intent i = getIntent();
		int pos = i.getIntExtra("pos", 0);
		TextView tv = (TextView)findViewById(R.id.time);
		tv.setText(Integer.toString(pos));
		String filepath = Environment.getExternalStorageDirectory().getPath() +"/"+"Bach.mid";
		mp = new MediaPlayer();
		btn_pause = (Button) findViewById(R.id.button_pause);
		btn_pause.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				mp.pause();
				int position =  mp.getCurrentPosition();
				returnPlay(position);
			}
		});
		try {
			mp.setDataSource(filepath);
			mp.prepare();
			mp.seekTo(pos);
			mp.start();
		} catch (Exception e) {
			System.err.println("play failed!");
			e.printStackTrace();
		}
	}
	
	@Override
	public void onBackPressed() {
	
	}

	public void returnPlay(int position){
		Intent i = new Intent();  
		//HomeActivity.position = position;
		i.putExtra("playPosition", position);  
		setResult(2, i);  
		finish();	
	}

}
