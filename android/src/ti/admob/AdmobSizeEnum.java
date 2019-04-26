/**
 * Copyright (c) 2011 by Studio Classics. All Rights Reserved.
 * Copyright (c) 2017-present by Axway Appcelerator. All Rights Reserved.
 * Author: Brian Kurzius, Axway Appcelerator
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */
package ti.admob;

import com.google.android.gms.ads.AdSize;

public enum AdmobSizeEnum {

	BANNER(AdmobModule.AD_SIZE_BANNER, AdSize.BANNER),
	FLUID(AdmobModule.AD_SIZE_FLUID, AdSize.FLUID),
	FULL_BANNER(AdmobModule.AD_SIZE_FULL_BANNER, AdSize.FULL_BANNER),
	LARGE_BANNER(AdmobModule.AD_SIZE_LARGE_BANNER, AdSize.LARGE_BANNER),
	LEADERBOARD(AdmobModule.AD_SIZE_LEADERBOARD, AdSize.LEADERBOARD),
	MEDIUM_RECTANGLE(AdmobModule.AD_SIZE_MEDIUM_RECTANGLE, AdSize.MEDIUM_RECTANGLE),
	SEARCH(AdmobModule.AD_SIZE_SEARCH, AdSize.SEARCH),
	SMART_BANNER(AdmobModule.AD_SIZE_SMART_BANNER, AdSize.SMART_BANNER),
	WIDE_SKYSCRAPER(AdmobModule.AD_SIZE_WIDE_SKYSCRAPER, AdSize.WIDE_SKYSCRAPER);

	private final AdmobSizeProxy admobSizeProxy;
	private final int adSizeConstantValue;

	private AdmobSizeEnum(int intValue, AdSize adSizeValue) {
		this.admobSizeProxy = new AdmobSizeProxy(adSizeValue);
		this.adSizeConstantValue = intValue;
	}

	public static AdmobSizeEnum fromModuleConst(int value)
	{
		for (AdmobSizeEnum nextObject : AdmobSizeEnum.values()) {
			if ((nextObject != null) && (nextObject.adSizeConstantValue == value)) {
				return nextObject;
			}
		}
		return null;
	}

	public AdmobSizeProxy getAdmobSizeProxy() {
		return this.admobSizeProxy;
	}

}