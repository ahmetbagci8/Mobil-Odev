package com.example.mobilodev;

import java.util.ArrayList;

import static com.example.mobilodev.R.drawable.ic_account_box_black_24dp;

public class Person {
    private String userName;
    private String userPassword;
    private int imageID;

    public Person(String userName, String userPassword, int imageID) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.imageID = imageID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public static ArrayList<Person> getData(){
        ArrayList<Person> personArrayList = new ArrayList<Person>();
        personArrayList.add(new Person("ahmet","1234", R.drawable.ic_account_box_black_24dp));
        personArrayList.add(new Person("mehmet","asdf", R.drawable.ic_account_circle_black_24dp));
        personArrayList.add(new Person("mustafa","qwert", R.drawable.ic_accessibility_black_24dp));
        personArrayList.add(new Person("sevgi","qwe123", R.drawable.ic_account_box_black_24dp));
        personArrayList.add(new Person("yusuf","123987", R.drawable.ic_account_circle_black_24dp));
        personArrayList.add(new Person("tarık","123456", R.drawable.ic_accessibility_black_24dp));
        personArrayList.add(new Person("fazlı","qwe123", R.drawable.ic_account_box_black_24dp));
        personArrayList.add(new Person("hayri","123987", R.drawable.ic_account_circle_black_24dp));
        personArrayList.add(new Person("aykut","123456", R.drawable.ic_accessibility_black_24dp));
        return personArrayList;
    }


}
