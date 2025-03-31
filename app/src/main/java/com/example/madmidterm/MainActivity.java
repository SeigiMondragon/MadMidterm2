package com.example.madmidterm;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Spinner spinner;

    String url = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Spinner spinner = findViewById(R.id.spinner);


        url = "https://mocki.io/v1/e71d8409-bf2b-4dbf-a6ee-f70b313e34d0";

        // Setting Up the Spinner Item
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url,null,
                response -> {
                    try {
                        String [] book= new String[response.length()];
                        for (int i = 0; i < response.length(); i++)
                        {
                            book [i] = response.getJSONObject(i).getString("BookName");
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_list, book);
                        adapter.setDropDownViewResource(R.layout.spinner_list);
                        spinner.setAdapter(adapter);
                    }catch (Exception e){
                        Toast.makeText(MainActivity.this, "Error has occurred", Toast.LENGTH_SHORT).show();
                    }

                },error -> {

        }

        );
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(req);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url,null,

                        response->{
                            try{
                                String [] characters = new String[response.length()];


                                JSONObject bookObject = response.getJSONObject(position);
                                String bookName = bookObject.getString("BookName");
                                int bookChapter = bookObject.getInt("Chapters");
                                int bookVerses = bookObject.getInt("Verses");
                                JSONArray charactersJsonArray = bookObject.getJSONArray("Characters");
                               /* for (int i = 0; i< charactersJsonArray.length(); i++){
                                    characters [i]  = charactersJsonArray.getString(i);
                                }

                                JSONObject typeObject = bookObject.getJSONObject("Type");
                                String deliveryType = typeObject.getString("Delivery Type");
                                String literatureType = typeObject.getString("Literature Type");
                                String testamentType = typeObject.getString("Testament Type");
                                */


                            }catch (Exception e){
                                Toast.makeText(MainActivity.this, "Error has occurred", Toast.LENGTH_SHORT).show();
                            }
                        },
                        error -> {

                        }

                );
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(req);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });

    }
}