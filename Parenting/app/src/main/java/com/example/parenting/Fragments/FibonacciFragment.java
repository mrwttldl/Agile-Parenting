package com.example.parenting.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.parenting.Activities.CardShowerActivity;
import com.example.parenting.R;

public class FibonacciFragment extends Fragment {
    //  private FibonacciFragmentListener listener;
    private View FibonacciView;
    private Button card_0, card_1, card_2, card_3, card_5, card_8, card_13, card_21, card_34, card_55, card_89, card_144, card_233, card_377, card_610, card_987;
    TextView shower;

    String cardPoint;



//    public interface FibonacciFragmentListener {
//        void onInputASent(CharSequence input);
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FibonacciView = inflater.inflate(R.layout.fragment_fibonacci, container, false);

        initButtons();
        buttonOnClicks();


        return FibonacciView;
    }


    public void initButtons() {
        shower = FibonacciView.findViewById(R.id.txt_CardShower);

        card_0 = FibonacciView.findViewById(R.id.btn_card_0);
        card_1 = FibonacciView.findViewById(R.id.btn_card_1);
        card_2 = FibonacciView.findViewById(R.id.btn_card_2);
        card_3 = FibonacciView.findViewById(R.id.btn_card_3);
        card_5 = FibonacciView.findViewById(R.id.btn_card_5);
        card_8 = FibonacciView.findViewById(R.id.btn_card_8);
        card_13 = FibonacciView.findViewById(R.id.btn_card_13);
        card_21 = FibonacciView.findViewById(R.id.btn_card_21);
        card_34 = FibonacciView.findViewById(R.id.btn_card_34);
        card_55 = FibonacciView.findViewById(R.id.btn_card_55);
        card_89 = FibonacciView.findViewById(R.id.btn_card_89);
        card_144 = FibonacciView.findViewById(R.id.btn_card_144);
        card_233 = FibonacciView.findViewById(R.id.btn_card_233);
        card_377 = FibonacciView.findViewById(R.id.btn_card_377);
        card_610 = FibonacciView.findViewById(R.id.btn_card_610);
        card_987 = FibonacciView.findViewById(R.id.btn_card_987);


    }

    public void buttonOnClicks() {
        card_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), CardShowerActivity.class);
                cardPoint = "0";
                i.putExtra("sayi", cardPoint);
                startActivity(i);


            }
        });

        card_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), CardShowerActivity.class);
                cardPoint = "1";
                i.putExtra("sayi", cardPoint);
                startActivity(i);


            }
        });

        card_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), CardShowerActivity.class);
                cardPoint = "2";
                i.putExtra("sayi", cardPoint);
                startActivity(i);


            }
        });

        card_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), CardShowerActivity.class);
                cardPoint = "3";
                i.putExtra("sayi", cardPoint);
                startActivity(i);


            }
        });

        card_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), CardShowerActivity.class);
                cardPoint = "5";
                i.putExtra("sayi", cardPoint);
                startActivity(i);


            }
        });

        card_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), CardShowerActivity.class);
                cardPoint = "8";
                i.putExtra("sayi", cardPoint);
                startActivity(i);


            }
        });

        card_13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), CardShowerActivity.class);
                cardPoint = "13";
                i.putExtra("sayi", cardPoint);
                startActivity(i);


            }
        });

        card_21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), CardShowerActivity.class);
                cardPoint = "21";
                i.putExtra("sayi", cardPoint);
                startActivity(i);


            }
        });

        card_34.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), CardShowerActivity.class);
                cardPoint = "34";
                i.putExtra("sayi", cardPoint);
                startActivity(i);


            }
        });

        card_55.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), CardShowerActivity.class);
                cardPoint = "55";
                i.putExtra("sayi", cardPoint);
                startActivity(i);


            }
        });

        card_89.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), CardShowerActivity.class);
                cardPoint = "89";
                i.putExtra("sayi", cardPoint);
                startActivity(i);


            }
        });

        card_144.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), CardShowerActivity.class);
                cardPoint = "144";
                i.putExtra("sayi", cardPoint);
                startActivity(i);


            }
        });

        card_233.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), CardShowerActivity.class);
                cardPoint = "233";
                i.putExtra("sayi", cardPoint);
                startActivity(i);


            }
        });

        card_377.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), CardShowerActivity.class);
                cardPoint = "377";
                i.putExtra("sayi", cardPoint);
                startActivity(i);


            }
        });

        card_610.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), CardShowerActivity.class);
                cardPoint = "610";
                i.putExtra("sayi", cardPoint);
                startActivity(i);


            }
        });

        card_987.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), CardShowerActivity.class);
                cardPoint = "987";
                i.putExtra("sayi", cardPoint);
                startActivity(i);


            }
        });

    }


}


