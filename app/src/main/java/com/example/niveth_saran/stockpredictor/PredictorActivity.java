package com.example.niveth_saran.stockpredictor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PredictorActivity extends AppCompatActivity  {
    private TextView StockTView,CompanyTView, predictView;
    final static String fileName = "fav.txt";
    final static String path = "MLMarksman" ;
    String data="";
    ImageView imageView,imageView1;
    double prevValue=0;
    private Spinner spinner;
    GraphView graphView;
    File myExternalFile;
    JSONObject obj,object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predictor);
        final Intent intent=getIntent();
        final String st=intent.getStringExtra("STOCK");
        final String cp=intent.getStringExtra("COMPANY");
        final int img=intent.getIntExtra("IMAGE",0);
        imageView=(ImageView)findViewById(R.id.logoholder);
        imageView.setImageResource(img);
        StockTView=(TextView)findViewById(R.id.StockName);
        CompanyTView=(TextView)findViewById(R.id.CompanyName);
        StockTView.setText(st);
        CompanyTView.setText(cp);

        spinner=(Spinner)findViewById(R.id.spinner1);
        List<String> spin=new ArrayList<String >();
        for(int i=0;i<375;i++)
        {
            spin.add("Predicted Value after "+i+" days");
        }
        predictView=(TextView) findViewById(R.id.predict);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spin);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        try{
            obj=new JSONObject(loadJSONFromAsset(st.toLowerCase()));

        }
        catch(Exception e)
        {
            Log.d("CSV",st.toLowerCase());
        }
        graphView=(GraphView)findViewById(R.id.graph);

        DataPoint dp[]=new DataPoint[375];
        if(obj!=null)
        {
            for(int i=0;i<375;i++)
            {
                try {
                     object=obj.getJSONObject("Predicted");
                    dp[i]=new DataPoint(i, object.getDouble(Integer.toString(i)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dp);
            graphView.addSeries(series);
        }
        imageView1=(ImageView)findViewById(R.id.upordown);



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(obj==null)
                    predictView.setText("Data Not Available");
                else if(position<=375) {
                    try {
                        predictView.setText(Double.toString(object.getDouble(Integer.toString(position))));
                        prevValue=object.getDouble(Integer.toString(0));
                        Log.d("VALUE",Double.toString(prevValue));
                        if(prevValue>object.getDouble(Integer.toString(position))){
                            imageView1.setImageResource(R.drawable.reddown);
                            predictView.setTextColor(getResources().getColor(R.color.Red));}
                        else{
                            imageView1.setImageResource(R.drawable.greenup);
                            predictView.setTextColor(getResources().getColor(R.color.Green));
                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                    predictView.setTextColor(getResources().getColor(R.color.common_google_signin_btn_text_light));
            }
        });

        Button button1=(Button)findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeToFile(st+" "+cp+" "+Integer.toString(img));
            }
        });
        Button button2=(Button)findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFromFile(st+" "+cp+" "+Integer.toString(img));
                Intent intent1 = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent1);

            }


        });
    }


    public void writeToFile(String line)
    {   int flag=0;
        myExternalFile = new File(getExternalFilesDir(path), fileName);
        ArrayList<StockDetails> temp=new ArrayList<StockDetails>();
        try {
            myExternalFile = new File(getExternalFilesDir(path), fileName);
        } catch (Exception e) {

        }
        try {
            FileInputStream fis = new FileInputStream(myExternalFile);
            DataInputStream in = new DataInputStream(fis);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                data = strLine;
                String arr[]=data.split(" ");
                if(strLine.equals(line))
                    flag++;
                }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileOutputStream fos = new FileOutputStream(myExternalFile,true);
            String templ=line+"\n";
            String test[]=line.split(" ");
            if(flag==0)
            fos.write(templ.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String loadJSONFromAsset(String st) {
        String json = null;
        try {
            InputStream is = getAssets().open(st+".json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
    private void removeFromFile(String line) {
        int flag=0;
        myExternalFile = new File(getExternalFilesDir(path), fileName);
        ArrayList<StockDetails> temp=new ArrayList<StockDetails>();
        ArrayList<String> remove=new ArrayList<String>();        try {
            myExternalFile = new File(getExternalFilesDir(path), fileName);
        } catch (Exception e) {

        }
        try {
            FileInputStream fis = new FileInputStream(myExternalFile);
            DataInputStream in = new DataInputStream(fis);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                data = strLine;
                String arr[]=data.split(" ");
                if(!strLine.equals(line)){
                    remove.add(strLine);

                }
                else
                    flag++;
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(flag==0)
            Toast.makeText(getApplicationContext(),"Stock Absent in Favourites List",Toast.LENGTH_SHORT).show();
        try {
            FileOutputStream fos = new FileOutputStream(myExternalFile,false);
            for(int i=0;i<remove.size();i++)
            {
                String temp1=remove.get(i);
                temp1=temp1+"\n";
                Log.d("REMOVE",temp1);
                fos.write(temp1.getBytes());
            }
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
