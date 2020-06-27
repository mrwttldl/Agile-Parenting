package com.example.parenting.adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.parenting.Fragments.tab1;
import com.example.parenting.Fragments.tab2;
import com.example.parenting.Fragments.tab3;

public class PageAdapter extends FragmentPagerAdapter {

    private int numberOfTabs;
    private String reference;

    public PageAdapter(@NonNull FragmentManager fm, int numberOfTabs, String reference) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
        this.reference = reference;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: {
                tab1 myObj = new tab1();
                Bundle bundle2 = new Bundle();

                bundle2.putString("tableRefference", reference);
                myObj.setArguments(bundle2);
                return myObj;
            }
            case 1: {
                tab2 myObj2 = new tab2();
                Bundle bundle3 = new Bundle();

                bundle3.putString("tableRefference", reference);
                myObj2.setArguments(bundle3);
                return myObj2;
                //return new tab2();


            }
            case 2: {
                tab3 myObj3 = new tab3();
                Bundle bundle4 = new Bundle();

                bundle4.putString("tableRefference", reference);
                myObj3.setArguments(bundle4);
                return myObj3;
                // return new tab3();
            }
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }

    @Override
    public long getItemId(int position) {   //neden reset atmıyor aykuşa sor
        return position;
    }

}
