package com.example.qqui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        String action = intent.getAction();
        Intent i = new Intent(context, MyService.class);
        if (action.equals("android.provider.Telephony.SMS_RECEIVED")){
            i.putExtra("flag","sms");
        }

        if (action.equals("android.intent.action.BOOT_COMPLETED")){
            i.putExtra("flag","boot");
        }
        context.startService(i);
    }
}
