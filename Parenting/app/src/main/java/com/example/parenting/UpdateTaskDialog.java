package com.example.parenting;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.parenting.Activities.MainActivity;
import com.example.parenting.Activities.TablesActivity;
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

public class UpdateTaskDialog extends AppCompatDialogFragment {

    private String[] interval = {"Bir Aralık Seçiniz.", "Günlük", "Haftalık", "Aylık", "Yıllık"};

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Boards");
    private List<String> membersArray = new ArrayList<>();
    private ArrayAdapter adapter;
    private String future;
    int currentTab;
    String currentTabText;
    String updateSpinnerIntervalText;


    private ArrayAdapter<String> memberAdapter;

    DatabaseReference mFirebasedatabase = FirebaseDatabase.getInstance().getReference("Boards");
    DatabaseReference guncellenecekRef, myMembersRef;
    Task mTask;
    String tableRef, loopu;
    String eklenecekKullaniciAdi;

    public UpdateTaskDialog(Task task, String TableRef, int currentTab) {

        this.mTask = task;
        this.tableRef = TableRef;
        this.currentTab = currentTab;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_updatetask, null);

        final EditText editText_updateTask = view.findViewById(R.id.editText_updateTask);
        final EditText editText_updateLocation = view.findViewById(R.id.editText_updateLocation);
        final EditText editText_updatePoint = view.findViewById(R.id.editText_updatePoint);
        final EditText editText_updateNote = view.findViewById(R.id.editText_updateNote);
        final Spinner updateSpinnerInterval = view.findViewById(R.id.updateSpinnerInterval);
        final Spinner updateSpinnerMembers = view.findViewById(R.id.updateSpinnerMembers);

        editText_updateTask.setText(mTask.getTask());
        editText_updateLocation.setText(mTask.getLocation());
        editText_updatePoint.setText(mTask.getPoint());
         editText_updateNote.setText(mTask.getNote());

        if (currentTab == 1) {
            currentTabText = "todo";
        } else if (currentTab == 2) {
            currentTabText = "workInProgress";
        } else if (currentTab == 3) {
            currentTabText = "done";

        }


        //**********
        myMembersRef = mDatabase.child((tableRef + "/member"));

        // membersArray.add(mTask.getPersonInCharge());

        myMembersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // membersArray = new ArrayList<>();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    membersArray.add(ds.child("name").getValue(String.class));
                    memberAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, membersArray);
                    updateSpinnerMembers.setAdapter(memberAdapter);

                    updateSpinnerMembers.setSelection(memberAdapter.getPosition(mTask.getPersonInCharge()));

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //******

//        memberAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, membersArray);
//        updateSpinnerMembers.setAdapter(memberAdapter);
//        updateSpinnerMembers.setSelection(memberAdapter.getPosition(mTask.getPersonInCharge()));


        updateSpinnerMembers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                eklenecekKullaniciAdi = adapterView.getItemAtPosition(i).toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, interval);
        updateSpinnerInterval.setAdapter(adapter);

        updateSpinnerInterval.setSelection(adapter.getPosition(mTask.getLoop()));
        updateSpinnerInterval.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                updateSpinnerIntervalText = adapterView.getItemAtPosition(i).toString();


                if (updateSpinnerIntervalText.equals("Günlük")) {
                    //  c.add(Calendar.DATE, 1);
                    future = getCalculatedDate("dd-MM-yyyy", 1);
                    loopu = "Günlük";

                } else if (updateSpinnerIntervalText.equals("Haftalık")) {
                    //   c.add(Calendar.DATE, 7);
                    future = getCalculatedDate("dd-MM-yyyy", 7);
                    loopu = "Haftalık";
                } else if (updateSpinnerIntervalText.equals("Aylık")) {
                    future = getCalculatedDate("dd-MM-yyyy", 30);
                    //  c.add(Calendar.MONTH, 1);
                    loopu = "Aylık";
                } else if (updateSpinnerIntervalText.equals("Yıllık")) {
                    future = getCalculatedDate("dd-MM-yyyy", 365);
                    //  c.add(Calendar.YEAR, 1);
                    loopu = "Yıllık";
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });

        builder.setView(view)
                .setTitle("Güncelle")
                .setNegativeButton("Iptal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //buraya bişey koymaya gerek yok
                    }
                })
                .setPositiveButton("Güncelle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String dene = editText_updatePoint.getText().toString();

                        if (dene.equals("")){

                            Toast.makeText(getActivity(), "Kayıt ekleme başarısız! Puan boş olamaz.", Toast.LENGTH_LONG).show();
                            //editText_updatePoint.setError("puan boş olamaz");
                           // editText_updatePoint.requestFocus();


                    }else{


                            guncellenecekRef = mFirebasedatabase.child(tableRef + "/" + currentTabText + "/" + mTask.getId());

                            //Boardımı bu blokta olusturuyorum board modeli ile

                            Task updatedTask = new Task();
                            updatedTask.setTask(editText_updateTask.getText().toString());
                            updatedTask.setLocation(editText_updateLocation.getText().toString());
                            updatedTask.setPoint(editText_updatePoint.getText().toString());
                            updatedTask.setNote(editText_updateNote.getText().toString());
                            updatedTask.setLoop(updateSpinnerIntervalText);
                            updatedTask.setPersonInCharge(eklenecekKullaniciAdi);
                            updatedTask.setDeadline(future);
                            updatedTask.setId(mTask.getId());


                            guncellenecekRef.setValue(updatedTask);

//                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Boards");
//                        Board pano = new Board();
//                        String id = mDatabase.push().getKey();
//
//                        pano.setCreator(FirebaseAuth.getInstance().getCurrentUser().getEmail()); //Bu satır düzgün almıyor
//                        pano.setId(id);
//                        pano.setName(editTextBoardname.getText().toString());
//                        mDatabase.child(id).setValue(pano);
                        }

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
