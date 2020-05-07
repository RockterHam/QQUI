package com.example.qqui;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String flag = intent.getStringExtra("flag").toString();
        if (flag.equals("sms")){
            Toast.makeText(this, "Service Started bt SMS", Toast.LENGTH_SHORT).show();
            Log.i("Service", "Service Started bt SMS");
        }

        if (flag.equals("boot")){
            Toast.makeText(this, "Service Started bt BOOT", Toast.LENGTH_SHORT).show();
            Log.i("Service", "Service Started bt BOOT");
        }

        return super.onStartCommand(intent, flags, startId);
    }
}
