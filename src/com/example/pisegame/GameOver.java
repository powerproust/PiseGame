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
//	public static final String topscore = "MyPreferencesFile";
//	boolean save=false;
//	int i=0;
//	String date_temp="";
//	int score_temp=0;
//	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        // retrieve data, compare the last score to the existing scores
//        ScoreStruct[] allscores=new ScoreStruct[5];
//        SharedPreferences settings=getSharedPreferences(topscore, 0);
//        allscores[0].sc=Integer.parseInt(settings.getString("score1", "0"));
//        allscores[0].date=settings.getString("date1", "N/A");
//        allscores[1].sc=Integer.parseInt(settings.getString("score2", "0"));
//        allscores[1].date=settings.getString("date2", "N/A");
//        allscores[2].sc=Integer.parseInt(settings.getString("score3", "0"));
//        allscores[2].date=settings.getString("date3", "N/A");
//        allscores[3].sc=Integer.parseInt(settings.getString("score4", "0"));
//        allscores[3].date=settings.getString("date4", "N/A");
//        allscores[4].sc=Integer.parseInt(settings.getString("score5", "0"));
//        allscores[4].date=settings.getString("date5", "N/A");
//        
//        // get current day :
//        Date date = new Date();
//        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String dd = formatter.format(date);
//        
//        // check if we need to save the latest score
//        // only compare with the last entry because it is automatically put into descending order
//        if (allscores[4].sc<=finalscore.fscore){
//        	save=true;
//        	allscores[4].sc=finalscore.fscore;
//        	allscores[4].date=dd;
//        }
//               
//        // if allscores is updated, put the 5 updated scores into descending order :
//        if (save){
//        	for (i=4;i>0;i--){
//        		if (allscores[i].sc>allscores[i-1].sc){
//        			score_temp=allscores[i-1].sc;
//        			date_temp=allscores[i-1].date;
//        			allscores[i-1].sc=allscores[i].sc;
//        			allscores[i-1].date=allscores[i].date;
//        			allscores[i].sc=score_temp;
//        			allscores[i].date=date_temp;
//        		}
//        	}
//        	// after sorting the scores, save them with SharedPreferences
//            SharedPreferences.Editor editor=settings.edit();
//            editor.putString("date1", allscores[0].date);
//            editor.putInt("score1", allscores[0].sc);
//            editor.putString("date2", allscores[1].date);
//            editor.putInt("score2", allscores[1].sc);
//            editor.putString("date3", allscores[2].date);
//            editor.putInt("score3", allscores[2].sc);
//            editor.putString("date4", allscores[3].date);
//            editor.putInt("score4", allscores[3].sc);
//            editor.putString("date5", allscores[4].date);
//            editor.putInt("score5", allscores[4].sc);
//            editor.commit();
//        	
//            setContentView(R.layout.gameover_goodscore);
//            Button okback = (Button)findViewById(R.id.button1);
//            okback.setOnClickListener(new Button.OnClickListener() {  
//            	public void onClick(View v){
//            		Intent intent = new Intent(GameOver.this, MainMenu.class);
//            		startActivity(intent);
//                	}
//            	});
//        }
//        else{
        	setContentView(R.layout.gameover_badscore);
            Button okback = (Button)findViewById(R.id.button1);
            okback.setOnClickListener(new Button.OnClickListener() {  
            	public void onClick(View v){
            		Intent intent = new Intent(GameOver.this, MainMenu.class);
            		startActivity(intent);
                	}
            	});
//        }
	}
}

















//while ((save==false)&&(i>0)){
//if (finalscore.fscore>allscores[i].sc){
//	allscores[i].sc=finalscore.fscore;
//	allscores[i].date=dd;
//	save=true;
//}
//i--;
//}
