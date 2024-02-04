package com.gard.urguard;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity2 extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    private ImageButton callbut;
    private ImageButton sosbut;
    private ImageButton opt;
    FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        opt = (ImageButton) findViewById(R.id.opt);
        opt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, MainActivity7.class);
                startActivity(intent);
            }
        });

        callbut = (ImageButton) findViewById(R.id.callbut);
        final MediaPlayer np = MediaPlayer.create(this, R.raw.ringing);
        callbut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity3.class);
                startActivity(intent);
                np.start();

            }
        });

        sosbut = (ImageButton) findViewById(R.id.sosbut);
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.sos);
        sosbut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity4.class);
                startActivity(intent);
                mp.start();
            }
        });
    }

}