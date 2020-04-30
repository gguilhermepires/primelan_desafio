package com.example.desafio;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.desafio.Adapters.MyListViewAdapter;
import com.example.desafio.Model.Article;

import org.json.JSONArray;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    TextView tv_page_title;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tv_page_title = (TextView) findViewById(R.id.activity_home_tv_page_title);
        lv = (ListView) findViewById(R.id.listview_article);

        ArrayList<Article> list_article = new ArrayList<Article>();
        MyListViewAdapter ad = new MyListViewAdapter(this, list_article);
        lv.setAdapter(ad);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            String detailValue = extras.getString("page_title");

            if (detailValue != null)
                tv_page_title.setText(detailValue);

            String json_article = extras.getString("json_listview");
            new UpdateListView(HomeActivity.this, json_article).execute();

        }//end if extras
    }//end onCreate
}

class UpdateListView extends AsyncTask<String, Integer, ArrayList<Article>> {
    private HomeActivity activity;
    private String json;

    public UpdateListView(HomeActivity activity, String json) {
        this.activity = activity;
        this.json = json;
    }

    @Override
    protected ArrayList<Article> doInBackground(String... strings) {
        ArrayList<Article> list = new ArrayList<Article>();
        try {
            JSONArray obj_children = new JSONArray(this.json);
            Article article;
            for (int i = 0; i < obj_children.length(); i++) {
                article = new Article();
                String title = "";
                try {
                    title = obj_children.getJSONObject(i).getString("title");
                    article.setTitle(title);
                    list.add(article);
                } catch (Exception e) { }
            }
            return list;
        } catch (Exception e) {
            return list;
        }
    }

    protected void onPostExecute(ArrayList<Article> result) {
        ListView lv = (ListView) activity.findViewById(R.id.listview_article);
        MyListViewAdapter ad = new MyListViewAdapter(activity, result);
        lv.setAdapter(ad);
    }
}

