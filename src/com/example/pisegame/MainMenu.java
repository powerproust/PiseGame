package com.example.pisegame;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

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
        b1.setOnClickListener(new Button.OnClickListener() {  
        public void onClick(View v){
                System.out.println("hi--1");
            }
        });
        b2.setOnClickListener(new Button.OnClickListener() {  
        public void onClick(View v){
                    System.out.println("hi--2");
            }
        });
        b3.setOnClickListener(new Button.OnClickListener() {  
        public void onClick(View v){
                    System.out.println("hi--3");
            }
        });
        b4.setOnClickListener(new Button.OnClickListener() {  
        public void onClick(View v){
                    System.out.println("hi--4");
            }
        });
    }
	

}
