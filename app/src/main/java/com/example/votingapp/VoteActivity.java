package com.example.votingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class VoteActivity extends AppCompatActivity {

    TextView fresult;
    Button bsend;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore fStore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        fresult=findViewById(R.id.resulttext);
        bsend=findViewById(R.id.btnsubmit);

        firebaseAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();

        final DocumentReference docRef = fStore.collection("result").document(userId);


        bsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!fresult.getText().toString().isEmpty())
                {
                    String fr = fresult.getText().toString();
                    Map<String, Object> result = new HashMap<>();
                    result.put("Vote Result", fr);
                    result.put("voted userid",userId);

                    docRef.set(result).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                Toast.makeText(VoteActivity.this, "Vote Successfully", Toast.LENGTH_SHORT).show();
                                Intent logIntent = new Intent(VoteActivity.this,exit.class);
                                startActivity(logIntent);

                            } else {
                                Toast.makeText(VoteActivity.this, "Data is Not Inserted", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });


                }
                else {
                    Toast.makeText(VoteActivity.this, "Please Select the Candidate", Toast.LENGTH_SHORT).show();
                    return;
                }


            }
        });


    }

    public void SelectPres(View view)
    {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId())
        {
            case R.id.rbs:
                if(checked) {
                    fresult.setText("SHARAD_PAWAR");
                    fresult.setEnabled(true);
                }
                else
                {
                    fresult.setEnabled(false);
                }
                break;
            case R.id.rbr:
                if(checked) {
                    fresult.setText("RAHUL_GANDHI");
                    fresult.setEnabled(true);
                }
                else
                {
                    fresult.setEnabled(false);
                }
                break;
            case R.id.rbn:
                if(checked) {
                    fresult.setText("NARENDRA_MODI");
                    fresult.setEnabled(true);
                }
                else
                {
                    fresult.setEnabled(false);
                }
                break;
            case R.id.rba:
                if(checked) {
                    fresult.setText("ARVIND_KEJRIVAL");
                    fresult.setEnabled(true);
                }
                else
                {
                    fresult.setEnabled(false);
                }
                break;


        }
    }

}