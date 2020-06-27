package com.example.parenting.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.parenting.R;
import com.example.parenting.models.PointValue;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GraphFragment extends Fragment {

    private View GraphView;

    private com.jjoe64.graphview.GraphView graphView;
    LineGraphSeries series;
    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");

    FirebaseAuth mFirebaseAuth;
    DatabaseReference usersDatabase = FirebaseDatabase.getInstance().getReference("Users");
    DatabaseReference puanGosterilecekKullanici;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        GraphView = inflater.inflate(R.layout.fragment_graph, container, false);

        graphView = GraphView.findViewById(R.id.graphView);
        series = new LineGraphSeries();
        graphView.addSeries(series);

        FirebaseUser mFirebaseUser = mFirebaseAuth.getInstance().getCurrentUser();
        final String userEmail = mFirebaseUser.getEmail();

        Query query = usersDatabase.orderByChild("email").equalTo(userEmail);

        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    String curentUserId = ds.child("id").getValue(String.class);
                    puanGosterilecekKullanici = usersDatabase.child(curentUserId + "/chartTable");


                }

                puanGosterilecekKullanici.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        DataPoint[] dp = new DataPoint[(int) dataSnapshot.getChildrenCount()];
                        int index = 0;

                        for (DataSnapshot myDataSnapshot : dataSnapshot.getChildren()) {
                            PointValue pointValue = myDataSnapshot.getValue(PointValue.class);

                            dp[index] = new DataPoint(pointValue.getxValue(), pointValue.getyValue());
                            index++;
                        }

                        series.resetData(dp);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


      //  graphView.getGridLabelRenderer().setNumHorizontalLabels(3);

        graphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {

            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    return sdf.format(new Date((long) value));
                } else {
                    return super.formatLabel(value, isValueX);

                }
            }
        });


        return GraphView;
    }

    private DataPoint[] getDataPoin() {
        DataPoint[] dp = new DataPoint[]{
                new DataPoint(0, 1), new DataPoint(1, 11), new DataPoint(2, 5), new DataPoint(3, 8),
                new DataPoint(4, 5), new DataPoint(5, 15), new DataPoint(6, 9), new DataPoint(7, 13)
        };
        return dp;
    }

}
