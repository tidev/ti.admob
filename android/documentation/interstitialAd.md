# Interstitial ads

Starting from 4.4.0 this module now supports Interstitial ads for Android.

Interstitial ads are full screen ads that are usually shown between natural steps of an application's interface flow.
For instance doing different tasks in your application or between reading different articles.

For best user experience Interstitial ads should be loaded prior showing the to the user. Interstitial ad instances can
be used for showing one ad per loading, but they can be used multiple times. A good way of reusing an Interstitial ad is
to show an ad, load a new after it has been closed one, and await for the proper time to show the recently loaded. 

## Properties

### adUnitId

Id for this add. This property can be set in the creation dictionary or after creating the Interstitial ad instance.

### isReady

> `isReady :Boolean`
Returns `true` if the interstitial is ready to be displayed.

## Methods

### setAdUnitId(String id)

Sets the adUnitId property.

### getAdUnitId()

Gets the adUnitId property.

### load([options])

Loads an ad for this Interstitial ad instance.

#### Parameters

 options(optional) - dictionary containing options for customizing the load call.

 | Name | Type | Description |
| --- | --- | --- |
| `options.keywords` | `Array<String>` | Keywords for targeting purposes. |
| `options.extras` | `Object` | Extra parameters to pass to a specific ad network adapter. |
| `options.contentUrl` | `Object` | Content URL for targeting purposes. |
| `options.tagForChildDirectedTreatment` | `Boolean` | This option allows you to specify whether you would like your app to be treated as child-directed for purposes of the Children’s Online Privacy Protection Act (COPPA) - https//business.ftc.gov/privacy-and-security/childrens-privacy. |
| `options.requestAgent` | `String` | Request agent string to identify the ad request's origin. |
| `options.testDevices` | `Array<String>` | Test ads will be returned for devices with device IDs specified in this array. Use AdMob.SIMULATOR_ID to add the simulator. |

### show()

Shows an Interstitial ad if there is one successfully loaded. 

## Events

### load

Fired when an ad finishes loading.

### fail

Fired when an ad request fails.

### open

Fired when an ad opens an overlay that covers the screen.

### close

Fired when the user is about to return to the app after tapping on an ad.

### leftapp

Fired when the user has left the app.

## Example:

	// Create an Interstitial ad with a testing AdUnitId
	var interstitialAd = Admob.createInterstitialAd({ adUnitId:"ca-app-pub-3940256099942544/1033173712" });

	// Add all listeners for the add.
	interstitialAd.addEventListener(Admob.AD_CLOSED, function () {
	    Ti.API.info('Interstitial Ad closed!');
	});
	interstitialAd.addEventListener(Admob.AD_RECEIVED, function () {
	    // When a new Interstitial ad is loaded, show it.
	    Ti.API.info('Interstitial Ad loaded!');
	    interstitialAd.show();
	});
	interstitialAd.addEventListener(Admob.AD_CLICKED, function () {
	    Ti.API.info('Interstitial Ad clicked!');
	});
	interstitialAd.addEventListener(Admob.AD_NOT_RECEIVED, function (e) {
	    Ti.API.info('Interstitial Ad not received! Error code = ' + e.errorCode);
	});
	interstitialAd.addEventListener(Admob.AD_OPENED, function () {
	    Ti.API.info('Interstitial Ad opened!');
	});
	interstitialAd.addEventListener(Admob.AD_LEFT_APPLICATION, function () {
	    Ti.API.info('Interstitial Ad left application!');
	});
	interstitialAd.load();
