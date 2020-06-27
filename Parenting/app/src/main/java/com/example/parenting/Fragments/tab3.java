package com.example.parenting.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.parenting.R;
import com.example.parenting.adapters.TaskListAdapter;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class tab3 extends Fragment {

    private View tab3View;

    private ListView listViewTask;
    private TaskListAdapter adapter;
    private ArrayList<Task> mTaskList;
    String todaysDate;

    int curerentTab = 3;

    DatabaseReference mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("Boards");
    DatabaseReference myRef;

    public tab3() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        tab3View = inflater.inflate(R.layout.fragment_tab1, container, false);

        Bundle bundleTab3 = getArguments();
        final String tableRef = bundleTab3.getString("tableRefference");
        myRef = mFirebaseDatabase.child(tableRef + "/done");

        //HERE IS MY CALISMA------------------------------------------


        listViewTask = (ListView) tab3View.findViewById(R.id.listView_tab1);

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
                    task.setPersonInCharge(ds.child("personInCharge").getValue(String.class));
                    task.setNote(ds.child("note").getValue(String.class));
                    task.setLoop(ds.child("loop").getValue(String.class));
                    mTaskList.add(task);

                    //   todaysDate = getTodaysDate("dd-MM-yyyy");

                    // System.out.println("DENEMEEEEEEEEEEEEEEEEEEEEEEEEE-----------------"+(ds.child("deadline").getValue(String.class)));
                    //  System.out.print("DENEMEEEEEEEEEEEEEEEEEEEEEEEEE-----------------");


                    adapter = new TaskListAdapter(getActivity(), mTaskList, tableRef, curerentTab, getFragmentManager());
                    listViewTask.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //END HERE IS MY CALISMA--------------------------------------
        // Inflate the layout for this fragment
        return tab3View;
    }

//    // enable options for this frag-----------------------------------------------------------------------
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        setHasOptionsMenu(true);
//        super.onCreate(savedInstanceState);
//    }
//
//    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        //inflate menu
//        inflater.inflate(R.menu.tables_menu,menu);
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        //handle menu item clicl
//        int id = item.getItemId();
//        if(id==R.id.action_settings){
//            //yapılıcaı iş buraya
//        }
//        if(id==R.id.action_sort){
//            //yapılıcaı iş buraya
//        }
//        return super.onOptionsItemSelected(item);
//    }
//    //EnablerEnd--------------------------------------------------------------------------------------------

    public static String getTodaysDate(String dateFormat) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat(dateFormat);


        cal.add(Calendar.DAY_OF_YEAR, 0);
        return s.format(new Date(cal.getTimeInMillis()));

        //    Date currentTime = Calendar.getInstance().getTime();

    }

}
