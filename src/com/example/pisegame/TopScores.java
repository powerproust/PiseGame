package com.example.pisegame;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TopScores extends Activity {
	public static final String topscore = "MyPreferencesFile";
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topscores); 
        TextView date1=(TextView)findViewById(R.id.date1);
        TextView score1=(TextView)findViewById(R.id.score1);
        TextView date2=(TextView)findViewById(R.id.date2);
        TextView score2=(TextView)findViewById(R.id.score2);
        TextView date3=(TextView)findViewById(R.id.date3);
        TextView score3=(TextView)findViewById(R.id.score3);
        TextView date4=(TextView)findViewById(R.id.date4);
        TextView score4=(TextView)findViewById(R.id.score4);
        TextView date5=(TextView)findViewById(R.id.date5);
        TextView score5=(TextView)findViewById(R.id.score5);
        
        SharedPreferences settings=getSharedPreferences(topscore, 0);
        
        
        score1.setText("Score 1: "+settings.getString("score1", "N/A"));
        date1.setText(settings.getString("date1", "N/A"));
        score2.setText("Score 2: "+settings.getString("score2", "N/A"));
        date2.setText(settings.getString("date2", "N/A"));
        score3.setText("Score 3: "+settings.getString("score3", "N/A"));
        date3.setText(settings.getString("date3", "N/A"));
        score4.setText("Score 4: "+settings.getString("score4", "N/A"));
        date4.setText(settings.getString("date4", "N/A"));
        score5.setText("Score 5: "+settings.getString("score5", "N/A"));
        date5.setText(settings.getString("date5", "N/A"));
        
        
        Button okback = (Button)findViewById(R.id.button1);
        okback.setOnClickListener(new Button.OnClickListener() {  
        	public void onClick(View v){
        		Intent intent = new Intent(TopScores.this, MainMenu.class);
        		startActivity(intent);
            	}
        	});
        
	}
}
