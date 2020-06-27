package com.example.parenting.Activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.parenting.R;

public class CardShowerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_cardshower);

        Bundle bundle = getIntent().getExtras();
        String sayi=bundle.getString("sayi");

        TextView txtCardShower = findViewById(R.id.txt_CardShower);

        txtCardShower.setText(sayi);

    }
}
