package com.gard.urguard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private Button regis;
    EditText email_input, password_input;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email_input = findViewById(R.id.email_input);
        password_input = findViewById(R.id.password_input);
        button = (Button) findViewById(R.id.button);
        regis = (Button) findViewById(R.id.regis);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                PerfoLogin();
            }
        });

        regis.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(MainActivity.this, MainActivity6.class);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser() != null){
            startActivity(new Intent(this, MainActivity2.class));
        }
    }

    private Boolean PerfoLogin() {
        String email = email_input.getText().toString();
        String password = password_input.getText().toString();

       if (!email.matches(emailPattern)) {
            email_input.setError("Email Invalid!");
            email_input.requestFocus();
            return false;
        }else if(email.isEmpty()){
           email_input.setError("Email Cannot be Empty!");
           email_input.requestFocus();
           return false;
       }else if (password.isEmpty() || password.length() < 8) {
            password_input.setError("Password Cannot be Empty and Must Contain 8 Characters!");
            password_input.requestFocus();
            return false;
        } else {
            progressDialog.setMessage("Login in Progress...");
            progressDialog.setTitle("Login");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

           mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
               @Override
               public void onComplete(@NonNull Task<AuthResult> task) {
                   if (task.isSuccessful()){
                       finish();
                       progressDialog.dismiss();
                       sendUsertoHomePage();
                       Toast.makeText(MainActivity.this, "Login Sucessfull!", Toast.LENGTH_SHORT).show();
                   }
               }
           });
           return true;
    }


    }



    private void sendUsertoHomePage() {
        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    }