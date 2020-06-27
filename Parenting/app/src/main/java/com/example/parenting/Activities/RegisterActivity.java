package com.example.parenting.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parenting.R;
import com.example.parenting.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    EditText emailId, passsword, passwordAgain, name, surname;
    Button btnSignUp;
    TextView tvSignIn;

    FirebaseAuth mFirebaseAuth;
    FirebaseDatabase mDatabase;
    DatabaseReference myDatabaseReference = FirebaseDatabase.getInstance().getReference("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mDatabase = FirebaseDatabase.getInstance();

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.editTextEmailKayit);
        passsword = findViewById(R.id.editTextPasswordKayit);
        btnSignUp = findViewById(R.id.buttonSignUp);
        tvSignIn = findViewById(R.id.textSignIn);
        passwordAgain = findViewById(R.id.editTextPasswordKayitAgain);
        name = findViewById(R.id.editTextUserName);
        // surname=findViewById(R.id.editTextUserSurname);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailId.getText().toString();
                String pwd = passsword.getText().toString();

                if (email.isEmpty()) {
                    emailId.setError("Lütfen email adresi giriniz");
                    emailId.requestFocus();

                } else if (pwd.isEmpty()) {
                    passsword.setError("Lütfen sifre giriniz");
                    passsword.requestFocus();
                } else if (!(email.isEmpty() && pwd.isEmpty())) {
                    mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Kayıt Başarısız! Bir daha Deneyin.", Toast.LENGTH_SHORT).show();

                            } else {
                                //Database uye kayıdı burada alınıryor

                                User user = new User();
                                String id = myDatabaseReference.push().getKey();

                                user.setEmail(emailId.getText().toString());

                                user.setName(name.getText().toString());
                                user.setId(id);
                                myDatabaseReference.child(id).setValue(user);


                                FirebaseUser currentUs = FirebaseAuth.getInstance().getCurrentUser();


                               UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                       .setDisplayName(name.getText().toString()).build();

                                currentUs.updateProfile(profileUpdates)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                   // Log.d(TAG, "User profile updated.");
                                                    System.out.println("kullanıcı isimi eklendi");
                                                }
                                            }
                                        });


                                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                            }
                        }
                    });

                } else {
                    Toast.makeText(RegisterActivity.this, "Hata Oluştu!", Toast.LENGTH_SHORT).show();

                }
            }
        });

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });


    }
}
