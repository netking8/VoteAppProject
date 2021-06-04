package com.example.votingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class User_DetailsActivity extends AppCompatActivity {

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    TextView mName, mEmail, mPhone, mDate, mAadhaar, mVoterid;
    Button bGOTo;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        mName = findViewById(R.id.profileFullName);
        mAadhaar = findViewById(R.id.tAadharNumber);
        mVoterid = findViewById(R.id.tvoterid);
        mPhone = findViewById(R.id.tMobileNumber);
        mDate = findViewById(R.id.tDateofBirth);
        mEmail = findViewById(R.id.tEmailAdd);
        bGOTo = findViewById(R.id.btnGoTo);

        Toolbar toolbar1 = findViewById(R.id.toolbar_CheckDet);

//        setSupportActionBar(toolbar1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


//        bGOTo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Intent gotoIntent = new Intent(com.example.myapplication.User_DetailsActivity.this,LoginDashBoardActivity.class);
//               // startActivity(gotoIntent);
//            }
//        });


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        DocumentReference docRf = fStore.collection("users").document(fAuth.getCurrentUser().getUid());
        docRf.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){

                    mName.setText(documentSnapshot.getString("fullName"));
                    mAadhaar.setText(documentSnapshot.getString("aadhaar"));
                    mVoterid.setText(documentSnapshot.getString("voterID"));
                    mEmail.setText(documentSnapshot.getString("emailId"));
                    mDate.setText(documentSnapshot.getString("dateofBirth"));
                    mPhone.setText(documentSnapshot.getString("mobileNumber"));


                }


            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), com.example.votingapp.auth.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void go(View view) {
//        Toast.makeText(this, "New activity", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(),VoteActivity.class));
    }
}
