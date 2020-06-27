package com.example.parenting.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parenting.Fragments.BoardsFragment;
import com.example.parenting.Fragments.FibonacciFragment;
import com.example.parenting.Fragments.CalendarFragment;
import com.example.parenting.Fragments.GraphFragment;
import com.example.parenting.Fragments.NotesFragment;
import com.example.parenting.Fragments.ProfileFragment;
import com.example.parenting.R;
import com.example.parenting.models.User;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    FirebaseAuth mFirebaseAuth;
    DatabaseReference usersDatabase = FirebaseDatabase.getInstance().getReference("Users");

    TextView dispName,dispMail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);
         dispMail = (TextView) headerView.findViewById(R.id.displayingMail);
        dispName = (TextView) headerView.findViewById(R.id.displayingName);

        FirebaseUser mFirebaseUser = mFirebaseAuth.getInstance().getCurrentUser();
        final String userEmaill = mFirebaseUser.getEmail();



        Query query = usersDatabase.orderByChild("email").equalTo(userEmaill);

        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                       dispMail.setText(ds.child("email").getValue(String.class));
                      dispName.setText(ds.child("name").getValue(String.class));


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new BoardsFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_boards);
        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_boards:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new BoardsFragment()).commit();
                break;
        }
        switch (menuItem.getItemId()) {
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
                break;
        }
        switch (menuItem.getItemId()) {
            case R.id.nav_fibonacci:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FibonacciFragment()).commit();
                break;
        }
        switch (menuItem.getItemId()) {
            case R.id.nav_takvim:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CalendarFragment()).commit();
                break;
        }
        switch (menuItem.getItemId()) {
            case R.id.nav_notes:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NotesFragment()).commit();
//                Intent i = new Intent(MainActivity.this, NoteActivity.class);
//                startActivity(i);
                break;
        }
        switch (menuItem.getItemId()) {
            case R.id.nav_graph:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new GraphFragment()).commit();
                break;
        }
        switch (menuItem.getItemId()) {
            case R.id.nav_exit:
                exitAlertDialog();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onBackPressed() {
        //burası hep lo
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void exitAlertDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Çıkış").setMessage("Çıkış yapmak istiyor musunuz?").setPositiveButton("Yes", null)
                .setNegativeButton("No", null).show();

        Button exitPossitiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        exitPossitiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Çıktınız", Toast.LENGTH_LONG).show();
                //buraya cıkıs komutu yazılıcak
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                dialog.dismiss();
            }
        });

        //negative gerek yok cunku bisey yapmasını istemiyorum

    }
}
