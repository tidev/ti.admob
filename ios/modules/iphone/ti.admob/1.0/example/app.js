var win = Ti.UI.createWindow({
    backgroundColor: 'white'
});

Titanium.Admob = Ti.Admob = require('ti.admob');

win.add(Ti.Admob.createView({
    top: 20, left: 10, right: 10,
    publisherId: 'a14e4acdd01df4f', // You can get your own at http: //www.admob.com/
    adBackgroundColor: 'black',
    primaryTextColor: 'blue',
    secondaryTextColor: 'green',
    testing: true,
    postalCode: '60187',
    areaCode: '630',
    dateOfBirth: new Date(1985, 10, 1, 12, 1, 1),
    gender: 'male',
    keywords: '',
    searchString: ''
}));

win.add(Ti.UI.createLabel({
    text: 'Play around with the ad!',
    bottom: 40,
    height: 'auto', width: 'auto'
}));

win.open();