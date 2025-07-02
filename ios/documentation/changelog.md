# Change Log

### v8.1.0
- Update Google Mobile Ads SDK 12.6.0 (https://github.com/CocoaPods/Specs/tree/master/Specs/5/9/a/Google-Mobile-Ads-SDK/12.6.0)
- Update Meta Audience Network SDK 6.20.0 (https://developers.facebook.com/docs/audience-network/setting-up/platform-setup/ios/changelog#6_20_0)
- Meta Audience Network Adapter 6.20.0.0 (https://developers.google.com/admob/ios/mediation/meta#version-6.20.0.0)
- Update InMobi SDK 10.8.3 (https://support.inmobi.com/monetize/sdk-documentation/download-sdk)
- Update InMobiAdapter 10.8.3.1 (https://developers.google.com/admob/ios/mediation/inmobi#version-10.8.3.1)

### v8.0.0
- Migration to SDK v12 (https://developers.google.com/admob/ios/migration#migrate-to-sdk-v12)
- Update Google Mobile Ads SDK 12.4.0 (https://github.com/CocoaPods/Specs/tree/master/Specs/5/9/a/Google-Mobile-Ads-SDK/12.4.0)
- Update Meta Audience Network SDK 6.17.1 (https://developers.facebook.com/docs/audience-network/setting-up/platform-setup/ios/changelog#6_17_1)
- Meta Audience Network Adapter 6.17.1.0 (https://developers.google.com/admob/ios/mediation/meta#version-6.17.1.0)
- Update InMobi SDK 10.8.3 (https://support.inmobi.com/monetize/sdk-documentation/download-sdk)
- Update InMobiAdapter 10.8.3.0 (https://developers.google.com/admob/ios/mediation/inmobi#version-10.8.3.0)
- Added iphone method `Admob.presentPrivacyOptionsForm()` (https://developers.google.com/admob/ios/privacy#present-privacy-options-form)
- Added iphone method `Admob.isPrivacyOptionsRequired()` (https://developers.google.com/admob/ios/privacy#check-privacy-options).
- Added iphone method `Admob.canRequestAds()` (https://developers.google.com/admob/ios/privacy#request-ads)
- Added iphone property: `Admob.DEBUG_GEOGRAPHY_REGULATED_US_STATE`(https://developers.google.com/admob/ios/privacy/api/reference/Enums/UMPDebugGeography.html#umpdebuggeographyregulatedusstate)
- Added iphone property: `Admob.DEBUG_GEOGRAPHY_OTHER` (https://developers.google.com/admob/ios/privacy/api/reference/Enums/UMPDebugGeography.html#umpdebuggeographyother)
- Removed iOS `Admob.DEBUG_GEOGRAPHY_NOT_EEA` because deprecated (https://developers.google.com/admob/ios/privacy/api/reference/Enums/UMPDebugGeography.html#umpdebuggeographynoteea)
- Removed iOS `Admob.SIMULATOR_ID`. Simulators are already in test mode by default

### v7.1.0
- Update Google Mobile Ads SDK 11.3.0 (https://github.com/CocoaPods/Specs/tree/master/Specs/5/9/a/Google-Mobile-Ads-SDK/11.3.0)
- Update Meta Audience Network SDK 6.15.0 (https://developers.facebook.com/docs/audience-network/setting-up/platform-setup/ios/changelog#6_15_0)
- Update Meta Adapter 6.15.0.0 (https://developers.google.com/admob/ios/mediation/meta#version-6.15.0.0)
- Update InMobi SDK 10.7.1 (https://support.inmobi.com/monetize/sdk-documentation/download-sdk)
- Update InMobiAdapter 10.7.1.0 (https://developers.google.com/admob/ios/mediation/inmobi#version-10.7.1.0)
- Deprecated iOS `setAdvertiserTrackingEnabled` for Audience Network
- Updated documentation

### v7.0.0
- Migration to SDK v11 (https://developers.google.com/admob/ios/migration#migrate-to-sdk-v11)
- Update Google Mobile Ads SDK 11.0.1 (https://github.com/CocoaPods/Specs/tree/master/Specs/5/9/a/Google-Mobile-Ads-SDK/11.0.1)
- Update InMobi SDK 10.6.4 (https://support.inmobi.com/monetize/sdk-documentation/download-sdk)
- Update InMobiAdapter 10.6.0.0 (https://developers.google.com/admob/ios/mediation/inmobi#version-10.6.0.0)
- Added iphone property: `tagForChildDirectedTreatment`. https://developers.google.com/admob/ios/targeting#child-directed_setting for more infos
- Added iphone property: `tagForUnderAgeOfConsent`.https://developers.google.com/admob/ios/targeting#users_under_the_age_of_consent for more infos
- Added iphone property: `maxAdContentRating`. https://developers.google.com/admob/ios/targeting#ad_content_filtering for more infos
- Deprecated iOS `Admob.SIMULATOR_ID`
- Updated documentation and iOS example

### v6.3.1
- Fixed AppOpen auto closing on iOS

### v6.3.0
- Added iphone methods `isGDPR()`, `canShowAds()`, `canShowPersonalizedAds()`
- Update Google Mobile Ads SDK 10.12.0 (https://github.com/CocoaPods/Specs/tree/master/Specs/5/9/a/Google-Mobile-Ads-SDK/10.12.0)
- Update Meta Audience Network SDK 6.14.0 (https://developers.facebook.com/docs/audience-network/setting-up/platform-setup/ios/changelog#6_14_0)
- Update Meta Adapter 6.14.0.0 (https://developers.google.com/admob/ios/mediation/meta#version-6.14.0.0)
- Update InMobi SDK 10.1.3 (https://support.inmobi.com/monetize/sdk-documentation/download-sdk)
- Update InMobiAdapter 10.5.8.0 (https://developers.google.com/admob/ios/mediation/inmobi#version-10.5.8.0)
- Updated documentation and iOS example


### v6.2.0
- Added support for App Open Ad (https://developers.google.com/admob/ios/app-open)
- Reset TC string if last updated date was more than 13 months ago (https://developers.google.com/admob/ios/privacy/gdpr#troubleshooting)
- Renamed Interstitial and Rewarded Video event names:
-- `adloaded` becomes `didReceiveAd`
-- `adfailedtoload` becomes `didFailToReceiveAd`
-- `adrewarded` becomes `didRewardUser`
- Added `didRecordImpression` and `didRecordClick` events to all ad types
- Added `didFailToShowAd` event to Open App ad type
- Removed deprecated `didPresentScreen` event
- Updated documentation and iOS example

### v6.1.0
- Update InMobi SDK 10.5.4 (https://support.inmobi.com/monetize/sdk-documentation/download-sdk)
- Update InMobiAdapter 10.5.4.0 (https://developers.google.com/admob/ios/mediation/inmobi#version-10.5.4.0_1)

### v6.0.0
- Migration to SDK v10 (https://developers.google.com/admob/ios/migration#migrate-to-sdk-v10)
- Update Google Mobile Ads SDK 10.4.0 (https://github.com/CocoaPods/Specs/tree/master/Specs/5/9/a/Google-Mobile-Ads-SDK/10.4.0)
- Update Meta Adapter 6.12.0.1 (https://developers.google.com/admob/ios/mediation/meta#version-6.12.0.1)
- Update InMobi SDK 10.1.3 (https://support.inmobi.com/monetize/sdk-documentation/download-sdk)
- Update InMobiAdapter 10.1.2.1 (https://developers.google.com/admob/ios/mediation/inmobi#version-10.1.2.1_1)
- The module includes Ad Inspector (https://developers.google.com/admob/ios/ad-inspector). It is possible to test each adUnit by requesting a Request test ad

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
