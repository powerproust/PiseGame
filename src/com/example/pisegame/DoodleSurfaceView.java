package com.example.pisegame;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

public class DoodleSurfaceView extends SurfaceView implements
		SurfaceHolder.Callback {

	class DoodleThread extends Thread implements SensorEventListener {
		private int surfaceWidth = 200;
		private int surfaceHeight = 400;

		private float factSpeed = 1;
		private static final double PI = 3.14159265359;
		private boolean run = false;
		float fps = 100;
		float dt = 1 / fps;
		float accumulator = 0;
		long frameStart;
		GameEngine world;
		Circle c1;
		PhysicObject ball;
		private float[] lastAccelerometer = new float[3];
		private float[] lastMagnetometer = new float[3];
		private boolean lastAccelerometerSet = false;
		private boolean lastMagnetometerSet = false;
		private float orientation;
		private float[] rotationMatrix = new float[9];
		private float[] orientationAngle = new float[3];
		private Direction dir;
		private float[] coefLatSpeed = new float[3];
		
		private SensorManager sensorManager;
		private Sensor accelerometer;
		private Sensor magnetometer;

		private final String TAG = DoodleThread.class.getSimpleName();

		public DoodleThread(Handler handler) {

			Handler handler1 = handler;
			sensorManager = (SensorManager) ctx
					.getSystemService(Context.SENSOR_SERVICE);
			accelerometer = sensorManager
					.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
			magnetometer = sensorManager
					.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

			sensorManager.registerListener((SensorEventListener) this,
					accelerometer, SensorManager.SENSOR_DELAY_GAME);
			sensorManager.registerListener((SensorEventListener) this,
					magnetometer, SensorManager.SENSOR_DELAY_GAME);

			coefLatSpeed[0] = (float) 0.2;
			coefLatSpeed[1] = (float) 0.6;
			coefLatSpeed[2] = 1;
			lastAccelerometerSet = true;
			lastMagnetometerSet = true;
			orientation = 0.0f;
			dir = Direction.MIDDLE;
		}

		public void doStart() {
			synchronized (sh) {
				/*
				 * DisplayMetrics metrics = new DisplayMetrics(); ((Activity)
				 * ctx
				 * ).getWindowManager().getDefaultDisplay().getMetrics(metrics);
				 * int width = metrics.widthPixels; int height =
				 * metrics.heightPixels;
				 */
				// Create World and set drag
				Vector2 drag = new Vector2(0, 5);
				world = new GameEngine(drag, ctx, surfaceWidth, surfaceHeight, (float) 640);

				// Start getting dt
				frameStart = System.currentTimeMillis();

				// init the main character;
				Bitmap pict = BitmapFactory.decodeResource(ctx.getResources(),
						R.drawable.ball);

				Vector2 posC = new Vector2(150, 300);
				ball = new PhysicObject(posC, pict, "player");
				c1 = new Circle(posC, 20, (pict.getWidth()/2), (pict.getHeight()/2));
				
				ball.AddCircle(c1);
				world.SetPlayer(ball);

				Vector2 otherpos = new Vector2(200, 300);
				world.CreateTree(otherpos);
			}
		}

		public void run() {

			// Log.d(TAG, "Starting game loop");
			// int tickCount = 0;
			while (run) {
				// tickCount++;

				Canvas c = null;
				try {
					c = sh.lockCanvas(null);
					synchronized (sh) {

						long currentTime = System.currentTimeMillis();

						accumulator += currentTime - frameStart;
						frameStart = currentTime;

						if (accumulator > 0.2f) {
							accumulator = 0.2f;
						}
						
						while (accumulator > dt) {
							UpdatePhysics(dt);
							accumulator -= dt;
						}
						doDraw(c);
					}
				} finally {
					if (c != null) {
						sh.unlockCanvasAndPost(c);
					}
				}
			}
		}

		private void UpdatePhysics(float dt) {
			world.step(dt);
		}

		public void setRunning(boolean b) {
			run = b;
		}

		public void setSurfaceSize(int width, int height) {
			synchronized (sh) {
				surfaceWidth = width;
				surfaceHeight = height;
				doStart();
			}
		}

		private void doDraw(Canvas canvas) {
			if (canvas != null) {
				world.doDraw(canvas);
				paint.setColor(Color.RED);
		        paint.setStyle(Style.FILL);
		        //canvas.drawCircle(c1.pos.x+150, c1.pos.y+150, c1.radius, paint);
		        //canvas.drawBitmap(ball.picture, ball.pos.x+150, ball.pos.y+150, paint);
			}
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSensorChanged(SensorEvent event) {
			synchronized (sh) {

				if (event.sensor == accelerometer) {
					System.arraycopy(event.values, 0, lastAccelerometer, 0,
							event.values.length);
					lastAccelerometerSet = true;
				} else if (event.sensor == magnetometer) {
					System.arraycopy(event.values, 0, lastMagnetometer, 0,
							event.values.length);
					lastMagnetometerSet = true;
				}

				if (lastAccelerometerSet && lastMagnetometerSet) {
					SensorManager.getRotationMatrix(rotationMatrix, null,
							lastAccelerometer, lastMagnetometer);
					SensorManager.getOrientation(rotationMatrix,
							orientationAngle);

					for (int i = 0; i < orientationAngle.length; i++) {
						orientationAngle[i] = (float) (orientationAngle[i] * (180 / PI));

					}
					/*
					 * Log.d("TAG", String.format("Orientation: %f, %f, %f",
					 * orientationAngle[0], orientationAngle[1],
					 * orientationAngle[2]));
					 */
				}

				// world.drag.Set(orientationAngle[1]*factSpeed, world.drag.y);
				restartDirection();
				
				if (orientationAngle[1] >= 2 && orientationAngle[1] <= 60) {

					if (orientationAngle[1] >= 2 && orientationAngle[1] <= 10) {
						//if (dir != Direction.RIGHTSOFT) {

							dir = Direction.RIGHTSOFT;
							world.player.v.x += coefLatSpeed[0] * (world.player.v.y*-1);
							orientation = coefLatSpeed[0];
							//Log.d("TAG", "RIGHT SOFT !");
						//}

					} else if (orientationAngle[1] > 10
							&& orientationAngle[1] <= 30) {
						//if (dir != Direction.RIGHTAVE) {

							dir = Direction.RIGHTAVE;
							world.player.v.x += coefLatSpeed[1] * (world.player.v.y*-1);
							orientation = coefLatSpeed[1];
							//Log.d("TAG", "RIGHT AVE !");
						//}

					} else if (orientationAngle[1] > 30
							&& orientationAngle[1] <= 60) {
						//if (dir != Direction.RIGHTHARD) {

							dir = Direction.RIGHTHARD;
							world.player.v.x += coefLatSpeed[2] * (world.player.v.y*-1);
							orientation = coefLatSpeed[2];
							//Log.d("TAG", "RIGHT HARD!");
						//}
					}
				} else if (orientationAngle[1] < -2
						&& orientationAngle[1] >= -60) {

					if (orientationAngle[1] <= -2 && orientationAngle[1] >= -10) {
						//if (dir != Direction.LEFTSOFT) {


							dir = Direction.LEFTSOFT;
							world.player.v.x += coefLatSpeed[0] * world.player.v.y;
							orientation = coefLatSpeed[0] * -1;
							//Log.d("TAG", "LEFT SOFT !");
						//}

					} else if (orientationAngle[1] < -10
							&& orientationAngle[1] >= -30) {
						//if (dir != Direction.LEFTAVE) {

							dir = Direction.LEFTAVE;
							world.player.v.x += coefLatSpeed[1] * world.player.v.y;
							orientation = coefLatSpeed[1] * -1;
							//Log.d("TAG", "LEFT AVE !");
						//}
					} else if (orientationAngle[1] < -30
							&& orientationAngle[1] >= -60) {
						//if (dir != Direction.LEFTHARD) {

							dir = Direction.LEFTHARD;
							world.player.v.x += coefLatSpeed[2] * world.player.v.y;
							orientation = coefLatSpeed[2] * -1;
							//Log.d("TAG", "LEFT HARD!");
						//}
					}

				}
			}
		}

		public void restartDirection() {
			world.player.v.x = 0;
		}
	}

	private Context ctx = null;
	DoodleThread thread;
	private SurfaceHolder sh;
	private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private final String TAG = DoodleThread.class.getSimpleName();
	//Canvas canvas;

	public enum Direction {
		LEFTSOFT, LEFTAVE, LEFTHARD, MIDDLE, RIGHTSOFT, RIGHTAVE, RIGHTHARD;
	}

	public DoodleSurfaceView(Context context) {
		super(context);
		sh = getHolder();
		sh.addCallback(this);
		paint.setColor(Color.BLUE);
		paint.setStyle(Style.FILL);
		ctx = context;
		setFocusable(true); // make sure we get key events
		//Bitmap Bit = Bitmap.createBitmap(480, 300, Bitmap.Config.RGB_565);
		//canvas = new Canvas(Bit);
	}

	public DoodleThread getThread() {
		return thread;
	}

	public void surfaceCreated(SurfaceHolder holder) {
		thread = new DoodleThread(new Handler());
		thread.setRunning(true);
		thread.start();
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		thread.setSurfaceSize(width, height);
	}

	public boolean onTouchEvent(MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (event.getY() > getHeight() - 50) {
				thread.setRunning(false);
				((Activity) getContext()).finish();
			} else {
				Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY());
				Vector2 imp = new Vector2(0, -10);
				thread.world.GiveImpulse(imp);
				// thread.world.drag.Set(0, 0);
			}
		}

		return super.onTouchEvent(event);

	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		thread.setRunning(false);
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
			}
		}
	}
	/*
	 * @Override protected void onMeasure(int widthMeasureSpec, int
	 * heightMeasureSpec) {
	 * 
	 * int desiredWidth = 200; int desiredHeight = 400;
	 * 
	 * int widthMode = MeasureSpec.getMode(widthMeasureSpec); int widthSize =
	 * MeasureSpec.getSize(widthMeasureSpec); int heightMode =
	 * MeasureSpec.getMode(heightMeasureSpec); int heightSize =
	 * MeasureSpec.getSize(heightMeasureSpec);
	 * 
	 * int width; int height;
	 * 
	 * //Measure Width if (widthMode == MeasureSpec.EXACTLY) { //Must be this
	 * size width = desiredWidth; //width = desiredWidth; } else if (widthMode
	 * == MeasureSpec.AT_MOST) { //Can't be bigger than... //width =
	 * Math.min(desiredWidth, widthSize); width = desiredWidth; } else { //Be
	 * whatever you want width = desiredWidth; }
	 * 
	 * //Measure Height if (heightMode == MeasureSpec.EXACTLY) { //Must be this
	 * size height = desiredHeight; } else if (heightMode ==
	 * MeasureSpec.AT_MOST) { //Can't be bigger than... //height =
	 * Math.min(desiredHeight, heightSize); height = desiredHeight; } else {
	 * //Be whatever you want height = desiredHeight; }
	 * 
	 * //MUST CALL THIS setMeasuredDimension(width, height); }
	 */

}