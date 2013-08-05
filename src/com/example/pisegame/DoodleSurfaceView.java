package com.example.pisegame;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class DoodleSurfaceView extends SurfaceView implements
		SurfaceHolder.Callback {

	class DoodleThread extends Thread implements SensorEventListener {
		private int canvasWidth = 200;
		private int canvasHeight = 400;
		private static final int SPEED = 2;
		private boolean run = false;
		float fps = 100;
		float dt = 1 / fps;
		float accumulator = 0;
		long frameStart;
		private float bubbleX;
		private float bubbleY;
		private float headingX;
		private float headingY;
		private final String TAG = DoodleThread.class.getSimpleName();

		public DoodleThread(Handler handler) {
			// sh = surfaceHolder;
			Handler handler1 = handler;
			// Context ctx = context;
			SensorManager sensorManager = (SensorManager) ctx
					.getSystemService(Context.SENSOR_SERVICE);
			Sensor sensor = sensorManager
					.getDefaultSensor(Sensor.TYPE_ORIENTATION);
			sensorManager.registerListener((SensorEventListener) this, sensor,
					1000000);
		}

		public void doStart() {
			synchronized (sh) {
				// Start bubble in centre and create some random motion
				frameStart = System.currentTimeMillis();
				bubbleX = canvasWidth / 2;
				bubbleY = canvasHeight / 2;
				headingX = (float) (-1 + (Math.random() * 2));
				headingY = (float) (-1 + (Math.random() * 2));
			}
		}

		public void run() {

			Log.d(TAG, "Starting game loop");
			int tickCount = 0;
			while (run) {
				tickCount++;

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
			Log.d(TAG, "Game loop executed " + tickCount + " times");

		}

		private void UpdatePhysics(float dt) {
			

		}

		public void setRunning(boolean b) {
			run = b;
		}

		public void setSurfaceSize(int width, int height) {
			synchronized (sh) {
				canvasWidth = width;
				canvasHeight = height;
				doStart();
			}
		}

		private void doDraw(Canvas canvas) {

			bubbleX = bubbleX + (headingX * SPEED);
			bubbleY = bubbleY + (headingY * SPEED);

			if (canvas != null) {
				canvas.restore();
				canvas.drawColor(Color.BLACK);
				canvas.drawCircle(bubbleX, bubbleY, 50, paint);
			}
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSensorChanged(SensorEvent event) {
			synchronized (sh) {
				if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
					Log.d(TAG, "X : " + event.values[0]);
					Log.d(TAG, "Y : " + event.values[1]);
					// headingX = event.values[0]/12;
					if (event.values[1] < 0) {
						if (headingX > 0) {
							headingX *= -1;
						}
					} else {
						if (headingX < 0) {
							headingX *= -1;
						}
					}
				}
			}
		}
	}

	private Context ctx = null;
	DoodleThread thread;
	private SurfaceHolder sh;
	private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private final String TAG = DoodleThread.class.getSimpleName();

	public DoodleSurfaceView(Context context) {
		super(context);
		sh = getHolder();
		sh.addCallback(this);
		paint.setColor(Color.BLUE);
		paint.setStyle(Style.FILL);
		ctx = context;
		setFocusable(true); // make sure we get key events

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
}