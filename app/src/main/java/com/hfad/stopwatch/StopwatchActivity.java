package com.hfad.stopwatch;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class StopwatchActivity extends Activity {

    private int seconds= 0;
    private boolean running;
    private int millisec =0;
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        if(savedInstanceState != null) {
            //Retrieve the savedInstance state
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            millisec = savedInstanceState.getInt("millisec");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        //Run the timer on startup
        runTimer();
    }

    //Saving the state of the device
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putInt("seconds",seconds);
        savedInstanceState.putInt("millec",millisec);
        savedInstanceState.putBoolean("running",running);
        savedInstanceState.putBoolean("wasRunning",wasRunning);

    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;       //Record whether the stopwatch was running
        running = false;            //when the onStop() method was called
    }

    /*Implement the onStart() method.
    **If the stopwatch was running, set it running again
     */
    @Override
    protected void onResume() {
        super.onResume();
        if(wasRunning){
            running = true;
        }
    }

    //Start the stopwatch running when the Start button is clicked
    public void onClickStart(View view){
        running = true;
    }

    //Stop the stopwatch running when the stop button is clicked
    public void onClickStop(View view){
        running = false;
    }

    //Reset the stopwatch when the Reset button is clicked
    public void onClickReset(View view){
        running = false;
        seconds = 0;
        millisec = 0;
    }

    private void runTimer(){
        final TextView timeView = (TextView)findViewById(R.id.time_view);
        final Handler handler = new Handler();
        final Locale locale = new Locale("en","uk");
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds/3600;
                int minutes = (seconds%3600)/60;
                int secs = seconds%60;
                int difference = millisec/10;
                String time = String.format(locale,"%d:%02d:%02d:%02d",
                        hours,minutes,secs,millisec);
                timeView.setText(time);
                if(running){
                    millisec++;
                    if(difference == 1) {
                        seconds++;  //if running is true, increment the seconds variable
                        millisec = 0;
                    }
                }
                handler.postDelayed(this,100);
            }
        });

    }

}
