package com.sheygam.java_19_10_04_18_hw;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class ContactListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private StoreProvider storeProvider;
    private ContactListAdapter adapter;
    private ListView contactList;
    private FrameLayout progressFrame;
    private boolean isUpdating = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storeProvider = new StoreProvider(this);
        setContentView(R.layout.activity_contact_list);
        contactList = findViewById(R.id.contact_list);
        progressFrame = findViewById(R.id.progress_frame);
        progressFrame.setOnClickListener(null);//get click events from list to frame layout
        contactList.setOnItemClickListener(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
//        adapter = new ContactListAdapter(storeProvider.getContacts());
//        contactList.setAdapter(adapter);
        new GetContactsTask().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.contact_list_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(isUpdating){
            Toast.makeText(this, "Wait for updating!", Toast.LENGTH_SHORT).show();
        }else {
            if (item.getItemId() == R.id.logout_item) {
                setResult(RESULT_OK);
                storeProvider.logout();
                finish();
            } else if (item.getItemId() == R.id.add_contact_item) {
                startActivity(new Intent(this, ContactViewActivity.class));
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Contact contact = (Contact) adapter.getItem(position);
        Intent intent = new Intent(this,ContactViewActivity.class);
        intent.putExtra("POSITION",position);
        intent.putExtra("CONTACT",contact);
        startActivity(intent);

    }

    class GetContactsTask extends AsyncTask<Void,Void,String>{
        private List<Contact> contacts;
        private boolean isSuccess = true;
        @Override
        protected void onPreExecute() {
            progressFrame.setVisibility(View.VISIBLE);
            isUpdating = true;
        }

        @Override
        protected String doInBackground(Void... voids) {
            String result = "";
            contacts = storeProvider.getContacts();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                isSuccess = false;
                result = "Error!";
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            isUpdating = false;
            progressFrame.setVisibility(View.INVISIBLE);
            if(isSuccess){
                adapter = new ContactListAdapter(contacts);
                contactList.setAdapter(adapter);
            }else{
                Toast.makeText(ContactListActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
