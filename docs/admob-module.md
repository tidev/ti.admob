# AdmobModule

The root AdmobModule which serves as the entry point to create the different ad types.

```js
const AdMob = require('ti.admob');
AdMob.initialize();
```

## Properties

### adProviders

> `adProviders :Array`

Array of ad providers.

---

### consentStatus

> `consentStatus :Boolean`

The user's consent status.

---

### debugIdentifiers

**iOS only**

> `debugIdentifiers :Array`

Array of advertising identifier UUID strings. Debug features are enabled for devices with these identifiers. Debug features are always enabled for simulators.

---

### debugGeography

> `debugGeography :Number`

Debug geography. Used for debug devices only.

---

### isTaggedForUnderAgeOfConsent

> `isTaggedForUnderAgeOfConsent :Boolean`

Indicates whether the user is tagged for under age of consent.

## Methods

### createBannerView

> createBannerView(options) → BannerView

Creates a new [banner ad](banner-view.md) view.

**Parameters**

| Name | Type | Description |
| --- | --- | --- |
| `options.adUnitId` | `String` | The ad unit ID |
| `options.adSize` | `AdSize` | The desired size of this banner. Defaults to AdMob.AD_SIZE_BANNER (320x50). |

---

### createInterstitialAd

> createInterstitialAd(options) → InterstitalAd

Creates a new [interstitial ad](interstitial-ad.md).

**Parameters**

| Name | Type | Description |
| --- | --- | --- |
| `options.adUnitId` | `String` | The ad unit ID |

---

### disableAutomatedInAppPurchaseReporting

**iOS only**

> `disableAutomatedInAppPurchaseReporting() → void`

Disables automated in app purchase (IAP) reporting. Must be called before any IAP transaction is initiated. IAP reporting is used to track IAP ad conversions. Do not disable reporting if you use IAP ads.

---

### disableSDKCrashReporting

**iOS only**

> `disableSDKCrashReporting() → void`

Disables automated SDK crash reporting. If not called, the SDK records the original exception handler if available and registers a new exception handler. The new exception handler only reports SDK related exceptions and calls the recorded original exception handler.

---

### getAndroidAdId

**Android only**

> `getAndroidAdId(callback) → void`

Gets the user Android Advertising ID. Since this works in a background thread in native Android a callback is called when the value is fetched. The callback parameter is a key/value pair with key `androidAdId` and a String value with the id.

**Parameters**

| Name | Type | Description |
| --- | --- | --- |
| `callback` | `Function` |  |

---

### initialize

> `initialize() → void`

Starts the Google Mobile Ads SDK.

This method should be called as early as possible to reduce latency on the session’s first ad request, and only once per application launch.

---

### isGooglePlayServicesAvailable

**Android only**

> `isGooglePlayServicesAvailable() → void`

Returns a number value indicating the availability of Google Play Services which are for push notifications.

Possible values include `SUCCESS`, `SERVICE_MISSING`, `SERVICE_VERSION_UPDATE_REQUIRED`, `SERVICE_DISABLED`, and `SERVICE_INVALID`.

---

### isLimitAdTrackingEnabled

**Android only**

> `isLimitAdTrackingEnabled(callback) → void`

Checks whether the user has opted out from ad tracking in the device's settings.

Since this works in a background thread in native Android a callback is called when the value is fetched. The callback parameter is a key/value pair with key `isLimitAdTrackingEnabled` and a boolean value for the user's setting.

**Parameters**

| Name | Type | Description |
| --- | --- | --- |
| `callback` | `Function` |  |

---

### requestConsentInfoUpdateForPublisherIdentifiers

> `requestConsentInfoUpdateForPublisherIdentifiers(options) → void`

Requests consent information update for the provided publisher identifiers. All publisher identifiers used in the application should be specified in this call. Consent status is reset to unknown when the ad provider list changes.

**Parameters**

| Name | Type | Description |
| --- | --- | --- |
| `options.publisherIdentifiers` | `Array<String>` |  |
| `options.callback` | `Function` |  |

---

### resetConsent

> `resetConsent() → void`

Resets consent information to default state and clears ad providers.

---

### setTagForUnderAgeOfConsent

> `setTagForUnderAgeOfConsent(value) → void`

Sets whether the user is tagged for under age of consent.

| Name | Type | Description |
| --- | --- | --- |
| `value` | `Boolean` |  |

---

### showConsentForm

> `showConsentForm(options) → void`

Shows a consent modal form.

**Parameters**

| Name | Type | Description |
| --- | --- | --- |
| `options.shouldOfferPersonalizedAds` | `Boolean` | Indicates whether the consent form should show a personalized ad option. Defaults to `true`. |
| `options.shouldOfferNonPersonalizedAds` | `Boolean` | Indicates whether the consent form should show a non-personalized ad option. Defaults to `true`. |
| `options.shouldOfferAdFree` | `Boolean` | Indicates whether the consent form should show an ad-free app option. Defaults to `false`. |
| `options.callback` | `Function` | Callback to be triggered once the form completes. |

## Constants

### AD_SIZE_BANNER

The default banner size (320x50).

Available on phones and tables.

---

### AD_SIZE_FLUID

An ad size that spans the full width of its container, with a height dynamically determined by the ad.

Available on phones and tablets.

---

### AD_SIZE_FULL_BANNER

A full sized banner (468x60).

Available on tablets only.

---

### AD_SIZE_LARGE_BANNER

Taller version of the default banner size (320x100).

Available on phones and tablets.

---

### AD_SIZE_LEADERBOARD

Leaderboard sized banner (728x90).

Available on phones and tablets.

---

### AD_SIZE_MEDIUM_RECTANGLE

Medium Rectangle size (300x250).

Available on phones and tablets.

---

### AD_SIZE_SMART_BANNER

Smart Banners are ad units that render screen-width banner ads on any screen size across different devices in either orientation. Smart Banners detect the width of the device in its current orientation and create the ad view that size (Screen width x 32|50|90).

Available on phones and tablets.

---

### AD_SIZE_SKYSCRAPER

Skyscraper size (120x600). AdMob/Google does not offer this size.

Available on tablets only.

---

### CONSENT_STATUS_UNKNOWN

Unknown consent status.

---

### CONSENT_STATUS_NON_PERSONALIZED

User consented to non-personalized ads.

---

### CONSENT_STATUS_PERSONALIZED

User consented to personalized ads.

---

### DEBUG_GEOGRAPHY_DISABLED

Disable geography debugging.

---

### DEBUG_GEOGRAPHY_EEA

Geography appears as in EEA for debug devices.

---

### DEBUG_GEOGRAPHY_NOT_EEA

Geography appears as not in EEA for debug devices.

---

### SUCCESS

**Android only**

Returned by `isGooglePlayServicesAvailable()` if the connection to Google Play Services was successful.

---

### SERVICE_MISSING

**Android only**

Returned by `isGooglePlayServicesAvailable()` if Google Play Services is missing on this device.

---

### SERVICE_VERSION_UPDATE_REQUIRED

**Android only**

Returned by `isGooglePlayServicesAvailable()` if the installed version of Google Play Services is out of date.

---

### SERVICE_DISABLED

**Android only**

Returned by `isGooglePlayServicesAvailable()` if the installed version of Google Play Services has been disabled on this device.

---

### SERVICE_INVALID

**Android only**

Returned by `isGooglePlayServicesAvailable()` if the version of the Google Play Services installed on this device is not authentic.

## Parity Info

- AdmobModule.debugIdentifiers: currently iOS only. Can be added to Android via https://developers.google.com/admob/android/eu-consent#testing
- AdmobModule.isTaggedForUnderAgeOfConsent: property on iOS, method on Android
- AdmobModule.SIMULATOR_ID constant is missing on Android (https://developers.google.com/android/reference/com/google/android/gms/ads/AdRequest.html#DEVICE_ID_EMULATOR)

## Changed

- New method `initialize` to initialize the Google Ads SDK
- New method `createBannerView` to create banner views
- New constants AD_SIZE_*