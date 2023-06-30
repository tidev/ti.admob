//
//  MTGMediaView.h
//  MTGSDK
//
//  Copyright © 2017年 Mintegral. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "MTGCampaign.h"
NS_ASSUME_NONNULL_BEGIN

@protocol MTGMediaViewDelegate;
@class MTGNativeAdManager;

@interface MTGMediaView : UIView

/* For best user experience, keep the aspect ratio of the mediaView at 16:9 */
- (instancetype)initWithFrame:(CGRect)frame;
/**
the media source, can be set again to reuse this view.
*/
- (void)setMediaSourceWithCampaign:(MTGCampaign *)campaign unitId:(NSString*)unitId;


@property (nonatomic, weak, nullable) id<MTGMediaViewDelegate> delegate;

// Whether to allow full-screen playback, default YES
@property (nonatomic, assign) BOOL  allowFullscreen;

// Whether update to video from static image when video is ready to be played, default YES
@property (nonatomic, assign) BOOL  videoRefresh;

// Auto replay, default YES
@property (nonatomic, assign) BOOL  autoLoopPlay;
/* show video process view or not. Default to be YES. */
@property (nonatomic, assign) BOOL  showVideoProcessView;
/* show sound indicator view or not. Default to be YES. */
@property (nonatomic, assign) BOOL  showSoundIndicatorView;
/* mute audio output of the video player or not. Default to be YES, means video player is muted. */
@property (nonatomic, assign) BOOL mute;

@property (nonatomic, strong, readonly) MTGCampaign *campaign;

@property (nonatomic, readonly) NSString *unitId;

/**
 After called 'setMediaSourceWithCampaign:(MTGCampaign *)campaign unitId:(NSString*)unitId',
 you can check this MediaView whether has video content via isVideoContent if needed;
 */
@property (nonatomic,readonly,getter = isVideoContent) BOOL videoContent;

@end

@protocol MTGMediaViewDelegate <NSObject>

@optional

/*!
 @method
 
 @abstract
 Sent just before an MTGMediaView will enter the fullscreen layout.
 
 @param mediaView  An mediaView object sending the message.
 */
- (void)MTGMediaViewWillEnterFullscreen:(MTGMediaView *)mediaView;

/*!
 @method
 
 @abstract
 Sent after an FBMediaView has exited the fullscreen layout.
 
 @param mediaView  An mediaView object sending the message.
 */
- (void)MTGMediaViewDidExitFullscreen:(MTGMediaView *)mediaView;


/**
 *  Called when the native video was starting to play.
 *
 *  @param mediaView  An mediaView object sending the message.
 */
- (void)MTGMediaViewVideoDidStart:(MTGMediaView *)mediaView;

/**
*  Called when  the video play completed.
*
*  @param mediaView  An mediaView object sending the message.
*/
- (void)MTGMediaViewVideoPlayCompleted:(MTGMediaView *)mediaView;

/*!
 @method
 
 @abstract
 Sent after an ad has been clicked by a user.
 
 @param nativeAd An MTGCampaign object sending the message.
 */
- (void)nativeAdDidClick:(nonnull MTGCampaign *)nativeAd;
- (void)nativeAdDidClick:(nonnull MTGCampaign *)nativeAd mediaView:(MTGMediaView *)mediaView;


/*!
 @method
 
 @abstract
 Sent after an ad url did start to resolve.
 
 @param clickUrl The click url of the ad.
 */
- (void)nativeAdClickUrlWillStartToJump:(nonnull NSURL *)clickUrl;
- (void)nativeAdClickUrlWillStartToJump:(nonnull NSURL *)clickUrl mediaView:(MTGMediaView *)mediaView;

/*!
 @method
 
 @abstract
 Sent after an ad url has jumped to a new url.
 
 @param jumpUrl The url during jumping.
 
 @discussion It will not be called if a ad's final jump url has been cached
 */
- (void)nativeAdClickUrlDidJumpToUrl:(nonnull NSURL *)jumpUrl;
- (void)nativeAdClickUrlDidJumpToUrl:(nonnull NSURL *)jumpUrl  mediaView:(MTGMediaView *)mediaView;

/*!
 @method
 
 @abstract
 Sent after an ad url did reach the final jump url.
 
 @param finalUrl the final jump url of the click url.
 @param error the error generated between jumping.
 */
- (void)nativeAdClickUrlDidEndJump:(nullable NSURL *)finalUrl
                             error:(nullable NSError *)error;
- (void)nativeAdClickUrlDidEndJump:(nullable NSURL *)finalUrl
                             error:(nullable NSError *)error  mediaView:(MTGMediaView *)mediaView;

- (void)nativeAdImpressionWithType:(MTGAdSourceType)type mediaView:(MTGMediaView *)mediaView;


@end

NS_ASSUME_NONNULL_END
