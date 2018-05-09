/**
 * Copyright (c) 2011 by Studio Classics. All Rights Reserved.
 * Author: Brian Kurzius
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */

package ti.admob;

import android.os.AsyncTask;

import java.io.IOException;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollFunction;
import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.kroll.common.Log;
import org.appcelerator.titanium.TiApplication;

@Kroll.module(name = "Admob", id = "ti.admob")
public class AdmobModule extends KrollModule {
	// Standard Debugging variables
	private static final String TAG = "AdmobModule";
	public static String MODULE_NAME = "AndroidAdMobModule";

	@Kroll.constant
	public static final String AD_RECEIVED = "ad_received";
	@Kroll.constant
	public static final String AD_NOT_RECEIVED = "ad_not_received";

	private final String ANDROID_ADVERTISING_ID = "aaID";
	private final String IS_LIMIT_AD_TRACKING_ENABLED = "isLimitAdTrackingEnabled";

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

	private KrollFunction aaInfoCallback;

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

	@Kroll.method
	public void isLimitAdTrackingEnabled(KrollFunction callback) {
		if (callback != null) {
			this.aaInfoCallback = callback;
			new getAndroidAdvertisingIDInfo().execute(IS_LIMIT_AD_TRACKING_ENABLED);
		}
	}

	@Kroll.method
	public void getAndroidAdID(KrollFunction callback) {
		if (callback != null) {
			this.aaInfoCallback = callback;
			new getAndroidAdvertisingIDInfo().execute(ANDROID_ADVERTISING_ID);
		}
	}

	private void invokeAIDCleintInfoCallback(AdvertisingIdClient.Info aaClientIDInfo, String responseKey) {
		KrollDict callbackDictionary = new KrollDict();
		Object responseValue = null;
		switch (responseKey) {
			case ANDROID_ADVERTISING_ID:
				responseValue = aaClientIDInfo.getId();
				break;
			case IS_LIMIT_AD_TRACKING_ENABLED:
				responseValue = aaClientIDInfo.isLimitAdTrackingEnabled();
				break;
		}
		callbackDictionary.put(responseKey, responseValue);
		this.aaInfoCallback.callAsync(getKrollObject(), callbackDictionary);
	}

	private class getAndroidAdvertisingIDInfo extends AsyncTask<String, Integer, String> {

		private AdvertisingIdClient.Info aaClientIDInfo = null;

		@Override
		protected String doInBackground(String... responseKey) {
			try {
				aaClientIDInfo = AdvertisingIdClient.getAdvertisingIdInfo(TiApplication.getInstance().getApplicationContext());
				return responseKey[0];
			} catch (IOException | GooglePlayServicesNotAvailableException | GooglePlayServicesRepairableException e) {
				e.printStackTrace();
				return null;
			}
		}

		@Override
		protected void onPostExecute(String responseKey) {
			if (aaClientIDInfo != null) {
				invokeAIDCleintInfoCallback(aaClientIDInfo, responseKey);
			}
		}
	}

}
