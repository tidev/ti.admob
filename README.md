ti.admob [![Build Status](https://travis-ci.org/appcelerator-modules/ti.admob.svg)](https://travis-ci.org/appcelerator-modules/ti.admob)
============

Admob module

If you want to use this version today
===

*Then this is a big disclaimer, this has only been tested on one single project. It works for us, but it might not work for you. Feel free to create an issue, I'll try to help.*

This is how you do it:
- Download our version of `libti.admob.a`: https://github.com/Your-Majesty/ti.admob/raw/feature/add-interstitial-support/extra/libti.admob.a.
- In your project, replace `/modules/iphone/ti.admob/1.7.0/libti.admob.a` with the version you just downloaded.
- Force a rebuild of the project (`titanium build -p ios --build-only true --force` if you use CLI)
- Use like this:
```
var Admob, ad, delay;

Admob = require('ti.admob');

delay = function(ms, func) {
  return setTimeout(func, ms);
};

ad = Admob.createInterstitial({
  adUnitId: pass-your-code-here,
  testing: false
});

ad.prepare();

// This is the worst part. I have not connected any callbacks so a timeout is required for now.
delay(3000, function() {
  return ad.showMe();
});
```
- If you're not just building for iOS, you will have to wrap this logic in an "if ios" kind of statement.
