package com.example.niveth_saran.stockpredictor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class DevelopersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developers);
        final ListView listView=(ListView)findViewById(R.id.devlist);
        ArrayList<String> adapterparam=new ArrayList<String>();
        adapterparam.add("Keshav R.(Lead Developer)");
        adapterparam.add("Pavithron B.");
        adapterparam.add("Ashwin B.");
        adapterparam.add("Niveth Saran V. J.(Android App Developer)");

        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,adapterparam);
        listView.setAdapter(adapter);
    }
}
