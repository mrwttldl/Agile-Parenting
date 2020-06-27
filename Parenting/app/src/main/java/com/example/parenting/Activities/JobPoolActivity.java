package com.example.parenting.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.parenting.R;
import com.example.parenting.models.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class JobPoolActivity extends AppCompatActivity {

    private ArrayList<String> puanlarArrayi, looplarAraryi;

    DatabaseReference mBoardDatabase = FirebaseDatabase.getInstance().getReference("Boards");
    DatabaseReference mBoardRefference;

    DatabaseReference mFirebaseDatabaseJobPoolRef = FirebaseDatabase.getInstance().getReference("JobPool");
    DatabaseReference ilkSpinnerdanAlinanRef;
    DatabaseReference ikinciSpinnerdanAlinanRef, setEdilicekPuanRef;

    private ArrayList<Task> listelenenTaskList;


    List<String> jobHeadersArray = new ArrayList<>();
    List<String> ikinciSpinneraKoyulanArrayList;
    List<String> containerArrayList;
    ArrayList<String> tasklarinPuanlari;

    // List<String> containerArray;

    String tableRef;
    String baslikTututcu, baslikTututcu2;


    ArrayAdapter adapter, adapter2, adapter3;

    /*ArrayList<> performForFirstSpinner(View view);
    {
        ArrayList<String> ikinciSpinnerKoyulanArrayList= new ArrayList<>();

        return ikinciSpinnerKoyulanArrayList;
    }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_pool);

        Bundle bundle = getIntent().getExtras();
        tableRef = bundle.getString("tableRefference");
        mBoardRefference = mBoardDatabase.child(tableRef + "/todo");

        final ListView mjobPoolListview = findViewById(R.id.jobPoolListview);
        final Spinner spinner1 = findViewById(R.id.jobSpinner1);
        final Spinner spinner2 = findViewById(R.id.jobSpinner2);
        // final Spinner spinner3 = findViewById(R.id.jobSpinner3);


        //  baslikTututcu = "Alışveriş";

        mFirebaseDatabaseJobPoolRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    jobHeadersArray.add(ds.getKey());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        jobHeadersArray.add("Bir Başlık seçiniz");
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, jobHeadersArray);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //  String text = adapterView.getItemAtPosition(i).toString();

                baslikTututcu = spinner1.getSelectedItem().toString();

                ilkSpinnerdanAlinanRef = mFirebaseDatabaseJobPoolRef.child(baslikTututcu);

                ikinciSpinneraKoyulanArrayList = new ArrayList<>();
                ikinciSpinneraKoyulanArrayList.add("Bir Alt Başlık Seçiniz");

                ilkSpinnerdanAlinanRef.addValueEventListener(new ValueEventListener() {

                    //ilerde bu kısımın gigerer başlıklarla aynı anda array doldurmaya calısıp programı yormasındansa
                    @Override
                    //ilk spinnerin içinde if else ile o an istenileni doldurtabilirsin

                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {

                            ikinciSpinneraKoyulanArrayList.add(ds.getKey());


                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                adapter2 = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, ikinciSpinneraKoyulanArrayList);
                spinner2.setAdapter(adapter2);
                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                        ikinciSpinnerdanAlinanRef = ilkSpinnerdanAlinanRef.child(spinner2.getSelectedItem().toString());

                        System.out.println("---------------------------------------REF: " + ikinciSpinnerdanAlinanRef);
                        if (ikinciSpinnerdanAlinanRef != null) {
                            ikinciSpinnerdanAlinanRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    containerArrayList = new ArrayList<>();
                                    puanlarArrayi = new ArrayList<>();
                                    looplarAraryi = new ArrayList<>();
                                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                        containerArrayList.add(ds.getKey());
                                        puanlarArrayi.add(ds.child("point").getValue(String.class));
                                        looplarAraryi.add(ds.child("loop").getValue(String.class));


                                        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, containerArrayList);
                                        mjobPoolListview.setAdapter(adapter);

                                    }


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }

                        mjobPoolListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                                String id = mBoardRefference.push().getKey();
                                Task task = new Task();
                                task.setId(id);
                                task.setPoint(puanlarArrayi.get(i));
                                task.setLoop(looplarAraryi.get(i));
                                task.setLocation(spinner1.getSelectedItem().toString() + " / " + spinner2.getSelectedItem().toString());
                                task.setTask(mjobPoolListview.getItemAtPosition(i).toString());
                                mBoardRefference.child(id).setValue(task);

                                //

                                Toast.makeText(getApplicationContext(), "Madde Başarı ile eklendi", Toast.LENGTH_SHORT).show();


                            }
                        });


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });


    }


}