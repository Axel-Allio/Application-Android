package com.example.projet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterTitle extends BaseAdapter {

    private Context context;
    int resource;
    private ArrayList<User> users;
    private LayoutInflater inflater;

    public AdapterTitle(Context c, int resource, ArrayList<User> users){
        this.context=c;
        this.users=users;
        this.resource=resource;
        this.inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public User getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        View view;
        if(convertView == null) {
            view = inflater.inflate(R.layout.test,null, false);
        } else {
            view = convertView;
        }

        TextView text1 = (TextView) view.findViewById(R.id.title);
        User user = getItem(position);
        text1.setText(user.title);
        return view;

    }
}