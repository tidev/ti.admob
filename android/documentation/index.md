# admob Module

## Description

Allows for the display of AdMob in Titanium Android applications. 

Please note that if your androidManifest has screen support set to: android:anyDensity="false", any banner ads will display too small on high density devices. 
It is not clear at this point if this is a bug with adMob or Titanium. 
In any event, you will either need to NOT set your screen support -- or set android:anyDensity="true" and adjust your app layout accordingly

## Accessing the admob Module

To access this module from JavaScript, you would do the following:

1. Add this to the <modules /> node in tiapp.xml: 

<modules>
    <module platform="android">ti.admob</module>
</modules>

2. Add this to the <android /> node in tiapp.xml: 

<android>
    <tool-api-level>14</tool-api-level>
</android>

3. Add this to your javascript:	var Admob = require('ti.admob');

4. The "Admob" variable is now a reference to the Module object.	

# Reference

## createAdMobView({options:object})

returns a view with an ad initialized by default

### Usage
var adMobView = Admob.createAdMobView({
    publisherId: <your publisher id>",
    testing:false, // default is false
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

## admob.AD_RECEIVED

returns the constant for AD_RECEIVED -- for use in an event listener

### Usage
adMobView.addEventListener(admob.AD_RECEIVED,function(){
    alert("ad was just received");
});

## admob.AD_NOT_RECEIVED

returns the constant for AD_NOT_RECEIVED -- for use in an event listener

### Usage
adMobView.addEventListener(admob.AD_NOT_RECEIVED,function(){
    alert("ad was not received");
});


## AdMobView.requestAd();

calls for a new ad if needed

### Usage

adMobView.requestAd();

## AdMobView.requestTestAd();

calls for a test ad if needed. This works independently from the testing flag above

### Usage

adMobView.requestTestAd();

## Module History

View the [change log](changelog.html) for this module.

# Author

Brian Kurzius | bkurzius@gmail.com

# License

Apache License
