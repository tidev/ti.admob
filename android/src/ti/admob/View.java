/**
 * Copyright (c) 2011 by Studio Classics. All Rights Reserved.
 * Author: Brian Kurzius
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */
package ti.admob;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.kroll.common.Log;
import org.appcelerator.titanium.view.TiUIView;

import android.app.Activity;
import java.util.HashMap;
import java.util.Map;

import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public class View extends TiUIView implements AdListener {
	private static final String LCAT = "AdMobView";
	AdView adView;
	int prop_top;
	int prop_left;
	int prop_right;
	String prop_color_bg;
	String prop_color_bg_top;
	String prop_color_border;
	String prop_color_text;
	String prop_color_link;
	String prop_color_url;

	public View(final TiViewProxy proxy) {
		super(proxy);
		Log.d(LCAT, "Creating an adMob ad view");
		// get the publisher id that was set in the module
		Log.d(LCAT, "AdmobModule.PUBLISHER_ID: " + AdmobModule.PUBLISHER_ID);
	}

	private void createAdView() {
		Log.d(LCAT, "createAdView()");
		// create the adView
		adView = new AdView(proxy.getActivity(), AdSize.BANNER, AdmobModule.PUBLISHER_ID);
		loadAd(AdmobModule.TESTING);
		// set the listener
		adView.setAdListener(this);
		adView.setPadding(prop_left, prop_top, prop_right, 0);
		// Add the AdView to your view hierarchy.
		// The view will have no size until the ad is loaded.
		setNativeView(adView);

	}

	// load the adMob ad
	public void loadAd(Boolean testing) {
		AdRequest adRequest = new AdRequest();
		Log.d(LCAT, "requestAd(Boolean testing) -- testing:" + testing);
		adRequest.setTesting(testing);
		Map<String, Object> extras = createAdRequestProperties();
		if (extras.size() > 0) {
			Log.d(LCAT, "extras.size() > 0 -- set ad properties");
			adRequest.setExtras(extras);
		}
		adView.loadAd(adRequest);
	}

	@Override
	public void processProperties(KrollDict d) {
		super.processProperties(d);
		Log.d(LCAT, "process properties");
		if (d.containsKey("publisherId")) {
			Log.d(LCAT, "has publisherId: " + d.getString("publisherId"));
			AdmobModule.PUBLISHER_ID = d.getString("publisherId");
		}
		if (d.containsKey("testing")) {
			Log.d(LCAT, "has testing param: " + d.getBoolean("testing"));
			AdmobModule.TESTING = d.getBoolean("testing");
		}
		if (d.containsKey(AdmobModule.PROPERTY_COLOR_BG)) {
			Log.d(LCAT, "has PROPERTY_COLOR_BG: " + d.getString(AdmobModule.PROPERTY_COLOR_BG));
			prop_color_bg = convertColorProp(d.getString(AdmobModule.PROPERTY_COLOR_BG));
		}
		if (d.containsKey(AdmobModule.PROPERTY_COLOR_BG_TOP)) {
			Log.d(LCAT, "has PROPERTY_COLOR_BG_TOP: " + d.getString(AdmobModule.PROPERTY_COLOR_BG_TOP));
			prop_color_bg_top = convertColorProp(d.getString(AdmobModule.PROPERTY_COLOR_BG_TOP));
		}
		if (d.containsKey(AdmobModule.PROPERTY_COLOR_BORDER)) {
			Log.d(LCAT, "has PROPERTY_COLOR_BORDER: " + d.getString(AdmobModule.PROPERTY_COLOR_BORDER));
			prop_color_border = convertColorProp(d.getString(AdmobModule.PROPERTY_COLOR_BORDER));
		}
		if (d.containsKey(AdmobModule.PROPERTY_COLOR_TEXT)) {
			Log.d(LCAT, "has PROPERTY_COLOR_TEXT: " + d.getString(AdmobModule.PROPERTY_COLOR_TEXT));
			prop_color_text = convertColorProp(d.getString(AdmobModule.PROPERTY_COLOR_TEXT));
		}
		if (d.containsKey(AdmobModule.PROPERTY_COLOR_LINK)) {
			Log.d(LCAT, "has PROPERTY_COLOR_LINK: " + d.getString(AdmobModule.PROPERTY_COLOR_LINK));
			prop_color_link = convertColorProp(d.getString(AdmobModule.PROPERTY_COLOR_LINK));
		}
		if (d.containsKey(AdmobModule.PROPERTY_COLOR_URL)) {
			Log.d(LCAT, "has PROPERTY_COLOR_URL: " + d.getString(AdmobModule.PROPERTY_COLOR_URL));
			prop_color_url = convertColorProp(d.getString(AdmobModule.PROPERTY_COLOR_URL));
		}
		// check for deprecated color values

		if (d.containsKey(AdmobModule.PROPERTY_COLOR_TEXT_DEPRECATED)) {
			Log.d(LCAT, "has PROPERTY_COLOR_TEXT_DEPRECATED: " + d.getString(AdmobModule.PROPERTY_COLOR_TEXT_DEPRECATED));
			prop_color_text = convertColorProp(d.getString(AdmobModule.PROPERTY_COLOR_TEXT_DEPRECATED));
		}
		if (d.containsKey(AdmobModule.PROPERTY_COLOR_LINK_DEPRECATED)) {
			Log.d(LCAT, "has PROPERTY_COLOR_LINK_DEPRECATED: " + d.getString(AdmobModule.PROPERTY_COLOR_LINK_DEPRECATED));
			prop_color_link = convertColorProp(d.getString(AdmobModule.PROPERTY_COLOR_LINK_DEPRECATED));
		}

		// now create the adView
		this.createAdView();
	}

	// destroy the ad when done
	public void destroy() {
		adView.destroy();
	}

	// pass the method the TESTING flag
	public void requestAd() {
		Log.d(LCAT, "requestAd()");
		// pass the module TESTING flag
		loadAd(AdmobModule.TESTING);
	}

	// pass true to requestAd(Boolean testing) -- this overrides how the module was set
	public void requestTestAd() {
		Log.d(LCAT, "requestTestAd()");
		loadAd(true);
	}

	// required methods for the AdListener interface
	public void onReceiveAd(Ad ad) {
		Log.d(LCAT, "onReceiveAd()");
		proxy.fireEvent(AdmobModule.AD_RECEIVED, new KrollDict());
	}

	public void onFailedToReceiveAd(Ad ad, AdRequest.ErrorCode e) {
		Log.d(LCAT, "onFailedToReceiveAd(): " + e);
		proxy.fireEvent(AdmobModule.AD_NOT_RECEIVED, new KrollDict());
	}

	// helper methods

	// create the adRequest extra props
	// http://code.google.com/mobile/ads/docs/bestpractices.html#adcolors
	private Map<String, Object> createAdRequestProperties() {
		Map<String, Object> extras = new HashMap<String, Object>();
		if (prop_color_bg != null) {
			Log.d(LCAT, "color_bg: " + prop_color_bg);
			extras.put("color_bg", prop_color_bg);

		}
		if (prop_color_bg_top != null)
			extras.put("color_bg_top", prop_color_bg_top);
		if (prop_color_border != null)
			extras.put("color_border", prop_color_border);
		if (prop_color_text != null)
			extras.put("color_text", prop_color_text);
		if (prop_color_link != null)
			extras.put("color_link", prop_color_link);
		if (prop_color_url != null)
			extras.put("color_url", prop_color_url);
		return extras;
	}

	// modifies the color prop -- removes # and changes constants into hex values
	private String convertColorProp(String color) {
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

	// not used
	public void onLeaveApplication(Ad ad) {
		Log.d(LCAT, "onLeaveApplication()");
	}

	public void onPresentScreen(Ad ad) {
		Log.d(LCAT, "onPresentScreen()");
	}

	public void onDismissScreen(Ad ad) {
		Log.d(LCAT, "onDismissScreen()");
	}

}