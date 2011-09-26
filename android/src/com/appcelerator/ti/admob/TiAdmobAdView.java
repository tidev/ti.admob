package com.appcelerator.ti.admob;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.titanium.util.TiConfig;
import org.appcelerator.titanium.util.TiConvert;
import org.appcelerator.titanium.view.TiUIView;

import android.util.Log;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public class TiAdmobAdView extends TiUIView {
	private static final String LCAT = "TiAdmobAdView";
	private static final boolean DBG = TiConfig.LOGD;
	
	public TiAdmobAdView(final TiViewProxy proxy) {
		super(proxy);
		
		AdSize adSize = AdSize.BANNER;
		String adUnitId = "";
		KrollDict d = proxy.getCreationDict();
		
		if (d.containsKey("adSize")) {
			Log.d(LCAT, d.getInt("adSize").toString());
			switch(d.getInt("adSize")) {
				case TiAdmobModule.ADSIZE_BANNER:
					adSize = AdSize.BANNER;
					break;
				case TiAdmobModule.ADSIZE_IAB_BANNER:
					adSize = AdSize.IAB_BANNER;
					break;
				case TiAdmobModule.ADSIZE_IAB_LEADERBOARD:
					adSize = AdSize.IAB_LEADERBOARD;
					break;
				case TiAdmobModule.ADSIZE_IAB_MRECT:
					adSize = AdSize.IAB_MRECT;
					break;
				default:
					adSize = AdSize.BANNER;
					break;
			}
			if (!d.containsKey("height")) {
				d.put("height", adSize.getHeight());
			}
			if (!d.containsKey("width")) {
				d.put("width", adSize.getWidth());
			}
		}
		if (d.containsKey("adUnitId")) {
			adUnitId = d.getString("adUnitId");
		}
		
		AdView adView = new AdView(proxy.getTiContext().getActivity(), adSize, adUnitId);
		setNativeView(adView);
	}
	
	@Override
	public void processProperties(KrollDict d)
	{
		super.processProperties(d);
		
		AdView adView = (AdView)getNativeView();
//		if (d.containsKey("adSize")) {
//			adView.setBackgroundColor(TiConvert.toColor(d, "tony"));
//		}
	}
	
	public void loadAd() {
		AdView adView = (AdView)getNativeView();
		adView.loadAd(new AdRequest());
	}
}
