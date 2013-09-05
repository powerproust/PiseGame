package com.example.pisegame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Howto extends Activity{
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.howto); 
        Button okback = (Button)findViewById(R.id.dialogButtonOK);
        okback.setOnClickListener(new Button.OnClickListener() {  
        	public void onClick(View v){
        		Intent intent = new Intent(Howto.this, MainMenu.class);
        		startActivity(intent);
        		finish();
            	}
        	});
	}
	
	
}
