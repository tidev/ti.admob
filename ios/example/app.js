/**
 * 
 * IMPORTANT: You need to add these keys in tiapp.xml
 *
 
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
  
 */


var Admob = require('ti.admob');

var rootWindow = Ti.UI.createWindow({
  backgroundColor: 'white',
  layout: 'vertical'
});

var loadConsentFormBtn = Ti.UI.createButton({
  title: 'Load Consent Form',
  font: {
    fontSize: "20sp"
  },
  backgroundColor: "gray",
  color: "white",
  width: "90%",
  top: 70
});

var resetConsentBtn = Ti.UI.createButton({
  title: 'Reset Consent',
  font: {
    fontSize: "20sp"
  },
  backgroundColor: "gray",
  color: "white",
  width: "90%",
  top: 20
});

rootWindow.add(loadConsentFormBtn);
rootWindow.add(resetConsentBtn);

loadConsentFormBtn.addEventListener("click", loadConsentForm);
resetConsentBtn.addEventListener("click", resetConsent);
rootWindow.addEventListener('open', onOpen);
rootWindow.open();

function resetConsent() {
  Admob.resetConsent();
  alert("Reset consent done!");
}

function loadConsentForm(e) {
  requestConsent();
}

// check trackingAuthorizationStatus on iOS >= 14
function checkTrackingAuthorizationStatus() {
  if (parseInt(Ti.Platform.version.split(".")[0]) >= 14 &&
    Admob.trackingAuthorizationStatus === Admob.TRACKING_AUTHORIZATION_STATUS_NOT_DETERMINED
  ) {
    requestTrackingAuthorization();
  } else {
    requestConsent();
  }
}

function requestTrackingAuthorization() {
  function getStatus(e) {
    console.log(e);
    if (e.status === Admob.TRACKING_AUTHORIZATION_STATUS_NOT_DETERMINED) {
      console.log('TRACKING_AUTHORIZATION_STATUS_NOT_DETERMINED');
    } else if (e.status === Admob.TRACKING_AUTHORIZATION_STATUS_RESTRICTED) {
      console.log('TRACKING_AUTHORIZATION_STATUS_RESTRICTED');
    } else if (e.status === Admob.TRACKING_AUTHORIZATION_STATUS_DENIED) {
      console.log('TRACKING_AUTHORIZATION_STATUS_DENIED');
    } else if (e.status === Admob.TRACKING_AUTHORIZATION_STATUS_AUTHORIZED) {
      console.log('TRACKING_AUTHORIZATION_STATUS_AUTHORIZED');
    } else {
      console.log('TRACKING_AUTHORIZATION_STATUS_NOT_DETERMINED');
    }
  }
  Admob.requestTrackingAuthorization({
    callback: e => {
      getStatus(e);
      if (e.status === Admob.TRACKING_AUTHORIZATION_STATUS_AUTHORIZED) {
        Ti.API.info("Admob.TRACKING_AUTHORIZATION_STATUS_AUTHORIZED, enable personalized ads in ads mediation too")
        Admob.setInMobi_updateGDPRConsent(true);
        Admob.setAdvertiserTrackingEnabled(true);
      }
      requestConsent();
    }
  });
};

function requestConsent() {
  console.log("request consent");
  Admob.requestConsentInfoUpdateWithParameters({
    testDeviceIdentifiers: ['YOUR-TEST-DEVICE-HASHED-ID'],
    geography: Admob.DEBUG_GEOGRAPHY_EEA, // `Admob.DEBUG_GEOGRAPHY_NOT_EEA` or `Admob.DEBUG_GEOGRAPHY_DISABLED`
    tagForUnderAgeOfConsent: false,
    callback: function (e) {
      console.log("requestConsentInfoUpdateWithParameters callback");
      console.log(
        `
        Admob.CONSENT_FORM_STATUS_UNKNOWN: ${Admob.CONSENT_FORM_STATUS_UNKNOWN};
        Admob.CONSENT_FORM_STATUS_AVAILABLE: ${Admob.CONSENT_FORM_STATUS_AVAILABLE};
        Admob.CONSENT_FORM_STATUS_UNAVAILABLE: ${Admob.CONSENT_FORM_STATUS_UNAVAILABLE};                   
        `
      );
      console.log(e);
      if (e.success && e.status === Admob.CONSENT_FORM_STATUS_AVAILABLE) {
        console.log("Consent form is available, load it!");
        Admob.loadForm({
          callback: (e) => {
            console.log("Admob.loadConsentForm callback:");
            console.log(
              `
              Admob.CONSENT_STATUS_UNKNOWN: ${Admob.CONSENT_STATUS_UNKNOWN};
              Admob.CONSENT_STATUS_REQUIRED: ${Admob.CONSENT_STATUS_REQUIRED};
              Admob.CONSENT_STATUS_NOT_REQUIRED: ${Admob.CONSENT_STATUS_NOT_REQUIRED};
              Admob.CONSENT_STATUS_OBTAINED: ${Admob.CONSENT_STATUS_OBTAINED};
              `
            )
            console.log(e);
            if (e.dismissError || e.loadError) {
              alert(e.dismissError || e.loadError);
              return;
            }
            // If the status is "obtained" (freshly granted) or not required (already granted) continue
            if ([Admob.CONSENT_STATUS_NOT_REQUIRED, Admob.CONSENT_STATUS_OBTAINED].includes(e.status)) {
              if (Admob.canShowAds()){
                openTestAdsWin();
              } else {
                alert('You have not granted at least the minimum requirements to show ads!' +
                  'No fear! You can buy an in-app purchase to use the app without ads :)');
              }                
            } else {
              alert('Not ready to show ads! Status = ' + e.status);
            }
          }
        })
      } else {
        console.log("No consent form is available");
        openTestAdsWin();
      }
    }
  });
};

function onOpen() {
  checkTrackingAuthorizationStatus();
}



/////////////
/***
 * TEST ADS WIN
 */

function openTestAdsWin() {


  var testAdsWin = Ti.UI.createWindow({
    backgroundColor: 'white'
  });

  var view = Ti.UI.createView({
    layout: "vertical",
    top: 50
  });
  var label = Ti.UI.createLabel({
    text: 'Loading the ads now! Note that there may be a several minute delay if you have not viewed an ad in over 24 hours.\n\n' +
          'Resume the app to show OpenApp ad',
    font: {
      fontSize: "20sp"
    },
    textAlign: 'center',
    color: "black",
    width: "90%",
    top: 20
  });
  var interstitialButton = Ti.UI.createButton({
    title: 'Load interstitial Ad',
    font: {
      fontSize: "20sp"
    },
    backgroundColor: "gray",
    color: "white",
    width: "90%",
    top: 20
  });
  var rewardedVideoButton = Ti.UI.createButton({
    title: 'Load Rewarded Video Ad',
    font: {
      fontSize: "20sp"
    },
    backgroundColor: "gray",
    color: "white",
    width: "90%",
    top: 20
  });
  var closeWin = Ti.UI.createButton({
    title: 'Close win',
    font: {
      fontSize: "20sp"
    },
    backgroundColor: "gray",
    color: "white",
    width: "90%",
    top: 40
  });

  interstitialButton.addEventListener("click", () => {
    showInterstitial();
  });
  rewardedVideoButton.addEventListener("click", () => {
    showRewarded();
  });
  closeWin.addEventListener("click", () => {
    testAdsWin.close();
  });

  view.add(label);
  view.add(interstitialButton);
  view.add(rewardedVideoButton);
  view.add(closeWin);
  testAdsWin.add(view);

  /* Banner ads */
  var bannerAdView = Admob.createView({
    height: 200,
    bottom: 50,
    adType: Admob.AD_TYPE_BANNER,
    adUnitId: 'ca-app-pub-3940256099942544/2934735716', // You can get your own at http: //www.admob.com/
    adBackgroundColor: 'black',
    contentURL: 'https://admob.com', // URL string for a webpage whose content matches the app content.
    requestAgent: 'Titanium Mobile App', // String that identifies the ad request's origin.
    extras: {
      'npa': !Admob.canShowPersonalizedAds(),
      'version': 1.0,
      'name': 'My App'
    }, // Object of additional infos
    tagForChildDirectedTreatment: false, // https://developers.google.com/admob/ios/targeting#child-directed_setting for more infos
    tagForUnderAgeOfConsent: false, //https://developers.google.com/admob/ios/targeting#users_under_the_age_of_consent for more infos
    maxAdContentRating: Admob.MAX_AD_CONTENT_RATING_GENERAL, // https://developers.google.com/admob/ios/targeting#ad_content_filtering for more infos
    keywords: ['keyword1', 'keyword2']
  });

  bannerAdView.addEventListener('didReceiveAd', function (e) {
    console.log(e)
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
  bannerAdView.addEventListener('didDismissScreen', function (e) {
    Ti.API.info('BannerAdView - Dismissed screen: ' + e.adUnitId);
  });


  /* interstitial Ads */
  var interstitialAd = Admob.createView({
    debugEnabled: false, // If enabled, a dummy value for `adUnitId` will be used to test
    adType: Admob.AD_TYPE_INTERSTITIAL,
    adUnitId: 'ca-app-pub-3940256099942544/4411468910', // You can get your own at http: //www.admob.com/
    keywords: ['keyword1', 'keyword2'],
    extras: {
      'npa': !Admob.canShowPersonalizedAds(),
      'version': 1.0,
      'name': 'My App'
    }, // Object of additional infos
    visible: false, // If true, covers the win when added and can't tap nothing
    tagForChildDirectedTreatment: false, // https://developers.google.com/admob/ios/targeting#child-directed_setting for more infos
    tagForUnderAgeOfConsent: false, //https://developers.google.com/admob/ios/targeting#users_under_the_age_of_consent for more infos
    maxAdContentRating: Admob.MAX_AD_CONTENT_RATING_GENERAL, // https://developers.google.com/admob/ios/targeting#ad_content_filtering for more infos
  });
  

  interstitialAd.addEventListener('didReceiveAd', function (e) {
    Ti.API.info('interstitialAd - Did receive ad: ' + e.adUnitId);
    interstitialButton.title = "Show interstitial Ad";
    enableInterstitialButton();
  });
  interstitialAd.addEventListener('didFailToReceiveAd', function (e) {
    Ti.API.error('interstitialAd - Failed to receive ad: ' + e.error);
    interstitialButton.title = "Load interstitial Ad";
    testAdsWin.remove(interstitialAd);
    enableInterstitialButton();
  });  
  interstitialAd.addEventListener('didDismissScreen', function (e) {
    Ti.API.info('interstitialAd - Dismissed screen: ' + e.adUnitId);
    testAdsWin.remove(interstitialAd);
    enableInterstitialButton();
  });
  interstitialAd.addEventListener('willDismissScreen', function (e) {
    Ti.API.info('interstitialAd - willDismissScreen: ' + e.adUnitId);
    enableInterstitialButton();
  });
  interstitialAd.addEventListener('didRecordImpression', function (e) {
    Ti.API.info('interstitialAd- didRecordImpression: ' + e.adUnitId);
    enableInterstitialButton();
  });
  interstitialAd.addEventListener('didRecordClick', function (e) {
    Ti.API.info('interstitialAd - didRecordClick: ' + e.adUnitId);
  });


  function showInterstitial() {
    if (interstitialButton.title === "Load interstitial Ad") {
      console.log("showInterstitial --> LOAD");
      testAdsWin.add(interstitialAd);
    } else {
      console.log("showInterstitial --> SHOW");
      interstitialAd.showInterstitial()
      interstitialButton.title = "Load interstitial Ad";
    }
    return;
  };

  function disableInterstitialButton() {
    interstitialButton.enabled = false;
  }

  function enableInterstitialButton() {
    interstitialButton.enabled = true;
  }

  /* Rewarded Video Ads */

  var rewardedVideo = Admob.createView({
    debugEnabled: false,
    adType: Admob.AD_TYPE_REWARDED_VIDEO,
    adUnitId: 'ca-app-pub-3940256099942544/1712485313',
    extras: {
      'npa': !Admob.canShowPersonalizedAds(),
      'version': 1.0,
      'name': 'My App'
    }, // Object of additional infos
    tagForChildDirectedTreatment: false, // https://developers.google.com/admob/ios/targeting#child-directed_setting for more infos
    tagForUnderAgeOfConsent: false, //https://developers.google.com/admob/ios/targeting#users_under_the_age_of_consent for more infos
    maxAdContentRating: Admob.MAX_AD_CONTENT_RATING_GENERAL, // https://developers.google.com/admob/ios/targeting#ad_content_filtering for more infos
  });
 
  rewardedVideo.addEventListener('didRewardUser', function (reward) {
    Ti.API.debug('rewardedVideo - didRewardUser');
    Ti.API.debug(`Received reward! type: ${reward.type}, amount: ${reward.amount}`);
    console.log(reward);
    disableRewardedVideoButton();
    alert("Well! Amount earned: " + reward.amount);
  });
  
  rewardedVideo.addEventListener('didReceiveAd', function (e) {
    Ti.API.info('rewardedVideo - Did receive ad: ' + e.adUnitId);
    console.log(e);
    enableRewardedVideoButton();
  });
  rewardedVideo.addEventListener('didFailToReceiveAd', function (e) {
    Ti.API.debug('rewardedVideo - Rewarded video ad failed to load: ' + e.error);
    disableRewardedVideoButton();
  });  
  rewardedVideo.addEventListener('didFailToReceiveAd', function (e) {
    Ti.API.error('rewardedVideo - Failed to receive ad: ' + e.error);
    disableRewardedVideoButton();
  }); 
  rewardedVideo.addEventListener('didDismissScreen', function (e) {
    Ti.API.info('rewardedVideo - Dismissed screen: ' + e.adUnitId);
    disableRewardedVideoButton();
  });
  rewardedVideo.addEventListener('willDismissScreen', function (e) {
    Ti.API.info('rewardedVideo - willDismissScreen: ' + e.adUnitId);
    enableRewardedVideoButton();
  });
  rewardedVideo.addEventListener('didRecordImpression', function (e) {
    Ti.API.info('rewardedVideo - didRecordImpression');
    console.log(e);
    disableRewardedVideoButton();
  });
  rewardedVideo.addEventListener('didRecordClick', function (e) {
    Ti.API.info('rewardedVideo - didRecordClick: ' + e.adUnitId);
  });
  

  function showRewarded() {
    if (rewardedVideoButton.title === "Load Rewarded Video Ad") {
      console.log("showRewarded --> LOAD");
      rewardedVideo.receive();
    } else {
      console.log("showRewarded --> SHOW");
      rewardedVideoButton.title = "Load Rewarded Video Ad";
      rewardedVideo.showRewardedVideo();
    }
  };

  function disableRewardedVideoButton() {
    //rewardedVideoButton.enabled = false;	
    setTimeout(() => {
      rewardedVideoButton.title = "Load Rewarded Video Ad";
    }, 10);
  }

  function enableRewardedVideoButton() {
    setTimeout(() => {
      rewardedVideoButton.title = 'Show Rewarded Video Ad';
    }, 10);
  }

  /* OpenApp Ad */

  let appOpenAd;

  function loadOpenAd() {
    const reload_max_tries_case_error = 4;
    let reload_max_tries = 0;

    function reloadAppOpenAd() {
      if (reload_max_tries < reload_max_tries_case_error) {
        setTimeout(() => {
          appOpenAd.requestAppOpenAd();
        }, 10000);
      }
      reload_max_tries += 1;
    }

    appOpenAd = Admob.createView({
      debugEnabled: false,
      adType: Admob.AD_TYPE_APP_OPEN,
      adUnitId: 'ca-app-pub-3940256099942544/5662855259', // You can get your own at http: //www.admob.com/
      extras: {
        'npa': !Admob.canShowPersonalizedAds(),
        'version': 1.0,
        'name': 'My App'
      }, // Object of additional infos
      tagForChildDirectedTreatment: false, // https://developers.google.com/admob/ios/targeting#child-directed_setting for more infos
      tagForUnderAgeOfConsent: false, //https://developers.google.com/admob/ios/targeting#users_under_the_age_of_consent for more infos
      maxAdContentRating: Admob.MAX_AD_CONTENT_RATING_GENERAL, // https://developers.google.com/admob/ios/targeting#ad_content_filtering for more infos
    });

    // appOpenAd custom events
    appOpenAd.addEventListener('didReceiveAd', function (e) {
      Ti.API.debug('appOpenAd - didReceiveAd: Did receive ad: ' + e.adUnitId);
      Ti.API.debug(e);
      reload_max_tries = 0;
      Titanium.App.Properties.setDouble('appOpenAdLoadTime', (new Date().getTime()));
    });   
    appOpenAd.addEventListener('didFailToShowAd', function (e) {
      Ti.API.error('appOpenAd - Failed to show: ' + e.error);
      reloadAppOpenAd();
    });

    // appOpenAd AdMob avents
    appOpenAd.addEventListener('didRecordClick', function (e) {
      Ti.API.debug('appOpenAd - didRecordClick: ' + e.adUnitId);
    });
    appOpenAd.addEventListener('didFailToReceiveAd', function (e) {
      Ti.API.error('appOpenAd - Failed to receive ad: ' + e.error);
      reloadAppOpenAd();
    });
    appOpenAd.addEventListener('didDismissScreen', function (e) {
      Ti.API.debug('appOpenAd - Dismissed screen: ' + e.adUnitId);
      Titanium.App.Properties.setDouble('lastTimeAppOpenAdWasShown', (new Date().getTime()));
      appOpenAd.requestAppOpenAd();
    });
    appOpenAd.addEventListener('willPresentScreen', function (e) {
      Ti.API.debug('appOpenAd - willPresentScreen: ' + e.adUnitId);
    });
    appOpenAd.addEventListener('willDismissScreen', function (e) {
      Ti.API.debug('appOpenAd - willDismissScreen: ' + e.adUnitId);
    });
    appOpenAd.addEventListener('didRecordImpression', function (e) {
      Ti.API.debug('appOpenAd- didRecordImpression: ' + e.adUnitId);
    });

    console.log("appOpenAd.receive();")
    appOpenAd.receive();    

  }

  function resumeOpenAd() {
    let currentTime = (new Date().getTime());
    let loadTime = Titanium.App.Properties.getDouble('appOpenAdLoadTime', currentTime);
    let lastTimeAppOpenAdWasShown = Titanium.App.Properties.getDouble('lastTimeAppOpenAdWasShown', 1);

    if ((currentTime - loadTime) < 14400000) { // then less than 4 hours elapsed.
      if ((currentTime - lastTimeAppOpenAdWasShown) > 600000) { // then more than 10 minutes elapsed after the last Ad showed.        
        console.log("appOpenAd.showAppOpenAd()!")
        setTimeout(() => {
          try {
            appOpenAd.showAppOpenAd();
          } catch (error) {
            Ti.API.error(error);
            Titanium.App.removeEventListener('resume', resumeOpenAd);
            setTimeout(() => {
              loadOpenAd();
              Titanium.App.addEventListener('resume', resumeOpenAd);
            }, 500);
          }
        }, 500);
      } else {
        Titanium.API.warn("You have showned an AppOpenAd less than 10 minutes ago. You should wait!");
      }
    } else {
      Titanium.API.warn("The AppOpenAd was requested more than 4 hours ago and has expired! You should load another one.");
      Titanium.App.removeEventListener('resume', resumeOpenAd);
      setTimeout(() => {
        loadOpenAd();
        Titanium.App.addEventListener('resume', resumeOpenAd);
      }, 500);
    }
  }

  loadOpenAd();
  Titanium.App.addEventListener('resume', resumeOpenAd);

  testAdsWin.addEventListener('close', () => {
    Titanium.App.removeEventListener('resume', resumeOpenAd);    
  });

  testAdsWin.open();
  setTimeout(() => {
    console.log("Add banner!!!!")
    testAdsWin.add(bannerAdView);
  }, 1000);
}