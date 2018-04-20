package com.sheygam.java_19_10_04_18_hw;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DownloadActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int STATUS_START = 0x01;
    public static final int STATUS_COUNT = 0x02;
    public static final int STATUS_CURRENT_FILE = 0x03;
    public static final int STATUS_PROGRESS = 0x04;
    public static final int STATUS_FINISH = 0x05;

    private TextView totalCountTxt, downloadTxt, downloadCountTxt, allDoneTxt;
    private ProgressBar myProgress, horProgress;
    private Button startBtn;

    private int totalCount = 0;

    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler(callback);
        setContentView(R.layout.activity_download);
        totalCountTxt = findViewById(R.id.countTxt);
        downloadTxt = findViewById(R.id.downloadTxt);
        downloadCountTxt = findViewById(R.id.downloadCountTxt);
        allDoneTxt = findViewById(R.id.allDoneTxt);
        myProgress = findViewById(R.id.myProgress);
        horProgress = findViewById(R.id.horProgress);
        startBtn = findViewById(R.id.startBtn);
        startBtn.setOnClickListener(this);
    }

    private Handler.Callback callback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case STATUS_START:
                    myProgress.setVisibility(View.VISIBLE);
                    startBtn.setEnabled(false);
                    allDoneTxt.setVisibility(View.INVISIBLE);
                    horProgress.setVisibility(View.VISIBLE);
                    horProgress.setProgress(0);
                    downloadTxt.setVisibility(View.VISIBLE);
                    downloadCountTxt.setVisibility(View.VISIBLE);
                    downloadCountTxt.setText("0/"+totalCount);
                    totalCountTxt.setText("");
                    break;
                case STATUS_COUNT:
                    totalCount = msg.arg1;
                    totalCountTxt.setText(String.valueOf(totalCount));
                    break;
                case STATUS_CURRENT_FILE:
                    downloadCountTxt.setText(msg.arg1+"/"+totalCount);
                    break;
                case STATUS_PROGRESS:
                    horProgress.setProgress(msg.arg1);
                    break;
                case STATUS_FINISH:
                    myProgress.setVisibility(View.INVISIBLE);
                    allDoneTxt.setVisibility(View.VISIBLE);
                    startBtn.setEnabled(true);
                    horProgress.setVisibility(View.INVISIBLE);
                    downloadTxt.setVisibility(View.INVISIBLE);
                    downloadCountTxt.setVisibility(View.INVISIBLE);
                    break;
            }
            return true;
        }
    };

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.startBtn){
            new DownloadWorker(handler).start();
        }
    }
}
