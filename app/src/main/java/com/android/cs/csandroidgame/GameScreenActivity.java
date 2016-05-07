package com.android.cs.csandroidgame;

import android.content.res.AssetManager;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class GameScreenActivity extends AppCompatActivity implements OnClickListener{

    private Dictionary dictionary;
    private CountDownTimer overallTimer;
    private CountDownTimer turnTimer;

    private int userScore;
    private int compScore;

    private boolean isUserTurn;

    private boolean isGameRunning;
    private boolean isGameOver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        // Initialize game states
        isGameOver = false;
        isUserTurn = false;
        isGameRunning = false;

        userScore = 0;
        compScore = 0;

        // Turn on the action listeners for the buttons
        Button  quit_button = (Button) findViewById(R.id.quit_button);
        quit_button.setOnClickListener(this);

        Button  reset_button = (Button) findViewById(R.id.reset_button);
        reset_button.setOnClickListener(this);

        Button  start_button = (Button) findViewById(R.id.start_button);
        start_button.setOnClickListener(this);

        Button  submit_button = (Button) findViewById(R.id.submit_button);
        submit_button.setOnClickListener(this);

        AssetManager asset = getAssets();
        try{
            InputStream inputStream = asset.open("words.txt");
             dictionary = new Dictionary(inputStream);
        }
        catch(IOException e)
        {
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.quit_button) {
            Intent intent = new Intent(GameScreenActivity.this, MainActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.reset_button) {
            if (isUserTurn) {
                // Stop the timers
                overallTimer.cancel();
                turnTimer.cancel();

                // Reset the dictionary
                dictionary.reset();

                // Reset the game states
                isGameRunning = false;
                isGameOver = false;
                isUserTurn = false;

                // Reset the labels of the views
                TextView overallTime = (TextView) findViewById(R.id.overall_time);
                overallTime.setText("0");

                TextView turnTime = (TextView) findViewById(R.id.turn_time);
                turnTime.setText("0");

                TextView turnLabel = (TextView) findViewById(R.id.turn_label);
                turnLabel.setText("P1 Turn: ");

                TextView fragment = (TextView) findViewById(R.id.fragment);
                fragment.setText("-");

                LinearLayout fragmentLayout = (LinearLayout) findViewById(R.id.fragment_layout);
                fragmentLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT) );

                LinearLayout scoreOneLayout = (LinearLayout) findViewById(R.id.score_one_layout);
                scoreOneLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0) );

                LinearLayout scoreTwoLayout = (LinearLayout) findViewById(R.id.score_two_layout);
                scoreTwoLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0) );

                TextView scoreOneText = (TextView) findViewById(R.id.score_one);
                scoreOneText.setText("" + userScore);

                TextView scoreTwoText = (TextView) findViewById(R.id.score_two);
                scoreTwoText.setText("" + userScore);
            }
        }
        else if (id == R.id.start_button) {
            // If the game is not running,
            if (!isGameRunning) {
                isGameRunning = true;
                isUserTurn = true;

                startOverallTimer();
                startTurnTimer();
            }
        }
        else if (id == R.id.submit_button) {

        }
    }

    public void startOverallTimer() {
        overallTimer = new CountDownTimer(10000, 100) {
            public void onTick(long millisUntilFinished) {
                TextView overallTime = (TextView) findViewById(R.id.overall_time);
                overallTime.setText( Long.toString(millisUntilFinished / 1000) );
            }
            public void onFinish() {
                TextView overallTime = (TextView) findViewById(R.id.overall_time);
                overallTime.setText("Game Over!");
                isGameOver = true;

                LinearLayout fragmentLayout = (LinearLayout) findViewById(R.id.fragment_layout);
                fragmentLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0) );

                LinearLayout scoreOneLayout = (LinearLayout) findViewById(R.id.score_one_layout);
                scoreOneLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT) );

                LinearLayout scoreTwoLayout = (LinearLayout) findViewById(R.id.score_two_layout);
                scoreTwoLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT) );

                isGameOver = false;
                isUserTurn = false;
                isGameRunning = false;

                turnTimer.cancel();

            }
        }.start();
    }
    public void startTurnTimer() {
        turnTimer = new CountDownTimer(5000, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                TextView turnTime = (TextView) findViewById(R.id.turn_time);
                turnTime.setText( "" + (millisUntilFinished / 1000) );
            }

            @Override
            public void onFinish() {
                if (!isGameOver) {

                    TextView turnTime = (TextView) findViewById(R.id.turn_time);

                    TextView turnLabel = (TextView) findViewById(R.id.turn_label);
                    if (isUserTurn) {
                        isUserTurn = false;
                        turnLabel.setText("P2 Time: ");
                    } else {
                        isUserTurn = true;
                        turnLabel.setText("P1 Time: ");
                    }
                    startTurnTimer();
                }

            }
        }.start();
    }
}
