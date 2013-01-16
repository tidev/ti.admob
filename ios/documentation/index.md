# Ti.Admob Module

## Description

Shows ads from Admob.

## Getting Started

View the [Using Titanium Modules](http://docs.appcelerator.com/titanium/latest/#!/guide/Using_Titanium_Modules) document for instructions on getting
started with using this module in your application.

## Requirements

The Google AdMob Ads SDK has the following requirements:

* iOS version 4.3 or later
* Xcode 4.5 or later

The SDK also requires that the following frameworks are available when you build your application:

* StoreKit
* AudioToolbox
* MessageUI
* SystemConfiguration
* CoreGraphics
* AdSupport

## Accessing the Ti.Admob Module

To access this module from JavaScript, you would do the following:

	var Admob = require('ti.admob');

## Doubleclick for Publishers Developer Docs
<https://developers.google.com/mobile-ads-sdk/>

## Functions

### Ti.Admob.createView({...})

Creates and returns a [Ti.Admob.View][] object which displays ads.

#### Arguments

parameters[object]: a dictionary object of properties defined in [Ti.Admob.View][].

#### Example:

	Admob.createView({
	    top: 0, left: 0,
	    width: 320, height: 50,
	    publisherId: '<<YOUR PUBLISHER ID HERE>>',
	    adBackgroundColor: 'black',
	    testing: true,
	    dateOfBirth: new Date(1985, 10, 1, 12, 1, 1),
	    gender: 'male',
	    keywords: ''
	});

## Usage

See example.

## Author

Jeff Haynie, Stephen Tramer

## Module History

View the [change log](changelog.html) for this module.

## Feedback and Support

Please direct all questions, feedback, and concerns to [info@appcelerator.com](mailto:info@appcelerator.com?subject=iOS%20Admob%20Module).

## License

Copyright(c) 2010-2013 by Appcelerator, Inc. All Rights Reserved. Please see the LICENSE file included in the distribution for further details.

[Ti.Admob.View]: view.html