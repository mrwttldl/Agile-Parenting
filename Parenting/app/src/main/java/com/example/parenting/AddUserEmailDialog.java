package com.example.parenting;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.parenting.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Queue;

public class AddUserEmailDialog extends AppCompatDialogFragment {

    EditText editAddUserEmail;
    Spinner spinnerMembers;

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Boards");
    DatabaseReference usersDatabase = FirebaseDatabase.getInstance().getReference("Users");
    DatabaseReference memberRefi;
    DatabaseReference myRef;


    String tableRefference;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_adduser, null);

        Bundle bundle = getArguments();
        if (bundle != null) {
            tableRefference = bundle.getString("tableRefference");

        }

        myRef = mDatabase.child(tableRefference);
        memberRefi = mDatabase.child(tableRefference + "/member");

        //  mFirebaseAuth=FirebaseAuth.getInstance();
        editAddUserEmail = view.findViewById(R.id.edit_addUserEmail);


        builder.setView(view).setTitle("Kullanıcı Ekle").setNegativeButton("Iptal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //buraya bişey koymaya gerek yok
            }
        })
                .setPositiveButton("Ekle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        final String emailOfUser = editAddUserEmail.getText().toString();


                        Query query = usersDatabase.orderByChild("email").equalTo(emailOfUser);


                        query.addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                    final User user = new User();
                                    //  System.out.println("myVALUE              ::::: " + ds.getValue());
                                    user.setId(ds.child("id").getValue(String.class));
                                    user.setEmail(ds.child("email").getValue(String.class));
                                    user.setName(ds.child("name").getValue(String.class));

                                    Query query2 = memberRefi.orderByChild("email").equalTo(user.getEmail());

                                    query2.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.getChildrenCount() == 0) {
                                                myRef.child("member/" + user.getId()).setValue(user);
                                            } else {
                                                Toast.makeText(getContext(), "Kullanıcı mevcut!", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });


                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }
                });

        return builder.create();

    }


}
