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
    top: 0,
    debugEnabled: true, // If enabled, a dummy value for `adUnitId` will be used to test
    adType: Admob.AD_TYPE_BANNER,
    adUnitId: '<<YOUR ADD UNIT ID HERE>>', // You can get your own at http: //www.admob.com/
    adBackgroundColor: 'black',
    testDevices: [Admob.SIMULATOR_ID], // You can get your device's id by looking in the console log
    dateOfBirth: new Date(1985, 10, 1, 12, 1, 1),
    gender: Admob.GENDER_MALE, // GENDER_MALE or GENDER_FEMALE, default: undefined
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

ad1.addEventListener('didReceiveAd', function() {
    alert('Did receive ad!');
});
ad1.addEventListener('didFailToReceiveAd', function(e) {
    alert('Failed to receive ad: ' + e.error);
});
ad1.addEventListener('willPresentScreen', function() {
    alert('Presenting screen!');
});
ad1.addEventListener('willDismissScreen', function() {
    alert('Dismissing screen!');
});
ad1.addEventListener('didDismissScreen', function() {
    alert('Dismissed screen!');
});
ad1.addEventListener('willLeaveApplication', function() {
    alert('Leaving the app!');
});
ad1.addEventListener('didReceiveInAppPurchase', function(e) {
    alert('Did receive an inApp purchase!');
    Ti.API.warn(e);
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
        alert('Did receive ad!');
    });

    ad2.addEventListener('didFailToReceiveAd', function(e) {
        alert('Failed to receive ad: ' + e.error);
    });
});

win.add(btn);

win.add(Ti.UI.createLabel({
    text: 'Loading the ads now! ' +
        'Note that there may be a several minute delay ' +
        'if you have not viewed an ad in over 24 hours.',
    bottom: 40,
    textAlign: 'center'
}));
win.open();
