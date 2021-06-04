package com.example.votingapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private EditText mEditDate;
    private DatePickerDialog.OnDateSetListener mDateSetListner;

    EditText mName,mAadhaar,mEmail,mvoterId;
    Button mSubmit;
    TextView mGender,mMobile;
    RadioButton mMale, mFemale, mOther;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore fStore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mName = findViewById(R.id.enter_name);
        mAadhaar = findViewById(R.id.enter_aadhar);
        mMobile = findViewById(R.id.enter_contact);
        mEmail = findViewById(R.id.enter_email);
        mEditDate = findViewById(R.id.enter_DOB);
        mGender = findViewById(R.id.tGender);
        mMale = findViewById(R.id.male);
        mFemale = findViewById(R.id.female);
        mOther = findViewById(R.id.other);
        mvoterId = findViewById(R.id.enter_overid);

        mSubmit = findViewById(R.id.btnSubmit);

//        Toolbar toolbar1 = findViewById(R.id.toolbar_CheckDet);

//        setSupportActionBar(toolbar1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        firebaseAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();

        final DocumentReference docRef = fStore.collection("users").document(userId);
        mMobile.setText(firebaseAuth.getCurrentUser().getPhoneNumber());

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mName.getText().toString().isEmpty() && !mAadhaar.getText().toString().isEmpty() && !mEmail.getText().toString().isEmpty()
                        && !mMobile.getText().toString().isEmpty() && !mEditDate.getText().toString().isEmpty() && !mGender.getText().toString().isEmpty()){
                    String name = mName.getText().toString();
                    String aadhaar = mAadhaar.getText().toString();
                    String voterID = mvoterId.getText().toString();
                    String email = mEmail.getText().toString();
                    String contact = mMobile.getText().toString();
                    String date = mEditDate.getText().toString();
                    String gender = mGender.getText().toString();


//                    VoterInfo voterInfo = new VoterInfo();
                    Map<String,Object> user = new HashMap<>();
                    user.put("fullName",name);
                    user.put("aadhaar",aadhaar);
                    user.put("voterID",voterID);
                    user.put("emailId",email);
                    user.put("mobileNumber",contact);
                    user.put("dateofBirth",date);
                    user.put("gender",gender);



                    docRef.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                startActivity(new Intent(getApplicationContext(), com.example.votingapp.User_DetailsActivity.class));
                                finish();
                            }else {
                                Toast.makeText(com.example.votingapp.RegisterActivity.this, "Data is Not Inserted", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }else {
                    Toast.makeText(com.example.votingapp.RegisterActivity.this, "All Fiedls Are Reqiued", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });




        mEditDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH );

                DatePickerDialog dialog = new DatePickerDialog(
                        com.example.votingapp.RegisterActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListner,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });
        mDateSetListner = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month +1;

                Log.d(TAG,"onDateSet: yyyy/mm/dd: "+year+" / " + month+" / "+dayOfMonth);
                String date = dayOfMonth+" / "+month+" / "+year;
                mEditDate.setText(date);

            }
        };
    }
}
