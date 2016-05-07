package com.android.cs.csandroidgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.google.android.gms.*;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.BaseGameUtils;

public class MainActivity extends AppCompatActivity implements OnClickListener{

    private boolean mResolvingConnectionFailure = false;
    private boolean mAutoStartSignInflow = true;
    private boolean mSignInClicked = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button computer_mode_button = (Button) findViewById(R.id.computer_mode);
        computer_mode_button.setOnClickListener(this);

        Button multiplayer_mode_button = (Button) findViewById(R.id.multiplayer_mode);
        multiplayer_mode_button.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.computer_mode) {

            // Create a bundle to send the game mode to use
            Bundle bundle = new Bundle();
            bundle.putString("mode", "computer");

            Intent intent = new Intent(MainActivity.this, GameScreenActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        else if (id == R.id.multiplayer_mode) {

            // Create a bundle to send the game mode to use
            Bundle bundle = new Bundle();
            bundle.putString("mode", "multi");

            Intent intent = new Intent(MainActivity.this, GameScreenActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}
