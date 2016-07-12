package com.boqii.plant.data.Login.model;

/**
 * Created by bin.teng on 7/8/16.
 */
public class User {

    private String uid;

    private String alias;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", alias='" + alias + '\'' +
                '}';
    }
}
