/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2010 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */
package com.appcelerator.ti.admob;

import org.appcelerator.kroll.KrollArgument;
import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.kroll.KrollConverter;
import org.appcelerator.kroll.KrollInvocation;
import org.appcelerator.kroll.KrollMethod;
import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.KrollProxyBinding;
import org.appcelerator.kroll.KrollModuleBinding;
import org.appcelerator.kroll.KrollDynamicProperty;
import org.appcelerator.kroll.KrollReflectionProperty;
import org.appcelerator.kroll.KrollInjector;
import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollDefaultValueProvider;
import org.appcelerator.kroll.util.KrollReflectionUtils;
import org.appcelerator.kroll.util.KrollBindingUtils;
import org.appcelerator.titanium.kroll.KrollBridge;
import org.appcelerator.titanium.TiContext;
import org.appcelerator.titanium.util.Log;

import org.mozilla.javascript.Scriptable;

import java.util.HashMap;

import com.appcelerator.ti.admob.TiAdmobModule;


/* WARNING: this code is generated for binding methods in Android */
public class TiAdmobModuleBindingGen
	extends org.appcelerator.kroll.KrollModuleBindingGen
{
	private static final String TAG = "TiAdmobModuleBindingGen";

	private static final String CONST_ADSIZE_IAB_LEADERBOARD = "ADSIZE_IAB_LEADERBOARD";
	private static final String CONST_ADSIZE_IAB_MRECT = "ADSIZE_IAB_MRECT";
	private static final String CONST_ADSIZE_BANNER = "ADSIZE_BANNER";
	private static final String CONST_ADSIZE_IAB_BANNER = "ADSIZE_IAB_BANNER";
	private static final String CREATE_AdView = "createAdView";
		
	public TiAdmobModuleBindingGen() {
		super();
		// Constants are pre-bound
		bindings.put(CONST_ADSIZE_IAB_LEADERBOARD, TiAdmobModule.ADSIZE_IAB_LEADERBOARD);
		bindings.put(CONST_ADSIZE_IAB_MRECT, TiAdmobModule.ADSIZE_IAB_MRECT);
		bindings.put(CONST_ADSIZE_BANNER, TiAdmobModule.ADSIZE_BANNER);
		bindings.put(CONST_ADSIZE_IAB_BANNER, TiAdmobModule.ADSIZE_IAB_BANNER);
		
		bindings.put(CREATE_AdView, null);
		
	}

	public void bindContextSpecific(KrollBridge bridge, KrollProxy proxy) {
	}

	@Override
	public Object getBinding(String name) {
		Object value = bindings.get(name);
		if (value != null) { 
			return value;
		}

		// Proxy create methods
		if (name.equals(CREATE_AdView)) {
			KrollBindingUtils.KrollProxyCreator creator = new KrollBindingUtils.KrollProxyCreator() {
				public KrollProxy create(TiContext context) {
					return new com.appcelerator.ti.admob.AdViewProxy(context);
				}
			};
			KrollMethod createAdView_method = KrollBindingUtils.createCreateMethod(CREATE_AdView, creator);
			bindings.put(CREATE_AdView, createAdView_method);
			return createAdView_method;
		}







		return super.getBinding(name);
	}

	private static final String SHORT_API_NAME = "TiAdmob";
	private static final String FULL_API_NAME = "TiAdmob";
	private static final String ID = "com.appcelerator.ti.admob";

	public String getAPIName() {
		return FULL_API_NAME;
	}

	public String getShortAPIName() {
		return SHORT_API_NAME;
	}

	public String getId() {
		return ID;
	}

	public KrollModule newInstance(TiContext context) {
		return new TiAdmobModule(context);
	}

	public Class<? extends KrollProxy> getProxyClass() {
		return TiAdmobModule.class;
	}

	public boolean isModule() {
		return true; 
	}
}