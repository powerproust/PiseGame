package com.example.pisegame;

public class AABB {
	Vector2 min;
	Vector2 max;

	public AABB() {

	}

	public static boolean AABBvsAABB (AABB a, AABB b){
		
		if(a.max.x < b.min.x || a.min.x > a.max.x) {
			return false;
		}
		if(a.max.y < b.min.y || a.min.y > b.max.y) {
			return false;
		}
		return true;
		
	}
	
}
