package com.example.pisegame;

import android.util.Log;

public class Vector2 {
	float x;
	float y;
	//double mag;

	public Vector2(float x1, float y1) {
		x = x1;
		y = y1;
	}

	public void Set(double d, double e) {
		this.x = (float)d;
		this.y = (float)e;
	}

	public Vector2 Add(Vector2 o) {
		Vector2 newVec = new Vector2(this.x + o.x, this.y + o.y);
		return newVec;
	}

	public Vector2 Substract(Vector2 o) {
		Vector2 newVec = new Vector2(this.x - o.x, this.y - o.y);
		return newVec;
	}

	public Vector2 MultByScalar(float num) {
		Vector2 newVec = new Vector2(this.x * num, this.y * num);
		return newVec;
	}

	public float GetAngleWithMe(Vector2 o) {
		float angle = (float) ((Math.acos(Vector2.GetMagnitude(this)
				* Vector2.GetMagnitude(o))) * 180 / Math.PI);
		return angle;
	}

	public float DotProductWithMe(Vector2 o) {
		float dot = (this.x * o.x) + (this.y * o.y);
		return dot;
	}

	public static double GetMagnitude(Vector2 o) {
		double mag = Math.sqrt((o.x * o.x) + (o.y * o.y));
		return mag;
	}

	public static Vector2 Normalized(Vector2 o) {
		double magVec = Vector2.GetMagnitude(o);
		Vector2 newVec = new Vector2((float) (o.x / magVec),
				(float) (o.y / magVec));
		return newVec;
	}

	public Vector2 GetNormalWithMe(Vector2 o) {
		Vector2 newVec = new Vector2(-o.y, o.x);
		return newVec;
	}
	
	public boolean Null()
	{
		if(this.x != 0 || this.y != 0) {
			return false;
		} else {
			return true;
		}
	}
	/*
	 * public Vector2 MultWithMe(float f) { Vector2 newVec = new Vector2(this.x
	 * * f, this.y * f); return newVec; }
	 */
}
