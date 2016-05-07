package com.android.cs.csandroidgame;

import android.content.res.AssetManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

public class GameScreenActivity extends AppCompatActivity implements OnClickListener{

    private Dictionary dictionary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
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

        }
        else if (id == R.id.submit_button) {

        }
    }
}
