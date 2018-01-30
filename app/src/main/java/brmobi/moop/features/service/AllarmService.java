package brmobi.moop.features.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AllarmService extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
       MyIntentService.startActionFoo(context);
    }
}
