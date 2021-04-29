package com.example.projet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.DialogFragment;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    static String CHANNEL_ID= "channel_01";
    static int NOTIFICATION_ID=100;
    static int REQUEST_CODE= 200;


    ArrayList<User> arrayOfUsers;
    ListView lv;
    AdapterTitle adapterTitle;
    Intent intent;
    private DataBase db;

    public void chargeData(){
        final String[] from = new String[]{DataBase._ID,
                DataBase.TITLE, DataBase.MDATE,DataBase.TIME, DataBase.CONTACT,DataBase.ADDRESS, DataBase.PHONE};
        final int[]to= new int[]{R.id.tvId,R.id.tvTitle,R.id.tvDate,R.id.tvTime,R.id.tvContact,R.id.tvAddress,R.id.tvPhone,};

        Cursor c = db.getAllMoments();
        SimpleCursorAdapter adapter= new SimpleCursorAdapter(this,R.layout.moment_item,c,from,to,0);
        adapter.notifyDataSetChanged();
        lv.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db=new DataBase(this);
        db.open();

        lv = (ListView) findViewById(R.id.lvMoments);
        lv.setEmptyView(findViewById(R.id.tvEmpty));
        chargeData();

        registerForContextMenu(lv);




        /*arrayOfUsers = new ArrayList<>();
        User user0 = new User("title0", "Date", "apparaitStp", "contact0", "address0", "110", false);
        User user1 = new User("title1", "date1", "time1", "contact1", "address1", "111",false);
        User user2 = new User("title2", "date2", "time2", "contact2", "address2", "112",false);
        arrayOfUsers.add(user0);
        arrayOfUsers.add(user1);
        arrayOfUsers.add(user2);*/

        //String[] myStringArray = getResources().getStringArray(R.array.my_string_array);

        //ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,myStringArray);

        //ListView myListView =(ListView)findViewById(R.id.myListView);

        //adapterTitle = new AdapterTitle(this, R.layout.test, arrayOfUsers);
        //lv.setAdapter(adapterTitle);

        intent = new Intent (this, MomentDetails.class) ;


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                String idItem= ((TextView)view.findViewById(R.id.tvId)).getText().toString();
                String titleItem= ((TextView)view.findViewById(R.id.tvTitle)).getText().toString();
                String dateItem= ((TextView)view.findViewById(R.id.tvDate)).getText().toString();
                String timeItem= ((TextView)view.findViewById(R.id.tvTime)).getText().toString();
                String contactItem= ((TextView)view.findViewById(R.id.tvContact)).getText().toString();
                String addressItem= ((TextView)view.findViewById(R.id.tvAddress)).getText().toString();
                String phoneItem= ((TextView)view.findViewById(R.id.tvPhone)).getText().toString();

                Moment pMoment= new Moment(Long.parseLong(idItem),titleItem,dateItem,timeItem,contactItem,addressItem,phoneItem,false);
                Intent intent = new Intent(getApplicationContext(), MomentDetails.class);
                intent.putExtra("SelectedMoment",pMoment);


                intent.putExtra("fromAdd",false);
                startActivity(intent);
            }
        });
    }



    ////////////////////// Add ///////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.moment_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.new_moment:{
                Intent intent=new Intent(this,MomentDetails.class);
                intent.putExtra("fromAdd",true);
                startActivity(intent);
                return true;
            }
            case R.id.search: {
                Toast.makeText(this, "Search", Toast.LENGTH_LONG).show();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    ////////////////////// Delete ///////////////////////
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info= (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        switch(item.getItemId()){
            case R.id.delete: {
                db.delete(info.id);
                chargeData();
                return true;
            }
            case R.id.share:{
                shareMethod();
            }
        }
        return super.onContextItemSelected(item);
    }

    ////////////////////// Share ///////////////////////
    public void shareMethod() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "Share App")) ;
    }

    ////////////////////// Alarm Setter ///////////////////////
    /*public void showDialog() {
        DialogFragment alarmDialog = new AlarmDialog();
        alarmDialog.show(getSupportFragmentManager(),"Tag");
    }*/
}

