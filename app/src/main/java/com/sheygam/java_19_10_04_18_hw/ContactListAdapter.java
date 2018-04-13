package com.sheygam.java_19_10_04_18_hw;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ContactListAdapter extends BaseAdapter{
    private List<Contact> contacts;

    public ContactListAdapter(@NonNull List<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.contact_row,parent,false);
        }
        Contact contact = contacts.get(position);
        TextView nameTxt = convertView.findViewById(R.id.row_name_txt);
        TextView emailTxt = convertView.findViewById(R.id.row_email_txt);
        nameTxt.setText(contact.getName());
        emailTxt.setText(contact.getEmail());
        return convertView;
    }
}
