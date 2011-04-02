
/* 
 * Loads the admob module and tests it, with some custom colors.
 */

// open a single window
var window = Ti.UI.createWindow({
  backgroundColor:'white'
});

Titanium.Admob = require('ti.admob');

var adview = Titanium.Admob.createView({
	top:20,
	left:10,
	right:10,
	testing:true,
	adBackgroundColor:'black',
	primaryTextColor:'blue',
	secondaryTextColor:'green',
	publisherId:'<<<YOUR PUBLISHER ID HERE>>>'
});
window.add(adview);

var label = Titanium.UI.createLabel({
	text:'Play around with the ad!',
	bottom:40,
	height:'auto',
	width:'auto'
});
window.add(label);

window.open();

