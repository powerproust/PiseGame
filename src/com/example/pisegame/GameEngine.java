package com.example.pisegame;

import java.util.Random;
import java.util.Vector;

import com.example.pisegame.DoodleSurfaceView.DoodleThread;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
	Vector<GameObject> fields;
	Vector2 drag;
	Vector2 defaultDrag;
	private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	Vector2 impulse;
	PhysicObject player;
	boolean playerDead;
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
	int turbRessource;
	int numTurbo;
	public int score;

	Bitmap bgPict;
	Bitmap turbPict;
	Bitmap treePict;
	Bitmap starPict;

	private final String TAG = DoodleThread.class.getSimpleName();

	public GameEngine(Vector2 d, Context ctx, float widthS, float heightS,
			float widthHS) {
		playerDead = false;
		turbRessource = 5;
		numTurbo = 0;
		score = 0;
		nbObjectDraw = 0;
		context = ctx;
		drag = d;
		defaultDrag = new Vector2(d.x, d.y);
		impulse = new Vector2(0, 0);
		vectGameObj = new Vector<GameObject>();
		toDeleteGameObj = new Vector<GameObject>();
		wantedScreenW = widthHS;

		widthRealScreen = widthS;
		heightRealScreen = heightS;

		ratioScreenW = widthRealScreen / wantedScreenW;
		wantedScreenH = 896;

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Config.RGB_565;
		bgPict = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.bggame, options);
		bgPict = Bitmap.createScaledBitmap(bgPict, 640, 1024, false);

		turbPict = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.turbo, options);
		turbPict = Bitmap.createScaledBitmap(turbPict, 64, 64, false);

		treePict = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.mainttree, options);
		treePict = Bitmap.createScaledBitmap(treePict, 128, 128, false);

		starPict = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.star, options);

		Vector2 otherpos = new Vector2(0, 0);
		nextBackground = new GameObject(otherpos, bgPict, "bg");
		currentBackground = null;
		for (int j = 0; j < 9; j++) {
			for (int i = 0; i < 5; i++) {
				Vector2 temp = new Vector2(i * 128, (j * 128) - 512);
				if (i == 0 || i == 4) {
					this.AddTree(temp);
				}
			}
		}
	}

	
	public GameEngine(Context context){
		this.context = context;
	}
	
	
	public void step(float dt) {
		if (player.v.y<0){  // TESTING
			CheckCollision();
			if (player.v.y >= 0) {
				//finalscore.fscore=this.score;
				player.v.y = 0;
				this.drag.Set(0, 0);
				playerDead = true;
			} else {
				drag.Set(0, defaultDrag.y);
			}
			if (player.v.y >= 20.0f) {
				player.v.y = 20.0f;
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
		}
		// cam.MoveAlongY(dt);
		GenWorld();
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
		cam.ResetDeltaPos();
	}

	public void doDraw(Canvas c) {
		nbObjectDraw = 0;

		c.scale((float) 1.25, (float) 1.25);

		c.drawColor(Color.WHITE);

		if (currentBackground != null) {
			if (currentBackground.draw(c, cam.pos, wantedScreenH) == 1) {
				nbObjectDraw += 1;
			}
		}
		if (nextBackground != null) {
			if (nextBackground.draw(c, cam.pos, wantedScreenH) == 1) {
				nbObjectDraw += 1;
			}
		}

		for (int i = 0; i < vectGameObj.size(); i++) {
			if (vectGameObj.get(i).toString() != "tree") {
				int result = vectGameObj.get(i).draw(c, cam.pos, wantedScreenH);

				if (result == 1) {
					nbObjectDraw += 1;
				} else if (result == 3) {
					this.AddDeleteObject(vectGameObj.get(i));
				}
			}
		}

		player.draw(c, cam.pos, wantedScreenH);

		for (int i = 0; i < vectGameObj.size(); i++) {
			if (vectGameObj.get(i).toString() == "tree") {
				int result = vectGameObj.get(i).draw(c, cam.pos, wantedScreenH);

				if (result == 1) {
					nbObjectDraw += 1;
				} else if (result == 3) {
					this.AddDeleteObject(vectGameObj.get(i));
				}
			}
		}
		nbObjectDraw += 1;
		paint.setColor(Color.BLACK);
		c.save();
		c.translate(0, 896);
		c.drawRect(0, 0, widthRealScreen, 300, paint);
		c.restore();
		c.save();
		c.translate(640, 0);
		c.drawRect(0, 0, 300, heightRealScreen, paint);
		c.restore();
		// Log.d(TAG, "NUMBER DELETE BEFORE DELETE :" + toDeleteGameObj.size());
		DeleteObject();
	}

	public void GenWorld() {
		// Vector2 desiredY = new Vector2(0, cam.pos.y - 896);

		if (nextBackground.pos.y >= cam.pos.y - 128) {
			Log.d(TAG, "GEN WORLD !");
			currentBackground = nextBackground;
			nextBackground = null;
			Vector2 bgPos = new Vector2(0, cam.pos.y - wantedScreenH);
			nextBackground = new GameObject(bgPos, bgPict, "bg");
			numTurbo = 0;
			Vector2 genPos = cam.pos;
			int widthTree = 1;
			Vector<Integer> vectTree = new Vector();
			boolean other = true;
			Random r = new Random();

			if (score < 30) {
				widthTree = 1;
			} else if (score < 60) {
				widthTree = r.nextInt(3) + 1;
			} else if (score < 100) {
				widthTree = r.nextInt(4) + 1;
			} else {
				widthTree = r.nextInt(4) + 2;
			}

			if (widthTree == 1) {
				vectTree.clear();
				vectTree.add(0);
				vectTree.add(4);
			} else if (widthTree == 2) {
				vectTree.clear();
				vectTree.add(0);
				vectTree.add(4);
				vectTree.add(1);
			} else if (widthTree == 3) {
				vectTree.clear();
				vectTree.add(0);
				vectTree.add(4);
				vectTree.add(3);
			} else if (widthTree == 4) {
				vectTree.clear();
				vectTree.add(0);
				vectTree.add(4);
				vectTree.add(1);
				vectTree.add(3);
			}

			for (int j = 0; j < 6; j++) {
				for (int i = 0; i < 5; i++) {
					other = true;
					Vector2 temp = new Vector2(i * 128, (j * 128) - 1024);
					for (int z = 0; z < vectTree.size(); z++) {
						if (i == vectTree.get(z)) {
							this.AddTree(genPos.Add(temp));
							other = false;
						}
					}
					if (other == true) {

						int randomObj = r.nextInt(3) + 1;
						Log.d(TAG, "RANDOM OBJ" + randomObj);
						if (randomObj == 1) {
							int randomStar = r.nextInt(1) + 1;
							if (randomStar == 1) {
								this.AddStar(genPos.Add(temp));
							}
						} else if (randomObj == 2) {
							int randomTurb = r.nextInt(8) + 1;
							int maxTurb = 1;
							int minTurb = 1;

							if (player.v.y > -250) {
								maxTurb = 8;
							} else if (player.v.y < -200) {
								minTurb = 0;
								maxTurb = 0;
							}

							if (randomTurb >= minTurb && randomTurb <= maxTurb
									&& numTurbo < 1) {
								this.AddTurbo(genPos.Add(temp));
								numTurbo++;
							}
						} else if (randomObj == 3) {
							int randomTree = r.nextInt(6) + 1;

							if (randomTree == 1) {
								this.AddTree(genPos.Add(temp));
							}
						}
					}

				}
			}
		}
	}

	public void AddGameObject(GameObject p) {
		vectGameObj.add(p);
	}

	public void AddTurbo(Vector2 pos) {
		/*
		 * Log.d(TAG, "ADD TURBO");
		 * 
		 * for (int j = 0; j < toDeleteGameObj.size(); j++) { if
		 * (toDeleteGameObj.get(j).toString() == "turbo") { Log.d(TAG,
		 * "FIND OBJECT TO MOVE !"); toDeleteGameObj.get(j).pos.Set(pos.x,
		 * pos.y); // vectGameObj.add(toDeleteGameObj.get(j));
		 * this.AddGameObject(toDeleteGameObj.get(j));
		 * toDeleteGameObj.remove(j); return; } }
		 */
		Vector2 newPos = new Vector2(pos.x, pos.y);
		GameObject turbo = new GameObject(newPos, turbPict, "turbo");
		Circle c1 = new Circle(newPos, 40, turbPict.getWidth() / 2,
				turbPict.getHeight() / 2);
		turbo.AddCircle(c1);
		this.AddGameObject(turbo);
	}

	public void AddTree(Vector2 pos2) {
		Log.d(TAG, "ADD TREE !");

		for (int j = 0; j < toDeleteGameObj.size(); j++) {
			if (toDeleteGameObj.get(j).toString() == "tree") {
				Log.d(TAG, "FIND OBJECT TO MOVE !" + pos2.x + " " + pos2.y);
				toDeleteGameObj.get(j).pos.Set(pos2.x, pos2.y);

				this.AddGameObject(toDeleteGameObj.get(j));

				toDeleteGameObj.removeElementAt(j);

				return;
			}
		}

		Vector2 newPos = new Vector2(pos2.x, pos2.y);
		GameObject tree = new GameObject(newPos, treePict, "tree");
		Circle c1 = new Circle(newPos, 35, treePict.getWidth() / 2,
				treePict.getHeight() / 2 - 10);
		tree.AddCircle(c1);
		Circle c2 = new Circle(newPos, 15, treePict.getWidth() / 2,
				treePict.getHeight() / 2 + 40);
		tree.AddCircle(c2);
		this.AddGameObject(tree);
	}

	public void AddStar(Vector2 pos) {
		/*
		 * Log.d(TAG, "ADD STAR !");
		 * 
		 * for (int j = 0; j < toDeleteGameObj.size(); j++) { if
		 * (toDeleteGameObj.get(j).toString() == "star") { Log.d(TAG,
		 * "FIND OBJECT TO MOVE !"); toDeleteGameObj.get(j).pos.Set(pos.x,
		 * pos.y); // vectGameObj.add(toDeleteGameObj.get(j));
		 * this.AddGameObject(toDeleteGameObj.get(j));
		 * toDeleteGameObj.remove(j); return; } }
		 */
		Vector2 newPos = new Vector2(pos.x, pos.y);
		GameObject star = new GameObject(newPos, starPict, "star");
		Circle c1 = new Circle(newPos, 30, starPict.getWidth() / 2,
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
					Vector2 imp = new Vector2(0, 100);
					this.GiveImpulse(imp);
				}

				if (vectGameObj.get(i).toString() == "star") {
					toDeleteGameObj.add(vectGameObj.get(i));
					score++;
				}

				if (vectGameObj.get(i).toString() == "turbo") {
					Vector2 imp = new Vector2(0, -150);
					this.GiveImpulse(imp);
					toDeleteGameObj.add(vectGameObj.get(i));
				}

			}
		}
	}

	public void SetPlayer(PhysicObject p) {
		player = p;
		player.pos.Set(640 / 2 - 32, 896 / 2 + 250);
		Vector2 posCam = new Vector2(0, 0);
		cam = new Camera(posCam, (float) 10.0, player.pos.y);
		Vector2 imp = new Vector2(0, -100);
		this.GiveImpulse(imp);
		Vector2 posTurb = new Vector2(640 / 2 - 48, (896 / 2 + 250) - 100);
		this.AddTurbo(posTurb);
		Vector2 posTurb2 = new Vector2(640 / 2 - 48, (896 / 2 + 250) - 170);
		this.AddTurbo(posTurb2);
	}

	public void GiveImpulse(Vector2 vec) {
		impulse = impulse.Add(vec);
		player.v = player.v.Add(vec);
	}

	public int GetNumberObjectTotal() {
		return vectGameObj.size() + 2;
	}

	public int GetNumberObjectDraw() {
		return nbObjectDraw;
	}

	public int GetNumberObjectToDelete() {
		return toDeleteGameObj.size();
	}

	public void AddDeleteObject(GameObject obj) {
		toDeleteGameObj.add(obj);
	}

	public void UseTurb() {
		if (turbRessource > 0) {
			turbRessource--;
			Vector2 imp = new Vector2(0, -50);
			this.GiveImpulse(imp);
		}
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
