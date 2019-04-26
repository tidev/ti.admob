/**
 * Copyright (c) 2011 by Studio Classics. All Rights Reserved.
 * Copyright (c) 2017-present by Axway Appcelerator. All Rights Reserved.
 * Author: Brian Kurzius, Axway Appcelerator
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */
package ti.admob;

import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.kroll.common.Log;
import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.annotations.Kroll;

import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.titanium.TiLifecycle.OnLifecycleEvent;
import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.titanium.TiBaseActivity;
import org.appcelerator.titanium.view.TiUIView;

import android.app.Activity;

@Kroll.proxy(creatableInModule = AdmobModule.class)
public class BannerViewProxy extends TiViewProxy implements OnLifecycleEvent
{
	private AdmobView adMob;
	protected static final String TAG = "BannerViewProxy";

	public BannerViewProxy()
	{
		super();
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
		adMob = new AdmobView(this);
		((TiBaseActivity) activity).addOnLifecycleEventListener(this);
		return adMob;
	}

	@Kroll.method
	public void requestAd(@Kroll.argument(optional = true) KrollDict parameters)
	{
		adMob.requestAd(parameters);
		Log.w(TAG, "requestAd() had been deprecated. Use load() instead.");
	}

	@Kroll.method
	public void requestTestAd()
	{
		adMob.requestTestAd();
		Log.w(TAG, "requestAd() had been deprecated. Use load() with 'debugIdentifiers' property instead.");
	}

	@Kroll.method
	public void load(@Kroll.argument(optional = true) KrollDict options) {
		// Load ad with options...
		adMob.nativeLoadAd(options);
	}

	@Override
	public void onDestroy(Activity activity)
	{
		adMob.destroy();
	}

	@Override
	public void onPause(Activity activity)
	{
		adMob.pause();
	}

	@Override
	public void onResume(Activity activity)
	{
		adMob.resume();
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