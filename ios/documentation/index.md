# Ti.Admob Module

## Description

Use the Google Admob Module in Titanium

## Getting Started

View the [Using Titanium Modules](https://titaniumsdk.com/guide/Titanium_SDK/Titanium_SDK_How-tos/Using_Modules/Using_a_Module.html) document for instructions on getting
started with using this module in your application.

## Requirements

The Google AdMob Ads SDK has the following requirements:

### Prerequisites
    
* Use Xcode 13.2.1 or higher
* Target iOS 10.0 or higher
* Recommended: Create an [AdMob account](https://support.google.com/admob/answer/2784575) and [register an app](https://support.google.com/admob/answer/2773509).


### Update your Info.plist

Update your app's `tiapp.xml` file to add two keys inside ios plist section:

* If using module 2.5.0+ 
    ```xml  
    <key>GADIsAdManagerApp</key>
    <true/>
    ```
* If using Xcode 12.0+, set minimum ios version 11.0+ using following key in tiapp.xml, inside ios section to run app on simulators.
  If app supports iOS < 11.0, be sure to remove it while building for device or app distribution. 
    ```xml
    <min-ios-ver>11.0</min-ios-ver>
    ```
* A `GADApplicationIdentifier` key with a string value of your AdMob app ID ([identified in the AdMob UI](https://support.google.com/admob/answer/7356431)).
    ```xml  
    <key>GADApplicationIdentifier</key>
    <string>YOUR-APP-ID</string>
    ```
* A `SKAdNetworkItems` key with `SKAdNetworkIdentifier` values for Google (*cstr6suwn9.skadnetwork*) and [select additional buyers](https://developers.google.com/admob/ios/3p-skadnetworks) who have provided these values to Google.
    ```xml
    <key>SKAdNetworkItems</key>
    <array>
      <dict>
        <key>SKAdNetworkIdentifier</key>
        <string>cstr6suwn9.skadnetwork</string>
      </dict>
    </array>
    ```

* Starting in iOS 14, IDFA will be unavailable until an app calls the [App Tracking Transparency](https://developers.google.com/admob/ump/ios/quick-start#app_tracking_transparency) framework to present the app-tracking authorization request to the end user. 
  To display the App Tracking Transparency authorization request for accessing the IDFA, update your `tiapp.xml` to add the `NSUserTrackingUsageDescription` key with a custom message describing your usage.
  Below is an example description text:
    ```xml
      <key>NSUserTrackingUsageDescription</key>
      <string>This identifier will be used to deliver personalized ads to you.</string>
    ```
  To present the authorization request, call `Admob.requestTrackingAuthorization()`. We recommend waiting for the completion callback prior to loading ads, so that if the user grants the App Tracking Transparency permission, the Google AdMob SDK can use the IDFA in ad requests.
  For more information about the possible status values, see `TRACKING_AUTHORIZATION_STATUS_*.`
  If an app does not present this request, the IDFA will automatically be zeroed out which may lead to a significant loss in ad revenue.

  This is a sample of `tiapp.xml` using iOS test app ID:

    ```xml
    <ios>
        <plist>
            <dict>
                <key>GADApplicationIdentifier</key>
                <string>ca-app-pub-3940256099942544~1458002511</string> <!--Test ads-->
                <key>GADIsAdManagerApp</key>
                <true />				
                <key>SKAdNetworkItems</key>
                <array>
                    <dict>
                      <key>SKAdNetworkIdentifier</key>
                      <string>cstr6suwn9.skadnetwork</string>
                    </dict>
                    <!--Add here all other SKAdNetworkIdentifier -->
                </array>
            </dict>
        </plist>
    </ios>
    ```

## Obtaining Consent with the User Messaging Platform

The [UMP SDK](https://developers.google.com/admob/ump/ios/quick-start#introduction) provides tools for publishers to request consent for personalized ads as well as to handle Apple's App Tracking Transparency (ATT) requirements. 
The SDK is designed to be used in a linear fashion. The steps for using the SDK are:

* Request the latest consent information.
* Check if consent is required.
* Check if a form is available and if so load a form.
* Present the form.
* Provide a way for users to change their consent.

It is recommended that you request an update of the consent information at every app launch. This will determine whether or not your user needs to provide consent.
The module uses these two methods to be able to use it:
`Admob.requestConsentInfoUpdateWithParameters();`
`Admob.loadForm();`

In the [app.js](/ios/example/app.js) there is a complete example to better understand how to use them.


### Mediation adapters

This module includes Meta (Facebook) and InMobi ads mediation adapters. 

  - If you use [Meta Audience Network (previously Facebook)](https://developers.google.com/admob/ios/mediation/meta), starting with iOS 14, you will need to implement the `Admob.setAdvertiserTrackingEnabled(true)` flag.
  This allows you to inform Facebook whether to use the data to deliver personalized ads. If the flag is set to false, Facebook will not be able to deliver personalized ads.
  You need to set this flag before initializing the mediation SDK in order to receive it in the bidding request.

  - If you use [InMobi mediation](https://developers.google.com/admob/ios/mediation/inmobi), instead, you will have to use the method `Admob.setInMobi_updateGDPRConsent(true)` to deliver personalized ads.

#### Mediation Test Suite
In the Ti.Admob iOS version 4.8.0 was added `Admob.showMediationTestSuite()` method to open [Google Mobile Ads Mediation Test Suite](https://developers.google.com/admob/ios/quick-start).
It was removed since 5.0.0. You can use `gmamts.showMediationTestSuite()` with the module https://github.com/Astrovic/ti.gmamts
        
### Ad inspector

This module include [Ad inspector](https://developers.google.com/admob/ios/ad-inspector), an in-app overlay that enables authorized devices to perform real-time analysis of test ad requests directly within a mobile app.

## Doubleclick for Publishers Developer Docs
<https://developers.google.com/mobile-ads-sdk/>

## Methods

### `Ti.Admob.createView(args)`

Creates and returns a [Ti.Admob.View](./view.md) object which displays ads. See the [AdView docs](./view.md) for details.

#### Arguments

parameters[object]: a dictionary object of properties defined in [Ti.Admob.View](./view.md).

#### Example:

```js
  var Admob = require('ti.admob');

  var ad = Admob.createView({
    bottom: 0,
    width: 320, // Will calculate the width internally to fit its container if not specified
    height: 50,
    debugEnabled: true, // If enabled, a dummy value for `adUnitId` will be used to test
    adType: Admob.AD_TYPE_BANNER, // One of `AD_TYPE_BANNER` (default), `AD_TYPE_INTERSTITIAL` or `AD_TYPE_REWARDED_VIDEO`
    adUnitId: '<<YOUR ADD UNIT ID HERE>>', // You can get your own at http: //www.admob.com/
    adBackgroundColor: 'black',    
    contentURL: 'https://admob.com', // URL string for a webpage whose content matches the app content.
    requestAgent: 'Titanium Mobile App', // String that identifies the ad request's origin.
    extras: { 'npa': "1", 'version': 1.0, 'name': 'My App' }, // Object of additional infos. NOTE: npa=1 disables personalized ads (!)
    tagForChildDirectedTreatment: false, // http:///business.ftc.gov/privacy-and-security/childrens-privacy for more infos
    keywords: ['keyword1', 'keyword2']
  });
```

### `disableSDKCrashReporting()`

Disables automated SDK crash reporting. If not called, the SDK records the original exception
handler if available and registers a new exception handler. The new exception handler only
reports SDK related exceptions and calls the recorded original exception handler.

### `disableAutomatedInAppPurchaseReporting()`

Disables automated in app purchase (IAP) reporting. Must be called before any IAP transaction is
initiated. IAP reporting is used to track IAP ad conversions. Do not disable reporting if you use IAP ads.

### `isTaggedForUnderAgeOfConsent()` (Boolean)

⚠️ Removed since Ti.Admob 5.0.0. You can set `tagForUnderAgeOfConsent` parameter in `Admob.requestConsentInfoUpdateWithParameters()`

Indicates whether the user is tagged for under age of consent.

### `loadForm(args)`

It is a best practice to load a form every time the user launches your app, even if you determine consent is not required, so that the form is ready to display in case the user wishes to change their consent setting.
The forms for obtaining consent are created in AdMob UI. Once you have determined that you will ask a user for consent, the next step is to determine if a form is available. There are a variety of reasons why a form may not be available, such as:
1) The user has limit ad tracking enabled.
2) You tagged the user as under the age of consent.
To check if a form is available, use the callback status parameter

- `callback` (Function)
Async callback function that return `{status: Modules.Admob.CONSENT_STATUS_*}`

### `requestConsentInfoUpdateWithParameters(args)`

Request the latest consent information.
It is recommended that you request an update of the consent information at every app launch.
This will determine whether or not your user needs to provide consent.

- `geography` (Number) `Admob.DEBUG_GEOGRAPHY_*`
To force the SDK to treat the device as though it is not in the EEA or UK, use UMPDebugGeographyNotEEA. Note that debug settings only work on test devices. Emulators do not need to be added to the device id list as they have testing enabled by default.
- `tagForUnderAgeOfConsent` (Boolean)
Sets whether the user is tagged for under age of consent
- `testDeviceIdentifiers` (Array<String>)
Array of "TEST-DEVICE-HASHED-ID" strings. You can use `Admob.SIMULATOR_ID` for simulator.
- `callback` (Function)
Async callback function to invoke when done

### `requestTrackingAuthorization(args)`

One-time request to authorize or deny access to app-elated data that can be used for tracking the user or the device.
The system remembers the user's choice and doesn't prompt again unless a user uninstalls and then reinstalls the app on the device.
Note, when calling `Admob.requestTrackingAuthorization()`, the **NSUserTrackingUsageDescription** key must be in the **tiapp.xml**.

- `callback` (Function)
Async callback function that return `{status: Modules.Admob.TRACKING_AUTHORIZATION_STATUS_*}`

### `requestConsentInfoUpdateForPublisherIdentifiers(args)`

⚠️ Removed since Ti.Admob 5.0.0 in favor of new UMP https://developers.google.com/admob/ump

Requests consent information update for the provided publisher identifiers. All publisher
identifiers used in the application should be specified in this call. Consent status is reset to
unknown when the ad provider list changes.

- `publisherIdentifiers` (Array<String>)
- `callback` (Function)

### `resetConsent()`

Resets consent information to default state and clears ad providers.

### `setAdvertiserTrackingEnabled(true|false)`

If you use Facebook Audience Network mediation, starting with iOS 14, you will need to implement the setAdvertiserTrackingEnabled flag.
This allows you to inform Facebook whether to use the data to deliver personalized ads. If the flag is set to false, Facebook will not be able to deliver personalized ads.
You need to set this flag before initializing the mediation SDK in order to receive it in the bidding request.

### `setInMobi_updateGDPRConsent(true|false)`

If you use InMobi mediation, this allows you to inform InMobi whether to use the data to deliver personalized ads. If the flag is set to false, InMobi will not be able to deliver personalized ads.
You need to set this flag before initializing the mediation SDK in order to receive it in the bidding request.


### `setTagForUnderAgeOfConsent(true|false)`

⚠️ Removed since Ti.Admob 5.0.0. You can set `tagForUnderAgeOfConsent` parameter in `Admob.requestConsentInfoUpdateWithParameters()` 

Sets whether the user is tagged for under age of consent.

### `showConsentForm(args)`

⚠️ Removed since Ti.Admob 5.0.0 in favor of new UMP https://developers.google.com/admob/ump.
Now you should use `Admob.requestConsentInfoUpdateWithParameters({})`

Shows a consent modal form. Arguments:

- `shouldOfferPersonalizedAds` (Boolean)
Indicates whether the consent form should show a personalized ad option. Defaults to `true`.
- `shouldOfferNonPersonalizedAds` (Boolean)
Indicates whether the consent form should show a non-personalized ad option. Defaults to `true`.
- `shouldOfferAdFree` (Boolean)
Indicates whether the consent form should show an ad-free app option. Defaults to `false`.
- `callback` (Function)
Callback to be triggered once the form completes.

## Properties

### `adProviders` (Array)

⚠️ Removed since Ti.Admob 5.0.0 because deprecated by Google https://developers.google.com/admob/ios/eu-consent;

Array of ad providers.

### `consentStatus` (`CONSENT_STATUS_UNKNOWN`, `CONSENT_STATUS_NON_PERSONALIZED` or `CONSENT_STATUS_PERSONALIZED`)
⚠️ Removed since Ti.Admob 5.0.0 because deprecated by Google https://developers.google.com/admob/ios/eu-consent;

### `debugGeography` (`DEBUG_GEOGRAPHY_DISABLED`, `DEBUG_GEOGRAPHY_EEA` or `DEBUG_GEOGRAPHY_NOT_EEA`)

⚠️ Removed since Ti.Admob 5.0.0  because deprecated by Google https://developers.google.com/admob/ios/eu-consent;
You can set `geography` parameter in `Admob.requestConsentInfoUpdateWithParameters()`

Debug geography. Used for debug devices only.

### `debugIdentifiers` (Array)

⚠️ Removed since Ti.Admob 5.0.0  because deprecated by Google https://developers.google.com/admob/ios/eu-consent;

Array of advertising identifier UUID strings. Debug features are enabled for devices with these
identifiers. Debug features are always enabled for simulators.

### `trackingAuthorizationStatus` (`TRACKING_AUTHORIZATION_STATUS_NOT_DETERMINED`, `TRACKING_AUTHORIZATION_STATUS_RESTRICTED`, `TRACKING_AUTHORIZATION_STATUS_DENIED` or `TRACKING_AUTHORIZATION_STATUS_AUTHORIZED`)

Check the tracking authorization status on iOS > 14

## Constants

### Number `AD_TYPE_BANNER`

Used when creating a <Modules.Admob.View> to determine ad type of banner

### Number `AD_TYPE_INTERSTITIAL`

Used when creating a <Modules.Admob.View> to determine ad type of interstitial

### Number `AD_TYPE_REWARDED_VIDEO`

Used when creating a <Modules.Admob.View> to determine ad type of rewarded video

### Number `CONSENT_FORM_STATUS_AVAILABLE`

Consent form is available.

### Number `CONSENT_FORM_STATUS_UNAVAILABLE`

Consent form is unavailable.

### Number `CONSENT_STATUS_NON_PERSONALIZED`

⚠️ Removed since Ti.Admob 5.0.0 

Returned by `consentStatus` if the consent status is not personalized.

### Number `CONSENT_STATUS_NOT_REQUIRED`

Consent status is not required.

### Number `CONSENT_STATUS_OBTAINED`

Consent status has already been obtained.

### Number `CONSENT_STATUS_PERSONALIZED`

⚠️ Removed since Ti.Admob 5.0.0 

Returned by `consentStatus` if the consent status is personalized.

### Number `CONSENT_STATUS_REQUIRED`

Consent status is required. You should call `Admob.loadForm()` to ask for permissions.

### Number `CONSENT_STATUS_UNKNOWN`

Consent status is unknown.

### Number `DEBUG_GEOGRAPHY_DISABLED`

Geography debugging is disabled.

### Number `DEBUG_GEOGRAPHY_EEA`

Geography appears as in EEA for debug devices.

### Number `DEBUG_GEOGRAPHY_NOT_EEA`

Geography appears as not in EEA for debug devices.

### Number `GENDER_MALE`

A constant to be passed to the `gender` property to specify a gender if used. **Deprecated by the AdMob SDK, deleted from 4.5.0**.

### Number `GENDER_FEMALE`

A constant to be passed to the `gender` property to specify a gender if used. **Deprecated by the AdMob SDK, deleted from 4.5.0**.

### Number `GENDER_UNKNOWN`

A constant to be passed to the `gender` property to specify a gender if used. **Deprecated by the AdMob SDK,deleted from 4.5.0**.

### String `SIMULATOR_ID`

A constant to be passed in an array to the `testDevices` property to get test ads on the simulator.

### Number `TRACKING_AUTHORIZATION_STATUS_NOT_DETERMINED`

Returned by `trackingAuthorizationStatus` Before a device receives an authorization request to approve access to app-related data that can be used for tracking the user or the device.

### Number `TRACKING_AUTHORIZATION_STATUS_AUTHORIZED`

Returned by `trackingAuthorizationStatus` if After a device receives an authorization request to approve access, user accpet the request

### Number `RACKING_AUTHORIZATION_STATUS_DENIED`

Returned by `trackingAuthorizationStatus` if After a device receives an authorization request to approve access, user denied the request

### Number `TRACKING_AUTHORIZATION_STATUS_RESTRICTED`

Returned by `trackingAuthorizationStatus` If authorization to use app tracking data is restricted

## Supported Ads

### Banner

To receive a banner ad, you need to add it to the view hierarchy.
It fires the `didReceiveAd` event if the ad was successfully received, the `didFailToReceiveAd` event otherwise. When ad is loaded, it will be visible.

```js
var Admob = require('ti.admob');

var bannerAdView = Admob.createView({
  height: 200,
  adType: Admob.AD_TYPE_BANNER,
  adUnitId: '<<YOUR ADD UNIT ID HERE>>', // You can get your own at http: //www.admob.com/
  adBackgroundColor: 'black',
  contentURL: 'https://admob.com', // URL string for a webpage whose content matches the app content.
  requestAgent: 'Titanium Mobile App', // String that identifies the ad request's origin.
  extras: {
    'version': 1.0,
    'name': 'My App'
  }, // Object of additional infos
  tagForChildDirectedTreatment: false, // http:///business.ftc.gov/privacy-and-security/childrens-privacy for more infos
  keywords: ['keyword1', 'keyword2']
});
win.add(bannerAdView);

bannerAdView.addEventListener('didReceiveAd', function (e) {
  Ti.API.info('BannerAdView - Did receive ad: ' + e.adUnitId + '!');
});

bannerAdView.addEventListener('didFailToReceiveAd', function (e) {
  Ti.API.error('BannerAdView - Failed to receive ad: ' + e.error);
});

bannerAdView.addEventListener('willPresentScreen', function (e) {
  Ti.API.error('BannerAdView - willPresentScreen');
});

bannerAdView.addEventListener('willDismissScreen', function () {
  Ti.API.info('BannerAdView - willDismissScreen!');
});

bannerAdView.addEventListener('didDismissScreen', function () {
  Ti.API.info('BannerAdView - Dismissed screen!');
});

bannerAdView.addEventListener('didPresentScreen', function (e) {
  Ti.API.info('BannerAdView - Presenting screen!' + e.adUnitId);
});
```

### Interstitials

To receive an interstitional ad, you need to add it to the view hierarchy.
It fires the `adloaded` event if the ad was successfully received, the `didFailToReceiveAd` event otherwise. When ad is loaded, then you can use `ad.showInterstitial()` to show.

```js
var Admob = require('ti.admob');

var interstitialAd = Admob.createView({
  adType: Admob.AD_TYPE_INTERSTITIAL,
  adUnitId: '<<YOUR ADD UNIT ID HERE>>', // You can get your own at http: //www.admob.com/
  keywords: ['keyword1', 'keyword2'],
  extras: {
    'version': 1.0,
    'name': 'My App'
  }, // Object of additional infos
  visible: false // If true, covers the win when added and can't tap nothing
});
win.add(interstitialAd);

interstitialAd.addEventListener('adloaded', function (e) {
  Ti.API.info('interstitialAd - adloaded: Did receive ad: ' +  e.source.adUnitId);
  interstitialAd.showInterstitial(); 
});

interstitialAd.addEventListener('didReceiveAd', function (e) {
  Ti.API.info('interstitialAd - Did receive ad: ' +  e.source.adUnitId);
});

interstitialAd.addEventListener('didFailToReceiveAd', function (e) {
  Ti.API.error('interstitialAd - Failed to receive ad: ' + e.error);  
});

interstitialAd.addEventListener('didPresentScreen', function (e) {
  Ti.API.info('interstitialAd - didPresentScreen: ' + e.adUnitId);
});

interstitialAd.addEventListener('didDismissScreen', function (e) {
  Ti.API.info('interstitialAd - Dismissed screen: ' + e.adUnitId);  
});

interstitialAd.addEventListener('willDismissScreen', function (e) {
  Ti.API.info('interstitialAd - willDismissScreen: ' + e.adUnitId);
});

interstitialAd.addEventListener('didRecordImpression', function (e) {
  Ti.API.info('interstitialAd- didRecordImpression: ' +  e.source.adUnitId);
});
```

Please see the example for a complete implementation.

### Rewarded Video

Since version 2.4.2 you can use Admob Rewarded Video ads. This is similar to interstitials with the addition of getting a reward after watching an ad video.

You create a rewarded video ad by specifying `Admob.AD_TYPE_REWARDED_VIDEO` as the `adType`. The first video will be automatically pre-loaded after creating the view and calling `ad.receive()`. To know when a video is completely loaded you can use the `adloaded` event. To show a rewarded video add call the `ad.showRewardedVideo()` method. Loading another video can be started with the `loadRewardedVideo(adUnitId)` method on the same instance.

```js
var Admob = require('ti.admob');

var rewardedVideo = Admob.createView({
  adType: Admob.AD_TYPE_REWARDED_VIDEO,
  adUnitId: '<<YOUR ADD UNIT ID HERE>>', // You can get your own at http: //www.admob.com/
  extras: {
    'version': 1.0,
    'name': 'My App'
  } // Object of additional infos
});

rewardedVideo.receive()

rewardedVideo.addEventListener('adloaded', function(e) {
  Ti.API.info('rewardedVideo - adloaded: Did receive ad: ' +  e.source.adUnitId);
  rewardedVideo.showRewardedVideo();
});

rewardedVideo.addEventListener('adrewarded', function (reward) {
  Ti.API.debug(`rewardedVideo -adrewarded: Received reward! type: ${reward.type}, amount: ${reward.amount}`);
  console.log(reward);  
});

rewardedVideo.addEventListener('adfailedtoload', function (error) {
  Ti.API.debug('rewardedVideo - Rewarded video ad failed to load: ' + error.message);
});

rewardedVideo.addEventListener('didReceiveAd', function (e) {
  Ti.API.info('rewardedVideo - Did receive ad!');
});

rewardedVideo.addEventListener('didFailToReceiveAd', function (e) {
  Ti.API.error('rewardedVideo - Failed to receive ad: ' + e.error);
});

rewardedVideo.addEventListener('didPresentScreen', function (e) {
  Ti.API.info('rewardedVideo - didPresentScreen: ' + e.adUnitId);
});

rewardedVideo.addEventListener('didDismissScreen', function (e) {
  Ti.API.info('rewardedVideo - Dismissed screen: ' + e.adUnitId);
});

rewardedVideo.addEventListener('willDismissScreen', function (e) {
  Ti.API.info('rewardedVideo - willDismissScreen: ' + e.adUnitId);
});

rewardedVideo.addEventListener('didRecordImpression', function (e) {
  Ti.API.info('rewardedVideo - didRecordImpression');
});
```

Please see the example for a complete implementation.

### iAd

⚠️ Removed by the Admob SDK 7.x and Ti.Admob 2.2.0

Starting in 2.1.0 you can use the included iAd adapter to turn on the iAd mediation in your Admob account.

### PersonalizedAdConsent
⚠️ Removed by Ti.Admob 5.0.0 in favor of new UMP https://developers.google.com/admob/ump

## IMPORTANT NOTES
If you are also using [Titanium Firebase Core Module](https://github.com/hansemannn/titanium-firebase-core) you will probably have a duplicate symbols error. Both this module share some common dependencies. In order to solve this conflict, remove this frameworks files from `<YOUR_PROJECT_DIR>/modules/iphone/ti.admob/<VERSION>/platform` and rebuild your app:

`FBLPromises.xcframework`
`GoogleAppMeasurement.xcframework`
`GoogleAppMeasurementIdentitySupport.xcframework`
`GoogleUtilities.xcframework`
`nanopb.xcframework`
`PromisesObjC.xcframework`

## Usage

See example in [app.js](/ios/example/app.js);

## Author

Jeff Haynie, Stephen Tramer, Jasper Kennis, Jon Alter, Hans Knoechel, Vittorio Sorbera

## Module History

View the [change log](/ios/documentation/changelog.md) for this module.

## Feedback and Support

Please direct all questions, feedback, and concerns in [TiSlack](https://slack.tidev.io/) community.
Interested in contributing? Fork and submit your PR :)

## License

Copyright(c) 2010-Present by Appcelerator, Inc. All Rights Reserved. Please see the LICENSE file included in the distribution for further details.

[Ti.Admob.View]: view.md