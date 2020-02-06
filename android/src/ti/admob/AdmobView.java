/**
 * Copyright (c) 2011 by Studio Classics. All Rights Reserved.
 * Copyright (c) 2017-present by Axway Appcelerator. All Rights Reserved.
 * Author: Brian Kurzius, Axway Appcelerator
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */
package ti.admob;

import android.os.Bundle;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.mediation.admob.AdMobExtras;
import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.common.Log;
import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.titanium.view.TiUIView;

public class AdmobView extends TiUIView
{
	private static final String TAG = "AdMobView";
	private AdView adView;

	AdmobSizeProxy prop_adSize = AdmobSizeEnum.BANNER.getAdmobSizeProxy();
	String prop_adUnitId;
	// Put these in extras bundle of laod
	Boolean prop_debugEnabled = false;
	int prop_top;
	int prop_left;
	int prop_right;
	String prop_color_bg;
	String prop_color_bg_top;
	String prop_color_border;
	String prop_color_text;
	String prop_color_link;
	String prop_color_url;
	// -------------------------------------
	Bundle extras;

	public AdmobView(final TiViewProxy proxy)
	{
		super(proxy);
	}

	private void createAdView()
	{
		Log.d(TAG, "createAdView()");

		adView = new AdView(proxy.getActivity());

		adView.setAdUnitId(prop_adUnitId);
		adView.setAdSize(prop_adSize.getAdSize());

		// set the listener
		adView.setAdListener(new CommonAdListener(proxy, TAG));
		adView.setPadding(prop_left, prop_top, prop_right, 0);
		// Add the AdView to your view hierarchy.
		// The view will have no size until the ad is loaded.
		setNativeView(adView);
		loadAd(prop_debugEnabled, null);
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
				Bundle bundle = createAdRequestProperties();
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
	public void processProperties(KrollDict d)
	{
		super.processProperties(d);
		if (d.containsKey(AdmobModule.PROPERTY_AD_UNIT_ID)) {
			prop_adUnitId = d.getString(AdmobModule.PROPERTY_AD_UNIT_ID);
		}
		if (d.containsKey(AdmobModule.PROPERTY_AD_SIZE)) {
			//KrollDict prop = d.getKrollDict(AdmobModule.PROPERTY_AD_SIZE);
			prop_adSize = AdmobSizeEnum.fromModuleConst(d.getInt(AdmobModule.PROPERTY_AD_SIZE)).getAdmobSizeProxy();
		}
		if (d.containsKey(AdmobModule.PROPERTY_DEBUG_ENABLED)) {
			warnForDeprecatedProperty(AdmobModule.PROPERTY_DEBUG_ENABLED);
			prop_debugEnabled = d.getBoolean(AdmobModule.PROPERTY_DEBUG_ENABLED);
		}
		if (d.containsKey(AdmobModule.PROPERTY_COLOR_BG)) {
			warnForDeprecatedProperty(AdmobModule.PROPERTY_COLOR_BG);
			Log.d(TAG, "has PROPERTY_COLOR_BG: " + d.getString(AdmobModule.PROPERTY_COLOR_BG));
			prop_color_bg = AdmobModule.convertColorProp(d.getString(AdmobModule.PROPERTY_COLOR_BG));
		}
		if (d.containsKey(AdmobModule.PROPERTY_COLOR_BG_TOP)) {
			warnForDeprecatedProperty(AdmobModule.PROPERTY_COLOR_BG_TOP);
			Log.d(TAG, "has PROPERTY_COLOR_BG_TOP: " + d.getString(AdmobModule.PROPERTY_COLOR_BG_TOP));
			prop_color_bg_top = AdmobModule.convertColorProp(d.getString(AdmobModule.PROPERTY_COLOR_BG_TOP));
		}
		if (d.containsKey(AdmobModule.PROPERTY_COLOR_BORDER)) {
			warnForDeprecatedProperty(AdmobModule.PROPERTY_COLOR_BORDER);
			Log.d(TAG, "has PROPERTY_COLOR_BORDER: " + d.getString(AdmobModule.PROPERTY_COLOR_BORDER));
			prop_color_border = AdmobModule.convertColorProp(d.getString(AdmobModule.PROPERTY_COLOR_BORDER));
		}
		if (d.containsKey(AdmobModule.PROPERTY_COLOR_TEXT)) {
			warnForDeprecatedProperty(AdmobModule.PROPERTY_COLOR_TEXT);
			Log.d(TAG, "has PROPERTY_COLOR_TEXT: " + d.getString(AdmobModule.PROPERTY_COLOR_TEXT));
			prop_color_text = AdmobModule.convertColorProp(d.getString(AdmobModule.PROPERTY_COLOR_TEXT));
		}
		if (d.containsKey(AdmobModule.PROPERTY_COLOR_LINK)) {
			warnForDeprecatedProperty(AdmobModule.PROPERTY_COLOR_LINK);
			Log.d(TAG, "has PROPERTY_COLOR_LINK: " + d.getString(AdmobModule.PROPERTY_COLOR_LINK));
			prop_color_link = AdmobModule.convertColorProp(d.getString(AdmobModule.PROPERTY_COLOR_LINK));
		}
		if (d.containsKey(AdmobModule.PROPERTY_COLOR_URL)) {
			warnForDeprecatedProperty(AdmobModule.PROPERTY_COLOR_URL);
			Log.d(TAG, "has PROPERTY_COLOR_URL: " + d.getString(AdmobModule.PROPERTY_COLOR_URL));
			prop_color_url = AdmobModule.convertColorProp(d.getString(AdmobModule.PROPERTY_COLOR_URL));
		}

		// now create the adView
		this.createAdView();
	}

	private void warnForDeprecatedProperty(String property)
	{
		Log.w(TAG, "You are using " + property
					   + " which is deprecated. Instead use the <extras> parameter in load() method.");
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

	// helper methods

	// http://code.google.com/mobile/ads/docs/bestpractices.html#adcolors
	// Deprecated in 5.0.0. Should be removed in the next major version.
	// Use createAdRequestExtrasBundleFromDictionary() instead.
	private Bundle createAdRequestProperties()
	{
		Bundle bundle = new Bundle();
		if (prop_color_bg != null) {
			Log.d(TAG, "color_bg: " + prop_color_bg);
			bundle.putString("color_bg", prop_color_bg);
		}
		if (prop_color_bg_top != null)
			bundle.putString("color_bg_top", prop_color_bg_top);
		if (prop_color_border != null)
			bundle.putString("color_border", prop_color_border);
		if (prop_color_text != null)
			bundle.putString("color_text", prop_color_text);
		if (prop_color_link != null)
			bundle.putString("color_link", prop_color_link);
		if (prop_color_url != null)
			bundle.putString("color_url", prop_color_url);
		if (extras != null)
			bundle.putAll(extras);

		return bundle;
	}
}