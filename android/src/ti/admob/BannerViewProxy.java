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

import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.kroll.common.Log;
import org.appcelerator.titanium.TiBaseActivity;
import org.appcelerator.titanium.TiLifecycle.OnLifecycleEvent;
import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.titanium.view.TiUIView;

import android.app.Activity;

import android.os.Bundle;

@Kroll.proxy(creatableInModule = AdmobModule.class)
public class BannerViewProxy extends TiViewProxy implements OnLifecycleEvent
{
	AdmobSizeProxy prop_adSize = AdmobSizeEnum.BANNER.getAdmobSizeProxy();
	String prop_adUnitId;
	// Put these in extras bundle of laod
	Bundle extras;
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

	private AdmobView adMob;
	protected static final String TAG = "BannerViewProxy";

	public BannerViewProxy()
	{
		super();
		this.adMob = new AdmobView(this);
	}

	@Override
	protected KrollDict getLangConversionTable()
	{
		KrollDict table = new KrollDict();
		table.put("title", "titleid");
		return table;
	}

	@Override
	public TiUIView createView(Activity activity)
	{
		((TiBaseActivity) activity).addOnLifecycleEventListener(this);
		return this.adMob;
	}

	@Kroll.method
	public void requestAd(@Kroll.argument(optional = true) KrollDict parameters)
	{
		this.adMob.requestAd(parameters);
		Log.w(TAG, "requestAd() has been deprecated. Use load() instead.");
	}

	@Kroll.method
	public void requestTestAd()
	{
		this.adMob.requestTestAd();
		Log.w(TAG, "requestAd() has been deprecated. Use load() with 'debugIdentifiers' property instead.");
	}

	@Kroll.method
	public void load(@Kroll.argument(optional = true) KrollDict options)
	{
		// Load ad with options...
		this.adMob.nativeLoadAd(options);
	}

	@Override
	public void handleCreationDict(KrollDict properties)
	{
		super.handleCreationDict(properties);
		if (properties.containsKey(AdmobModule.PROPERTY_AD_UNIT_ID)) {
			prop_adUnitId = properties.getString(AdmobModule.PROPERTY_AD_UNIT_ID);
		}
		if(properties.containsKey(AdmobModule.PROPERTY_AD_SIZE)) {
			prop_adSize = AdmobSizeEnum.fromModuleConst(properties.getInt(AdmobModule.PROPERTY_AD_SIZE)).getAdmobSizeProxy();
		}
		if (properties.containsKey(AdmobModule.PROPERTY_DEBUG_ENABLED)) {
			warnForDeprecatedProperty(AdmobModule.PROPERTY_DEBUG_ENABLED);
			prop_debugEnabled = properties.getBoolean(AdmobModule.PROPERTY_DEBUG_ENABLED);
			this.adMob.setDebugEnabled(prop_debugEnabled);
		}
		if (properties.containsKey(AdmobModule.PROPERTY_COLOR_BG)) {
			warnForDeprecatedProperty(AdmobModule.PROPERTY_COLOR_BG);
			Log.d(TAG, "has PROPERTY_COLOR_BG: " + properties.getString(AdmobModule.PROPERTY_COLOR_BG));
			prop_color_bg = AdmobModule.convertColorProp(properties.getString(AdmobModule.PROPERTY_COLOR_BG));
		}
		if (properties.containsKey(AdmobModule.PROPERTY_COLOR_BG_TOP)) {
			warnForDeprecatedProperty(AdmobModule.PROPERTY_COLOR_BG_TOP);
			Log.d(TAG, "has PROPERTY_COLOR_BG_TOP: " + properties.getString(AdmobModule.PROPERTY_COLOR_BG_TOP));
			prop_color_bg_top = AdmobModule.convertColorProp(properties.getString(AdmobModule.PROPERTY_COLOR_BG_TOP));
		}
		if (properties.containsKey(AdmobModule.PROPERTY_COLOR_BORDER)) {
			warnForDeprecatedProperty(AdmobModule.PROPERTY_COLOR_BORDER);
			Log.d(TAG, "has PROPERTY_COLOR_BORDER: " + properties.getString(AdmobModule.PROPERTY_COLOR_BORDER));
			prop_color_border = AdmobModule.convertColorProp(properties.getString(AdmobModule.PROPERTY_COLOR_BORDER));
		}
		if (properties.containsKey(AdmobModule.PROPERTY_COLOR_TEXT)) {
			warnForDeprecatedProperty(AdmobModule.PROPERTY_COLOR_TEXT);
			Log.d(TAG, "has PROPERTY_COLOR_TEXT: " + properties.getString(AdmobModule.PROPERTY_COLOR_TEXT));
			prop_color_text = AdmobModule.convertColorProp(properties.getString(AdmobModule.PROPERTY_COLOR_TEXT));
		}
		if (properties.containsKey(AdmobModule.PROPERTY_COLOR_LINK)) {
			warnForDeprecatedProperty(AdmobModule.PROPERTY_COLOR_LINK);
			Log.d(TAG, "has PROPERTY_COLOR_LINK: " + properties.getString(AdmobModule.PROPERTY_COLOR_LINK));
			prop_color_link = AdmobModule.convertColorProp(properties.getString(AdmobModule.PROPERTY_COLOR_LINK));
		}
		if (properties.containsKey(AdmobModule.PROPERTY_COLOR_URL)) {
			warnForDeprecatedProperty(AdmobModule.PROPERTY_COLOR_URL);
			Log.d(TAG, "has PROPERTY_COLOR_URL: " + properties.getString(AdmobModule.PROPERTY_COLOR_URL));
			prop_color_url = AdmobModule.convertColorProp(properties.getString(AdmobModule.PROPERTY_COLOR_URL));
		}
		createAdView();
	}

	private void createAdView()
	{
		Log.d(TAG, "createAdView()");

		this.adMob.getAdView().setAdUnitId(prop_adUnitId);
		this.adMob.getAdView().setAdSize(prop_adSize.getAdSize());

		// set the listener
		this.adMob.getAdView().setAdListener(new CommonAdListener(this, TAG));
		this.adMob.getAdView().setPadding(prop_left, prop_top, prop_right, 0);
	}

	// http://code.google.com/mobile/ads/docs/bestpractices.html#adcolors
	// Deprecated in 5.0.0. Should be removed in the next major version.
	// Use createAdRequestExtrasBundleFromDictionary() instead.
	public Bundle createAdRequestProperties()
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

	private void warnForDeprecatedProperty(String property) {
		Log.w(TAG, "You are using " + property + " which is deprecated. Instead use the <extras> parameter in load() method.");
	}

	@Override
	public void onDestroy(Activity activity)
	{
		this.adMob.destroy();
	}

	@Override
	public void onPause(Activity activity)
	{
		this.adMob.pause();
	}

	@Override
	public void onResume(Activity activity)
	{
		this.adMob.resume();
	}

	@Override
	public void onStart(Activity activity)
	{
	}

	@Override
	public void onStop(Activity activity)
	{
	}
}
