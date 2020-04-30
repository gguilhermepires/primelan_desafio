package com.example.desafio.Adapters;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import com.example.desafio.Model.Article;
import com.example.desafio.R;

import java.util.ArrayList;

 public class MyListViewAdapter extends ArrayAdapter<Article> {

    public MyListViewAdapter(Activity ctx, ArrayList<Article> articles ){
        super(ctx,0,articles);
    }

    @NonNull
    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {

        View listItemView = convertView;

        if(listItemView==null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item,parent,false
            );
        }

        Article article = getItem(position);

        TextView tv_article_title = (TextView) listItemView.findViewById(R.id.list_item_title);
        tv_article_title.setText(article.getTitle());

        return listItemView;
    }
}
