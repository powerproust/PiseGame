package com.example.pisegame;

public class Circle {

	float radius;
	Vector2 pos;
	float offSetX;
	float offSetY;
	
	public Circle(Vector2 p, float r, float offX, float offY)
	{
		pos = new Vector2(p.x, p.y);
		radius = r;
		offSetX = offX;
		offSetY = offY;
	}
	
	
}
