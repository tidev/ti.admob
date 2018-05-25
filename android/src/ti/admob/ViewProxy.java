/**
 * Copyright (c) 2011 by Studio Classics. All Rights Reserved.
 * Copyright (c) 2017-present by Axway Appcelerator. All Rights Reserved.
 * Author: Brian Kurzius, Axway Appcelerator
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */
package ti.admob;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.titanium.TiLifecycle.OnLifecycleEvent;
import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.titanium.TiBaseActivity;
import org.appcelerator.kroll.common.Log;
import org.appcelerator.titanium.view.TiUIView;

import android.app.Activity;

@Kroll.proxy(creatableInModule = AdmobModule.class)
public class ViewProxy extends TiViewProxy implements OnLifecycleEvent
{
	private View adMob;
	private static final String TAG = "AdMobViewProxy";

	public ViewProxy()
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
		adMob = new View(this);
		((TiBaseActivity) activity).addOnLifecycleEventListener(this);
		return adMob;
	}

	@Kroll.method
	public void requestAd(@Kroll.argument(optional = true) KrollDict parameters)
	{
		Log.d(TAG, "requestAd()");
		adMob.requestAd(parameters);
	}

	@Kroll.method
	public void requestTestAd()
	{
		Log.d(TAG, "requestTestAd(): ");
		adMob.requestTestAd();
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
