package com.example.OSWAME.FutbolitoPocket;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SensorManager sensorManager;
    SensorEventListener sensorEventListener;
    Sensor sensor;
    ImageView balon=null;
    TextView Marcador1,Marcador2;
    int a=0;
    int width=0;
    int height=0;
    int equipo1=0,equipo2=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        balon =  (ImageView)findViewById(R.id.baloncito);
        Marcador1 =  (TextView) findViewById(R.id.MarcadorA);
        Marcador2 =  (TextView) findViewById(R.id.MarcadorB);

        final ImageView cancha =  (ImageView)findViewById(R.id.canchita);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width=metrics.widthPixels;
        height = metrics.heightPixels;
        sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
        sensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if(sensor==null){
            finish();
        }

        sensorEventListener=new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {

                float x = sensorEvent.values[0];
                float y = sensorEvent.values[1];
                if (x<-1){
                        if(balon.getX()<width-balon.getWidth()) {
                            balon.setX(balon.getX() + 20);
                        }
                }else if(x>1){
                        if(balon.getX()>1) {
                            balon.setX(balon.getX() - 20);
                        }
                }

                if(y<-1){
                    if(balon.getY()>0) {
                        balon.setY(balon.getY() - 20);
                    }else {
                        if(balon.getX()>400&&balon.getX()<580) {
                            gol();
                            equipo2++;
                            Marcador2.setText(equipo2+"");
                        }
                    }
                }else if (y>1){
                    if(balon.getY()<(width-balon.getHeight()+850)) {
                        balon.setY(balon.getY() + 20);
                    }else {
                        if(balon.getX()>400&&balon.getX()<580) {
                            gol();
                            equipo1++;
                            Marcador1.setText(equipo1+"");
                        }
                    }
                }
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        };
        start();
    }

    public void gol(){
        balon.setY(645);
        balon.setX(410);
    }

    private  void start(){
        sensorManager.registerListener(sensorEventListener,sensor,sensorManager.SENSOR_DELAY_GAME);
    }

    private  void stop(){
        sensorManager.unregisterListener(sensorEventListener);
    }

    @Override
    protected void onPause() {
        stop();
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        start();
        super.onResume();
        gol();
    }
}
