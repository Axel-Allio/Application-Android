package com.example.projet;

public class User {
    public String title;
    public String date;
    public String time;
    public String contact;
    public String address;
    public String phone;
    public boolean check;


    public int photo;
    public User(String title, String date, String time, String contact, String address, String phone, boolean check){
        this.title=title;
        this.date=date;
        this.time=time;
        this.contact=contact;
        this.address=address;
        this.phone=phone;
        this.check=check;
    }

    //////////////// SETTER ///////////////
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

    //////////////// GETTER ///////////////
    public boolean getCheck(){
        return this.check;
    }
}
