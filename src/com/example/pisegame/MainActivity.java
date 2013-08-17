package com.example.pisegame;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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
    
// Partie MENU : ¨¤ voir comment l'int¨¦grer aux codes
    
//    public boolean onCreateOptionsMenu(Menu menu) { 
//	    MenuInflater inflater = getMenuInflater();
//	    inflater.inflate(R.menu.my_menu, menu);
//	    return true;
//	}
//	// faut faire en sorte que le menu soit lanc¨¦ au d¨¦marrage de l'appli
//	
//	public boolean onOptionsItemSelected(MenuItem item) { //ce qui se passe lors des clics
//	    switch (item.getItemId()) {
//	        case R.id.open:
//	        	// lancer le jeu
//	            return true;
//	        case R.id.save:
//	            // lancer sauvegarde
//	            return true;
//	        case R.id.how:
//	            // lancer manuel
//	            return true;
//	        default:
//	            return super.onOptionsItemSelected(item);
//	    }
//	}

    
    
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
  */  
}
