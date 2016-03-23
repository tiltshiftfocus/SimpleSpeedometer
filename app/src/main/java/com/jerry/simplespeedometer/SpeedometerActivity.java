package com.jerry.simplespeedometer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

import com.jerry.simplespeedometer.helper.PreferenceHelper;

import net.margaritov.preference.colorpicker.ColorPickerPreference;

/**
 * Created by Jerry on 23/3/2016.
 * Credit  @cpwc
 */
public class SpeedometerActivity extends FullscreenActivity implements LocationListener {

    public static final int SETTINGS_RESULT = 190;

    private LocationManager lm;
    private TextView text;
    private TextView topSpeed;
    private TextView speedUnit;
    private int topSpeedTemp=0;

    private WorkingCompass compass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_fullscreen2);
        super.onCreate(savedInstanceState);

        text = (TextView)findViewById(R.id.speed);
        topSpeed = (TextView)findViewById(R.id.top_speed);
        speedUnit = (TextView) findViewById(R.id.speed_unit);

        setSpeedometerColor();
        setFont();

        lm = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
        compass = new WorkingCompass(this,this);

        this.onLocationChanged(null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == this.SETTINGS_RESULT){
            setSpeedometerColor();
        }
    }

    @Override
    public void onBackPressed(){

        lm.removeUpdates(this);
        finish();
    }

    @Override
    public void onPause(){
        lm.removeUpdates(this);
        compass.pauseCompass();
        super.onPause();
    }

    @Override
    public void onResume(){
        super.onResume();
        text.setText("--");
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
        compass.resumeCompass();
    }

    @Override
    public void onLocationChanged(Location location) {

        float mCurrentSpeed=0;
        int mCurrentSpeedKMH=0;

        if(location==null){
            text.setText("--");
            topSpeed.setText("--");
        }else{
            mCurrentSpeed = location.getSpeed();
            mCurrentSpeedKMH = (int)Math.round((mCurrentSpeed*3600)/1000);
            //String speedText = String.format("%.0f", mCurrentSpeedKMH);
            text.setText(mCurrentSpeedKMH + "");

            if(mCurrentSpeedKMH>topSpeedTemp){

                topSpeedTemp = mCurrentSpeedKMH;
                //String topSpeedStr = String.format("%.0f", topSpeedTemp);
                topSpeed.setText(topSpeedTemp + " km/h");
            }

        }

		/*
		 * mCurrentSpeedKMH=2
		 * newTopSpeed=0
		 * topSpeedTemp=2

		*/

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    private void setFont() {
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "DIGITALDREAMFAT.ttf");
        text.setTypeface(custom_font);
    }

    private void setSpeedometerColor() {
        String textColorHEX = Integer.toString(PreferenceHelper.getInstance(this).getSpeedometerColor());
        textColorHEX = ColorPickerPreference.convertToARGB(Integer.valueOf(String.valueOf(textColorHEX)));
        text.setTextColor(Color.parseColor(textColorHEX));
        speedUnit.setTextColor(Color.parseColor(textColorHEX));
    }
}
