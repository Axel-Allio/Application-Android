package com.example.projet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class UserAdapter extends BaseAdapter {

    private Context context;
    int resource;
    private ArrayList<User> users;
    private LayoutInflater inflater;

    public UserAdapter(Context c, int resource, ArrayList<User> users){
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
            view = inflater.inflate(R.layout.custom_list_item,null, false);
        } else {
            view = convertView;
        }

        TextView text1 = (TextView) view.findViewById(R.id.title);
        TextView text2 = (TextView) view.findViewById(R.id.date);
        TextView text3 = (TextView) view.findViewById(R.id.time);
        TextView text4 = (TextView) view.findViewById(R.id.contact);
        TextView text5 = (TextView) view.findViewById(R.id.address);
        TextView text6 = (TextView) view.findViewById(R.id.phone);
        //CheckBox checkBox = (CheckBox) view.findViewById(R.id.state);
        User user = getItem(position);
        text1.setText(user.title);
        text2.setText(user.date);
        text3.setText(user.time);
        text4.setText(user.contact);
        text5.setText(user.address);
        text6.setText(user.phone);
        return view;

    }
}
