package com.example.pisegame;

import android.graphics.Bitmap;

public class PhysicObject extends GameObject {
	Vector2 v;
	Vector2 forces;
	float mass;
	
	public PhysicObject(Vector2 po, Bitmap p, String n) {
		super(po, p, n);
		forces = new Vector2(0,0);
		v = new Vector2(0, 0);
		mass = 1;
	}

}
