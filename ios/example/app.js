var Admob = require('ti.admob');
var win = Ti.UI.createWindow({
    backgroundColor: 'white',
    orientationModes: [Ti.UI.PORTRAIT, Ti.UI.LANDSCAPE_LEFT, Ti.UI.LANDSCAPE_RIGHT]
});

/*
 We'll make two ads. This first one doesn't care about where the user is located.
 */
var ad1 = Admob.createView({
    height: 50,
    bottom: 0,
    debugEnabled: true, // If enabled, a dummy value for `adUnitId` will be used to test
    adType: Admob.AD_TYPE_BANNER,
    adUnitId: '<<YOUR ADD UNIT ID HERE>>', // You can get your own at http: //www.admob.com/
    adBackgroundColor: 'black',
    testDevices: [Admob.SIMULATOR_ID], // You can get your device's id by looking in the console log
    dateOfBirth: new Date(1985, 10, 1, 12, 1, 1),
    gender: Admob.GENDER_MALE, // GENDER_MALE, GENDER_FEMALE or GENDER_UNKNOWN, default: GENDER_UNKNOWN
    contentURL: 'https://admob.com', // URL string for a webpage whose content matches the app content.
    requestAgent: 'Titanium Mobile App', // String that identifies the ad request's origin.
    extras: {
       'version': 1.0,
       'name': 'My App'
    }, // Object of additional infos
    tagForChildDirectedTreatment: false, // http:///business.ftc.gov/privacy-and-security/childrens-privacy for more infos
    keywords: ['keyword1', 'keyword2']
});
win.add(ad1);

ad1.addEventListener('didReceiveAd', function(e) {
    Ti.API.info('Did receive ad: ' + e.adUnitId + '!');
});
ad1.addEventListener('didFailToReceiveAd', function(e) {
    Ti.API.error('Failed to receive ad: ' + e.error);
});
ad1.addEventListener('willPresentScreen', function() {
    Ti.API.info('Presenting screen!');
});
ad1.addEventListener('willDismissScreen', function() {
    Ti.API.info('Dismissing screen!');
});
ad1.addEventListener('didDismissScreen', function() {
    Ti.API.info('Dismissed screen!');
});
ad1.addEventListener('willLeaveApplication', function() {
    Ti.API.info('Leaving the app!');
});
ad1.addEventListener('didReceiveInAppPurchase', function(e) {
    Ti.API.info('Did receive an In-App purchase: ' + e.productId + '!');
    Ti.API.info(e);
});

var btn = Ti.UI.createButton({
    title: 'Show interstitial'
});

btn.addEventListener('click', function() {
    var ad2 = Admob.createView({
        debugEnabled: true, // If enabled, a dummy value for `adUnitId` will be used to test
        adType: Admob.AD_TYPE_INTERSTITIAL,
        adUnitId: '<<YOUR ADD UNIT ID HERE>>', // You can get your own at http: //www.admob.com/
        testDevices: [Admob.SIMULATOR_ID], // You can get your device's id by looking in the console log
        dateOfBirth: new Date(1985, 10, 1, 12, 1, 1),
        gender: Admob.GENDER_MALE, // GENDER_MALE or GENDER_FEMALE, default: undefined
        keywords: ['keyword1', 'keyword2']
    });
    ad2.receive();

    ad2.addEventListener('didReceiveAd', function(e) {
        Ti.API.info('Did receive ad!');
    });

    ad2.addEventListener('didFailToReceiveAd', function(e) {
        Ti.API.error('Failed to receive ad: ' + e.error);
    });
});

win.add(btn);

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
