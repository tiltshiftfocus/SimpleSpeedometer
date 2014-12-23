package com.jerry.simplespeedometer;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class WorkingCompass implements SensorEventListener {
	
	private ImageView compassArrow;
	private float currentDegree = 0f;
	
	private SensorManager mSensorManager;
	
	public WorkingCompass(Context context, FullscreenActivity view){
		
		compassArrow = (ImageView)view.findViewById(R.id.compass_arrow);
		
		mSensorManager = (SensorManager)context.getSystemService(context.SENSOR_SERVICE);
	}
	
	public void resumeCompass(){
		mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), 
				SensorManager.SENSOR_DELAY_GAME);
	}
	
	public void pauseCompass(){
		mSensorManager.unregisterListener(this);
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		int degree = Math.round(event.values[0]);
		
		RotateAnimation ra = new RotateAnimation(
				currentDegree,
				degree,
				Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		
		ra.setDuration(210);
		ra.setFillAfter(true);
		
		compassArrow.startAnimation(ra);
		currentDegree = degree;
		
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

}
