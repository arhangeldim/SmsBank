package arhangel.dim.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 *
 */
public class SmsReceiver extends BroadcastReceiver {
    private static final String TAG = "SmsReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i(TAG, "onReceive() called.");

        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Object[] objects = (Object[]) bundle.get("pdus");
            SmsMessage[] messages = new SmsMessage[objects.length];
            for (int i = 0; i < objects.length; i++) {
                messages[i] = SmsMessage.createFromPdu((byte[]) objects[i]);
                String sender = messages[i].getOriginatingAddress();
                String messageBody = messages[i].getMessageBody();
                Log.i(TAG, "Sms: " + sender + " - " + messageBody);
            }
        }
    }
}
