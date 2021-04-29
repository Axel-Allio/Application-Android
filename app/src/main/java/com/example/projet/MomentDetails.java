package com.example.projet;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MomentDetails extends AppCompatActivity {
    private int year,month,day,hours, minutes;
    private int notificationId = 1;
    private CheckBox cb1day,cb2days,cb1week;
    private Button btnPickDate;
    private Button btnPickTime;
    private Button btnMap;
    private TextView tvId;
    private EditText etTitle;
    private EditText etDate;
    private EditText etTime;
    private EditText etContact;
    private EditText etAddress;
    private EditText etPhone;
    //private TextView tvId;
    private DataBase db;
    boolean fromAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moment_details);

        btnPickDate=(Button)findViewById(R.id.btnPickDate);
        btnPickTime=(Button)findViewById(R.id.btnPickTime);

        tvId = (TextView) findViewById(R.id.tvId);
        etTitle = (EditText) findViewById(R.id.etTitle);
        etDate = (EditText) findViewById(R.id.etDate);
        etTime = (EditText) findViewById(R.id.etTime);
        etContact = (EditText) findViewById(R.id.etContact);
        etAddress = (EditText) findViewById(R.id.etAddress);
        etPhone = (EditText) findViewById(R.id.etPhone);
        //tvId=(TextView)findViewById(R.id.tvId);
        db = new DataBase(this);
        db.open();

        Intent intent = getIntent();
        fromAdd= intent.getBooleanExtra("fromAdd",false);
        if(!fromAdd){
            Bundle b= intent.getExtras();
            Moment selectedMoment= b.getParcelable("SelectedMoment");
            tvId.setText(String.valueOf(selectedMoment.getId()));
            etTitle.setText(selectedMoment.getTitle());
            etDate.setText(selectedMoment.getDate());
            etTime.setText(selectedMoment.getTime());
            etContact.setText(selectedMoment.getContact());
            etAddress.setText(selectedMoment.getAddress());
            etPhone.setText(selectedMoment.getPhone());

        }
    }

    public void saveMoment(View view) {
        String title= etTitle.getText().toString();
        String date= etDate.getText().toString();
        String time= etTime.getText().toString();
        String contact= etContact.getText().toString();
        String address= etAddress.getText().toString();
        String phone= etPhone.getText().toString();

        //////////////// Alarm ///////////////////
        Intent intent = new Intent(MomentDetails.this, AlarmReceiver.class);
        intent.putExtra("notificationId", notificationId);
        intent.putExtra("message", "Don't forget your meeting of "+etDate.getText().toString());

        // PendingIntent
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                MomentDetails.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT
        );

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        // Create time.
        Calendar startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, year);
        startTime.set(Calendar.HOUR_OF_DAY, month);
        startTime.set(Calendar.MINUTE, day);
        startTime.set(Calendar.HOUR_OF_DAY, hours);
        startTime.set(Calendar.MINUTE, minutes);
        startTime.set(Calendar.SECOND, 0);
        long alarmStartTime = startTime.getTimeInMillis();
        long timeToAdd;

        // Set Alarm
         cb1day = (CheckBox)findViewById(R.id.cb1day);
         cb2days = (CheckBox)findViewById(R.id.cb2days);
         cb1week = (CheckBox)findViewById(R.id.cb1week);
        if (cb1day.isChecked()) {
            timeToAdd = 1000 * 60 * 60 * 24 * 1;
            alarmStartTime -= timeToAdd;
        }
        if (cb2days.isChecked()) {
            timeToAdd = 1000 * 60 * 60 * 24 * 2;
            alarmStartTime -= timeToAdd;
        }
        if (cb1week.isChecked()) {
            timeToAdd = 1000 * 60 * 60 * 24 * 7;
            alarmStartTime -= timeToAdd;
        }
        if (!cb1day.isChecked() && !cb2days.isChecked()&& !cb1week.isChecked())
            alarmManager.cancel(pendingIntent);


        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmStartTime, pendingIntent);


        Toast.makeText(this, "Alarm Registered!", Toast.LENGTH_SHORT).show();
        ///////////////////////////////////////


        boolean check = false;


        if(fromAdd) {
            Moment moment = new Moment(title,date,time, contact, address, phone, check);
            db.add(moment);
        }
        else {
            Long id = Long.parseLong(tvId.getText().toString());
            Moment moment = new Moment(id,title,date,time, contact, address, phone, check);
            int n = db.update(moment);
        }
        Intent main = new Intent(this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(main);

    }

        public void onCancelClick(View v) {
        finish();
    }

    //////////////// Map ///////////////////
    public void launchMaps(View view) {
        String map = "http://maps.google.co.in/maps?q=" + etAddress.getText();
        Uri gmmIntentUri = Uri.parse(map);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        String addressTmp = etAddress.getText().toString();
        if (addressTmp != null) {
            startActivity(mapIntent);
        }
    }


    //////////////// DatePicker ///////////////////
    private void showDatePicker() {
        DatePickerFragment date= new DatePickerFragment();
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        Bundle args = new Bundle();
        args.putInt("year",year);
        args.putInt("month",month);
        args.putInt("day",day);
        date.setArguments(args);
        date.setCallBack(onDate);
        date.show(getSupportFragmentManager(),"Date Picker");
    }

    public void pickDate(View view){
        showDatePicker();
    }

    DatePickerDialog.OnDateSetListener onDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay)
        {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;
            etDate.setText(new StringBuilder().append(month +1).
                    append("-").append(day).append("-").append(year).append(" "));
        }
    };

    //////////////// TimePicker ///////////////////
    TimePickerDialog.OnTimeSetListener onTime = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hour, int minute) {
            hours = hour;
            minutes = minute;

            etTime.setText(new StringBuilder().append(hours).append(":").append(minutes));
        }
    };

    private void showTimePicker() {
        TimePickerFragment time= new TimePickerFragment();
        final Calendar c = Calendar.getInstance();
        int hours = c.get(Calendar.HOUR_OF_DAY);
        int minutes = c.get(Calendar.MINUTE);
        Bundle args = new Bundle();
        args.putInt("hours",hours);
        args.putInt("minutes",minutes);
        time.setArguments(args);
        time.setCallBack(onTime);
        time.show(getSupportFragmentManager(),"Time Picker");
    }

    public void pickTime(View view){
        showTimePicker();
    }


   /////////////////// setAlarm ///////////////////
/*   public void onClickCheckBox(View v){
       CheckBox cb1day = (CheckBox)findViewById(R.id.cb1day);
       CheckBox cb2days = (CheckBox)findViewById(R.id.cb2days);
       CheckBox cb1week = (CheckBox)findViewById(R.id.cb1week);
       if (cb1day.isChecked())
           Toast.makeText(this, "Egg checked", Toast.LENGTH_SHORT).show();
       if (cb2days.isChecked())
           Toast.makeText(this,"Salmon checked", Toast.LENGTH_SHORT).show();
       if (cb1week.isChecked())
           Toast.makeText(this,"Salmon checked", Toast.LENGTH_SHORT).show();
   }*/

}

