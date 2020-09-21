# Titanium AdMob Module

Use the Google Mobile Ads SDK in Appcelerator Titanium.

## Requirements

- [x] Android: Titanium SDK 7.0.0+ and [Ti.PlayServices](https://github.com/appcelerator-modules/ti.playservices) v16.1.4+
- [x] iOS: Titanium SDK 8.0.0+ (for Titanium SDK 7.x use version [2.4.0](https://github.com/appcelerator-modules/ti.admob/releases/tag/ios-2.4.0))

## Getting started

This getting started guide shows you how to integrate the Google Mobile Ads module into your Titanium app.

### Install the module

Copy the module to your local project or install it globally and enable it in your project.

```xml
<ti:app>
  <modules>
    <module>ti.admob</module>
  </modules>
</ti:app>
```

> 💡 **Android Note:**  Make sure that you have at least version 16.1.4 of the [ti.playservices](https://github.com/appcelerator-modules/ti.playservices) module installed. This is currently not shipped with the latest SDK so you need to download and install it manually from [here](https://github.com/appcelerator-modules/ti.playservices/releases).

### Configure your App ID

You need to configure both Android and iOS with your AdMob [App ID](https://support.google.com/admob/answer/7356431).

> ❗️ **Warning:** Properly configuring your app with your AdMob App ID is required in the latest Google Mobile Ads SDK. Failure to add this results in a crash with the message: **"The Google Mobile Ads SDK was initialized incorrectly."**

#### Android

Update the `<android>` section in your tiapp.xml and add your AdMob App ID to the manifest as shown below.

```xml
<ti:app>
  <android xmlns:android="http://schemas.android.com/apk/res/android">
    <manifest>
      <application>
        <!-- Sample AdMob App ID: ca-app-pub-3940256099942544~3347511713 -->
        <meta-data
          android:name="com.google.android.gms.ads.APPLICATION_ID"
          android:value="YOUR_ADMOB_APP_ID"/>
      </application>
    </manifest>
  </android>
</ti:app>
```

#### iOS

Add a `GADApplicationIdentifier` key with a string value of your AdMob app ID to the `<ios>` section in your tiapp.xml.

```xml
<ti:app>
  <ios>
    <plist>
      <key>GADApplicationIdentifier</key>
      <!-- Sample AdMob App ID: ca-app-pub-3940256099942544~3347511713 -->
      <string>YOUR_ADMOB_APP_ID</string>
    </plist>
  </ios>
</ti:app>
```

## Initialize Mobile Ads

Before loading ads, have your app initialize the Mobile Ads SDK by calling AdMob.initialize() with your AdMob AppID. This needs to be done only once, ideally at app launch.

```js
const AdMob = require('ti.admob');
AdMob.initialize('YOUR_ADMOB_APP_ID');
```

## Implement ad formats

Now that the module is imported and initialized, you're ready to imlpement an ad. All ad formats can be created in a similar fashion. You create a new ad with one of the `create*` factory methods and then call `load()` on the returned instance to start loading a new ad. See the below example how to create a simple banner ad.

```js
const AdMob = require('ti.admob');
AdMob.initialize('ca-app-pub-3940256099942544~1458002511');
const win = Ti.UI.createWindow();
const bannerView = AdMob.createBannerView({
  adUnitId: 'ca-app-pub-3940256099942544/2934735716',
  bottom: 0,
  height: 50,
  backgroundColor: 'black'
});
bannerView.addEventListener('load', () => console.log('ad loaded'));
bannerView.load({
  keywords: ['test']
});
win.add(bannerView);
win.open();
```

Available ad formats are:

- [BannerView](./docs/banner-view.md)
- [InterstitalAd](./docs/interstitial-ad.md)

Refer to the examples in the above types for more info how to implement each ad format.

## API Reference

See the [API docs](./docs/README.md) for a reference all available types.

## Contributors

- Jasper Kennis
- Jon Alter
- Jeff English
- Muhammad Dadu
- Gary Mathews
- Dawson Toth
- Hans Knöchel
- Jan Vennemann
- Yordan Banev

Interested in contributing? Read the [contributors/committer's](https://wiki.appcelerator.org/display/community/Home) guide.

## Issues

Report issues to [Appcelerator JIRA](https://jira.appcelerator.org).

## License

Apache License. Version 2.0
