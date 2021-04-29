package com.example.projet;

import android.os.Parcel;
import android.os.Parcelable;

public class Moment implements Parcelable {

    private long id;
    private String title;
    private String date;
    private String time;
    private String contact;
    private String address;
    private String phone;
    private boolean check;
    //public int photo;

    //////////////// CONSTRUCTORS ///////////////
    public Moment(String title, String date, String time, String contact, String address, String phone, boolean check){
        this.title=title;
        this.date=date;
        this.time=time;
        this.contact=contact;
        this.address=address;
        this.phone=phone;
        this.check=check;
    }

    public Moment(long id, String title, String date, String time, String contact, String address, String phone, boolean check){
        this.id = id;
        this.title=title;
        this.date=date;
        this.time=time;
        this.contact=contact;
        this.address=address;
        this.phone=phone;
        this.check=check;
    }

    //////////////// SETTERS ///////////////
    public void setId(long id){
        this.id=id;
    }
    public void setTitle(String title){
        this.title=title;
    }
    public void setDate(String date){
        this.date=date;
    }
    public void setTime(String time){
        this.time=time;
    }
    public void setContact(String contact){
        this.contact=contact;
    }
    public void setAddress(String address){
        this.address=address;
    }
    public void setPhone(String phone){
        this.phone=phone;
    }
    public void setCheck(boolean bool){
        this.check=bool;
    }

    //////////////// GETTERS ///////////////
    public long getId(){
        return this.id;
    }
    public String getTitle(){
        return this.title;
    }
    public String getDate( ){return this.date;
    }
    public String getTime (){
        return this.time;
    }
    public String getContact(){
        return this.contact;
    }
    public String getAddress(){
        return this.address;
    }
    public String getPhone(){
        return this.phone;
    }
    public boolean getCheck(){
        return this.check;
    }

    @Override
    public int describeContents() {
        return hashCode();

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(date);
        dest.writeString(time);
        dest.writeString(contact);
        dest.writeString(address);
        dest.writeString(phone);
        //dest.writeBoolean(check);
    }

    public static final Parcelable.Creator<Moment> CREATOR = new Parcelable.Creator<Moment>(){
        @Override
        public Moment createFromParcel(Parcel parcel) {
            return new Moment(parcel);
        }
        @Override
        public Moment[] newArray(int size) {
            return new Moment[size];
        }
    };
    public Moment(Parcel parsel){
        id=parsel.readLong();
        title=parsel.readString();
        date=parsel.readString();
        time=parsel.readString();
        contact=parsel.readString();
        address=parsel.readString();
        phone=parsel.readString();
    }
}


