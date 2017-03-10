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

@Kroll.module(name = "Admob", id = "ti.admob")
public class AdmobModule extends KrollModule {
	private static final String TAG = "AdmobModule";
	public static String MODULE_NAME = "AndroidAdMobModule";

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

	public AdmobModule() {
		super();
		Log.d(TAG, "adMob module instantiated");
	}

	@Kroll.method
	public int isGooglePlayServicesAvailable() {
		return GooglePlayServicesUtil.isGooglePlayServicesAvailable(TiApplication.getAppRootOrCurrentActivity());
	}
}
