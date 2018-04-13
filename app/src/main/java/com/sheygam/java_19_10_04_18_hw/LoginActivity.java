package com.sheygam.java_19_10_04_18_hw;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText inputEmail, inputPassword;
    private Button loginBtn;
    private StoreProvider storeProvider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storeProvider = new StoreProvider(this);
        if(storeProvider.isAuth()){
            startListActivity();
        }
        setContentView(R.layout.activity_login);
        inputEmail = findViewById(R.id.input_email);
        inputPassword = findViewById(R.id.input_password);
        loginBtn = findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.login_btn){
            String email = inputEmail.getText().toString();
            String password = inputPassword.getText().toString();
            storeProvider.login(email,password);
            startListActivity();
        }
    }

    private void startListActivity(){
        startActivityForResult(new Intent(this,ContactListActivity.class),1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_OK){
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);

    }
}
