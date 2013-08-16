package com.example.pisegame;
import com.example.pisegame.R;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainMenu extends Activity {
	public boolean onCreateOptionsMenu(Menu menu) { 
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.my_menu, menu);
	    return true;
	}
	// faut faire en sorte que le menu soit lanc¨¦ au d¨¦marrage de l'appli
	// donc devrais-je faire le inflate menu dans MainActivity plutot?
	
	public boolean onOptionsItemSelected(MenuItem item) { //ce qui se passe lors des clics
	    switch (item.getItemId()) {
	        case R.id.open:
	            // Ok mais comment appeler le lancement du jeu?
	            return true;
	        case R.id.save:
	            // lancer sauvegarde
	            return true;
	        case R.id.how:
	            // lancer manuel
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
}
