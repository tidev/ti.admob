/**
 * Copyright (c) 2011 by Studio Classics. All Rights Reserved.
 * Copyright (c) 2017-present by Axway Appcelerator. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */

package ti.admob;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
//import com.google.android.gms.ads.mediation.admob.AdMobExtras;
import com.google.ads.mediation.admob.AdMobAdapter;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.kroll.common.Log;

import android.os.Bundle;

@Kroll.proxy(creatableInModule = AdmobModule.class)
public class RewardedVideoProxy extends KrollProxy implements RewardedVideoAdListener {

	private final static String EVENT_ON_AD_LOADED = "adloaded";
	private final static String EVENT_ON_AD_REWARDED = "adrewarded";
	private final static String EVENT_ON_AD_FAILED_TO_LOAD = "adfailedtoload";
	private final static String EVENT_ON_AD_LEFT_APPLICATION = "adleftapplication";
	private final static String EVENT_ON_AD_CLOSED = "adclosed";
	private final static String EVENT_ON_AD_OPENED = "adopened";
	private final static String EVENT_ON_VIDEO_STARTED = "videostarted";
	// The following event is not yet available in admob version 11.8
	public final static String EVENT_ON_VIDEO_COMPLETED = "videocompleted";

	private final static String TAG = "RewardedVideo";

	private final static String PROPERTY_TYPE = "type";
	private final static String PROPERTY_AMOUNT = "amount";

	private RewardedVideoAd rewardedVideoAd;

	public RewardedVideoProxy() {
		this.rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(getActivity());
		this.rewardedVideoAd.setRewardedVideoAdListener(this);
	}

	@Kroll.method
	public void loadAd(String adUnitId, @Kroll.argument(optional = true) KrollDict parameters) {
		AdRequest adRequest = new AdRequest.Builder().build();

		if (parameters != null) {
			// Pass additional extras if existing
			if (parameters.containsKeyAndNotNull("npa")) {
				Log.d(TAG, "extras{npa:1}");
	      // https://developers.google.com/admob/android/eu-consent#forward_consent_to_the_google_mobile_ads_sdk
				Bundle extras = new Bundle();
				extras.putString("npa", "1");
				adRequest = new AdRequest.Builder()
	        .addNetworkExtrasBundle(AdMobAdapter.class, extras)
	        .build();
			}else{
				Log.d(TAG, "extras{npa:0}");
			}
    }else{
			Log.d(TAG, "no extras parameters");
		}
		this.rewardedVideoAd.loadAd(adUnitId, adRequest);
	}

	@Kroll.method
	public void show() {
		if (this.rewardedVideoAd.isLoaded()) {
			this.rewardedVideoAd.show();
		} else {
			Log.w(TAG, "Trying to show a rewarded video ad that has not loaded.");
		}
	}

	@Override
	public void onRewardedVideoAdLoaded() {
		if (this.hasListeners(EVENT_ON_AD_LOADED)) {
			fireEvent(EVENT_ON_AD_LOADED, new KrollDict());
		}
	}

	@Override
	public void onRewardedVideoAdOpened() {
		if (this.hasListeners(EVENT_ON_AD_OPENED)) {
			fireEvent(EVENT_ON_AD_OPENED, new KrollDict());
		}
	}

	@Override
	public void onRewardedVideoStarted() {
		if (this.hasListeners(EVENT_ON_VIDEO_STARTED)) {
			fireEvent(EVENT_ON_VIDEO_STARTED, new KrollDict());
		}
	}

	@Override
	public void onRewardedVideoAdClosed() {
		if (this.hasListeners(EVENT_ON_AD_CLOSED)) {
			fireEvent(EVENT_ON_AD_CLOSED, new KrollDict());
		}
	}

	@Override
	public void onRewarded(RewardItem rewardItem) {
		if (this.hasListeners(EVENT_ON_AD_REWARDED)) {
			KrollDict rewardReceived = new KrollDict();
			rewardReceived.put(PROPERTY_TYPE, rewardItem.getType());
			rewardReceived.put(PROPERTY_AMOUNT, rewardItem.getAmount());
			fireEvent(EVENT_ON_AD_REWARDED, rewardReceived);
		}
	}

	@Override
	public void onRewardedVideoAdLeftApplication() {
		if (this.hasListeners(EVENT_ON_AD_LEFT_APPLICATION)) {
			fireEvent(EVENT_ON_AD_LEFT_APPLICATION, new KrollDict());
		}
	}

	@Override
	public void onRewardedVideoAdFailedToLoad(int i) {
		if (this.hasListeners(EVENT_ON_AD_FAILED_TO_LOAD)) {
			fireEvent(EVENT_ON_AD_FAILED_TO_LOAD, new KrollDict());
		}
	}
}
