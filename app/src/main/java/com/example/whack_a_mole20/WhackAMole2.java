package com.example.whack_a_mole20;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class WhackAMole2 extends AppCompatActivity {

    private CountDownTimer countDownTimer;
    private TextView resultTextView;
    private static final String TAG = "Whack-A-Mole!";
    private Integer randomLocation;
    private Integer score = 0;

    private final List<Button> holeButtonList = new ArrayList<>();

    private static final int[] BUTTON_IDS = {

            R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5,
            R.id.button6, R.id.button7, R.id.button8, R.id.button9
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.whackamole2);

        //Get Intent
        Intent receivingEnd = getIntent();
        score = receivingEnd.getIntExtra("Score", 0);
        Log.v(TAG, "Current User Score: " + score.toString());

        //Set current score value
        resultTextView = (TextView)findViewById(R.id.resultTextView);
        resultTextView.setText(score.toString());

        //Initialise listener
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button buttonPressed = (Button) v;
                doCheck(buttonPressed);
            }
        };

        //Initialise buttons
        for(final int id : BUTTON_IDS){

            Button holeButton = (Button) findViewById(id);
            holeButton.setOnClickListener(listener);
            holeButtonList.add(holeButton);
        }

        //Once all initialized, start timer
        readyTimer();
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    protected void onStop(){
        super.onStop();
        Log.v(TAG, "Stopped Whack-A-Mole!");
    }

    private void doCheck(Button checkButton)
    {

        switch (checkButton.getText().toString()) {
            case "0":
                Log.v(TAG, "Missed, score deducted!");
                score -= 1;
                resultTextView.setText(score.toString());
                break;
            case "*":
                Log.v(TAG, "Hit, score added!");
                score += 1;
                resultTextView.setText(score.toString());
                resetMole(); //If hit, reset mole
                setNewMole();
                countDownTimer.cancel();
                placeMoleTimer(); //Reset timer.
                break;
            default:
                Log.v(TAG, "Unknown button pressed, no case for it's text set.");
        }
    }

    private void readyTimer(){

        countDownTimer = new CountDownTimer(10*1000, 1000) {
            @Override
            public void onTick(long ms) {
                //Calculate time remaining
                Long timeRemaining = ms/1000;
                Log.v(TAG, "Ready CountDown!" + ms/ 1000);
                String msg = "Get ready in " + timeRemaining.toString() + " seconds!";

                //Toast is a small pop up which length is the same as the text length.
                final Toast countDownMsg = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
                countDownMsg.show();

                //Set timer to delete toast
                Timer deleteCountDownMsg = new Timer();
                deleteCountDownMsg.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        countDownMsg.cancel();
                    }
                }, 1000);
            }

            @Override
            public void onFinish() {
                Log.v(TAG, "Ready CountDown Complete!");
                Toast.makeText(getApplicationContext(), "GO!", Toast.LENGTH_SHORT).show();
                setNewMole();
                placeMoleTimer();
            }
        };

        countDownTimer.start();
    }

    private void placeMoleTimer(){

        countDownTimer = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long ms) {
                //Calculate time remaining
                Long timeRemaining = ms/1000;
                Log.v(TAG, "New Mole Location!");
                resetMole();
                setNewMole();
            }

            @Override
            public void onFinish() {
                countDownTimer.start();
            }
        };

        countDownTimer.start();
    }

    public void resetMole()
    {
        holeButtonList.get(randomLocation).setText("0");
    }

    public void setNewMole()
    {

        Random ran = new Random();
        randomLocation = ran.nextInt(9);
        holeButtonList.get(randomLocation).setText("*");
    }
}