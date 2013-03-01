package org.syc.android;

import java.util.ArrayList;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class PlayScrollActivity extends Activity {

	ViewPager pager;
	ArrayList<View> viewList;
	TextView indicator, nameText;
	Button billboard;
	SeekBar seekBar;
	String itemName;
	long itemPrice, labelPrice;
	int color;
	String filepath = Environment.getExternalStorageDirectory().getPath() + "/"
			+ "Bach.mid";

	MediaPlayer player;
	Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play_scroll);

		viewList = new ArrayList<View>();
		indicator = (TextView) findViewById(R.id.indicator);
		pager = (ViewPager) findViewById(R.id.viewpagerLayout);
		pager.setAdapter(new MPagerAdapter());
		player = new MediaPlayer();

		LayoutInflater mInflater = getLayoutInflater();
		for (int i = 0; i < 5; ++i) {
			View view = mInflater.inflate(R.layout.activity_play, null);
			viewList.add(view);
		}

		indicator.setText("1 of " + viewList.size());
		renewView(0); // start with first

		pager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int index) {
				renewView(index);
			}

			@Override
			public void onPageScrolled(int index, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	void renewView(int index) {
		try {
			itemName = "Track" + Integer.toString(index + 1);
			itemPrice = 20;
			labelPrice = 40;

			player.reset();
			player.setDataSource(filepath);
			player.prepare();
			player.setLooping(false);

			indicator.setText(Integer.toString(index + 1) + " of "
					+ Integer.toString(viewList.size()));

			nameText = (TextView) viewList.get(index).findViewById(
					R.id.itemName);
			nameText.setText(itemName);

			seekBar = (SeekBar) viewList.get(index).findViewById(R.id.seekBar);
			seekBar.setMax(player.getDuration());
			seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
				@Override
				public void onStopTrackingTouch(SeekBar arg0) {
					player.seekTo(seekBar.getProgress());
					player.start();
				}

				@Override
				public void onStartTrackingTouch(SeekBar arg0) {
					player.pause();
				}

				@Override
				public void onProgressChanged(SeekBar seekBar, int progress,
						boolean fromUser) {
				}
			});

			billboard = (Button) viewList.get(index).findViewById(
					R.id.btn_billboard);
			billboard.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (player.isPlaying()) {
						player.pause();
						showPricingDialog();
					} else {
						player.start();
					}
				}
			});

			handler.post(start);

		} catch (Exception e) {
			Toast.makeText(PlayScrollActivity.this,
					"Music Service Failed to start", Toast.LENGTH_LONG).show();
			e.printStackTrace();
			onBackPressed();
		}
	}

	void showPricingDialog() {
		final PriceDialog pd = new PriceDialog(this);
		pd.setItem(itemName, labelPrice, itemPrice, 0xaf000000 + color);
		pd.done_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				labelPrice = pd.getLabelPrice();
				player.start();
				pd.dismiss();
			}
		});
		pd.show();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		handler.removeCallbacks(start);
		handler.removeCallbacks(updatesb);
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
		Intent intent = new Intent();
		// intent.putExtra("labelPrice", labelPrice);
		// intent.putExtra("position", pos);
		setResult(10, intent);
		PlayScrollActivity.this.finish();
	}

	Runnable start = new Runnable() {
		@Override
		public void run() {
			player.start();
			handler.post(updatesb);
		}
	};

	Runnable updatesb = new Runnable() {
		@Override
		public void run() {
			seekBar.setProgress(player.getCurrentPosition());
			handler.postDelayed(updatesb, 100);
		}
	};

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
