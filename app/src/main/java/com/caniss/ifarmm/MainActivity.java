package com.caniss.ifarmm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    CardView priceCard, machineCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Define Cards by Id's
        priceCard = (CardView) findViewById(R.id.price_grid);
        machineCard = (CardView) findViewById(R.id.machine_grid);

        //Setting Onclick Listeners
        priceCard.setOnClickListener(this);
        machineCard.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent next = null;
        switch (v.getId()){
            case R.id.price_grid: next = new Intent(this,PriceViewActivity.class);startActivity(next); break;
            case R.id.machine_grid: next = new Intent(this,MachineViewActivity.class);startActivity(next); break;
            default: break;
        }
    }
}
