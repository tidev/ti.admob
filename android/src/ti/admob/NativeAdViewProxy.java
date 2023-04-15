/**
 * Copyright (c) 2011 by Studio Classics. All Rights Reserved.
 * Copyright (c) 2017-present by Axway Appcelerator. All Rights Reserved.
 * Author: Brian Kurzius, Axway Appcelerator
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */
package ti.admob;

import android.app.Activity;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.titanium.TiBaseActivity;
import org.appcelerator.titanium.TiLifecycle.OnLifecycleEvent;
import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.titanium.view.TiUIView;

@Kroll.proxy(creatableInModule = AdmobModule.class)
public class NativeAdViewProxy extends TiViewProxy implements OnLifecycleEvent {
    protected static final String TAG = "BannerViewProxy";
    private NativeAdView adMob;
    private String prop_adUnitId;
    private int imageWidth = -1;

    public NativeAdViewProxy() {
        super();
    }

    @Override
    public TiUIView createView(Activity activity) {
        adMob = new NativeAdView(this);
        ((TiBaseActivity) activity).addOnLifecycleEventListener(this);
        return adMob;
    }

    @Override
    public void handleCreationDict(KrollDict options) {
        super.handleCreationDict(options);
        if (options.containsKeyAndNotNull("adUnitId")) {
            prop_adUnitId = options.getString("adUnitId");
        }
        if (options.containsKeyAndNotNull("imageWidth")) {
            imageWidth = options.getInt("imageWidth");
        }

    }

    @Kroll.method
    public void loadAd(){
        adMob.loadAd();
    }

    @Override
    public void onDestroy(Activity activity) {

    }

    @Override
    public void onPause(Activity activity) {

    }

    @Override
    public void onResume(Activity activity) {

    }

    @Override
    public void onStart(Activity activity) {
    }

    @Override
    public void onStop(Activity activity) {
    }
}