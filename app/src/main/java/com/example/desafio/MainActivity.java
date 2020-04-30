package com.example.desafio;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.example.desafio.Model.Article;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button bt_start;
    private ProgressDialog dialog;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("");

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        bt_start = (Button) findViewById(R.id.bt_start);

        bt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                String url = "https://www.reddit.com/r/androiddev.json?raw_json=1";

                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {

                                    ArrayList<Article> list_article = new ArrayList<Article>();

                                    JSONObject obj = new JSONObject(response);

                                    JSONArray obj_children = obj.getJSONObject("data").getJSONArray("children");

                                    Article article;

                                    for (int i = 0; i < obj_children.length(); i++) {
                                        article = new Article();
                                        String title = "";

                                        try {
                                            title = String.valueOf(obj_children.getJSONObject(i).getJSONObject("data").get("title"));
                                        } catch (Exception e) {
                                            Log.e("obj-erro", title);
                                        }

                                        article.setTitle(title);
                                        list_article.add(article);
                                    }//end for

                                    Gson gson = new Gson();
                                    String numbersJson = gson.toJson(list_article);

                                    progressBar.setVisibility(View.INVISIBLE);

                                    Intent i = new Intent(MainActivity.this, HomeActivity.class);
                                    i.putExtra("page_title", "r/androiddev");
                                    i.putExtra("json_listview", numbersJson);

                                    startActivity(i);

                                } catch (Exception e) {
                                    Toast.makeText(getApplicationContext(),  getString(R.string.msg_process_json), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),  getString(R.string.msg_process_json), Toast.LENGTH_SHORT).show();
                    }
                });

                queue.add(stringRequest);

            }//onclick
        });//bt_start event
    }//oncreate
}

