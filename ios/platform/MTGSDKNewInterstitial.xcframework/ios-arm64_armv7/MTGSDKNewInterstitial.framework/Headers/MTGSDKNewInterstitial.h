//
//  MTGSDKNewInterstitial.h
//  MTGSDKNewInterstitial
//
//  Created by Herui on 2022/1/7.
//  Copyright © 2022 Mintegral. All rights reserved.
//

#import <Foundation/Foundation.h>

//! Project version number for MTGSDKNewInterstitial.
FOUNDATION_EXPORT double MTGSDKNewInterstitialVersionNumber;

//! Project version string for MTGSDKNewInterstitial.
FOUNDATION_EXPORT const unsigned char MTGSDKNewInterstitialVersionString[];

// In this header, you should import all the public headers of your framework using statements like #import <MTGSDKNewInterstitial/PublicHeader.h>

#import <Foundation/Foundation.h>
#if __has_include(<MTGSDK/MTGSDK.h>)
    #import <MTGSDK/MTGRewardAdInfo.h>
#else
    #import "MTGRewardAdInfo.h"
#endif

#define MTGNewInterstitialSDKVersion @"7.3.8"


/**
 We will call back the time when the user saw the alert message. The timing depends on the way you set MTGNIRewardMode
*/
typedef NS_ENUM(NSInteger,MTGNIRewardMode) {
    MTGNIRewardCloseMode,//The alert was shown when the user tried to close the ad.
    MTGNIRewardPlayMode//The alert was shown when the ad played to a certain extent
};

/**
 We will call back whether the alert information has shown to the user and decision of the user.
*/
typedef NS_ENUM(NSInteger,MTGNIAlertWindowStatus) {
    MTGNIAlertNotShown, //The alert window was not shown
    MTGNIAlertChooseContinue,//The alert window has shown and the user chooses to continue which means he wants the reward.
    MTGNIAlertChooseCancel //The alert window has shown and the user chooses to cancel which means he doesn’t want the reward.
};

@class MTGNewInterstitialAdManager;
@class MTGNewInterstitialBidAdManager;

/**
 *  This protocol defines a listener for ad events.
 */
@protocol MTGNewInterstitialAdDelegate <NSObject>
@optional

/**
 *  Called when the ad is loaded , but not ready to be displayed,need to wait load resources completely
 */
- (void)newInterstitialAdLoadSuccess:(MTGNewInterstitialAdManager *_Nonnull)adManager;

/**
 *  Called when the ad is successfully load , and is ready to be displayed
 */
- (void)newInterstitialAdResourceLoadSuccess:(MTGNewInterstitialAdManager *_Nonnull)adManager;

/**
 *  Called when there was an error loading the ad.
 *  @param error       - error object that describes the exact error encountered when loading the ad.
 */
- (void)newInterstitialAdLoadFail:(nonnull NSError *)error adManager:(MTGNewInterstitialAdManager *_Nonnull)adManager;


/**
 *  Called when the ad displayed successfully
 */
- (void)newInterstitialAdShowSuccess:(MTGNewInterstitialAdManager *_Nonnull)adManager;

/**
 *  Called when the ad failed to display
 *  @param error       - error object that describes the exact error encountered when showing the ad.
 */
- (void)newInterstitialAdShowFail:(nonnull NSError *)error adManager:(MTGNewInterstitialAdManager *_Nonnull)adManager;

/**
 *  Called only when the ad has a video content, and called when the video play completed
 */
- (void)newInterstitialAdPlayCompleted:(MTGNewInterstitialAdManager *_Nonnull)adManager;

/**
 *  Called only when the ad has a endcard content, and called when the endcard show
 */
- (void)newInterstitialAdEndCardShowSuccess:(MTGNewInterstitialAdManager *_Nonnull)adManager;


/**
 *  Called when the ad is clicked
 */
- (void)newInterstitialAdClicked:(MTGNewInterstitialAdManager *_Nonnull)adManager;

/**
 *  Called when the ad has been dismissed from being displayed, and control will return to your app
 *  @param converted   - BOOL describing whether the ad has converted
 */
- (void)newInterstitialAdDismissedWithConverted:(BOOL)converted adManager:(MTGNewInterstitialAdManager *_Nonnull)adManager;

/**
 *  Called when the ad  did closed;
 */
- (void)newInterstitialAdDidClosed:(MTGNewInterstitialAdManager *_Nonnull)adManager;

 /**
*  If NewInterstitial reward is set, you will receive this callback
*  @param rewardedOrNot  Whether the video played to required rate
*  @param alertWindowStatus  {@link MTGNIAlertWindowStatus} for list of            supported types
  NOTE:You can decide whether or not to give the reward based on this callback
 */
- (void)newInterstitialAdRewarded:(BOOL)rewardedOrNot alertWindowStatus:(MTGNIAlertWindowStatus)alertWindowStatus adManager:(MTGNewInterstitialAdManager *_Nonnull)adManager;

@end

/**
 *  This protocol defines a listener for ad events.
 */
@protocol MTGNewInterstitialBidAdDelegate <NSObject>
@optional

/**
 *  Called when the ad is loaded , but not ready to be displayed,need to wait load resources completely
 */
- (void)newInterstitialBidAdLoadSuccess:(MTGNewInterstitialBidAdManager *_Nonnull)adManager;

/**
 *  Called when the ad is successfully load , and is ready to be displayed
 */
- (void)newInterstitialBidAdResourceLoadSuccess:(MTGNewInterstitialBidAdManager *_Nonnull)adManager;

/**
 *  Called when there was an error loading the ad.
 *  @param error       - error object that describes the exact error encountered when loading the ad.
 */
- (void)newInterstitialBidAdLoadFail:(nonnull NSError *)error adManager:(MTGNewInterstitialBidAdManager *_Nonnull)adManager;


/**
 *  Called when the ad display success
 */
- (void)newInterstitialBidAdShowSuccess:(MTGNewInterstitialBidAdManager *_Nonnull)adManager;

/**
 *  Only called when displaying bidding ad.
 */
- (void)newInterstitialBidAdShowSuccessWithBidToken:(nonnull NSString * )bidToken adManager:(MTGNewInterstitialBidAdManager *_Nonnull)adManager;

/**
 *  Called when the ad failed to display
 *  @param error       - error object that describes the exact error encountered when showing the ad.
 */
- (void)newInterstitialBidAdShowFail:(nonnull NSError *)error adManager:(MTGNewInterstitialBidAdManager *_Nonnull)adManager;

/**
 *  Called only when the ad has a video content, and called when the video play completed
 */
- (void)newInterstitialBidAdPlayCompleted:(MTGNewInterstitialBidAdManager *_Nonnull)adManager;

/**
 *  Called only when the ad has a endcard content, and called when the endcard show
 */
- (void)newInterstitialBidAdEndCardShowSuccess:(MTGNewInterstitialBidAdManager *_Nonnull)adManager;


/**
 *  Called when the ad is clicked
 */
- (void)newInterstitialBidAdClicked:(MTGNewInterstitialBidAdManager *_Nonnull)adManager;

/**
 *  Called when the ad has been dismissed from being displayed, and control will return to your app
 *  @param converted   - BOOL describing whether the ad has converted
 */
- (void)newInterstitialBidAdDismissedWithConverted:(BOOL)converted adManager:(MTGNewInterstitialBidAdManager *_Nonnull)adManager;

/**
 *  Called when the ad  did closed;
 */
- (void)newInterstitialBidAdDidClosed:(MTGNewInterstitialBidAdManager *_Nonnull)adManager;

 /**
*  If New Interstitial  reward is set, you will receive this callback
*  @param rewardedOrNot  Whether the video played to required rate
* @param alertWindowStatus  {@link MTGNIAlertWindowStatus} for list of supported types
  NOTE:You can decide whether or not to give the reward based on this callback
 */
- (void)newInterstitialBidAdRewarded:(BOOL)rewardedOrNot alertWindowStatus:(MTGNIAlertWindowStatus)alertWindowStatus adManager:(MTGNewInterstitialBidAdManager *_Nonnull)adManager;

@end




