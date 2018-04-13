package com.sheygam.java_19_10_04_18_hw;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class ContactViewActivity extends AppCompatActivity {
    private Contact currentContact;
    private int currentPosition;
    private StoreProvider storeProvider;

    private TextView nameTxt, emailTxt, phoneTxt,addressTxt;
    private EditText inputName, inputEmail, inputPhone, inputAddress;
    private ViewGroup inputWrapper,viewWrapper;

    private MenuItem deleteItem,doneItem,editItem;

    private boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storeProvider = new StoreProvider(this);
        setContentView(R.layout.activity_contact_view);
        currentPosition = getIntent().getIntExtra("POSITION",-1);
        if (currentPosition < 0){
            currentContact = new Contact("Default","Default","Default","Default");
        }else{
            currentContact = (Contact) getIntent().getSerializableExtra("CONTACT");
        }

        nameTxt = findViewById(R.id.name_txt);
        emailTxt = findViewById(R.id.email_txt);
        phoneTxt = findViewById(R.id.phone_txt);
        addressTxt = findViewById(R.id.address_txt);
        inputName = findViewById(R.id.input_name);
        inputEmail = findViewById(R.id.input_email);
        inputPhone = findViewById(R.id.input_phone);
        inputAddress = findViewById(R.id.input_address);
        inputWrapper = findViewById(R.id.input_wrapper);
        viewWrapper = findViewById(R.id.view_wrapper);

        inputEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("MY_TAG", "beforeTextChanged() called with: s = [" + s + "], start = [" + start + "], count = [" + count + "], after = [" + after + "]");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("MY_TAG", "onTextChanged() called with: s = [" + s + "], start = [" + start + "], before = [" + before + "], count = [" + count + "]");
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("MY_TAG", "afterTextChanged() called with: s = [" + s + "]");
            }
        });

    }


    private void setMenuEdit(){
        deleteItem.setVisible(false);
        editItem.setVisible(false);
        doneItem.setVisible(true);
        viewWrapper.setVisibility(View.GONE);
        isEdit = true;
        if(currentPosition >= 0){
            inputName.setText(currentContact.getName());
            inputEmail.setText(currentContact.getEmail());
            inputPhone.setText(currentContact.getPhone());
            inputAddress.setText(currentContact.getAddress());
        }
        inputWrapper.setVisibility(View.VISIBLE);
    }
    private void setMenuView(){
        isEdit = false;
        deleteItem.setVisible(true);
        editItem.setVisible(true);
        doneItem.setVisible(false);
        inputWrapper.setVisibility(View.GONE);
        nameTxt.setText(currentContact.getName());
        emailTxt.setText(currentContact.getEmail());
        phoneTxt.setText(currentContact.getPhone());
        addressTxt.setText(currentContact.getAddress());
        viewWrapper.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_contact_menu,menu);
        doneItem = menu.findItem(R.id.done_item);
        editItem = menu.findItem(R.id.edit_item);
        deleteItem = menu.findItem(R.id.remove_item);
        if(currentPosition < 0){
            setMenuEdit();
        }else{
            setMenuView();
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.done_item:
                currentContact.setName(inputName.getText().toString());
                currentContact.setPhone(inputPhone.getText().toString());
                currentContact.setEmail(inputEmail.getText().toString());
                currentContact.setAddress(inputAddress.getText().toString());
                if(currentPosition<0){
                    storeProvider.add(currentContact);
                }else{
                    storeProvider.update(currentContact,currentPosition);
                }
                finish();
                break;
            case R.id.remove_item:
                storeProvider.remove(currentPosition);
                finish();
                break;
            case R.id.edit_item:
                setMenuEdit();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialog(){
        final String name = inputName.getText().toString();
        final String email = inputEmail.getText().toString();
        final String phone = inputPhone.getText().toString();
        final String address = inputAddress.getText().toString();

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Save changed")
                .setMessage("Do you want save changed?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        currentContact.setName(name);
                        currentContact.setEmail(email);
                        currentContact.setPhone(phone);
                        currentContact.setAddress(address);
                        storeProvider.update(currentContact,currentPosition);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setMenuView();
                    }
                })
                .create();
        if(name.equals(currentContact.getName()) && email.equals(currentContact.getEmail())
        && phone.equals(currentContact.getPhone()) && address.equals(currentContact.getAddress())){
            setMenuView();
        }else{
            dialog.show();
        }
    }

    @Override
    public void onBackPressed() {
        if(isEdit && currentPosition >=0){
           showDialog();
        }else {
            super.onBackPressed();
        }
    }
}
