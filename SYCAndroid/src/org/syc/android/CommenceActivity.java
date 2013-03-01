package org.syc.android;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;

public class CommenceActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_commence);
		RelativeLayout screen = (RelativeLayout) findViewById(R.id.screen);
		screen.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(CommenceActivity.this,LoginActivity.class));
				//startActivity(new Intent(CommenceActivity.this,LocalActivity.class));

				overridePendingTransition(R.anim.slide2,R.anim.slide1);	
				CommenceActivity.this.finish();				
			}
		});
	}
	

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.commence, menu);
		return true;
	}*/

}
