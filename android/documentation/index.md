# Admob Module

## Description

Allows for the display of AdMob in Titanium Android applications. 

Please note that if your androidManifest has screen support set to: android:anyDensity="false", any banner ads will display too small on high density devices. 
It is not clear at this point if this is a bug with AdMob or Titanium. 
In any event, you will either need to NOT set your screen support -- or set android:anyDensity="true" and adjust your app layout accordingly

## Getting Started

View the [Using Titanium Modules](http://docs.appcelerator.com/titanium/latest/#!/guide/Using_Titanium_Modules) document for instructions on getting
started with using this module in your application.

## Requirements

Add this to the &lt;android /&gt; node in tiapp.xml: 

	<android>
	    <tool-api-level>14</tool-api-level>
	</android>

## Accessing the Admob Module

To access this module from JavaScript, you would do the following (recommended):

	var Admob = require('ti.admob');

The "Admob" variable is now a reference to the Module object.	

## Doubleclick for Publishers Developer Docs
<https://developers.google.com/mobile-ads-sdk/>

## Functions

### number isGooglePlayServicesAvailable()

Returns a number value indicating the availability of Google Play Services which are for push notifications.

Possible values include `SUCCESS`, `SERVICE_MISSING`, `SERVICE_VERSION_UPDATE_REQUIRED`, `SERVICE_DISABLED`, and `SERVICE_INVALID`.

### createAdMobView({ . . . })

Returns a view with an ad initialized by default.

#### Arguments

parameters[object]: a dictionary object of properties.

#### Example:

	var adMobView = Admob.createView({
	    publisherId: "<<YOUR PUBLISHER ID HERE>>",
	    testing:false, // default is false
	    smartBanner: true, // default is false
	    top: 0, //optional
	    left: 0, // optional
	    right: 0, // optional
	    bottom: 0 // optional
	    adBackgroundColor:"FF8800", // optional
	    backgroundColorTop: "738000", //optional - Gradient background color at top
	    borderColor: "#000000", // optional - Border color
	    textColor: "#000000", // optional - Text color
	    urlColor: "#00FF00", // optional - URL color
	    linkColor: "#0000FF" //optional -  Link text color
	    primaryTextColor: "blue", // deprecated -- now maps to textColor
	    secondaryTextColor: "green" // deprecated -- now maps to linkColor
	});

### Admob.AD_RECEIVED

returns the constant for AD_RECEIVED -- for use in an event listener

#### Example:

	adMobView.addEventListener(Admob.AD_RECEIVED,function(){
	    alert("ad was just received");
	});

### Admob.AD_NOT_RECEIVED

returns the constant for AD_NOT_RECEIVED -- for use in an event listener

#### Example:

	adMobView.addEventListener(Admob.AD_NOT_RECEIVED,function(){
	    alert("ad was not received");
	});

### AdMobView.requestAd();

Calls for a new ad if needed.

#### Example:

	adMobView.requestAd();

### AdMobView.requestTestAd();

Calls for a test ad if needed. This works independently from the testing flag above.

#### Example:

	adMobView.requestTestAd();

## Constants

### number SUCCESS
Returned by `isGooglePlayServicesAvailable` if the connection to Google Play services was successful.

### number SERVICE_MISSING
Returned by `isGooglePlayServicesAvailable` if Google Play services is missing on this device.

### number SERVICE_VERSION_UPDATE_REQUIRED
Returned by `isGooglePlayServicesAvailable` if the installed version of Google Play services is out of date.

### number SERVICE_DISABLED
Returned by `isGooglePlayServicesAvailable` if the installed version of Google Play services has been disabled on this device.

### number SERVICE_INVALID
Returned by `isGooglePlayServicesAvailable` if the version of the Google Play services installed on this device is not authentic.


## Module History

View the [change log](changelog.html) for this module.

## Feedback and Support

Please direct all questions, feedback, and concerns to [info@appcelerator.com](mailto:info@appcelerator.com?subject=Android%20Admob%20Module).

## Author

Brian Kurzius | bkurzius@gmail.com

## License
Copyright 2011 Brian Kurzius, Studio Classics. Please see the LICENSE file included in the distribution for further details.
