package com.example.puzzle_test_v001;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PrincipalActivity extends AppCompatActivity {
    private Button playButton, scoresButton;
    ImageView img_puzzle;
    TextView title;
    Animation desota, dedalt, girar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        Intent intent = new Intent(PrincipalActivity.this, BgSoundService.class);
        startService(intent);

        Intent musicservice = new Intent(PrincipalActivity.this, BgSoundService.class);
        musicservice.putExtra("operation","start");
        startService(musicservice);

        if(BgSoundService.SingletonServiceManager.isBackgroundSoundServiceRunning()){
            Log.println(Log.WARN,"FUNCIONA","FUNCIONA");
        }else{
            Log.println(Log.ERROR,"ERROOR","ERROOOOOOOOR");
        }


        title = (TextView)findViewById(R.id.title);
        playButton = (Button) findViewById(R.id.playButton);
        scoresButton = (Button) findViewById(R.id.scoresButton);
        img_puzzle = (ImageView) findViewById(R.id.img_puzzle);


        dedalt = AnimationUtils.loadAnimation(this,R.anim.dedalt);
        desota = AnimationUtils.loadAnimation(this,R.anim.desota);
        girar = AnimationUtils.loadAnimation(this,R.anim.girar);


        title.setAnimation(AnimationUtils.loadAnimation(this,R.anim.dedalt));
        playButton.setAnimation(AnimationUtils.loadAnimation(this,R.anim.desota));
        scoresButton.setAnimation(AnimationUtils.loadAnimation(this,R.anim.desota));
        img_puzzle.setAnimation(AnimationUtils.loadAnimation(this,R.anim.girar));

        // Button "Jugar"
        playButton = findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(PrincipalActivity.this, PuzzleActivity.class);
                startActivity(intent);
            }
        });

        // Button "Puntuacions"
        scoresButton = findViewById(R.id.scoresButton);
        scoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(PrincipalActivity.this, ScoreboardsActivity.class);
                startActivity(intent);
            }
        });
    }
}
