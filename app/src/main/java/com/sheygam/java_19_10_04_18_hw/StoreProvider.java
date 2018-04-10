package com.sheygam.java_19_10_04_18_hw;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class StoreProvider {
    private Context context;
    private static final String SP_AUTH = "auth";
    private static final String SP_CNT = "contacts";

    public StoreProvider(Context context) {
        this.context = context;
    }


    public void login(String email, String password){
        context.getSharedPreferences(SP_AUTH,Context.MODE_PRIVATE)
                .edit()
                .putString("CURRENT",email+password)
                .putBoolean("AUTH",true)
                .apply();
    }

    public void logout(){
        context.getSharedPreferences(SP_AUTH,Context.MODE_PRIVATE)
                .edit()
                .putBoolean("AUTH",false)
                .apply();
    }

    public boolean isAuth(){
        return context.getSharedPreferences(SP_AUTH,Context.MODE_PRIVATE)
                .getBoolean("AUTH",false);
    }

    public List<Contact> getContacts(){
        String token = getToken();

        ArrayList<Contact> contacts = new ArrayList<>();
        String str = context.getSharedPreferences(SP_CNT,Context.MODE_PRIVATE)
                .getString(token,null);
        if(str != null){
            String[] arr = str.split(";");
            for (int i = 0; i < arr.length; i++) {
                contacts.add(Contact.newInstance(arr[i]));
            }
        }
        return contacts;
    }

    public void update(Contact contact, int position){
        String token = getToken();
        String contacts = context.getSharedPreferences(SP_CNT,Context.MODE_PRIVATE)
                .getString(token,null);
        if(contacts != null){
            String[] arr = contacts.split(";");
            for (int i = 0; i < arr.length; i++) {
                String current = arr[i];
                if(i == position){
                    current = contact.toString();
                }
                if(i == 0){
                    contacts = current;
                }else{
                    contacts += ";" + current;
                }
            }
            context.getSharedPreferences(SP_CNT,Context.MODE_PRIVATE)
                    .edit()
                    .putString(token,contacts)
                    .apply();
        }
    }

    public void remove(int position){
        String token = getToken();
        String contacts = context.getSharedPreferences(SP_CNT,Context.MODE_PRIVATE)
                .getString(token,null);
        if(contacts!= null){
            String[] arr = contacts.split(";");
            contacts = "";
            if(arr.length == 1 && position == 0){
                context.getSharedPreferences(SP_CNT,Context.MODE_PRIVATE)
                        .edit()
                        .remove(token)
                        .apply();
            }else{
                for (int i = 0; i < arr.length; i++) {
                    if(i == position){
                        continue;
                    }

                    if(contacts.isEmpty()){
                        contacts = arr[i];
                    }else{
                        contacts += ";" + arr[i];
                    }
                }
                context.getSharedPreferences(SP_CNT,Context.MODE_PRIVATE)
                        .edit()
                        .putString(token,contacts)
                        .apply();
            }
        }
    }
    public void add(Contact contact){
        String token = getToken();
        String contacts = context.getSharedPreferences(SP_CNT,Context.MODE_PRIVATE)
                .getString(token,null);
        if(contacts == null){
            contacts = contact.toString();
        }else{
            contacts += ";" + contact.toString();
        }
        context.getSharedPreferences(SP_CNT,Context.MODE_PRIVATE)
                .edit()
                .putString(token,contacts)
                .apply();

    }




    private String getToken(){
        String token = context.getSharedPreferences(SP_AUTH,Context.MODE_PRIVATE)
                .getString("CURRENT", null);
        if(token == null){
            throw new RuntimeException("Some thing was wrong!");
        }
        return token;
    }
}
