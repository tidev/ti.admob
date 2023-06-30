//
//  MTGRewardAd.h
//  MTGSDK
//
//  Copyright © 2019 Mintegral. All rights reserved.
//

#import <Foundation/Foundation.h>
#if __has_include(<MTGSDK/MTGSDK.h>)
    #import <MTGSDK/MTGRewardAdInfo.h>
#else
    #import "MTGRewardAdInfo.h"
#endif

#define MTGRewardVideoSDKVersion @"7.3.8"


/**
 *  This protocol defines a listener for ad video load events.
 */
@protocol MTGRewardAdLoadDelegate <NSObject>
@optional

/**
*  Called when the ad is loaded , but not ready to be displayed,need to wait load video
completely
 
*  @param placementId - the placementId string of the Ad that was loaded.
*  @param unitId - the unitId string of the Ad that was loaded.
*/
- (void)onAdLoadSuccess:(nullable NSString *)placementId unitId:(nullable NSString *)unitId;

/**
 *  Called when the ad is successfully load , and is ready to be displayed
 
 *  @param placementId - the placementId string of the Ad that was loaded.
 *  @param unitId - the unitId string of the Ad that was loaded.
 */
- (void)onVideoAdLoadSuccess:(nullable NSString *)placementId unitId:(nullable NSString *)unitId;

/**
 *  Called when there was an error loading the ad.
 
 *  @param placementId - the placementId string of the Ad that was loaded.
 *  @param unitId      - the unitId string of the Ad that failed to load.
 *  @param error       - error object that describes the exact error encountered when loading the ad.
 */
- (void)onVideoAdLoadFailed:(nullable NSString *)placementId unitId:(nullable NSString *)unitId error:(nonnull NSError *)error;

@end

/**
 *  This protocol defines a listener for ad video show events.
 */
@protocol MTGRewardAdShowDelegate <NSObject>
@optional

/**
 *  Called when the ad display success
 
 *  @param placementId - the placementId string of the Ad that display success.
 *  @param unitId - the unitId string of the Ad that display success.
 */
- (void)onVideoAdShowSuccess:(nullable NSString *)placementId unitId:(nullable NSString *)unitId;

/**
 *  Called when the ad display success,It will be called only when bidding is used.
 
 *  @param placementId - the placementId string of the Ad that display success.
 *  @param unitId - the unitId string of the Ad that display success.
 *  @param bidToken - the bidToken string of the Ad that display success.
 */
- (void)onVideoAdShowSuccess:(nullable NSString *)placementId unitId:(nullable NSString *)unitId bidToken:(nullable NSString *)bidToken;

/**
 *  Called when the ad failed to display for some reason
 
 *  @param placementId      - the placementId string of the Ad that failed to be displayed.
 *  @param unitId      - the unitId string of the Ad that failed to be displayed.
 *  @param error       - error object that describes the exact error encountered when showing the ad.
 */
- (void)onVideoAdShowFailed:(nullable NSString *)placementId unitId:(nullable NSString *)unitId withError:(nonnull NSError *)error;

/**
 *  Called only when the ad has a video content, and called when the video play completed.
 
 *  @param placementId - the placementId string of the Ad that video play completed.
 *  @param unitId - the unitId string of the Ad that video play completed.
 */
- (void) onVideoPlayCompleted:(nullable NSString *)placementId unitId:(nullable NSString *)unitId;

/**
 *  Called only when the ad has a endcard content, and called when the endcard show.
 
 *  @param placementId - the placementId string of the Ad that endcard show.
 *  @param unitId - the unitId string of the Ad that endcard show.
 */
- (void) onVideoEndCardShowSuccess:(nullable NSString *)placementId unitId:(nullable NSString *)unitId;

/**
 *  Called when the ad is clicked
 *
 *  @param placementId - the placementId string of the Ad clicked.
 *  @param unitId - the unitId string of the Ad clicked.
 */
- (void)onVideoAdClicked:(nullable NSString *)placementId unitId:(nullable NSString *)unitId;

/**
 *  Called when the ad will dismiss from being displayed, and control will return to your app
 *
 *  @param placementId      - the placementId string of the Ad that has been dismissed
 *  @param unitId      - the unitId string of the Ad that has been dismissed
 *  @param converted   - BOOL describing whether the ad has converted
 *  @param rewardInfo  - the rewardInfo object containing the info that should be given to your user.
 */
- (void)onVideoAdDismissed:(nullable NSString *)placementId unitId:(nullable NSString *)unitId withConverted:(BOOL)converted withRewardInfo:(nullable MTGRewardAdInfo *)rewardInfo;

/**
 *  Called when the ad did dismissed;
 *
 *  @param unitId - the unitId string of the Ad that video play did dismissed.
 *  @param placementId - the placementId string of the Ad that video play did dismissed.
 */
- (void)onVideoAdDidClosed:(nullable NSString *)placementId unitId:(nullable NSString *)unitId;

@end


