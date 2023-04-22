# Change Log

### v6.0.0
- Migration to SDK v10 (https://developers.google.com/admob/ios/migration#migrate-to-sdk-v10)
- Update Google Mobile Ads SDK 10.4.0 (https://github.com/CocoaPods/Specs/tree/master/Specs/5/9/a/Google-Mobile-Ads-SDK/10.4.0)
- Update Meta Adapter 6.12.0.1 (https://developers.google.com/admob/ios/mediation/meta#version-6.12.0.1)
- Update InMobi SDK 10.1.3 (https://support.inmobi.com/monetize/sdk-documentation/download-sdk)
- Update InMobiAdapter 10.1.2.1 (https://developers.google.com/admob/ios/mediation/inmobi#version-10.1.2.1_1)

### v5.1.0
- Removed the FBLPromises framework
- Added sample demo app: https://github.com/Astrovic/ti.admob-sample-app/
- Minor fixes

### v5.0.0
- Migration to SDK v9 (https://developers.google.com/admob/ios/migration):
- Update Google Mobile Ads SDK 9.13.0 (https://github.com/CocoaPods/Specs/tree/master/Specs/5/9/a/Google-Mobile-Ads-SDK/9.13.0)
- Update Audience Network SDK 6.12.0 (https://developers.facebook.com/docs/audience-network/setting-up/platform-setup/ios/add-sdk)
- Added Meta Adapter 6.12.0.0 (https://developers.google.com/admob/ios/mediation/meta#facebook-ios-mediation-adapter-changelog)
- Removed Facebook Adapter 6.9.0.0 (https://developers.google.com/admob/ios/mediation/facebook#facebook-ios-mediation-adapter-changelog)- 
- Update InMobi SDK 10.1.0 (https://support.inmobi.com/monetize/sdk-documentation/download-sdk)
- Update InMobiAdapter 10.1.0.0 (https://developers.google.com/admob/ios/mediation/inmobi#inmobi-ios-mediation-adapter-changelog)
- Removed `Admob.setLocation()` because `setLocationWithLatitude` has been removed by Google
- Removed `Admob.showMediationTestSuite()`. You can use `gmamts.showMediationTestSuite()` with the module https://github.com/Astrovic/ti.gmamts
- Removed `PersonalizedAdConsent` framework because deprecated by Google (https://developers.google.com/admob/ios/eu-consent)

### v4.8.0
- Added `Admob.showMediationTestSuite()` method to open Google Mobile Ads Mediation Test Suite
- More info here: https://developers.google.com/admob/ios/mediation-test-suite?hl=en

### v4.7.0
- Update Google Mobile Ads SDK 8.13.0 (https://github.com/CocoaPods/Specs/tree/master/Specs/5/9/a/Google-Mobile-Ads-SDK/8.13.0 and https://github.com/firebase/firebase-ios-sdk/releases/tag/v8.13.0)
- Update Audience Network SDK 6.9.0 (https://developers.facebook.com/docs/audience-network/setting-up/platform-setup/ios/add-sdk)
- Update Facebook Adapter 6.9.0.0 (https://developers.google.com/admob/ios/mediation/facebook#facebook-ios-mediation-adapter-changelog)
- Update InMobi SDK 10.0.2 (https://support.inmobi.com/monetize/sdk-documentation/download-sdk)
- Update InMobiAdapter 10.0.2.0 (https://developers.google.com/admob/ios/mediation/inmobi#step_3_import_the_inmobi_sdk_and_adapter)

### v4.5.6
- Update AdMob SDK 8.12.0
- Update iOS Audience Network SDK 6.8.0
- Update iOS Facebook Adapter 6.8.0.0

### v4.5.0
- Update AdMob SDK 8.12.0

### v4.4.0
- Update AdMob SDK 8.11.0 (iOS 15 support)

### v4.3.0
- Added `UMPDebugSettings` on `Admob.requestConsentInfoUpdateWithParameters`: `testDeviceIdentifiers` and `geography`
More info here: https://developers.google.com/admob/ump/ios/quick-start#testing

### v4.2.0
- Update AdMob SDK 8.10.0
- Update iOS Audience Network SDK 6.6.0
- Update iOS Facebook Adapter 6.6.0.0
- Update InMobi SDK 9.2.1
- Update InMobiAdapter 9.2.1.0

### v4.1.0
- Update AdMob SDK 8.4.0 (Firebase 7.11.0)
- Update iOS Audience Network SDK 6.4.1
- Update iOS Facebook Adapter 6.4.1.0
- Update InMobi SDK 9.1.7
- Update InMobiAdapter 9.1.7.0

### v4.0.0
- Update AdMob SDK to 8 (Removed all deprecated classes)
- Added support for new Rewarded Video Ads
- Added UMP SDK 2.0.0

### v3.1.0
- Update iOS Audience Network SDK 6.2.1
- Update iOS Facebook Adapter 6.2.1.0
- Added setAdvertiserTrackingEnabled for Audience Network (iOS 14)

### v3.0.1
- Update AdMob SDK to 7.69.0
- Update Audience Network SDK 6.2.0
- Update Facebook Adapter 6.2.0.0
- AppTrackingTransparency (iOS 14)

### v2.5.2
- Update AdMob SDK to 7.64.0
- Update Audience Network SDK 5.10.1
- Update Facebook Adapter 5.10.1.0

### 2.4.4
- Update AdMob SDK to 7.59.0
- Update PersonalizedAdConsent to 1.5.0 (Removed all references to deprecated UIWebView)
- Update Audience Network SDK 5.8.0
- Update Facebook Adapter 5.8.0.2

### v2.4.3
- Update AdMob SDK to 7.57.0
- Update Audience Network SDK 5.7.1

### v2.4.2
- Added support for Rewarded Video Ads.
- Update AdMob SDK to 7.47.0
- Update Audience Network SDK 5.4.0

### v2.4.0
- Support the Facebook Audience Network adapter
- iOS integration guide: https://developers.google.com/admob/ios/mediation/facebook

### v2.3.0
- [MOD-2196] Update AdMob to 7.31.0, conform to GDPR

### v2.2.0
- Update Admob iOS SDK to 7.27.0, remove iAd adapter due to Google removal

### v2.1.0
- [MOD-2196] Support the iAd adapter

### v2.0.0
- [MOD-2182] Updating Admob SDK to 7.6.0, support iOS 9, support for new API's'

### v1.9.0
- [TIMOB-18092] ti.admob added 64bit support for iOS #15

### v1.8.0
- [TIMOB-17928] Updated to build for 64-bit

### v1.7.0
- [TIMODOPEN-436] Updating Admob SDK to 6.12.0

### v1.6.0
- [TIMODOPEN-427] Updating Admob SDK to 6.10.0, Building with TiSDK 3.2.3.GA, update documentation
- Replaced `publisherId` with `adUnitId`
- Deprecated `testing`
- Added `testDevices`

### v1.5.0
- [TIMODOPEN-242] Updating Admob SDK to 6.4.2 (Removed all uses of UDID)

### v1.4.0
- [TIMODOPEN-212] Updating Admob SDK to 6.2.1, Building with TiSDK 2.1.3.GA, update documentation

### v1.3
- Fixed crash on iOS 4.3 [MOD-600]

### v1.2
- Upgraded to SDK 5.0.5 [MOD-410]

### v1.1
- Fixed ad retention issue [MOD-320]

### v1.0
- Initial Release
