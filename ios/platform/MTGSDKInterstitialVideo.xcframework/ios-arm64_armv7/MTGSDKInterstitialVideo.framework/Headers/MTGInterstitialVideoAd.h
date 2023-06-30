//
//  MTGInterstitialVideoAd.m
//  MTGSDKInterstitialVideo
//
//  Copyright © 2019 Mintegral. All rights reserved.
//

#import <Foundation/Foundation.h>
#if __has_include(<MTGSDK/MTGSDK.h>)
    #import <MTGSDK/MTGRewardAdInfo.h>
#else
    #import "MTGRewardAdInfo.h"
#endif

#define MTGInterstitialVideoSDKVersion @"7.3.8"


/**
 We will call back the time when the user saw the alert message. The timing depends on the way you set MTGIVRewardMode
*/
typedef NS_ENUM(NSInteger,MTGIVRewardMode) {
    MTGIVRewardCloseMode,//The alert was shown when the user tried to close the ad.
    MTGIVRewardPlayMode//The alert was shown when the ad played to a certain extent
};

/**
 We will call back whether the alert information has shown to the user and decision of the user.
*/
typedef NS_ENUM(NSInteger,MTGIVAlertWindowStatus) {
    MTGIVAlertNotShown, //The alert window was not shown
    MTGIVAlertChooseContinue,//The alert window has shown and the user chooses to continue which means he wants the reward.
    MTGIVAlertChooseCancel //The alert window has shown and the user chooses to cancel which means he doesn’t want the reward.
};

@class MTGInterstitialVideoAdManager;
@class MTGBidInterstitialVideoAdManager;

/**
 *  This protocol defines a listener for ad video load events.
 */
@protocol MTGInterstitialVideoDelegate <NSObject>
@optional

/**
 *  Called when the ad is loaded , but not ready to be displayed,need to wait load video
 completely
 */
- (void)onInterstitialAdLoadSuccess:(MTGInterstitialVideoAdManager *_Nonnull)adManager;

/**
 *  Called when the ad is successfully load , and is ready to be displayed
 */
- (void)onInterstitialVideoLoadSuccess:(MTGInterstitialVideoAdManager *_Nonnull)adManager;

/**
 *  Called when there was an error loading the ad.
 *  @param error       - error object that describes the exact error encountered when loading the ad.
 */
- (void)onInterstitialVideoLoadFail:(nonnull NSError *)error adManager:(MTGInterstitialVideoAdManager *_Nonnull)adManager;


/**
 *  Called when the ad display success
 */
- (void)onInterstitialVideoShowSuccess:(MTGInterstitialVideoAdManager *_Nonnull)adManager;

/**
 *  Called when the ad failed to display for some reason
 *  @param error       - error object that describes the exact error encountered when showing the ad.
 */
- (void)onInterstitialVideoShowFail:(nonnull NSError *)error adManager:(MTGInterstitialVideoAdManager *_Nonnull)adManager;

/**
 *  Called only when the ad has a video content, and called when the video play completed
 */
- (void)onInterstitialVideoPlayCompleted:(MTGInterstitialVideoAdManager *_Nonnull)adManager;

/**
 *  Called only when the ad has a endcard content, and called when the endcard show
 */
- (void)onInterstitialVideoEndCardShowSuccess:(MTGInterstitialVideoAdManager *_Nonnull)adManager;


/**
 *  Called when the ad is clicked
 */
- (void)onInterstitialVideoAdClick:(MTGInterstitialVideoAdManager *_Nonnull)adManager;

/**
 *  Called when the ad has been dismissed from being displayed, and control will return to your app
 *  @param converted   - BOOL describing whether the ad has converted
 */
- (void)onInterstitialVideoAdDismissedWithConverted:(BOOL)converted adManager:(MTGInterstitialVideoAdManager *_Nonnull)adManager;

/**
 *  Called when the ad  did closed;
 */
- (void)onInterstitialVideoAdDidClosed:(MTGInterstitialVideoAdManager *_Nonnull)adManager;

 /**
*  If Interstitial Video  reward is set, you will receive this callback
*  @param achieved  Whether the video played to required rate
* @param alertWindowStatus  {@link MTGIVAlertWindowStatus} fro list of supported types
  NOTE:You can decide whether to give the reward based on that callback
 */
- (void)onInterstitialVideoAdPlayVideo:(BOOL)achieved alertWindowStatus:(MTGIVAlertWindowStatus)alertWindowStatus adManager:(MTGInterstitialVideoAdManager *_Nonnull)adManager;

@end

@protocol MTGBidInterstitialVideoDelegate <NSObject>
@optional

/**
 *  Called when the ad is loaded , but not ready to be displayed,need to wait load video
 completely
 */
- (void)onInterstitialAdLoadSuccess:(MTGBidInterstitialVideoAdManager *_Nonnull)adManager;

/**
 *  Called when the ad is successfully load , and is ready to be displayed
 */
- (void)onInterstitialVideoLoadSuccess:(MTGBidInterstitialVideoAdManager *_Nonnull)adManager;

/**
 *  Called when there was an error loading the ad.
 *  @param error       - error object that describes the exact error encountered when loading the ad.
 */
- (void)onInterstitialVideoLoadFail:(nonnull NSError *)error adManager:(MTGBidInterstitialVideoAdManager *_Nonnull)adManager;


/**
 *  Called when the ad display success
 */
- (void)onInterstitialVideoShowSuccess:(MTGBidInterstitialVideoAdManager *_Nonnull)adManager;

/**
 *  Called when the ad display success,It will be called only when bidding is used.
 */
- (void)onInterstitialVideoShowSuccess:(MTGBidInterstitialVideoAdManager *_Nonnull)adManager bidToken:(NSString *_Nullable)bidToken;

/**
 *  Called when the ad failed to display for some reason
 *  @param error       - error object that describes the exact error encountered when showing the ad.
 */
- (void)onInterstitialVideoShowFail:(nonnull NSError *)error adManager:(MTGBidInterstitialVideoAdManager *_Nonnull)adManager;

/**
 *  Called only when the ad has a video content, and called when the video play completed
 */
- (void)onInterstitialVideoPlayCompleted:(MTGBidInterstitialVideoAdManager *_Nonnull)adManager;

/**
 *  Called only when the ad has a endcard content, and called when the endcard show
 */
- (void)onInterstitialVideoEndCardShowSuccess:(MTGBidInterstitialVideoAdManager *_Nonnull)adManager;


/**
 *  Called when the ad is clicked
 */
- (void)onInterstitialVideoAdClick:(MTGBidInterstitialVideoAdManager *_Nonnull)adManager;

/**
 *  Called when the ad has been dismissed from being displayed, and control will return to your app
 *  @param converted   - BOOL describing whether the ad has converted
 */
- (void)onInterstitialVideoAdDismissedWithConverted:(BOOL)converted adManager:(MTGBidInterstitialVideoAdManager *_Nonnull)adManager;

/**
 *  Called when the ad  did closed;
 */
- (void)onInterstitialVideoAdDidClosed:(MTGBidInterstitialVideoAdManager *_Nonnull)adManager;

 /**
*  If Interstitial Video  reward is set, you will receive this callback
*  @param achieved  Whether the video played to required rate
* @param alertWindowStatus  {@link MTGIVAlertWindowStatus} fro list of supported types
  NOTE:You can decide whether to give the reward based on that callback
 */
- (void)onInterstitialVideoAdPlayVideo:(BOOL)achieved alertWindowStatus:(MTGIVAlertWindowStatus)alertWindowStatus adManager:(MTGBidInterstitialVideoAdManager *_Nonnull)adManager;

@end
