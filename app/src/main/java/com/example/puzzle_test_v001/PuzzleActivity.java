package com.example.puzzle_test_v001;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.time.Duration;
import java.time.Instant;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;


public class PuzzleActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static String username;
    private Bitmap imageBitmap = null;
    private PuzzleBoardView boardView;
    public TextView crono;
    public TextView txV = null;
    public int num = 0;
    final Handler handler = new Handler();
    public int score;
    public Instant startGame;
    public Instant finishGame;
    public long gameDurationSeconds;
    public MediaPlayer mp;


    //FirebaseDatabase firebaseDatabase;
    //DatabaseReference databaseReference;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);




        //Asking for username to the user
        // Input username on start a game
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Nom del jugador");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        alertDialog.setView(input);

        // Set up the buttons
        alertDialog.setPositiveButton("Acceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                username = input.getText().toString();

                final Handler handler = new Handler();

                // Get time when starts the game

            }
        });

        alertDialog.setNegativeButton("Cancel·lar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Intent intent = new Intent(PuzzleActivity.this, PrincipalActivity.class);
                startActivity(intent);
            }
        });

        alertDialog.setCancelable(false);
        alertDialog.show();


        // This code programmatically adds the PuzzleBoardView to the UI.
        RelativeLayout container = (RelativeLayout) findViewById(R.id.puzzle_container);
        boardView = new PuzzleBoardView(this);
        // Some setup of the view.
        boardView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        container.addView(boardView);
    }


    public void dispatchTakePictureIntent(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            boardView.initialize(imageBitmap);

            MediaStore.Images.Media.insertImage(getContentResolver(), imageBitmap, "Foto Puzzle Game" , "Foto Board PuzzleGame ");

            Toast toast = Toast.makeText(getApplicationContext(), "Imatge guardada a la galeria", Toast.LENGTH_LONG);
            toast.show();

            startGame = Instant.now();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    chrono(startGame);
                    handler.postDelayed(this, 1000);
                }
            }, 1000);

            mp = MediaPlayer.create(this, R.raw.f1);
            mp.start();


        }
    }

    public void shuffleImage(View view) {
        boardView.shuffle();
    }

    public void solve(View view) {
        boardView.solve();

    }

    public void stop(View view) {
        mp.stop();

    }


    public void chrono(Instant start) {
        Instant now = Instant.now();
        long seconds = Duration.between(start,now).getSeconds();
        crono = (TextView)findViewById(R.id.timeTextView);
        crono.setText("Temps: "+String.valueOf(seconds));

    }

    public void goToAfterGameScoreActivity(){
        // Passing username, score and game duration to AfterGameScoreActivity
        Intent intent = new Intent(PuzzleActivity.this, AfterGameScoreActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("score", score);
        intent.putExtra("gameDuration", gameDurationSeconds);
        startActivity(intent);
        finish();
    }
}
