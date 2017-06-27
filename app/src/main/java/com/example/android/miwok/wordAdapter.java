package com.example.android.miwok;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Nitish on 16-06-2017.
 */

public class wordAdapter extends ArrayAdapter<word> {

    private int colorResourceId;

    public wordAdapter(Activity context, ArrayList<word>words , int category_colors){

        super(context,0,words);
        colorResourceId = category_colors;
    }

    @Override
    public  View getView(int position, View convertView, ViewGroup parent){
        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.sample_layout,parent,false);
        }
        word CurrentWord = getItem(position);
        TextView defaultView = (TextView)listItemView.findViewById(R.id.english_view);
        defaultView.setText(CurrentWord.getmDefaultTranslation());

        TextView miwokView = (TextView)listItemView.findViewById(R.id.miwok_view);
        miwokView.setText(CurrentWord.getmMiwokTranslation());

        ImageView iconView = (ImageView)listItemView.findViewById(R.id.image2);
        if(CurrentWord.hasImage()) {
            iconView.setImageResource(CurrentWord.getmMiwokImage());
        }
        else {
            iconView.setVisibility(View.GONE);
        }

        View colorContainer = listItemView.findViewById(R.id.text_container);
        int color = ContextCompat.getColor(getContext(), colorResourceId);
        colorContainer.setBackgroundColor(color);

        return listItemView;


    }
}
