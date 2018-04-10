package com.sheygam.java_19_10_04_18_hw;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.show_dialog_btn)
                .setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.show_dialog_btn){
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Error!")
                    .setIcon(R.mipmap.ic_launcher)
                    .setMessage("Something was wrong")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(LoginActivity.this, "Was clocked ok", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No",null)
                    .setNeutralButton("Cancel",null)
                    .setCancelable(false)
                    .create();
            dialog.show();
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
