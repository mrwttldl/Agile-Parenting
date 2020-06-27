package com.example.parenting;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.example.parenting.Activities.TablesActivity;
import com.example.parenting.models.PointValue;
import com.example.parenting.models.Task;
import com.example.parenting.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskButtonDropdownClickListener implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
    private View appContext;
    private String myTask, myLocation, myPoint, tabRef, myId, taskDeadline, taskNote, personInCharge, taskLoop;   //BUNLAR NEDEN YORUM SATIRI ÇÖZ

    private DatabaseReference mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("Boards");
    // private DatabaseReference mFirebaseUserDatabase=FirebaseDatabase.getInstance().getReference("Users");;
    FirebaseAuth mFirebaseAuth;
    DatabaseReference usersDatabase = FirebaseDatabase.getInstance().getReference("Users");
    private DatabaseReference puanEklenecekKullanici;
    private FragmentManager taskFragmentManager;

    String pointId;
    String curentUserId;

    long x;
    //User user;

    Task task = new Task();


    private int currentTab;

    public TaskButtonDropdownClickListener(View context, String location, String task, String point, String myId, String tabRef, String taskDeadline, String taskNote, String personInCharge, String taskLoop, int currentTab, FragmentManager fm) {
        this.appContext = context;
        this.myTask = task;
        this.myLocation = location;
        this.myPoint = point;
        this.myId = myId;
        this.tabRef = tabRef;
        this.currentTab = currentTab;
        this.taskDeadline = taskDeadline;
        this.taskNote = taskNote;
        this.personInCharge = personInCharge;
        this.taskLoop = taskLoop;
        this.taskFragmentManager = fm;

    }


    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        DatabaseReference myRef = mFirebaseDatabase.child(tabRef + "/todo");
        DatabaseReference myRef2 = mFirebaseDatabase.child(tabRef + "/workInProgress");
        DatabaseReference myRef3 = mFirebaseDatabase.child(tabRef + "/done");

        pointId = usersDatabase.push().getKey();
        x = new Date().getTime();


        // Task task = new Task();
        task.setTask(myTask);
        task.setLocation(myLocation);
        task.setPoint(myPoint);
        task.setId(myId);
        task.setPersonInCharge(personInCharge);
        task.setDeadline(taskDeadline);
        task.setNote(taskNote);
        task.setLoop(taskLoop);
        //  String id = myRef2.push().getKey();

        switch (menuItem.getItemId()) {
            case R.id.todo:
                if (currentTab == 1) {

                } else if (currentTab == 2) {
                    myRef.child(myId).setValue(task);
                    myRef2.child(myId).removeValue();

                } else if (currentTab == 3) {

                    myRef.child(myId).setValue(task);
                    myRef3.child(myId).removeValue();
                }
                return true;
            case R.id.workInProgress:
                if (currentTab == 1) {

                    myRef2.child(myId).setValue(task);
                    myRef.child(myId).removeValue();
                } else if (currentTab == 2) {

                } else if (currentTab == 3) {
                    myRef2.child(myId).setValue(task);  //BURA tektarlanan zaman geldiginde
                    myRef3.child(myId).removeValue();


                }
                return true;
            case R.id.done: {
                //DONE burada basılıyor

                FirebaseUser mFirebaseUser = mFirebaseAuth.getInstance().getCurrentUser();
                final String userEmail = mFirebaseUser.getEmail();
                final String userName = mFirebaseUser.getDisplayName();

                if (!userName.equals(task.getPersonInCharge())) {

                       Toast.makeText(appContext.getContext(), "Buna yetkiniz yok!", Toast.LENGTH_SHORT).show();
                 //   System.out.println("Buna yetkiniz yok");

                } else {

                    Query query3 = usersDatabase.orderByChild("email").equalTo(userEmail);

                    query3.addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                curentUserId = ds.child("id").getValue(String.class);


                            }

                            puanEklenecekKullanici = usersDatabase.child(curentUserId + "/chartTable");
                            //pointId = puanEklenecekKullanici.push().getKey();

                            int y = Integer.parseInt(task.getPoint());

                            PointValue pointValue = new PointValue(x, y);

                            //   puanEklenecekKullanici.child(pointId).setValue(pointValue);
                            puanEklenecekKullanici.child(pointId).setValue(pointValue);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }

                    });


                    if (currentTab == 1) {

                        myRef3.child(myId).setValue(task);
                        myRef.child(myId).removeValue();
                    } else if (currentTab == 2) {

                        myRef3.child(myId).setValue(task);
                        myRef2.child(myId).removeValue();

                    } else if (currentTab == 3) {

                    }

                }
            }
            return true;
            case R.id.duzenle:
                if (currentTab == 1) {

                    openUpdateTaskDialog(task, tabRef, currentTab);
                }

                return true;
            case R.id.sil:
                if (currentTab == 1) {
                    myRef.child(myId).removeValue();
                } else if (currentTab == 2) {
                    myRef2.child(myId).removeValue();
                } else if (currentTab == 3) {
                    myRef3.child(myId).removeValue();

                }

                return true;
            default:
                return false;
        }

    }

    @Override
    public void onClick(View view) {
        PopupMenu popup = new PopupMenu(appContext.getContext(), appContext);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.dropdown_taskitem_menu);
        popup.show();

    }

    public void openUpdateTaskDialog(Task mtTask, String TableRef, int currentTab) {
        UpdateTaskDialog updateTaskDialog = new UpdateTaskDialog(mtTask, TableRef, currentTab);
        updateTaskDialog.show(taskFragmentManager, "updateTaskDialog");


    }


}
