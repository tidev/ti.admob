/**
 * Copyright (c) 2011 by Studio Classics. All Rights Reserved.
 * Author: Brian Kurzius
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */

package ti.admob;

import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.kroll.common.Log;
import org.appcelerator.titanium.TiApplication;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.ads.InterstitialAd;

@Kroll.module(name = "Admob", id = "ti.admob")
public class AdmobModule extends KrollModule {
	// Standard Debugging variables
	private static final String TAG = "AdmobModule";
	public static String MODULE_NAME = "AndroidAdMobModule";
	
	@Kroll.constant
	public static final String AD_RECEIVED = "ad_received";

	@Kroll.constant
	public static final String AD_NOT_RECEIVED = "ad_not_received";

	@Kroll.constant
	public static final String AD_CLOSED = "ad_closed";

	public static Boolean TESTING = false;
	public static String PUBLISHER_ID;

	// *
	public static String PROPERTY_COLOR_BG = "adBackgroundColor";
	public static String PROPERTY_COLOR_BG_TOP = "backgroundTopColor";
	public static String PROPERTY_COLOR_BORDER = "borderColor";
	public static String PROPERTY_COLOR_TEXT = "textColor";
	public static String PROPERTY_COLOR_LINK = "linkColor";
	public static String PROPERTY_COLOR_URL = "urlColor";

	public static String PROPERTY_COLOR_TEXT_DEPRECATED = "primaryTextColor";
	public static String PROPERTY_COLOR_LINK_DEPRECATED = "secondaryTextColor";

	// */

	public AdmobModule() {
		super();
		Log.d(TAG, "adMob module instantiated");
	}

	// Response from isGooglePlayServicesAvailable()
	@Kroll.constant public static final int SUCCESS = 0;
	@Kroll.constant public static final int SERVICE_MISSING = 1;
	@Kroll.constant public static final int SERVICE_VERSION_UPDATE_REQUIRED = 2;
	@Kroll.constant public static final int SERVICE_DISABLED = 3;
	@Kroll.constant public static final int SERVICE_INVALID = 9;

	@Kroll.method
	public void showInterstitial(KrollDict args) {
		String adUnitId = (String) args.get("adUnitId");

		InterstitialAd interstitialAd = new InterstitialAd(TiApplication.getInstance().getCurrentActivity());
		interstitialAd.setAdUnitId(adUnitId);

		interstitialAd.setAdListener(new AdListener() {
			public void onAdLoaded() {
				Log.d(TAG, "onAdLoaded()");
				fireEvent(AD_RECEIVED, new KrollDict());
			}

			public void onAdFailedToLoad(int errorCode) {
				Log.d(TAG, "onAdFailedToLoad(): " + errorCode);
				fireEvent(AD_NOT_RECEIVED, new KrollDict());
			}

			public void onAdClosed(i) {
				Log.d(TAG, "onAdClosed()");
				fireEvent(AD_CLOSED, new KrollDict());
			}
		});

		TiApplication.getInstance().getCurrentActivity().runOnUiThread(new Runnable() {
			public void run() {
				final AdRequest.Builder adRequestBuilder = new AdRequest.Builder();
				if (TESTING) {
					adRequestBuilder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
				}
				interstitialAd.loadAd(adRequestBuilder.build());
			}
		});
	}

	@Kroll.method
	public int isGooglePlayServicesAvailable() {
		return GooglePlayServicesUtil.isGooglePlayServicesAvailable(TiApplication.getAppRootOrCurrentActivity());
	}

	// use this to set the publisher id
	// must be done before the call to instantiate the view
	@Kroll.method
	public void setPublisherId(String pubId) {
		Log.d(TAG, "setPublisherId(): " + pubId);
		PUBLISHER_ID = pubId;
	}

	@Kroll.method
	public void setTesting(Boolean testing) {
		Log.d(TAG, "setTesting(): " + testing);
		TESTING = testing;
	}

}
