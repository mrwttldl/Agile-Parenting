package com.example.parenting.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.parenting.Activities.TablesActivity;
import com.example.parenting.CreateBoardDialog;
import com.example.parenting.R;
import com.example.parenting.adapters.BoardsAdapter;
import com.example.parenting.models.Board;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

public class BoardsFragment extends Fragment {

    private View BoardsView;
    private DatabaseReference mFirebaseDatabase;
    private DatabaseReference myRef;
    private ListView mListView;
    private ArrayList<String> ids, array;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        BoardsView = inflater.inflate(R.layout.fragment_boards, container, false);

        mListView = (ListView) BoardsView.findViewById(R.id.ilkListView);
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();
        myRef = mFirebaseDatabase.child("Boards");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // showData(dataSnapshot);

                array = new ArrayList<>();
                ids = new ArrayList<>();

                if (dataSnapshot.getChildrenCount() == 0) {

                    mListView.setAdapter(null);
                }
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    array.add(ds.child("name").getValue(String.class));
                    ids.add(ds.child("id").getValue(String.class));

                    if(getActivity() !=null)
                    {
                   ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, array);
                   //  ArrayAdapter adapter = new ArrayAdapter(, android.R.layout.simple_list_item_1, array);

                    mListView.setAdapter(adapter);
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Intent intent = new Intent(getContext(), TablesActivity.class);
                intent.putExtra("boardName", mListView.getItemAtPosition(i).toString());
                intent.putExtra("id", ids.get(i));

                startActivity(intent);
            }
        });


        FloatingActionButton floatingActionButton = BoardsView.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createBoardDialog();

            }
        });


        return BoardsView;
    }

//    private void showData(DataSnapshot dataSnapshot) {
//        ArrayList<String> array = new ArrayList<>();
//
//        for (DataSnapshot ds : dataSnapshot.getChildren()) {
//
//
//            array.add(ds.child("name").getValue(String.class));
//
//            ArrayAdapter adapter = new ArrayAdapter(this.getActivity(), android.R.layout.simple_list_item_1, array);
//            mListView.setAdapter(adapter);
//
//
//        }
//
//
//    }


    public void createBoardDialog() {
        CreateBoardDialog createBoardDialog = new CreateBoardDialog();
        createBoardDialog.show(getFragmentManager(), "example dialog");

    }


}
