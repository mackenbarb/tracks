package org.developerworks.android.tracks;

import java.io.IOException;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

public class CameraOverlay extends Activity {
	String temp = "Intent Failed";
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i("TestCam Create", "Creating");
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		temp =	 "Location - " + intent.getStringExtra(MainActivity.LOCATION) +
				"/nDirection - " + intent.getStringExtra(MainActivity.DIRECTION);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		Preview mPreview = new Preview(this);
		DrawOnTop mDraw = new DrawOnTop(this, temp);
		mDraw.invalidate();

		setContentView(mPreview);
		addContentView(mDraw, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		Log.i("TestCam Create", "Done creating");
	}
}

class DrawOnTop extends View {
	String temp = "Intent Passover Failed";

	public DrawOnTop(Context context, String temp) {
		super(context);
		this.temp = temp;
		Log.i("DrawOnTop", "DrawOnTop created");
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		Log.i("DrawOnTop", "onDraw called");
		//		Paint paint = new Paint();
		//		paint.setStyle(Paint.Style.FILL);
		//		paint.setColor(Color.BLACK);
		//		canvas.drawText("Test Text", 10, 10, paint);
		TextView tV = (TextView) this.findViewById(R.id.textView3);
		tV.setText(temp);

		Log.i("DrawOnTop", "onDraw complete");
	}
}

//----------------------------------------------------------------------

class Preview extends SurfaceView implements SurfaceHolder.Callback {
	SurfaceHolder mHolder;
	Camera mCamera;

	Preview(Context context) {
		super(context);
		Log.i("mHolder", "Preview started");
		// Install a SurfaceHolder.Callback so we get notified when
		// the underlying surface is created and destroyed.
		mHolder = getHolder();
		mHolder.addCallback(this);
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	public void surfaceCreated(SurfaceHolder holder) {
		// The Surface has been created, acquire the camera and tell
		// it where to draw.
		Log.i("mHolder", "surfaceCreated");
		mCamera = Camera.open();
		try {
			mCamera.setPreviewDisplay(holder);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// Surface will be destroyed when we return, so stop the preview.
		// Because the CameraDevice object is not a shared resource,
		// it's very important to release it when the activity is paused.
		Log.i("mHolder", "surfaceDestroyed");
		mCamera.stopPreview();
		mCamera = null;
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
		// Now that the size is known, set up the camera parameters
		// and begin the preview.
		Log.i("mHolder", "surfaceChanged");
		Camera.Parameters parameters = mCamera.getParameters();
		parameters.setPreviewSize(w, h);

		mCamera.setParameters(parameters);
		mCamera.startPreview();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mCamera.stopPreview();
	}
}
