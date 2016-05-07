package com.android.cs.csandroidgame;

import android.content.res.AssetManager;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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
    private boolean isComputerTurn;

    private boolean isGameRunning;
    private boolean isGameOver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

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

        }
        else if (id == R.id.start_button) {
            // If the game is not running,
            if (!isGameRunning) {
                startOverallTimer();
            }
        }
        else if (id == R.id.submit_button) {

        }
    }

    public void startOverallTimer() {
        overallTimer = new CountDownTimer(20000, 1000) {
            public void onTick(long millisUntilFinished) {
                TextView overallTime = (TextView) findViewById(R.id.overall_time);
                overallTime.setText( Long.toString(millisUntilFinished / 1000) );
            }
            public void onFinish() {
                TextView overallTime = (TextView) findViewById(R.id.overall_time);
                overallTime.setText("Game Over!");
            }
        }.start();
    }
}
