package com.dr.sharingtest.utils;

import android.graphics.Bitmap;
import android.view.View;

/**
 * Created by User on 1/26/2017.
 */

public interface ImageLoadingListener {
    void onLoadingStarted(String var1, View var2);

    void onLoadingFailed(String var1, View var2, FailReason var3);

    void onLoadingComplete(String var1, View var2, Bitmap var3);

    void onLoadingCancelled(String var1, View var2);

}
