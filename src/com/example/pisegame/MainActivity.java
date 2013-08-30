//package com.example.pisegame;
//
//import android.app.ActionBar.LayoutParams;
//import android.app.Activity;
//import android.content.pm.ActivityInfo;
//import android.hardware.Sensor;
//import android.hardware.SensorManager;
//import android.os.Bundle;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//public class MainActivity extends Activity {
//	
//	@Override
//  public void onCreate(Bundle savedInstanceState) {
//       setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//       requestWindowFeature(Window.FEATURE_NO_TITLE);
//       getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//       getWindow().setFlags(
//       	    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
//       	    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
//       int rotation = getWindowManager().getDefaultDisplay().getRotation();       
//       setContentView(new DoodleSurfaceView(this, rotation));
//       //Ball ball = new Ball(this);
//       //setContentView(ball);
//   }
//	
//}



