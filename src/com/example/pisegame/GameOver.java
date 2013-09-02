package com.example.pisegame;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameOver extends Activity {
	public static final String topscore = "topscore";

	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences scores = getApplicationContext().getSharedPreferences("prefname", MODE_PRIVATE);
        
        // Add scores + date:
        Date date = new Date();
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dd = formatter.format(date);
        Editor editor = scores.edit();
        editor.putInt("score1", finalscore.fscore).commit();
        editor.putString("date1", dd).commit();
        showPreferences("score1");
        showPreferences("date1");
        
    	
        
//    if score in top 5 then : 
//        setContentView(R.layout.gameover_goodscore);
//        Button okback = (Button)findViewById(R.id.button1);
//        okback.setOnClickListener(new Button.OnClickListener() {  
//        	public void onClick(View v){
        	
//        		Intent intent = new Intent(GameOver.this, MainMenu.class);
//        		startActivity(intent);
//            	}
//        	});
        
        
//	  else :
        setContentView(R.layout.gameover_badscore);
        Button okback = (Button)findViewById(R.id.button1);
        okback.setOnClickListener(new Button.OnClickListener() {  
        	public void onClick(View v){
        		Intent intent = new Intent(GameOver.this, MainMenu.class);
        		startActivity(intent);
            	}
        	});
	}
	private void showPreferences(String key){
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        String savedPref = sharedPreferences.getString(key, "");
        System.out.println("savedPref: "+savedPref);
    }
}
