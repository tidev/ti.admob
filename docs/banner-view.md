# Banner Ad View

Banner ads are rectangular image or text ads that occupy a spot within an app's layout. They stay on screen while users are interacting with the app, and can refresh automatically after a certain period of time. If you're new to mobile advertising, they're a great place to start.

```js
const AdMob = require('ti.admob');
AdMob.initialize({ 'appId': 'ca-app-pub-3940256099942544~1458002511' });
const win = Ti.UI.createWindow();
const bannerView = AdMob.createBannerView({
  adUnitId: 'ca-app-pub-3940256099942544/2934735716',
  bottom: 0,
  height: 50
});
bannerView.load();
win.add(bannerView);
win.open();
```

## Properties

### adSize

> `adSize :AdSize`

The size of the banner ad. Use one of the AD_SIZE_* constants. Also see [AdSize](ad-size.md).

Defaults to [AD_SIZE_BANNER](admob-module.md#ad_size_banner).

---

### adUnitId

> `adUnitId :String`

Ad unit id used for displaying this banner.

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

## Parity Info

- Android: `loadAd` and `requestAd` need to be replaced by `load`
- Android: All properties except for `adUnitId` and `adSize` can be removed. Ad request customization is done via `load(options)`.

## Changed

- Ad will now be loaded via `load` and options can be passed for each load request.
- Ad size can be specified via the new `AdSize` proxy.
- Renamed events to be more JS'ish