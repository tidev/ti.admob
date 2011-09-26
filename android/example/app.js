// This is a test harness for your module
// You should do something interesting in this harness 
// to test out the module and to provide instructions 
// to users on how to use it by example.


// open a single window
var window = Ti.UI.createWindow({
	backgroundColor:'black'
});
var label = Ti.UI.createLabel();
window.add(label);
window.open();

// TODO: write your module tests here
var admob = require('com.appcelerator.ti.admob');
Ti.API.info("module is => " + admob);

label.text = 'ADMOB';

var view = admob.createAdView({
	adUnitId: 'a14dee86c85301c',
	adSize: admob.ADSIZE_BANNER,
	top: 0,
	left: 0
});
window.add(view);

view.loadAd();

// if (Ti.Platform.name == "android") {
	// var proxy = ti.admob.createExample({message: "Creating an example Proxy"});
	// proxy.printMessage("Hello world!");
// }