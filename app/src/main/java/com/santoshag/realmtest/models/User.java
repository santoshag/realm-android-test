package com.santoshag.realmtest.models;

import io.realm.RealmList;
import io.realm.RealmObject;

public class User extends RealmObject{
    private String firstName;
    private String lastName;
    RealmList<NickName> nickNames;

    public RealmList<NickName> getNickNames() {
        return nickNames;
    }

    public void setNickNames(RealmList<NickName> nickNames) {
        if(this.nickNames == null){
            this.nickNames = new RealmList<>();
        }
        this.nickNames.addAll(nickNames);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
