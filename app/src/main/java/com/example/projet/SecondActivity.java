package com.example.projet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {
    ArrayList<User> arrayOfUsers;
    ListView myListView;
    UserAdapter userAdapter;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /////////////////////////////////////////////

        arrayOfUsers = new ArrayList<>();
        User user0 = new User("title0", "Date", "apparaitStp", "contact0", "address0", "110", false);
        User user1 = new User("title1", "date1", "time1", "contact1", "address1", "111",false);
        User user2 = new User("title2", "date2", "time2", "contact2", "address2", "112",false);
        arrayOfUsers.add(user0);
        arrayOfUsers.add(user1);
        arrayOfUsers.add(user2);

        userAdapter = new UserAdapter(this, R.layout.custom_list_item, arrayOfUsers);

        myListView = (ListView) findViewById(R.id.lvMoments);
        myListView.setAdapter(userAdapter);

        intent = new Intent (SecondActivity.this, MainActivity.class) ;

    }

    public void onClickCheckBox(View v){
        CheckBox cb = (CheckBox)findViewById(R.id.checkBox);
        User user = userAdapter.getItem(v.getVerticalScrollbarPosition());
        user.setCheck(cb.isChecked());
        Toast.makeText(SecondActivity.this," Enregistré",Toast.LENGTH_SHORT).show();
    }


    public void set(android.view.View v) {
        Toast.makeText(SecondActivity.this," CA BOUTONNE",Toast.LENGTH_SHORT).show();
        startActivity (intent) ;
    }
}
