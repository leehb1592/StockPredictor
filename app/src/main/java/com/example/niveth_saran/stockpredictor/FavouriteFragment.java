package com.example.niveth_saran.stockpredictor;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteFragment extends Fragment {

    final static String fileName = "fav.txt";
    final static String path = "MLMarksman";
    File myExternalFile;
    String data = "";
    ArrayList<StockDetails> temp;
    private List<StockDetails> std;


    public FavouriteFragment() {
        // Required empty public constructor
    }

    FavouriteFragment.ListFragmentItemClickListener2 ifaceItemClickListener2;

    /**
     * An interface for defining the callback method
     */
    public interface ListFragmentItemClickListener2 {
        /**
         * This method will be invoked when an item in the ListFragment is clicked
         */
        void onListFragmentItemClick2(int position);
    }

    /**
     * A callback function, executed when this fragment is attached to an activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            /** This statement ensures that the hosting activity implements ListFragmentItemClickListener */
            ifaceItemClickListener2 = (FavouriteFragment.ListFragmentItemClickListener2) activity;
        } catch (Exception e) {
            Toast.makeText(activity.getBaseContext(), "Exception", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);
        std = new ArrayList<>();

        ListView lst = (ListView) view.findViewById(R.id.listview1);
        std = getListfromFile();
        StockListAdapter stockListAdapter = new StockListAdapter(getContext(), std);
        lst.setAdapter(stockListAdapter);
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ifaceItemClickListener2.onListFragmentItemClick2(position);
            }
        });
        return view;
    }


    public ArrayList<StockDetails> getListfromFile() {
        temp=new ArrayList<StockDetails>();
        try {
            myExternalFile = new File(getActivity().getExternalFilesDir(path), fileName);
        } catch (Exception e) {

        }
        int flag=0;
        try {
            FileInputStream fis = new FileInputStream(myExternalFile);
            DataInputStream in = new DataInputStream(fis);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                data = strLine;
                String arr[]=data.split(" ");
                flag=0;
                Log.d("CREATION",Integer.toString(temp.size()));
                if(flag==0)
                {
                    temp.add(new StockDetails(arr[0],arr[1],Integer.parseInt(arr[2])));
                }


            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return temp;
    }


}