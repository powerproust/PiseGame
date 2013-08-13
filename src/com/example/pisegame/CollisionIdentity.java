package com.example.pisegame;

import java.util.Vector;

import com.example.pisegame.DoodleSurfaceView.DoodleThread;

import android.util.Log;

public class CollisionIdentity {
	AABB aabb;
	Vector<Circle> vectCirc;
	private final String TAG = DoodleThread.class.getSimpleName();
	public CollisionIdentity(AABB aab) {
		aabb = aab;
		vectCirc = new Vector<Circle>();
	}

	public void AddCircle(Circle c) {
		vectCirc.add(c);
	}
	
	public boolean AABBvsAABB(CollisionIdentity b) {

		
		if(aabb.max.x < b.aabb.min.x || aabb.min.x > b.aabb.max.x) {
			//Log.d(TAG, " HERE MOTHERFUCKER 1!");
			return false;
		}
		
		if(aabb.max.y < b.aabb.min.y || aabb.min.y > b.aabb.max.y) {
			//Log.d(TAG, " HERE MOTHERFUCKER 2!");
			return false;
		}
		
		for(int i = 0; i < vectCirc.size(); i ++)
		{
			//Log.d(TAG, " HERE MOTHERFUCKER 3!");
			if(CirclevsCircle(vectCirc.get(i), b.vectCirc.get(0))) {
				return true;
			}
		}
		
		return false;
		
		/*
		 * if(this.max.x < b.min.x || this.min.x > b.max.x) { return 0; } else
		 * if ((this.max.x > b.min.x || this.min.x < b.max.x) && (this.max.y >
		 * b.min.y && b.max.y > this.min.y)) {
		 * 
		 * if((this.max.y - b.min.y > this.max.x - b.min.x)) { /* float a =
		 * this.max.y - b.min.y; float d = this.max.x - b.min.x; float c =
		 * b.max.x - this.min.x; Log.d(TAG, a + " " + d + " " + c);
		 * 
		 * return 1;
		 * 
		 * } else if (this.max.y - b.min.y > b.max.x - this.min.x){ return 2; }
		 * else { return 3; }
		 * 
		 * 
		 * }
		 * 
		 * 
		 * if(this.max.y < b.min.y || this.min.y > b.max.y) { return 0; } else
		 * if (this.max.y > b.min.y || this.min.y < b.max.y){ return 2; }
		 */
		

	}

	public boolean CirclevsCircle(Circle c1, Circle c2) {
		float r = c1.radius + c2.radius;
		Vector2 circleToCircle = c2.pos.Substract(c1.pos);
		double mag = Vector2.GetMagnitude(circleToCircle);
		
		//Log.d(TAG, "R" + r + " mag : " + mag);
		if (mag < r) {
			return true;
		} else {
			return false;
		}
	}

}
