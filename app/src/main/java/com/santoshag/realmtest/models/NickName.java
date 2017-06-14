package com.santoshag.realmtest.models;

import io.realm.RealmObject;

public class NickName extends RealmObject{
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NickName(String name) {
        this.name = name;
    }

    public NickName() {
    }
}
