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
      if (Admob.TRACKING_AUTHORIZATION_STATUS_AUTHORIZED) {
        Ti.info("Admob.TRACKING_AUTHORIZATION_STATUS_AUTHORIZED, enable personalized ads in ads mediation too")
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
    testDeviceIdentifiers: [Admob.SIMULATOR_ID, 'YOUR-TEST-DEVICE-HASHED-ID'],
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
              openTestAdsWin();
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
    top: 20
  });
  var label = Ti.UI.createLabel({
    text: 'Loading the ads now! Note that there may be a several minute delay if you have not viewed an ad in over 24 hours.',
    font: {
      fontSize: "20sp"
    },
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
      'version': 1.0,
      'name': 'My App'
    }, // Object of additional infos
    tagForChildDirectedTreatment: false, // http:///business.ftc.gov/privacy-and-security/childrens-privacy for more infos
    keywords: ['keyword1', 'keyword2']
  });

  bannerAdView.addEventListener('didReceiveAd', function (e) {
    console.log(e)
    Ti.API.info('BannerAdView - Did receive ad: ' + e.adUnitId + '!');
  });
  bannerAdView.addEventListener('didFailToReceiveAd', function (e) {
    Ti.API.error('BannerAdView - Failed to receive ad: ' + e.error);
  });

  bannerAdView.addEventListener('willPresentScreen', function (e) {
    Ti.API.error('BannerAdView - willPresentScreen');
  });
  bannerAdView.addEventListener('willDismissScreen', function () {
    Ti.API.info('BannerAdView - willDismissScreen!');
  });
  bannerAdView.addEventListener('didDismissScreen', function () {
    Ti.API.info('BannerAdView - Dismissed screen!');
  });
  bannerAdView.addEventListener('didPresentScreen', function (e) {
    Ti.API.info('BannerAdView - Presenting screen!' + e.adUnitId);
  });


  /* interstitial Ads */
  var interstitialAd = Admob.createView({
    debugEnabled: false, // If enabled, a dummy value for `adUnitId` will be used to test
    adType: Admob.AD_TYPE_INTERSTITIAL,
    adUnitId: 'ca-app-pub-3940256099942544/4411468910', // You can get your own at http: //www.admob.com/
    keywords: ['keyword1', 'keyword2'],
    extras: {
      'version': 1.0,
      'name': 'My App'
    }, // Object of additional infos
    visible: false // If true, covers the win when added and can't tap nothing
  });
  interstitialAd.addEventListener('adloaded', function (e) {
    Ti.API.info('interstitialAd - adloaded: Did receive ad!');
    console.log(e);
    interstitialButton.title = "Show interstitial Ad";
    enableInterstitialButton();
  });

  interstitialAd.addEventListener('didReceiveAd', function (e) {
    Ti.API.info('interstitialAd - Did receive ad!');
    interstitialButton.title = "Show interstitial Ad";
    console.log(e);
    enableInterstitialButton();
  });
  interstitialAd.addEventListener('didFailToReceiveAd', function (e) {
    Ti.API.error('interstitialAd - Failed to receive ad: ' + e.error);
    interstitialButton.title = "Load interstitial Ad";
    testAdsWin.remove(interstitialAd);
    enableInterstitialButton();
  });
  interstitialAd.addEventListener('didPresentScreen', function (e) {
    Ti.API.info('interstitialAd - didPresentScreen: ' + e.adUnitId);
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
    Ti.API.info('interstitialAd- didRecordImpression');
    console.log(e);
    enableInterstitialButton();
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
      'version': 1.0,
      'name': 'My App'
    } // Object of additional infos
  });

  rewardedVideo.addEventListener('adloaded', function (e) {
    Ti.API.debug('rewardedVideo - Rewarded video loaded!');
    console.log(e);
    enableRewardedVideoButton();
  });
  rewardedVideo.addEventListener('adrewarded', function (reward) {
    Ti.API.debug('rewardedVideo -adrewarded');
    Ti.API.debug(`Received reward! type: ${reward.type}, amount: ${reward.amount}`);
    console.log(reward);
    disableRewardedVideoButton();
    alert("Well! Amount earned: " + reward.amount);
  });
  rewardedVideo.addEventListener('adfailedtoload', function (error) {
    Ti.API.debug('rewardedVideo - Rewarded video ad failed to load: ' + error.message);
    disableRewardedVideoButton();
  });
  rewardedVideo.addEventListener('didReceiveAd', function (e) {
    Ti.API.info('rewardedVideo - Did receive ad!');
    console.log(e);
    enableRewardedVideoButton();
  });
  rewardedVideo.addEventListener('didFailToReceiveAd', function (e) {
    Ti.API.error('rewardedVideo - Failed to receive ad: ' + e.error);
    disableRewardedVideoButton();
  });
  rewardedVideo.addEventListener('didPresentScreen', function (e) {
    Ti.API.info('rewardedVideo - didPresentScreen: ' + e.adUnitId);
    enableRewardedVideoButton();
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

  testAdsWin.open();
  setTimeout(() => {
    console.log("Add banner!!!!")
    testAdsWin.add(bannerAdView);
  }, 1000);
}