package com.example.pisegame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

public class MainMenu extends Activity {
	Button b1;
	Button b2;
	Button b3;
	Button b4;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu); 
        Button b1 = (Button)findViewById(R.id.Button01);
        Button b2 = (Button)findViewById(R.id.Button02);
        Button b3 = (Button)findViewById(R.id.Button03);
        Button b4 = (Button)findViewById(R.id.Button04);
        
        // linking menu buttons to different views
        b1.setOnClickListener(new Button.OnClickListener() {  
        	public void onClick(View v){
        		Context context = getApplicationContext();
        		int duration = Toast.LENGTH_SHORT;
        		Toast.makeText(context, "Button1", duration).show();
            	}
        	});
        b2.setOnClickListener(new Button.OnClickListener() {  
        	public void onClick(View v){
        		Context context = getApplicationContext();
        		int duration = Toast.LENGTH_SHORT;
        		Toast.makeText(context, "Button2", duration).show();
            	}
        	});
        b3.setOnClickListener(new Button.OnClickListener() {  
        	public void onClick(View v){
        		Context context = getApplicationContext();
        		int duration = Toast.LENGTH_SHORT;
        		Toast.makeText(context, "Button3", duration).show();
            	}
        	});
        b4.setOnClickListener(new Button.OnClickListener() {  // pour avoir l'explication du jeu
        	public void onClick(View v){
        		Intent intent = new Intent(MainMenu.this, Howto.class);
        		startActivity(intent);
            	}
        	});
    }
}
