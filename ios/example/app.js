var Admob = require('ti.admob');

var win = Ti.UI.createWindow({
    backgroundColor: 'white',
    orientationModes: [Ti.UI.PORTRAIT, Ti.UI.LANDSCAPE_LEFT, Ti.UI.LANDSCAPE_RIGHT]
});

win.addEventListener('postlayout', function onPostLayout(event) {
  win.removeEventListener('postlayout', onPostLayout);
  bannerAdView.bottom = event.source.safeAreaPadding.bottom;
});

/* Banner ads */

var bannerAdView = Admob.createView({
    height: 50,
    bottom: 0,
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
win.add(bannerAdView);

bannerAdView.addEventListener('didReceiveAd', function(e) {
    Ti.API.info('Did receive ad: ' + e.adUnitId + '!');
});
bannerAdView.addEventListener('didFailToReceiveAd', function(e) {
    Ti.API.error('Failed to receive ad: ' + e.error);
});
bannerAdView.addEventListener('didPresentScreen', function() {
    Ti.API.info('Presenting screen!');
});
bannerAdView.addEventListener('willDismissScreen', function() {
    Ti.API.info('Dismissing screen!');
});
bannerAdView.addEventListener('didDismissScreen', function() {
    Ti.API.info('Dismissed screen!');
});

/* Interstitial Ads */

var interstitialAdButton = Ti.UI.createButton({
    title: 'Show interstitial ad'
});

interstitialAdButton.addEventListener('click', function() {
    var ad2 = Admob.createView({
        debugEnabled: true, // If enabled, a dummy value for `adUnitId` will be used to test
        adType: Admob.AD_TYPE_INTERSTITIAL,
        adUnitId: '<<YOUR ADD UNIT ID HERE>>', // You can get your own at http: //www.admob.com/
        keywords: ['keyword1', 'keyword2']
    });
    ad2.receive();
  
    ad2.addEventListener('adloaded', function() {
      ad2.showInterstitial();
    });
  
    ad2.addEventListener('didReceiveAd', function(e) {
        Ti.API.info('Did receive ad!');
    });

    ad2.addEventListener('didFailToReceiveAd', function(e) {
        Ti.API.error('Failed to receive ad: ' + e.error);
    });
});

win.add(interstitialAdButton);

/* Rewarded Video Ads */

var rewardedVideoButton = Ti.UI.createButton({
    title: 'Show Rewarded Video Ad',
    center: {
        y: '55%'
    }
});

rewardedVideoButton.addEventListener('click', function() {
    rewardedVideoButton.title = 'Loading Rewarded Video ...';

    var rewardedVideo = Admob.createView({
        debugEnabled: true,
        adType: Admob.AD_TYPE_REWARDED_VIDEO,
    });
    rewardedVideo.receive();

    rewardedVideo.addEventListener('adloaded', function() {
        rewardedVideo.showRewardedVideo();
    });
    rewardedVideo.addEventListener('adrewarded', function (reward) {
        Ti.API.debug(`Received reward! type: ${reward.type}, amount: ${reward.amount}`);
        // pre load next rewarded video
        // rewardedVideo.loadRewardedVideo('ad-unit-id');
    });
    rewardedVideo.addEventListener('adclosed', function() {
        Ti.API.debug('No gold for you!');
    });
    rewardedVideo.addEventListener('adfailedtoload', function(error) {
        Ti.API.debug('Rewarded video ad failed to load: ' + error.message);
    });
});
win.add(rewardedVideoButton);

win.add(Ti.UI.createLabel({
    text: 'Loading the ads now! ' +
        'Note that there may be a several minute delay ' +
        'if you have not viewed an ad in over 24 hours.',
    top: 40,
    textAlign: 'center'
}));

win.open();
