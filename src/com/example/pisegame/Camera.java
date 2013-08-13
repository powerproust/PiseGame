package com.example.pisegame;

public class Camera {
	
	Vector2 pos;
	Vector2 deltaPos;
	private float speed;
	private float offSet;
	
	public Camera(Vector2 p, float s, float off) {
		pos = p;
		deltaPos = p;
		speed = s;
		offSet = off;
	}

	public void MoveAlongY(float dt) {
		pos.y = pos.y + (dt * speed);
		deltaPos.Set(0, (dt*speed));
	}

	public void MoveAlongX(float dt) {
		pos.x = pos.x + (dt * speed);
	}
	
	public void UpdateCam(PhysicObject p)
	{	
		pos.Set(0, pos.y - (p.pos.y - offSet));
	}

	public void ResetDeltaPos()
	{
		deltaPos.Set(0, 0);
	}
	public void SetSpeed(float sp) {
		speed = sp;
	}
}
