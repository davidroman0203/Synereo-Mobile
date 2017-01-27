package com.dr.sharingtest.model;

import java.security.Timestamp;

import io.realm.RealmObject;

/**
 * Created by User on 1/25/2017.
 */

public class Amplification extends RealmObject {
    private String timeStamp;
    private int	value;
    private String assetURL;
    private String shareURL;
    private String user;
    private String referrer;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    public String getShareURL() {
        return shareURL;
    }

    public void setShareURL(String shareURL) {
        this.shareURL = shareURL;
    }

    public String getAssetURL() {
        return assetURL;
    }

    public void setAssetURL(String assetURL) {
        this.assetURL = assetURL;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }


}
