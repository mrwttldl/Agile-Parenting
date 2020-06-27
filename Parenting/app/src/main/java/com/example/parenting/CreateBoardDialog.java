package com.example.parenting;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.parenting.models.Board;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateBoardDialog extends AppCompatDialogFragment {

    EditText editTextBoardname;
    FirebaseAuth mFirebaseAuth;




    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.create_board_dialog, null);
        mFirebaseAuth = FirebaseAuth.getInstance();

        editTextBoardname = view.findViewById(R.id.editText_board_name);


        builder.setView(view).setTitle("Oluştur").setNegativeButton("Iptal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //buraya bişey koymaya gerek yok
            }
        })
                .setPositiveButton("Oluştur", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //Boardımı bu blokta olusturuyorum board modeli ile

                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Boards");
                        Board pano = new Board();
                        String id = mDatabase.push().getKey();

                        pano.setCreator(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                        pano.setId(id);
                        pano.setName(editTextBoardname.getText().toString());
                        mDatabase.child(id).setValue(pano);


                    }
                });


        return builder.create();
    }



}
