package com.example.votingapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class auth extends AppCompatActivity {

    public static final String TAG = "TAG";
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    EditText phoneNumber, codeEnter;
    Button nextBtn;
    ProgressBar progressBar;
    TextView state;
    CountryCodePicker codePicker;

    String verificationID;
    PhoneAuthProvider.ForceResendingToken token;
    Boolean verificationInProgress = false;
    private long pressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        phoneNumber = (EditText) findViewById(R.id.phone);
        codeEnter = (EditText)findViewById(R.id.codeEnter);
        nextBtn = (Button) findViewById(R.id.nextBtn);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        state = (TextView) findViewById(R.id.state);

        codePicker =(CountryCodePicker)findViewById(R.id.ccp);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!verificationInProgress){
                    if (! phoneNumber.getText().toString().isEmpty() && phoneNumber.getText().toString().length() == 10){

                        String phonenum = "+" + codePicker.getSelectedCountryCode()+ phoneNumber.getText().toString();
                        Log.d(TAG,"onClick : Phone No -> "+phonenum);
                        progressBar.setVisibility(View.VISIBLE);
                        state.setText("OTP is Send");
                        state.setVisibility(View.VISIBLE);

                        requestOTP(phonenum);

                    }
                    else {
                        phoneNumber.setError("Phone Number is Not Valid");
                    }
                }
                else {
                    String userOTP = codeEnter.getText().toString();
                    if(!userOTP.isEmpty() && userOTP.length() == 6){

                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID, userOTP);
                        verifyAuth(credential);

                    }
                    else {
                        codeEnter.setError("Please Enter Valid OTP");
                    }
                }
            }
        });

    }
//    protected void onStart(){
//        super.onStart();
//
//        if (fAuth.getCurrentUser() != null){
//            progressBar.setVisibility(View.VISIBLE);
//            state.setText("Checking...");
//            state.setVisibility(View.VISIBLE);
//            checkUserProfille();
//        }
//    }




    private void verifyAuth(PhoneAuthCredential credential) {
        fAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    checkUserProfille();
                    Toast.makeText(com.example.votingapp.auth.this, "Authentication is Successfull", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(com.example.votingapp.auth.this, "Authentication is Failed", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    private void checkUserProfille() {
        DocumentReference docRef = fStore.collection("users").document(fAuth.getCurrentUser().getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    startActivity(new Intent(getApplicationContext(), User_DetailsActivity.class));
                    finish();
                }else {
                    startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
                    finish();
                }

            }
        });

    }

    private void requestOTP(String phonenum) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phonenum, 60L, TimeUnit.SECONDS, this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                progressBar.setVisibility(View.GONE);
                state.setVisibility(View.GONE);
                codeEnter.setVisibility(View.VISIBLE);

                verificationID = s;
                token = forceResendingToken;

                nextBtn.setText("Verify");

                verificationInProgress = true;
            }

            @Override
            public void onCodeAutoRetrievalTimeOut(String s) {
                super.onCodeAutoRetrievalTimeOut(s);
                Toast.makeText(com.example.votingapp.auth.this, "OTP Expired Re-Request The OTP", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                verifyAuth(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                Toast.makeText(com.example.votingapp.auth.this, "Cannot Creat Account"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    public void onBackPressed() {

        if (pressedTime + 2000 > System.currentTimeMillis()) {
            ////
            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory( Intent.CATEGORY_HOME );
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
            System.exit(1);
            ///
            ;
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }


}