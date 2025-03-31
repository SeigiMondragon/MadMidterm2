package com.example.madmidterm;

import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        String url = "https://mocki.io/v1/2e6af150-20a1-4966-a6c7-100331830790";

        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url,null,

                response->{
                    try{
                        String [] characters = new String[response.length()];

                        JSONObject bookObject = response.getJSONObject(0);
                        String bookName = bookObject.getString("BookName");
                        int bookChapter = bookObject.getInt("Chapters");
                        int bookVerses = bookObject.getInt("Verses");
                        JSONArray charactersJsonArray = bookObject.getJSONArray("Characters");
                        for (int i = 0; i< charactersJsonArray.length(); i++){
                            characters [i]  = charactersJsonArray.getString(i);
                        }

                        JSONObject typeObject = bookObject.getJSONObject("Type");
                        String deliveryType = typeObject.getString("Delivery Type");
                        String literatureType = typeObject.getString("Literature Type");
                        String testamentType = typeObject.getString("Testament Type");



                    }catch (Exception e){
                        Toast.makeText(this, "Error has occurred", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {

                }
        );
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(req);

    }

}