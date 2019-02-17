package com.example.niveth_saran.stockpredictor;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;

import java.util.ArrayList;


public class AboutFragment extends Fragment {
    private Intent intent;

    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_about, container, false);
        final ListView listView=(ListView)view.findViewById(R.id.aboutlist);
        ArrayList<String> adapterparam=new ArrayList<String>();
        adapterparam.add("About the Project");
        adapterparam.add("Libraries Used");
        adapterparam.add("Developers");
        adapterparam.add("License Details");
        ArrayAdapter adapter=new ArrayAdapter(this.getContext(),android.R.layout.simple_list_item_1,adapterparam);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:
                        intent=new Intent(getActivity().getApplicationContext(),AboutProjectActivity.class);
                        break;
                    case 1:
                        intent=new Intent(getActivity().getApplicationContext(),LibrariesUsedActivity.class);
                        break;
                    case 2:
                        intent=new Intent(getActivity().getApplicationContext(),DevelopersActivity.class);
                        break;
                    case 3:
                        intent=new Intent(getActivity().getApplicationContext(),LicenseActivity.class);
                        break;
                }
                startActivity(intent);
            }
        });
        return view;
    }




}
