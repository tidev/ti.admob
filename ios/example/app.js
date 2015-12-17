var Admob = require('ti.admob');
var win = Ti.UI.createWindow({
    backgroundColor: 'white',
    orientationModes: [Ti.UI.PORTRAIT, Ti.UI.LANDSCAPE_LEFT, Ti.UI.LANDSCAPE_RIGHT]
});

/*
 We'll make two ads. This first one doesn't care about where the user is located.
 */
var ad;
win.add(ad = Admob.createView({
    top: 0,
    height: 50,
    debugEnabled: true, // If enabled, a dummy value for `adUnitId` will be used to test
    adUnitId: '<<YOUR ADD UNIT ID HERE>>', // You can get your own at http: //www.admob.com/
    adBackgroundColor: 'black',
    testDevices: [Admob.SIMULATOR_ID], // You can get your device's id by looking in the console log
    dateOfBirth: new Date(1985, 10, 1, 12, 1, 1),
    gender: Admob.GENDER_MALE, // GENDER_MALE or GENDER_FEMALE, default: undefined
    contentURL: 'https://admob.com', // URL string for a webpage whose content matches the app content.
    requestAgent: 'Titanium Mobile App', // String that identifies the ad request's origin.
    extras: {  // Object of additional infos
        "version": 1.0,
        "name": "My App"
    },
    tagForChildDirectedTreatment: false, // http:///business.ftc.gov/privacy-and-security/childrens-privacy for more infos
    keywords: ['keyword1', 'keyword2']
}));
ad.addEventListener('didReceiveAd', function() {
    alert('Did receive ad!');
});
ad.addEventListener('didFailToReceiveAd', function(e) {
    alert('Failed to receive ad: ' + e.error);
});
ad.addEventListener('willPresentScreen', function() {
    alert('Presenting screen!');
});
ad.addEventListener('willDismissScreen', function() {
    alert('Dismissing screen!');
});
ad.addEventListener('didDismissScreen', function() {
    alert('Dismissed screen!');
});
ad.addEventListener('willLeaveApplication', function() {
    alert('Leaving the app!');
});
ad.addEventListener('didReceiveInAppPurchase', function(e) {
    alert('Did receive an inApp purchase!');
    Ti.API.warn(e);
});

/*
 And we'll try to get the user's location for this second ad!
 */
Ti.Geolocation.accuracy = Ti.Geolocation.ACCURACY_BEST;
Ti.Geolocation.distanceFilter = 0;
Ti.Geolocation.purpose = 'To show you local ads, of course!';
Ti.Geolocation.getCurrentPosition(function reportPosition(e) {
    if (!e.success || e.error) {
        // aw, shucks...
    } else {
        win.add(Admob.createView({
            top: 100,
            height: 50,
            debugEnabled: true,
            adUnitId: '<<YOUR AD UNIT ID HERE>>', // You can get your own at http: //www.admob.com/
            adBackgroundColor: 'black',
            // You can get your device's id for testDevices by looking in the console log after the app launched
            testDevices: [Admob.SIMULATOR_ID],
            dateOfBirth: new Date(1985, 10, 1, 12, 1, 1),
            gender: Admob.GENDER_FEMALE,
            keywords: ['test1', 'test2'],
            location: e.coords
        }));
    }
});

win.add(Ti.UI.createLabel({
    text: 'Loading the ads now! ' +
        'Note that there may be a several minute delay ' +
        'if you have not viewed an ad in over 24 hours.',
    bottom: 40,
    height: Ti.UI.SIZE,
    width: Ti.UI.SIZE
}));
win.open();
