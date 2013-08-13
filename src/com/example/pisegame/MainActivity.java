package com.example.pisegame;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
        /*
        String hello = getResources().getString(R.string.hello_world);
        // Au lieu d'afficher "Hello World!" on va afficher "Hello les Zéros !"
        hello = hello.replace("world", "les Zéros ");
         
        TextView text = new TextView(this);
        text.setText(hello);
        //setContentView(R.layout.activity_main);
        setContentView(text);
        */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(
        	    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
        	    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        //Ball ball = new Ball(this);
        //setContentView(ball);
        setContentView(new DoodleSurfaceView(this, rotation));
        
    }
    

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
  */  
}
