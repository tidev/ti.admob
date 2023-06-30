//
//  MTGNativeAd.h
//  MTGSDK
//
//  Copyright © 2019 Mintegral. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "MTGCampaign.h"


#define MTGNativeAdSDKVersion @"7.3.8"



typedef NS_ENUM(NSInteger, MTGAdCategory) {
    MTGAD_CATEGORY_ALL  = 0,
    MTGAD_CATEGORY_GAME = 1,
    MTGAD_CATEGORY_APP  = 2,
};

@class MTGNativeAdManager;
@class MTGBidNativeAdManager;

/*!
 @protocol MTGNativeAdManagerDelegate
 @abstract Messages from MTGNativeAdManager indicating success or failure loading ads.
 */

@protocol MTGNativeAdManagerDelegate <NSObject>

@optional


/*!
 
 When the MTGNativeAdManager has finished loading a batch of ads this message will be sent. A batch of ads may be loaded in response to calling loadAds.
 @param nativeAds A array contains native ads (MTGCampaign).
 
 */
- (void)nativeAdsLoaded:(nullable NSArray *)nativeAds DEPRECATED_ATTRIBUTE;
- (void)nativeAdsLoaded:(nullable NSArray *)nativeAds nativeManager:(nonnull MTGNativeAdManager *)nativeManager;


/*!
 
 When the MTGNativeAdManager has reached a failure while attempting to load a batch of ads this message will be sent to the application.
 @param error An NSError object with information about the failure.
 
 */
- (void)nativeAdsFailedToLoadWithError:(nonnull NSError *)error DEPRECATED_ATTRIBUTE;
- (void)nativeAdsFailedToLoadWithError:(nonnull NSError *)error nativeManager:(nonnull MTGNativeAdManager *)nativeManager;

/*!
 
 When the MTGNativeAdManager has finished loading a batch of frames this message will be sent. A batch of frames may be loaded in response to calling loadAds.
 @param nativeFrames A array contains native frames (MTGFrame).
 
 @deprecated This method has been deprecated.
 */
- (void)nativeFramesLoaded:(nullable NSArray *)nativeFrames DEPRECATED_ATTRIBUTE;

/*!
 
 When the MTGNativeAdManager has reached a failure while attempting to load a batch of frames this message will be sent to the application.
 @param error An NSError object with information about the failure.
 
 @deprecated This method has been deprecated.
 */
- (void)nativeFramesFailedToLoadWithError:(nonnull NSError *)error DEPRECATED_ATTRIBUTE;

/*!
 
 Sent after an ad has been clicked by a user.
 
 @param nativeAd An MTGCampaign object sending the message.
 */
- (void)nativeAdDidClick:(nonnull MTGCampaign *)nativeAd DEPRECATED_ATTRIBUTE;
- (void)nativeAdDidClick:(nonnull MTGCampaign *)nativeAd nativeManager:(nonnull MTGNativeAdManager *)nativeManager;


/*!
 
 Sent after an ad url did start to resolve.
 
 @param clickUrl The click url of the ad.
 */
- (void)nativeAdClickUrlWillStartToJump:(nonnull NSURL *)clickUrl DEPRECATED_ATTRIBUTE;
- (void)nativeAdClickUrlWillStartToJump:(nonnull NSURL *)clickUrl nativeManager:(nonnull MTGNativeAdManager *)nativeManager;
/*!
 
 Sent after an ad url has jumped to a new url.
 
 @param jumpUrl The url during jumping.
 
 @discussion It will not be called if a ad's final jump url has been cached
 */
- (void)nativeAdClickUrlDidJumpToUrl:(nonnull NSURL *)jumpUrl DEPRECATED_ATTRIBUTE;
- (void)nativeAdClickUrlDidJumpToUrl:(nonnull NSURL *)jumpUrl nativeManager:(nonnull MTGNativeAdManager *)nativeManager;


/*!
 
 Sent after an ad url did reach the final jump url.
 
 @param finalUrl the final jump url of the click url.
 @param error the error generated between jumping.
 */
- (void)nativeAdClickUrlDidEndJump:(nullable NSURL *)finalUrl
                             error:(nullable NSError *)error DEPRECATED_ATTRIBUTE;

- (void)nativeAdClickUrlDidEndJump:(nullable NSURL *)finalUrl
                             error:(nullable NSError *)error nativeManager:(nonnull MTGNativeAdManager *)nativeManager;


- (void)nativeAdImpressionWithType:(MTGAdSourceType)type nativeManager:(nonnull MTGNativeAdManager *)nativeManager;


@end


/*!
 @protocol MTGBidNativeAdManagerDelegate
 @abstract Messages from MTGBidNativeAdManager indicating success or failure loading ads.
 */

@protocol MTGBidNativeAdManagerDelegate <NSObject>

@optional


/*!
 
 When the MTGBidNativeAdManager has finished loading a batch of ads this message will be sent. A batch of ads may be loaded in response to calling loadAds.
 @param nativeAds A array contains native ads (MTGCampaign).
 
 */
- (void)nativeAdsLoaded:(nullable NSArray *)nativeAds bidNativeManager:(nonnull MTGBidNativeAdManager *)bidNativeManager;


/*!
 
 When the MTGBidNativeAdManager has reached a failure while attempting to load a batch of ads this message will be sent to the application.
 @param error An NSError object with information about the failure.
 
 */
- (void)nativeAdsFailedToLoadWithError:(nonnull NSError *)error bidNativeManager:(nonnull MTGBidNativeAdManager *)bidNativeManager;

/*!
 
 Sent after an ad has been clicked by a user.
 
 @param nativeAd An MTGCampaign object sending the message.
 */
- (void)nativeAdDidClick:(nonnull MTGCampaign *)nativeAd bidNativeManager:(nonnull MTGBidNativeAdManager *)bidNativeManager;


/*!
 
 Sent after an ad url did start to resolve.
 
 @param clickUrl The click url of the ad.
 */
- (void)nativeAdClickUrlWillStartToJump:(nonnull NSURL *)clickUrl bidNativeManager:(nonnull MTGBidNativeAdManager *)bidNativeManager;
/*!
 
 Sent after an ad url has jumped to a new url.
 
 @param jumpUrl The url during jumping.
 
 @discussion It will not be called if a ad's final jump url has been cached
 */
- (void)nativeAdClickUrlDidJumpToUrl:(nonnull NSURL *)jumpUrl bidNativeManager:(nonnull MTGBidNativeAdManager *)bidNativeManager;


/*!
 
 Sent after an ad url did reach the final jump url.
 
 @param finalUrl the final jump url of the click url.
 @param error the error generated between jumping.
 */

- (void)nativeAdClickUrlDidEndJump:(nullable NSURL *)finalUrl
                             error:(nullable NSError *)error bidNativeManager:(nonnull MTGBidNativeAdManager *)bidNativeManager;


- (void)nativeAdImpressionWithType:(MTGAdSourceType)type bidNativeManager:(nonnull MTGBidNativeAdManager *)bidNativeManager;



@end

