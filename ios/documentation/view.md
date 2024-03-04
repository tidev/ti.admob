# Ti.Admob.View

## Description

A view for displaying ads delivered through Admob.

You should explicitly size the ad to one of the following predefined sizes (width x height). An appropriate
ad will be loaded from the server automatically:

* 320x50
* 300x250
* 468x60
* 728x90

Note: Starting in 2.0.0, you just need to specify a height and an appropriate ad is selected by the Admob SDK.
See the example.js for more infos.

## Properties

### string adUnitId

The ad Unit ID for Admob.

### string adBackgroundColor

The background color used for the ad.

### Array[String] testDevices

An array of test device ids. Adding the id of a test device to this array 
will allow that device to be served test ads. 

If you do not know the id for your device, launch your app and request an ad 
like you normally would, then look in the console for the id. 

eg. <Google> To get test ads on this device, set the property `debugEnabled` to true

Add the id to the array passed to `testDevices`.

### Date dateOfBirth

**Deprecated as part of the AdMob SDK**

A date object representing the user's date of birth that should be used
for determining ad delivery. 

### string gender

**Deprecated as part of the AdMob SDK, deleted from 4.5.0**

The user's gender for the purpose of determining ad delivery. This should be one of the constants `GENDER_MALE`, `GENDER_FEMALE` or `GENDER_UNKNOWN`.

### Array[String] keywords

An array of keywords used to determine ad delivery.

### object location

A dictionary with the location of the user for location-based ads:

* float latitude
* float longitude
* float accuracy

### object extras

Ad networks may have additional parameters they accept. To pass these parameters to them, create the ad network extras 
object for that network, fill in the parameters, and register it here. The ad network should have a header defining the 
interface for the `extras` object to create. All networks will have access to the basic settings you've set in this 
GADRequest (gender, birthday, testing mode, etc.). If you register an extras object that is the same class as one you have.

**Important**: You can pass `{ 'npa: '1' }` to disable personalized ads. This is required to conform to [GDPR](https://www.eugdpr.org/).

### String contentURL

URL string for a webpage whose content matches the app content. This webpage content is used for targeting purposes.

### String requestAgent

String that identifies the ad request's origin. Third party libraries that reference the Mobile. Ads SDK should set this property 
to denote the platform from which the ad request originated. For example, a third party ad network called "CoolAds network" that 
is mediating requests to the Mobile Ads SDK should set this property as "CoolAds".

### Boolean tagForChildDirectedTreatment

For purposes of the [Children's Online Privacy Protection Act (COPPA)](https://www.ftc.gov/business-guidance/privacy-security/childrens-privacy), there is a setting called tagForChildDirectedTreatment.

As an app developer, you can indicate whether you want Google to treat your content as child-directed when you make an ad request. When you indicate that you want Google to treat your content as child-directed, Google takes steps to disable IBA and remarketing ads on that ad request. The setting options are as follows:

- Set `tagForChildDirectedTreatment` to `true` to indicate that you want your content treated as child-directed for purposes of COPPA. This prevents the transmission of the Advertising Identifier, IDFA.
- Set `tagForChildDirectedTreatment` to `false` to indicate that you don't want your content treated as child-directed for purposes of COPPA.
- Don't set `tagForChildDirectedTreatment` if you don't want to indicate how you would like your content treated with respect to COPPA.

By setting this tag, you certify that this notification is accurate and you are authorized to act on behalf of the owner of the app. You understand that abuse of this setting may result in termination of your Google Account.

### Boolean tagForUnderAgeOfConsent

You can mark your ad requests to receive treatment for users in the European Economic Area (EEA) under the age of consent. This feature is designed to help facilitate compliance with the [General Data Protection Regulation (GDPR)](https://eur-lex.europa.eu/legal-content/EN/TXT/?uri=CELEX:32016R0679). Note that you may have other legal obligations under GDPR. Review the European Union’s guidance and consult with your own legal counsel. Note that Google's tools are designed to facilitate compliance and do not relieve any particular publisher of its obligations under the law. [Learn more about how the GDPR affects publishers](https://support.google.com/admob/answer/7666366).

When using this feature, a Tag For Users under the Age of Consent in Europe (TFUA) parameter will be included in all future ad requests. This parameter disables personalized advertising, including remarketing, for that specific ad request. It also disables requests to third-party ad vendors, such as ad measurement pixels and third-party ad servers.

The setting can be used with all versions of the Google Mobile Ads SDK by setting the `tagForUnderAgeOfConsent` property on the GADMobileAds.requestConfiguration object and passing in `true`.

- Set `tagForUnderAgeOfConsent` to `true` to indicate that you want ad requests to be handled in a manner suitable for users under the age of consent. This also prevents the transmission of the Advertising Identifier, IDFA.
- Not setting `tagForUnderAgeOfConsent` indicates that you don't want ad requests to be handled in a manner suitable for users under the age of consent.

The tags to enable the `tagForChildDirectedTreatmentsetting` and `tagForUnderAgeOfConsent` shouldn't both simultaneously be set to `true`. If they are, the child-directed setting takes precedence.

### Module constant maxAdContentRating

Apps can set a maximum ad content rating for all ad requests using the `maxAdContentRating` property. This setting applies to all future ad requests for the remainder of the session. The possible values for this property are based on [digital content label classifications](https://support.google.com/admob/answer/7562142), and should be one of the following Admob module constants:
- `MAX_AD_CONTENT_RATING_GENERAL`
- `MAX_AD_CONTENT_RATING_PARENTAL_GUIDANCE`
- `MAX_AD_CONTENT_RATING_TEEN`
- `MAX_AD_CONTENT_RATING_MATURE_AUDIENCE`

### Boolean debugEnabled

Sets a testing value for `adUnitId` to test ads without an admob account.

## Events

### didReceiveAd

 Sent when an ad request loaded an ad. This is a good opportunity to add this
 view to the hierarchy if it has not yet been added. If the ad was received
 as a part of the server-side auto refreshing, you can examine the
 hasAutoRefreshed property of the view. Use the `adUnitId` property of the callback
 to identify the ad that was loaded.

### didFailToReceiveAd

 Sent when an ad request failed. Normally this is because no network
 connection was available or no ads were available (i.e. no fill).

### didFailToShowAd

Sent when App Open Ad failed to show.

### willPresentScreen

Sent just before presenting the user a full screen view, such as a browser,
in response to clicking on an ad. Use this opportunity to stop animations,
time sensitive interactions, etc.

Normally the user looks at the ad, dismisses it, and control returns to your
application by firing off `didDismissScreen`: However if the user hits the
Home button or clicks on an App Store link your application will end. In that case,
`willLeaveApplication` would fire.

### willDismissScreen

Sent just before dismissing a full screen view.

### didDismissScreen

Sent just after dismissing a full screen view. Use this opportunity to
restart anything you may have stopped as part of `willPresentScreen`.

### willLeaveApplication

Sent just before the application will background or terminate because the
user clicked on an ad that will launch another application (such as the App
Store).

### didReceiveInAppPurchase

⚠️ Deprecated by the Admob SDK 7.x and Ti.Admob 2.2.0

Called when the user clicks on the buy button of an in-app purchase ad. After the receiver handles the purchase, it must 
call the GADInAppPurchase object's reportPurchaseStatus: method.

### didRewardUser

Fired when the reward based video ad has rewarded the user.

### adrewarded
⚠️ Removed since Ti.Admob 6.2.0. You can use `didRewardUser`

Fired when the reward based video ad has rewarded the user.

#### Parameters

| Name | Type | Description |
| --- | --- | --- |
| `reward` | `Object` | Reward information for rewarded video ads. |
| `reward.type` | `String` | Type of the reward. |
| `reward.amount` | `Number` | Amount rewarded to the user. |

### adloaded 
⚠️ Removed since Ti.Admob 6.2.0. You can use `didReceiveAd`

Fired when a reward based video ad was received. From this point on you can open the video using `showRewardedVideo()`.

### adfailedtoload
⚠️ Removed since Ti.Admob 6.2.0. You can use `didFailToReceiveAd`

Fired when the reward based video ad failed to load.
