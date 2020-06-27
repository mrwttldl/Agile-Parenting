package com.example.parenting.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.parenting.R;
import com.example.parenting.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    private View ProfileView;
    EditText name, mail, password, location, phoneNumber;
    Button save;
    FirebaseAuth mFirebaseAuth;
    DatabaseReference usersDatabase = FirebaseDatabase.getInstance().getReference("Users");
    DatabaseReference guncellenecekKullanici;

    User user;

    private DatabaseReference mFirebaseDatabase, myRef;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ProfileView = inflater.inflate(R.layout.fragment_profile, container, false);
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();
        myRef = mFirebaseDatabase.child("Users");
        init();

        FirebaseUser mFirebaseUser = mFirebaseAuth.getInstance().getCurrentUser();
        final String userEmail = mFirebaseUser.getEmail();

        Query query = usersDatabase.orderByChild("email").equalTo(userEmail);


        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                user = new User();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {


                    user.setId(ds.child("id").getValue(String.class));
                    user.setEmail(ds.child("email").getValue(String.class));
                    user.setName(ds.child("name").getValue(String.class));
                    user.setLocation(ds.child("location").getValue(String.class));
                    user.setPhoneNumber(ds.child("phoneNumber").getValue(String.class));

                    guncellenecekKullanici = myRef.child(user.getId());

                    name.setText(user.getName());
                    mail.setText(user.getEmail());
                    location.setText(user.getLocation());
                    phoneNumber.setText(user.getPhoneNumber());

                    save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            user.setEmail(mail.getText().toString());
                            user.setName(name.getText().toString());
                            user.setLocation(location.getText().toString());
                            user.setPhoneNumber(phoneNumber.getText().toString());

                            guncellenecekKullanici.setValue(user);
                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return ProfileView;
    }


    public void init() {

        name = ProfileView.findViewById(R.id.editTextUserName);
        mail = ProfileView.findViewById(R.id.editTextEmailUpdate);
        password = ProfileView.findViewById(R.id.editTextPasswordUpdate);
        location = ProfileView.findViewById(R.id.editTextLocationUpdate);
        phoneNumber = ProfileView.findViewById(R.id.editTextPhoneUpdate);

        save = ProfileView.findViewById(R.id.buttonUpdate);

    }

}
