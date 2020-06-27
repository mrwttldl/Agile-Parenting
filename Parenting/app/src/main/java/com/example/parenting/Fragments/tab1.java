package com.example.parenting.Fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.parenting.Activities.JobPoolActivity;
import com.example.parenting.Activities.MainActivity;
import com.example.parenting.AddCardDialog;
import com.example.parenting.AddUserEmailDialog;
import com.example.parenting.R;
import com.example.parenting.adapters.TaskListAdapter;
import com.example.parenting.models.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class tab1 extends Fragment {


    private View tab1View;

    private ListView listViewTask;
    private TaskListAdapter adapter;
    private ArrayList<Task> mTaskList;
    String tableRef;

    int curerentTab = 1;


    DatabaseReference mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("Boards");
    DatabaseReference myRef;

    public tab1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        tab1View = inflater.inflate(R.layout.fragment_tab1, container, false);

        Bundle bundle3 = getArguments();
        tableRef = bundle3.getString("tableRefference");
        myRef = mFirebaseDatabase.child(tableRef + "/todo");


        listViewTask = (ListView) tab1View.findViewById(R.id.listView_tab1);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mTaskList = new ArrayList<Task>();
                if (dataSnapshot.getChildrenCount() == 0) {

                    listViewTask.setAdapter(null);
                }

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Task task = new Task();
                    task.setTask(ds.child("task").getValue(String.class));
                    task.setLocation(ds.child("location").getValue(String.class));
                    task.setPoint(ds.child("point").getValue(String.class));
                    task.setId(ds.child("id").getValue(String.class));
                    task.setDeadline(ds.child("deadline").getValue(String.class));
                    task.setNote(ds.child("note").getValue(String.class));
                    task.setPersonInCharge(ds.child("personInCharge").getValue(String.class));
                    task.setLoop(ds.child("loop").getValue(String.class));
                    mTaskList.add(task);

                    adapter = new TaskListAdapter(getActivity(), mTaskList, tableRef, curerentTab, getFragmentManager());
                    listViewTask.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        FloatingActionButton fab2 = tab1View.findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddCardDialog();

            }
        });


        // Inflate the layout for this fragment
        return tab1View;
    }

    // enable options for this frag-----------------------------------------------------------------------
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {


        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        //inflate menu
        inflater.inflate(R.menu.tables_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //handle menu item clicl
        int id = item.getItemId();
        if (id == R.id.action_addUser) {
            openAddUserEmailDialog();
        }
        if (id == R.id.action_settings) {


            //yapılıcaı iş buraya
        }
        if (id == R.id.action_boardDelete) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Odayı Sil").setMessage("Bu odayı gerçekten silmek İstiyor Musunuz?").setNegativeButton("Hayır", null).setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                   mFirebaseDatabase.child(tableRef).removeValue();
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    startActivity(intent);

                }
            });
            builder.show();


            //yapılıcaı iş buraya
        }
        if (id == R.id.action_jobPool) {
            Intent i = new Intent(getContext(), JobPoolActivity.class);
            i.putExtra("tableRefference", tableRef);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
    //EnablerEnd--------------------------------------------------------------------------------------------

    public void openAddCardDialog() {
        AddCardDialog addCardDialog = new AddCardDialog();
        Bundle bundle = getArguments();
        Bundle bundle2 = new Bundle();
        bundle2.putString("tableRefference", bundle.getString("tableRefference"));
        // set MyFragment Arguments
        addCardDialog.setArguments(bundle2);
        addCardDialog.show(getFragmentManager(), "addCardDialog");
    }

    public void openAddUserEmailDialog() {
        AddUserEmailDialog addUserEmailDialog = new AddUserEmailDialog();
        Bundle bundle = getArguments();
        Bundle bundle2 = new Bundle();
        bundle2.putString("tableRefference", bundle.getString("tableRefference"));
        addUserEmailDialog.setArguments(bundle2);
        addUserEmailDialog.show(getFragmentManager(), "addUserEmailDialog");

    }


}




