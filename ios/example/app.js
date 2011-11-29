var Admob = require('ti.admob');
var win = Ti.UI.createWindow({
    backgroundColor: 'white'
});

/*
 We'll make two ads. This first one doesn't care about where the user is located.
 */
var ad;
win.add(ad = Admob.createView({
    top: 0, left: 0,
    width: 320, height: 50,
    publisherId: '<<YOUR PUBLISHER ID HERE>>', // You can get your own at http: //www.admob.com/
    adBackgroundColor: 'black',
    testing: true,
    dateOfBirth: new Date(1985, 10, 1, 12, 1, 1),
    gender: 'male',
    keywords: ''
}));
ad.addEventListener('didReceiveAd', function() {
    alert('Did receive ad!');
});
ad.addEventListener('didFailToReceiveAd', function() {
    alert('Failed to receive ad!');
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

/*
 And we'll try to get the user's location for this second ad!
 */
Ti.Geolocation.accuracy = Ti.Geolocation.ACCURACY_BEST;
Ti.Geolocation.distanceFilter = 0;
Ti.Geolocation.purpose = 'To show you local ads, of course!';
Ti.Geolocation.getCurrentPosition(function reportPosition(e) {
    if (!e.success || e.error) {
        // aw, shucks...
    }
    else {
        win.add(Admob.createView({
            top: 100, left: 0,
            width: 320, height: 50,
            publisherId: '<<YOUR PUBLISHER ID HERE>>', // You can get your own at http: //www.admob.com/
            adBackgroundColor: 'black',
            testing: true,
            dateOfBirth: new Date(1985, 10, 1, 12, 1, 1),
            gender: 'female',
            keywords: '',
            location: e.coords
        }));
    }
});

win.add(Ti.UI.createLabel({
    text: 'Loading the ads now! ' +
        'Note that there may be a several minute delay ' +
        'if you have not viewed an ad in over 24 hours.',
    bottom: 40,
    height: 'auto', width: 'auto'
}));
win.open();