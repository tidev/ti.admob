/**
 * Copyright (c) 2011 by Studio Classics. All Rights Reserved.
 * Copyright (c) 2017-present by Axway Appcelerator. All Rights Reserved.
 * Author: Brian Kurzius, Axway Appcelerator
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

import org.appcelerator.kroll.KrollFunction;
import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollObject;
import org.appcelerator.titanium.TiApplication;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.net.URL;
import java.net.MalformedURLException;

import android.net.Uri;
import android.content.Context;
import android.app.Activity;

import com.google.android.gms.common.GooglePlayServicesUtil;

import com.google.ads.consent.ConsentInformation;
import com.google.ads.consent.ConsentForm;
import com.google.ads.consent.ConsentFormListener;
import com.google.ads.consent.ConsentInfoUpdateListener;
import com.google.ads.consent.ConsentStatus;
import com.google.ads.consent.DebugGeography;
import com.google.ads.consent.AdProvider;

@Kroll.module(name = "Admob", id = "ti.admob")
public class AdmobModule extends KrollModule
{
	// Standard Debugging variables
	private static final String TAG = "AdmobModule";
	public static String MODULE_NAME = "AndroidAdMobModule";

	private final String ANDROID_ADVERTISING_ID = "androidAdId";
	private final String IS_LIMIT_AD_TRACKING_ENABLED = "isLimitAdTrackingEnabled";

	public static Boolean TESTING = false;

	private ConsentForm form = null;

	public static String PUBLISHER_ID;

	// constants
	@Kroll.constant public static final int AD_TYPE_BANNER = 0;
	@Kroll.constant public static final int AD_TYPE_INTERSTITIAL = 1;
	@Kroll.constant public static final String AD_RECEIVED = "ad_received";
	@Kroll.constant public static final String AD_NOT_RECEIVED = "ad_not_received";
	@Kroll.constant public static final int SUCCESS = 0;
	@Kroll.constant public static final int SERVICE_MISSING = 1;
	@Kroll.constant public static final int SERVICE_VERSION_UPDATE_REQUIRED = 2;
	@Kroll.constant public static final int SERVICE_DISABLED = 3;
	@Kroll.constant public static final int SERVICE_INVALID = 9;

	// properties
	public static String PROPERTY_AD_UNIT_ID = "adUnitId";
	public static String PROPERTY_AD_TYPE = "adType";
	public static String PROPERTY_DEBUG_ENABLED = "debugEnabled";
	public static String PROPERTY_TESTING = "testing";
	public static String PROPERTY_PUBLISHER_ID = "publisherId";
	public static String PROPERTY_COLOR_BG = "adBackgroundColor";
	public static String PROPERTY_COLOR_BG_TOP = "backgroundTopColor";
	public static String PROPERTY_COLOR_BORDER = "borderColor";
	public static String PROPERTY_COLOR_TEXT = "textColor";
	public static String PROPERTY_COLOR_LINK = "linkColor";
	public static String PROPERTY_COLOR_URL = "urlColor";
	public static String PROPERTY_COLOR_TEXT_DEPRECATED = "primaryTextColor";
	public static String PROPERTY_COLOR_LINK_DEPRECATED = "secondaryTextColor";

	public AdmobModule()
	{
		super();
		Log.d(TAG, "adMob module instantiated");
	}

	// Events for receiving ads
	@Kroll.constant
	public static final String AD_RECEIVED = "ad_received";
	@Kroll.constant
	public static final String AD_NOT_RECEIVED = "ad_not_received";

	// Response from "isGooglePlayServicesAvailable()""
	@Kroll.constant
	public static final int SUCCESS = 0;
	@Kroll.constant
	public static final int SERVICE_MISSING = 1;
	@Kroll.constant
	public static final int SERVICE_VERSION_UPDATE_REQUIRED = 2;
	@Kroll.constant
	public static final int SERVICE_DISABLED = 3;
	@Kroll.constant
	public static final int SERVICE_INVALID = 9;

	// Response from "consentStatus"
	@Kroll.constant
	public static final int CONSENT_STATUS_UNKNOWN = 0;
	@Kroll.constant
	public static final int CONSENT_STATUS_NON_PERSONALIZED = 1;
	@Kroll.constant
	public static final int CONSENT_STATUS_PERSONALIZED = 2;

	@Kroll.constant
	public static final int DEBUG_GEOGRAPHY_DISABLED = 0;
	@Kroll.constant
	public static final int DEBUG_GEOGRAPHY_EEA = 1;
	@Kroll.constant
	public static final int DEBUG_GEOGRAPHY_NOT_EEA = 3;

	@Kroll.method
	public int isGooglePlayServicesAvailable()
	{
		return GooglePlayServicesUtil.isGooglePlayServicesAvailable(TiApplication.getAppRootOrCurrentActivity());
	}

	// clang-format off
	@Kroll.setProperty
	@Kroll.method
	public void setPublisherId(String pubId)
	// clang-format on
	{
		Log.d(TAG, "setPublisherId(): " + pubId);
		PUBLISHER_ID = pubId;
	}

	@Kroll.method
	public void setTesting(boolean testing)
	{
		Log.d(TAG, "setTesting(): " + testing);
		TESTING = testing;
	}

	@Kroll.method
	public void isLimitAdTrackingEnabled(KrollFunction callback) {
		if (callback != null) {
			new getAndroidAdvertisingIDInfo(callback).execute(IS_LIMIT_AD_TRACKING_ENABLED);
		}
	}

	@Kroll.method
	public void getAndroidAdId(KrollFunction callback) {
		if (callback != null) {
			new getAndroidAdvertisingIDInfo(callback).execute(ANDROID_ADVERTISING_ID);
		}
	}

	private void invokeAIDClientInfoCallback(AdvertisingIdClient.Info aaClientIDInfo, String responseKey, KrollFunction callback) {
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
		callback.callAsync(getKrollObject(), callbackDictionary);
	}

	private class getAndroidAdvertisingIDInfo extends AsyncTask<String, Integer, String> {

		private AdvertisingIdClient.Info aaClientIDInfo = null;
		private KrollFunction aaInfoCallback;

		public getAndroidAdvertisingIDInfo(KrollFunction infoCallback) {
			this.aaInfoCallback = infoCallback;
		}

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
				invokeAIDClientInfoCallback(aaClientIDInfo, responseKey, aaInfoCallback);
			}
		}
	}

	@Kroll.method
	public void requestConsentInfoUpdateForPublisherIdentifiers(KrollDict args)
	{
		String[] publisherIdentifiers = args.getStringArray("publisherIdentifiers");
		final KrollFunction callback;
		{
			Object value = args.get("callback");
			if (value instanceof KrollFunction) {
				callback = (KrollFunction) value;
			} else {
				throw new RuntimeException("The 'callback' property is required and must be set to a function.");
			}
		}

		Context appContext = TiApplication.getInstance().getApplicationContext();

		ConsentInformation consentInformation = ConsentInformation.getInstance(appContext);
		consentInformation.requestConsentInfoUpdate(publisherIdentifiers, new ConsentInfoUpdateListener() {
			@Override
			public void onConsentInfoUpdated(ConsentStatus consentStatus)
			{
				Log.d(TAG, "consent info updated");

				KrollObject krollObject = getKrollObject();
				KrollDict event = new KrollDict();

				event.put("consentStatus", consentStatus.ordinal());

				callback.callAsync(krollObject, event);
			}

			@Override
			public void onFailedToUpdateConsentInfo(String errorReason)
			{
				Log.d(TAG, "consent info failed");

				KrollObject krollObject = getKrollObject();
				KrollDict event = new KrollDict();

				event.put("error", errorReason);

				callback.callAsync(krollObject, event);
			}
		});
	}

	@Kroll.method
	public void showConsentForm(KrollDict args)
	{
		URL privacyUrl = null;
		Context currentActivity = TiApplication.getInstance().getCurrentActivity();

		final KrollFunction callback;
		{
			Object value = args.get("callback");
			if (value instanceof KrollFunction) {
				callback = (KrollFunction) value;
			} else {
				throw new RuntimeException("The 'callback' property is required and must be set to a function.");
			}
		}

		boolean shouldOfferPersonalizedAds = args.optBoolean("shouldOfferPersonalizedAds", true);
		boolean shouldOfferNonPersonalizedAds = args.optBoolean("shouldOfferNonPersonalizedAds", true);
		boolean shouldOfferAdFree = args.optBoolean("shouldOfferAdFree", false);

		try {
			privacyUrl = new URL(args.getString("privacyURL"));
		} catch (Exception e) {
			KrollObject krollObject = getKrollObject();
			KrollDict event = new KrollDict();

			event.put("error", e.getMessage());

			callback.callAsync(krollObject, event);
			return;
		}

		ConsentForm.Builder formBuilder =
			new ConsentForm.Builder(currentActivity, privacyUrl).withListener(new ConsentFormListener() {
				@Override
				public void onConsentFormLoaded()
				{
					Log.d(TAG, "consent form loaded");
					if (form != null) {
						form.show();
					}
				}

				@Override
				public void onConsentFormOpened()
				{
					Log.d(TAG, "consent form opened");
				}

				@Override
				public void onConsentFormClosed(ConsentStatus consentStatus, Boolean userPrefersAdFree)
				{
					Log.d(TAG, "consent form closed");

					KrollObject krollObject = getKrollObject();
					KrollDict event = new KrollDict();

					event.put("userPrefersAdFree", userPrefersAdFree);
					event.put("consentStatus", consentStatus.ordinal());
					event.put("error", null);

					callback.callAsync(krollObject, event);
				}

				@Override
				public void onConsentFormError(String errorDescription)
				{
					Log.d(TAG, "consent form error: " + errorDescription);

					KrollObject krollObject = getKrollObject();
					KrollDict event = new KrollDict();

					event.put("error", errorDescription);

					callback.callAsync(krollObject, event);
				}
			});

		if (shouldOfferPersonalizedAds) {
			formBuilder = formBuilder.withPersonalizedAdsOption();
		}

		if (shouldOfferNonPersonalizedAds) {
			formBuilder = formBuilder.withNonPersonalizedAdsOption();
		}

		if (shouldOfferAdFree) {
			formBuilder = formBuilder.withAdFreeOption();
		}

		form = formBuilder.build();
		runOnMainThread(new Runnable() {
			@Override
			public void run()
			{
				form.load();
			}
		});
	}

	// clang-format off
	@Kroll.setProperty
	@Kroll.method
	public void setTagForUnderAgeOfConsent(boolean underAgeOfConsent)
	// clang-format on
	{
		Context appContext = TiApplication.getInstance().getApplicationContext();
		ConsentInformation.getInstance(appContext).setTagForUnderAgeOfConsent(underAgeOfConsent);
	}

	@Kroll.method
	public boolean isTaggedForUnderAgeOfConsent()
	{
		Context appContext = TiApplication.getInstance().getApplicationContext();
		return ConsentInformation.getInstance(appContext).isTaggedForUnderAgeOfConsent();
	}

	// clang-format off
	@Kroll.getProperty
	@Kroll.method
	public int getConsentStatus()
	// clang-format on
	{
		Context appContext = TiApplication.getInstance().getApplicationContext();
		return ConsentInformation.getInstance(appContext).getConsentStatus().ordinal();
	}

	// clang-format off
	@Kroll.getProperty
	@Kroll.method
	public int getDebugGeography()
	// clang-format on
	{
		Context appContext = TiApplication.getInstance().getApplicationContext();
		return ConsentInformation.getInstance(appContext).getDebugGeography().ordinal();
	}

	// clang-format off
	@Kroll.setProperty
	@Kroll.method
	public void setDebugGeography(int debugGeography)
	// clang-format on
	{
		Context appContext = TiApplication.getInstance().getApplicationContext();
		ConsentInformation.getInstance(appContext).setDebugGeography(DebugGeography.values()[debugGeography]);
	}

	@Kroll.method
	public void resetConsent()
	{
		Context appContext = TiApplication.getInstance().getApplicationContext();
		ConsentInformation.getInstance(appContext).reset();
	}

	// clang-format off
	@Kroll.getProperty
	@Kroll.method
	public KrollDict[] getAdProviders()
	// clang-format on
	{
		Context appContext = TiApplication.getInstance().getApplicationContext();
		List<AdProvider> adProviders = ConsentInformation.getInstance(appContext).getAdProviders();
		KrollDict[] result = new KrollDict[adProviders.size()];

		for (int i = 0; i < adProviders.size(); i++) {
			AdProvider adProvider = adProviders.get(i);
			KrollDict dict = new KrollDict();

			dict.put("identifier", adProvider.getId());
			dict.put("name", adProvider.getName());
			dict.put("privacyPolicyURL", adProvider.getPrivacyPolicyUrl());

			result[i] = dict;
		}

		return result;
	}

}
