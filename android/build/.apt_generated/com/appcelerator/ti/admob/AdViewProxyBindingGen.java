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

import com.appcelerator.ti.admob.AdViewProxy;


/* WARNING: this code is generated for binding methods in Android */
public class AdViewProxyBindingGen
	extends org.appcelerator.titanium.proxy.TiViewProxyBindingGen
{
	private static final String TAG = "AdViewProxyBindingGen";

	private static final String METHOD_loadAd = "loadAd";
		
	public AdViewProxyBindingGen() {
		super();
		// Constants are pre-bound
		
		bindings.put(METHOD_loadAd, null);
		
	}

	public void bindContextSpecific(KrollBridge bridge, KrollProxy proxy) {
	}

	@Override
	public Object getBinding(String name) {
		Object value = bindings.get(name);
		if (value != null) { 
			return value;
		}






		// Methods
		if (name.equals(METHOD_loadAd)) {
			KrollMethod loadAd_method = new KrollMethod(METHOD_loadAd) {
				public Object invoke(KrollInvocation __invocation, Object[] __args) throws Exception
				{
	

	Object __loadAd_tmp;
	
	
	
	
	((AdViewProxy) __invocation.getProxy()).loadAd(
);
		return KrollProxy.UNDEFINED;
				}
			};
			bindings.put(METHOD_loadAd, loadAd_method);
			return loadAd_method;
		}


		return super.getBinding(name);
	}

	private static final String SHORT_API_NAME = "AdView";
	private static final String FULL_API_NAME = "TiAdmob.AdView";
	private static final String ID = "com.appcelerator.ti.admob.AdViewProxy";

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
		return null;
	}

	public Class<? extends KrollProxy> getProxyClass() {
		return AdViewProxy.class;
	}

	public boolean isModule() {
		return false; 
	}
}