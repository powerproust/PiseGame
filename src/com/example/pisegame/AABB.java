package com.example.pisegame;

import com.example.pisegame.DoodleSurfaceView.DoodleThread;

import android.util.Log;

public class AABB {
	Vector2 min;
	Vector2 max;
	float halfX;
	float halfY;
	
	private final String TAG = DoodleThread.class.getSimpleName();

	public AABB(Vector2 mi, Vector2 ma, float hx, float hy) {
		min = mi;
		max = ma;
		halfX = hx;
		halfY = hy;
	}
	
}
