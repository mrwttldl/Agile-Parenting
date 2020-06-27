package com.example.parenting.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.example.parenting.AddCardDialog;
import com.example.parenting.R;
import com.example.parenting.adapters.PageAdapter;
import com.example.parenting.models.Task;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TablesActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabItem tab1, tab2, tab3;
    public PageAdapter pagerAdapter;
    String tableRefference;



    //ForDialog-----------------------------------------------------------------------------

    EditText editTextTask, editTextLocation, editTextPoint;

    //EndForDialog----------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tables);


        editTextTask = (EditText) findViewById(R.id.editText_Task);
        editTextLocation = (EditText) findViewById(R.id.editText_location);
        editTextPoint = (EditText) findViewById(R.id.editText_point);

       tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tab1 = (TabItem) findViewById(R.id.Tab1);
        tab2 = (TabItem) findViewById(R.id.Tab2);
        tab3 = (TabItem) findViewById(R.id.Tab3);
        viewPager = findViewById(R.id.viewpager);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            this.setTitle(bundle.getString("boardName"));
            //  Log.d("Value:",bundle.getString("boardName"));
            tableRefference = bundle.getString("id");
            // Log.d("VALU", tableRefference);

        }


        //    Log.d("tabREF", tableRefference);


        pagerAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), tableRefference);
        viewPager.setAdapter(pagerAdapter);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                   // System.out.println("1");
                    pagerAdapter.notifyDataSetChanged();
                } else if (tab.getPosition() == 1) {
                   // System.out.println("2");
                    pagerAdapter.notifyDataSetChanged();
                } else if (tab.getPosition() == 2) {
                   // System.out.println("3");
                    pagerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }


}
