/**
 * Copyright (c) 2011 by Studio Classics. All Rights Reserved.
 * Copyright (c) 2017-present by Axway Appcelerator. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */

package ti.admob;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.kroll.common.Log;

@Kroll.proxy(creatableInModule = AdmobModule.class)
public class InterstitialAdProxy extends KrollProxy
{

	private final String TAG = "InterstitialAd";
	private InterstitialAd mInterstitialAd;
	String adId = "";

	public InterstitialAdProxy()
	{
		//this.interstitialAd = new InterstitialAd(getActivity());
		//this.interstitialAd.setAdListener(new CommonAdListener(this, TAG));
	}

	@Override
	public void handleCreationDict(KrollDict dict)
	{
		super.handleCreationDict(dict);
		if (dict.containsKeyAndNotNull(AdmobModule.PROPERTY_AD_UNIT_ID)) {
			//this.interstitialAd.setAdUnitId(dict.getString(AdmobModule.PROPERTY_AD_UNIT_ID));
			adId = dict.getString(AdmobModule.PROPERTY_AD_UNIT_ID);
		}
	}

	// clang format off
	@Kroll.method
	@Kroll.setProperty
	public void setAdUnitId(String adUnitId)
	// clang format on
	{
		// Validate the parameter
		if (adUnitId != null && adUnitId instanceof String) {
			//
		}
	}

	// clang format off
	@Kroll.method
	@Kroll.getProperty
	public String getAdUnitId()
	// clang format on
	{
		return mInterstitialAd.getAdUnitId();
	}

	@Kroll.method
	public void load(@Kroll.argument(optional = true) KrollDict options)
	{
		AdRequest.Builder adRequestBuilder = AdmobModule.createRequestBuilderWithOptions(options);
		mInterstitialAd.load(getActivity(), adId, adRequestBuilder.build(), new InterstitialAdLoadCallback() {
			@Override
			public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
				super.onAdLoaded(interstitialAd);
				mInterstitialAd = interstitialAd;
			}

			@Override
			public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
				super.onAdFailedToLoad(loadAdError);
				mInterstitialAd  = null;
			}
		});
	}

	@Kroll.method
	public void show()
	{
		if (this.mInterstitialAd != null) {
			this.mInterstitialAd.show(getActivity());
		} else {
			Log.w(TAG, "Trying to show an ad that has not been loaded.");
		}
	}
}