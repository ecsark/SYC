package org.syc.android;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class LoginActivity extends Activity {

	private EditText username, password;
	private Button submitbtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		submitbtn = (Button) findViewById(R.id.submit);
		submitbtn.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				if(submit()==true){
					go2Next();
				}
			}
		});
	}
	
	private boolean submit(){
		String usrn = username.getText().toString();
		String pswd = password.getText().toString();
		//TODO submit to server
		return true;
	}

	private void go2Next(){
		startActivity(new Intent(this,LocalActivity.class));
		overridePendingTransition(R.anim.slide2,R.anim.slide1);	
		this.finish();
	}
	
	 
	/*private void addTouchEffect(){
		RelativeLayout rl = (RelativeLayout) findViewById(R.layout.activity_commence);
		rl.setOnTouchListener(new View.OnTouchListener() {

	        @Override
	        public boolean onTouch(View v, MotionEvent event) {
	            float x = event.getX();
	            float y = event.getY();
	            return super.onTouchEvent();
	        }


	    });
	}*/
	

}
