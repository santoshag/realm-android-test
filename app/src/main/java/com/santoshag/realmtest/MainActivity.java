package com.santoshag.realmtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.santoshag.realmtest.models.NickName;
import com.santoshag.realmtest.models.User;
import com.santoshag.realmtest.services.MyIntentService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @BindView(R.id.etFirstName)
    EditText etFirstName;

    @BindView(R.id.etLastName)
    EditText etLastName;

    @BindView(R.id.tvDbContent)
    TextView tvDbContent;

    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();
    }

    @OnClick(R.id.btnSaveToDB)
    public void saveToRealm() {
        final String firstName = etFirstName.getText().toString();
        final String lastName = etLastName.getText().toString();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                User user = realm.createObject(User.class);
                user.setFirstName(firstName);
                user.setLastName(lastName);

                RealmList<NickName> names = new RealmList<>();
                NickName nickName1 = realm.createObject(NickName.class);
                nickName1.setName("tom");
                NickName nickName2 = realm.createObject(NickName.class);
                nickName2.setName("dick");
                NickName nickName3 = realm.createObject(NickName.class);
                nickName3.setName("harry");

                names.add(nickName1);
                names.add(nickName2);
                names.add(nickName3);

                user.setNickNames(names);
            }
        });
    }

    @OnClick(R.id.btnDeleteDb)
    public void deleteRealmDb() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();
            }
        });
    }

    @OnClick(R.id.btnReadDb)
    public void readDb() {
        RealmQuery<User> query = realm.where(User.class);
        RealmResults<User> users = query.findAll();
        StringBuilder contextText = new StringBuilder();
        for (User user : users) {
            Log.i(TAG, "user:   " + user.getFirstName() + " " + user.getLastName());
            contextText.append(user.getFirstName()).append(" ").append(user.getLastName()).append(
                    " " + user.getNickNames().get(0).getName() + "\n");
        }
        tvDbContent.setText(contextText);
    }

    @OnClick(R.id.btnTestOnIntentService)
    public void testOnIntentService() {
        MyIntentService.startAction(getApplicationContext());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Realm.getDefaultInstance().close();
    }
}
