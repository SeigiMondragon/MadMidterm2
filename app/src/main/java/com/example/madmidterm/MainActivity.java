package com.example.madmidterm;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
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
    TextView lblBookName, lblChapter, lblVerse, lblCharacter, lblType;
    String url = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Spinner spinner = findViewById(R.id.spinner);

        lblChapter = findViewById(R.id.lblChapter);
        lblBookName = findViewById(R.id.lblBookName);
        lblVerse = findViewById(R.id.lblVerse);
        lblCharacter = findViewById(R.id.lblCharacter);
        lblType = findViewById(R.id.lblType);




         url = "https://mocki.io/v1/e71d8409-bf2b-4dbf-a6ee-f70b313e34d0";



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
                                String characterConcat = "";

                                JSONObject bookObject = response.getJSONObject(position);
                                String bookName = bookObject.getString("BookName");
                                int bookChapter = bookObject.getInt("Chapters");
                                int bookVerses = bookObject.getInt("Verses");
                                JSONArray charactersJsonArray = bookObject.getJSONArray("Characters");

                                for (int i = 0; i< charactersJsonArray.length(); i++){
                                    characters [i]  = charactersJsonArray.getString(i);

                                    if(i== charactersJsonArray.length()-1){
                                        characterConcat += characters[i];

                                    }else{
                                        characterConcat  += characters[i] + ", ";
                                    }

                                }

                                Log.d("Concat", characterConcat);


                                JSONObject typeObject = bookObject.getJSONObject("Type");
                                String deliveryType = typeObject.getString("Delivery Type");
                                String literatureType = typeObject.getString("Literature Type");
                                String testamentType = typeObject.getString("Testament Type");


                                lblBookName.setText(bookName);
                                lblChapter.setText("No. Of Chapter: " + String.valueOf(bookChapter) );
                                lblVerse.setText( "No. Of Verse: " + String.valueOf(bookVerses));
                                lblCharacter.setText("Characters: \n"+ characterConcat);
                                lblType.setText("Delivery Type: " + deliveryType + "\n Literature Type: " + literatureType + "\n Testament Type: "+ testamentType);



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

    public void bookCall(){


    }

}