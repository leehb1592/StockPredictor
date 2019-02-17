package com.example.niveth_saran.stockpredictor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class LibrariesUsedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libraries_used);
        final ListView listView=(ListView)findViewById(R.id.librarylist);
        ArrayList<String> adapterparam=new ArrayList<String>();
        adapterparam.add("DDJOE GraphView");
        adapterparam.add("Firebase by Google");
        adapterparam.add("Android Design Library");
        adapterparam.add("Android Support Library");
        adapterparam.add("Android RecyclerView Library");
        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,adapterparam);
        listView.setAdapter(adapter);
    }
}
