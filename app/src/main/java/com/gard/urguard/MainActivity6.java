package com.gard.urguard;

import static android.widget.ProgressBar.*;

import android.app.MediaRouteButton;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity6 extends AppCompatActivity {

    private Button log;
    private Button btnReg;
    private EditText editTextfullname, editTextemail, editTextpassword;

    EditText fullname_input, email_input, password_input;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    private MediaRouteButton progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        editTextfullname = (EditText) findViewById(R.id.fullname_input);
        editTextemail = (EditText) findViewById(R.id.email_input);
        editTextpassword = (EditText) findViewById(R.id.fullname_input);
        fullname_input = findViewById(R.id.fullname_input);
        email_input = findViewById(R.id.email_input);
        password_input = findViewById(R.id.password_input);
        btnReg = (Button) findViewById(R.id.btnReg);
        log = (Button) findViewById(R.id.log);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerforAuth();
            }
        });


        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(MainActivity6.this, MainActivity.class);
                startActivity(intent);

            }
        });


    }

    private void PerforAuth() {
        String fullname = fullname_input.getText().toString();
        String email = email_input.getText().toString();
        String password = password_input.getText().toString();

        if (fullname.isEmpty()) {
            fullname_input.requestFocus();
            progressDialog.dismiss();
            makeUserStay();
            Toast.makeText(MainActivity6.this, "Fullname Cannot be Empty!", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.VISIBLE);
        } else if (!email.matches(emailPattern)) {
            email_input.requestFocus();
            progressDialog.dismiss();
            makeUserStay();
            Toast.makeText(MainActivity6.this, "Email invalid!", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.VISIBLE);
        } else if(email.isEmpty()){
            email_input.requestFocus();
            progressDialog.dismiss();
            makeUserStay();
            Toast.makeText(MainActivity6.this, "Email Cannot be Empty!", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.VISIBLE);
        }else if(password.length() < 8){
            password_input.requestFocus();
            progressDialog.dismiss();
            makeUserStay();
            Toast.makeText(MainActivity6.this, "Password Length Must Contain Atleast 8 Characters", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.VISIBLE);
        }else if (password.isEmpty()) {
            password_input.requestFocus();
            progressDialog.dismiss();
            makeUserStay();
            Toast.makeText(MainActivity6.this, "Password Cannot be Empty! ", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.VISIBLE);
        }else {
            progressDialog.setMessage("Registration in Progress...");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        User user = new User(fullname, email);
                        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(MainActivity6.this, "Registration Sucessfull!", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.VISIBLE);
                                    sendUsertoLoginPage();
                                }else{
                                    Toast.makeText(MainActivity6.this, "Registration Failed!", Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });
                    } else {
                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                            makeUserStay();
                            Toast.makeText(getApplicationContext(), "Email Already Registered!", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }

    private void sendUsertoLoginPage() {
        Intent intent = new Intent(MainActivity6.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void makeUserStay() {
        Intent intent = new Intent(MainActivity6.this, MainActivity6.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}

