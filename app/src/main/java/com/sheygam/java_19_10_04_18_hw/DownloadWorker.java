package com.sheygam.java_19_10_04_18_hw;


import android.os.Handler;
import android.os.Message;

import java.util.Random;

public class DownloadWorker extends Thread{
    private Handler handler;

    public DownloadWorker(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        handler.sendEmptyMessage(DownloadActivity.STATUS_START);
        Random rnd = new Random();
        int fileCount = rnd.nextInt(16) + 1;
        Message msg = handler.obtainMessage(DownloadActivity.STATUS_COUNT,fileCount,-1);
        handler.sendMessage(msg);
        for (int i = 0; i < fileCount; i++) {
            msg = handler.obtainMessage(DownloadActivity.STATUS_CURRENT_FILE,i+1,-1);
            handler.sendMessage(msg);
            for (int j = 0; j < 100; j++) {
                try {
                    sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                msg = handler.obtainMessage(DownloadActivity.STATUS_PROGRESS,j+1,-1);
                handler.sendMessage(msg);
            }
        }
        handler.sendEmptyMessage(DownloadActivity.STATUS_FINISH);
    }
}
