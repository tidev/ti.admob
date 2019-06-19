# Admob Module

## Description

Allows for the display of AdMob in Titanium Android applications.

Please note that if your androidManifest has screen support set to: android:anyDensity="false", any banner ads will 
display too small on high density devices.
It is not clear at this point if this is a bug with AdMob or Titanium.
In any event, you will either need to NOT set your screen support -- or set android:anyDensity="true" and adjust your app layout accordingly

## Getting Started

View the [Using Titanium Modules](http://docs.appcelerator.com/platform/latest/#!/guide/Using_Titanium_Modules) document 
for instructions on getting started with using this module in your application.

In order to use the module you would need to add the following tags in yout tiapp.xml

	<android 
	    xmlns:android="http://schemas.android.com/apk/res/android">
	    <manifest>
	      <application>
	        <meta-data
	            android:name="com.google.android.gms.ads.APPLICATION_ID"
	            android:value="your-admob-application-id"/>
	      </application>
	    </manifest>
	  </android>

## Requirements

- [x] Titanium SDK 7.0.0+
- [x] [Ti.PlayServices](https://github.com/appcelerator-modules/ti.playservices) module

## Accessing the Admob Module

To access this module from JavaScript, you would do the following (recommended):

```js
var Admob = require('ti.admob');
```

The "Admob" variable is now a reference to the Module object.

## Doubleclick for Publishers Developer Docs

<https://developers.google.com/mobile-ads-sdk/>

## Functions

### initialize(admobApplicationID)

You need to initialize the Admob SDK by passing your AdmobAppID as a parameter to this method.

### Number isGooglePlayServicesAvailable()

Returns a number value indicating the availability of Google Play Services which are for push notifications.

Possible values include `SUCCESS`, `SERVICE_MISSING`, `SERVICE_VERSION_UPDATE_REQUIRED`, `SERVICE_DISABLED`,
and `SERVICE_INVALID`.

### `createView(args)`

DEPRECATED since 4.5.0. Use `createBannerView` instead.
Returns a view with an ad initialized by default.

#### Arguments

parameters[object]: a dictionary object of properties.

#### Example:

	var adMobView = Admob.createView({
	    adUnitId: 'ENTER_YOUR_AD_UNIT_ID_HERE',
	    testing:false, // default is false
	    top: 0, // optional
	    left: 0, // optional
	    right: 0, // optional
	    bottom: 0 // optional
	    adBackgroundColor: '#FF8800', // optional
	    backgroundColorTop: '#738000', // optional - Gradient background color at top
	    borderColor: '#000000', // optional - Border color
	    textColor: '#000000', // optional - Text color
	    urlColor: '#00FF00', // optional - URL color
	    linkColor: '#0000FF' // optional -  Link text color
	});

### `AdMobView.requestAd(args)`

DEPRECATED since 4.5.0. Use `load()` instead.
Calls for a new ad if needed. Pass optional `args` to configure extras.

#### Example:

```js
    bannerView.load({
        extras: {
            adBackgroundColor:"FF8855", // optional
            backgroundColorTop: "738000", //optional - Gradient background color at top
            borderColor: "#000000", // optional - Border color
            textColor: "#000000", // optional - Text color
            urlColor: "#00FF00", // optional - URL color
            linkColor: "#0000FF" //optional -  Link text color
        }
    });
```

Deprecated:
```js
adMobView.requestAd({
    extras: {
        'npa': '1' // Disable personalized ads (GDPR)
    }
});
```

### `AdMobView.requestTestAd()`

DEPRECATED since 4.5.0. Use `load()` with the `testDevices` property instead. More details about this can be found on this link:
https://developers.google.com/admob/android/test-ads#enable_test_devices
Calls for a test ad if needed. This works independently from the testing flag above.

#### Example:

```js
adMobView.requestTestAd();
```

### `requestConsentInfoUpdateForPublisherIdentifiers(args)`

Requests consent information update for the provided publisher identifiers. All publisher
identifiers used in the application should be specified in this call. Consent status is reset to
unknown when the ad provider list changes.

- `publisherIdentifiers` (Array<String>)
- `callback` (Function)

### `showConsentForm(args)`

Shows a consent modal form. Arguments:

- `shouldOfferPersonalizedAds` (Boolean)
Indicates whether the consent form should show a personalized ad option. Defaults to `true`.
- `shouldOfferNonPersonalizedAds` (Boolean)
Indicates whether the consent form should show a non-personalized ad option. Defaults to `true`.
- `shouldOfferAdFree` (Boolean)
Indicates whether the consent form should show an ad-free app option. Defaults to `false`.
- `callback` (Function)
Callback to be triggered once the form completes.

### `resetConsent()`

Resets consent information to default state and clears ad providers.

### `setTagForUnderAgeOfConsent(true|false)`

DEPRECATED since 4.5.0. Use directly `isTaggedForUnderAgeOfConsent` instead.
Sets whether the user is tagged for under age of consent.

### `isTaggedForUnderAgeOfConsent()` (Boolean)

DEPRECATED since 4.5.0. Use directly `isTaggedForUnderAgeOfConsent` instead.
Indicates whether the user is tagged for under age of consent.

## Properties

### `consentStatus` (`CONSENT_STATUS_UNKNOWN`, `CONSENT_STATUS_NON_PERSONALIZED` or `CONSENT_STATUS_PERSONALIZED`)

### `adProviders` (Array)

Array of ad providers.

### `debugGeography` (`DEBUG_GEOGRAPHY_DISABLED`, `DEBUG_GEOGRAPHY_EEA` or `DEBUG_GEOGRAPHY_NOT_EEA`)

Debug geography. Used for debug devices only.

### IsTaggedForUnderAgeOfConsent: Boolean

### getAndroidAdId(callback)

Gets the user Android Advertising ID. Since this works in a background thread in native
Android a callback is called when the value is fetched. The callback parameter is a key/value
pair with key `androidAdId` and a String value with the id.

#### Example:

	Admob.getAndroidAdId(function (data) {
		Ti.API.info('AAID is ' + data.aaID);
	});

### isLimitAdTrackingEnabled(callback)

Checks whether the user has opted out from ad tracking in the device's settings. Since
this works in a background thread in native Android a callback is called when the value
is fetched. The callback parameter is a key/value pair with key `isLimitAdTrackingEnabled`
and a boolean value for the user's setting.

#### Example:

	Admob.isLimitAdTrackingEnabled(function (data) {
		Ti.API.info('Ad tracking is limited: ' + data.isLimitAdTrackingEnabled);
	});

## Constants

### Number `SUCCESS`
Returned by `isGooglePlayServicesAvailable()` if the connection to Google Play Services was successful.

### Number `SERVICE_MISSING`
Returned by `isGooglePlayServicesAvailable()` if Google Play Services is missing on this device.

### Number `SERVICE_VERSION_UPDATE_REQUIRED`
Returned by `isGooglePlayServicesAvailable()` if the installed version of Google Play Services is out of date.

### Number `SERVICE_DISABLED`
Returned by `isGooglePlayServicesAvailable()` if the installed version of Google Play Services has been disabled on this device.

### Number `SERVICE_INVALID`
Returned by `isGooglePlayServicesAvailable()` if the version of the Google Play Services installed on this device is not authentic.

### Number `CONSENT_STATUS_UNKNOWN`
Returned by `consentStatus` if the consent status is unknown.

### Number `CONSENT_STATUS_NON_PERSONALIZED`
Returned by `consentStatus` if the consent status is not personalized.

### Number `CONSENT_STATUS_PERSONALIZED`
Returned by `consentStatus` if the consent status is personalized.

### Number `DEBUG_GEOGRAPHY_DISABLED`
Returned by `debugGeography` if geography debugging is disabled.

### Number `DEBUG_GEOGRAPHY_EEA`
Returned by `debugGeography` if geography appears as in EEA for debug devices.

### Number `DEBUG_GEOGRAPHY_NOT_EEA`
Returned by `debugGeography` if geography appears as not in EEA for debug devices.

### `Admob.AD_RECEIVED`

DEPRECATED since 4.5.0. Use `load` instead.
returns the constant for AD_RECEIVED -- for use in an event listener

#### Example:

	adMobView.addEventListener('load', function () {
	    alert('ad was just received');
	});

Deprecated:
	adMobView.addEventListener(Admob.AD_RECEIVED, function () {
	    alert('ad was just received');
	});

### `Admob.AD_NOT_RECEIVED`

DEPRECATED since 4.5.0. Use `fail` instead.
returns whenever the ad was not successfully loaded. The callback contains the
error code in its parameter under the key `errorCode`
Error codes for Android can be checked here:
https://developers.google.com/android/reference/com/google/android/gms/ads/AdRequest#ERROR_CODE_INTERNAL_ERROR

#### Example:

	adMobView.addEventListener('fail', function (e) {
	    alert('ad was not received. error code is ' + e.errorCode);
	});

Deprecated:
	adMobView.addEventListener(Admob.AD_NOT_RECEIVED, function (e) {
	    alert('ad was not received. error code is ' + e.errorCode);
	});

### `Admob.AD_OPENED`

DEPRECATED since 4.5.0. Use `open` instead.
returns the constant for AD_OPENED -- for use in an event listener

#### Example:

	adMobView.addEventListener('open', function () {
	    alert('ad was just opened');
	});

Deprecated:
	adMobView.addEventListener(Admob.AD_OPENED, function () {
	    alert('ad was just opened');
	});

### `Admob.AD_CLOSED`

DEPRECATED since 4.5.0. Use `close` instead.

#### Example:

	adMobView.addEventListener('closed', function () {
	    alert('ad was just closed');
	});

Deprecated:
	adMobView.addEventListener(Admob.AD_CLOSED, function () {
	    alert('ad was just closed');
	});

### `Admob.AD_LEFT_APPLICATION`

DEPRECATED since 4.5.0. Use `leftapp` instead. 

#### Example:

	adMobView.addEventListener('leftapp', function () {
	    alert('user just left the application through the ad');
	});

Deprecated:
	adMobView.addEventListener(Admob.AD_LEFT_APPLICATION, function () {
	    alert('user just left the application through the ad');
	});

### Admob.AD_SIZE_BANNER

### Admob.AD_SIZE_FLUID

### Admob.AD_SIZE_FULL_BANNER

### Admob.AD_SIZE_LARGE_BANNER

### Admob.AD_SIZE_LEADERBOARD

### Admob.AD_SIZE_MEDIUM_RECTANGLE

### Admob.AD_SIZE_SEARCH

### Admob.AD_SIZE_SMART_BANNER

### Admob.AD_SIZE_WIDE_SKYSCRAPER

## Support the Facebook Audience Network adapter

Starting in 4.3.0 you can use the included Facebook Audience Network adapter to turn on the mediation in your AdMob account.
Here you do not have to do anything 😙. You only need to configure mediation in your AdMob and Facebook accounts by 
following the [official guide](https://developers.google.com/admob/android/mediation/facebook).

WARNING! From version 4.5.0 the Facebook Audience Network adapter is deprecated. Once it is removed in a future release, it would depend
on users to add it manually to the module when they need it.

## Module History

View the [change log](changelog.html) for this module.

## Feedback and Support

Please direct all questions, feedback, and concerns to [info@appcelerator.com](mailto:info@appcelerator.com?subject=Android%20Admob%20Module).

## Author

Brian Kurzius | bkurzius@gmail.com
Axway Appcelerator

## License
Copyright 2011, Brian Kurzius, Studio Classics.
Copyright 2014 - Present, Appcelerator.

Please see the LICENSE file included in the distribution for further details.
