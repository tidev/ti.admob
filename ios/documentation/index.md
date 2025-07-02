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

### User Consent and Ad serving 

**If consent is denied, or if certain values are not checked in the consent management phase, the ads will not be loaded**.

Why does this happen? If you pay attention to the **ConsentStatus.OBTAINED**  field, you will notice that it says that  **the consent is obtained, but the personalization is not defined**. As you see [here](https://itnext.io/android-admob-consent-with-ump-personalized-or-non-personalized-ads-in-eea-3592e192ec90).

It is up to us developers to check if the user has granted the  [**minimum requirements**](https://support.google.com/admob/answer/9760862?ref_topic=10303737) to be able to view the ads, and if he has chosen to see personalized or non-personalized ones. 

In order to assist you with this, [Mirko Dimartino](https://mirko-ddd.medium.com/?source=post_page-----3592e192ec90--------------------------------) created a solution inspired on [Tyler V](https://stackoverflow.com/questions/65351543/how-to-implement-ump-sdk-correctly-for-eu-consent/68310602#68310602) that I have implemented in this module thanks to [deckameron](https://github.com/deckameron).

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
    tagForChildDirectedTreatment: false, // https://developers.google.com/admob/ios/targeting#child-directed_setting for more infos
    tagForUnderAgeOfConsent: false, //https://developers.google.com/admob/ios/targeting#users_under_the_age_of_consent for more infos
    maxAdContentRating: Admob.MAX_AD_CONTENT_RATING_GENERAL, // https://developers.google.com/admob/ios/targeting#ad_content_filtering for more infos
    keywords: ['keyword1', 'keyword2']
  });
```

### `disableSDKCrashReporting()`

Disables automated SDK crash reporting. If not called, the SDK records the original exception
handler if available and registers a new exception handler. The new exception handler only
reports SDK related exceptions and calls the recorded original exception handler.

### `disableAutomatedInAppPurchaseReporting()`

⚠️ Removed since Ti.Admob 6.2.0.

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

### `isGDPR()` (Boolean)

Check in the IABTCF string if GDPR applies, so if in EEA.

### `canRequestAds()` (Boolean)

Indicates whether the SDK has gathered consent aligned with the app’s configured messages. Returns NO until `requestConsentInfoUpdateWithParameters()` is called.

### `canShowAds()` (Boolean)

If false (and GDPR applies, so if in EEA) you should prompt the user or to accept all, or explain in details (check above) what to check to display at least Non-Personalized Ads, or ask the user to opt for a premium version of the app, otherwise you will earn absolutely nothing.

If true you can check if user granted at least minimum requirements to show Personalized Ads with the following method.

### `canShowPersonalizedAds` (Boolean)

Finally you know if you can request AdMob Personalized or Non-Personalized Ads, if Non-Personalized you have to forward the request using this snippet.

```js
  var Admob = require('ti.admob');

  var ad = Admob.createView({
    // your properties...
    extras: { 'npa': "1"}, // npa=1 disables personalized ads 
  });  
```

or 

```js
  import Admob from 'ti.admob';
  
  var ad = Admob.createView({
    // your properties...
    extras: { 'npa': "1"}, // npa=1 disables personalized ads 
  });  
```

### `isPrivacyOptionsRequired()` (Boolean)

Check privacy options requirement status. This method should only be called after `requestConsentInfoUpdateWithParameters()`, otherwise it returns value defaults to the previous session’s value or `false`.

### `presentPrivacyOptionsForm(args)`
Presents a privacy options form if `isPrivacyOptionsRequired()` is `true`. 

This method should only be called in response to a user input to request a privacy options form to be shown. The privacy options form is preloaded by the SDK automatically when a form becomes available. If no form is preloaded, the SDK will invoke the completionHandler on the next run loop, but will asynchronously retry to load one. If viewController is nil, uses the top view controller of the application’s main window.

- `callback` (Function)
Async callback function that return `{error: ...}` or `{success: true}`

### `requestConsentInfoUpdateWithParameters(args)`

Requests consent information update. Must be called in every app session before checking the user’s consentStatus or loading a consent form. After calling this method, consentStatus will be updated synchronously to hold the consent state from the previous app session, if one exists. consentStatus may be updated again immediately before the completion handler is called.

- `geography` (Number) `Admob.DEBUG_GEOGRAPHY_*`
The UMP SDK provides a way to test your app's behavior as though the device were located in various regions, such as the EEA or UK. Note that debug settings only work on test devices. Emulators do not need to be added to the device id list as they have testing enabled by default.
- `tagForUnderAgeOfConsent` (Boolean)
Sets whether the user is tagged for under age of consent
- `testDeviceIdentifiers` (Array<String>)
Array of "TEST-DEVICE-HASHED-ID" strings.
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

If you use Facebook Audience Network mediation, starting with iOS 14, you will need to implement the `setAdvertiserTrackingEnabled` flag.
This allows you to inform Facebook whether to use the data to deliver personalized ads. If the flag is set to false, Facebook will not be able to deliver personalized ads.
You need to set this flag before initializing the mediation SDK in order to receive it in the bidding request.

 The setter for advertiserTrackingEnabled flag is deprecated since Ti.Admob 7.1.0: The `setAdvertiserTrackingEnabled` flag is not used for Audience Network SDK 6.15.0+ on iOS 17+ as the Audience Network SDK 6.15.0+ on iOS 17+ now relies on `[ATTrackingManager trackingAuthorizationStatus]` to accurately represent ATT permission for users of your app.

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

### Number `AD_TYPE_APP_OPEN`

Used when creating a <Modules.Admob.View> to determine ad type of app open

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

⚠️ Removed since Ti.Admob 8.0.0, Use `DEBUG_GEOGRAPHY_OTHER`

Geography appears as not in EEA for debug devices.

### Number `DEBUG_GEOGRAPHY_OTHER`

Geography appears as in a region with no regulation in force.

### Number `DEBUG_GEOGRAPHY_REGULATED_US_STATE`

Geography appears as in a regulated US State.

### Number `GENDER_MALE`

A constant to be passed to the `gender` property to specify a gender if used. **Deprecated by the AdMob SDK, deleted from 4.5.0**.

### Number `GENDER_FEMALE`

A constant to be passed to the `gender` property to specify a gender if used. **Deprecated by the AdMob SDK, deleted from 4.5.0**.

### Number `GENDER_UNKNOWN`

A constant to be passed to the `gender` property to specify a gender if used. **Deprecated by the AdMob SDK,deleted from 4.5.0**.

### String `MAX_AD_CONTENT_RATING_GENERAL`
A constant to be passed to the `maxAdContentRating` property to specify a maximum ad content rating for all ad requests if used.

### String `MAX_AD_CONTENT_RATING_PARENTAL_GUIDANCE`
A constant to be passed to the `maxAdContentRating` property to specify a maximum ad content rating for all ad requests if used. 

### String `MAX_AD_CONTENT_RATING_TEEN`
A constant to be passed to the `maxAdContentRating` property to specify a maximum ad content rating for all ad requests if used. 

### String `MAX_AD_CONTENT_RATING_MATURE_AUDIENCE`
A constant to be passed to the `maxAdContentRating` property to specify a maximum ad content rating for all ad requests if used. 

### String `SIMULATOR_ID`

A constant to be passed in an array to the `testDevices` property to get test ads on the simulator. **Deprecated since 7.0.0, deleted from 8.0.0 (Simulators are already in test mode by default.)**

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
  tagForChildDirectedTreatment: false, // https://developers.google.com/admob/ios/targeting#child-directed_setting for more infos
  tagForUnderAgeOfConsent: false, //https://developers.google.com/admob/ios/targeting#users_under_the_age_of_consent for more infos
  maxAdContentRating: Admob.MAX_AD_CONTENT_RATING_GENERAL, // https://developers.google.com/admob/ios/targeting#ad_content_filtering for more infos
  keywords: ['keyword1', 'keyword2']
});
win.add(bannerAdView);

bannerAdView.addEventListener('didReceiveAd', function (e) {
  Ti.API.info('BannerAdView - Did receive ad: ' + e.adUnitId);
});

bannerAdView.addEventListener('didFailToReceiveAd', function (e) {
  Ti.API.error('BannerAdView - Failed to receive ad: ' + e.error);
});

bannerAdView.addEventListener('didRecordImpression', function (e) {
  Ti.API.info('BannerAdView - didRecordImpression: ' + e.adUnitId);
});

bannerAdView.addEventListener('didRecordClick', function (e) {
  Ti.API.info('BannerAdView - didRecordClick: ' + e.adUnitId);
});

bannerAdView.addEventListener('willPresentScreen', function (e) {
  Ti.API.error('BannerAdView - willPresentScreen: ' + e.adUnitId);
});

bannerAdView.addEventListener('willDismissScreen', function (e) {
  Ti.API.info('BannerAdView - willDismissScreen: ' + e.adUnitId);
});

bannerAdView.addEventListener('didDismissScreen', function () {
  Ti.API.info('BannerAdView - Dismissed screen: ' + e.adUnitId);
});

```

### Interstitials

To receive an interstitional ad, you need to add it to the view hierarchy.
It fires the `didReceiveAd` event if the ad was successfully received, the `didFailToReceiveAd` event otherwise. When ad is loaded, then you can use `ad.showInterstitial()` to show.

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
  visible: false, // If true, covers the win when added and can't tap nothing
  tagForChildDirectedTreatment: false, // https://developers.google.com/admob/ios/targeting#child-directed_setting for more infos
  tagForUnderAgeOfConsent: false, //https://developers.google.com/admob/ios/targeting#users_under_the_age_of_consent for more infos
  maxAdContentRating: Admob.MAX_AD_CONTENT_RATING_GENERAL, // https://developers.google.com/admob/ios/targeting#ad_content_filtering for more infos
});
win.add(interstitialAd);

interstitialAd.addEventListener('didReceiveAd', function (e) {
  Ti.API.info('interstitialAd - Did receive ad: ' + e.adUnitId);
});

interstitialAd.addEventListener('didFailToReceiveAd', function (e) {
  Ti.API.error('interstitialAd - Failed to receive ad: ' + e.error);  
});

interstitialAd.addEventListener('didDismissScreen', function (e) {
  Ti.API.info('interstitialAd - Dismissed screen: ' + e.adUnitId);  
});

interstitialAd.addEventListener('willDismissScreen', function (e) {
  Ti.API.info('interstitialAd - willDismissScreen: ' + e.adUnitId);
});

interstitialAd.addEventListener('didRecordImpression', function (e) {
  Ti.API.info('interstitialAd- didRecordImpression: ' + e.adUnitId);
});

interstitialAd.addEventListener('didRecordClick', function (e) {
  Ti.API.info('interstitialAd - didRecordClick: ' + e.adUnitId);
});
```

Please see the example for a complete implementation.

### Rewarded Video

Since version 2.4.2 you can use Admob Rewarded Video ads. This is similar to interstitials with the addition of getting a reward after watching an ad video.

You create a rewarded video ad by specifying `Admob.AD_TYPE_REWARDED_VIDEO` as the `adType`. The first video will be automatically pre-loaded after creating the view and calling `ad.receive()`. To know when a video is completely loaded you can use the `didReceiveAd` event. To show a rewarded video add call the `ad.showRewardedVideo()` method. Loading another video can be started with the `loadRewardedVideo(adUnitId)` method on the same instance.

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

rewardedVideo.addEventListener('didRewardUser', function (reward) {
  Ti.API.debug(`rewardedVideo - didRewardUser: Received reward! type: ${reward.type}, amount: ${reward.amount}`);
  console.log(reward);  
});

rewardedVideo.addEventListener('didFailToReceiveAd', function (er) {
  Ti.API.debug('rewardedVideo - Rewarded video ad failed to load: ' + e.error);
});

rewardedVideo.addEventListener('didReceiveAd', function (e) {
  Ti.API.info('rewardedVideo - Did receive ad: ' + e.adUnitId);
});

rewardedVideo.addEventListener('didFailToReceiveAd', function (e) {
  Ti.API.error('rewardedVideo - Failed to receive ad: ' + e.error);
});

rewardedVideo.addEventListener('didDismissScreen', function (e) {
  Ti.API.info('rewardedVideo - Dismissed screen: ' + e.adUnitId);
});

rewardedVideo.addEventListener('willDismissScreen', function (e) {
  Ti.API.info('rewardedVideo - willDismissScreen: ' + e.adUnitId);
});

rewardedVideo.addEventListener('didRecordImpression', function (e) {
  Ti.API.info('rewardedVideo - didRecordImpression: ' + e.adUnitId);
});

rewardedVideo.addEventListener('didRecordClick', function (e) {
  Ti.API.info('rewardedVideo - didRecordClick: ' + e.adUnitId);
});
```

Please see the example for a complete implementation.

### Open App

Since version 6.2.0 you can use Admob Open App Ads, a special ad format intended for publishers wishing to monetize their app load screens. App open ads can be closed by your users at any time. App open ads can be shown when users bring your app to the foreground.

You create a rewarded video ad by specifying `Admob.AD_TYPE_APP_OPEN` as the `adType`. The first ad will be automatically pre-loaded after creating the view and calling `appOpenAd.receive()`. To know when the ad is completely loaded you can use the `didReceiveAd` event. To show an Open Ad call the `appOpenAd.showAppOpenAd()` method. Loading another ad can be started with the `appOpenAd.requestAppOpenAd();` method on the same instance.
App open ads will time out after four hours. Ads rendered more than four hours after request time will no longer be valid and may not earn revenue, so you should request a new ad. See the example for a complete implementation or read the official documentation: https://developers.google.com/admob/ios/app-open

```js
var Admob = require('ti.admob');

appOpenAd = Admob.createView({
  adType: Admob.AD_TYPE_APP_OPEN,
  adUnitId: 'ca-app-pub-3940256099942544/5662855259', // You can get your own at http: //www.admob.com/
  extras: {
    'version': 1.0,
    'name': 'My App'
  } // Object of additional infos
});		
		
// appOpenAd custom events
appOpenAd.addEventListener('didReceiveAd', function (e) {
  console.debug('appOpenAd - didReceiveAd: Did receive ad!');
});
appOpenAd.addEventListener('didFailToShowAd', function (e) {
  console.error('appOpenAd - Failed to show: ' + e.error);
});

// appOpenAd AdMob avents
appOpenAd.addEventListener('didRecordClick', function (e) {
  console.debug('appOpenAd - didRecordClick: ' + e.adUnitId);
});
appOpenAd.addEventListener('didFailToReceiveAd', function (e) {
  console.error('appOpenAd - Failed to receive ad: ' + e.error);
});		
appOpenAd.addEventListener('didDismissScreen', function (e) {
  console.debug('appOpenAd - Dismissed screen: ' + e.adUnitId);
});
appOpenAd.addEventListener('willPresentScreen', function (e) {
  console.debug('appOpenAd - willPresentScreen: ' + e.adUnitId);
});
appOpenAd.addEventListener('willDismissScreen', function (e) {
  console.debug('appOpenAd - willDismissScreen: ' + e.adUnitId);
});
appOpenAd.addEventListener('didRecordImpression', function (e) {
  console.debug('appOpenAd- didRecordImpression: ' + e.adUnitId);
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

## Usage

See example in [app.js](/ios/example/app.js);
Here a demo app ready to use: https://github.com/Astrovic/ti.admob-sample-app/

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