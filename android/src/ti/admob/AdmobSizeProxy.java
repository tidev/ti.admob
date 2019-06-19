/**
 * Copyright (c) 2011 by Studio Classics. All Rights Reserved.
 * Copyright (c) 2017-present by Axway Appcelerator. All Rights Reserved.
 * Author: Brian Kurzius, Axway Appcelerator
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */
package ti.admob;

import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.kroll.common.Log;

import com.google.android.gms.ads.AdSize;

@Kroll.proxy
public class AdmobSizeProxy extends KrollProxy
{

	private AdSize adSize;

	private static final String TAG = "AdmobSizeProxy";

	public AdmobSizeProxy(AdSize adSizeValue) {
		super();
		this.adSize = adSizeValue;
	}

	public AdSize getAdSize() {
		return this.adSize;
	}

	@Kroll.method
	public int getWidth() {
		if (this.adSize != null) {
			return adSize.getWidth();
		}
		return -1;
	}

	@Kroll.method
	public int getHeight() {
		if (this.adSize != null) {
			return adSize.getHeight();
		}
		return -1;
	}

}