package com.example.pisegame;

import java.util.Random;
import java.util.Vector;

import com.example.pisegame.DoodleSurfaceView.DoodleThread;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

public class GameEngine {

	Vector<GameObject> vectGameObj;
	Vector<GameObject> toDeleteGameObj;
	// Vector<PhysicObject> vectPhysicObj;
	Vector<GameObject> fields;
	Vector2 drag;
	Vector2 defaultDrag;
	private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	Vector2 impulse;
	PhysicObject player;
	Camera cam;
	GameObject currentBackground;
	GameObject nextBackground;
	Context context;
	float widthRealScreen;
	float heightRealScreen;
	float ratioScreenW;
	int nbObjectDraw;
	float wantedScreenW;
	float wantedScreenH;

	int numEnnemy;
	int numTree;

	Bitmap bgPict;
	Bitmap enPict;
	Bitmap treePict;
	Bitmap starPict;

	private final String TAG = DoodleThread.class.getSimpleName();

	public GameEngine(Vector2 d, Context ctx, float widthS, float heightS,
			float widthHS) {
		nbObjectDraw = 0;
		context = ctx;
		numEnnemy = 0;
		drag = d;
		defaultDrag = new Vector2(d.x, d.y);
		impulse = new Vector2(0, 0);
		vectGameObj = new Vector<GameObject>();
		toDeleteGameObj = new Vector<GameObject>();
		wantedScreenW = widthHS;

		widthRealScreen = widthS;
		heightRealScreen = heightS;

		// float actualRatio = heightRealScreen / widthRealScreen;
		// wantedScreenH = wantedScreenW * actualRatio;
		ratioScreenW = widthRealScreen / wantedScreenW;
		wantedScreenH = 896;
		// ratioScreenH = (640*1.4)/heightRealScreen;

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Config.RGB_565;
		bgPict = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.bggame, options);
		bgPict = Bitmap.createScaledBitmap(bgPict, 640, 900, false);
		/*
		 * (int) (bgPict.getWidth() * (bgPict.getWidth()/640)), (int)
		 * (bgPict.getHeight() * (bgPict.getHeight()/1088)), false);
		 */

		enPict = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.mainttree, options);
		enPict = Bitmap.createScaledBitmap(enPict, 128, 128, false);

		treePict = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.mainttree, options);
		treePict = Bitmap.createScaledBitmap(treePict, 128, 128, false);

		starPict = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.star, options);
		// starPict = Bitmap.createScaledBitmap(starPict, 64, 64, false);

		Vector2 otherpos = new Vector2(0, 0);
		nextBackground = new GameObject(otherpos, bgPict, "bg");
		currentBackground = null;

	}

	public void step(float dt) {
		// CheckCollision
		CheckCollision();

		if (player.v.y >= 0) {
			player.v.y = 0;
			this.drag.Set(0, 0);
		} else {
			drag.Set(0, defaultDrag.y);
		}
		
		if(player.v.y >= 20.0f) {
			
		}

		// Using Newton's equation !
		// object.v += (dt/object.mass) * object.forces;
		// object.pos += dt * object.v;

		// F = m1*m2/d²
		// ->(m2/d²)*m1
		Vector2 dragMass = drag.MultByScalar(player.mass);
		// Log.d(TAG, "DRAG :" + drag.x + " " + drag.y);
		player.forces = player.forces.Add(dragMass);

		// We look at how forces affects the body regarding to its mass
		player.forces = player.forces.MultByScalar(dt / player.mass);
		player.v = player.v.Add(player.forces);
		Vector2 finalV = player.v.MultByScalar(dt);
		player.pos = player.pos.Add(finalV);
		// Log.d(TAG, "VELOCITY :" + finalV.x + " " + finalV.y);

		// Log.d(TAG, "FINAL POS : " + vectObject.get(i).pos.x + " " +
		player.forces.Set(0.0, 0.0);

		// cam.MoveAlongY(dt);

		cam.UpdateCam(player);
		player.UpdateCam(cam.deltaPos);
		player.UpdateCollisionIdentity();

		for (int i = 0; i < vectGameObj.size(); i++) {
			vectGameObj.get(i).UpdateCam(cam.deltaPos);
		}

		for (int i = 0; i < vectGameObj.size(); i++) {
			vectGameObj.get(i).UpdateCollisionIdentity();
		}

		if (currentBackground != null) {
			currentBackground.UpdateCam(cam.deltaPos);
			currentBackground.UpdateCollisionIdentity();

		}
		if (nextBackground != null) {
			nextBackground.UpdateCam(cam.deltaPos);
			nextBackground.UpdateCollisionIdentity();
		}

		// GenWorld();
		// Log.d(TAG, "FINAL POS : " + player.pos.x + " " + player.pos.y);
		GenWorld();
		cam.ResetDeltaPos();
	}

	public void doDraw(Canvas c) {
		nbObjectDraw = 0;
		// Log.d(TAG, "SCREEN :" + x + " " + y);
		c.scale((float) 1.25, (float) 1.25);

		c.drawColor(Color.WHITE);

		// Vector2 nul = new Vector2(0,0);
		if (currentBackground != null) {
			if(currentBackground.draw(c, cam.pos, wantedScreenH) == 1) {
				nbObjectDraw += 1;
			}
		}
		if (nextBackground != null) {
			if(nextBackground.draw(c, cam.pos, wantedScreenH) == 1) {
				nbObjectDraw += 1;
			}
		}
		player.draw(c, cam.pos, wantedScreenH);
		for (int i = 0; i < vectGameObj.size(); i++) {
			
			int result = vectGameObj.get(i).draw(c, cam.pos, wantedScreenH);
			
			if (result == 3) {
				this.AddDeleteObject(vectGameObj.get(i));
			} else if (result == 1) {
				nbObjectDraw += 1;
			}
		}
		nbObjectDraw += 1;
		paint.setColor(Color.BLACK);
		// c.drawRect(heightRealScreen - wantedScreenH, widthRealScreen - 940,
		// heightRealScreen - wantedScreenH, widthRealScreen - 940, paint);
		c.save();
		c.translate(0, 896);
		c.drawRect(0, 0, widthRealScreen, 300, paint);
		c.restore();
		c.save();
		c.translate(640, 0);
		c.drawRect(0, 0, 300, heightRealScreen, paint);
		c.restore();
		DeleteObject();
	}

	public void GenWorld() {
		Vector2 desiredY = new Vector2(0, cam.pos.y - 896);

		if (nextBackground.pos.y >= cam.pos.y) {

			currentBackground = nextBackground;
			nextBackground = null;
			// otherpict = Bitmap.createScaledBitmap(otherpict, 800, 1232,
			// false);
			Vector2 bgPos = new Vector2(0, cam.pos.y - wantedScreenH);
			nextBackground = new GameObject(bgPos, bgPict, "bg");

			Log.d(TAG, "HERE BITCHES !");

			Vector2 genPos = cam.pos;

			for (int j = 0; j < 9; j++) {
				for (int i = 0; i < 5; i++) {

					Vector2 temp = new Vector2(i * 128, (j * 128) - 896);
					if (i == 0 || i == 4) {
						this.CreateTree(genPos.Add(temp));
					} else {
						Random r = new Random();
						int randomInt = r.nextInt(4);
						switch (randomInt) {
						case 1:
							break;
						case 2:
							this.CreateStar(genPos.Add(temp));
							break;
						case 3:
							// this.CreateTree(genPos.Add(temp));
							break;
						}
					}
				}
			}

		}
	}

	public void AddGameObject(GameObject p) {
		vectGameObj.add(p);
	}

	public void CreateEnnemy(Vector2 pos) {
		Log.d(TAG, "CREATE ENNEMY :" + pos.x + " " + pos.y);
		Vector2 newPos = new Vector2(pos.x, pos.y);
		GameObject enemy = new GameObject(newPos, enPict, "enn");
		this.AddGameObject(enemy);
	}

	public void CreateTree(Vector2 pos) {
		Log.d(TAG, "CREATE TREE !");
		Vector2 newPos = new Vector2(pos.x, pos.y);

		GameObject tree = new GameObject(newPos, treePict, "tree");
		Circle c1 = new Circle(newPos, 35, treePict.getWidth() / 2,
				treePict.getHeight() / 2 - 10);
		tree.AddCircle(c1);
		Circle c2 = new Circle(newPos, 15, treePict.getWidth() / 2,
				treePict.getHeight() / 2 + 40);
		tree.AddCircle(c2);
		this.AddGameObject(tree);
	}

	public void CreateStar(Vector2 pos) {
		Log.d(TAG, "CREATE STAR !");
		Vector2 newPos = new Vector2(pos.x, pos.y);

		GameObject star = new GameObject(newPos, starPict, "star");
		Circle c1 = new Circle(newPos, 16, starPict.getWidth() / 2,
				starPict.getHeight() / 2);
		star.AddCircle(c1);
		this.AddGameObject(star);
	}

	/*
	 * public void CreatePhysicObject(PhysicObject p) { vectPhysicObj.add(p); }
	 */
	public void CheckCollision() {

		for (int i = 0; i < vectGameObj.size(); i++) {
			if (vectGameObj.get(i).cI.AABBvsAABB(player.cI)) {
				if (vectGameObj.get(i).toString() == "tree") {
					Log.d(TAG, "TREE !");
					player.v.x = 0;
					Vector2 imp = new Vector2(0, 10);
					this.GiveImpulse(imp);
				}

				if (vectGameObj.get(i).toString() == "star") {
					Vector2 imp = new Vector2(0, -100);
					this.GiveImpulse(imp);
					toDeleteGameObj.add(vectGameObj.get(i));
				}
			}
		}
		/*
		 * if (vectGameObj.get(i).aabb.AABBvsAABB(player.aabb) == 1) {
		 * 
		 * Log.d(TAG, "COLLATERAL COLLISION !"); //Vector2 vec = new Vector2(1,
		 * 0); player.v.x = 0; Vector2 imp = new Vector2(0, 10);
		 * this.GiveImpulse(imp);
		 * 
		 * } else if (vectGameObj.get(i).aabb.AABBvsAABB(player.aabb) == 2) {
		 * 
		 * Log.d(TAG, "COLLATERAL COLLISION !"); //Vector2 vec = new Vector2(-1,
		 * 0); player.v.x = 0; Vector2 imp = new Vector2(0, 10);
		 * this.GiveImpulse(imp);
		 * 
		 * } else if (vectGameObj.get(i).aabb.AABBvsAABB(player.aabb) == 3) {
		 * Log.d(TAG, "VERTICAL COLLISION !"); Vector2 imp = new Vector2(0, 10);
		 * GiveImpulse(imp); }
		 */

	}

	public void SetPlayer(PhysicObject p) {
		player = p;
		player.pos.Set(640 / 2 - 32, 896 / 2 + 250);
		Vector2 posCam = new Vector2(0, 0);
		cam = new Camera(posCam, (float) 10.0, player.pos.y);
	}

	public void GiveImpulse(Vector2 vec) {
		impulse = impulse.Add(vec);
		player.v = player.v.Add(vec);
		// player.forces = player.forces.Add(vec);
	}

	public int GetNumberObjectTotal() {
		return vectGameObj.size() + 2;
	}
	
	public int GetNumberObjectDraw() {
		return nbObjectDraw;
	}

	public void AddDeleteObject(GameObject obj) {
		toDeleteGameObj.add(obj);
	}

	public void DeleteObject() {
		for (int i = 0; i < vectGameObj.size(); i++) {
			for (int j = 0; j < toDeleteGameObj.size(); j++) {
				if (vectGameObj.get(i) == toDeleteGameObj.get(j)) {
					vectGameObj.remove(toDeleteGameObj.get(j));
				}
			}
		}
	}
}
