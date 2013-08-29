package com.example.pisegame;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainMenu extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        
    }
	
	
	public void rungame(View view) { 
		   //protected void onCreate(Bundle savedInstanceState) {
	       setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	       requestWindowFeature(Window.FEATURE_NO_TITLE);
	       getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
	       getWindow().setFlags(
	       	    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
	       	    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
	       int rotation = getWindowManager().getDefaultDisplay().getRotation();       
	       setContentView(new DoodleSurfaceView(this, rotation));
	       //Ball ball = new Ball(this);
	       //setContentView(ball);
	   }
}
