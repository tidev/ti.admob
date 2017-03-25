# Ti.Admob Module

## Description

Shows ads from Admob.

## Getting Started

View the [Using Titanium Modules](http://docs.appcelerator.com/titanium/latest/#!/guide/Using_Titanium_Modules) document for instructions on getting
started with using this module in your application.

## Requirements

The Google AdMob Ads SDK has the following requirements:

* An AdMob site ID.
* Xcode 6.4 or later.
* Runtime of iOS 7.1 or later.

The SDK also requires that the following frameworks are available when you build your application:

* AdSupport
* AudioToolbox
* AVFoundation
* CoreGraphics
* CoreTelephony
* MessageUI
* StoreKit
* SystemConfiguration

## Accessing the Ti.Admob Module

To access this module from JavaScript, you would do the following:

	var Admob = require('ti.admob');

## Doubleclick for Publishers Developer Docs
<https://developers.google.com/mobile-ads-sdk/>

## Constants

### String SIMULATOR_ID

A constant to be passed in an array to the `testDevices` property to get test ads on the simulator.

### Number GENDER_MALE

A constant to be passed to the `gender` property to specify a gender if used.

### Number GENDER_FEMALE

A constant to be passed to the `gender` property to specify a gender if used. 

### Number GENDER_UNKNOWN

A constant to be passed to the `gender` property to specify a gender if used. 

## Functions

### Ti.Admob.createView({...})

Creates and returns a [Ti.Admob.View][] object which displays ads.

#### Arguments

parameters[object]: a dictionary object of properties defined in [Ti.Admob.View][].

#### Example:

	var ad = Admob.createView({
		bottom: 0, 
		width: 320, // Will calculate the width internally to fit its container if not specified
        height: 50,
        debugEnabled: true, // If enabled, a dummy value for `adUnitId` will be used to test
        adType: Admob.AD_TYPE_BANNER, // One of `AD_TYPE_BANNER` (default) or `AD_TYPE_INTERSTITIAL`
		adUnitId: '<<YOUR ADD UNIT ID HERE>>', // You can get your own at http: //www.admob.com/
		adBackgroundColor: 'black', 
		testDevices: [Admob.SIMULATOR_ID], // You can get your device's id by looking in the console log
		dateOfBirth: new Date(1985, 10, 1, 12, 1, 1),
        gender: Admob.GENDER_MALE, // GENDER_MALE or GENDER_FEMALE, default: undefined
        contentURL: 'https://admob.com', // URL string for a webpage whose content matches the app content.
        requestAgent: 'Titanium Mobile App', // String that identifies the ad request's origin.
        extras: {"version": 1.0, "name": "My App"}, // Object of additional infos
        tagForChildDirectedTreatment: false, // http:///business.ftc.gov/privacy-and-security/childrens-privacy for more infos
		keywords: ['keyword1', 'keyword2']
	});

### Interstitials

To receive an interstitional ad, you need to call `ad.receive()` instead of adding it to the viewe hierarchy. 
It fires the `didReceiveAd` event if the  ad was successfully received, the `didFailToReceiveAd` event otherwise. Please check 
the example for a detailed example of different banner types.

### iAd

Starting in 2.1.0 you can use the included iAd adapter to turn on the iAd mediation in your Admob account.

## Usage

See example.

## Author

Jeff Haynie, Stephen Tramer, Jasper Kennis, Jon Alter, Hans Knoechel

## Module History

View the [change log](changelog.html) for this module.

## Feedback and Support

Please direct all questions, feedback, and concerns to [info@appcelerator.com](mailto:info@appcelerator.com?subject=iOS%20Admob%20Module).

## License

Copyright(c) 2010-Present by Appcelerator, Inc. All Rights Reserved. Please see the LICENSE file included in the distribution for further details.

[Ti.Admob.View]: view.html
