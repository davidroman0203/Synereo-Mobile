package com.dr.sharingtest.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 1/25/2017.
 */

public class DataAccess {
    public static List<Amplification> amplifications;

    public DataAccess(){
        amplifications = getAmplifications();
    }

    public List<Amplification> getAmplifications(){
        amplifications = new ArrayList<>();

        Amplification amplification = new Amplification();
        amplification.setTimeStamp("25/01/2017 10:00:0");
        amplification.setValue(1);
        amplification.setAssetURL("https://www.youtube.com/watch?v=dyE2MLq24OE");
        amplification.setShareURL("snip.ly/mcm26");
        amplification.setUser("Yaniv");
        amplification.setReferrer("David");
        amplifications.add(amplification);

        Amplification amplification2 = new Amplification();
        amplification2.setTimeStamp("25/01/2017 11:00:0");
        amplification2.setValue(5);
        amplification2.setAssetURL("https://www.youtube.com/watch?v=K1xnYFCZ9Yg");
        amplification2.setShareURL("snip.ly/5ku8p");
        amplification2.setUser("Yaniv");
        amplification2.setReferrer("James");
        amplifications.add(amplification2);

        Amplification amplification3 = new Amplification();
        amplification3.setTimeStamp("25/01/2017 13:00:0");
        amplification3.setValue(10);
        amplification3.setAssetURL("https://www.youtube.com/watch?v=K1xnYFCZ9Yg");
        amplification3.setShareURL("snip.ly/5ku8p");
        amplification3.setUser("Yaniv");
        amplification3.setReferrer("David");
        amplifications.add(amplification3);

        return amplifications;
    }
}
