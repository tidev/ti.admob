/**
 * Copyright (c) 2011 by Studio Classics. All Rights Reserved.
 * Copyright (c) 2017-present by Axway Appcelerator. All Rights Reserved.
 * Author: Brian Kurzius, Axway Appcelerator
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */
package ti.admob;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.kroll.common.Log;
import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.titanium.view.TiUIView;

import android.os.Bundle;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.mediation.admob.AdMobExtras;
import com.google.ads.mediation.admob.AdMobAdapter;

public class AdmobView extends TiUIView
{
	private static final String TAG = "AdMobView";
	private final AdView adView;
	private boolean prop_debugEnabled;

	Bundle extras;

	public AdmobView(final TiViewProxy proxy)
	{
		super(proxy);
		adView = new AdView(proxy.getActivity());
		// Add the AdView to your view hierarchy.
		// The view will have no size until the ad is loaded.
		setNativeView(adView);
	}

	public AdView getAdView() {
		return this.adView;
	}

	public void setDebugEnabled(boolean value) {
		this.prop_debugEnabled = value;
	}

	//Deprecated in 5.0.0. Should be remove in 6.0.0
	private void loadAd(final Boolean testing, Bundle extrasBundle)
	{
		proxy.getActivity().runOnUiThread(new Runnable() {
			public void run()
			{
				final AdRequest.Builder adRequestBuilder = new AdRequest.Builder();
				Log.d(TAG, "requestAd(Boolean testing) -- testing:" + testing);
				if (testing) {
					adRequestBuilder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
				}
				Bundle bundle = ((BannerViewProxy)proxy).createAdRequestProperties();
				if (bundle.size() > 0) {
					Log.d(TAG, "extras.size() > 0 -- set ad properties");
					adRequestBuilder.addNetworkExtras(new AdMobExtras(bundle));
				}
				adView.loadAd(adRequestBuilder.build());
			}
		});
	}

	public void nativeLoadAd(final KrollDict options)
	{
		proxy.getActivity().runOnUiThread(new Runnable() {
			public void run()
			{
				final AdRequest.Builder adRequestBuilder = AdmobModule.createRequestBuilderWithOptions(options);
				adView.loadAd(adRequestBuilder.build());
			}
		});
	}

	@Override
	public void propertyChanged(String key, Object oldValue, Object newValue, KrollProxy proxy)
	{
		super.propertyChanged(key, oldValue, newValue, proxy);
	}

	public void pause()
	{
		Log.d(TAG, "pause");
		adView.pause();
	}

	public void resume()
	{
		Log.d(TAG, "resume");
		adView.resume();
	}

	public void destroy()
	{
		Log.d(TAG, "destroy");
		adView.destroy();
	}

	// pass the method the TESTING flag
	// Deprecated in 5.0.0. Should be removed in 6.0.0
	// Use nativeLoadAd() instead.
	public void requestAd(KrollDict parameters)
	{
		Log.d(TAG, "requestAd()");

		if (parameters != null) {
			// Pass additional extras if existing
			if (parameters.containsKeyAndNotNull("extras")) {
				extras = AdmobModule.mapToBundle(parameters.getKrollDict("extras"));
			}
		}

		loadAd(prop_debugEnabled, null);
	}

	// pass true to requestAd(Boolean testing) -- this overrides how the module was set
	// Deprecated in 5.0.0. Should be removed in 6.0.0.
	// Use the test parameter for nativeLoadAd() options instead.
	public void requestTestAd()
	{
		Log.d(TAG, "requestTestAd()");
		loadAd(true, null);
	}

}
