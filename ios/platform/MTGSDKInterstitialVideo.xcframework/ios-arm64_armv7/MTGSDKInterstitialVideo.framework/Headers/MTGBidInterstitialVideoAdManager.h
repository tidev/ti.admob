//
//  MTGBidInterstitialVideoAdManager.h
//  MTGSDKInterstitialVideo
//
//  Copyright © 2019 Mintegral. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
#import "MTGInterstitialVideoAd.h"

__attribute__((deprecated("MTGBidInterstitialVideoAdManager is deprecated, and will be removed in the future release, use MTGNewInterstitialBidAdManager instead.")))
@interface MTGBidInterstitialVideoAdManager : NSObject

@property (nonatomic, weak) id  <MTGBidInterstitialVideoDelegate> _Nullable delegate;

@property (nonatomic, readonly)   NSString * _Nonnull currentUnitId;

@property (nonatomic, readonly)   NSString * _Nullable placementId;

/**
 * Play the video is mute in the beginning ,defult is NO
 *
 */
@property (nonatomic, assign) BOOL  playVideoMute;

- (nonnull instancetype)initWithPlacementId:(nullable NSString *)placementId
                                     unitId:(nonnull NSString *)unitId
                                   delegate:(nullable id<MTGBidInterstitialVideoDelegate>)delegate;
/**
 * Begins loading bidding ad content for the interstitialVideo.
 *
 * You can implement the `onInterstitialVideoLoadSuccess:` and `onInterstitialVideoLoadFail: adManager:` methods of
 * `MTGInterstitialVideoDelegate` if you would like to be notified as loading succeeds or
 * fails.
 * @param bidToken - the token from bid request within MTGBidFramework.
 */
- (void)loadAdWithBidToken:(nonnull NSString *)bidToken;

/** @name Presenting an interstitialVideo Ad */

/**
 * Presents the interstitialVideo ad modally from the specified view controller.
 *
 * @param viewController The view controller that should be used to present the interstitialVideo ad.
 */
- (void)showFromViewController:(UIViewController *_Nonnull)viewController;


/**
*  Whether the given unitId is loaded and ready to be shown.
 
* @param placementId   - the placementId string of the Ad that display.
*  @param unitId - adPositionId value in Self Service.
*
*  @return - YES if the unitId is loaded and ready to be shown, otherwise NO.
*/
- (BOOL)isVideoReadyToPlayWithPlacementId:(nullable NSString *)placementId unitId:(nonnull NSString *)unitId;


/**
 *  Clean all the video file cache from the disk.
 */
- (void)cleanAllVideoFileCache;

/**
  * Set interstitial video reward if you need，call before loadAd.
  * @param ivRewardMode  {@link MTGIVRewardMode} for list of supported types
  * @param playRate Set the timing of the reward alertView,range of 0~1(eg:set 0.6,indicates 60%).
  NOTE:In MTGIVRewardPlayMode, playRate value indicates that a reward alertView will appear when the playback reaches the set playRate.
       In MTGIVRewardCloseMode, playRate value indicates that when the close button is clicked, if the video playback time is less than the set playRate, reward alertView will appear.
 */
- (void)setIVRewardMode:(MTGIVRewardMode)ivRewardMode playRate:(CGFloat)playRate;

/**
 * Set interstitial video reward if you need，call before loadAd.
 * @param ivRewardMode  {@link MTGIVRewardMode} for list of supported types
 * @param playTime Set the timing of the reward alertView,range of 0~100s.
  NOTE:In MTGIVRewardPlayMode, playTime value indicates that a reward alertView will appear when the playback reaches the set playTime.
      In MTGIVRewardCloseMode, playTime value indicates that when the close button is clicked, if the video playback time is less than the set playTime, reward alertView will appear.
*/
- (void)setIVRewardMode:(MTGIVRewardMode)ivRewardMode playTime:(NSInteger)playTime;

/**
*  Call this method when you want custom the reward alert  display text.
*
* @param title  alert title
* @param content    alertcontent
* @param confirmText    confirm button text
* @param cancelText     cancel button text
 
 NOTE:Must be called before loadAd
*/
- (void)setAlertWithTitle:(NSString *_Nullable)title
                  content:(NSString *_Nullable)content
              confirmText:(NSString *_Nullable)confirmText
               cancelText:(NSString *_Nullable)cancelText;

/**
* get the id of this request ad,call  after onInterstitialAdLoadSuccess.
*/
- (NSString *_Nullable)getRequestIdWithUnitId:(nonnull NSString *)unitId;

@end



