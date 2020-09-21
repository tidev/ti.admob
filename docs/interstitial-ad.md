# Interstitial Ad

Interstitial ads are full-screen ads that cover the interface of an app until closed by the user. They're typically displayed at natural transition points in the flow of an app, such as between activities or during the pause between levels in a game. When an app shows an interstitial ad, the user has the choice to either tap on the ad and continue to its destination or close it and return to the app.

```js
const AdMob = require('ti.admob');
AdMob.initialize({ 'appId': 'ca-app-pub-3940256099942544~1458002511' });
const interstitial = AdMob.createInterstitialAd({
  adUnitId: 'ca-app-pub-3940256099942544/4411468910'
});
interstitial.load();

// ... later in your app when you plan to display the interstitial
if (interstitial.isReady) {
  interstitial.show();
}
```

> 💡 Don't use the `load` event to show the interstitial. This can cause a poor user experience. Instead, pre-load the ad before you need to show it. Then check the `isReady` flag on the interstitial to find out if it is ready to be shown.

## Properties

### adUnitId

> `adUnitId :String`

Ad unit id used for displaying this ad.

---

### isReady

> `isReady :Boolean`

Returns `true` if the interstitial is ready to be displayed.

## Methods

### load

> `load(options) → void`

Starts loading the ad. You can customize the request by specifiying any of the following options.

**Parameters**

| Name | Type | Description |
| --- | --- | --- |
| `options.keywords` | `Array<String>` | Keywords for targeting purposes. |
| `options.extras` | `Object` | Extra parameters to pass to a specific ad network adapter. |
| `options.contentUrl` | `Object` | Content URL for targeting purposes. |
| `options.requestAgent` | `String` | Request agent string to identify the ad request's origin. |

---

### show

> `show() → void`

Show the interstitial ad.

## Events

### load

Fired when an ad finishes loading.

### fail

Fired when an ad request fails.

### open

Fired when an ad opens an overlay that covers the screen.

### close

Fired when the user is about to return to the app after tapping on an ad.

### leftapp

Fired when the user has left the app.