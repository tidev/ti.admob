/**
 * Copyright (c) 2011 by Studio Classics. All Rights Reserved.
 * Copyright (c) 2017-present by Axway Appcelerator. All Rights Reserved.
 * Author: Brian Kurzius, Axway Appcelerator
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */
package ti.admob;

import java.io.IOException;
import java.util.Map;

import org.appcelerator.titanium.TiBlob;
import org.appcelerator.titanium.io.TiBaseFile;
import org.appcelerator.titanium.util.TiConvert;
import org.appcelerator.kroll.KrollDict;
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

public class View extends TiUIView
{
	private static final String TAG = "AdMobView";
	AdView adView;
	AdSize prop_adSize = AdSize.BANNER;
	String prop_adUnitId;
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
	Bundle extras;

	public View(final TiViewProxy proxy)
	{
		super(proxy);
	}

	private void createAdView()
	{
		Log.d(TAG, "createAdView()");

		adView = new AdView(proxy.getActivity());
		adView.setAdSize(prop_adSize);
		adView.setAdUnitId(prop_adUnitId);

		// set the listener
		adView.setAdListener(new AdListener() {
			public void onAdLoaded()
			{
				Log.d(TAG, "onAdLoaded()");
				proxy.fireEvent(AdmobModule.AD_RECEIVED, new KrollDict());
			}

			public void onAdFailedToLoad(int errorCode)
			{
				Log.d(TAG, "onAdFailedToLoad(): " + errorCode);
				proxy.fireEvent(AdmobModule.AD_NOT_RECEIVED, new KrollDict());
			}
		});
		adView.setPadding(prop_left, prop_top, prop_right, 0);
		// Add the AdView to your view hierarchy.
		// The view will have no size until the ad is loaded.
		setNativeView(adView);
		loadAd(prop_debugEnabled);
	}

	// load the adMob ad
	public void loadAd(final Boolean testing)
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

	@Override
	public void processProperties(KrollDict d)
	{
		super.processProperties(d);
		Log.d(TAG, "process properties");
		if (d.containsKey(AdmobModule.PROPERTY_AD_UNIT_ID)) {
			prop_adUnitId = d.getString(AdmobModule.PROPERTY_AD_UNIT_ID);
		}
		if (d.containsKey(AdmobModule.PROPERTY_AD_TYPE)) {
			int type = d.getInt(AdmobModule.PROPERTY_AD_TYPE);
			switch (type) {
				case AdmobModule.AD_TYPE_BANNER:
					prop_adSize = AdSize.BANNER;
					break;
				case AdmobModule.AD_TYPE_INTERSTITIAL:
					prop_adSize = AdSize.FLUID;
					break;
			}
		}
		if (d.containsKey(AdmobModule.PROPERTY_DEBUG_ENABLED)) {
			prop_debugEnabled = d.getBoolean(AdmobModule.PROPERTY_DEBUG_ENABLED);
		}
		if (d.containsKey(AdmobModule.PROPERTY_COLOR_BG)) {
			Log.d(TAG, "has PROPERTY_COLOR_BG: " + d.getString(AdmobModule.PROPERTY_COLOR_BG));
			prop_color_bg = convertColorProp(d.getString(AdmobModule.PROPERTY_COLOR_BG));
		}
		if (d.containsKey(AdmobModule.PROPERTY_COLOR_BG_TOP)) {
			Log.d(TAG, "has PROPERTY_COLOR_BG_TOP: " + d.getString(AdmobModule.PROPERTY_COLOR_BG_TOP));
			prop_color_bg_top = convertColorProp(d.getString(AdmobModule.PROPERTY_COLOR_BG_TOP));
		}
		if (d.containsKey(AdmobModule.PROPERTY_COLOR_BORDER)) {
			Log.d(TAG, "has PROPERTY_COLOR_BORDER: " + d.getString(AdmobModule.PROPERTY_COLOR_BORDER));
			prop_color_border = convertColorProp(d.getString(AdmobModule.PROPERTY_COLOR_BORDER));
		}
		if (d.containsKey(AdmobModule.PROPERTY_COLOR_TEXT)) {
			Log.d(TAG, "has PROPERTY_COLOR_TEXT: " + d.getString(AdmobModule.PROPERTY_COLOR_TEXT));
			prop_color_text = convertColorProp(d.getString(AdmobModule.PROPERTY_COLOR_TEXT));
		}
		if (d.containsKey(AdmobModule.PROPERTY_COLOR_LINK)) {
			Log.d(TAG, "has PROPERTY_COLOR_LINK: " + d.getString(AdmobModule.PROPERTY_COLOR_LINK));
			prop_color_link = convertColorProp(d.getString(AdmobModule.PROPERTY_COLOR_LINK));
		}
		if (d.containsKey(AdmobModule.PROPERTY_COLOR_URL)) {
			Log.d(TAG, "has PROPERTY_COLOR_URL: " + d.getString(AdmobModule.PROPERTY_COLOR_URL));
			prop_color_url = convertColorProp(d.getString(AdmobModule.PROPERTY_COLOR_URL));
		}
		// check for deprecated color values

		if (d.containsKey(AdmobModule.PROPERTY_COLOR_TEXT_DEPRECATED)) {
			Log.d(TAG,
				  "has PROPERTY_COLOR_TEXT_DEPRECATED: " + d.getString(AdmobModule.PROPERTY_COLOR_TEXT_DEPRECATED));
			prop_color_text = convertColorProp(d.getString(AdmobModule.PROPERTY_COLOR_TEXT_DEPRECATED));
		}
		if (d.containsKey(AdmobModule.PROPERTY_COLOR_LINK_DEPRECATED)) {
			Log.d(TAG,
				  "has PROPERTY_COLOR_LINK_DEPRECATED: " + d.getString(AdmobModule.PROPERTY_COLOR_LINK_DEPRECATED));
			prop_color_link = convertColorProp(d.getString(AdmobModule.PROPERTY_COLOR_LINK_DEPRECATED));
		}

		// now create the adView
		this.createAdView();
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
	public void requestAd(KrollDict parameters)
	{
		Log.d(TAG, "requestAd()");

		if (parameters != null) {
			// Pass additional extras if existing
			if (parameters.containsKeyAndNotNull("extras")) {
				extras = mapToBundle(parameters.getKrollDict("extras"));
			}
		}

		loadAd(prop_debugEnabled);
	}

	// pass true to requestAd(Boolean testing) -- this overrides how the module was set
	public void requestTestAd()
	{
		Log.d(TAG, "requestTestAd()");
		loadAd(true);
	}

	// helper methods

	// create the adRequest extra props
	// http://code.google.com/mobile/ads/docs/bestpractices.html#adcolors
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

	// modifies the color prop -- removes # and changes constants into hex values
	private String convertColorProp(String color)
	{
		color = color.replace("#", "");
		if (color.equals("white"))
			color = "FFFFFF";
		if (color.equals("red"))
			color = "FF0000";
		if (color.equals("blue"))
			color = "0000FF";
		if (color.equals("green"))
			color = "008000";
		if (color.equals("yellow"))
			color = "FFFF00";
		if (color.equals("black"))
			color = "000000";
		return color;
	}

	private Bundle mapToBundle(Map<String, Object> map)
	{
		if (map == null) {
			return new Bundle();
		}

		Bundle bundle = new Bundle(map.size());

		for (String key : map.keySet()) {
			Object val = map.get(key);
			if (val == null) {
				bundle.putString(key, null);
			} else if (val instanceof TiBlob) {
				bundle.putByteArray(key, ((TiBlob) val).getBytes());
			} else if (val instanceof TiBaseFile) {
				try {
					bundle.putByteArray(key, ((TiBaseFile) val).read().getBytes());
				} catch (IOException e) {
					Log.e(TAG, "Unable to put '" + key + "' value into bundle: " + e.getLocalizedMessage(), e);
				}
			} else {
				bundle.putString(key, TiConvert.toString(val));
			}
		}

		return bundle;
	}
}