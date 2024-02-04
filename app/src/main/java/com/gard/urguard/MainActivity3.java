package com.gard.urguard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity3 extends AppCompatActivity {
    MediaPlayer mediaPlayer;

    private ImageButton tutup;
    private ImageButton angkat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        tutup = (ImageButton) findViewById(R.id.tutup);
        tutup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity2.class);
                startActivity(intent);
                mediaPlayer.stop();

            }
        });
        angkat = (ImageButton) findViewById(R.id.angkat);
        angkat.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity5.class);
                startActivity(intent);
                mediaPlayer.stop();

            }
        });

    }

}