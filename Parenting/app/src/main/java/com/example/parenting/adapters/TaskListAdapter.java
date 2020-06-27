package com.example.parenting.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import com.example.parenting.R;
import com.example.parenting.UpdateTaskDialog;
import com.example.parenting.models.Task;
import com.example.parenting.TaskButtonDropdownClickListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TaskListAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Task> mTaskList;
    String tableRefference;
    int currentTab;
    FragmentManager fm;
    String todaysDate, myId;
    DatabaseReference mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("Boards");


    public TaskListAdapter(Context mContext, ArrayList<Task> mTaskList, String tabRef, int currentTab, FragmentManager fm) {
        this.mContext = mContext;
        this.mTaskList = mTaskList;
        this.tableRefference = tabRef;
        this.currentTab = currentTab;
        this.fm = fm;
        //System.out.println("DEĞERİM                   :::::::::::"+ mTaskList.size());
    }


    @Override
    public int getCount() {

        return mTaskList.size();
        //  return  0;

    }

    @Override
    public Object getItem(int i) {
        return mTaskList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final View v = View.inflate(mContext, R.layout.item_task_list, null);

        DatabaseReference refTodo = mFirebaseDatabase.child(tableRefference + "/todo");
        DatabaseReference refDone = mFirebaseDatabase.child(tableRefference + "/done");


        TextView textViewLocation = (TextView) v.findViewById(R.id.textViewLocation);
        TextView textViewTask = (TextView) v.findViewById(R.id.textViewTask);
        TextView textViewPoint = (TextView) v.findViewById(R.id.textViewPoint);
        TextView textViewPersonInCharge = (TextView) v.findViewById(R.id.textViewPersonInCharge);
        TextView textViewTaskDeadline = (TextView) v.findViewById(R.id.textViewTaskDeadline);
        TextView textViewTaskNote = (TextView) v.findViewById(R.id.textViewTaskNote);

        Button buttonPopup = (Button) v.findViewById(R.id.button_task_popup);

        //set text for textView
        textViewLocation.setText(mTaskList.get(i).getLocation());
        textViewTask.setText(mTaskList.get(i).getTask());
        textViewPoint.setText(mTaskList.get(i).getPoint());
        textViewPersonInCharge.setText(mTaskList.get(i).getPersonInCharge());
        textViewTaskDeadline.setText((mTaskList.get(i).getDeadline()));
        textViewTaskNote.setText((mTaskList.get(i).getNote()));

        todaysDate = getTodaysDate("dd-MM-yyyy");
        myId = mTaskList.get(i).getId();

        if(((mTaskList.get(i).getDeadline())==null)){

            System.out.println("----------------------------------------BOŞ deadline");
            System.out.println("--------------------------------------DL"+mTaskList.get(i).getDeadline());

        }else if (currentTab == 3  && (mTaskList.get(i).getDeadline()).equals(todaysDate)) {


            //kişiyi puanlamayı nasıl yapıcam düşün puanların haftalık olarak tutulması ve gösterilmesi gerek

            if (mTaskList.get(i).getLoop().equals("Günlük")) {

                mTaskList.get(i).setDeadline(getCalculatedDate("dd-MM-yyyy", 1));

            } else if (mTaskList.get(i).getLoop().equals("Haftalık")) {

                mTaskList.get(i).setDeadline(getCalculatedDate("dd-MM-yyyy", 7));

            } else if (mTaskList.get(i).getLoop().equals("Aylık")) {

                mTaskList.get(i).setDeadline(getCalculatedDate("dd-MM-yyyy", 30));

            } else if (mTaskList.get(i).getLoop().equals("Yıllık")) {

                mTaskList.get(i).setDeadline(getCalculatedDate("dd-MM-yyyy", 365));
            }


            refTodo.child(myId).setValue(mTaskList.get(i));
            refDone.child(myId).removeValue();


        }

        buttonPopup.setOnClickListener(new TaskButtonDropdownClickListener(v, mTaskList.get(i).getLocation(), mTaskList.get(i).getTask(), mTaskList.get(i).getPoint(),
                mTaskList.get(i).getId(), tableRefference, mTaskList.get(i).getDeadline(), mTaskList.get(i).getNote(), mTaskList.get(i).getPersonInCharge(), mTaskList.get(i).getLoop(), currentTab, fm));


        return v;
    }

    public static String getTodaysDate(String dateFormat) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat(dateFormat);


        cal.add(Calendar.DAY_OF_YEAR, 0);  //defaultu 0 olmalı sonradan karıştıma diye not
        return s.format(new Date(cal.getTimeInMillis()));


    }

    public static String getCalculatedDate(String dateFormat, int days) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat(dateFormat);
        cal.add(Calendar.DAY_OF_YEAR, days);
        return s.format(new Date(cal.getTimeInMillis()));
    }

    public void loopupdater() {


    }


}
