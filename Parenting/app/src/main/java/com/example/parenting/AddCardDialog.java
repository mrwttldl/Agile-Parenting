package com.example.parenting;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.parenting.models.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddCardDialog extends AppCompatDialogFragment {


    private EditText editTextTask, editTextLocation, editTextPoint, editTextNote;

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Boards");
    private DatabaseReference myRef;
    private DatabaseReference myMembersRef;

    private String[] interval = {"Bir Aralık Seçiniz.", "Günlük", "Haftalık", "Aylık", "Yıllık"};
    //private  String[] membersArray = {"Bir Aralık Seçiniz.", "Günlük", "Haftalık", "Aylık", "Yıllık"};
    // private  ArrayList<String> membersArray;
    private List<String> membersArray = new ArrayList<>();
    private ArrayAdapter adapter;
    private ArrayAdapter<String> memberAdapter;
    //   private Date future;
    private String future;

    private String tableRefference, loopu, eklenecekKullaniciAdi;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_addcard, null);

        Bundle bundle = getArguments();
        if (bundle != null) {
            tableRefference = bundle.getString("tableRefference");

        }

        myMembersRef = mDatabase.child((tableRefference + "/member"));

        //  mFirebaseAuth=FirebaseAuth.getInstance();
        editTextTask = view.findViewById(R.id.editText_Task);
        editTextLocation = view.findViewById(R.id.editText_location);
        editTextPoint = view.findViewById(R.id.editText_point);
        editTextNote = view.findViewById(R.id.editText_Note);
        Spinner spinnerMembers = view.findViewById(R.id.spinnerMembers);
        Spinner spinnerTimeInterval = view.findViewById(R.id.spinnerInterval);

        membersArray.add("Bir Sorumlu seçiniz");


        myMembersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // membersArray = new ArrayList<>();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    membersArray.add(ds.child("name").getValue(String.class));

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, interval);
        spinnerTimeInterval.setAdapter(adapter);
        spinnerTimeInterval.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = adapterView.getItemAtPosition(i).toString();

                //  Calendar c = Calendar.getInstance();
                if (text.equals("Günlük")) {
                    //  c.add(Calendar.DATE, 1);
                    future = getCalculatedDate("dd-MM-yyyy", 1);
                    loopu = "Günlük";

                } else if (text.equals("Haftalık")) {
                    //   c.add(Calendar.DATE, 7);
                    future = getCalculatedDate("dd-MM-yyyy", 7);
                    loopu = "Haftalık";
                } else if (text.equals("Aylık")) {
                    future = getCalculatedDate("dd-MM-yyyy", 30);
                    //  c.add(Calendar.MONTH, 1);
                    loopu = "Aylık";
                } else if (text.equals("Yıllık")) {
                    future = getCalculatedDate("dd-MM-yyyy", 365);
                    //  c.add(Calendar.YEAR, 1);
                    loopu = "Yıllık";
                }

                // future = c.getTime();

                //  future = getCalculatedDate("dd-MM-yyyy", 7);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        memberAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, membersArray);
        spinnerMembers.setAdapter(memberAdapter);
        spinnerMembers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                eklenecekKullaniciAdi = adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        builder.setView(view).setTitle("Ekle").setNegativeButton("Iptal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //buraya bişey koymaya gerek yok
            }
        })
                .setPositiveButton("Oluştur", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //Boardımı bu blokta olusturuyorum board modeli ile


                        //  String taskParrent = editTextTask.getText().toString();
                        myRef = mDatabase.child(tableRefference + "/todo");
                        String id = myRef.push().getKey();
                        // myRef = mDatabase.child(tableRefference + "/todo/" + taskParrent);

                        // Task task = new Task(editTextTask.getText().toString(),editTextLocation.getText().toString(),editTextPoint.getText().toString());
                        Task task = new Task();
                        task.setId(id);
                        task.setTask(editTextTask.getText().toString());
                        task.setLocation(editTextLocation.getText().toString());
                        task.setPoint(editTextPoint.getText().toString());
                        task.setDeadline(future);
                        task.setNote(editTextNote.getText().toString());
                        task.setLoop(loopu);
                        task.setPersonInCharge(eklenecekKullaniciAdi);

                        myRef.child(id).setValue(task);


                    }
                });

        return builder.create();
    }

    public static String getCalculatedDate(String dateFormat, int days) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat(dateFormat);
        cal.add(Calendar.DAY_OF_YEAR, days);
        return s.format(new Date(cal.getTimeInMillis()));
    }
}
