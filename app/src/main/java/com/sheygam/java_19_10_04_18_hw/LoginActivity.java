package com.sheygam.java_19_10_04_18_hw;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText inputEmail, inputPassword;
    private Button loginBtn;
    private StoreProvider storeProvider;
    private ProgressBar myProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storeProvider = new StoreProvider(this);
        if(storeProvider.isAuth()){
            startListActivity();
        }
        setContentView(R.layout.activity_login);
        inputEmail = findViewById(R.id.input_email);
        myProgress = findViewById(R.id.my_progress);
        inputPassword = findViewById(R.id.input_password);
        loginBtn = findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.login_btn){
//            String email = inputEmail.getText().toString();
//            String password = inputPassword.getText().toString();
//            storeProvider.login(email,password);
//            startListActivity();
            new LoginTask().execute();
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

    class LoginTask extends AsyncTask<Void,Void,String>{
        private String email, password;
        private boolean isSuccess = true;
        @Override
        protected void onPreExecute() {
            email = inputEmail.getText().toString();
            password = inputPassword.getText().toString();
            inputEmail.setVisibility(View.INVISIBLE);
            inputPassword.setVisibility(View.INVISIBLE);
            loginBtn.setVisibility(View.INVISIBLE);
            myProgress.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... voids) {
            String result = "Login ok!";
            storeProvider.login(email,password);
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
            inputPassword.setVisibility(View.VISIBLE);
            inputEmail.setVisibility(View.VISIBLE);
            loginBtn.setVisibility(View.VISIBLE);
            myProgress.setVisibility(View.INVISIBLE);
            Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
            if(isSuccess){
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startListActivity();
                    }
                },1500);
            }
        }
    }
}
