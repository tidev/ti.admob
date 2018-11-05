/**
 * Copyright (c) 2011 by Studio Classics. All Rights Reserved.
 * Author: Brian Kurzius
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */

var win = Titanium.UI.createWindow({
    backgroundColor: "#FFFFFF"
});

// require AdMob
var Admob = require('ti.admob');

// check if google play services are available
var code = Admob.isGooglePlayServicesAvailable();
if (code != Admob.SUCCESS) {
    alert("Google Play Services is not installed/updated/available");
}

// then create an adMob view
var adMobView = Admob.createView({
    adUnitId:"ca-app-pub-3940256099942544/6300978111",
    testing:false, // default is false
    //top: 10, //optional
    //left: 0, // optional
    //right: 0, // optional
    bottom: 0, // optional
    adBackgroundColor:"FF8855", // optional
    backgroundColorTop: "738000", //optional - Gradient background color at top
    borderColor: "#000000", // optional - Border color
    textColor: "#000000", // optional - Text color
    urlColor: "#00FF00", // optional - URL color
    linkColor: "#0000FF" //optional -  Link text color
    //primaryTextColor: "blue", // deprecated -- now maps to textColor
    //secondaryTextColor: "green" // deprecated -- now maps to linkColor
    
});

// Create an Interstitial ad with a testing AdUnitId
var interstitialAd = Admob.createInterstitialAd({ adUnitId:"ca-app-pub-3940256099942544/1033173712" });

// Add all listeners for the add.
interstitialAd.addEventListener(Admob.AD_CLOSED, function () {
    Ti.API.info('Interstitial Ad closed!');
    // Once the Interstitial ad is closed disable the button
    // until another add is successfully loaded.
    showInterstitialAdButton.enabled = false;
    showInterstitialAdButton.touchEnabled = false;
    interstitialAd.load();
});
interstitialAd.addEventListener(Admob.AD_RECEIVED, function () {
    // When a new Interstitial ad is loaded, enabled the button.
    Ti.API.info('Interstitial Ad loaded!');
    showInterstitialAdButton.enabled = true;
    showInterstitialAdButton.touchEnabled = true;
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

//listener for adReceived
adMobView.addEventListener(Admob.AD_RECEIVED,function(){
   // alert("ad received");
   Ti.API.info("ad received");
});

adMobView.addEventListener('ad_clicked', function(){
   // alert("ad received");
   Ti.API.info("ad clicked");
});

//listener for adNotReceived
adMobView.addEventListener(Admob.AD_NOT_RECEIVED,function(){
    //alert("ad not received");
     Ti.API.info("ad not received");
});


var adRequestBtn = Ti.UI.createButton({
    title:"Request an ad",
    top:"10%",
    height: "10%",
    width: "80%"
});

adRequestBtn.addEventListener("click",function(){
    adMobView.requestAd();
});

var adRequestBtn2 = Ti.UI.createButton({
    title: "Request a test ad",
    top: "25%",
    height: "10%",
    width: "80%"
});

adRequestBtn2.addEventListener("click",function(){
    adMobView.requestTestAd();
});

var showInterstitialAdButton = Ti.UI.createButton({
    title: "Show Interstitial",
    top: "40%",
    height: "10%",
    width: "80%"
})

var getAAID = Ti.UI.createButton({
    title: "Get AAID",
    top: "55%",
    height: "10%",
    width: "80%"
});

var getIsAdTrackingLimited = Ti.UI.createButton({
    title: "Is Ad tracking limited",
    top: "70%",
    height: "10%",
    width: "80%"
});

getAAID.addEventListener('click', function() {
    Admob.getAndroidAdId(function (data) {
        Ti.API.info('AAID is ' + data.aaID);
    });
});

getIsAdTrackingLimited.addEventListener('click', function() {
    Admob.isLimitAdTrackingEnabled(function (data) {
        Ti.API.info('Ad tracking is limited: ' + data.isLimitAdTrackingEnabled);
    });
});

showInterstitialAdButton.addEventListener('click', function () {
    interstitialAd.show();
});

win.add(adMobView);
win.add(adRequestBtn);
win.add(adRequestBtn2);
win.add(showInterstitialAdButton);
win.add(getAAID);
win.add(getIsAdTrackingLimited)
win.open();