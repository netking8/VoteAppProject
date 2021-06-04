package com.example.votingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class VoteDone extends AppCompatActivity {


    String candi_name;
    Integer draw;
    ImageView imageView;
    TextView textView;
    public static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_done);

        candi_name = getIntent().getStringExtra("candi_name");
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);

        Toast.makeText(this, "" + candi_name, Toast.LENGTH_SHORT).show();

        if (candi_name.equals("aitc")) {
            imageView.setImageResource(R.drawable.aitc);
        } else if (candi_name.equals("bjp")) {
            imageView.setImageResource(R.drawable.bjp);
        } else if (candi_name.equals("inc")) {
            imageView.setImageResource(R.drawable.inc);
        } else if (candi_name.equals("ncp")) {
            imageView.setImageResource(R.drawable.ncp);
        } else {
            imageView.setImageResource(R.drawable.bsp);
        }

        textView.setText("Your vote submitted to " + candi_name.toUpperCase());

        new Handler().postDelayed(() -> {
            Intent homeIntent = new Intent(VoteDone.this, auth.class);
            startActivity(homeIntent);
            finish();
        }, SPLASH_TIME_OUT);
//     C:\Users\ARGS\AndroidStudioProjects\VotingApp\app\build\outputs\apk\debug
    }
}