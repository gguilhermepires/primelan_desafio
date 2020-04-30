package com.example.desafio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 6000;
    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        if (player == null) {
            player = MediaPlayer.create(SplashScreenActivity.this, R.raw.tense);

        }
        player.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent home = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(home);
                finish();
                if(player!=null){
                    player.stop();
                    player.release();
                    player=null;
                }
            }
        }, SPLASH_TIME_OUT);

    }
}
