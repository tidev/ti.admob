/**
 * Copyright (c) 2011 by Studio Classics. All Rights Reserved.
 * Copyright (c) 2017-present by Axway Appcelerator. All Rights Reserved.
 * Author: Brian Kurzius, Axway Appcelerator
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */

package ti.admob;

import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.kroll.common.Log;
import org.appcelerator.titanium.TiApplication;
import org.appcelerator.kroll.KrollFunction;
import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollObject;
import org.appcelerator.titanium.TiApplication;

import java.net.URL;
import java.net.MalformedURLException;
import android.net.Uri;
import android.content.Context;

import com.google.android.gms.common.GooglePlayServicesUtil;

import com.google.ads.consent.ConsentInformation;
import com.google.ads.consent.ConsentForm;
import com.google.ads.consent.ConsentFormListener;
import com.google.ads.consent.ConsentInfoUpdateListener;
import com.google.ads.consent.ConsentStatus;

@Kroll.module(name = "Admob", id = "ti.admob")
public class AdmobModule extends KrollModule {
	// Standard Debugging variables
	private static final String TAG = "AdmobModule";
	public static String MODULE_NAME = "AndroidAdMobModule";

	private ConsentForm form = null;

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

	// Events for receiving ads
	@Kroll.constant public static final String AD_RECEIVED = "ad_received";
	@Kroll.constant public static final String AD_NOT_RECEIVED = "ad_not_received";

	// Response from "isGooglePlayServicesAvailable()""
	@Kroll.constant public static final int SUCCESS = 0;
	@Kroll.constant public static final int SERVICE_MISSING = 1;
	@Kroll.constant public static final int SERVICE_VERSION_UPDATE_REQUIRED = 2;
	@Kroll.constant public static final int SERVICE_DISABLED = 3;
	@Kroll.constant public static final int SERVICE_INVALID = 9;

	// Response from "consentStatus"
	@Kroll.constant public static final int CONSENT_STATUS_UNKNOWN = 0;
	@Kroll.constant public static final int CONSENT_STATUS_NON_PERSONALIZED = 1;
	@Kroll.constant public static final int CONSENT_STATUS_PERSONALIZED = 2;

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
	public void requestConsentInfoUpdateForPublisherIdentifiers(KrollDict args) {
		Log.e(TAG, "Not implemented due to missing docs so far.");

        String[] publisherIdentifiers = (String[]) args.get("publisherIdentifiers");
		final KrollFunction callback = (KrollFunction) args.get("callback");
		Context appContext = TiApplication.getInstance().getApplicationContext();

		ConsentInformation consentInformation = ConsentInformation.getInstance(appContext);
        consentInformation.requestConsentInfoUpdate(publisherIdentifiers, new ConsentInfoUpdateListener() {
            @Override
            public void onConsentInfoUpdated(ConsentStatus consentStatus) {
				Log.d(TAG, "consent info updated");

				KrollObject krollObject = getKrollObject();
				KrollDict event = new KrollDict();
	
				event.put("consentStatus", consentStatus.ordinal());
	
				callback.callAsync(krollObject, event);
			}

            @Override
            public void onFailedToUpdateConsentInfo(String errorReason) {
				Log.d(TAG, "consent info failed");

				KrollObject krollObject = getKrollObject();
				KrollDict event = new KrollDict();
	
				event.put("error", errorReason);
	
				callback.callAsync(krollObject, event);
			}
        });
	}

	@Kroll.method
	public void showConsentForm(KrollDict args) {
		URL privacyUrl = null;
		Context appContext = TiApplication.getInstance().getApplicationContext();

		final KrollFunction callback = (KrollFunction) args.get("callback");
		Boolean shouldOfferPersonalizedAds = args.optBoolean("shouldOfferPersonalizedAds", true);
		Boolean shouldOfferNonPersonalizedAds = args.optBoolean("shouldOfferNonPersonalizedAds", true);
		Boolean shouldOfferAdFree = args.optBoolean("shouldOfferAdFree", false);

		try {
			privacyUrl = new URL(args.getString("privacyURL"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
			KrollObject krollObject = getKrollObject();
			KrollDict event = new KrollDict();

			event.put("error", e.getMessage());

			callback.callAsync(krollObject, event);
			return;
		}

		ConsentForm.Builder formBuilder = new ConsentForm.Builder(appContext, privacyUrl).withListener(new ConsentFormListener() {
			@Override
			public void onConsentFormLoaded() {
				Log.d(TAG, "consent form loaded");
				if (form != null) {
					form.show();
				}
			}

			@Override
			public void onConsentFormOpened() {
				Log.d(TAG, "consent form opened");
			}

			@Override
			public void onConsentFormClosed(ConsentStatus consentStatus, Boolean userPrefersAdFree) {
				Log.d(TAG, "consent form closed");

				KrollObject krollObject = getKrollObject();
				KrollDict event = new KrollDict();
	
				event.put("userPrefersAdFree", userPrefersAdFree);
				event.put("consentStatus", consentStatus.ordinal());
				event.put("error", null);
	
				callback.callAsync(krollObject, event);
			}

			@Override
			public void onConsentFormError(String errorDescription) {
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
		form.load();
	}

	@Kroll.getProperty
	@Kroll.method
	public int getConsentStatus() {
		Context appContext = TiApplication.getInstance().getApplicationContext();
		return ConsentInformation.getInstance(appContext).getConsentStatus().ordinal();
	}

	@Kroll.method
	public void reset() {
		Context appContext = TiApplication.getInstance().getApplicationContext();
		ConsentInformation.getInstance(appContext).reset();
	}
}
