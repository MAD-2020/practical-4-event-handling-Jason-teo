package com.example.whack_a_mole20;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //Declare Variable
    private static final String TAG = "Whack-A-Mole";

    private TextView resultTextView;
    //Basic Variables
    private Button Button1;
    private Button Button2;
    private Button Button3;

    private List<Button> buttonList = new ArrayList<>();
    private Integer randomLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextView = (TextView)findViewById(R.id.resultTextView);
        Button1 = (Button)findViewById(R.id.button1);//using id of the buttons in xml
        buttonList.add(Button1);
        Button2 = (Button)findViewById(R.id.button2);
        buttonList.add(Button2);
        Button3 = (Button)findViewById(R.id.button3);
        buttonList.add(Button3);
        Log.v(TAG, "Finished Pre-initialisation.");//just to inform user
    }
    //Loaded
    @Override
    protected void onStart() {
        super.onStart();

        setNewMole();

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button buttonPressed = (Button) v;
                Log.v(TAG,"Reached");
                switch (buttonList.indexOf(buttonPressed)) {
                    case 0:
                        Log.v(TAG,"Button 1 Clicked!");
                        break;
                    case 1:
                        Log.v(TAG,"Button 2 Clicked!");
                        break;
                    case 2:
                        Log.v(TAG,"Button 3 Clicked!");
                        break;
                    default:
                        Log.v(TAG,"Unknown Button pressed. Found within ButtonList");
                }

                Integer score = Integer.parseInt(resultTextView.getText().toString());
                switch (buttonPressed.getText().toString()) {
                    case "0":
                        Log.v(TAG,"Missed, score deducted!");
                        score -= 1;//deduct 1 from score
                        resultTextView.setText(score.toString());
                        break;
                    case "*":
                        Log.v(TAG,"Hit, score added!");
                        score += 1;//Add 1 to score
                        resultTextView.setText(score.toString());
                        break;
                    default:
                        Log.v(TAG,"Unknown button pressed, no case for it's text set.");
                }
                if (score  == 10){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                    builder.setTitle("Warning! Insane Whack-A-Mole Incoming!");
                    builder.setMessage("Would you like to advance to advanced mode?");

                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing but close the dialog
                            Log.v(TAG,"User accepted");
                            Intent intent = new Intent(MainActivity.this, WhackAMole2.class);
                            startActivity(intent);
                            dialog.dismiss();
                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing
                            Log.v(TAG,"User decline");
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();

                }

                resetMole();
                setNewMole();
            }
        };
        Button1.setOnClickListener(listener);
        Button2.setOnClickListener(listener);
        Button3.setOnClickListener(listener);

        Log.v(TAG,"Starting Gui.");

    }
    public void setNewMole()
    {
        Random ran = new Random();
        randomLocation = ran.nextInt(3);
        buttonList.get(randomLocation).setText("*");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG,"Resuming.");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(TAG,"Pausing.");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG,"Destroying.");
    }

    public void resetMole()
    {
        buttonList.get(randomLocation).setText("0");
    }
}

