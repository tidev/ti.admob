/**
 * Copyright (c) 2011 by Studio Classics. All Rights Reserved.
 * Copyright (c) 2017-present by Axway Appcelerator. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */

package ti.admob;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.LoadAdError;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.kroll.common.Log;

public class CommonAdListener extends AdListener {

    private final static String TAG = "AdEventListener";

    private final KrollProxy sourceProxy;
    private final String sourceTag;

    public CommonAdListener(KrollProxy sourceProxy, String tag) {
        this.sourceProxy = sourceProxy;
        this.sourceTag = tag;
    }

    @Override
    public void onAdLoaded() {
        Log.d(this.sourceTag, "onAdLoaded()");
        warnForDeprecatedConstant(AdmobModule.AD_RECEIVED, AdmobModule.EVENT_AD_LOAD);
        this.sourceProxy.fireEvent(AdmobModule.AD_RECEIVED, new KrollDict());
        this.sourceProxy.fireEvent(AdmobModule.EVENT_AD_LOAD, new KrollDict());
    }

    @Override
    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
        super.onAdFailedToLoad(loadAdError);
        Log.d(this.sourceTag, "onAdFailedToLoad(): " + loadAdError);
        KrollDict eventData = new KrollDict();
        eventData.put("errorCode", loadAdError);
        warnForDeprecatedConstant(AdmobModule.AD_NOT_RECEIVED, AdmobModule.EVENT_AD_FAIL);
        this.sourceProxy.fireEvent(AdmobModule.AD_NOT_RECEIVED, eventData);
        this.sourceProxy.fireEvent(AdmobModule.EVENT_AD_FAIL, eventData);
    }

    @Override
    public void onAdClosed() {
        super.onAdClosed();
        warnForDeprecatedConstant(AdmobModule.AD_CLOSED, AdmobModule.EVENT_AD_CLOSED);
        this.sourceProxy.fireEvent(AdmobModule.AD_CLOSED, new KrollDict());
        this.sourceProxy.fireEvent(AdmobModule.EVENT_AD_CLOSED, new KrollDict());
    }

    @Override
    public void onAdOpened() {
        super.onAdOpened();
        warnForDeprecatedConstant(AdmobModule.AD_OPENED, AdmobModule.EVENT_AD_OPENED);
        this.sourceProxy.fireEvent(AdmobModule.AD_OPENED, new KrollDict());
        this.sourceProxy.fireEvent(AdmobModule.EVENT_AD_OPENED, new KrollDict());
    }

    @Override
    public void onAdClicked() {
        super.onAdClicked();
        this.sourceProxy.fireEvent(AdmobModule.EVENT_AD_LEFT_APP, new KrollDict());
    }

    private void warnForDeprecatedConstant(String deprecatedConstant, String newConstant) {
        Log.w(TAG, "\"" + deprecatedConstant + "\" is deprecated. Use \"" + newConstant + "\" instead.");
    }
}
