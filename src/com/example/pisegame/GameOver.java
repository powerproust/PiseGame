package com.example.pisegame;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOver extends Activity {
public static final String topscore = "MyPreferencesFile"; // la constante indispensable pour utiliser SharedPreferences

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Integer value = 0;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getInt("SCORE"); // this is the score of the last player
            //System.out.println(value);
        }
        
        SharedPreferences settings = getSharedPreferences(topscore, 0);
        ArrayList<Integer> unsortList = new ArrayList<Integer>();
        // put scores saved in SharedPreferences into the ArrayList
        unsortList.add(settings.getInt("score1", 0));
        unsortList.add(settings.getInt("score2", 0));
        unsortList.add(settings.getInt("score3", 0));
        unsortList.add(settings.getInt("score4", 0));
        unsortList.add(settings.getInt("score5", 0));
        Collections.sort(unsortList);
        //System.out.println("********** Value after sorting **************");
        
        if(value > unsortList.get(0)) // compare last score to the smallest value in the Arraylist
        {
        	unsortList.remove(0);
        	unsortList.add(value);
        	System.out.println("ENTER VALUE !");
            Collections.sort(unsortList);
            Collections.reverse(unsortList);
            
//            for (int i = 0; i < unsortList.size(); i++) {
//                System.out.println(unsortList.get(i));
//            }
            
            // on resauvegarde les scores maintenant dans l'ordre
            SharedPreferences.Editor editor = settings.edit();
            for(int i = 0; i <= 4 ;i++) {
            	editor.putInt("score" + (i+1), unsortList.get(i));
            }
            editor.commit();
            // ensuite, afficher gameover_goodscore.xml pour annoncer le GameOver
            setContentView(R.layout.gameover_goodscore);
            TextView yrsc=(TextView)findViewById(R.id.yrscore);
            yrsc.setText("Your score is: "+value);
            Button okback = (Button)findViewById(R.id.button1);
            okback.setOnClickListener(new Button.OnClickListener() {  
            	public void onClick(View v){
	          		Intent intent = new Intent(GameOver.this, MainMenu.class);
	          		startActivity(intent);
	          		finish();
	              	}
          		});
        }
        else{
        	Collections.reverse(unsortList);
        	// ensuite, afficher gameover_badscore.xml pour annoncer le GameOver
        	setContentView(R.layout.gameover_badscore);
            Button okback = (Button)findViewById(R.id.button1);
            okback.setOnClickListener(new Button.OnClickListener() {  
            	public void onClick(View v){
            		Intent intent = new Intent(GameOver.this, MainMenu.class);
            		startActivity(intent);
            		finish();
                	}
            	});
        }
	}
}