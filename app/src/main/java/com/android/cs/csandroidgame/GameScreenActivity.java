package com.android.cs.csandroidgame;

import android.content.res.AssetManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;

public class GameScreenActivity extends AppCompatActivity implements OnClickListener{

    private Dictionary dictionary;
    private CountDownTimer overallTimer;
    private CountDownTimer turnTimer;

    private boolean computerOn;
    private int userScore;
    private int compScore;

    private boolean isUserTurn;
    private boolean isGameRunning;
    private boolean isGameOver;

    private TextView fragment;
    private  EditText input_text;
    private  TextView turnLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        // Initialize game states
        isGameOver = false;
        isUserTurn = false;
        isGameRunning = false;

        userScore=0;
        compScore=0;
        computerOn=true;

        Bundle b = new Bundle();
        b = getIntent().getExtras();
        String name = b.getString("mode");
        if (name.equals("multi") )
        {
            computerOn = false;
        }

        // Turn on the action listeners for the buttons
        Button  quit_button = (Button) findViewById(R.id.quit_button);
        quit_button.setOnClickListener(this);

        Button  reset_button = (Button) findViewById(R.id.reset_button);
        reset_button.setOnClickListener(this);

        Button  start_button = (Button) findViewById(R.id.start_button);
        start_button.setOnClickListener(this);

        Button  submit_button = (Button) findViewById(R.id.submit_button);
        submit_button.setOnClickListener(this);

        fragment = (TextView) findViewById(R.id.fragment);
        turnLabel = (TextView) findViewById(R.id.turn_label);
        input_text =(EditText) findViewById(R.id.edit_text);


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
            // Stop the timers
            overallTimer.cancel();

            turnTimer.cancel();

            //reset variables
            isGameOver = false;
            isUserTurn = false;
            isGameRunning = false;

            userScore = 0;
            compScore = 0;

            // Reset the dictionary
            dictionary.reset();

            // Reset the labels of the views
            TextView overallTime = (TextView) findViewById(R.id.overall_time);
            TextView turnTime = (TextView) findViewById(R.id.turn_time);
            TextView turnLabel = (TextView) findViewById(R.id.turn_label);
            TextView fragment = (TextView) findViewById(R.id.fragment);
            TextView previousWord = (TextView) findViewById(R.id.prev_word);

            overallTime.setText("0");
            turnTime.setText("0");
            turnLabel.setText("P1 Turn: ");
            fragment.setText("-");
            previousWord.setText("-");
            previousWord.setTextColor(Color.parseColor("#000000"));

            // Reset the scores in the text views
            TextView scoreOneText = (TextView) findViewById(R.id.score_one);
            TextView scoreTwoText = (TextView) findViewById(R.id.score_two);

            scoreOneText.setText("" + userScore);
            scoreTwoText.setText("" + compScore);

            LinearLayout fragmentLayout = (LinearLayout) findViewById(R.id.fragment_layout);
            LinearLayout scoreOneLayout = (LinearLayout) findViewById(R.id.score_one_layout);
            LinearLayout scoreTwoLayout = (LinearLayout) findViewById(R.id.score_two_layout);

            scoreTwoLayout.setVisibility(View.GONE);
            scoreOneLayout.setVisibility(View.GONE);
            fragmentLayout.setVisibility(View.VISIBLE);

        } else if (id == R.id.start_button) {
            // If the game is not running,
            if (!isGameRunning) {
                isGameRunning = true;
                isUserTurn = true;

                startOverallTimer();
                startTurnTimer();
                input_text.setFocusable(true);
                input_text.setClickable(true);


                //change to random letter
                fragment.setText(dictionary.randomStart());
            }
        } else if (id == R.id.submit_button) {
            if (isGameRunning) {
                userTurn();
            }
        }
    }
    public void computerTurn()
    {
        //get the word from input
        String prefix = fragment.getText().toString();
        String input = dictionary.getPossibleWord(prefix);
        TextView lastWord= (TextView) findViewById(R.id.prev_word);
        int color=Color.GREEN;
        compScore++;
        lastWord.setText(input);
        lastWord.setTextColor(color);

        input_text.setFocusable(true);
        input_text.setClickable(true);

    }

    public void userTurn()
    {
        //get the word from input
        String input = input_text.getText().toString();
        input_text.setText("");
        TextView lastWord= (TextView) findViewById(R.id.prev_word);
        int color=Color.RED;
        //assume the word is already used or not a word

        String prefix = fragment.getText().toString();


        //dont let the user submit word when
        //not his turn and playing with computer
        Log.d("booleans", "___________________________");
        Log.d("booleans", "isUserTurn: " + isUserTurn);
        Log.d("booleans", "computerOn: " + computerOn);
        Log.d("booleans", "isGameRunning: " + isGameRunning);
        Log.d("booleans", "isGameOver: " + isGameOver);
        Log.d("booleans", "Human word" + input);
        //if input is a word then and starts with prefix display green
        //add to score
        //change player labels

        if(dictionary.isWord(input) && input.startsWith(prefix) && !dictionary.isWordTaken(input))
        {
            color = Color.GREEN;
            dictionary.removeWord(input);
               userScore++;
        }
        lastWord.setText(input);
        Log.d("booleans", "word color" + color);
        lastWord.setTextColor(color);

        //change player
        turnTimer.onFinish();
        startTurnTimer();
        input_text.setFocusable(false);
        input_text.setClickable(false);

    }
    public void startOverallTimer() {
        overallTimer = new CountDownTimer(21000, 500) {
            public void onTick(long millisUntilFinished) {
                TextView overallTime = (TextView) findViewById(R.id.overall_time);
                overallTime.setText( Long.toString(millisUntilFinished / 1000) );
            }
            public void onFinish() {


                TextView overallTime = (TextView) findViewById(R.id.overall_time);
                overallTime.setText("Game Over!");
                isGameOver = true;
                isGameRunning = false;

                // Turn off the turn timer
                turnTimer.onFinish();


                // Update the visibility of the fragment and score layouts
                LinearLayout fragmentLayout = (LinearLayout) findViewById(R.id.fragment_layout);
                LinearLayout scoreOneLayout = (LinearLayout) findViewById(R.id.score_one_layout);
                LinearLayout scoreTwoLayout = (LinearLayout) findViewById(R.id.score_two_layout);

                fragmentLayout.setVisibility(View.GONE);
                scoreOneLayout.setVisibility(View.VISIBLE);
                scoreTwoLayout.setVisibility(View.VISIBLE);

                // Update the score of the players
                TextView scoreOneText = (TextView) findViewById(R.id.score_one);
                TextView scoreTwoText = (TextView) findViewById(R.id.score_two);

                scoreOneText.setText("" + userScore);
                scoreTwoText.setText("" + compScore);
            }
        }.start();
    }
    public void startTurnTimer() {
        turnTimer = new CountDownTimer(10000, 500) {
            @Override
            public void onTick(long millisUntilFinished) {
                TextView turnTime = (TextView) findViewById(R.id.turn_time);
                turnTime.setText( "" + (millisUntilFinished / 1000) );
            }

            @Override
            public void onFinish() {
                cancel();
                TextView turnTime = (TextView) findViewById(R.id.turn_time);

                if (isUserTurn) {
                    isUserTurn = false;
                    turnLabel.setText("P2 Time: ");
                    computerTurn();
                    input_text.setText("");

                }
                else {
                    isUserTurn = true;
                    turnLabel.setText("P1 Time: ");
                    input_text.setText("");

                }

                if (!isGameOver) {

                    startTurnTimer();
                }
            }
        }.start();
    }
}
