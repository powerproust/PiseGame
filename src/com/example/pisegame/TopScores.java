package com.example.pisegame;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TopScores extends Activity {
	public static final String topscore = "MyPreferencesFile"; // la constance indispensable
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topscores); 
        
        // declarer les TextView pour afficher les top scores
        TextView score1=(TextView)findViewById(R.id.score1);
        TextView score2=(TextView)findViewById(R.id.score2);
        TextView score3=(TextView)findViewById(R.id.score3);
        TextView score4=(TextView)findViewById(R.id.score4);
        TextView score5=(TextView)findViewById(R.id.score5);
        
        // on recupere les 5 top scores de Shared
        SharedPreferences settings=getSharedPreferences(topscore, 0);
        score1.setText("Score 1: "+settings.getInt("score1", 0));
        score2.setText("Score 2: "+settings.getInt("score2", 0));
        score3.setText("Score 3: "+settings.getInt("score3", 0));
        score4.setText("Score 4: "+settings.getInt("score4", 0));
        score5.setText("Score 5: "+settings.getInt("score5", 0));
        
        Button okback = (Button)findViewById(R.id.button1);
        okback.setOnClickListener(new Button.OnClickListener() {  
        	public void onClick(View v){
        		Intent intent = new Intent(TopScores.this, MainMenu.class);
        		startActivity(intent);
        		finish();
            	}
        	});
        
	}
}
