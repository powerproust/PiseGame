package com.example.pisegame;

import com.example.pisegame.DoodleSurfaceView.DoodleThread;

import android.R.drawable;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class GameObject {
	Vector2 pos;
	Bitmap picture;
	private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	
	CollisionIdentity cI;
	private final String TAG = DoodleThread.class.getSimpleName();
	String name;
	
	public GameObject (Vector2 po, Bitmap p, String n) {
		picture = p;
		pos = po;

		paint.setAlpha(255);
		Vector2 max = new Vector2(pos.x + picture.getWidth(), pos.y + picture.getHeight());
		AABB aabb;
		aabb = new AABB(pos, max, picture.getWidth()/2, picture.getHeight()/2);
		name = n;
		cI = new CollisionIdentity(aabb);
	}
	
	
	public void UpdateCam(Vector2 cam)
	{
		pos = pos.Add(cam);
	}
	
	public void draw(Canvas c, Vector2 camPos, float heightScreen) {
		
		if(cI.aabb.max.y >= camPos.y && cI.aabb.min.y <= camPos.y + heightScreen) {
			//c.save();
			//paint.setColor(Color.RED);
	        //paint.setStyle(Style.FILL);
			//for(int i = 0; i < cI.vectCirc.size() ; i++) {
				//c.drawCircle(cI.vectCirc.get(i).pos.x, cI.vectCirc.get(i).pos.y, cI.vectCirc.get(i).radius, paint);
			//}
			
			c.drawBitmap(picture, pos.x, pos.y, paint);
			//c.restore();
		}
		
	}
	
	public void UpdateCollisionIdentity() {
		cI.aabb.max.x = pos.x + picture.getWidth();
		cI.aabb.max.y = pos.y + picture.getHeight();
		cI.aabb.min.x = pos.x;
		cI.aabb.min.y = pos.y;
		
		for(int i = 0; i < cI.vectCirc.size(); i++) {
			cI.vectCirc.get(i).pos.x = pos.x + cI.vectCirc.get(i).offSetX;
			cI.vectCirc.get(i).pos.y = pos.y + cI.vectCirc.get(i).offSetY;
		}
		
	}
	
	public void AddCircle(Circle c)
	{
		cI.AddCircle(c);
	}
	
	
	public String toString() {
		return name;
	}
}
