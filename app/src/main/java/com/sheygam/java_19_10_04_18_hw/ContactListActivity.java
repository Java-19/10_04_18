package com.sheygam.java_19_10_04_18_hw;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ContactListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private StoreProvider storeProvider;
    private ContactListAdapter adapter;
    private ListView contactList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storeProvider = new StoreProvider(this);
        setContentView(R.layout.activity_contact_list);
        contactList = findViewById(R.id.contact_list);
        contactList.setOnItemClickListener(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter = new ContactListAdapter(storeProvider.getContacts());
        contactList.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.contact_list_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.logout_item){
            setResult(RESULT_OK);
            storeProvider.logout();
            finish();
        }else if(item.getItemId() == R.id.add_contact_item){
            startActivity(new Intent(this, ContactViewActivity.class));
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
}
