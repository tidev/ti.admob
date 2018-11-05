/**
 * Copyright (c) 2011 by Studio Classics. All Rights Reserved.
 * Copyright (c) 2017-present by Axway Appcelerator. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */

package ti.admob;

import com.google.android.gms.ads.AdListener;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.kroll.common.Log;

public class CommonAdListener extends AdListener {

	private KrollProxy sourceProxy;
	private String sourceTag;

	public CommonAdListener(KrollProxy sourceProxy, String tag) {
		this.sourceProxy = sourceProxy;
		this.sourceTag = tag;
	}

	@Override
	public void onAdLoaded() {
		Log.d(this.sourceTag, "onAdLoaded()");
		this.sourceProxy.fireEvent(AdmobModule.AD_RECEIVED, new KrollDict());
	}

	@Override
	public void onAdFailedToLoad(int errorCode)
	{
		super.onAdFailedToLoad(errorCode);
		Log.d(this.sourceTag, "onAdFailedToLoad(): " + errorCode);
		KrollDict eventData = new KrollDict();
		eventData.put("errorCode", String.valueOf(errorCode));
		this.sourceProxy.fireEvent(AdmobModule.AD_NOT_RECEIVED, eventData);
	}

	@Override
	public void onAdClosed() {
		super.onAdClosed();
		this.sourceProxy.fireEvent(AdmobModule.AD_CLOSED, new KrollDict());
	}

	@Override
	public void onAdOpened() {
		super.onAdOpened();
		this.sourceProxy.fireEvent(AdmobModule.AD_OPENED, new KrollDict());
	}

	@Override
	public void onAdLeftApplication() {
		super.onAdLeftApplication();
		this.sourceProxy.fireEvent(AdmobModule.AD_LEFT_APPLICATION, new KrollDict());
	}
}