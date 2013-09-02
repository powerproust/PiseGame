package com.example.pisegame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameOver extends Activity {
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
//		check if score falls into the top 5 list
//        if score in top 5 then : 
//        setContentView(R.layout.gameover_goodscore);
//	      else :
//        setContentView(R.layout.gameover_badscore);
        
        setContentView(R.layout.gameover_badscore);
        Button okback = (Button)findViewById(R.id.button1);
        okback.setOnClickListener(new Button.OnClickListener() {  
        	public void onClick(View v){
        		Intent intent = new Intent(GameOver.this, MainMenu.class);
        		startActivity(intent);
            	}
        	});
	}
}
