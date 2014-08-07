# Ti.Admob Module

## Description

Shows ads from Admob.

## Getting Started

View the [Using Titanium Modules](http://docs.appcelerator.com/titanium/latest/#!/guide/Using_Titanium_Modules) document for instructions on getting
started with using this module in your application.

## Requirements

The Google AdMob Ads SDK has the following requirements:

* An AdMob site ID.
* Xcode 5.1 or later.
* Runtime of iOS 5.0 or later.

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

## Functions

### Ti.Admob.createView({...})

Creates and returns a [Ti.Admob.View][] object which displays ads.

#### Arguments

parameters[object]: a dictionary object of properties defined in [Ti.Admob.View][].

#### Example:

	Admob.createView({
		top: 0, left: 0,
		width: 320, height: 50,
		adUnitId: '<<YOUR ADD UNIT ID HERE>>', // You can get your own at http: //www.admob.com/
		adBackgroundColor: 'black',
		// You can get your device's id for testDevices by looking in the console log after the app launched
		testDevices: [Admob.SIMULATOR_ID],
		dateOfBirth: new Date(1985, 10, 1, 12, 1, 1),
		gender: 'male',
		keywords: ''
	});

## Usage

See example.

## Author

Jeff Haynie, Stephen Tramer, Jasper Kennis, and Jon Alter

## Module History

View the [change log](changelog.html) for this module.

## Feedback and Support

Please direct all questions, feedback, and concerns to [info@appcelerator.com](mailto:info@appcelerator.com?subject=iOS%20Admob%20Module).

## License

Copyright(c) 2010-2014 by Appcelerator, Inc. All Rights Reserved. Please see the LICENSE file included in the distribution for further details.

[Ti.Admob.View]: view.html