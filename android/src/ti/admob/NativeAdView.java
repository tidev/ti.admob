/**
 * Copyright (c) 2011 by Studio Classics. All Rights Reserved.
 * Copyright (c) 2017-present by Axway Appcelerator. All Rights Reserved.
 * Author: Brian Kurzius, Axway Appcelerator
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */
package ti.admob;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.titanium.TiDimension;
import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.titanium.util.TiConvert;
import org.appcelerator.titanium.view.TiUIView;

public class NativeAdView extends TiUIView {
    private final String TAG = "NativeAdView";
    AdLoader adLoader;
    private com.google.android.gms.ads.nativead.NativeAdView adView;
    private String prop_adUnitId;
    private int imageWidth = -1;


    public NativeAdView(final TiViewProxy proxy) {
        super(proxy);
    }

    @SuppressLint("MissingPermission")
    private void createAdView() {
        if (proxy.getProperty("adUnitId") != null && !proxy.getProperty("adUnitId").toString().isEmpty()) {
            prop_adUnitId = proxy.getProperty("adUnitId").toString();
        }

        if (proxy.getProperty("imageWidth") != null && TiConvert.toInt(proxy.getProperty("imageWidth")) != -1) {
            imageWidth = (int) proxy.getProperty("imageWidth");
            imageWidth = TiConvert.toTiDimension(TiConvert.toInt(imageWidth),
                    TiDimension.TYPE_WIDTH).getAsPixels(nativeView);
        }
        String packageName = proxy.getActivity().getPackageName();
        Resources resources = proxy.getActivity().getResources();

        int resId_adview = resources.getIdentifier("layout", "layout", packageName);
        int resId_icon = resources.getIdentifier("ad_app_icon", "id", packageName);
        int resId_headline = resources.getIdentifier("ad_headline", "id", packageName);
        int resId_mediaview = resources.getIdentifier("ad_mediaview", "id", packageName);
        int resId_button = resources.getIdentifier("cta", "id", packageName);
        int resId_rating = resources.getIdentifier("rating_bar", "id", packageName);

        LayoutInflater inflater = LayoutInflater.from(proxy.getActivity());
        adView = (com.google.android.gms.ads.nativead.NativeAdView) inflater.inflate(resId_adview, null);
        ImageView img = adView.findViewById(resId_icon);
        if (imageWidth != -1) {
            ViewGroup.LayoutParams layoutParam = img.getLayoutParams();
            layoutParam.width = imageWidth;
            img.setLayoutParams(layoutParam);
        }
        TextView textview = adView.findViewById(resId_headline);
        MediaView mediaView = null;
        if (adView.findViewById(resId_mediaview) != null) {
            mediaView = adView.findViewById(resId_mediaview);
        }
        AppCompatButton button = adView.findViewById(resId_button);
        RatingBar ratingbar = adView.findViewById(resId_rating);

        MediaView finalMediaView = mediaView;
        adLoader = new AdLoader.Builder(proxy.getActivity(), prop_adUnitId).forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
            @Override
            public void onNativeAdLoaded(@NonNull NativeAd nativeAd) {
                fireEvent("loaded", new KrollDict());
                adView.setNativeAd(nativeAd);
                textview.setText(nativeAd.getHeadline());

                if (nativeAd.getIcon() != null) {
                    img.setImageDrawable(nativeAd.getIcon().getDrawable());
                    adView.setImageView(img);
                }
                adView.setHeadlineView(textview);
                adView.setCallToActionView(button);

                if (nativeAd.getStarRating() != null) {
                    adView.setStarRatingView(ratingbar);
                    ratingbar.setRating(nativeAd.getStarRating().floatValue());
                    ratingbar.setEnabled(false);
                    ratingbar.setVisibility(View.VISIBLE);
                } else {
                    ratingbar.setVisibility(View.GONE);
                }
                button.setText(nativeAd.getCallToAction());
                if (finalMediaView != null) {
                    adView.setMediaView(finalMediaView);
                    finalMediaView.setMediaContent(nativeAd.getMediaContent());
                    finalMediaView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
                }
            }
        }).withNativeAdOptions(new NativeAdOptions.Builder().build()).build();
        setNativeView(adView);
        loadAd();
    }

    @SuppressLint("MissingPermission")
    public void loadAd() {
        adLoader.loadAd(new AdRequest.Builder().build());
    }

    @Override
    public void processProperties(KrollDict d) {
        super.processProperties(d);
        if (d.containsKey(AdmobModule.PROPERTY_AD_UNIT_ID)) {
            prop_adUnitId = d.getString(AdmobModule.PROPERTY_AD_UNIT_ID);
        }

        // now create the adView
        this.createAdView();
    }
}
