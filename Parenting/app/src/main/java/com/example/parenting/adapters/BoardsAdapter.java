package com.example.parenting.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parenting.R;

import java.util.ArrayList;
import java.util.Map;

public class BoardsAdapter extends BaseAdapter {
    Activity context;
    View view;
    ArrayList<String> stringlst_boards;

    public BoardsAdapter(Activity ctx, ArrayList <String> strlist)
    {

        this.context=ctx;
        this.stringlst_boards=strlist;


    }
    @Override
    public int getCount() {

        return stringlst_boards.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        TextView txt_boards;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.boards_layout, viewGroup, false);
        txt_boards= (TextView) view.findViewById(R.id.boards_textview);

        txt_boards.setText(stringlst_boards.get(i));
        return view;
    }
}
