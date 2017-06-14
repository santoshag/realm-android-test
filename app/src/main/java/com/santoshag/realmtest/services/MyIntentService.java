package com.santoshag.realmtest.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.santoshag.realmtest.R;
import com.santoshag.realmtest.models.User;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * helper methods.
 */
public class MyIntentService extends IntentService {

    private static final String ACTION_NOTIFY = "com.santoshag.realmtest.services.action.notify";

    public MyIntentService() {
        super("MyIntentService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startAction(Context context) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION_NOTIFY);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_NOTIFY.equals(action)) {
                handleActionFoo(getApplicationContext());
            }
        }
    }

    /**
     * Handle action Notify in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(Context context) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        Realm realm = Realm.getDefaultInstance();
        RealmQuery<User> query = realm.where(User.class);
        RealmResults<User> users = query.findAll();

        NotificationCompat.InboxStyle inboxStyle =
                new NotificationCompat.InboxStyle();
        for (User user : users) {
            inboxStyle.addLine(user.getFirstName() + " " + user.getLastName());

        }
        inboxStyle.setBigContentTitle("DB content:");

        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("My notification")
                .setStyle(inboxStyle);

        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Builds the notification and issues it.
        mNotifyMgr.notify(1, builder.build());
    }

}
